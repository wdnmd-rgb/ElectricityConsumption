package com.controller;

import com.entity.EleConWeibiao;
import com.service.EleConWeibiaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (EleConWeibiao)表控制层
 *
 * @author makejava
 * @since 2021-02-03 09:22:34
 */
@RestController
@RequestMapping("eleConWeibiao")
public class EleConWeibiaoController {
    /**
     * 服务对象
     */
    @Resource
    private EleConWeibiaoService eleConWeibiaoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public EleConWeibiao selectOne(Integer id) {
        return this.eleConWeibiaoService.queryById(id);
    }

}