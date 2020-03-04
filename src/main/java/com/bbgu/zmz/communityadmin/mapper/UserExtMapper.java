package com.bbgu.zmz.communityadmin.mapper;

import com.bbgu.zmz.communityadmin.model.User;
import com.bbgu.zmz.communityadmin.model.UserExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserExtMapper {

        List<User> findUser(@Param("accountId") Long accountId,
                            @Param("name") String name,
                            @Param("email") String email,
                            @Param("sex") Integer sex,
                            @Param("role") String role,
                            @Param("status") Integer status);
       UserExt findUserByAccountId(Long accountId);

}