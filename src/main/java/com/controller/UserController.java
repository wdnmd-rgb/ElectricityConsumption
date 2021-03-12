package com.controller;

import com.entity.User;
import com.service.UserService;
import com.util.ExcelUtil;
import com.util.JsonUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-03-04 09:56:56
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(Integer id) {
        return this.userService.queryById(id);
    }

    @RequestMapping("login")
    public String login(String name,String password){
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return "1";
        }catch (AuthenticationException e){
            e.printStackTrace();
        }
        return "0";
    }

    @RequestMapping("selectpage")
    public void selectPage(@RequestParam(required=false, defaultValue="1") Integer page, Integer limit, HttpServletResponse response, User user) throws ServletException, IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("code",Integer.valueOf(0));
        map.put("count",Integer.valueOf(this.userService.selectNum(user)));
        map.put("data",this.userService.queryAll(page,limit,user));
        JsonUtil.responseWriteJson(response,map);
}

    @RequestMapping("delete")
    public String delete(Integer id){
        if(this.userService.deleteUserRole(id)){
            if(this.userService.deleteById(id)){
                return "1";
            }
        }
            return "0";
    }

}