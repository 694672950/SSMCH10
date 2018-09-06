package cn.bdqn.dao;

import cn.bdqn.pojo.Provider;

import java.sql.Connection;
import java.util.List;

public interface ProviderDao {
    int add(Provider provider, Connection con);

    List<Provider> getProviderByLikeNameByPage(String proCode, String ProName,Integer StartRow,Integer pageSize);

    int getProviderCount(String proCode, String ProName);

    Provider getProviderById(Integer id);

    int update(Provider provider,Connection connection);
}
