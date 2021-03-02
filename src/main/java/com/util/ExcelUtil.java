package com.util;

import com.entity.EleConWeibiao;
import com.entity.Electrics;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.*;

public class ExcelUtil {

    public static Map<String,String> readExcel(String excelName) throws IOException {
        //将文件读入
        InputStream in  = new FileInputStream(new File(excelName));
        //创建工作簿
        //XSSFWorkbook wb = new XSSFWorkbook(in);
        HSSFWorkbook wb = new HSSFWorkbook(in);
        //读取第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        int totalRow=sheet.getLastRowNum();
        Row row=null;
        //循环读取科目
        String consStr = "";
        String areaStr = "";
        Map<String,String> map = new HashMap<>();
        for (int i = 1; i <=totalRow; i++) {
            row = sheet.getRow(i);
            consStr += row.getCell(1);
            areaStr += row.getCell(2);
            if(map.containsKey(areaStr)){
                String string = map.get(areaStr);
                map.put(areaStr,string+","+consStr);
            }else {
                map.put(areaStr,consStr);
            }
            consStr="";
            areaStr="";
        }
        return map;
    }

    public static HSSFWorkbook  sendExcel(List<Electrics> list){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row1 = sheet.createRow(0);
        String[] row1Cell = {"设备ID","用户编号","用户名称","地区名称","数据时间","电能总示值","倍率"};
        for (int i =0 ; i < row1Cell.length ; i++ ){
            row1.createCell(i).setCellValue(row1Cell[i]);
        }
        if(list!= null && list.size()>0){
            for(int j=0 ; j<list.size() ;j++){
                HSSFRow rowEle = sheet.createRow(j+1);
                Electrics electrics = list.get(j);
                rowEle.createCell(0).setCellValue(electrics.getRid());
                rowEle.createCell(1).setCellValue(electrics.getConsNo());
                rowEle.createCell(2).setCellValue(electrics.getConsName());
                rowEle.createCell(3).setCellValue(electrics.getAreaName());
                rowEle.createCell(4).setCellValue(electrics.getEventTime());
                rowEle.createCell(5).setCellValue(electrics.getPapR());
                rowEle.createCell(6).setCellValue(electrics.gettFactor());
            }
        }
        return workbook;
    }
}
