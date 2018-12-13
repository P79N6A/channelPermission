package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.invoice.service.OdsTMFXRebatesSummaryService;
import com.haier.shop.model.OdsTMFXIndustrySummary;
import com.haier.shop.model.OdsTMFXRebatesSummary;
import com.haier.shop.model.OdsTMFXShopSummary;
import com.haier.shop.service.OdsTMFXIndustrySummaryDataService;
import com.haier.shop.service.OdsTMFXRebatesSummaryDataService;
import com.haier.shop.service.OdsTMFXShopSummaryDataService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.invoice.services
 * @Date: Created in 2018/5/29 15:01
 * @Modified By:
 */
@Service
public class OdsTMFXRebatesSummaryServiceImpl implements OdsTMFXRebatesSummaryService {

  @Resource
  private OdsTMFXShopSummaryDataService odsTMFXShopSummaryDataService;
  @Resource
  private OdsTMFXRebatesSummaryDataService odsTMFXRebatesSummaryDataService;
  @Resource
  private OdsTMFXIndustrySummaryDataService odsTMFXIndustrySummaryDataService;

  @Override
  public JSONObject queryRebatesSummary(OdsTMFXRebatesSummary param) {
    return odsTMFXRebatesSummaryDataService.queryRebatesSummary(param);
  }

  @Override
  public List<OdsTMFXRebatesSummary> queryRebatesSummaryExcel(OdsTMFXRebatesSummary param) {
    return odsTMFXRebatesSummaryDataService.queryRebatesSummaryExcel(param);
  }

  @Override
  public JSONObject queryShopSummary(OdsTMFXShopSummary param) {
    return odsTMFXShopSummaryDataService.queryShopSummary(param);
  }

  @Override
  public List<OdsTMFXShopSummary> queryShopSummaryExcel(OdsTMFXShopSummary param) {
    return odsTMFXShopSummaryDataService.queryShopSummaryExcel(param);
  }


  @Override
  public JSONObject queryIndustrySummary(OdsTMFXIndustrySummary param) {
    return odsTMFXIndustrySummaryDataService.queryIndustrySummary(param);
  }

  @Override
  public List<OdsTMFXIndustrySummary> queryIndustrySummaryExcel(OdsTMFXIndustrySummary param) {
    return odsTMFXIndustrySummaryDataService.queryIndustrySummaryExcel(param);
  }


}
