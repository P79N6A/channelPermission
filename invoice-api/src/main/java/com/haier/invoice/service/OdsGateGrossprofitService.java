package com.haier.invoice.service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dto.OdsGateGrossprofitDto;
import com.haier.shop.model.Brands;
import com.haier.shop.model.OdsGateGrossprofit;
import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.ProductCates;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/26.
 */
public interface OdsGateGrossprofitService {
    public JSONObject paging(OdsGateGrossprofitDto param, PagerInfo pagerInfo);

    public ServiceResult<JSONObject> insert(OdsGateGrossprofit param);

    public ServiceResult<JSONObject> update(OdsGateGrossprofit param);

    public List<Map<String,Object>> getBrands();

    List<ProductCates> selectCateName();

    public ServiceResult<JSONObject> delBatch(List<String> ids);

    public ServiceResult<String> checkInfo(List<List<String>> list);

    public ServiceResult<String> execExcel(List<List<String>> list, String user);


}
