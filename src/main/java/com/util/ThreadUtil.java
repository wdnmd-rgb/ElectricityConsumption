package com.util;

import com.entity.EleConWeibiao;
import com.entity.Electrics;
import sun.security.krb5.internal.crypto.HmacSha1Aes128CksumType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static List<String> idsTh = new ArrayList<>();

    private static List<String> successNum = new ArrayList<>();

    private static Map<String, EleConWeibiao> eleConWeibiaoMap = new HashMap<>();

    private static List<List<Electrics>> result = new ArrayList<>();

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

    public static List<String> getSuccessNum() {
        return successNum;
    }

    public static void setSuccessNum(List<String> successNum) {
        ThreadUtil.successNum = successNum;
    }
    public static void addSuccessNum(String num) {
        ThreadUtil.successNum.add(num);
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

    public static Map<String, EleConWeibiao> getEleConWeibiaoMap() {
        return eleConWeibiaoMap;
    }

    public static void setEleConWeibiaoMap(Map<String, EleConWeibiao> eleConWeibiaoMap) {
        ThreadUtil.eleConWeibiaoMap = eleConWeibiaoMap;
    }

    public static void setResult(List<List<Electrics>> lists) {
        ThreadUtil.result=lists;
    }

    public static List<List<Electrics>> getResult() {
        return result;
    }

    public static void addResult(List<Electrics> list) {
        ThreadUtil.result.add(list);
    }

    public static void doJob(List<String> ids, String areaCode, String date, Map<String, EleConWeibiao> eleConWeibiaoMap){
        setIdsTh(ids);
        setAreaCode(areaCode);
        setDate(date);
        setEleConWeibiaoMap(eleConWeibiaoMap);
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
