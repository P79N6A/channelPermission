package com.haier.distribute.service;

import com.alibaba.fastjson.JSONObject;

import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.TSaleProductPrice;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6 0006.
 */
public interface DistributeCenterOrderCommissionService {
    JSONObject orderCommissionInfoList(PagerInfo pager, TSaleProductPrice tSaleProductPrice);

    List<TSaleProductPrice> getExportData(TSaleProductPrice condition);

}
