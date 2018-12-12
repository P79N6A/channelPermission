package com.haier.eop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.StocksyncProstorage;

public interface EopCenterStocksyncProstorageService {
    int deleteByPrimaryKey(Integer id);
    int insert(StocksyncProstorage record);
    int updateByPrimaryKey(StocksyncProstorage record);
    JSONObject Listf(PagerInfo pager, StocksyncProstorage condition);
	JSONObject jiaoyan(String sku, String sCode, String source);
    
}