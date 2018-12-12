package com.haier.eop.service;


import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;

public interface EopCenterStockSyncLogService {
	JSONObject LogListf(PagerInfo pager,String sse,
		   String sku,String sourceProductId,String sCode,String sourceStoreCode,
			  String stockSyncResult,String addTimeStart,String addTimeEnd);
}