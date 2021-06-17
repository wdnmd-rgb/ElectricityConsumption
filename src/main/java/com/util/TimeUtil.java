package com.util;

import com.entity.Electrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeUtil {
    public static Map<String,Object> makeMap(List<Electrics> list){
        Map<String,Electrics> map = new HashMap<>();
        Map<String,Object> stringObjectMap = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Set<String> ids = new HashSet<>();
        int size = list.size();
        for (int i=0;i<size;i++){
            Electrics electrics = list.get(i);
            String id = electrics.getRid();
            String date = electrics.getEventTime();
            Date date2 = null;
            try {
                date2 = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ids.add(id);
            String key = id+date2.getTime();
            map.put(key,electrics);
        }
        stringObjectMap.put("map",map);
        stringObjectMap.put("set",ids);
        return stringObjectMap;
    }
    public static Map<String,Object> sort(String date, List<Electrics> list) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = simpleDateFormat1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long time = date1.getTime();
        Map<String,Object> stringObjectMap = makeMap(list);
        Map<String,Electrics> map = (Map<String, Electrics>) stringObjectMap.get("map");
        Set<String> ids = (Set<String>) stringObjectMap.get("set");
        String [] times = new String[list.size()];
        String [] uas = new String[list.size()];
        String [] ubs = new String[list.size()];
        String [] ucs = new String[list.size()];
        String [] ias = new String[list.size()];
        String [] ibs = new String[list.size()];
        String [] ics = new String[list.size()];
        String [] i0s = new String[list.size()];
        String [] pas = new String[list.size()];
        String [] pbs = new String[list.size()];
        String [] pcs = new String[list.size()];
        String [] ps = new String[list.size()];
        for(String rid:ids){
            for (int i =0,j=0;i<96;i++){
                Long time2 = time+(i*900000);
                String key = rid+time2;
                Electrics electrics = map.get(key);
                if (electrics != null){
                    times[j]=electrics.getEventTime();
                    uas[j]=electrics.getUa();
                    ubs[j]=electrics.getUb();
                    ucs[j]=electrics.getUc();
                    ias[j]=electrics.getIa();
                    ibs[j]=electrics.getIb();
                    ics[j]=electrics.getIc();
                    i0s[j]=electrics.getI0();
                    pas[j]=electrics.getPa();
                    pbs[j]=electrics.getPb();
                    pcs[j]=electrics.getPc();
                    ps[j]=electrics.getP();
                    j++;
                }

            }
        }
        stringObjectMap.clear();
        stringObjectMap.put("times",times);
        stringObjectMap.put("uas",uas);
        stringObjectMap.put("ubs",ubs);
        stringObjectMap.put("ucs",ucs);
        stringObjectMap.put("ias",ias);
        stringObjectMap.put("ibs",ibs);
        stringObjectMap.put("ics",ics);
        stringObjectMap.put("i0s",i0s);
        stringObjectMap.put("pas",pas);
        stringObjectMap.put("pbs",pbs);
        stringObjectMap.put("pcs",pcs);
        stringObjectMap.put("ps",ps);
        return stringObjectMap;
    }


}
