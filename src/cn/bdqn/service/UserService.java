package cn.bdqn.service;

import cn.bdqn.pojo.User;
import cn.bdqn.util.PageBean;

public interface UserService {

    User login(String userCode, String userPassword);

    boolean findUserCode(String userCode);

    PageBean<User> findUserByRoleAndLikeName(Integer userRole,String userName,Integer pageIndex,Integer pageSize);

    User findUserId(Integer id);

    int updatePw(Integer id, String newPwd);
}
