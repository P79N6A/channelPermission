package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.shop.dao.settleCenter.OdsTMFXRebatesSummaryDao;
import com.haier.shop.model.OdsTMFXRebatesSummary;
import com.haier.shop.service.OdsTMFXRebatesSummaryDataService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.services
 * @Date: Created in 2018/5/29 15:04
 * @Modified By:
 */
@Service
public class OdsTMFXRebatesSummaryDataServiceImpl implements OdsTMFXRebatesSummaryDataService {

  @Resource
  private OdsTMFXRebatesSummaryDao odsTMFXRebatesSummaryDao;

  @Override
  public JSONObject queryRebatesSummary(OdsTMFXRebatesSummary param) {
    JSONObject result = new JSONObject();
    result.put("total", odsTMFXRebatesSummaryDao.queryRebatesSummaryCount(param));
    result.put("rows", odsTMFXRebatesSummaryDao.queryRebatesSummaryList(param));
    return result;
  }

  @Override
  public List<OdsTMFXRebatesSummary> queryRebatesSummaryExcel(OdsTMFXRebatesSummary param) {
    return odsTMFXRebatesSummaryDao.queryRebatesSummaryExcel(param);
  }
}
