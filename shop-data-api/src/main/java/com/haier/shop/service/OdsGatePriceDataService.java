package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Brands;
import com.haier.shop.model.GatePrice;
import com.haier.shop.model.OdsGateGrossprofit;

import java.util.List;
import java.util.Map;

public interface OdsGatePriceDataService {
    Map<String, Object> queryGatePrice(Map<String, Object> params);

    List<GatePrice> getExportGatePriceList(Map<String, Object> params);

    ServiceResult<String> bulkImport(List<GatePrice> list);

    GatePrice queryOdsGatePriceById(String id);

    ServiceResult<String> updateGatePrice(GatePrice gatePrice);

    List<String> selectBrandsList();
    List<String> selectCategoryList();

    GatePrice getOdsGatePriceBySku(String sku);
}
