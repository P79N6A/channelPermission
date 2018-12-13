package com.haier.shop.service;

import com.haier.shop.model.Wlbstocksyncstorages;

import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/7/26 15:45
 */
public interface WLBStockSyncStoragesService {

    /**
     * 分页查询物流宝库存同步库位列表
     * @param params
     * @return
     */
    Map<String,Object> getStockSyncStorageList(Map<String,Object> params);

    /**
     * 添加物流宝库存同步库位
     * @param wlbstocksyncstorages
     * @return
     */
    Integer insert(Wlbstocksyncstorages wlbstocksyncstorages);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);
}
