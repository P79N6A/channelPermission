package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.shop.dao.settleCenter.OdsTMFXShopSummaryDao;
import com.haier.shop.model.OdsTMFXShopSummary;
import com.haier.shop.service.OdsTMFXShopSummaryDataService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.services
 * @Date: Created in 2018/5/29 15:10
 * @Modified By:
 */
@Service
public class OdsTMFXShopSummaryDataServiceImpl implements OdsTMFXShopSummaryDataService {

  @Resource
  private OdsTMFXShopSummaryDao odsTMFXShopSummaryDao;

  @Override
  public JSONObject queryShopSummary(OdsTMFXShopSummary param) {
    JSONObject result = new JSONObject();
    result.put("total", odsTMFXShopSummaryDao.queryShopSummaryCount(param));
    result.put("rows", odsTMFXShopSummaryDao.queryShopSummaryList(param));
    return result;
  }

  @Override
  public List<OdsTMFXShopSummary> queryShopSummaryExcel(OdsTMFXShopSummary param) {
    return odsTMFXShopSummaryDao.queryShopSummaryExcel(param);
  }
}
