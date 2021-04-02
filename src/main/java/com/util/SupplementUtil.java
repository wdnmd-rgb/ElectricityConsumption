package com.util;

import com.entity.EleConWeibiao;
import com.entity.Electrics;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class SupplementUtil {

    public static Map<String,Object> supplement(String url, HttpServletRequest request,Map<String, EleConWeibiao> map2,String name,int index){
        String day = url.substring(5,15);
        String path = request.getSession().getServletContext().getRealPath("/")+"file";
        int point = 96/index;
        double[] doubles = new double[point];
        double[] doubles2 = new double[point];
        try {
            Map<String,Object> map = ExcelUtil.readExcel2(request.getSession().getServletContext().getRealPath("/")+url);
            Map<String, Electrics> map1 = (Map<String, Electrics>) map.get("map");
            Set<String> set = (Set<String>) map.get("set");
            List ids = new ArrayList(set);
            Map<String,Object> map3 = ExcelUtil.sendExcel2(map1,set,day,map2,index);
            doubles = (double[]) map3.get("doubles");
            doubles2 = (double[]) map3.get("doubles2");
            SXSSFWorkbook workbook = (SXSSFWorkbook) map3.get("workbook");
            String fileName = day+name+"用户96点用电量（补点后）.xlsx";
            String realPath = path+"/"+fileName;
            File file=new File(realPath);
            System.out.println(file);
            FileOutputStream os=new FileOutputStream(file);
            workbook.write(os);
            path = "file/"+fileName;
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String,Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("path",path);
        stringObjectMap.put("doubles",doubles);
        stringObjectMap.put("doubles2",doubles2);
        return stringObjectMap;
    }
}
