package com.haier.eop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.Stocksyncproducts;

public interface EopCenterStocksyncproductsService {
    int deleteByPrimaryKey(Integer id,String source,String sku);
    int insert(Stocksyncproducts record);
    int updateByPrimaryKey(Stocksyncproducts record);
    JSONObject Listf(PagerInfo pager, Stocksyncproducts condition);
    int jiaoyan(String sku);
}