package com.controller;

import com.entity.EleCon;
import com.service.EleConService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (EleCon)表控制层
 *
 * @author makejava
 * @since 2021-02-03 09:39:44
 */
@RestController
@RequestMapping("eleCon")
public class EleConController {
    /**
     * 服务对象
     */
    @Resource
    private EleConService eleConService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public EleCon selectOne(Integer id) {
        return this.eleConService.queryById(id);
    }

}