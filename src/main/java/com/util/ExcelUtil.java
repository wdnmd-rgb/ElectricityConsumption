package com.util;

import com.entity.*;
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
            electrics.setEventTime(time);
            electrics.setPapR(Double.parseDouble(row.getCell(5)+""));
            electrics.setPapRDiff(row.getCell(6)+"");
            electrics.settFactor(Double.parseDouble(row.getCell(7)+""));
            electrics.setEle(row.getCell(8)+"");
            electrics.setUa(row.getCell(20)+"");
            electrics.setUb(row.getCell(21)+"");
            electrics.setUc(row.getCell(22)+"");
            electrics.setIa(row.getCell(23)+"");
            electrics.setIb(row.getCell(24)+"");
            electrics.setIc(row.getCell(25)+"");
            electrics.setI0(row.getCell(26)+"");
            electrics.setP(row.getCell(27)+"");
            electrics.setPa(row.getCell(28)+"");
            electrics.setPb(row.getCell(29)+"");
            electrics.setPc(row.getCell(30)+"");
            electrics.setQ(row.getCell(31)+"");
            electrics.setQa(row.getCell(32)+"");
            electrics.setQb(row.getCell(33)+"");
            electrics.setQc(row.getCell(34)+"");
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
                "台区名称","供电所编号","供电所名称","资产编号","终端资产编号","测量点号","营销终端资产编号","ct","pt","计量点分类","ua","ub","uc",
                "ia","ib","ic","i0","p","pa","pb","pc","q","qa","qb","qc"};
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
                rowEle.createCell(13).setCellValue(electrics.getAssetNo());
                rowEle.createCell(14).setCellValue(electrics.getTmnlAssetNo());
                rowEle.createCell(15).setCellValue(electrics.getMpSn());
                rowEle.createCell(16).setCellValue(electrics.getCisTmnlAssetNo());
                rowEle.createCell(17).setCellValue(electrics.getCt());
                rowEle.createCell(18).setCellValue(electrics.getPt());
                rowEle.createCell(19).setCellValue(electrics.getTypeCode());
                rowEle.createCell(20).setCellValue(electrics.getUa());
                rowEle.createCell(21).setCellValue(electrics.getUb());
                rowEle.createCell(22).setCellValue(electrics.getUc());
                rowEle.createCell(23).setCellValue(electrics.getIa());
                rowEle.createCell(24).setCellValue(electrics.getIb());
                rowEle.createCell(25).setCellValue(electrics.getIc());
                rowEle.createCell(26).setCellValue(electrics.getI0());
                rowEle.createCell(27).setCellValue(electrics.getP());
                rowEle.createCell(28).setCellValue(electrics.getPa());
                rowEle.createCell(29).setCellValue(electrics.getPb());
                rowEle.createCell(30).setCellValue(electrics.getPc());
                rowEle.createCell(31).setCellValue(electrics.getQ());
                rowEle.createCell(32).setCellValue(electrics.getQa());
                rowEle.createCell(33).setCellValue(electrics.getQb());
                rowEle.createCell(34).setCellValue(electrics.getQc());
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
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(day);
        Long time = date.getTime();
        SXSSFSheet sheet = workbook.createSheet();
        SXSSFRow row1 = sheet.createRow(0);
        String[] row1Cell = {"设备ID","用户编号","用户名称","地区名称","数据时间","电能总示值","电能总示值差值","倍率","电量","台区编号","台区名称","供电所编号","供电所名称","是否缺失数据","资产编号","终端资产编号","测量点号","营销终端资产编号","ct","pt","计量点分类","ua","ub","uc",
                "ia","ib","ic","i0","p","pa","pb","pc","q","qa","qb","qc"};
        for (int i =0 ; i < row1Cell.length ; i++ ){
            row1.createCell(i).setCellValue(row1Cell[i]);
        }
        int rows = 1;
        if(map.size()>0){
            for(String rid:set){
                for (int i = 0;i<point;i++,rows++){
                    Long time2 = time+(i*900000*(96/point));
                    String key = rid+time2;
                    Electrics electrics = map.get(key);
                    Double ele = 00.00;
                    Double papRDiff = 00.00;
                    SXSSFRow rowEle = sheet.createRow(rows);
                    EleConWeibiao eleConWeibiao = map2.get(rid);
                    if (electrics == null){
                        Double papR = 00.00;
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
                                Double diff = Double.valueOf(decimalFormat.format((papRN-papRP)/(index-index2)));
                                papR = papRP+diff;
                                papR = Double.valueOf(decimalFormat.format(papR));
                                papRDiff = papR-papRP;
                                papRDiff = Double.valueOf(decimalFormat.format(papRDiff));
                            }else {
                                Map<String,Object> map3 = findPre((i-1),time,rid,map,point);
                                int index2 = (int) map3.get("index");
                                Map<String,Object> map4 = findPre((index2-1),time,rid,map,point);
                                Double papRP = (Double) map3.get("papRP");
                                Double papRP2 = (Double) map4.get("papRP");
                                Double diff = papRP-papRP2;
                                diff = Double.valueOf(decimalFormat.format(diff));
                                papR = papRP+diff;
                                papR = Double.valueOf(decimalFormat.format(papR));
                                papRDiff = papR-papRP;
                                papRDiff = Double.valueOf(decimalFormat.format(papRDiff));
                            }
                        }
                        Electrics electrics1 = new Electrics();
                        electrics1.setPapR(papR);
                        map.put(key,electrics1);
                        ele = papRDiff*eleConWeibiao.gettFactor();
                        Date date1 = new Date(time2);
                        String date2 = simpleDateFormat2.format(date1);
                        rowEle.createCell(4).setCellValue(date2);
                        rowEle.createCell(5).setCellValue(papR);
                        rowEle.createCell(6).setCellValue(papRDiff);
                        rowEle.createCell(8).setCellValue(ele);
                        rowEle.createCell(13).setCellValue("是");
                        for (int k=21;k<36;k++){
                            rowEle.createCell(k).setCellValue("暂无数据");
                        }
                    }else{
                        Map<String,Object> stringObjectMap = findPre((i-1),time,rid,map,point);
                        Double papRP = (Double) stringObjectMap.get("papRP");
                        ele = (electrics.getPapR()-papRP)*electrics.gettFactor();
                        if(i == 0){
                            rowEle.createCell(6).setCellValue("暂无数据");
                            rowEle.createCell(8).setCellValue("暂无数据");
                        }else{
                            rowEle.createCell(6).setCellValue(electrics.getPapR()-papRP);
                            rowEle.createCell(8).setCellValue(ele);
                        }
                        rowEle.createCell(4).setCellValue(electrics.getEventTime());
                        rowEle.createCell(5).setCellValue(electrics.getPapR());
                        rowEle.createCell(13).setCellValue("否");
                        rowEle.createCell(21).setCellValue(electrics.getUa());
                        rowEle.createCell(22).setCellValue(electrics.getUb());
                        rowEle.createCell(23).setCellValue(electrics.getUc());
                        rowEle.createCell(24).setCellValue(electrics.getIa());
                        rowEle.createCell(25).setCellValue(electrics.getIb());
                        rowEle.createCell(26).setCellValue(electrics.getIc());
                        rowEle.createCell(27).setCellValue(electrics.getI0());
                        rowEle.createCell(28).setCellValue(electrics.getP());
                        rowEle.createCell(29).setCellValue(electrics.getPa());
                        rowEle.createCell(30).setCellValue(electrics.getPb());
                        rowEle.createCell(31).setCellValue(electrics.getPc());
                        rowEle.createCell(32).setCellValue(electrics.getQ());
                        rowEle.createCell(33).setCellValue(electrics.getQa());
                        rowEle.createCell(34).setCellValue(electrics.getQb());
                        rowEle.createCell(35).setCellValue(electrics.getQc());
                    }
                    rowEle.createCell(0).setCellValue(rid);
                    rowEle.createCell(1).setCellValue(eleConWeibiao.getConsNo());
                    rowEle.createCell(2).setCellValue(eleConWeibiao.getConsName());
                    rowEle.createCell(3).setCellValue(eleConWeibiao.getAreaName());
                    rowEle.createCell(7).setCellValue(eleConWeibiao.gettFactor());
                    rowEle.createCell(9).setCellValue(eleConWeibiao.getTgNo());
                    rowEle.createCell(10).setCellValue(eleConWeibiao.getTgName());
                    rowEle.createCell(11).setCellValue(eleConWeibiao.getOrgNo());
                    rowEle.createCell(12).setCellValue(eleConWeibiao.getOrgName());
                    rowEle.createCell(14).setCellValue(eleConWeibiao.getAssetNo());
                    rowEle.createCell(15).setCellValue(eleConWeibiao.getTmnlAssetNo());
                    rowEle.createCell(16).setCellValue(eleConWeibiao.getMpSn());
                    rowEle.createCell(17).setCellValue(eleConWeibiao.getCisTmnlAssetNo());
                    rowEle.createCell(18).setCellValue(eleConWeibiao.getCt());
                    rowEle.createCell(19).setCellValue(eleConWeibiao.getPt());
                    rowEle.createCell(20).setCellValue(eleConWeibiao.getTypeCode());
                    ele= Double.valueOf(decimalFormat.format(ele));
                    if("01".equals(eleConWeibiao.getTypeCode())){
                        if (i!=0){
                            doubles[i] += ele;
                        }
                    }else{
                        if (i!=0){
                            doubles2[i] += ele;
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
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> stringObjectMap = TimeUtil.makeMap(list);
        Map<String,Electrics> map = (Map<String, Electrics>) stringObjectMap.get("map");
        Set<String> ids = (Set<String>) stringObjectMap.get("set");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Date date1 = simpleDateFormat1.parse(date);
        Long time = date1.getTime();
        int rowNum = 1;
        list = new ArrayList<>();
        SXSSFWorkbook workbook = new SXSSFWorkbook ();
        SXSSFSheet sheet = workbook.createSheet();
        SXSSFRow row1 = sheet.createRow(0);
        String[] row1Cell = {"设备ID","用户编号","用户名称","地区名称","数据时间","电能总示值","电能总示值差值","倍率","电量","台区编号",
                "台区名称","供电所编号","供电所名称","资产编号","终端资产编号","测量点号","营销终端资产编号","ct","pt","计量点分类","ua","ub","uc",
                "ia","ib","ic","i0","p","pa","pb","pc","q","qa","qb","qc"};
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
                    rowEle.createCell(13).setCellValue(electrics.getAssetNo());
                    rowEle.createCell(14).setCellValue(electrics.getTmnlAssetNo());
                    rowEle.createCell(15).setCellValue(electrics.getMpSn());
                    rowEle.createCell(16).setCellValue(electrics.getCisTmnlAssetNo());
                    rowEle.createCell(17).setCellValue(electrics.getCt());
                    rowEle.createCell(18).setCellValue(electrics.getPt());
                    rowEle.createCell(19).setCellValue(electrics.getTypeCode());
                    rowEle.createCell(20).setCellValue(electrics.getUa());
                    rowEle.createCell(21).setCellValue(electrics.getUb());
                    rowEle.createCell(22).setCellValue(electrics.getUc());
                    rowEle.createCell(23).setCellValue(electrics.getIa());
                    rowEle.createCell(24).setCellValue(electrics.getIb());
                    rowEle.createCell(25).setCellValue(electrics.getIc());
                    rowEle.createCell(26).setCellValue(electrics.getI0());
                    rowEle.createCell(27).setCellValue(electrics.getP());
                    rowEle.createCell(28).setCellValue(electrics.getPa());
                    rowEle.createCell(29).setCellValue(electrics.getPb());
                    rowEle.createCell(30).setCellValue(electrics.getPc());
                    rowEle.createCell(31).setCellValue(electrics.getQ());
                    rowEle.createCell(32).setCellValue(electrics.getQa());
                    rowEle.createCell(33).setCellValue(electrics.getQb());
                    rowEle.createCell(34).setCellValue(electrics.getQc());
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

    public static  SXSSFWorkbook  sendExcel4(List<ConsEle> consEles, List<TgLineLoss> lineLosses,TgResult tgResult) throws ParseException {
        SXSSFWorkbook workbook = new SXSSFWorkbook ();
        SXSSFSheet sheet = workbook.createSheet("台区小时级线损");
        SXSSFSheet sheet1 = workbook.createSheet("台区用户电量明细");
        SXSSFRow row1 = sheet.createRow(0);
        SXSSFRow row2 = sheet.createRow(1);
        String[] row1Cell = {"供电所名称","台区编号","台区名称","数据时间","台区用户数","实际用户数",
                "丢失点数量","跳变点数量","负值点数量","缺失点数量"};
        for (int i =0 ; i < row1Cell.length ; i++ ){
            row1.createCell(i).setCellValue(row1Cell[i]);
        }
        row2.createCell(0).setCellValue(tgResult.getOrgName());
        row2.createCell(1).setCellValue(tgResult.getTgNo());
        row2.createCell(2).setCellValue(tgResult.getTgName());
        row2.createCell(3).setCellValue(tgResult.getEventTime());
        row2.createCell(4).setCellValue(tgResult.getCount());
        row2.createCell(5).setCellValue(tgResult.getRealCount());
        row2.createCell(6).setCellValue(tgResult.getRemark0());
        row2.createCell(7).setCellValue(tgResult.getRemark1());
        row2.createCell(8).setCellValue(tgResult.getRemark2());
        row2.createCell(9).setCellValue(tgResult.getRemark3());

        SXSSFRow row3 = sheet.createRow(3);
        String[] row3Cell = {"数据时间","考核表电量","用户表电量","损失电量","线损率(%)",
                "考核表电量(累计)","用户表电量(累计)","损失电量(累计)","线损率(累计%)",
                "该时间点丢失的值"};
        for (int i =0 ; i < row3Cell.length ; i++ ){
            row3.createCell(i).setCellValue(row3Cell[i]);
        }
        int rowNum = 4;
        int size = lineLosses.size();
        for (int i=0;i<size;i++,rowNum++){
            SXSSFRow row = sheet.createRow(rowNum);
            TgLineLoss tgLineLoss = lineLosses.get(i);
            row.createCell(0).setCellValue(tgLineLoss.getEventTime());
            row.createCell(1).setCellValue(tgLineLoss.getPpq());
            row.createCell(2).setCellValue(tgLineLoss.getUpq());
            row.createCell(3).setCellValue(tgLineLoss.getLossPq());
            row.createCell(4).setCellValue(tgLineLoss.getLossPer());
            row.createCell(5).setCellValue(tgLineLoss.getPpqTol());
            row.createCell(6).setCellValue(tgLineLoss.getUpqTol());
            row.createCell(7).setCellValue(tgLineLoss.getLossPqTol());
            row.createCell(8).setCellValue(tgLineLoss.getLossPerTol());
            row.createCell(9).setCellValue(tgLineLoss.getRemark());
        }
        SXSSFRow row4 = sheet1.createRow(0);
        String[] rowCell = {"设备ID","用户编号","用户名称","倍率","数据时间","电能总示值","电能总示值差值","电量","ua","ub","uc",
                "ia","ib","ic","i0","p","pa","pb","pc","q","qa","qb","qc","remark","rap","rapR","rapDiff","rapEle"};
        for (int i =0 ; i < rowCell.length ; i++ ){
            row4.createCell(i).setCellValue(rowCell[i]);
        }
        rowNum = 1;
        size = consEles.size();
        for (int i=0;i<size;i++,rowNum++){
            SXSSFRow row = sheet1.createRow(rowNum);
            ConsEle consEle = consEles.get(i);
            row.createCell(0).setCellValue(consEle.getRid());
            row.createCell(1).setCellValue(consEle.getConsNo());
            row.createCell(2).setCellValue(consEle.getConsName());
            row.createCell(3).setCellValue(consEle.gettFactor());
            row.createCell(4).setCellValue(consEle.getEventTime());
            row.createCell(5).setCellValue(consEle.getPapR());
            row.createCell(6).setCellValue(consEle.getPapRDiff());
            row.createCell(7).setCellValue(consEle.getEle());
            row.createCell(8).setCellValue(consEle.getUa());
            row.createCell(9).setCellValue(consEle.getUb());
            row.createCell(10).setCellValue(consEle.getUc());
            row.createCell(11).setCellValue(consEle.getIa());
            row.createCell(12).setCellValue(consEle.getIb());
            row.createCell(13).setCellValue(consEle.getIc());
            row.createCell(14).setCellValue(consEle.getI0());
            row.createCell(15).setCellValue(consEle.getP());
            row.createCell(16).setCellValue(consEle.getPa());
            row.createCell(17).setCellValue(consEle.getPb());
            row.createCell(18).setCellValue(consEle.getPc());
            row.createCell(19).setCellValue(consEle.getQ());
            row.createCell(20).setCellValue(consEle.getQa());
            row.createCell(21).setCellValue(consEle.getQb());
            row.createCell(22).setCellValue(consEle.getQc());
            row.createCell(23).setCellValue(consEle.getRemark());
            row.createCell(24).setCellValue(consEle.getRap());
            row.createCell(25).setCellValue(consEle.getRapR()+"");
            row.createCell(26).setCellValue(consEle.getRapRDiff()+"");
            row.createCell(27).setCellValue(consEle.getRapEle()+"");
        }
        return workbook;
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

    public static  SXSSFWorkbook  sendConsReport(List<TgConsReport> tgConsReports) {
        SXSSFWorkbook workbook = new SXSSFWorkbook ();
        SXSSFSheet sheet = workbook.createSheet("台区小时级线损");
        SXSSFRow row1 = sheet.createRow(0);
        String[] row1Cell = {"市公司名称","县公司名称","供电所编号","供电所名称","台区名称","台区编号",
                "设备ID","倍率","用户编号","用户名称","相关性系数","起始时间","终止时间","相关性高的时间段","用户电量","台区损失电量",
                "台区平均线损率","资产编号","细分标签","分类规则","目标线损率","目标线损率区间","用户电量数组","台区损失电量数组"};
        for (int i =0 ; i < row1Cell.length ; i++ ){
            row1.createCell(i).setCellValue(row1Cell[i]);
        }
        int size = tgConsReports.size();
        if (size>0&&(tgConsReports != null)){
            for (int i = 0;i<size;i++){
                SXSSFRow row = sheet.createRow(i+1);
                TgConsReport tgConsReport = tgConsReports.get(i);
                row.createCell(0).setCellValue(tgConsReport.getCityName());
                row.createCell(1).setCellValue(tgConsReport.getCountyName());
                row.createCell(2).setCellValue(tgConsReport.getOrgNo());
                row.createCell(3).setCellValue(tgConsReport.getOrgName());
                row.createCell(4).setCellValue(tgConsReport.getTgName());
                row.createCell(5).setCellValue(tgConsReport.getTgNo());
                row.createCell(6).setCellValue(tgConsReport.getRid());
                row.createCell(7).setCellValue(tgConsReport.gettFactor());
                row.createCell(8).setCellValue(tgConsReport.getConsNo());
                row.createCell(9).setCellValue(tgConsReport.getConsName());
                row.createCell(10).setCellValue(tgConsReport.getPearson());
                row.createCell(11).setCellValue(tgConsReport.getDateDayStart());
                row.createCell(12).setCellValue(tgConsReport.getDateDay());
                row.createCell(13).setCellValue(tgConsReport.getMaxIndex());
                row.createCell(14).setCellValue(tgConsReport.getEle());
                row.createCell(15).setCellValue(tgConsReport.getLossEle());
                row.createCell(16).setCellValue(tgConsReport.getLossPerAvg());
                row.createCell(17).setCellValue(tgConsReport.getAssetNo());
                row.createCell(18).setCellValue(tgConsReport.getTgClass());
                row.createCell(19).setCellValue(tgConsReport.getTgClassComment());
                row.createCell(20).setCellValue(tgConsReport.getMergeLinelossRate());
                row.createCell(21).setCellValue(tgConsReport.getLinelossRateInterval());
                row.createCell(22).setCellValue(tgConsReport.getEleArray());
                row.createCell(23).setCellValue(tgConsReport.getTgEleArray());
            }
        }
        return workbook;
    }

}
