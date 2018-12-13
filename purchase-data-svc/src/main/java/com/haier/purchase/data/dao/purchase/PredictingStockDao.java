package com.haier.purchase.data.dao.purchase;

import com.haier.purchase.data.model.PredictingStockItem;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/4/23 17:33
 * @modificed by:
 */
public interface PredictingStockDao {

  /**
   * 获取预测备料查询
   */
  List<PredictingStockItem> findPredictingStockList(Map<String, Object> params);

  /**
   * 获取预测备料查询条数
   */
  Integer findPredictingStockListCNT(Map<String, Object> params);

  /**
   * 获取预测备料历史查询表单
   */
  public List<PredictingStockItem> findPredictingStockOrders(Map<String, Object> params);

  /**
   * 获取预测备料历史条数
   */
  Integer getPredictingStockHistoryListCNT(Map<String, Object> params);

  /**
   * 获取预测备料查看
   */
  List<PredictingStockItem> findPredictingStockDetails(Map<String, Object> params);

  /**
   * 获取预测备料条数
   */
  Integer findPredictingStockDetailCNT(Map<String, Object> params);

  /**
   * 获取预测备料提报
   */
  List<PredictingStockItem> findPredictingStockReport(Map<String, Object> params);

  /**
   * 不分渠道预测备料
   */
  List<PredictingStockItem> findPredictingStockNoChannel(Map<String, Object> params);

  /**
   * 预测备料汇总登陆
   */
  Integer insertsummaryt(PredictingStockItem summyData);

  /**
   * 预测备料状态反映
   */
  Integer updateStatus(PredictingStockItem statusData);

  /**
   * 预测备料汇总情报状态反映
   */
  Integer updateSummyStatus(PredictingStockItem statusData);

  /**
   * 根据渠道，产品组，物料编号和上报周年查询验证是否存在T+3预测备料信息表明细记录
   */
  Integer isExistPredictingStock(@Param("productGroupId") String productGroupId,
      @Param("materialsId") String materialsId,
      @Param("reportYearWeek") String reportYearWeek,
      @Param("edChannelId") String edChannelId);

  /**
   * 创建T+3预测备料信息表明细记录
   */
  Integer insert(PredictingStockItem predictingStockItem);

  /**
   * 更新T+3预测备料信息表明细记录
   */
  Integer update(PredictingStockItem predictingStockItem);

  /**
   * 根据id更新T+3预测备料信息表记录
   */
  Integer updatePredictionStockById(PredictingStockItem predictingStockItem);

  /**
   * 获得条数
   */
  public int getRowCnts();

  /**
   * 供应链保障分析表预测备料查询
   */
  List<PredictingStockItem> getPredictingStockForAnalyzeReport(Map<String, Object> params);

List<PredictingStockItem> findPredictingStockWithChannel(Map<String, Object> params);


}
