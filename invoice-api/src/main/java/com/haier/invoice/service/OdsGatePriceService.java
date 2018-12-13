package com.haier.invoice.service;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Brands;
import com.haier.shop.model.GatePrice;

import java.util.List;
import java.util.Map;

public interface OdsGatePriceService {
    /**
     * 分页查询
     */
    Map<String, Object> queryGatePrice(
            Map<String, Object> params);
    /**
     * 下市
     */
    ServiceResult<String> updateGatePrice(GatePrice gatePrice);

    /**
     * 查询导出发票信息
     * @param param
     * @return
     */
    List<GatePrice> getExportGatePriceList(Map<String, Object> param);

    /**
     * 导入处理
     */
    ServiceResult<String> execExcel(List<GatePrice> list);

    ServiceResult<List<String>> selectBrandsList();

    ServiceResult<List<String>> selectCategoryList();


}
