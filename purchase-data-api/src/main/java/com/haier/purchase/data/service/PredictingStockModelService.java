package com.haier.purchase.data.service;

import com.haier.purchase.data.model.PredictingStockItem;
import java.util.List;
import java.util.Map;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/4/23 17:23
 * @modificed by:
 */
public interface PredictingStockModelService {

  /**
   * 获取预测备料查询
   */
  public List<PredictingStockItem> getPredictingStockList(Map<String, Object> params);

  /**
   * 获取预测备料历史条数
   * @param params
   * @return
   */
  public Integer getPredictingStockHistoryListCNT(Map<String, Object> params);

  public List<PredictingStockItem> getPredictingStockItem(Map<String, Object> params);

  /**
   * 获取预测备料件数
   * @param params
   * @return
   */
  public Integer getPredictingStockListCNT(Map<String, Object> params);

  /**
   * 更新T+3预测备料信息表单
   * @param predictingStockItem
   * @return
   */
  public Boolean updatePredictingStock(PredictingStockItem predictingStockItem);

  /**
   * 获取预测备料历史查询表单
   * @param params
   * @return
   */
  public List<PredictingStockItem> getPredictingStockHistoryList(Map<String, Object> params);

  /**
   * 获取预测备料查看
   * @param params
   * @return
   */
  public List<PredictingStockItem> getPredictingStockDetail(Map<String, Object> params);

  /**
   * 获取预测备料条数
   * @param params
   * @return
   */
  public Integer getPredictingStockDetailCNT(Map<String, Object> params);

  /**
   * 获取预测备料提报一览
   * @param params
   * @return
   */
  public List<PredictingStockItem> getPredictingStockReport(Map<String, Object> params);

  /**
   * 预测备料手工提报
   * @param params
   * @return
   */
  public List<PredictingStockItem> reportByHand(Map<String, Object> params);

  /**
   * 预测备料状态更新
   * @param predictingStockItem
   * @return
   */
  public Boolean updStatus(PredictingStockItem predictingStockItem);

  /**
   * 根据渠道，产品组，物料编号和上报周年查询验证是否存在T+3预测备料信息表明细记录
   * @param productGroupId
   * @param materialsId
   * @param reportYearWeek
   * @param edChannelId
   * @return
   */
  public Boolean isExistPredictingStock(String productGroupId, String materialsId,
      String reportYearWeek, String edChannelId);

  /**
   * 创建T+3预测备料信息表单
   * @param predictingStockItem
   * @return
   */
  public Boolean insertPredictingStock(PredictingStockItem predictingStockItem);

  /**
   * 获得条数
   * @return
   */
  public Integer getRowCnts();
  /**
   * 供应链保障分析表预测备料查询
   * @param params
   * @return
   */
  public List<PredictingStockItem> getPredictingStockForAnalyzeReport(Map<String, Object> params);

  /**
   * 滚动T+3预测备料信息
   */
  public Boolean rollT3Data();
}

