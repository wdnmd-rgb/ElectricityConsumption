package com.util;

import com.entity.EleConWeibiao;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelUtil {

    public static List<String> readExcel(String excelName) throws IOException {
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
        int num = sheet.getRow(0).getPhysicalNumberOfCells();
        String str = "";
        List<String> consNos = new ArrayList<>();
        for (int i = 1; i <=totalRow; i++) {
            row = sheet.getRow(i);
            str += row.getCell(1);
            if(str.contains(",")){
                List<String> list = Arrays.asList(str.split(","));
                for (int j = 0;j<list.size();j++){
                    consNos.add(list.get(j));
                }
            }else{
                consNos.add(str);
            }
            str="";
        }
        return consNos;
    }

    public static HSSFWorkbook  sendExcel(List<EleConWeibiao> list){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row1 = sheet.createRow(0);
        String[] row1Cell = {"设备ID","用户编号","用户名称","地区名称","数据时间","用电量"};
        for (int i =0 ; i < row1Cell.length ; i++ ){
            row1.createCell(i).setCellValue(row1Cell[i]);
        }
        if(list!= null && list.size()>0){
            for(int j=0 ; j<list.size() ;j++){
                HSSFRow rowEle = sheet.createRow(j+1);
                EleConWeibiao eleConWeibiao = list.get(j);
                rowEle.createCell(0).setCellValue(eleConWeibiao.getRid());
                rowEle.createCell(1).setCellValue(eleConWeibiao.getConsNo());
                rowEle.createCell(2).setCellValue(eleConWeibiao.getConsName());
                rowEle.createCell(3).setCellValue(eleConWeibiao.getAreaName());
                rowEle.createCell(4).setCellValue(eleConWeibiao.getEventTime());
                rowEle.createCell(5).setCellValue(eleConWeibiao.getEle());
            }
        }
        return workbook;
    }
}
