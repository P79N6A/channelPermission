package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.OdsTMFXShopSummary;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.service
 * @Date: Created in 2018/5/29 15:10
 * @Modified By:
 */
public interface OdsTMFXShopSummaryDataService {

  JSONObject queryShopSummary(OdsTMFXShopSummary param);

  List<OdsTMFXShopSummary> queryShopSummaryExcel(OdsTMFXShopSummary param);
}
