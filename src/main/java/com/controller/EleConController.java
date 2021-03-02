package com.controller;

import com.entity.EleConWeibiao;
import com.entity.Electrics;
import com.grid.datacenter.service.HplcEleServiceImpl;
import com.service.EleConWeibiaoService;
import com.util.ExcelUtil;
import com.util.JsonUtil;
import com.util.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * (EleConTem)表控制层
 *
 * @author makejava
 * @since 2021-02-01 09:53:23
 */
@RestController
@RequestMapping("eleConTem")
public class EleConController {
    /**
     * 服务对象
     */


    @Resource
    private EleConWeibiaoService eleConWeibiaoService;

    private HplcEleServiceImpl hplcEleService = HplcEleServiceImpl.getInstance();

    private Logger logger = LoggerFactory.getLogger(EleConController.class);

    @RequestMapping("update")
    public void update(@RequestParam("file") MultipartFile file,String date ,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = file.getOriginalFilename();
        FileOutputStream out = null;
        String path = "d:\\files";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        path = path + File.separator + fileName;
        //将临时文件写入到真实文件中去
        try {
            out = new FileOutputStream(path);
            out.write(file.getBytes());
            //同时解析excle文件
            Map<String,String> consNos = ExcelUtil.readExcel(path);
            List<Electrics> electrics = new ArrayList<>();
            consNos.forEach((key,value)->{
                List<String> consList = Arrays.asList(value.split(","));
                List<String> idsList = eleConWeibiaoService.selectIdByConsNo(consList);
                String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
                String areaNo = eleConWeibiaoService.queryAreaName(key);
                String jsonString = hplcEleService.getElecData(null,ids,null,areaNo,date);
                Map<String,EleConWeibiao> map = eleConWeibiaoService.queryByRid(idsList);
                List<Electrics> list1 = JsonUtil.readJson(jsonString,map);
                electrics.addAll(list1);
            });
            OutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + new String("用户96点用电量.xls".getBytes(), "ISO-8859-1"));
            response.setContentType("application/x-excel;charset=UTF-8");
            HSSFWorkbook workbook = ExcelUtil.sendExcel(electrics);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            //解析之后将返回得结果扔给消息服务器
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @RequestMapping("queryByConsNo")
    public Result queryByConsNo(String consNos, String areaCode,String date){

        logger.info("Hello World!!!");
        List<String> list = Arrays.asList(consNos.split(","));
        List<String> idsList = eleConWeibiaoService.selectIdByConsNo(list);
        String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
        String string = hplcEleService.getElecData(null, ids, null, areaCode, date);
        Map<String,EleConWeibiao> map = eleConWeibiaoService.queryByRid(idsList);
        List<Electrics> list1 = JsonUtil.readJson(string,map);
        return Result.success(list1);

    }

    @RequestMapping("selectOne")
    public void selectOne(String id, String date) {

    }

}