package com.controller;

import com.entity.ConsEle;
import com.entity.TgLineLoss;
import com.entity.TgResult;
import com.service.TgLineLossService;
import com.util.ExcelUtil;
import com.util.JsonUtil;
import com.util.Result;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("tgLine")
public class TgLineLossController {

    @Resource
    private TgLineLossService tgLineLossService;

    @RequestMapping("queryTgLineLoss")
    public void queryTgLineLoss(String tgNo, String date, HttpServletResponse response, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String fileName = "";
        map.put("code","0");
        List<TgLineLoss> lineLosses = tgLineLossService.queryTgLineLoss(tgNo,date);
        if (!(lineLosses.size()>0)){
            map.put("msg","");
            map.put("count","");
            map.put("data","");
            try {
                JsonUtil.responseWriteJson(response,map);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        String[] time = new String [23];
        Double[] ppq = new Double[23];
        Double[] upq = new Double[23];
        Double[] lossPer = new Double[23];
        for (int i =0;i<lineLosses.size();i++){
            TgLineLoss tgLineLoss = lineLosses.get(i);
            time[i] = tgLineLoss.getEventTime();
            ppq[i] = tgLineLoss.getPpq();
            upq[i] = tgLineLoss.getUpq();
            lossPer[i]=tgLineLoss.getLossPer();
        }
        List<TgResult> tgResults = tgLineLossService.queryTgResult(tgNo,date);
        List<ConsEle> consEles = tgLineLossService.queryConsEle(tgNo,date);
        try {
            SXSSFWorkbook workbook = ExcelUtil.sendExcel4(consEles,lineLosses,tgResults.get(0));
            String path = request.getSession().getServletContext().getRealPath("/")+"file";
            String name =tgResults.get(1).getTgName();
            fileName = date+name+"小时级线损明细.xlsx";
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
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object[] obj = new Object[]{time,ppq,upq,lossPer,("file/"+fileName)};
        map.put("msg",obj);
        map.put("count","");
        map.put("data",tgResults);
        try {
            JsonUtil.responseWriteJson(response,map);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;

    }
}
