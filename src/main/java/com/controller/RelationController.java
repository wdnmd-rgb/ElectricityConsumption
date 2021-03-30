package com.controller;

import com.entity.Relation;
import com.service.RelationService;
import com.util.JsonUtil;
import com.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Relation)表控制层
 *
 * @author makejava
 * @since 2021-03-22 16:30:14
 */
@RestController
@RequestMapping("relation")
public class RelationController {
    /**
     * 服务对象
     */
    @Resource
    private RelationService relationService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Relation selectOne(Integer id) {
        return this.relationService.queryById(id);
    }

    @RequestMapping("queryByTgNo")
    public void queryByTgNo(String tgNo, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> map = new HashMap<>();
        if ("".equals(tgNo)||tgNo == null){
            map.put("code","0");
            map.put("msg","");
            map.put("count","");
            map.put("data","");
            JsonUtil.responseWriteJson(response,map);
            return;
        }
        Relation relation = this.relationService.queryByTgNo(tgNo);
        List<Relation> list = new ArrayList<>();
        list.add(relation);
        map.put("code","0");
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list);
        JsonUtil.responseWriteJson(response,map);
    }

}