package cn.bdqn.dao;

import cn.bdqn.pojo.Bill;

import java.util.List;

public interface BillDao {

    int add(Bill bill);

    List<Bill> getBillByLikeProName(String productName,Integer providerId,Integer isPayment,Integer startRow,Integer pageSize);

    int getBillCount(String productName,Integer providerId,Integer isPayment);
}
