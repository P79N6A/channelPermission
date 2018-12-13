package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.OdsTMFXRebatesSummary;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.service
 * @Date: Created in 2018/5/29 15:03
 * @Modified By:
 */
public interface OdsTMFXRebatesSummaryDataService {

  JSONObject queryRebatesSummary(OdsTMFXRebatesSummary param);

  List<OdsTMFXRebatesSummary> queryRebatesSummaryExcel(OdsTMFXRebatesSummary param);

}
