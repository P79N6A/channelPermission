package com.haier.eop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.StocksynCstorage;


public interface EopCenterStocksynCstorageService {
    int deleteByPrimaryKey(Integer id);
    int insert(StocksynCstorage record);
    int updateByPrimaryKeySelective(StocksynCstorage record);
    int getPagerCountS(StocksynCstorage entity);
	JSONObject Listf(PagerInfo pager, StocksynCstorage condition);
	 int jiaoyan(String sCode);
	
}