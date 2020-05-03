package com.bbgu.zmz.communityadmin.mapper;

import com.bbgu.zmz.communityadmin.model.Ad;
import com.bbgu.zmz.communityadmin.model.Qiandao;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface AdExtMapper{

    List<Ad> findAllAd(@Param("title") String title);
}