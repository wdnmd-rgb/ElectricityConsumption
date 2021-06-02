package com.util;

import com.entity.TgLineLoss;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TgLineLossUtil {
    public static List<TgLineLoss> doJob(List<TgLineLoss> lineLosses, String date, int index) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        List<TgLineLoss> lineLosses1 = new ArrayList<>();
        Date date1 = simpleDateFormat1.parse(date);
        Map<Long, TgLineLoss> map = new HashMap<>();
        Long time = date1.getTime();
        for (TgLineLoss tgLineLoss : lineLosses) {
            String date2 = tgLineLoss.getEventTime();
            Date date3 = simpleDateFormat.parse(date2);
            map.put(date3.getTime(), tgLineLoss);
        }
        Double ppqSum = 0.0;
        Double upqSum = 0.0;
        int remarkSum = 0;
        for (int i = 1; i < 24; i++) {
            Long time2 = time + (i * 900000 * 4);
            TgLineLoss tgLineLoss = map.get(time2);
            Double ppq = tgLineLoss.getPpq();
            Double upq = tgLineLoss.getUpq();
            String remark = tgLineLoss.getRemark();
            remarkSum += Integer.parseInt(remark);
            Double lossPer = 0.0;
            ppqSum += ppq;
            upqSum += upq;
            if ((i % index) == 0) {
                if (ppq != 0.0) {
                    lossPer = (ppqSum - upqSum) / ppq;
                }
                tgLineLoss.setPpq(Double.valueOf(decimalFormat.format(ppqSum)));
                tgLineLoss.setUpq(Double.valueOf(decimalFormat.format(upqSum)));
                tgLineLoss.setLossPq(Double.valueOf(decimalFormat.format(ppqSum - upqSum)));
                tgLineLoss.setLossPer(Double.valueOf(decimalFormat.format(lossPer)));
                tgLineLoss.setRemark(remarkSum + "");
                remarkSum = 0;
                ppqSum = 0.0;
                upqSum = 0.0;
                lineLosses1.add(tgLineLoss);
            }

        }
        return lineLosses1;
    }

}
