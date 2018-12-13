package com.haier.eop.data.dao;

import com.haier.eop.data.model.StockSyncLog;
import com.haier.eop.data.model.TmStockSyncLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TmStockSyncLogDao {
	List<TmStockSyncLog> TmListf(@Param("start") int start, @Param("rows") int rows,
                              @Param("sse") String sse, @Param("sku") String sku,
                              @Param("scode") String scode,
                              @Param("stockSyncResult") String stockSyncResult, @Param("addTimeStart") String addTimeStart,
                              @Param("addTimeEnd") String addTimeEnd);
	int getTmPagerCountS(
            @Param("sse") String sse, @Param("sku") String sku,
            @Param("scode") String scode,
            @Param("stockSyncResult") String stockSyncResult, @Param("addTimeStart") String addTimeStart,
            @Param("addTimeEnd") String addTimeEnd);
}