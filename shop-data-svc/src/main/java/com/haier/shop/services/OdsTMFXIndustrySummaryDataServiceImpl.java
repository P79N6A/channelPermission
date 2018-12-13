package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.shop.dao.settleCenter.OdsTMFXIndustrySummaryDao;
import com.haier.shop.model.OdsTMFXIndustrySummary;
import com.haier.shop.service.OdsTMFXIndustrySummaryDataService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.services
 * @Date: Created in 2018/5/29 15:08
 * @Modified By:
 */
@Service
public class OdsTMFXIndustrySummaryDataServiceImpl implements OdsTMFXIndustrySummaryDataService {

  @Resource
  private OdsTMFXIndustrySummaryDao odsTMFXIndustrySummaryDao;


  @Override
  public JSONObject queryIndustrySummary(OdsTMFXIndustrySummary param) {
    JSONObject result = new JSONObject();
    result.put("total", odsTMFXIndustrySummaryDao.queryIndustrySummaryCount(param));
    result.put("rows", odsTMFXIndustrySummaryDao.queryIndustrySummaryList(param));
    return result;
  }

  @Override
  public List<OdsTMFXIndustrySummary> queryIndustrySummaryExcel(OdsTMFXIndustrySummary param) {
    return odsTMFXIndustrySummaryDao.queryIndustrySummaryExcel(param);
  }
}
