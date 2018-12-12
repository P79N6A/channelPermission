package com.haier.shop.service;

import com.haier.shop.model.IcmsOutStore;

/*
*
* 作者:张波
* 2017/12/19
* */
public interface IcmsOutStoreService {
    /**
     * 新增 京东订单出库信息
     * @param icmsOutStore
     * @return
     */
    Integer insert(IcmsOutStore icmsOutStore);
}
