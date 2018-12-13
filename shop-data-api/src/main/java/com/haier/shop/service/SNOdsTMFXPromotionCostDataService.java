package com.haier.shop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.SNOdsTMFXPromotionCost;

/**
 * @Author: wsh
 * @Description:苏宁分销
 * @ProjectName: svc
 * @PackageName: com.haier.shop.service
 * @Date: Created in 2018/5/22 13:53
 * @Modified By:
 */
public interface SNOdsTMFXPromotionCostDataService {

  JSONObject queryPromotionCost(SNOdsTMFXPromotionCost param);

  List<SNOdsTMFXPromotionCost> queryPromotionCostExcel(SNOdsTMFXPromotionCost param);

  int updateByPrimaryKeySelective(SNOdsTMFXPromotionCost param);

  List<SNOdsTMFXPromotionCost> queryRepetition(SNOdsTMFXPromotionCost param);

  int insertSelective(SNOdsTMFXPromotionCost record);

  ServiceResult<String> addPromotionCost(SNOdsTMFXPromotionCost odsTMFXPromotionCost);

  int deleteByPrimaryKey(String id);

  ServiceResult<String> bulkImport(List<SNOdsTMFXPromotionCost> list);
}
