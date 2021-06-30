package com.controller;

import com.dao.UserDao;
import com.entity.*;
import com.grid.datacenter.service.HplcEleServiceImpl;
import com.service.EleConWeibiaoService;
import com.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * (EleConTem)表控制层
 *
 * @author makejava
 * @since 2021-02-01 09:53:23
 */
@RestController
@RequestMapping("ele")
public class EleController {
    /**
     * 服务对象
     */

    @Resource
    private EleConWeibiaoService eleConWeibiaoService;

    @Autowired
    private UserDao userDao;

    //从Hbase取数的方法类
    private HplcEleServiceImpl hplcEleService = HplcEleServiceImpl.getInstance();


    //上传excel后发送结果excel
    @RequiresPermissions({"update:*"})
    @RequestMapping("update")
    public Result update(String path,String date ,HttpServletRequest request, HttpServletResponse response){
        //获取账号区域权限
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = this.userDao.queryByUserName(username);
        String area = user.getArea();
        //结果list
        List<Electrics> electrics = new ArrayList<>();
        String fileName = "";
        //将临时文件写入到真实文件中去
        try {
            //解析excle文件，返回以区域为key用户编号字符串为value的map
            Map<String,String> consNos = ExcelUtil.readExcel(path,area);
            //判断文件中的excel是否为空
            if (consNos.isEmpty()){
                return Result.fail("无数据");
            }
            consNos.forEach((key,value)->{
                List<String> consList = Arrays.asList(value.split(","));
                List<String> idsList = eleConWeibiaoService.selectIdByConsNo(consList);
                Map<String,EleConWeibiao> map = eleConWeibiaoService.queryByRid(idsList);
                String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
                System.out.println(ids);
                String areaNo = eleConWeibiaoService.queryAreaName(key);
                if("".equals(ids)||"".equals(areaNo)||"".equals(date)){
                    return;
                }
                if (idsList.size()>50){
                    ThreadUtil.doJob(idsList,areaNo,date,map);
                }else{
                    String jsonString = hplcEleService.getElecData(null,ids,null,areaNo,date);
                    List<Electrics> electrics1 = JsonUtil.readJson(jsonString,map);
                    ThreadUtil.addResult(electrics1);
                }
            });
            List<List<Electrics>> lists = ThreadUtil.getResult();
            int size = lists.size();
            for (int i=0;i<size;i++){
                electrics.addAll(lists.get(i));
            }
            ThreadUtil.setResult(new ArrayList<>());
            SXSSFWorkbook workbook = ExcelUtil.sendExcel(electrics);
            path = request.getSession().getServletContext().getRealPath("/")+"file";
            fileName = date+"用户96点用电量.xlsx";
            File parent = new File(path);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            String realPath = path+"/"+fileName;
            File file=new File(realPath);
            System.out.println(file);
            FileOutputStream os=new FileOutputStream(file);
            workbook.write(os);
            os.flush();
            os.close();
            //解析之后将返回得结果扔给消息服务器
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Result.success(electrics,"file/"+fileName);
    }


    @RequestMapping("updateCons")
    public Result updateCons(@RequestParam("file") MultipartFile file,String date ,HttpServletRequest request, HttpServletResponse response){
        String fileName = file.getOriginalFilename();
        FileOutputStream out = null;
        String path = "/home/jxdluser/usr/local/file";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        path = path + File.separator + fileName;
        List<Cons> list = new ArrayList<>();
        //将临时文件写入到真实文件中去
        try {
            out = new FileOutputStream(path);
            out.write(file.getBytes());
            list = ExcelUtil.readCons(path);
            //同时解析excle文件
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return Result.success(list,path);
    }

    @RequestMapping("query")
    public String queryByConsNo(String consNos, String areaCode,String date){
        List<String> list = Arrays.asList(consNos.split(","));
        List<String> idsList = eleConWeibiaoService.selectIdByConsNo(list);
        String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
        Long start = System.currentTimeMillis();
        String string = hplcEleService.getElecData(null, ids, null, areaCode, date);
        Long end = System.currentTimeMillis();
        System.out.println("从Hbase中获取数据所用时间："+(end-start));
        return string;
    }

    @RequiresPermissions({"elecon:select"})
    @RequestMapping("queryRealEle")
    public void queryRealEle(@RequestParam(required=false, defaultValue="1") Integer page, Integer limit,String tgNo,String orgNo,String date,HttpServletResponse response,HttpServletRequest request,String index) {
        Map<String,Object> map1 = new HashMap<>();
        if(("".equals(tgNo)||tgNo == null)&("".equals(orgNo)||orgNo == null)){
            map1.put("code","0");
            map1.put("msg","");
            map1.put("count","");
            map1.put("data","");
            try {
                JsonUtil.responseWriteJson(response,map1);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        Long startTime = System.currentTimeMillis();
        List<AreaIds> areaIds = eleConWeibiaoService.queryAreaByTgOrg(tgNo, orgNo);
        Long endTime = System.currentTimeMillis();
        System.out.println("获取供电所台区信息所用时间："+(endTime-startTime));
        int size = areaIds.size();
        List<Electrics> list = new ArrayList<>();
        Map<String,EleConWeibiao> map = eleConWeibiaoService.queryAllByTgOrg(tgNo,orgNo);
        for (int i =0;i<size;i++){
            AreaIds area = areaIds.get(i);
            String id = area.getIds();
            List<String> ids = Arrays.asList(id.split(","));
            int idSize = ids.size();
            Long start = System.currentTimeMillis();
            if (idSize>100){
                ThreadUtil.doJob(ids,area.getArea(),date,map);
            }else{
                String jsonStr = hplcEleService.getElecData(null,area.getIds(),null,area.getArea(),date);
                List<Electrics> electrics = JsonUtil.readJson(jsonStr,map);
                ThreadUtil.addResult(electrics);
            }
            Long end = System.currentTimeMillis();
            System.out.println(idSize+"从Hbase中获取数据并解析JSON所用时间："+(end-start));
        }
        List<List<Electrics>> lists = ThreadUtil.getResult();
        int size1 = lists.size();
        for (int i=0;i<size1;i++){
            list.addAll(lists.get(i));
        }
        ThreadUtil.setResult(new ArrayList<>());
        if(!(list.size()>0)){
            map1.put("code","0");
            map1.put("msg","");
            map1.put("count","");
            map1.put("data","");
            try {
                JsonUtil.responseWriteJson(response,map1);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        Long startTime2 = System.currentTimeMillis();
        System.out.println("sendExcel3 start");
        Map<String,Object> stringObject = null;
        try {
            stringObject = ExcelUtil.sendExcel3(list,date,Integer.parseInt(index));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("sendExcel3 end");
        Long endTimeExcel = System.currentTimeMillis();
        System.out.println("excel3所用时间："+(endTimeExcel-startTime2));
        SXSSFWorkbook workbook = (SXSSFWorkbook) stringObject.get("workbook");
        list = (List<Electrics>) stringObject.get("list");
        List<Electrics> list2 = ListUtil.page(list,page,limit);
        String path = request.getSession().getServletContext().getRealPath("/")+"file";
        String name ="";
        if(("".equals(tgNo)||tgNo == null)){
            name = list.get(1).getOrgName();
            name = name.replace("#","");
        }else{
            name = list.get(1).getTgName();
            name = name.replace("#","");
        }
        String fileName = date+name+"用户"+(96/Integer.parseInt(index))+"点用电量.xlsx";
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        String realPath = path+"/"+fileName;
        File file=new File(realPath);
        FileOutputStream os= null;
        try {
            os = new FileOutputStream(file);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Long endTime2 = System.currentTimeMillis();
        System.out.println("形成excel所用时间："+(endTime2-startTime2));
        String url = "";
        map1.put("code","0");
        map1.put("msg",url);
        map1.put("count",list.size());
        map1.put("data",list2);
        Long endTime3 = System.currentTimeMillis();
        System.out.println("程序所用时间："+(endTime3-startTime));
        try {
            JsonUtil.responseWriteJson(response,map1);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions({"elecon:select"})
    @RequestMapping("queryTgOrg")
    public void queryTgOrg(@RequestParam(required=false, defaultValue="1") Integer page, Integer limit,String tgNo,String orgNo,String date,HttpServletResponse response,HttpServletRequest request,String index) throws ServletException, IOException, ParseException {
        Map<String,Object> map1 = new HashMap<>();
        if(("".equals(tgNo)||tgNo == null)&("".equals(orgNo)||orgNo == null)){
            map1.put("code","0");
            map1.put("msg","");
            map1.put("count","");
            map1.put("data","");
            JsonUtil.responseWriteJson(response,map1);
            return;
        }
        Long startTime = System.currentTimeMillis();
        List<AreaIds> areaIds = eleConWeibiaoService.queryAreaByTgOrg(tgNo, orgNo);
        Long endTime = System.currentTimeMillis();
        System.out.println("获取供电所台区信息所用时间："+(endTime-startTime));
        int size = areaIds.size();
        List<Electrics> list = new ArrayList<>();
        Map<String,EleConWeibiao> map = eleConWeibiaoService.queryAllByTgOrg(tgNo,orgNo);
        for (int i =0;i<size;i++){
            AreaIds area = areaIds.get(i);
            String id = area.getIds();
            List<String> ids = Arrays.asList(id.split(","));
            int idSize = ids.size();
            Long start = System.currentTimeMillis();
            if (idSize>100){
                ThreadUtil.doJob(ids,area.getArea(),date,map);
            }else{
                String jsonStr = hplcEleService.getElecData(null,area.getIds(),null,area.getArea(),date);
                List<Electrics> electrics = JsonUtil.readJson(jsonStr,map);
                ThreadUtil.addResult(electrics);
            }
            Long end = System.currentTimeMillis();
            System.out.println(idSize+"从Hbase中获取数据并解析JSON所用时间："+(end-start));
        }
        List<List<Electrics>> lists = ThreadUtil.getResult();
        int size1 = lists.size();
        for (int i=0;i<size1;i++){
            list.addAll(lists.get(i));
        }
        ThreadUtil.setResult(new ArrayList<>());
        if(!(list.size()>0)){
            map1.put("code","0");
            map1.put("msg","");
            map1.put("count","");
            map1.put("data","");
            JsonUtil.responseWriteJson(response,map1);
            return;
        }
        Long startTime2 = System.currentTimeMillis();
        System.out.println("sendExcel3 start");
        Map<String,Object> stringObject = ExcelUtil.sendExcel3(list,date,Integer.parseInt(index));
        System.out.println("sendExcel3 end");
        Long endTimeExcel = System.currentTimeMillis();
        System.out.println("excel3所用时间："+(endTimeExcel-startTime2));
        SXSSFWorkbook workbook = (SXSSFWorkbook) stringObject.get("workbook");
        list = (List<Electrics>) stringObject.get("list");
        List<Electrics> list2 = ListUtil.page(list,page,limit);
        String path = request.getSession().getServletContext().getRealPath("/")+"file";
        String name ="";
        if(("".equals(tgNo)||tgNo == null)){
            name = list.get(1).getOrgName();
            name = name.replace("#","");
        }else{
            name = list.get(1).getTgName();
            name = name.replace("#","");
        }
        String fileName = date+name+"用户"+(96/Integer.parseInt(index))+"点用电量.xlsx";
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        String realPath = path+"/"+fileName;
        File file=new File(realPath);
        FileOutputStream os=new FileOutputStream(file);
        workbook.write(os);
        os.flush();
        os.close();
        Long endTime2 = System.currentTimeMillis();
        System.out.println("形成excel所用时间："+(endTime2-startTime2));
        String url = "";
        int point = 96/Integer.parseInt(index);
        double[] doubles = new double[point];
        double[] doubles2 = new double[point];
        Long startTime3 = System.currentTimeMillis();
        if(("".equals(orgNo)||orgNo == null)){
            System.out.println("开始补点");
            Map<String,Object> stringObjectMap = SupplementUtil.supplement(("file/"+fileName),request,map,name,Integer.parseInt(index));
            doubles = (double[]) stringObjectMap.get("doubles");
            doubles2 = (double[]) stringObjectMap.get("doubles2");
            url = (String) stringObjectMap.get("path");
        }
        Long endTime4 = System.currentTimeMillis();
        System.out.println("补点所用时间："+(endTime4-startTime3));
        Object str[] = new Object[] {("file/"+fileName),url,doubles,doubles2,(date+name)};
        map1.put("code","0");
        map1.put("msg",str);
        map1.put("count",list.size());
        map1.put("data",list2);
        Long endTime3 = System.currentTimeMillis();
        System.out.println("程序所用时间："+(endTime3-startTime));
        JsonUtil.responseWriteJson(response,map1);
    }

    @RequestMapping("queryByCons")
    public void queryByCons(@RequestParam(required=false, defaultValue="1") Integer page, Integer limit,String consNo,String date,HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException, ParseException {
        Map<String,Object> map = new HashMap<>();
        if ("".equals(consNo)||consNo == null){
            map.put("code","0");
            map.put("msg","");
            map.put("count","");
            map.put("data","");
            JsonUtil.responseWriteJson(response,map);
            return;
        }
        List<String> idsList = this.eleConWeibiaoService.queryByConsNo(consNo);
        String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
        String areaCode = eleConWeibiaoService.queryAreaCode(consNo);
        String string = hplcEleService.getElecData(null, ids, null, areaCode, date);
        Map<String,EleConWeibiao> map1 = eleConWeibiaoService.queryByRid(idsList);
        List<Electrics> list = JsonUtil.readJson(string,map1);
        if(!(list.size()>0)){
            map.put("code","0");
            map.put("msg","");
            map.put("count","");
            map.put("data","");
            JsonUtil.responseWriteJson(response,map);
            return;
        }
        Map<String,Object> stringObjectMap = ExcelUtil.sendExcel3(list,date,1);
        SXSSFWorkbook workbook = (SXSSFWorkbook) stringObjectMap.get("workbook");
        String path = request.getSession().getServletContext().getRealPath("/")+"file";
        String name =list.get(1).getConsName();
        String fileName = date+name+"用户96点用电量.xlsx";
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        String realPath = path+"/"+fileName;
        File file=new File(realPath);
        FileOutputStream os=new FileOutputStream(file);
        workbook.write(os);
        os.flush();
        os.close();
        stringObjectMap=TimeUtil.sort(date,list);
        List<Electrics> list1 = ListUtil.page(list,page,limit);
        Object object[] = new Object[]{("file/"+fileName),stringObjectMap.get("times"),stringObjectMap.get("uas"),stringObjectMap.get("ubs"),
                stringObjectMap.get("ucs"),stringObjectMap.get("ias"),stringObjectMap.get("ibs"),stringObjectMap.get("ics"),stringObjectMap.get("i0s"),
        stringObjectMap.get("pas"),stringObjectMap.get("pbs"),stringObjectMap.get("pcs"),stringObjectMap.get("ps"),stringObjectMap.get("ele")};
        map.put("code","0");
        map.put("msg",object);
        map.put("count",list.size());
        map.put("data",list1);
        JsonUtil.responseWriteJson(response,map);
    }

    @RequestMapping("updateAddr")
    public Result updateAddr(@RequestParam("file") MultipartFile file,String date ,HttpServletRequest request, HttpServletResponse response){
        String fileName = file.getOriginalFilename();
        FileOutputStream out = null;
        String path = "/home/jxdluser/usr/local/file";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        path = path + File.separator + fileName;
        List<String> list = new ArrayList<>();
        //将临时文件写入到真实文件中去
        try {
            out = new FileOutputStream(path);
            out.write(file.getBytes());
            //将文件读入
            InputStream in  = new FileInputStream(new File(path));
            //创建工作簿
            //XSSFWorkbook wb = new XSSFWorkbook(in);
            HSSFWorkbook wb = new HSSFWorkbook(in);
            //读取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            int totalRow=sheet.getLastRowNum();
            Row row=null;
            //循环读取科目
            for (int i = 1; i <=totalRow; i++) {
                row = sheet.getRow(i);
                list.add(row.getCell(1)+"");
            }
            //同时解析excle文件
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        int num = eleConWeibiaoService.insertBatch(list);
        return Result.success(num);
    }


    }

