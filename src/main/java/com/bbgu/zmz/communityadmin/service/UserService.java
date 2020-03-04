package com.bbgu.zmz.communityadmin.service;

import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.model.User;
import com.bbgu.zmz.communityadmin.model.UserExt;

import java.util.List;

public interface UserService {

    List<User> findAllUser(Long accountId,String name,String email,Integer sex,String role,Integer status);

    Result insertUser(User user);

    void delUsers(String id);

    UserExt findUserByAccountId(Long accountId);

    int updateUser(User user);

    void delUser(Long id);

    Result modifyUserPassword(String nowpass, String pass,String repass,Long accountId);

    User loginCheck(User user);


}
