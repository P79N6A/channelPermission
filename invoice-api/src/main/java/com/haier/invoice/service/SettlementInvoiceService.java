package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.SettlementInvoiceData;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jtbshan on 2018/5/29.
 */
public interface SettlementInvoiceService {
    public JSONObject paging (Map<String, String> param, PagerInfo pagerInfo);

    public JSONObject export (Map<String, String> param);

    public ServiceResult<JSONObject> insert (SettlementInvoiceData param);

    public ServiceResult<JSONObject> update (SettlementInvoiceData param);

    public ServiceResult<String> checkAndImport(List<List<String>> list,String user,List<SettlementInvoiceData> newList);
    
    public List<String> getSelections();



}
