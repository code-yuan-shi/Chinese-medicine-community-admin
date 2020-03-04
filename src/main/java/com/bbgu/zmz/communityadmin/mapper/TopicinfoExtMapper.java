package com.bbgu.zmz.communityadmin.mapper;

import com.bbgu.zmz.communityadmin.model.Topicinfo;
import com.bbgu.zmz.communityadmin.model.TopicinfoExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TopicinfoExtMapper{

    List<TopicinfoExt> findAllTopic(@Param("title") String title,
                                    @Param("userId") Long userId,
                                    @Param("categoryId") Long categoryId,
                                    @Param("kindId") Long kindId);
}