package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.DistributionDetails;

/**
 * Created by jtbshan on 2018/5/28.
 */
public interface DistributionDetailsService {
    public JSONObject paging (DistributionDetails param, PagerInfo pagerInfo);
    public JSONObject export (DistributionDetails param);
}
