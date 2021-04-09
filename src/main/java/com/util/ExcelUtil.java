package com.util;

import com.entity.Cons;
import com.entity.EleConWeibiao;
import com.entity.Electrics;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    public static Map<String,String> readExcel(String excelName,String area) throws IOException {
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
            consStr += row.getCell(4);
            areaStr += row.getCell(2);
            if ("null".equals(consStr) || "".equals(consStr)){
                consStr="";
                areaStr="";
                continue;
            }
            if(!area.equals(areaStr)){
                if(!area.equals("admin")){
                    consStr="";
                    areaStr="";
                    continue;
                }
            }
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

    public static Map<String,Object> readExcel2(String excelName) throws IOException, ParseException {
        //将文件读入
        InputStream in  = new FileInputStream(new File(excelName));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook(in);
        //HSSFWorkbook wb = new HSSFWorkbook(in);
        //读取第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        int totalRow=sheet.getLastRowNum();
        Row row=null;
        //循环读取科目
        Map<String,Electrics> map = new HashMap<>();
        Map<String,Object> mapMap = new HashMap<>();
        Set<String> set = new HashSet<>();
        for (int i = 1; i <=totalRow; i++) {
            row = sheet.getRow(i);
            String rid = row.getCell(0)+"";
            String time = row.getCell(4)+"";
            Date date = simpleDateFormat.parse(time);
            String key = rid+(date.getTime());
            Electrics electrics = new Electrics();
            electrics.setRid(rid);
            electrics.setConsNo(row.getCell(1)+"");
            electrics.setConsName(row.getCell(2)+"");
            electrics.setAreaName(row.getCell(3)+"");
            electrics.setEventTime(time);
            electrics.setPapR(Double.parseDouble(row.getCell(5)+""));
            electrics.setPapRDiff(row.getCell(6)+"");
            electrics.settFactor(Double.parseDouble(row.getCell(7)+""));
            electrics.setEle(row.getCell(8)+"");
            electrics.setTgNo(row.getCell(9)+"");
            electrics.setTgName(row.getCell(10)+"");
            electrics.setOrgNo(row.getCell(11)+"");
            electrics.setOrgName(row.getCell(12)+"");
            map.put(key,electrics);
            set.add(rid);
        }
        mapMap.put("map",map);
        mapMap.put("set",set);
        return mapMap;
    }

    public static SXSSFWorkbook  sendExcel(List<Electrics> list){
        SXSSFWorkbook workbook = new SXSSFWorkbook ();
        SXSSFSheet sheet = workbook.createSheet();
        SXSSFRow row1 = sheet.createRow(0);
        String[] row1Cell = {"设备ID","用户编号","用户名称","地区名称","数据时间","电能总示值","电能总示值差值","倍率","电量","台区编号",
                "台区名称","供电所编号","供电所名称","终端资产编号","测量点号","营销终端资产编号","ct","pt"};
        for (int i =0 ; i < row1Cell.length ; i++ ){
            row1.createCell(i).setCellValue(row1Cell[i]);
        }
        if(list!= null && list.size()>0){
            for(int j=0 ; j<list.size() ;j++){
                SXSSFRow rowEle = sheet.createRow(j+1);
                Electrics electrics = list.get(j);
                rowEle.createCell(0).setCellValue(electrics.getRid());
                rowEle.createCell(1).setCellValue(electrics.getConsNo());
                rowEle.createCell(2).setCellValue(electrics.getConsName());
                rowEle.createCell(3).setCellValue(electrics.getAreaName());
                rowEle.createCell(4).setCellValue(electrics.getEventTime());
                rowEle.createCell(5).setCellValue(electrics.getPapR());
                rowEle.createCell(6).setCellValue(electrics.getPapRDiff());
                rowEle.createCell(7).setCellValue(electrics.gettFactor());
                if ("暂无数据".equals(electrics.getPapRDiff())){
                    rowEle.createCell(8).setCellValue("暂无数据");
                }else{
                    rowEle.createCell(8).setCellValue(Double.parseDouble(electrics.getPapRDiff())*electrics.gettFactor());
                }
                rowEle.createCell(9).setCellValue(electrics.getTgNo());
                rowEle.createCell(10).setCellValue(electrics.getTgName());
                rowEle.createCell(11).setCellValue(electrics.getOrgNo());
                rowEle.createCell(12).setCellValue(electrics.getOrgName());
                rowEle.createCell(13).setCellValue(electrics.getTmnlAssetNo());
                rowEle.createCell(14).setCellValue(electrics.getMpSn());
                rowEle.createCell(15).setCellValue(electrics.getCisTmnlAssetNo());
                rowEle.createCell(16).setCellValue(electrics.getCt());
                rowEle.createCell(17).setCellValue(electrics.getPt());
            }
        }
        return workbook;
    }

    public static List<Cons> readCons(String excelName) throws IOException {
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
        List<Cons> list = new ArrayList<>();
        for (int i = 1; i <=totalRow; i++) {
            row = sheet.getRow(i);
            Cons cons = new Cons();
            cons.setConsName(row.getCell(1)+"");
            cons.setAreaName(row.getCell(2)+"");
            cons.setVoltage(row.getCell(3)+"");
            cons.setConsNo(row.getCell(4)+"");
            list.add(cons);
        }
        return list;
    }

    public static Map<String,Object>  sendExcel2(Map<String,Electrics> map, Set<String> set, String day, Map<String, EleConWeibiao> map2,int point)throws  ParseException{
        point = 96/point;
        double[] doubles = new double[point];
        double[] doubles2 = new double[point];
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        SXSSFWorkbook workbook = new SXSSFWorkbook ();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(day);
        Long time = date.getTime();
        SXSSFSheet sheet = workbook.createSheet();
        SXSSFRow row1 = sheet.createRow(0);
        String[] row1Cell = {"设备ID","用户编号","用户名称","地区名称","数据时间","电能总示值","电能总示值差值","倍率","电量","台区编号","台区名称","供电所编号","供电所名称","是否缺失数据"};
        for (int i =0 ; i < row1Cell.length ; i++ ){
            row1.createCell(i).setCellValue(row1Cell[i]);
        }
        int rows = 1;
        if(map.size()>0){
            for(String rid:set){
                for (int i = 0;i<point;i++){
                    Long time2 = time+(i*900000*(96/point));
                    String key = rid+time2;
                    Electrics electrics = map.get(key);
                    Double ele = 00.00;
                    Double papRDiff = 00.00;
                    Double T = 00.00;
                    if (electrics == null){
                        Double papR = 00.00;
                        System.out.println("i="+i);
                        if(i==0){
                            Map<String,Object> map1 = findNext((i+1),time,rid,map,point);
                            Double papRN = (Double) map1.get("papRN");
                            int index = (int) map1.get("index");
                            map1 = findNext((index+1),time,rid,map,point);
                            Double papRN2 = (Double) map1.get("papRN");
                            int index2 = (int) map1.get("index");
                            Double diff = Double.valueOf(decimalFormat.format((papRN2-papRN)/(index2-index)));
                            diff=index*diff;
                            papR = papRN-diff;
                            papR = Double.valueOf(decimalFormat.format(papR));
                        }else{
                            Map<String,Object> map1 = findNext((i+1),time,rid,map,point);
                            int index = (int) map1.get("index");
                            if (index<96){
                                Map<String,Object> map3 = findPre((i-1),time,rid,map,point);
                                int index2 = (int) map3.get("index");
                                Double papRN = (Double) map1.get("papRN");
                                Double papRP = (Double) map3.get("papRP");
                                System.out.println("index："+index+"index2："+index2);
                                System.out.println("papRN："+papRN+"papRP："+papRP);
                                Double diff = Double.valueOf(decimalFormat.format((papRN-papRP)/(index-index2)));
                                System.out.println("diff："+diff);
                                papR = papRP+diff;
                                papR = Double.valueOf(decimalFormat.format(papR));
                                papRDiff = papR-papRP;
                                papRDiff = Double.valueOf(decimalFormat.format(papRDiff));
                                System.out.println("papR："+papR);
                            }else {
                                Map<String,Object> map3 = findPre((i-1),time,rid,map,point);
                                int index2 = (int) map3.get("index");
                                Map<String,Object> map4 = findPre((index2-1),time,rid,map,point);
                                Double papRP = (Double) map3.get("papRP");
                                Double papRP2 = (Double) map4.get("papRP");
                                System.out.println("papRP："+papRP+"papRP2："+papRP2);
                                Double diff = papRP-papRP2;
                                System.out.println(diff);
                                diff = Double.valueOf(decimalFormat.format(diff));
                                System.out.println("diff："+diff);
                                papR = papRP+diff;
                                papR = Double.valueOf(decimalFormat.format(papR));
                                papRDiff = papR-papRP;
                                papRDiff = Double.valueOf(decimalFormat.format(papRDiff));
                                System.out.println("papR："+papR);
                            }
                        }
                        Electrics electrics1 = new Electrics();
                        electrics1.setPapR(papR);
                        map.put(key,electrics1);
                        EleConWeibiao eleConWeibiao = map2.get(rid);
                        ele = papRDiff*eleConWeibiao.gettFactor();
                        System.out.println("papRDiff"+papRDiff);
                        SXSSFRow rowEle = sheet.createRow(rows);
                        T=eleConWeibiao.gettFactor();
                        rowEle.createCell(0).setCellValue(rid);
                        rowEle.createCell(1).setCellValue(eleConWeibiao.getConsNo());
                        rowEle.createCell(2).setCellValue(eleConWeibiao.getConsName());
                        rowEle.createCell(3).setCellValue(eleConWeibiao.getAreaName());
                        Date date1 = new Date(time2);
                        String date2 = simpleDateFormat2.format(date1);
                        rowEle.createCell(4).setCellValue(date2);
                        rowEle.createCell(5).setCellValue(papR);
                        rowEle.createCell(6).setCellValue(papRDiff);
                        rowEle.createCell(7).setCellValue(eleConWeibiao.gettFactor());
                        rowEle.createCell(8).setCellValue(ele);
                        rowEle.createCell(9).setCellValue(eleConWeibiao.getTgNo());
                        rowEle.createCell(10).setCellValue(eleConWeibiao.getTgName());
                        rowEle.createCell(11).setCellValue(eleConWeibiao.getOrgNo());
                        rowEle.createCell(12).setCellValue(eleConWeibiao.getOrgName());
                        rowEle.createCell(13).setCellValue("是");
                        rows++;
                    }else{
                        Map<String,Object> stringObjectMap = findPre((i-1),time,rid,map,point);
                        Double papRP = (Double) stringObjectMap.get("papRP");
                        ele = (electrics.getPapR()-papRP)*electrics.gettFactor();
                        System.out.println("papRP"+papRP);
                        SXSSFRow rowEle = sheet.createRow(rows);
                        if(i == 0){
                            rowEle.createCell(6).setCellValue("暂无数据");
                            rowEle.createCell(8).setCellValue("暂无数据");
                        }else{
                            rowEle.createCell(6).setCellValue(electrics.getPapR()-papRP);
                            rowEle.createCell(8).setCellValue(ele);
                        }
                        T=electrics.gettFactor();
                        rowEle.createCell(0).setCellValue(rid);
                        rowEle.createCell(1).setCellValue(electrics.getConsNo());
                        rowEle.createCell(2).setCellValue(electrics.getConsName());
                        rowEle.createCell(3).setCellValue(electrics.getAreaName());
                        rowEle.createCell(4).setCellValue(electrics.getEventTime());
                        rowEle.createCell(5).setCellValue(electrics.getPapR());
                        rowEle.createCell(7).setCellValue(electrics.gettFactor());
                        rowEle.createCell(9).setCellValue(electrics.getTgNo());
                        rowEle.createCell(10).setCellValue(electrics.getTgName());
                        rowEle.createCell(11).setCellValue(electrics.getOrgNo());
                        rowEle.createCell(12).setCellValue(electrics.getOrgName());
                        rowEle.createCell(13).setCellValue("否");
                        rows++;
                    }
                    ele= Double.valueOf(decimalFormat.format(ele));
                    System.out.println("ele："+ele);
                    if(T!=1){
                        if (i!=0){
                            doubles2[i] += ele;
                        }
                    }else{
                        if (i!=0){
                            doubles[i] += ele;
                        }
                    }

                }
            }
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("doubles",doubles);
        map1.put("doubles2",doubles2);
        map1.put("workbook",workbook);
        return map1;
    }

    public static  Map<String,Object>  sendExcel3(List<Electrics> list,String date,int index) throws ParseException {
        Map<String,Electrics> map = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> stringObjectMap = new HashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Date date1 = simpleDateFormat1.parse(date);
        Long time = date1.getTime();
        int size = list.size();
        int rowNum = 1;
        Set<String> ids = new HashSet<>();
        for (int i=0;i<size;i++){
            Electrics electrics = list.get(i);
            String id = electrics.getRid();
            String date2 = electrics.getEventTime();
            Date date3 = simpleDateFormat.parse(date2);
            ids.add(id);
            String key = id+date3.getTime();
            map.put(key,electrics);
        }
        list = new ArrayList<>();
        SXSSFWorkbook workbook = new SXSSFWorkbook ();
        SXSSFSheet sheet = workbook.createSheet();
        SXSSFRow row1 = sheet.createRow(0);
        String[] row1Cell = {"设备ID","用户编号","用户名称","地区名称","数据时间","电能总示值","电能总示值差值","倍率","电量","台区编号",
                "台区名称","供电所编号","供电所名称","终端资产编号","测量点号","营销终端资产编号","ct","pt"};
        for (int i =0 ; i < row1Cell.length ; i++ ){
            row1.createCell(i).setCellValue(row1Cell[i]);
        }
        int point = (96/index);
        for(String rid:ids){
            Double papR = 00.00;
            Double papRp = 00.00;
            for (int i =0;i<point;i++){
                Long time3 = time+(i*900000*index);
                String key2 = rid+time3;
                Electrics electrics = map.get(key2);
                if(electrics != null){
                    SXSSFRow rowEle = sheet.createRow(rowNum);
                    papR= electrics.getPapR();
                    Double papRDiff = papR-papRp;
                    rowEle.createCell(0).setCellValue(rid);
                    rowEle.createCell(1).setCellValue(electrics.getConsNo());
                    rowEle.createCell(2).setCellValue(electrics.getConsName());
                    rowEle.createCell(3).setCellValue(electrics.getAreaName());
                    rowEle.createCell(4).setCellValue(electrics.getEventTime());
                    if (i == 0){
                        rowEle.createCell(5).setCellValue(electrics.getPapR());
                        rowEle.createCell(6).setCellValue(electrics.getPapRDiff());
                    }else{
                        rowEle.createCell(5).setCellValue(papR);
                        rowEle.createCell(6).setCellValue(decimalFormat.format(papRDiff));
                        electrics.setPapR(papR);
                        electrics.setPapRDiff(decimalFormat.format(papRDiff));
                    }
                    rowEle.createCell(7).setCellValue(electrics.gettFactor());
                    if ("暂无数据".equals(electrics.getPapRDiff())){
                        rowEle.createCell(8).setCellValue("暂无数据");
                        electrics.setEle("暂无数据");
                    }else{
                        rowEle.createCell(8).setCellValue(Double.parseDouble(decimalFormat.format(papRDiff))*electrics.gettFactor());
                        electrics.setEle(Double.parseDouble(decimalFormat.format(papRDiff))*electrics.gettFactor()+"");
                    }
                    rowEle.createCell(9).setCellValue(electrics.getTgNo());
                    rowEle.createCell(10).setCellValue(electrics.getTgName());
                    rowEle.createCell(11).setCellValue(electrics.getOrgNo());
                    rowEle.createCell(12).setCellValue(electrics.getOrgName());
                    rowEle.createCell(13).setCellValue(electrics.getTmnlAssetNo());
                    rowEle.createCell(14).setCellValue(electrics.getMpSn());
                    rowEle.createCell(15).setCellValue(electrics.getCisTmnlAssetNo());
                    rowEle.createCell(16).setCellValue(electrics.getCt());
                    rowEle.createCell(17).setCellValue(electrics.getPt());
                    papRp=papR;
                    rowNum++;
                    list.add(electrics);
                }
            }
        }
        stringObjectMap.put("workbook",workbook);
        stringObjectMap.put("list",list);
        return stringObjectMap;
    }

    public static Map<String,Object> findPre(int i,Long time,String rid,Map<String,Electrics> map,int point){
        Map<String,Object> map1 = new HashMap<>();
        Double papRP = 00.00;
        for (;i>=0;i--){
            Long time1 = time+(i*900000*(96/point));
            String key = rid+time1;
            Electrics electrics = map.get(key);
            if (electrics != null){
                papRP = electrics.getPapR();
                break;
            }
        }
        map1.put("index",i);
        map1.put("papRP",papRP);
        return map1;
    }

    public static Map<String,Object> findNext(int i,Long time,String rid,Map<String,Electrics> map,int point){
        Map<String,Object> map1 = new HashMap<>();
        Double papRN = 00.00;
        for(;i<point;i++){
            Long time2 = time+(i*900000*(96/point));
            String key = rid+time2;
            Electrics electrics = map.get(key);
            if (electrics != null){
                papRN = electrics.getPapR();
                break;
            }
        }
        map1.put("index",i);
        map1.put("papRN",papRN);
        return map1;
    }

}
