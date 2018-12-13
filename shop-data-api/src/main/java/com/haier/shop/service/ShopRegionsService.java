package com.haier.shop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Regions;

public interface ShopRegionsService {
    JSONObject Listf(PagerInfo pager, Regions condition);
    JSONArray getRegion(int id);
    ServiceResult<Boolean> insert(Regions condition);
    ServiceResult<Boolean> update(Regions condition);
    ServiceResult<Boolean> delete(int id);
    ServiceResult<Boolean> deleteSubordinateRegion(int id,int regionType);
}
