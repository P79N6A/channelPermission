package com.haier.svc.services;

import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PredictingStockItem;
import com.haier.purchase.data.service.PredictingStockModelService;
import com.haier.svc.model.OMSOrderModel;
import com.haier.svc.service.PredictingStockService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author: gaohaiming
 * @description: 采购管理（海尔） impl
 * @date:created in 2018/4/23 16:58
 * @modificed by:
 */
@Service
public class PredictingStockServiceImpl implements PredictingStockService {

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(PredictingStockServiceImpl.class);

  @Autowired
  private PredictingStockModelService predictingStockModelService;

  @Autowired
  private OMSOrderModel omsOrderModel;


  @Override
  public ServiceResult<List<PredictingStockItem>> getPredictingStockList(
      Map<String, Object> params) {
    ServiceResult<List<PredictingStockItem>> result = new ServiceResult<List<PredictingStockItem>>();
    try {
      result.setResult(predictingStockModelService.getPredictingStockList(params));
      PagerInfo pi = new PagerInfo();
      pi.setRowsCount(predictingStockModelService.getPredictingStockListCNT(params));
      result.setPager(pi);
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      log.error("获取预测备料查看信息失败：", e);
    }
    return result;
  }

  /**
   * 更新T+3预测备料信息表单
   */
  @Override
  public ServiceResult<Boolean> updatePredictingStock(PredictingStockItem predictingStockItem) {
    Assert.notNull(predictingStockModelService, "Property 'predictingStockModel' is required.");

    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    try {
      result.setResult(predictingStockModelService.updatePredictingStock(predictingStockItem));
    } catch (BusinessException be) {
      result.setSuccess(false);
      result.setMessage(be.getMessage());
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage("T+3预测备料信息表单更新失败，" + e.getMessage());
      log.error("[Predicting_Stock_service][updatePredictingStock]:T+3预测备料信息表单更新失败", e);
    }
    return result;
  }

  @Override
  public ServiceResult<List<PredictingStockItem>> getPredictingStockItem(
      Map<String, Object> params) {
    ServiceResult<List<PredictingStockItem>> result = new ServiceResult<List<PredictingStockItem>>();
    try {
      result.setResult(predictingStockModelService.getPredictingStockItem(params));
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      log.error("获取预测备料历史表单失败：", e);
    }
    return result;
  }

  /**
   * 获取预测备料查看
   */
  @Override
  public ServiceResult<List<PredictingStockItem>> getPredictingStockDetail(
      Map<String, Object> params) {
    ServiceResult<List<PredictingStockItem>> result = new ServiceResult<List<PredictingStockItem>>();
    try {
      result.setResult(predictingStockModelService.getPredictingStockDetail(params));
      PagerInfo pi = new PagerInfo();
      pi.setRowsCount(predictingStockModelService.getPredictingStockDetailCNT(params));
      result.setPager(pi);
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      log.error("获取预测备料查看信息失败：", e);
    }
    return result;
  }

  /**
   * 获取预测备料历史查询表单
   */
  @Override
  public ServiceResult<List<PredictingStockItem>> getPredictingStockHistoryList(
      Map<String, Object> params) {
    ServiceResult<List<PredictingStockItem>> result = new ServiceResult<List<PredictingStockItem>>();
    try {
      result.setResult(predictingStockModelService.getPredictingStockHistoryList(params));
      PagerInfo pi = new PagerInfo();
      pi.setRowsCount(predictingStockModelService.getPredictingStockHistoryListCNT(params));
      result.setPager(pi);
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      log.error("获取预测备料历史查询表单失败：", e);
    }
    return result;
  }

  /**
   * 获取预测备料提报一览
   */
  @Override
  public ServiceResult<List<PredictingStockItem>> getPredictingStockReport(
      Map<String, Object> params) {
    ServiceResult<List<PredictingStockItem>> result = new ServiceResult<List<PredictingStockItem>>();
    try {
      result.setResult(predictingStockModelService.getPredictingStockReport(params));
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      log.error("获取预测备料信息失败：", e);
    }
    return result;
  }

