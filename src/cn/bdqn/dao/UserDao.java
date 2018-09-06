package cn.bdqn.dao;

import cn.bdqn.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {

    User getUserByUserCodeAndUserPassword(String userCode, String userPassword);

    boolean getUserCode(String userCode);

    List<User> getUserByLikeNamePage(Integer userRole, String userName,Integer startRow,Integer pageSiez);

    int getUserCount(String userName, Integer userRole);


    User getUserById(Integer id);

    int updatePw(Integer id, String newPwd, Connection con);
}
