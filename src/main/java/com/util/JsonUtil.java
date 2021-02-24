package com.util;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.Electric;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public static List<Electric> readJson(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Electric> list = new ArrayList<>();
        list = (List<Electric>)JSONArray.parseArray(jsonArray.toString(),Electric.class);
        return list;

    }

}
