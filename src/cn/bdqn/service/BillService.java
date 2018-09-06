package cn.bdqn.service;

import cn.bdqn.pojo.Bill;
import cn.bdqn.util.PageBean;

public interface BillService {

    int add(Bill bill);

    PageBean<Bill> findBillPage(String productName, Integer providerId, Integer isPayment, Integer pageIndex, Integer pageSize);
}
