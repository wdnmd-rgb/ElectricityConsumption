package com.controller;

import com.entity.EleCon;
import com.entity.EleConTem;
import com.entity.EleConWeibiao;
import com.entity.Electric;
import com.grid.datacenter.service.HplcEleServiceImpl;
import com.service.EleConService;
import com.service.EleConTemService;
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

/**
 * (EleConTem)表控制层
 *
 * @author makejava
 * @since 2021-02-01 09:53:23
 */
@RestController
@RequestMapping("eleConTem")
public class EleConTemController {
    /**
     * 服务对象
     */
    @Resource
    private EleConTemService eleConTemService;

    @Resource
    private EleConWeibiaoService eleConWeibiaoService;

    @Resource
    private EleConService eleConService;

    @RequestMapping("update")
    public void update(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = file.getOriginalFilename();
        List<EleConTem> list = new ArrayList<>();
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
            List<String> consNos = ExcelUtil.readExcel(path);
            List<String> idsList = eleConWeibiaoService.selectIdByConsNo(consNos);
            List<EleCon> eleConList = eleConService.queryByRid(idsList);
            int flag = eleConService.insertResult(eleConList);
            List<EleConWeibiao> weibiaoList = new ArrayList<>();
            if (flag > 0) {
                weibiaoList = eleConWeibiaoService.queryAllResult();
                if (weibiaoList.size() > 0) {
                    eleConService.deleteResult();
                }
            }
            //list = eleConTemService.queryByConsNo(consNos);
            OutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + new String("用户96点用电量.xls".getBytes(), "ISO-8859-1"));
            response.setContentType("application/x-excel;charset=UTF-8");
            HSSFWorkbook workbook = ExcelUtil.sendExcel(weibiaoList);
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
    public Result queryByConsNo(String consNos, String areaCode,String date) {
        Logger logger = LoggerFactory.getLogger(EleConTemController.class);
        logger.info("SSM!!!!");
        System.out.println("Hello World!");
        List<String> list = Arrays.asList(consNos.split(","));
        List<String> idsList = eleConWeibiaoService.selectIdByConsNo(list);
        String ids = StringUtils.join(Arrays.asList(idsList.toArray()), ",");
        HplcEleServiceImpl hplcEleService = HplcEleServiceImpl.getInstance();
        String string = hplcEleService.getElecData(null, ids, null, areaCode, date);
        System.out.println(string);
//        List<Electric> list1 = JsonUtil.readJson(string);
//        List<EleCon> eleConList = eleConService.queryByRid(idsList);
//        int flag = eleConService.insertResult(eleConList);
//        List<EleConWeibiao> weibiaoList = new ArrayList<>();
//        if (flag > 0) {
//            weibiaoList = eleConWeibiaoService.queryAllResult();
//            if (weibiaoList.size() > 0) {
//                eleConService.deleteResult();
//            }
//        } else {
//            Result.fail("获取数据失败！");
//        }
        return Result.success(string);

    }

    @RequestMapping("selectOne")
    public void selectOne(String id, String date) {

    }

}