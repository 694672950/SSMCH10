package cn.bdqn.service.impl;

import cn.bdqn.dao.BaseDao;
import cn.bdqn.dao.ProviderDao;
import cn.bdqn.pojo.Provider;
import cn.bdqn.service.ProviderService;
import cn.bdqn.util.PageBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {
    private static Logger logger=Logger.getLogger(ProviderServiceImpl.class);

    @Resource(name = "providerDao")
    private ProviderDao providerDao;
    @Override
    public int add(Provider provider) {
        Connection conn = BaseDao.getConnByJNDI();
        try {
            conn.setAutoCommit(false);
            int add = providerDao.add(provider, conn);
            logger.debug("add:"+add);
            conn.commit();
            return add;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                if(conn!=null){
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public PageBean<Provider> findProviderByPage(String proCode, String ProName, Integer pageIndex, Integer pageSize) {
        PageBean<Provider> pageBean=new PageBean<>();
        int providerCount = providerDao.getProviderCount(proCode, ProName);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(providerCount);
        pageBean.setPageIndex(pageIndex);
        List<Provider> providerByLikeNameByPage = providerDao.getProviderByLikeNameByPage(proCode, ProName, (pageIndex - 1) * pageSize, pageSize);
        pageBean.setPageIndexList(providerByLikeNameByPage);
        return pageBean;
    }

    @Override
    public int update(Provider provider) {
        Connection conn = BaseDao.getConnByJNDI();
        try {
            conn.setAutoCommit(false);
            int update = providerDao.update(provider, conn);
            conn.commit();
            logger.debug("执行供应商修改操作: "+update);
            return update;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    @Override
    public Provider findProById(Integer id) {
        return providerDao.getProviderById(id);
    }
}
