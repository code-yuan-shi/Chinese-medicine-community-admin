package com.bbgu.zmz.communityadmin.controller;


import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.enums.MsgEnum;
import com.bbgu.zmz.communityadmin.mapper.UserMapper;
import com.bbgu.zmz.communityadmin.model.User;
import com.bbgu.zmz.communityadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String login(HttpServletRequest request){
        return "/login";

    }

    @PostMapping("/dologin")
    @ResponseBody
    public Result dologin(User user,String check,HttpServletRequest request){
        String savecheck = (String)request.getSession().getAttribute("check");
        if(check.toLowerCase().equals(savecheck)){
            User user1 = userService.loginCheck(user);
            if(user1 != null){
                request.getSession().setAttribute("user",user1);
                Map map = new HashMap();
                map.put("action","/index");
                return new Result().ok(MsgEnum.LOGIN_SUCCESS,map);
            }else{
                return new Result().error(MsgEnum.USER_PWD_INCORRECT);
            }
        }else{
            return new Result().error(MsgEnum.CODE_INCORRECT);
        }
    }

    /*
    退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/index")
    public String index(){
        return "/index";

    }
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request, Model model){
        User user = (User)request.getSession().getAttribute("user");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("usertime", sdf.format(new Date(user.getUserModified())));
        return "/welcome";
    }
}
