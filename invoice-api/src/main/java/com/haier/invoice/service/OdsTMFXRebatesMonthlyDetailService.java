package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.OdsTMFXRebatesMonthlyDetail;

/**
 * Created by xupeng on 2018/5/22.
 */
public interface OdsTMFXRebatesMonthlyDetailService {
    public JSONObject paging (OdsTMFXRebatesMonthlyDetail param, PagerInfo pagerInfo);
    public JSONObject export (OdsTMFXRebatesMonthlyDetail param);
    /**
     * 手动执行计算
     *
     * @param year
     * @param month
     * @param type
     * @param flag
     * @return
     */
    public JSONObject actionToCreateData(String year, String month, String type, String flag);
}
