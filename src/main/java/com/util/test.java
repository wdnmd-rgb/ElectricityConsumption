package com.util;

import com.entity.Electrics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {
    private static List<String> list = new ArrayList<>();
    public static int poolNum = 10;
    static ExecutorService pool = Executors.newFixedThreadPool(poolNum);
    public static List<String> getList(){
        return list;
    }
    public static void doJob(){
        for (int i = 0;i<10;i++){
            Thread thread = new MyThread();
            pool.execute(thread);
        }
        pool.shutdown();
    }
    public static void main(String[] args) throws ParseException {
        String date = "20210402";
        StringBuffer stringBuffe = new StringBuffer(date);
        stringBuffe.insert(4,"-");
        stringBuffe.insert(7,"-");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = df.parse(stringBuffe.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date2 = calendar.getTime();
        System.out.println(date1.toString());
        System.out.println(df.format(date2));
        System.out.println(stringBuffe);
    }
}
