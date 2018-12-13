package com.haier.order.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.SuningGroups;

public interface OrderCenterSuNingGroupsService {
    int deleteByPrimaryKey(Integer id);
    String insert(SuningGroups record);
    String updateByPrimaryKey(SuningGroups record);
    JSONObject Listf(PagerInfo pager, SuningGroups condition);
    int jiaoyan(String sku);
}
