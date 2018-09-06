package cn.bdqn.service;

import cn.bdqn.pojo.Provider;
import cn.bdqn.util.PageBean;

import java.util.List;

public interface ProviderService {
    int add(Provider provider);

    PageBean<Provider> findProviderByPage(String proCode, String ProName, Integer pageIndex, Integer pageSize);

    Provider findProById(Integer id);

    int update(Provider provider);

}
