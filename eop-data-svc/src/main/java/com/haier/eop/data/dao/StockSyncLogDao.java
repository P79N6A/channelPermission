package com.haier.eop.data.dao;

import java.util.List;

import com.haier.eop.data.model.TmStockSyncLog;
import org.apache.ibatis.annotations.Param;

import com.haier.eop.data.model.StockSyncLog;
import com.haier.eop.data.model.Stocksyncproducts;

public interface StockSyncLogDao {
	List<StockSyncLog>Listf( @Param("start") int start, @Param("rows") int rows,
			@Param("sse")String sse,@Param("sku")String sku,
			@Param("sourceProductId")String sourceProductId,
			@Param("sCode")String sCode,@Param("sourceStoreCode")String sourceStoreCode,
			@Param("stockSyncResult")String stockSyncResult,@Param("addTimeStart")String addTimeStart,
			@Param("addTimeEnd")String addTimeEnd);
	int getPagerCountS(
			@Param("sse")String sse,@Param("sku")String sku,
			@Param("sourceProductId")String sourceProductId,
			@Param("sCode")String sCode,@Param("sourceStoreCode")String sourceStoreCode,
			@Param("stockSyncResult")String stockSyncResult,@Param("addTimeStart")String addTimeStart,
			@Param("addTimeEnd")String addTimeEnd);
}