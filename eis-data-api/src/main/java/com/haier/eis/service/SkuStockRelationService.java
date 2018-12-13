/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.eis.service;

import java.util.List;

import com.haier.eis.model.SkuStockRelation;

/**
 * @Filename: HhckInfoService.java
 * @Version: 1.0
 * @Author: 穆占强
 * @Email: muzhanqiang@ehaier.com
 */
public interface SkuStockRelationService {

    /**
     * job执行换货拆单操作
     *
     * @return
     * @throws ApiException
     */
//    public String syncSkuStock(ShopEnum shop);
//
//    public List<SkuStockRelation> qryStockSyncLogDaiyl(ShopEnum shop,List<String> skuList,List<String> scodeList);
//
//    public List<SkuStockRelation> qryStockSyncLog(
//            List skuList,
//            String addTimeStart,
//            List scodeList);

    /**
     * 获取3W SKU 和 数量对应关系
     * @param time 获取时间
     * @return List<SkuStockRelation>
     * @see SkuStockRelation
     */
    public List<SkuStockRelation> get3WskuToNum(String getTime);

}
