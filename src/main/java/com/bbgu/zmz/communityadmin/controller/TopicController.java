package com.bbgu.zmz.communityadmin.controller;


import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.enums.MsgEnum;
import com.bbgu.zmz.communityadmin.model.Category;
import com.bbgu.zmz.communityadmin.model.Comment;
import com.bbgu.zmz.communityadmin.model.Kind;
import com.bbgu.zmz.communityadmin.model.Topicinfo;
import com.bbgu.zmz.communityadmin.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /*
    跳转帖子管理页面
     */
    @GetMapping("/list")
    public String list(HttpServletRequest request){
        List<Category> categoryList = topicService.findCategory();
        List<Kind> kindList =  topicService.findKind();
        request.getServletContext().setAttribute("kinds",kindList);
        request.getServletContext().setAttribute("categorys",categoryList);
        return "/topic/list";

    }
    /*
    获取帖子列表数据
     */
    @GetMapping("/getlist")
    @ResponseBody
    public Result getlist(@RequestParam("page") Integer page,
                          @RequestParam("limit") Integer limit,
                          @RequestParam(value = "title",required = false) String title,
                          @RequestParam(value ="userId",required = false) Long userId,
                          @RequestParam(value = "categoryId",required = false) Long categoryId,
                          @RequestParam(value = "kindId",required = false) Long kindId){
        return topicService.findAllTopic(page,limit,title,userId,categoryId,kindId);
    }
    /*
    多选删除帖子
     */
    @GetMapping("/dellist")
    @ResponseBody
    public Result delList(String id){
        topicService.delTopics(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }
    /*
    单选删除帖子
     */
    @GetMapping("/deltopic")
    @ResponseBody
    public Result delTopic(String id){
        topicService.delTopic(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }
    /*
    添加帖子
     */
    @GetMapping("/addtopic")
    @ResponseBody
    public Result addTopic(String title,
                           Long userId,
                           String content,
                           Long categoryId,
                           Long kindId,
                           Long experience,
                           @RequestParam(value = "isGood",required = false) String isGood,
                           @RequestParam(value = "isTop",required = false) String isTop,
                           @RequestParam(value = "isEnd",required = false)  String isEnd,
                           @RequestParam(value = "status",required = false) String status){
        Topicinfo topicinfo = new Topicinfo();
        topicinfo.setTitle(title);
        topicinfo.setUserId(userId);
        topicinfo.setContent(content);
        topicinfo.setCategoryId(categoryId);
        topicinfo.setKindId(kindId);
        topicinfo.setExperience(experience);
        topicinfo.setIsGood(isGood != null?1:0);
        topicinfo.setIsTop(isTop!= null?1:0);
        topicinfo.setIsEnd(isEnd!= null?1:0);
        topicinfo.setStatus(status!= null?1:0);
        topicinfo.setTopicCreate(System.currentTimeMillis());
        topicinfo.setTopicModified(topicinfo.getTopicCreate());
        int num = topicService.addTopic(topicinfo);
        if(num>0){
            return new Result().ok(MsgEnum.ADD_SUCCESS);
        }else{
            return new Result().error(MsgEnum.ADD_FAILE);
        }
    }
    /*
    更新帖子信息
     */
    @PostMapping("/updatetopic")
    @ResponseBody
    public Result updatetopic(Long id,
                            String title,
                           Long userId,
                           String content,
                           Long categoryId,
                           Long kindId,
                           Long experience,
                           @RequestParam(value = "isGood",required = false) String isGood,
                           @RequestParam(value = "isTop",required = false) String isTop,
                           @RequestParam(value = "isEnd",required = false)  String isEnd,
                           @RequestParam(value = "status",required = false) String status){
        Topicinfo topicinfo = new Topicinfo();
        topicinfo.setId(id);
        topicinfo.setTitle(title);
        topicinfo.setUserId(userId);
        topicinfo.setContent(content);
        topicinfo.setCategoryId(categoryId);
        topicinfo.setKindId(kindId);
        topicinfo.setIsGood(isGood != null?1:0);
        topicinfo.setIsTop(isTop!= null?1:0);
        topicinfo.setIsEnd(isEnd!= null?1:0);
        topicinfo.setStatus(status!= null?1:0);
        topicinfo.setExperience(experience);
        topicinfo.setTopicCreate(System.currentTimeMillis());
        topicinfo.setTopicModified(topicinfo.getTopicCreate());
        topicService.updateTopic(topicinfo);
        return new Result().ok(MsgEnum.UPDATE_SUCCESS);
    }
    /*
    打开帖子表单
     */
    @GetMapping("/listform")
    public String listform(@RequestParam(value = "id",required = false) Long id,Model model){
        Topicinfo topicinfo = topicService.findTopicById(id);
        model.addAttribute("topic",topicinfo);
        return "/topic/listform";
    }
    /*
    跳转评论管理
     */
    @GetMapping("/comment")
    public String comment(){
        return "/topic/comment";

    }
    /*
    获取评论列表数据
     */
    @GetMapping("/getComment")
    @ResponseBody
    public Result getComment(@RequestParam("page") Integer page,
                             @RequestParam("limit") Integer limit,
                             @RequestParam(value = "userId",required = false) Long userId,
                             @RequestParam(value = "content",required = false) String content){
        return topicService.findComment(page, limit, userId, content);

    }
    /*
    多选删除评论
     */
    @GetMapping("/delComments")
    @ResponseBody
    public Result delComments(String id){
        topicService.delComments(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }

    @PostMapping("/updatecomment")
    @ResponseBody
    public Result updateComment(Comment comment){
        topicService.updateComment(comment);
        return new Result().ok(MsgEnum.UPDATE_SUCCESS);
    }

    @GetMapping("/delcomment")
    @ResponseBody
    public Result delComment(Long id){
        topicService.delComment(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }

    /*
    打开评论表单
     */
    @GetMapping("/contform")
    public String contform(@RequestParam(value = "id",required = false) Long id,Model model){
        Comment comment = topicService.findCommentById(id);
        model.addAttribute("comment",comment);
        return "/topic/contform";

    }
    /*
    跳转板块管理
     */
    @GetMapping("/category")
    public String category(){
        return "/topic/category";

    }
    /*
    打开板块表单
     */
    @GetMapping("/categoryform")
    public String categoryform(@RequestParam(value = "id",required = false) Long id,Model model){
        Category category = topicService.findCateById(id);
        model.addAttribute("cate",category);
        return "/topic/categoryform";

    }

    /*
    获取板块列表
     */
    @GetMapping("/getcategory")
    @ResponseBody
    public Result getcategory(){
        Result result = new Result();
        List<Category> categories = topicService.findAllCate();
        result.setData(categories);
        return  result;
    }

    /*
    更新板块信息
     */
    @PostMapping("/updatecategory")
    @ResponseBody
    public Result updatecategory(Category category){
        category.setCategoryModified(System.currentTimeMillis());
        topicService.updateCate(category);
        return new Result().ok(MsgEnum.UPDATE_SUCCESS);
    }
    /*
    删除板块
     */
    @GetMapping("/delcategory")
    @ResponseBody
    public Result delcategory(Long id){
        topicService.delCate(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }

    @GetMapping("/addcategory")
    @ResponseBody
    public Result addcategory(Category category){
        category.setCategoryCreate(System.currentTimeMillis());
        category.setCategoryModified(category.getCategoryCreate());
        topicService.addCate(category);
        return new Result().ok(MsgEnum.ADD_SUCCESS);
    }


    @GetMapping("/kind")
    public String kind(){
        return "/topic/kind";

    }
    @GetMapping("/kindform")
    public String kindform(@RequestParam(value = "id",required = false) Long id,Model model){
        Kind kind = topicService.findKindById(id);
        model.addAttribute("kind",kind);
        return "/topic/kindform";

    }
    /*
    获取分类信息数据
     */
    @GetMapping("/getkind")
    @ResponseBody
    public Result getkind(){
        Result result = new Result();
        List<Kind> kindList = topicService.findKind();
        result.setData(kindList);
        return  result;
    }


    @PostMapping("/updatekind")
    @ResponseBody
    public Result updatekind(Kind kind){
        kind.setKindModified(System.currentTimeMillis());
        topicService.updateKind(kind);
        return new Result().ok(MsgEnum.UPDATE_SUCCESS);
    }

    @GetMapping("/delkind")
    @ResponseBody
    public Result delkind(Long id){
        topicService.delKind(id);
        return new Result().ok(MsgEnum.DEL_SUCCESS);
    }

    @GetMapping("/addkind")
    @ResponseBody
    public Result addkind(Kind kind){
       kind.setKindCreate(System.currentTimeMillis());
       kind.setKindModified(kind.getKindCreate());
        topicService.addKind(kind);
        return new Result().ok(MsgEnum.ADD_SUCCESS);
    }

}
