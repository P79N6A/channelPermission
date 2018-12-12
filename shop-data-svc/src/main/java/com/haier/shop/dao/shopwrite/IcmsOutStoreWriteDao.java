package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.IcmsOutStore;
import org.apache.ibatis.annotations.Mapper;

/*
*
* 作者:张波
* 2017/12/19
* */
@Mapper
public interface IcmsOutStoreWriteDao {
    /**
     * 新增 京东订单出库信息
     * @param icmsOutStore
     * @return
     */
    Integer insert(IcmsOutStore icmsOutStore);
}
