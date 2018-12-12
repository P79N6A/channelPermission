package com.haier.stock.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.stock.model.InvBaseStockExcel;

import java.util.List;
import java.util.Map;

public interface SvcInvBaseStockService {
        public JSONObject queryInvBaseStockList(Map<String, String> map, PagerInfo pager);
        public List<InvBaseStockExcel> queryInvBaseStockList1(InvBaseStockExcel invBaseStock, PagerInfo pager);
        int getRowCnt();
}
