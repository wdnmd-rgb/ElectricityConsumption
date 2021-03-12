package com.controller;

import com.entity.Role;
import com.service.RoleService;
import com.util.JsonUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * (Role)表控制层
 *
 * @author makejava
 * @since 2021-03-12 16:02:07
 */
@RestController
@RequestMapping("role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Role selectOne(Integer id) {
        return this.roleService.queryById(id);
    }

    @RequestMapping("selectpage")
    public void selectpage(@RequestParam(required=false, defaultValue="1") Integer page, Integer limit, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap();
        map.put("code", "0");
        map.put("msg","");
        map.put("count", Integer.valueOf(this.roleService.selectNum(new Role())));
        map.put("data", this.roleService.queryAll(page, limit, new Role()));
        JsonUtil.responseWriteJson(response, map);
    }



}