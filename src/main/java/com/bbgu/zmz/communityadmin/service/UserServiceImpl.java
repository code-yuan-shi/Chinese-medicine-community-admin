package com.bbgu.zmz.communityadmin.service;

import com.bbgu.zmz.communityadmin.dto.Result;
import com.bbgu.zmz.communityadmin.enums.MsgEnum;
import com.bbgu.zmz.communityadmin.mapper.UserExtMapper;
import com.bbgu.zmz.communityadmin.mapper.UserMapper;
import com.bbgu.zmz.communityadmin.model.User;
import com.bbgu.zmz.communityadmin.model.UserExt;
import com.bbgu.zmz.communityadmin.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserExtMapper userExtMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> findAllUser(Long accountId,String name,String email,Integer sex,String role,Integer status) {
        return userExtMapper.findUser(accountId, name, email, sex, role, status);
    }

    @Override
    public Result insertUser(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("accountId",user.getAccountId());
        User olduser = userMapper.selectOneByExample(example);
        if(olduser == null){
            Example example1 = new Example(User.class);
            example1.createCriteria().andEqualTo("email",user.getEmail());
            User olduser1 = userMapper.selectOneByExample(example1);
            if(olduser1 == null){
                userMapper.insertSelective(user);
                return new Result().ok(MsgEnum.ADD_SUCCESS);
            }else{
                return new Result().error(MsgEnum.EMAIL_EXIT);
            }

        }else{
            return new Result().error(MsgEnum.USER_EXIT);
        }
    }

    @Override
    public void delUsers(String id) {
        String ids[] = id.split(",");
        for(int i=0;i<ids.length;i++){
            userMapper.deleteByPrimaryKey(ids[i]);
        }
    }

    @Override
    public UserExt findUserByAccountId(Long accountId) {
        return userExtMapper.findUserByAccountId(accountId);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
    @Override
    /*
    用户修改密码
     */
    public Result modifyUserPassword(String nowpass, String pass,String repass,Long accountId) {
        String pwd = MD5Utils.getMd5(nowpass);
        String rep = MD5Utils.getMd5(repass);
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("accountId", accountId);
        User userinfo = userMapper.selectOneByExample(example);
        if (pass.equals(repass)) {  //如果两次输入密码相同
            if (userinfo.getPwd().equals(pwd)) {   //如果数据库密码相同
                if(rep.equals(userinfo.getPwd())){
                    return new Result().error(MsgEnum.OLD_NEW_SAME);
                }else{
                    User user = new User();
                    user.setPwd(MD5Utils.getMd5(pass));
                    userMapper.updateByExampleSelective(user, example);
                    return new Result().ok(MsgEnum.UPDATE_SUCCESS);
                }
            }else{
                return new Result().error(MsgEnum.OLD_PWD_INCORRECT);
            }
        } else {
            return new Result().error(MsgEnum.PWD_ATYPISM);
        }
    }



        /*
    用户登录验证
     */
    @Override
    public User loginCheck(User user){
        String pwd =  MD5Utils.getMd5(user.getPwd());
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("accountId",user.getAccountId()).andEqualTo("pwd",pwd);
        List<User> userList = userMapper.selectByExample(example);
        if(userList.size() != 0){
            if(userList.get(0).getRole().equals("管理员")){
                User user1 = new User();
                user1.setId(userList.get(0).getId());
                user1.setUserModified(System.currentTimeMillis());
                userMapper.updateByPrimaryKeySelective(user1);
                return userList.get(0);
            }
        }
        return null;
    }

}
