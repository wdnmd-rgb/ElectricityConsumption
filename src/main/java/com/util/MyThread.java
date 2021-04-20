package com.util;

import com.entity.EleConWeibiao;
import com.entity.Electrics;
import com.grid.datacenter.service.HplcEleServiceImpl;
import org.apache.avro.data.Json;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyThread extends Thread{

    @Override
    public void run(){
        String name = Thread.currentThread().getName();
        name = name.substring(name.lastIndexOf("-")+1);
        List<String> list = ThreadUtil.getIdsTh();
        Map<String, EleConWeibiao> map = new HashMap<>();
        int sizeTol = list.size();
        int num = Integer.valueOf(name);
        int size =  sizeTol/ThreadUtil.poolNum;
        int start = (num-1)*size;
        int end = num*size;
        if (num == ThreadUtil.poolNum){
            end = sizeTol;
        }
        list = list.subList(start,end);
        System.out.println("name："+name+" size："+list.size());
        HplcEleServiceImpl hplcEleService = HplcEleServiceImpl.getInstance();
        String ids = StringUtils.join(Arrays.asList(list.toArray()), ",");
        String date = ThreadUtil.getDate();
        String areaCode = ThreadUtil.getAreaCode();
        String json = hplcEleService.getElecData(null,ids,null,areaCode,date);
        List<Electrics> list1 = JsonUtil.readJson(json,map);
        ThreadUtil.addResult(list1);
        ThreadUtil.addSuccessNum(name);
    }

}
