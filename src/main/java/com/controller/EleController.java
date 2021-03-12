package com.controller;

import com.dao.UserDao;
import com.entity.Cons;
import com.entity.EleConWeibiao;
import com.entity.Electrics;
import com.entity.User;
import com.grid.datacenter.service.HplcEleServiceImpl;
import com.service.EleConWeibiaoService;
import com.util.ExcelUtil;
import com.util.JsonUtil;
import com.util.ListUtil;
import com.util.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
        String realPath = "";
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
                long startTime2 = System.currentTimeMillis();
                String jsonString = hplcEleService.getElecData(null,ids,null,areaNo,date);
                long endTime2 = System.currentTimeMillis();
                long time2 = endTime2-startTime2;
                System.out.println(key+"："+time2);
                Map<String,EleConWeibiao> map = eleConWeibiaoService.queryByRid(idsList);
                List<Electrics> list1 = JsonUtil.readJson(jsonString,map);
                electrics.addAll(list1);
            });
            HSSFWorkbook workbook = ExcelUtil.sendExcel(electrics);
            path = "/pot/data-center/data/file";
            String fileName = date+"用户96点用电量.xls";
            File parent = new File(path);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            File file=new File(path,fileName);
            realPath = path+fileName;
            OutputStream os=new FileOutputStream(file);
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
        return Result.success(electrics,realPath);
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


    @RequiresPermissions({"elecon:select"})
    @RequestMapping("query")
    public Result queryByConsNo(String consNos, String areaCode,String date){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = this.userDao.queryByUserName(username);
        String area = user.getArea();
        String areaName = eleConWeibiaoService.queryByAreaNo(areaCode);
        if(!area.equals(areaName)){
            return Result.fail("权限不足！");
        }
        List<String> list = Arrays.asList(consNos.split(","));
        List<String> idsList = eleConWeibiaoService.selectIdByConsNo(list);
        String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
        String string = hplcEleService.getElecData(null, ids, null, areaCode, date);
        Map<String,EleConWeibiao> map = eleConWeibiaoService.queryByRid(idsList);
        List<Electrics> list1 = JsonUtil.readJson(string,map);
        return Result.success(list1);
    }



    @RequiresPermissions({"update:*"})
    @RequestMapping("queryByTgOrg")
    public void queryByTgOrg(String tgNo,String orgNo,String date,HttpServletResponse response)  {
        if(("".equals(tgNo)||tgNo == null)&("".equals(orgNo)||orgNo == null)){
            return;
        }
        List<String> idsList = eleConWeibiaoService.queryByTgOrg(tgNo,orgNo);
        String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
        String area = eleConWeibiaoService.queryAreaByTgOrg(tgNo,orgNo);
        String jsonStr = hplcEleService.getElecData(null,ids,null,area,date);
        Map<String,EleConWeibiao> map = eleConWeibiaoService.queryByRid(idsList);
        List<Electrics> list = JsonUtil.readJson(jsonStr,map);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" +date+new String("用户96点用电量.xls".getBytes(), "ISO-8859-1"));
            response.setContentType("application/x-excel;charset=UTF-8");
            HSSFWorkbook workbook = ExcelUtil.sendExcel(list);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions({"elecon:select"})
    @RequestMapping("queryTgOrg")
    public void queryTgOrg(@RequestParam(required=false, defaultValue="1") Integer page, Integer limit,String tgNo,String orgNo,String date,HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> map1 = new HashMap<>();
        if(("".equals(tgNo)||tgNo == null)&("".equals(orgNo)||orgNo == null)){
            map1.put("code","0");
            map1.put("msg","");
            map1.put("count","");
            map1.put("data","");
            JsonUtil.responseWriteJson(response,map1);
            return;
        }
        List<String> idsList = eleConWeibiaoService.queryByTgOrg(tgNo,orgNo);
        String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
        String area = eleConWeibiaoService.queryAreaByTgOrg(tgNo,orgNo);
        String jsonStr = hplcEleService.getElecData(null,ids,null,area,date);
        Map<String,EleConWeibiao> map = eleConWeibiaoService.queryByRid(idsList);
        List<Electrics> list = JsonUtil.readJson(jsonStr,map);
        List<Electrics> list1 = ListUtil.page(list,page,limit);
        map1.put("code","0");
        map1.put("msg","");
        map1.put("count",list.size());
        map1.put("data",list1);
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