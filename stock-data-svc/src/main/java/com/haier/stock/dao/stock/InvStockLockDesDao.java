package com.haier.stock.dao.stock;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvStockLockDes;

public interface InvStockLockDesDao {
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
	    
	    List<InvStockLockDes> queryWaStockQtyByChannel(@Param("sku") String sku,
                @Param("secCode") String secCode,
                @Param("channel") String channel);
	    
	    Integer insert(@Param("lockDes") InvStockLockDes des);
	    Integer update(@Param("lockDes") InvStockLockDes des);
}
