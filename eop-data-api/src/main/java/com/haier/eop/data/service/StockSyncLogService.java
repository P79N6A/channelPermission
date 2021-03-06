package com.haier.eop.data.service;

import java.util.List;

import com.haier.eop.data.model.StockSyncLog;
import com.haier.eop.data.model.TmStockSyncLog;

public interface StockSyncLogService {
   List<StockSyncLog>LogListf(int start, int rows,String sse,
		   String sku,String sourceProductId,String sCode,String sourceStoreCode,
			  String stockSyncResult,String addTimeStart,String addTimeEnd);
   int getPagerCountS(String sse,
		   String sku,String sourceProductId,String sCode,String sourceStoreCode,
			  String stockSyncResult,String addTimeStart,String addTimeEnd);

	List<TmStockSyncLog>TmLogListf(int start, int rows, String sse,
								   String sku, String sCode, String stockSyncResult,
								   String addTimeStart, String addTimeEnd);
	int getTmPagerCountS(String sse,
					   String sku,String sCode,String stockSyncResult,
					   String addTimeStart,String addTimeEnd);
}