package com.bbgu.zmz.communityadmin.controller;

import com.bbgu.zmz.communityadmin.dto.AdPosition;
import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.enums.MsgEnum;
import com.bbgu.zmz.communityadmin.enums.Position;
import com.bbgu.zmz.communityadmin.model.Ad;
import com.bbgu.zmz.communityadmin.model.Kind;
import com.bbgu.zmz.communityadmin.model.Topicinfo;
import com.bbgu.zmz.communityadmin.model.User;
import com.bbgu.zmz.communityadmin.service.AdService;
import com.bbgu.zmz.communityadmin.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ad")
public class AdController {

    @Autowired
    private AdService adService;

    @GetMapping("/adList")
    public String kind(){
        return "/ad/ad";
    }

    @GetMapping("/test")
    @ResponseBody
    public String text(){
        return "are you ok !!!";
    }

    /*
    获取广告信息数据
     */
    @GetMapping("/getad")
    @ResponseBody
    public Result getAd(@RequestParam(value ="title",required = false) String title) {
        Result result = new Result();
        List<Ad> adList = adService.findAd(title);
        result.setData(adList);
        return  result;
    }
    /*
    删除广告
     */
    @GetMapping("/delad")
    @ResponseBody
    public Result delAd(Integer id){
        adService.delAd(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }
    /*
    打开广告表单
     */
    @GetMapping("/adform")
    public String listform(@RequestParam(value = "id",required = false) Integer id, Model model){
        Ad ad = adService.findAdById(id);
        List<AdPosition> list = new ArrayList();
        for(Position position:Position.values()){
            AdPosition adPosition = new AdPosition();
            adPosition.setPos(position.getPos());
            adPosition.setMessage(position.getMessage());
            list.add(adPosition);
        }
        model.addAttribute("ad",ad);
        model.addAttribute("position",list);
        return "/ad/adform";
    }
    /*
    添加广告信息
     */
    @PostMapping("/addad")
    @ResponseBody
    public Result addUser(Ad ad,String start,String end, @RequestParam(value = "zhuangtai",required = false)String zhuangtai) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date adstart =  simpleDateFormat.parse(start);
        Date adend =  simpleDateFormat.parse(end);
        ad.setAdCreate(System.currentTimeMillis());
        ad.setAdModified(ad.getAdCreate());
        ad.setAdStart(adstart.getTime());
        ad.setAdEnd(adend.getTime());
        ad.setStatus(zhuangtai!= null?1:0);
        int num = adService.addAd(ad);
        if(num>0){
            return new Result().ok(MsgEnum.ADD_SUCCESS);
        }else{
            return new Result().error(MsgEnum.ADD_FAILE);
        }
    }

    /*
    更新广告信息
     */
    @PostMapping("updatead")
    @ResponseBody
    public Result updateUser(Ad ad, String start,String end, String zhuangtai) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date adstart =  simpleDateFormat.parse(start);
        Date adend =  simpleDateFormat.parse(end);
        ad.setAdModified(System.currentTimeMillis());
        ad.setAdStart(adstart.getTime());
        ad.setAdEnd(adend.getTime());
        ad.setStatus(zhuangtai != null?1:0);
        adService.updateAd(ad);
        return new Result().ok(MsgEnum.UPDATE_SUCCESS);
    }

}
