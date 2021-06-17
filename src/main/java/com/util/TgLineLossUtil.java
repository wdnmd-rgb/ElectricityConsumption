package com.util;

import com.entity.ConsEle;
import com.entity.TgLineLoss;
import com.entity.TgResult;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TgLineLossUtil {
    public static Map<String,Object>  doJob(List<ConsEle> consEles, String date, int index) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> stringObjectMap = new HashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        DecimalFormat decimalFormat2 = new DecimalFormat("#.0000");
        List<TgLineLoss> lineLosses = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Date date1 = simpleDateFormat1.parse(date);
        List<ConsEle> consEleList = new ArrayList<>();
        Map<String,ConsEle> stringConsEleMap = new HashMap<>();
        int remark0=0;
        int remark1=0;
        int remark2=0;
        int remark3=0;
        Long time = date1.getTime();
        int size = consEles.size();
        for (int i=0;i<size;i++) {
            ConsEle consEle = consEles.get(i);
            String rid = consEle.getRid();
            String date2 = consEle.getEventTime();
            Date date3 = simpleDateFormat.parse(date2);
            String key = rid+date3.getTime();
            stringConsEleMap.put(key,consEle);
            set.add(rid);
        }
        Double ppqTol = 0.0;
        Double upqTol = 0.0;
        for (int i = 1; i < (24/index); i++) {
            Long time2 = time + (i * 900000 * 4 * index);
            Long time3 = time + ((i-1) * 900000 * 4 * index);
            String date4 = simpleDateFormat.format(new Date(time2));
            Double ppq = 0.0;
            Double upq = 0.0;
            int sum = 0;
            for (String rid :set){
                String key = rid+time2;
                String key2 = rid+time3;
                ConsEle consEle = stringConsEleMap.get(key);
                ConsEle consEle2 = stringConsEleMap.get(key2);
                if (consEle == null){
                    continue;
                }
                String code = consEle.getTypeCode();
                String remark = consEle.getRemark();
                String rap = consEle.getRap();
                if ("0".equals(remark)){
                    remark0++;
                } else if ("1".equals(remark)){
                    remark1++;
                }else if ("2".equals(remark)){
                    remark2++;
                }else if ("3".equals(remark)){
                    remark3++;
                }
                if ("3".equals(remark)||"0".equals(remark)){
                    sum++;
                }
                Double rapEle = 0.0;
                Double papR = consEle.getPapR();
                Double t = consEle.gettFactor();
                Double papRP = consEle2.getPapR();
                Double ele = Double.valueOf(decimalFormat.format((papR-papRP)*t));
                consEle.setEle(ele);
                consEle.setPapRDiff(Double.valueOf(decimalFormat.format(papR-papRP)));
                if ("1".equals(rap)){
                    Double rapR = consEle.getRapR();
                    Double rapRP = consEle2.getRapR();
                    if (rapR==null||rapR==0||rapRP==null||rapRP==0){
                        rapEle=0.0;
                    }else {
                        rapEle = Double.valueOf(decimalFormat.format((rapR-rapRP)*t));
                    }
                    consEle.setRapEle(rapEle);
                    consEle.setRapRDiff(Double.valueOf(decimalFormat.format(rapR-rapRP)));
                    ppq+=rapEle;
                }
                if ("02".equals(code)){
                    ppq+=ele;
                }else{
                    upq+=ele;
                }
                consEleList.add(consEle);
            }
            Double lossPq = 0.0;
            Double lossPer = 0.0;
            Double lossPqTol = 0.0;
            Double lossPerTol = 0.0;
            if (ppq == 0.0){
                lossPq = ppq-upq;
                lossPer = 0.0;
            }else {
                lossPq = ppq-upq;
                lossPer = Double.valueOf(decimalFormat2.format(lossPq/ppq))*100;
            }
            ppqTol+=ppq;
            upqTol+=upq;
            if (ppqTol == 0.0){
                lossPqTol = ppqTol-upqTol;
                lossPerTol = 0.0;
            }else{
                lossPqTol = ppqTol-upqTol;
                lossPerTol = Double.valueOf(decimalFormat2.format(lossPqTol/ppqTol))*100;
            }
            TgLineLoss tgLineLoss = new TgLineLoss();
            tgLineLoss.setPpq(Double.valueOf(decimalFormat.format(ppq)));
            tgLineLoss.setUpq(Double.valueOf(decimalFormat.format(upq)));
            tgLineLoss.setLossPer(Double.valueOf(decimalFormat.format(lossPer)));
            tgLineLoss.setLossPq(Double.valueOf(decimalFormat.format(lossPq)));
            tgLineLoss.setPpqTol(Double.valueOf(decimalFormat.format(ppqTol)));
            tgLineLoss.setUpqTol(Double.valueOf(decimalFormat.format(upqTol)));
            tgLineLoss.setLossPerTol(Double.valueOf(decimalFormat.format(lossPerTol)));
            tgLineLoss.setLossPqTol(Double.valueOf(decimalFormat.format(lossPqTol)));
            tgLineLoss.setEventTime(date4);
            tgLineLoss.setRemark(sum);
            lineLosses.add(tgLineLoss);
        }
        TgResult tgResult = new TgResult();
        tgResult.setEventTime(date);
        tgResult.setRealCount(set.size());
        tgResult.setRemark0(remark0);
        tgResult.setRemark1(remark1);
        tgResult.setRemark2(remark2);
        tgResult.setRemark3(remark3);
        stringObjectMap.put("lineLosses",lineLosses);
        stringObjectMap.put("tgResult",tgResult);
        stringObjectMap.put("consEles",consEleList);
        return stringObjectMap;
    }

}
