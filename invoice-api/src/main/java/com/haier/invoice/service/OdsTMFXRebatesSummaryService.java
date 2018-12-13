package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.OdsTMFXIndustrySummary;
import com.haier.shop.model.OdsTMFXRebatesSummary;
import com.haier.shop.model.OdsTMFXShopSummary;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.invoice.service
 * @Date: Created in 2018/5/29 14:57
 * @Modified By:
 */
public interface OdsTMFXRebatesSummaryService {

  /**
   * 分页查询
   */
  JSONObject queryRebatesSummary(OdsTMFXRebatesSummary odsTMFXRebatesSummary);

  /**
   * 查询导出
   */
  List<OdsTMFXRebatesSummary> queryRebatesSummaryExcel(OdsTMFXRebatesSummary param);

  /**
   * 按生态店汇总--分页查询
   */
  JSONObject queryShopSummary(OdsTMFXShopSummary odsTMFXShopSummary);

  /**
   * 按生态店汇总---查询导出
   */
  List<OdsTMFXShopSummary> queryShopSummaryExcel(OdsTMFXShopSummary map);

  /**
   * 按产业品牌汇总--分页查询
   */
  JSONObject queryIndustrySummary(OdsTMFXIndustrySummary odsTMFXIndustrySummary);

  /**
   * 按产业品牌汇总--查询导出
   */
  List<OdsTMFXIndustrySummary> queryIndustrySummaryExcel(OdsTMFXIndustrySummary map);
}
