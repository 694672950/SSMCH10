package cn.bdqn.dao.impl;
import cn.bdqn.dao.BaseDao;
import cn.bdqn.dao.BillDao;
import cn.bdqn.pojo.Bill;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository(value = "billDao")
public class BillDaoImpl extends BaseDao<Bill> implements BillDao {
    @Override
    public Bill getEntity(ResultSet rs) {
        Bill bill=null;
        if(rs!=null){
                 /*
                private Integer id;   //id
                private String billCode; //账单编码
                private String productName; //商品名称
                private String productDesc; //商品描述
                private String productUnit; //商品单位
                private BigDecimal productCount; //商品数量
                private BigDecimal totalPrice; //总金额
                private Integer isPayment; //是否支付
                private Integer providerId; //供应商ID
                private Integer createdBy; //创建者
                private Date creationDate; //创建时间
                private Integer modifyBy; //更新者
                private Date modifyDate;//更新时间*/
            try {
                int id = rs.getInt("id");
                String billCode = rs.getString("billCode");
                String productName = rs.getString("productName");
                String productDesc = rs.getString("productDesc");
                String productUnit = rs.getString("productUnit");
                BigDecimal productCount = rs.getBigDecimal("productCount");
                BigDecimal totalPrice = rs.getBigDecimal("totalPrice");
                int isPayment = rs.getInt("isPayment");
                int providerId = rs.getInt("providerId");
                int createdBy = rs.getInt("createdBy");
                Date creationDate = rs.getDate("creationDate");
                int modifyBy = rs.getInt("modifyBy");
                Date modifyDate = rs.getDate("modifyDate");
                return bill=new Bill(id,billCode,productName,productDesc,productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate,modifyBy,modifyDate);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public int add(Bill bill) {
        return 0;
    }

    @Override
    public List<Bill> getBillByLikeProName(String productName, Integer providerId, Integer isPayment, Integer startRow, Integer pageSize) {
        StringBuffer sql=new StringBuffer("select * from smbms_bill where 1=1");
        List<Object> list=new ArrayList<>();
        if(productName!=null&&!"".equals(productName)){
            sql.append(" and productName like concat ('%',?,'%') ");
            list.add(productName);
        }
        if(providerId!=null&&providerId!=0){
            sql.append( " providerId=? ");
            list.add(providerId);
        }
        if(isPayment!=null&&isPayment!=0){
            sql.append(" isPayment=? ");
            list.add(isPayment);
        }
        sql.append(" limit ?,? ");
        list.add(startRow);
        list.add(pageSize);
        return super.executeQuery(sql.toString(), Arrays.asList(list.toArray()).toArray());
    }


    @Override
    public int getBillCount(String productName, Integer providerId, Integer isPayment) {
        StringBuffer sql=new StringBuffer("select * from smbms_bill where 1=1");
        List<Object> list=new ArrayList<>();
        if(productName!=null&&!"".equals(productName)){
            sql.append(" and productName like concat ('%',?,'%') ");
            list.add(productName);
        }
        if(providerId!=null&&providerId!=0){
            sql.append( " providerId=? ");
            list.add(providerId);
        }
        if(isPayment!=null&&isPayment!=0){
            sql.append(" isPayment=? ");
            list.add(isPayment);
        }
        return super.executeQuery(sql.toString(),list.toArray()).size();
    }
}
