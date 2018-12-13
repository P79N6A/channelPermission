package com.haier.invoice.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.SNOdsTMFXPromotionCost;

/**
 * 苏宁分销-营销费用 Created by jzj on 2018/9/4.
 */
public interface SNOdsTMFXPromotionCostService {

  /**
   * 分页查询
   */
  JSONObject queryPromptionCost(SNOdsTMFXPromotionCost sNodsTMFXPromotionCost);

  /**
   * 查询导出
   */
  List<SNOdsTMFXPromotionCost> queryPromptionCostExcel(SNOdsTMFXPromotionCost map);

  /**
   * 新增/修改
   */
  ServiceResult<String> editPromotionCost(SNOdsTMFXPromotionCost odsTMFXPromotionCost);


  /**
   * 导入处理
   */
  ServiceResult<String> execExcel(List<SNOdsTMFXPromotionCost> list);

  /**
   * 删除
   */
  ServiceResult<String> deletePromotionCost(String ids);

  /**
   * 审核
   */
  ServiceResult<String> auditPromotionCost(String ids, String audit, String nickName);

}
