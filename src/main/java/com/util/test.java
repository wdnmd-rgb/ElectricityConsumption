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

        float a = 1.53f;
        float b = 2.56f;
        float c = 3.22f;
        float d = a+b+c;
        System.out.println(d);
    }
}
