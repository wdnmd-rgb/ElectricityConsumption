package com.controller;

import com.entity.User;
import com.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class ViewController {

    @Resource
    private UserService userService;

    @RequestMapping("user/out")
    public String out()
    {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:../admin.jsp";
    }

    @RequestMapping("user/selectOne")
    public String selectOne(String username, HttpSession session){
        User user = this.userService.queryByUserName(username);
        session.setAttribute("user",user);
        return "redirect:../admin_user_update.jsp";
    }
}
