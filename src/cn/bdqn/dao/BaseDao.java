package cn.bdqn.dao;
import org.apache.log4j.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {
    public static Logger logger=Logger.getLogger(BaseDao.class);

    //获取连接
    public static Connection getConnByJNDI(){
        try {
            Context context=new InitialContext();
            DataSource lookup = (DataSource) context.lookup("java:comp/env/jdbc/smbms");
            return lookup.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("获取连接失败，请检查jndi配置是否正确！");
        return null;
    }

    //关闭资源
    public static void CloseAll(ResultSet rs, PreparedStatement ps,Connection con){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null){

            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //增删减
    public int executeUpdate(String sql,Object[] params,Connection con){
        PreparedStatement ps=null;
        try {
            ps=con.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.length;i++){
                    ps.setObject(i+1,params[i]);
                }
            }
           return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            CloseAll(null, ps, null);
        }
        logger.debug("执行"+sql+"语句失败！");
        return 0;
    }

    /**
     *  通用查询方法
     */
    public List<T> executeQuery(String sql,Object[] params){
        List<T> list=new ArrayList<>();
        Connection con=getConnByJNDI();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = con.prepareStatement(sql);
            if(params!=null){
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i+1, params[i]);
                }
            }
            rs = ps.executeQuery();
            while (rs.next()){
               T en=getEntity(rs);
                list.add(en);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            CloseAll(rs, ps,con );
        }
        return list;
    }

    /**
     * 查询单个对象
     * @param sql
     * @param params
     * @return
     */
    public T executeOne(String sql,Object[] params){
        Connection conn = getConnByJNDI();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps= conn.prepareStatement(sql);
            if(params!=null){
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i+1,params[i] );
                }
            }
            rs=ps.executeQuery();
            while (rs.next()){
                T en=getEntity(rs);
                return en;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            CloseAll(rs,ps,conn);
        }
        return null;
    }

    /**
     * 查询数量
     * @param sql
     * @param params
     * @return
     */
    public int executeCount(String sql,Object[] params){
        Connection con=getConnByJNDI();
        PreparedStatement ps=null;
        ResultSet rs=null;
        int count=-1;
        try {
            ps = con.prepareStatement(sql);
            if(params!=null){
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i+1, params[i]);
                }
            }
            rs = ps.executeQuery();
            while (rs.next()){
                count=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            CloseAll(rs, ps,con );
        }
        return count;
    }

    public abstract T getEntity(ResultSet rs);
}
