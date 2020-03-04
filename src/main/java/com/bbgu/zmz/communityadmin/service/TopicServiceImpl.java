package com.bbgu.zmz.communityadmin.service;

import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.mapper.*;
import com.bbgu.zmz.communityadmin.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicinfoExtMapper topicinfoExtMapper;
    @Autowired
    private KindMapper kindMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TopicinfoMapper topicinfoMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Result findAllTopic(Integer page,Integer limit,String title, Long userId, Long categoryId, Long kindId) {
        Result result = new Result();
        PageHelper.startPage(page, limit);
        List<TopicinfoExt> topicinfoExtList = topicinfoExtMapper.findAllTopic(title,userId,categoryId,kindId);
        PageInfo<?> pageList = new PageInfo<>(topicinfoExtList);
        result.setCount(pageList.getTotal());
        result.setData(topicinfoExtList);
        return result;
    }

    @Override
    public List<Kind> findKind() {
        return kindMapper.selectAll();
    }

    @Override
    public List<Category> findCategory() {
        return categoryMapper.selectAll();
    }

    @Override
    public void delTopics(String id) {
        String ids[] = id.split(",");
        for(int i=0;i<ids.length;i++){
            topicinfoMapper.deleteByPrimaryKey(ids[i]);
        }
    }

    @Override
    public void delTopic(String id) {
        topicinfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addTopic(Topicinfo topicinfo) {
        return topicinfoMapper.insertSelective(topicinfo);
    }

    @Override
    public int updateTopic(Topicinfo topicinfo) {
        return topicinfoMapper.updateByPrimaryKey(topicinfo);
    }

    @Override
    public Topicinfo findTopicById(Long id) {
        return topicinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Category> findAllCate() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Kind> findAllKind() {
        return kindMapper.selectAll();
    }

    @Override
    public Category findCateById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Kind findKindById(Long id) {
        return kindMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateCate(Category category) {

        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int updateKind(Kind kind) {
        return kindMapper.updateByPrimaryKeySelective(kind);
    }

    @Override
    public void delCate(Long id) {

        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delKind(Long id) {
        kindMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addCate(Category category) {
        return categoryMapper.insertSelective(category);
    }

    @Override
    public int addKind(Kind kind) {
        return kindMapper.insertSelective(kind);
    }

    @Override
    public Result findComment(Integer page, Integer limit, Long userId, String content) {
        Result result = new Result();
        PageHelper.startPage(page,limit);
        List<CommentExt> commentExtList =  commentExtMapper.findComment(userId, content);
        PageInfo pageInfo = new PageInfo(commentExtList);
        result.setCount(pageInfo.getTotal());
        result.setData(commentExtList);
        return result;

    }

    @Override
    public void delComments(String id) {
        String ids[] = id.split(",");
        for(int i=0;i<ids.length;i++){
            commentMapper.deleteByPrimaryKey(ids[i]);
        }
    }

    @Override
    public Comment findCommentById(Long id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateComment(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public void delComment(Long id) {
        commentMapper.deleteByPrimaryKey(id);
    }
}
