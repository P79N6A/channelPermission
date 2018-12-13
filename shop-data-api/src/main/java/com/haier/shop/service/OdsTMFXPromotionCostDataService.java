package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.OdsTMFXPromotionCost;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.service
 * @Date: Created in 2018/5/22 13:53
 * @Modified By:
 */
public interface OdsTMFXPromotionCostDataService {

  JSONObject queryPromotionCost(OdsTMFXPromotionCost param);

  List<OdsTMFXPromotionCost> queryPromotionCostExcel(OdsTMFXPromotionCost param);

  int updateByPrimaryKeySelective(OdsTMFXPromotionCost param);

  List<OdsTMFXPromotionCost> queryRepetition(OdsTMFXPromotionCost param);

  int insertSelective(OdsTMFXPromotionCost record);

  ServiceResult<String> addPromotionCost(OdsTMFXPromotionCost odsTMFXPromotionCost);

  int deleteByPrimaryKey(String id);

  ServiceResult<String> bulkImport(List<OdsTMFXPromotionCost> list);
}
