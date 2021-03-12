package com.util;

import com.entity.Electrics;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static List<Electrics> page(List<Electrics> datalist, Integer page, Integer size) {
        List<Electrics> list = new ArrayList<>();
        if (datalist != null && datalist.size() > 0) {
            int cuurIdx = (page > 1 ? (page - 1) * size : 0);
            for (int i = 0; i < size && i < datalist.size() - cuurIdx; i++) {
                Electrics electrics = datalist.get(cuurIdx + i);
                list.add(electrics);
            }
        }
        return list;
    }
}
