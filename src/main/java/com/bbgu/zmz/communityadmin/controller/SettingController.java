package com.bbgu.zmz.communityadmin.controller;


import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.model.User;
import com.bbgu.zmz.communityadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/set")
public class SettingController {

    @Autowired
    private UserService userService;

    @GetMapping("/modifiedpwd")
    public String modifiedpwd(){
        return "/set/password";
    }


    //修改密码
    @PostMapping("modifypass")
    @ResponseBody
    public Result modifyUserPassword(String nowpass, String pass, String repass, HttpServletRequest request){
        User userinfo = (User)request.getSession().getAttribute("user");
        return userService.modifyUserPassword(nowpass,pass,repass,userinfo.getAccountId());
    }

}
