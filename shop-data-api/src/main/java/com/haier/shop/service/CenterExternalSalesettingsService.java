package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.ExternalSaleSettings;

public interface CenterExternalSalesettingsService {
    int deleteByPrimaryKey(Integer id);
    String insert(ExternalSaleSettings record);
    String updateByPrimaryKey(ExternalSaleSettings record);
    JSONObject Listf(PagerInfo pager, ExternalSaleSettings condition);
}