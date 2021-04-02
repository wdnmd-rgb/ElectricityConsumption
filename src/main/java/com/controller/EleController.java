package com.controller;

import com.dao.UserDao;
import com.entity.*;
import com.grid.datacenter.service.HplcEleServiceImpl;
import com.service.EleConWeibiaoService;
import com.service.ElectricsService;
import com.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Resource
    private ElectricsService electricsService;

    @Autowired
    private UserDao userDao;

    private HplcEleServiceImpl hplcEleService = HplcEleServiceImpl.getInstance();

    private Logger logger = LoggerFactory.getLogger(EleController.class);




    @RequiresPermissions({"update:*"})
    @RequestMapping("update")
    public Result update(String path,String date ,HttpServletRequest request, HttpServletResponse response){
        long startTime = System.currentTimeMillis();
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = this.userDao.queryByUserName(username);
        String area = user.getArea();
        List<Electrics> electrics = new ArrayList<>();
        String fileName = "";
        //将临时文件写入到真实文件中去
        try {
            //同时解析excle文件
            Map<String,String> consNos = ExcelUtil.readExcel(path,area);
            if (consNos.isEmpty()){
                return Result.fail("无数据");
            }
            consNos.forEach((key,value)->{
                List<String> consList = Arrays.asList(value.split(","));
                List<String> idsList = eleConWeibiaoService.selectIdByConsNo(consList);
                String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
                String areaNo = eleConWeibiaoService.queryAreaName(key);
                System.out.println("ids："+ids+"areaNo："+areaNo+"date："+date);
                if("".equals(ids)||"".equals(areaNo)||"".equals(date)){
                    return;
                }
                long startTime2 = System.currentTimeMillis();
                String jsonString = hplcEleService.getElecData(null,ids,null,areaNo,date);
                long endTime2 = System.currentTimeMillis();
                long time2 = endTime2-startTime2;
                System.out.println(key+"："+time2);
                Map<String,EleConWeibiao> map = eleConWeibiaoService.queryByRid(idsList);
                List<Electrics> list1 = JsonUtil.readJson(jsonString,map);
                electrics.addAll(list1);
            });
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
            long endTime = System.currentTimeMillis();
            long time = endTime-startTime;
            System.out.println("程序运行所用时间："+time);
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
        for (int i =0;i<size;i++){
            AreaIds area = areaIds.get(i);
            String id = area.getIds();
            List<String> ids = Arrays.asList(id.split(","));
            int idSize = ids.size();
            Long start = System.currentTimeMillis();
            if (idSize>100){
                ThreadUtil.doJob(ids,area.getArea(),date);
            }else{
                String jsonStr = hplcEleService.getElecData(null,area.getIds(),null,area.getArea(),date);
                ThreadUtil.addResultList(jsonStr);
            }
            Long end = System.currentTimeMillis();
            System.out.println(idSize+"从Hbase中获取数据所用时间："+(end-start));
        }
        Long start2 =System.currentTimeMillis();
        Map<String,EleConWeibiao> map = eleConWeibiaoService.queryAllByTgOrg(tgNo,orgNo);
        Long end2=System.currentTimeMillis();
        System.out.println("从数据库获取维表数据所用时间："+(end2-start2));
        List<String> list1 = ThreadUtil.getResultList();
        int size1 = list1.size();
        for (int i=0;i<size1;i++){
            List<Electrics> electrics = JsonUtil.readJson(list1.get(i),map);
            list.addAll(electrics);
        }
        ThreadUtil.setResultList(new ArrayList<>());
        ThreadUtil.setSuccessNum(new ArrayList<>());
        Long end3 = System.currentTimeMillis();
        System.out.println("解析JSON并形成完整数据所用时间："+(end3-end2));
        if(!(list.size()>0)){
            map1.put("code","0");
            map1.put("msg","");
            map1.put("count","");
            map1.put("data","");
            JsonUtil.responseWriteJson(response,map1);
            return;
        }
        List<Electrics> list2 = ListUtil.page(list,page,limit);
        Long startTime2 = System.currentTimeMillis();
        SXSSFWorkbook workbook = ExcelUtil.sendExcel3(list,date,Integer.parseInt(index));
        String path = request.getSession().getServletContext().getRealPath("/")+"file";
        String name ="";
        if(("".equals(tgNo)||tgNo == null)){
            name = list.get(1).getOrgName();
            name = name.replace("#","");
        }else{
            name = list.get(1).getTgName();
            name = name.replace("#","");

        }
        String fileName = date+name+"用户96点用电量.xlsx";
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
        Long endTime2 = System.currentTimeMillis();
        System.out.println("形成excel所用时间："+(endTime2-startTime2));
        String url = "";
        int point = 96/Integer.parseInt(index);
        double[] doubles = new double[point];
        double[] doubles2 = new double[point];
        Long startTime3 = System.currentTimeMillis();
        if(("".equals(orgNo)||orgNo == null)){
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
    public void queryByCons(@RequestParam(required=false, defaultValue="1") Integer page, Integer limit,String consNo,String areaCode,String date,HttpServletResponse response) throws ServletException, IOException {
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
        String string = hplcEleService.getElecData(null, ids, null, areaCode, date);
        Map<String,EleConWeibiao> map1 = eleConWeibiaoService.queryByRid(idsList);
        List<Electrics> list = JsonUtil.readJson(string,map1);
        List<Electrics> list1 = ListUtil.page(list,page,limit);
        map.put("code","0");
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list1);
        JsonUtil.responseWriteJson(response,map);
    }
    }

