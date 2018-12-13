package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.OdsTMFXPeculiarCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by jtbshan on 2018/6/1.
 */
public interface OdsTMFXPeculiarCategoryService {

    public JSONObject paging (OdsTMFXPeculiarCategory param, PagerInfo pagerInfo);
    public JSONObject export (OdsTMFXPeculiarCategory param);
    public ServiceResult<JSONObject> insert (OdsTMFXPeculiarCategory param);

    public ServiceResult<JSONObject> update (OdsTMFXPeculiarCategory param);

    public ServiceResult<JSONObject>  delBatch(List<String> ids);
}
