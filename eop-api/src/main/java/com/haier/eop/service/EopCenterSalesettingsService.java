package com.haier.eop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.Salesettings;
import com.haier.eop.data.model.Stocksyncproducts;

public interface EopCenterSalesettingsService {
    int deleteByPrimaryKey(Integer id);
    String insert(Salesettings record);
    String updateByPrimaryKey(Salesettings record);
    JSONObject Listf(PagerInfo pager, Salesettings condition);
}