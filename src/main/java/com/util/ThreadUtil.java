package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static List<String> idsTh = new ArrayList<>();

    private static List<String> resultList = new ArrayList<>();

    private static List<String> successNum = new ArrayList<>();

    private static String areaCode ="";

    private static String date ="";

    public static int poolNum = 15;

    static ExecutorService pool = Executors.newFixedThreadPool(poolNum);

    public static List<String> getIdsTh() {
        return idsTh;
    }

    public static void setIdsTh(List<String> idsTh) {
        ThreadUtil.idsTh = idsTh;
    }

    public static List<String> getResultList() {
        return resultList;
    }

    public static List<String> getSuccessNum() {
        return successNum;
    }

    public static void setSuccessNum(List<String> successNum) {
        ThreadUtil.successNum = successNum;
    }
    public static void addSuccessNum(String num) {
        ThreadUtil.successNum.add(num);
    }

    public static void setResultList(List<String> resultList) {
        ThreadUtil.resultList = resultList;
    }
    public static void addResultList(String string) {
        ThreadUtil.resultList.add(string);
    }

    public static String getAreaCode() {
        return areaCode;
    }

    public static void setAreaCode(String areaCode) {
        ThreadUtil.areaCode = areaCode;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        ThreadUtil.date = date;
    }

    public static void doJob(List<String> ids, String areaCode, String date){
        setIdsTh(ids);
        setAreaCode(areaCode);
        setDate(date);
        System.out.println(areaCode+date);
        for (int i = 0;i<poolNum;i++){
            Thread thread = new MyThread();
            System.out.println("线程"+i);
            pool.execute(thread);
        }
        while (true){
            int size = successNum.size();
            if (size == poolNum){
                break;
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    }
}
