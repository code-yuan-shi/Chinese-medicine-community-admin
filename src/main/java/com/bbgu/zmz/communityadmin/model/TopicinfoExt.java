package com.bbgu.zmz.communityadmin.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class TopicinfoExt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "topic_create")
    private Long topicCreate;
    @Column(name = "topic_modified")
    private Long topicModified;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "kind_id")
    private Long kindId;
    @Column(name = "view_count")
    private Integer viewCount;
    @Column(name = "is_good")
    private Integer isGood;
    @Column(name = "is_end")
    private Integer isEnd;
    @Column(name = "is_top")
    private Integer isTop;
    private Integer status;
    private Long experience;
    private String content;
    private String catename;
    private String kindname;
    private String time;
}