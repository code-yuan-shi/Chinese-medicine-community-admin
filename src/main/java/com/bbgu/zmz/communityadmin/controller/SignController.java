package com.bbgu.zmz.communityadmin.controller;

import com.bbgu.zmz.communityadmin.dto.AdPosition;
import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.enums.MsgEnum;
import com.bbgu.zmz.communityadmin.enums.Position;
import com.bbgu.zmz.communityadmin.model.Ad;
import com.bbgu.zmz.communityadmin.model.Qiandao;
import com.bbgu.zmz.communityadmin.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sign")
public class SignController {

    @Autowired
    private SignService signService;

    @GetMapping("/signList")
    public String kind(){
        return "/sign/sign";
    }

    /*
    获取签到数据
     */
    @GetMapping("/getsign")
    @ResponseBody
    public Result getSign( @RequestParam(value ="userId",required = false) Long userId){
        Result result = new Result();
        List<Qiandao> SignList = signService.findSign(userId);
        result.setData(SignList);
        return  result;
    }


    /*
    删除签到记录
     */
    @GetMapping("/delsign")
    @ResponseBody
    public Result delSign(Long id){
        signService.delSign(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }
    /*
    打开签到表单
     */
    @GetMapping("/signform")
    public String listform(@RequestParam(value = "id",required = false) Long id, Model model){
        Qiandao qiandao = signService.findSignById(id);
        model.addAttribute("qd",qiandao);
        return "/sign/signform";
    }
    /*
    添加签到信息
     */
    @PostMapping("/addsign")
    @ResponseBody
    public Result addSign(Qiandao qiandao,String signtime) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date qdtime =  simpleDateFormat.parse(signtime);
        qiandao.setQiandaoCreate(qdtime.getTime());
        int num = signService.addSign(qiandao);
        if(num>0){
            return new Result().ok(MsgEnum.ADD_SUCCESS);
        }else{
            return new Result().error(MsgEnum.ADD_FAILE);
        }
    }

    /*
    更新签到信息
     */
    @PostMapping("/updatesign")
    @ResponseBody
    public Result updateSign(Qiandao qiandao,String signtime) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date signTime =  simpleDateFormat.parse(signtime);
        qiandao.setQiandaoCreate(signTime.getTime());
        signService.updateSign(qiandao);
        return new Result().ok(MsgEnum.UPDATE_SUCCESS);
    }

}
