package com.controller;

import com.entity.*;
import com.service.EleConWeibiaoService;
import com.service.TgLineLossService;
import com.util.ExcelUtil;
import com.util.JsonUtil;
import com.util.Result;
import com.util.TgLineLossUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tgLine")
public class TgLineLossController {

    @Resource
    private TgLineLossService tgLineLossService;


    @RequiresPermissions({"tgLineLoss:select"})
    @RequestMapping("queryTgLineLoss")
    public void queryTgLineLoss(String tgNo, String date, int index, HttpServletResponse response, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String fileName = "";
        map.put("code", "0");
        List<TgLineLoss> lineLosses = new ArrayList<>();
        String[] time = new String[(24 / index) - 1];
        Double[] ppq = new Double[(24 / index) - 1];
        Double[] loss = new Double[(24 / index) - 1];
        Double[] upq = new Double[(24 / index) - 1];
        Double[] lossPer = new Double[(24 / index) - 1];
        Double[] ppqTol = new Double[(24 / index) - 1];
        Double[] upqTol = new Double[(24 / index) - 1];
        Double[] lossTol = new Double[(24 / index) - 1];
        Double[] lossPerTol = new Double[(24 / index) - 1];
        Integer[] remarks = new Integer[(24 / index) - 1];
        Long startTime = System.currentTimeMillis();
        List<ConsEle> consEles = tgLineLossService.queryConsEle(tgNo, date);
        TgResult tgResult = new TgResult();
        try {
            Map<String, Object> stringObjectMap = TgLineLossUtil.doJob(consEles, date, index);
            lineLosses = (List<TgLineLoss>) stringObjectMap.get("lineLosses");
            tgResult = (TgResult) stringObjectMap.get("tgResult");
            consEles = (List<ConsEle>) stringObjectMap.get("consEles");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!(lineLosses.size() > 0)) {
            map.put("msg", "");
            map.put("count", "");
            map.put("data", "");
            try {
                JsonUtil.responseWriteJson(response, map);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        Long endTime2 = System.currentTimeMillis();
        System.out.println("???????????????" + (endTime2 - startTime));
        for (int i = 0; i < lineLosses.size(); i++) {
            TgLineLoss tgLineLoss = lineLosses.get(i);
            time[i] = tgLineLoss.getEventTime();
            ppq[i] = tgLineLoss.getPpq();
            upq[i] = tgLineLoss.getUpq();
            loss[i] = tgLineLoss.getLossPq();
            lossPer[i] = tgLineLoss.getLossPer();
            ppqTol[i] = tgLineLoss.getPpqTol();
            upqTol[i] = tgLineLoss.getUpqTol();
            lossTol[i] = tgLineLoss.getLossPqTol();
            lossPerTol[i] = tgLineLoss.getLossPerTol();
            remarks[i] = tgLineLoss.getRemark();
        }
        Long endTime3 = System.currentTimeMillis();
        String date1 = date.replaceAll("-", "");
        List<TgResult> tgResults = tgLineLossService.queryTgResult(tgNo, date1);
        if (tgResults.size() == 0) {
            tgResults = tgLineLossService.queryTgResult2(tgNo);
        }
        TgResult tgResult1 = tgResults.get(0);
        tgResult1.setRealCount(tgResult.getRealCount());
        tgResult1.setEventTime(tgResult.getEventTime());
        tgResult1.setRemark0(tgResult.getRemark0());
        tgResult1.setRemark1(tgResult.getRemark1());
        tgResult1.setRemark2(tgResult.getRemark2());
        tgResult1.setRemark3(tgResult.getRemark3());
        tgResults.remove(0);
        tgResults.add(tgResult1);
        Long endTime4 = System.currentTimeMillis();
        System.out.println("??????tgResults???consEles???" + (endTime4 - endTime3));
        try {
            SXSSFWorkbook workbook = ExcelUtil.sendExcel4(consEles, lineLosses, tgResult1);
            String path = request.getSession().getServletContext().getRealPath("/") + "file";
            String name = tgResult1.getTgName();
            name = name.replace("#", "");
            fileName = date + name + "?????????????????????.xlsx";
            File parent = new File(path);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            String realPath = path + "/" + fileName;
            File file = new File(realPath);
            FileOutputStream os = new FileOutputStream(file);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long endTime5 = System.currentTimeMillis();
        System.out.println("??????Excel???" + (endTime5 - endTime4));
        Object[] obj = new Object[]{time, ppq, loss, lossPer, ("file/" + fileName), remarks, ppqTol, lossTol, lossPerTol};
        map.put("msg", obj);
        map.put("count", "");
        map.put("data", tgResults);
        try {
            JsonUtil.responseWriteJson(response, map);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;

    }

    @RequiresPermissions({"tgLineLoss:select"})
    @RequestMapping("queryMonitoringTg")
    public void queryMonitoringTg(String tgNo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();
        List<MonitoringTg> monitoringTgs = tgLineLossService.queryMonitoringTg(tgNo, page, limit);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", tgLineLossService.selectMonitoringTgNum(tgNo));
        resultMap.put("data", monitoringTgs);
        try {
            JsonUtil.responseWriteJson(response, resultMap);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions({"tgLineLoss:add"})
    @ResponseBody
    @RequestMapping("addMonitoringTg")
    public Result addMonitoringTg(String tgNo) {
        int num = tgLineLossService.selectTgNum(tgNo);
        if (num == 0) {
            return Result.fail("????????????????????????????????????????????????");
        }
        num = tgLineLossService.selectMonitoringTgNum(tgNo);
        if (num > 0) {
            return Result.fail("????????????????????????????????????");
        }
        boolean flag = tgLineLossService.addMonitoringTg(tgNo);
        if (flag) {
            return Result.success();
        }
        return Result.fail("???????????????");
    }


    @RequestMapping("queryTgReport")
    public void queryTgReport(String tgNo, String date, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, HttpServletResponse response) {
        List<TgReport> list = tgLineLossService.queryTgReport(tgNo, date, page, limit);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", tgLineLossService.selectTgReportNum(tgNo, date));
        resultMap.put("data", list);
        try {
            JsonUtil.responseWriteJson(response, resultMap);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions({"tgLineLoss:select"})
    @RequestMapping("queryOrgReport")
    public void queryOrgReport(String orgNo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, HttpServletResponse response) {
        System.out.println("in");
        List<OrgReport> list = tgLineLossService.queryOrgReport(orgNo, page, limit);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", tgLineLossService.selectOrgReportNum(orgNo));
        resultMap.put("data", list);
        try {
            JsonUtil.responseWriteJson(response, resultMap);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions({"tgLineLoss:select"})
    @RequestMapping("queryTgConsReport")
    public void queryTgConsReport(TgConsReport tgConsReport, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, HttpServletResponse response, HttpServletRequest request) {
        List<TgConsReport> list = tgLineLossService.queryTgConsReport(tgConsReport, page, limit);
        List<TgConsReport> tgConsReports = tgLineLossService.queryTgConsReport(tgConsReport);
        String fileName = "";
        try {
            SXSSFWorkbook workbook = ExcelUtil.sendConsReport(tgConsReports);
            String path = request.getSession().getServletContext().getRealPath("/") + "file";
            fileName = tgConsReport.getDateDay() +"  "+tgConsReport.getOrgNo() + "??????????????????????????????.xlsx";
            File parent = new File(path);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            String realPath = path + "/" + fileName;
            File file = new File(realPath);
            FileOutputStream os = new FileOutputStream(file);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "file/"+fileName);
        resultMap.put("count", tgLineLossService.selectTgConsReportNum(tgConsReport));
        resultMap.put("data", list);
        try {
            JsonUtil.responseWriteJson(response, resultMap);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions({"tgLineLoss:select"})
    @RequestMapping("queryTgLossReport")
    public void queryTgLossReport(TgLossReport tgLossReport, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, HttpServletResponse response) {
        System.out.println(tgLossReport);
        System.out.println("in");
        List<TgLossReport> list =  tgLineLossService.queryTgLossReport(tgLossReport, page, limit);
        System.out.println(list.size());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", tgLineLossService.selectTgLossReportNum(tgLossReport));
        resultMap.put("data", list);
        try {
            JsonUtil.responseWriteJson(response, resultMap);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("queryExcCons")
    public void queryExcCons(ExcConsReport excConsReport, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit, HttpServletResponse response) {
        System.out.println("in");
        List<ExcConsReport> list = tgLineLossService.queryExcConsReport(excConsReport, page, limit);
        System.out.println(list.size());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", tgLineLossService.selectExcConsReportNum(excConsReport));
        resultMap.put("data", list);
        try {
            JsonUtil.responseWriteJson(response, resultMap);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("queryRelationCity")
    public void queryRelationCity(HttpServletResponse response){
        List<Relation> list = tgLineLossService.queryRelationCity();
        try {
            JsonUtil.responseWriteJson(response,list);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("queryRelationCounty")
    public void queryRelationCounty(Relation relation,HttpServletResponse response){
        List<Relation> list = tgLineLossService.queryRelationCounty(relation);
        try {
            JsonUtil.responseWriteJson(response,list);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("queryRelationOrg")
    public void queryRelationOrg(Relation relation,HttpServletResponse response){
        List<Relation> list = tgLineLossService.queryRelationOrg(relation);
        try {
            JsonUtil.responseWriteJson(response,list);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("queryRelationTg")
    public void queryRelationTg(Relation relation,HttpServletResponse response){
        List<Relation> list = tgLineLossService.queryRelationTg(relation);
        try {
            JsonUtil.responseWriteJson(response,list);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
