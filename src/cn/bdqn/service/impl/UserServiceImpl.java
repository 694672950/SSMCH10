package cn.bdqn.service.impl;

import cn.bdqn.dao.BaseDao;
import cn.bdqn.dao.UserDao;
import cn.bdqn.pojo.User;
import cn.bdqn.service.UserService;
import cn.bdqn.util.PageBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name="userDao")
    private UserDao userDao;


    @Override
    public User login(String userCode, String userPassword) {
        return userDao.getUserByUserCodeAndUserPassword(userCode,userPassword);
    }


    @Override
    public boolean findUserCode(String userCode) {
        return userDao.getUserCode(userCode);
    }

    @Override
    public PageBean<User> findUserByRoleAndLikeName(Integer userRole, String userName, Integer pageIndex, Integer pageSize) {
        PageBean<User> pageBean=new PageBean<>();
        int likeNameCount = userDao.getUserCount(userName, userRole);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(likeNameCount);
        pageBean.setPageIndex(pageIndex);
        List<User> userByLikeNamePage = userDao.getUserByLikeNamePage(userRole, userName, (pageIndex - 1) * pageSize, pageSize);
        pageBean.setPageIndexList(userByLikeNamePage);
        return pageBean;
    }


    @Override
    public User findUserId(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public int updatePw(Integer id, String newPwd) {
        Connection con = BaseDao.getConnByJNDI();
        try {
            con.setAutoCommit(false);
            int i = userDao.updatePw(id, newPwd, con);
            con.commit();
            return i;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            if(con!=null){
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
}
