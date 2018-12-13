package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PredictingStockItem;
import java.util.List;
import java.util.Map;

/**
 * @author: gaohaiming
 * @description: 采购管理（海尔） api
 * @date:created in 2018/4/23 16:49
 * @modificed by:
 */
public interface PredictingStockService {

  /**
   * 获取预测备料List
   *
   * @param params 入参
   * @return 结果数据
   */
  ServiceResult<List<PredictingStockItem>> getPredictingStockList(Map<String, Object> params);

  /**
   * 更新T+3预测备料信息表单
   */
  ServiceResult<Boolean> updatePredictingStock(PredictingStockItem predictingStockItem);

  /**
   * 获取预测备料历史查询表单
   */
  ServiceResult<List<PredictingStockItem>> getPredictingStockHistoryList(
      Map<String, Object> params);

  /**
   * 获取预测备料表单
   */
  ServiceResult<List<PredictingStockItem>> getPredictingStockItem(Map<String, Object> params);

  /**
   * 获取预测备料查看
   */
  ServiceResult<List<PredictingStockItem>> getPredictingStockDetail(Map<String, Object> params);

  /**
   * 获取预测备料提报一览
   */
  ServiceResult<List<PredictingStockItem>> getPredictingStockReport(Map<String, Object> params);

  /**
   * 预测备料手工提报
   */
  ServiceResult<String> reportByHand(Map<String, Object> params);


  /**
   * 根据渠道，产品组，物料编号和上报周年查询验证是否存在T+3预测备料信息表明细记录
   */
  ServiceResult<Boolean> isExistPredictingStock(String productGroupId, String materialsId,
      String reportYearWeek, String edChannelId);

  /**
   * 创建T+3预测备料信息表单
   */
  ServiceResult<Boolean> insertPredictingStock(PredictingStockItem predictingStockItem);


  /**
   * 返回总条数
   */
  ServiceResult<Integer> getRowCnts();

  /**
   * 供应链保障分析表预测备料查询
   */
  public ServiceResult<List<PredictingStockItem>> getPredictingStockForAnalyzeReport(
      Map<String, Object> params);

  /**
   * 滚动T+3预测备料信息
   */
  ServiceResult<Boolean> rollT3Data();

}
