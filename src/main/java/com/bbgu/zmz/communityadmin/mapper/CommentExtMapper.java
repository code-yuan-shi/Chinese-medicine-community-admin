package com.bbgu.zmz.communityadmin.mapper;

import com.bbgu.zmz.communityadmin.model.Comment;
import com.bbgu.zmz.communityadmin.model.CommentExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommentExtMapper extends Mapper<Comment> {
    List<CommentExt> findComment(@Param("userId") Long userId, @Param("content") String content);
}