package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.OdsTMFXIndustrySummary;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.service
 * @Date: Created in 2018/5/29 15:08
 * @Modified By:
 */
public interface OdsTMFXIndustrySummaryDataService {

  JSONObject queryIndustrySummary(OdsTMFXIndustrySummary param);

  List<OdsTMFXIndustrySummary> queryIndustrySummaryExcel(OdsTMFXIndustrySummary param);
}
