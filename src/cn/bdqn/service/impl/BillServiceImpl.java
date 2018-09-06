package cn.bdqn.service.impl;

import cn.bdqn.dao.BillDao;
import cn.bdqn.pojo.Bill;
import cn.bdqn.service.BillService;
import cn.bdqn.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("billService")
public class BillServiceImpl implements BillService {

    @Resource(name="billDao")
    private BillDao billDao;


    @Override
    public int add(Bill bill) {
        return 0;
    }


    @Override
    public PageBean<Bill> findBillPage(String productName, Integer providerId, Integer isPayment, Integer pageIndex, Integer pageSize) {
        PageBean<Bill> pageBean=new PageBean<>();
        int billCount = billDao.getBillCount(productName, providerId, isPayment);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(billCount);
        pageBean.setPageIndex(pageIndex);
        List<Bill> billByLikeProName = billDao.getBillByLikeProName(productName, providerId, isPayment, (pageIndex - 1) * pageSize, pageSize);
        pageBean.setPageIndexList(billByLikeProName);
        return pageBean;
    }

}
