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
        String str = "2021-03-29 08:45:00";

        System.out.println(str.substring(11,13));
        System.out.println(str.substring(14,16));
    }
}
