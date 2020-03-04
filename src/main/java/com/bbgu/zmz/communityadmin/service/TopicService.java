package com.bbgu.zmz.communityadmin.service;
import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.model.Category;
import com.bbgu.zmz.communityadmin.model.Comment;
import com.bbgu.zmz.communityadmin.model.Kind;
import com.bbgu.zmz.communityadmin.model.Topicinfo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface TopicService {
    Result findAllTopic(Integer page,Integer limit, String title, Long userId, Long categoryId, Long kindId);
    List<Kind> findKind();
    List<Category> findCategory();
    void delTopics(String id);
    void delTopic(String id);
    int addTopic(Topicinfo topicinfo);
    int updateTopic(Topicinfo topicinfo);
    Topicinfo findTopicById(Long id);
    List<Category> findAllCate();
    List<Kind> findAllKind();
    Category findCateById(Long id);
    Kind findKindById(Long id);
    int updateCate(Category category);
    int updateKind(Kind kind);
    void delCate(Long id);
    void delKind(Long id);
    int addCate(Category category);
    int addKind(Kind kind);
    Result findComment(Integer page, Integer limit, Long userId, String content);
    void delComments(String id);
    Comment findCommentById(Long id);
    int updateComment(Comment comment);
    void delComment(Long id);
}
