package com.util;



import com.alibaba.fastjson.*;
import com.entity.EleConWeibiao;
import com.entity.Electrics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grid.datacenter.model.Electric;
import com.grid.datacenter.model.Result;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {
//
//    public static void main(String[] args) {
//        String  json = "\"{\\\"data\\\":[{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.2,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 00:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 00:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.2,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 00\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.2,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 01:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 01:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.21,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 01\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.21,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 02:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 02:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.22,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 02\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.22,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 03:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 03:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.22,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 03\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.23,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 04:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 04:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.23,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 04\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.23,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 05:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 05:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.24,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 05\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 06:00:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.24,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 06\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 07:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.25,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 07\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.26,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 08:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 08:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.26,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 08\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.27,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 09:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 09:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.28,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 09\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.3,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 10:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 10:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.31,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 10\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.32,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 11:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 11:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.33,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 11\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.34,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 12:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 12:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.36,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 12\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.37,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 13:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 13:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.38,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 13\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.39,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 14:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 14:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.4,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 14\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.41,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 15:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 15:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.42,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 15\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.43,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 16:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 16:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.44,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 16\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 17:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.47,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 17\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.48,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 18:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 18:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.5,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 18\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 20:00:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.54,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 20\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 21:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.59,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 21\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.59,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 22:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 22:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.6,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 22\\\",\\\"id\\\":\\\"171117414\\\"},{\\\"areaCod\\\":\\\"01\\\",\\\"elec\\\":\\\"[{\\\\\\\"pap_r\\\\\\\":1516.61,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 23:00:00\\\\\\\",\\\\\\\"point\\\\\\\":\\\\\\\"00\\\\\\\"},{\\\\\\\"event_time\\\\\\\":\\\\\\\"2021-02-25 23:30:00\\\\\\\",\\\\\\\"pap_diff\\\\\\\":-0.99,\\\\\\\"pap_r\\\\\\\":1516.61,\\\\\\\"pap_r_pre\\\\\\\":0.0,\\\\\\\"point\\\\\\\":\\\\\\\"30\\\\\\\"}]\\\",\\\"eventTime\\\":\\\"2021-02-25 23\\\",\\\"id\\\":\\\"171117414\\\"}],\\\"message\\\":\\\"\\\",\\\"success\\\":true}\"";
//        List<Electrics> list = readJson(json);
//        for (Electrics e:list){
//            System.out.println(e);
//        }
//
//    }

    public static List<Electrics> readJson(String json,Map<String,EleConWeibiao> map){
        List<Electric> list= parseElectric(json);
        List<Electrics> electricsList = new ArrayList<>();
        if (list.isEmpty()){
            return electricsList;
        }
        int electricSize = list.size();
        for (int i = 0;i<electricSize;i++){
            Electric electric = list.get(i);
            EleConWeibiao eleConWeibiao = map.get(electric.getId());
            //System.out.println(e.getElec());
            List list1 = (List)JSONPath.read(electric.getElec(), "$.");
            int eleSize = list1.size();
            for(int j =0;j<eleSize;j++){
                if (eleConWeibiao == null){
                    System.out.println("没有找到ID");
                    break;
                }
                Object obj = list1.get(j);
                Electrics electrics = new Electrics();
                electrics.setRid(electric.getId());
                String time  = JSONPath.read(obj.toString(), "$.event_time").toString();
                String point =JSONPath.read(obj.toString(), "$.point").toString();
                time = time.replaceAll(":[0-9][0-9]",":00");
                time = time.replaceAll(":[0-9][0-9]:",":"+point+":");
                electrics.setEventTime(time);
                electrics.setPapR(Double.parseDouble(JSONPath.read(obj.toString(), "$.pap_r").toString()));
                Object ua =  JSONPath.read(obj.toString(), "$.ua");
                Object ub =  JSONPath.read(obj.toString(), "$.ub");
                Object uc =  JSONPath.read(obj.toString(), "$.uc");
                Object ia =  JSONPath.read(obj.toString(), "$.ia");
                Object ib =  JSONPath.read(obj.toString(), "$.ib");
                Object ic =  JSONPath.read(obj.toString(), "$.ic");
                Object i0 =  JSONPath.read(obj.toString(), "$.i0");
                Object pa =  JSONPath.read(obj.toString(), "$.pa");
                Object pb =  JSONPath.read(obj.toString(), "$.pb");
                Object pc =  JSONPath.read(obj.toString(), "$.pc");
                Object qa =  JSONPath.read(obj.toString(), "$.qa");
                Object qb =  JSONPath.read(obj.toString(), "$.qb");
                Object qc =  JSONPath.read(obj.toString(), "$.qc");
                Object p =   JSONPath.read(obj.toString(), "$.p");
                Object q =   JSONPath.read(obj.toString(), "$.q");
                if ("".equals(ua)||ua == null){
                    electrics.setUa("暂无数据");
                }else{
                    electrics.setUa(ua.toString());
                }
                if ("".equals(ub)||ub == null){
                    electrics.setUb("暂无数据");
                }else{
                    electrics.setUb(ub.toString());
                }
                if ("".equals(uc)||uc == null){
                    electrics.setUc("暂无数据");
                }else{
                    electrics.setUc(uc.toString());
                }
                if ("".equals(ia)||ia == null){
                    electrics.setIa("暂无数据");
                }else{
                    electrics.setIa(ia.toString());
                }
                if ("".equals(ib)||ib == null){
                    electrics.setIb("暂无数据");
                }else{
                    electrics.setIb(ib.toString());
                }
                if ("".equals(ic)||ic == null){
                    electrics.setIc("暂无数据");
                }else{
                    electrics.setIc(ic.toString());
                }
                if ("".equals(i0)||i0 == null){
                    electrics.setI0("暂无数据");
                }else{
                    electrics.setI0(i0.toString());
                }
                if ("".equals(pa)||pa == null){
                    electrics.setPa("暂无数据");
                }else{
                    electrics.setPa(pa.toString());
                }
                if ("".equals(pb)||pb == null){
                    electrics.setPb("暂无数据");
                }else{
                    electrics.setPb(pb.toString());
                }
                if ("".equals(pc)||pc == null){
                    electrics.setPc("暂无数据");
                }else{
                    electrics.setPc(pc.toString());
                }
                if ("".equals(p)||p == null){
                    electrics.setP("暂无数据");
                }else{
                    electrics.setP(p.toString());
                }
                if ("".equals(qa)||qa == null){
                    electrics.setQa("暂无数据");
                }else{
                    electrics.setQa(qa.toString());
                }
                if ("".equals(qb)||qb == null){
                    electrics.setQb("暂无数据");
                }else{
                    electrics.setQb(qb.toString());
                }
                if ("".equals(qc)||qc == null){
                    electrics.setQc("暂无数据");
                }else{
                    electrics.setQc(qc.toString());
                }
                if ("".equals(q)||q == null){
                    electrics.setQ("暂无数据");
                }else{
                    electrics.setQ(q.toString());
                }
                electrics.setConsNo(eleConWeibiao.getConsNo());
                electrics.setConsName(eleConWeibiao.getConsName());
                electrics.setAreaName(eleConWeibiao.getAreaName());
                electrics.settFactor(eleConWeibiao.gettFactor());
                Double papRDiff = Double.parseDouble(JSONPath.read(obj.toString(), "$.pap_diff").toString());
                if (papRDiff<0){
                    electrics.setPapRDiff("暂无数据");
                    electrics.setEle("暂无数据");
                }else{
                    electrics.setPapRDiff(papRDiff.toString());
                    electrics.setEle((Double.parseDouble(electrics.getPapRDiff())*electrics.gettFactor())+"");
                }
                electrics.setTgNo(eleConWeibiao.getTgNo());
                electrics.setTgName(eleConWeibiao.getTgName());
                electrics.setOrgNo(eleConWeibiao.getOrgNo());
                electrics.setOrgName(eleConWeibiao.getOrgName());
                electrics.setAssetNo(eleConWeibiao.getAssetNo());
                electrics.setTmnlAssetNo(eleConWeibiao.getTmnlAssetNo());
                electrics.setMpSn(eleConWeibiao.getMpSn());
                electrics.setCisTmnlAssetNo(eleConWeibiao.getCisTmnlAssetNo());
                electrics.setCt(eleConWeibiao.getCt());
                electrics.setPt(eleConWeibiao.getPt());
                electrics.setTypeCode(eleConWeibiao.getTypeCode());
                electricsList.add(electrics);
            }
        }
        return electricsList;
    }

    public static List<Electrics> readJson2(String json,Map<String,EleConWeibiao> map){
        List<Electric> list= parseElectric(json);
        List<Electrics> electricsList = new ArrayList<>();
        if (list.isEmpty()){
            return electricsList;
        }
        int electricSize = list.size();
        for (int i = 0;i<electricSize;i++){
            Electric electric = list.get(i);
            EleConWeibiao eleConWeibiao = map.get(electric.getId());
            //System.out.println(e.getElec());
            List list1 = (List)JSONPath.read(electric.getElec(), "$.");
            int eleSize = list1.size();
            for(int j =0;j<eleSize;j++){
                if (eleConWeibiao == null){
                    System.out.println("没有找到ID");
                    break;
                }
                Object obj = list1.get(j);
                Electrics electrics = new Electrics();
                electrics.setRid(electric.getId());
                String time  = JSONPath.read(obj.toString(), "$.event_time").toString();
                String point =JSONPath.read(obj.toString(), "$.point").toString();
                if (!point.equals("00")){
                    continue;
                }
                time = time.replaceAll(":[0-9][0-9]",":00");
                time = time.replaceAll(":[0-9][0-9]:",":"+point+":");
                electrics.setEventTime(time);
                electrics.setPapR(Double.parseDouble(JSONPath.read(obj.toString(), "$.pap_r").toString()));
                Object ua =  JSONPath.read(obj.toString(), "$.ua");
                Object ub =  JSONPath.read(obj.toString(), "$.ub");
                Object uc =  JSONPath.read(obj.toString(), "$.uc");
                Object ia =  JSONPath.read(obj.toString(), "$.ia");
                Object ib =  JSONPath.read(obj.toString(), "$.ib");
                Object ic =  JSONPath.read(obj.toString(), "$.ic");
                Object i0 =  JSONPath.read(obj.toString(), "$.i0");
                Object pa =  JSONPath.read(obj.toString(), "$.pa");
                Object pb =  JSONPath.read(obj.toString(), "$.pb");
                Object pc =  JSONPath.read(obj.toString(), "$.pc");
                Object qa =  JSONPath.read(obj.toString(), "$.qa");
                Object qb =  JSONPath.read(obj.toString(), "$.qb");
                Object qc =  JSONPath.read(obj.toString(), "$.qc");
                Object p =   JSONPath.read(obj.toString(), "$.p");
                Object q =   JSONPath.read(obj.toString(), "$.q");
                if ("".equals(ua)||ua == null){
                    electrics.setUa("暂无数据");
                }else{
                    electrics.setUa(ua.toString());
                }
                if ("".equals(ub)||ub == null){
                    electrics.setUb("暂无数据");
                }else{
                    electrics.setUb(ub.toString());
                }
                if ("".equals(uc)||uc == null){
                    electrics.setUc("暂无数据");
                }else{
                    electrics.setUc(uc.toString());
                }
                if ("".equals(ia)||ia == null){
                    electrics.setIa("暂无数据");
                }else{
                    electrics.setIa(ia.toString());
                }
                if ("".equals(ib)||ib == null){
                    electrics.setIb("暂无数据");
                }else{
                    electrics.setIb(ib.toString());
                }
                if ("".equals(ic)||ic == null){
                    electrics.setIc("暂无数据");
                }else{
                    electrics.setIc(ic.toString());
                }
                if ("".equals(i0)||i0 == null){
                    electrics.setI0("暂无数据");
                }else{
                    electrics.setI0(i0.toString());
                }
                if ("".equals(pa)||pa == null){
                    electrics.setPa("暂无数据");
                }else{
                    electrics.setPa(pa.toString());
                }
                if ("".equals(pb)||pb == null){
                    electrics.setPb("暂无数据");
                }else{
                    electrics.setPb(pb.toString());
                }
                if ("".equals(pc)||pc == null){
                    electrics.setPc("暂无数据");
                }else{
                    electrics.setPc(pc.toString());
                }
                if ("".equals(p)||p == null){
                    electrics.setP("暂无数据");
                }else{
                    electrics.setP(p.toString());
                }
                if ("".equals(qa)||qa == null){
                    electrics.setQa("暂无数据");
                }else{
                    electrics.setQa(qa.toString());
                }
                if ("".equals(qb)||qb == null){
                    electrics.setQb("暂无数据");
                }else{
                    electrics.setQb(qb.toString());
                }
                if ("".equals(qc)||qc == null){
                    electrics.setQc("暂无数据");
                }else{
                    electrics.setQc(qc.toString());
                }
                if ("".equals(q)||q == null){
                    electrics.setQ("暂无数据");
                }else{
                    electrics.setQ(q.toString());
                }
                electrics.setConsNo(eleConWeibiao.getConsNo());
                electrics.setConsName(eleConWeibiao.getConsName());
                electrics.setAreaName(eleConWeibiao.getAreaName());
                electrics.settFactor(eleConWeibiao.gettFactor());
                Double papRDiff = Double.parseDouble(JSONPath.read(obj.toString(), "$.pap_diff").toString());
                if (papRDiff<0){
                    electrics.setPapRDiff("暂无数据");
                    electrics.setEle("暂无数据");
                }else{
                    electrics.setPapRDiff(papRDiff.toString());
                    electrics.setEle((Double.parseDouble(electrics.getPapRDiff())*electrics.gettFactor())+"");
                }
                electrics.setTgNo(eleConWeibiao.getTgNo());
                electrics.setTgName(eleConWeibiao.getTgName());
                electrics.setOrgNo(eleConWeibiao.getOrgNo());
                electrics.setOrgName(eleConWeibiao.getOrgName());
                electrics.setAssetNo(eleConWeibiao.getAssetNo());
                electrics.setTmnlAssetNo(eleConWeibiao.getTmnlAssetNo());
                electrics.setMpSn(eleConWeibiao.getMpSn());
                electrics.setCisTmnlAssetNo(eleConWeibiao.getCisTmnlAssetNo());
                electrics.setCt(eleConWeibiao.getCt());
                electrics.setPt(eleConWeibiao.getPt());
                electrics.setTypeCode(eleConWeibiao.getTypeCode());
                electricsList.add(electrics);
            }
        }
        return electricsList;
    }

    public static List<Electric> parseElectric(String json){

        String replaceAll = json.replaceAll("\\\\\"", "\"");

        String replaceAll2 = replaceAll.replaceAll("\\\\\\\\","\\\\");

        String substring = replaceAll2.substring(1, replaceAll2.length() - 1);

        List<Electric> list = null ;
        if(StringUtils.isNotBlank(substring)){
           Result<List<Electric>> r = JSON.parseObject(substring, new TypeReference<Result<List<Electric>>>() {});
           if(r.isSuccess()){
               list = r.getData();
           }
        }
        return list;
    }

    public static String ObjectToJson(Object object)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        String json = gson.toJson(object);
        return json;
    }

    public static void responseWriteJson(HttpServletResponse response, Object object)
            throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(ObjectToJson(object));
    }

}
