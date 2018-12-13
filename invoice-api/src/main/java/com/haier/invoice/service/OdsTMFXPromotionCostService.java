package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.OdsTMFXPromotionCost;
import java.util.List;

/**
 * 天猫分销-营销费用 Created by zdd on 2017/10/23.
 */
public interface OdsTMFXPromotionCostService {

  /**
   * 分页查询
   */
  JSONObject queryPromptionCost(
      OdsTMFXPromotionCost odsTMFXPromotionCost);

  /**
   * 查询导出
   */
  List<OdsTMFXPromotionCost> queryPromptionCostExcel(OdsTMFXPromotionCost map);

  /**
   * 新增/修改
   */
  ServiceResult<String> editPromotionCost(OdsTMFXPromotionCost odsTMFXPromotionCost);

  /*    *//**
   * 数据校验
   * @param list
   * @return
   *//*
     ServiceResult<String> checkInfo(List<OdsTMFXPromotionCostExcel> list);*/

  /**
   * 导入处理
   */
  ServiceResult<String> execExcel(List<OdsTMFXPromotionCost> list);

  /**
   * 删除
   */
  ServiceResult<String> deletePromotionCost(String ids);

  /**
   * 审核
   */
  ServiceResult<String> auditPromotionCost(String ids, String audit, String nickName);

}
