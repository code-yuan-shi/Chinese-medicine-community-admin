package com.bbgu.zmz.communityadmin.controller;


import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.enums.MsgEnum;
import com.bbgu.zmz.communityadmin.model.User;
import com.bbgu.zmz.communityadmin.model.UserExt;
import com.bbgu.zmz.communityadmin.service.UserService;
import com.bbgu.zmz.communityadmin.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String userList(){
        return "/user/user/list";

    }
    @GetMapping("/getuserlist")
    @ResponseBody
    public Result getUserList(Integer page,
                              Integer limit,
                              @RequestParam(value = "accountId",required = false) Long accountId,
                              @RequestParam(value = "name",required = false) String name,
                              @RequestParam(value = "email",required = false) String email,
                              @RequestParam(value = "sex",required = false) Integer sex,
                              @RequestParam(value = "role",required = false) String role,
                              @RequestParam(value = "status",required = false) Integer status){
        Result result = new Result();
        PageHelper.startPage(page,limit);
        List<User> userList =  userService.findAllUser(accountId,name,email,sex,role,status);
        PageInfo pageInfo = new PageInfo(userList);
        result.setCount(pageInfo.getTotal());
        result.setData(userList);
        return result;
    }
    @GetMapping("/userform")
    public String userform(@RequestParam(value = "id",required = false) Long id,Model model){
        UserExt user = userService.findUserByAccountId(id);
        model.addAttribute("userinfo",user);
        return "/user/user/userform";
    }

    @PostMapping("/adduser")
    @ResponseBody
    public Result addUser(User user, @RequestParam(value = "zhuangtai",required = false)String zhuangtai){
        user.setBio("该用户很懒，什么都没有留下！");
        user.setUserCreate(System.currentTimeMillis());
        user.setUserModified(user.getUserCreate());
        user.setStatus(zhuangtai!= null?1:0);
        user.setPwd(MD5Utils.getMd5(user.getPwd()));
        return userService.insertUser(user);
    }

    /*
    多选删除用户
     */
    @GetMapping("/delusers")
    @ResponseBody
    public Result delUsers(String id){
        userService.delUsers(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }

    @PostMapping("updateuser")
    @ResponseBody
    public Result updateUser(User user,String zhuangtai){
        user.setUserModified(System.currentTimeMillis());
        user.setStatus(zhuangtai != null?1:0);
        userService.updateUser(user);
        return new Result().ok(MsgEnum.UPDATE_SUCCESS);
    }

    @GetMapping("deluser")
    @ResponseBody
    public Result delUser(Long id){
        userService.delUser(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }
}
