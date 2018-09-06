package cn.bdqn.dao.impl;
import cn.bdqn.dao.BaseDao;
import cn.bdqn.dao.ProviderDao;
import cn.bdqn.pojo.Provider;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "providerDao")
public class ProviderDaoImpl extends BaseDao<Provider> implements ProviderDao {
    @Override
    public Provider getEntity(ResultSet rs) {
        Provider provider=null;
        if(rs!=null){
            try {
                int id = rs.getInt("id");
                String proCode = rs.getString("proCode");
                String proName = rs.getString("proName");
                String proDesc = rs.getString("proDesc");
                String proContact = rs.getString("proContact");
                String proPhone = rs.getString("proPhone");
                String proAddress = rs.getString("proAddress");
                String proFax = rs.getString("proFax");
                int createdBy = rs.getInt("createdBy");
                Date creationDate = rs.getDate("creationDate");
                int modifyBy = rs.getInt("modifyBy");
                Date modifyDate = rs.getDate("modifyDate");
                String proPath=rs.getString("proPath");
                String workPath = rs.getString("workPath");
                provider=new Provider(id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyBy,modifyDate);
                provider.setProPath(proPath);
                provider.setWorkPath(workPath);
                return provider;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public int add(Provider provider, Connection con) {
        /**
         *  private String proCode; //供应商编码
         *     private String proName; //供应商名称
         *     private String proDesc; //供应商描述
         *     private String proContact; //供应商联系人
         *     private String proPhone; //供应商电话
         *     private String proAddress; //供应商地址
         *     private String proFax; //供应商传真
         *     private Integer createdBy; //创建者
         *     private Date creationDate; //创建时间
         *     private Integer modifyBy; //更新者
         *     private Date modifyDate;//更新时间
         */

        String sql=" INSERT INTO smbms_provider (proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,proPath,workPath) values " +
                " (?,?,?,?,?,?,?,?,?,?,?) ";
        return super.executeUpdate(sql,new Object[]{provider.getProCode(),provider.getProName(),provider.getProDesc(),provider.getProContact(),
                provider.getProPhone(),provider.getProAddress(),provider.getProFax(),provider.getCreatedBy(),provider.getCreationDate(),provider.getProPath(),provider.getWorkPath()},con);
    }

    @Override
    public List<Provider> getProviderByLikeNameByPage(String proCode, String ProName, Integer startRow, Integer pageSize) {
        StringBuffer sb=new StringBuffer("select * from smbms_provider where 1=1 ");
        List<Object> list=new ArrayList<>();
        if(proCode!=null&&!"".equals(proCode)){
            sb.append(" and proCode like concat('%',?,'%') ");
            list.add(proCode);
        }
        if(ProName!=null&&!"".equals(ProName)){
            sb.append(" and ProName like concat ('%',?,'%') ");
            list.add(ProName);
        }
        sb.append(" limit ?,? ");
        list.add(startRow);
        list.add(pageSize);
        List<Provider> pros = super.executeQuery(sb.toString(), list.toArray());
        return  pros;
    }


    @Override
    public int getProviderCount(String proCode, String ProName) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from smbms_provider where 1=1");
        List<Object> list = new ArrayList<Object>();
        if(null!=proCode&&!"".equals(proCode)){
            sql.append(" and proCode like concat('%',?,'%') ");
            list.add(proCode);
        }
        if(null!=ProName&&!"".equals(ProName)){
            sql.append(" and ProName like concat('%',?,'%') ");
            list.add(ProName);
        }
        List<Provider> pro = super.executeQuery(sql.toString(), list.toArray());
        return pro.size();
    }

    @Override
    public Provider getProviderById(Integer id) {
        String sql="select * from smbms_provider where id=?";
        List<Provider> providers = super.executeQuery(sql, new Object[]{id});
        if(providers!=null&&providers.size()>0){
            return providers.get(0);
        }
        return null;
    }

    @Override
    public int update(Provider provider, Connection connection) {
        String sql="update smbms_provider set proCode=?, proName=?,proDesc=?,\n " +
                " proContact=?, proPhone=?, proAddress=?, proFax=?, createdBy=?,\n " +
                " creationDate=?, modifyBy=?, modifyDate=?,proPath=? where id=? ";
        logger.debug(sql);
        return super.executeUpdate(sql, new Object[]{provider.getProCode(),provider.getProName(),provider
        .getProDesc(),provider.getProContact(),provider.getProPhone(),provider.getProAddress()
        ,provider.getProFax(),provider.getCreatedBy(),provider.getCreationDate(),provider.getModifyBy(),provider.getModifyDate(),provider.getProPath(),provider.getId()}, connection);
    }
}
