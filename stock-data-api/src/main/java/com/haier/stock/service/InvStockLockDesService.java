package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvStockLockDes;

public interface InvStockLockDesService {
	List<Map<String, Object>> queryWaLockDetails(Map<String, Object> parmas);

    List<Map<String, Object>> queryChannelLockDetails(Map<String, Object> params);
    
    /**
     * 根据sku和库位查询集合
     * @param sku
     * @param secCode
     * @return
     */
    List<Map<String, Object>> getBySecCodeAndSku(Map<String, Object> params);
    
    /**
     * /**
     * 根据sku和库位查询集合数量
     * @param sku
     * @param secCode
     * @return
     */
    Integer getBySecCodeAndSkuCount(Map<String, Object> params);
    
    Integer getRowCnt();
    Integer delete();
    
    
    List<InvStockLockDes> queryWaStockQtyByChannel( String sku, String secCode,String channel);
    Integer insert(InvStockLockDes des);
    Integer update(InvStockLockDes des);
}