  /**
   * 预测备料手工提报
   */
  @Override
  public ServiceResult<String> reportByHand(Map<String, Object> params) {

    ServiceResult<String> result = new ServiceResult<String>();
    String returnString = "";
    try {
      // 手工提交
      List<PredictingStockItem> summyData = predictingStockModelService.reportByHand(params);
      if (summyData != null) {
        // 向OMS提报
        List<com.haier.svc.bean.PredictingStockItem> changeSummyData =
            new ArrayList<com.haier.svc.bean.PredictingStockItem>();
        for (PredictingStockItem predictingStockItem : summyData) {
          com.haier.svc.bean.PredictingStockItem stockItem = new com.haier.svc.bean.PredictingStockItem();
          BeanUtils.copyProperties(predictingStockItem, stockItem);
          changeSummyData.add(stockItem);
        }
        omsOrderModel.syncT3OrderToOMS(changeSummyData);

        // 提交成功件数
        int successCount = 0;
        // 提交失败数
        int failureCount = 0;
        // 最新状态更新
        for (com.haier.svc.bean.PredictingStockItem predictingStockItem : changeSummyData) {
          // 画面表示信息
          if (predictingStockItem.getFlow_flag() == null
              || 3 == predictingStockItem.getFlow_flag()) {
            failureCount++;
          } else {
            successCount++;
          }
          PredictingStockItem psi = new PredictingStockItem();
          BeanUtils.copyProperties(predictingStockItem, psi);
          // 状态更新
          predictingStockModelService.updStatus(psi);
        }

        returnString = "提交成功数：" + successCount + "件\n";
        returnString = returnString + "提交失败数：" + failureCount + "件";
        result.setResult(returnString);
      } else {
        result.setSuccess(false);
      }
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      log.error("手工提报预测备料信息失败：", e);
    }
    return result;
  }

  /**
   * 根据渠道，产品组，物料编号和上报周年查询验证是否存在T+3预测备料信息表明细记录
   *
   * java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public ServiceResult<Boolean> isExistPredictingStock(String productGroupId, String materialsId,
      String reportYearWeek, String edChannelId) {
    Assert.notNull(predictingStockModelService, "Property 'PredictingStockModel' is required.");

    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    try {
      result.setResult(predictingStockModelService.isExistPredictingStock(productGroupId,
          materialsId, reportYearWeek, edChannelId));
    } catch (BusinessException be) {
      result.setSuccess(false);
      result.setMessage(be.getMessage());
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage("根据渠道，产品组，物料编号和上报周年查询验证是否存在T+3预测备料信息表明细记录失败！，" + e.getMessage());
      log.error(
          "[Predicting_Stock_service][isExistPredictingStock]:根据渠道，产品组，物料编号和上报周年查询验证是否存在T+3预测备料信息表明细记录失败",
          e);
    }
    return result;
  }

  /**
   * 创建T+3预测备料信息表单
   */
  @Override
  public ServiceResult<Boolean> insertPredictingStock(PredictingStockItem predictingStockItem) {
    Assert.notNull(predictingStockModelService, "Property 'predictingStockModel' is required.");

    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    try {
      result.setResult(predictingStockModelService.insertPredictingStock(predictingStockItem));
    } catch (BusinessException be) {
      result.setSuccess(false);
      result.setMessage(be.getMessage());
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage("T+3预测备料信息表单创建失败，" + e.getMessage());
      log.error("[Predicting_Stock_service][insertPredictingStock]:T+3预测备料信息表单创建失败", e);
    }
    return result;
  }

  /**
   * 获得明细条数
   */
  @Override
  public ServiceResult<Integer> getRowCnts() {
    ServiceResult<Integer> result = new ServiceResult<Integer>();
    try {
      result.setResult(predictingStockModelService.getRowCnts());
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      log.error("获取明细条数失败：", e);
    }
    return result;
  }

  /**
   * 供应链保障分析表预测备料查询
   */
  @Override
  public ServiceResult<List<PredictingStockItem>> getPredictingStockForAnalyzeReport(
      Map<String, Object> params) {
    ServiceResult<List<PredictingStockItem>> result = new ServiceResult<List<PredictingStockItem>>();
    try {
      result.setResult(predictingStockModelService.getPredictingStockForAnalyzeReport(params));
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage(e.getMessage());
      log.error("供应链保障分析表预测备料查询失败：", e);
    }
    return result;
  }

  /**
   * 滚动T+3预测备料信息
   */
  @Override
  public ServiceResult<Boolean> rollT3Data() {

    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    try {
      result.setResult(predictingStockModelService.rollT3Data());
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage("滚动T+3预测备料信息失败，" + e.getMessage());
      log.error("[PredictingStockServiceImpl][rollT3Data]:滚动T+3预测备料信息失败", e);
    }
    return result;
  }
}
