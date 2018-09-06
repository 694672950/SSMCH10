package cn.bdqn.dao.impl;
import cn.bdqn.dao.BaseDao;
import cn.bdqn.dao.UserDao;
import cn.bdqn.pojo.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository(value = "userDao")
public class UserDaoImpl extends BaseDao<User> implements UserDao {
    @Override
    public User getEntity(ResultSet rs) {
        User user=null;
        if(rs!=null){
            try {
                int id = rs.getInt("id");
                String userCode = rs.getString("userCode");
                String userName = rs.getString("userName");
                String userPassword = rs.getString("userPassword");
                int gender = rs.getInt("gender");
                Date birthday = rs.getDate("birthday");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int userRole = rs.getInt("userRole");
                int createdBy = rs.getInt("createdBy");
                Date creationDate = rs.getDate("creationDate");
                int modifyBy = rs.getInt("modifyBy");
                Date modifyDate = rs.getDate("modifyDate");
               return user=new User(id,userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate,modifyBy,modifyDate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public User getUserByUserCodeAndUserPassword(String userCode, String userPassword) {
        String sql="select * from smbms_user where userCode=? and userPassword=?";
        List<User> users = super.executeQuery(sql, new Object[]{userCode, userPassword});
        if(users!=null&&users.size()>0){
            return users.get(0);
        }
        return null;
    }


    @Override
    public boolean getUserCode(String userCode) {
        String sql="select * from smbms_user where userCode=?";
        List<User> users = super.executeQuery(sql, new Object[]{userCode});
        if(users.size()>0){
            return true;
        }
        return false;
    }

    //分页查询
    @Override
    public List<User> getUserByLikeNamePage(Integer userRole, String userName, Integer startRow, Integer pageSize) {
        StringBuffer sb=new StringBuffer("select * from smbms_user where 1=1");
        List<Object> list=new ArrayList<>();
        if(userName!=null&&userRole!=0){
            sb.append(" and userRole=? ");
            list.add(userRole);
        }
        if(userName!=null&&!"".equals(userName)){
            sb.append(" and userName like concat ('%',?,'%') ");
            list.add(userName);
        }
        sb.append(" limit  ? , ? ");
        list.add(startRow);
        list.add(pageSize);
        List<User> users = super.executeQuery(sb.toString(), list.toArray());
        return  users;
    }


    //分页查询记录数
    @Override
    public int getUserCount(String userName, Integer userRole){
            StringBuffer sql = new StringBuffer();
            sql.append("select * from smbms_user where 1=1");
            List<Object> list = new ArrayList<Object>();
            if(null!=userName&&!"".equals(userName)){
                sql.append(" and userName like concat('%',?,'%')");
                list.add(userName);
            }
            if(userRole!=null&&userRole!=0){
                    sql.append(" and userRole = ?");
                    list.add(userRole);
            }
            List<User> users = super.executeQuery(sql.toString(), list.toArray());
            return users.size();
    }

    @Override
    public User getUserById(Integer id) {
        String sql="select * from smbms_user where id=?";
        return super.executeOne(sql,new Object[]{id} );
    }

    @Override
    public int updatePw(Integer id, String newPwd, Connection con) {
        String sql="update smbms_user set userPassword=? where id=?";
        return super.executeUpdate(sql,new Object[]{id},con);
    }
}
