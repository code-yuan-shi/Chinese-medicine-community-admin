package com.bbgu.zmz.communityadmin.mapper;

import com.bbgu.zmz.communityadmin.model.Qiandao;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QiandaoExtMapper{

    List<Qiandao> findAllSign(@Param("userId") Long userId);
}