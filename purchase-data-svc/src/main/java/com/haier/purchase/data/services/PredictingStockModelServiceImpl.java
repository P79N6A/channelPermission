package com.haier.purchase.data.services;

import com.haier.common.BusinessException;
import com.haier.purchase.data.dao.purchase.PredictingStockDao;
import com.haier.purchase.data.model.PredictingStockItem;
import com.haier.purchase.data.service.PredictingStockModelService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/4/23 17:26
 * @modificed by:
 */
@Service
public class PredictingStockModelServiceImpl implements PredictingStockModelService {

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(PredictingStockModelServiceImpl.class);

  @Autowired
  private DataSourceTransactionManager transactionManager;

  @Autowired
  private PredictingStockDao predictingStockDao;

  private static int DELETE_FLAG_UNDELETE = 0;

  private static int FLOW_FLAG_NOTREPORT = 1;

  private static int FLOW_FLAG_REPORTED = 2;

  private static int FLOW_FLAG_REPORTFAILURE = 3;

  // 日期式样，年月日，用横杠离开，例如：2006-12-25，2008-08-08
  public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";


  /**
   * 获取预测备料历史查询表单
   */
  @Override
  public List<PredictingStockItem> getPredictingStockHistoryList(Map<String, Object> params) {
    return predictingStockDao.findPredictingStockOrders(params);

  }

  /**
   * 获取预测备料历史条数
   */
  @Override
  public Integer getPredictingStockHistoryListCNT(Map<String, Object> params) {
    return predictingStockDao.getPredictingStockHistoryListCNT(params);
  }

  @Override
  public List<PredictingStockItem> getPredictingStockItem(Map<String, Object> params) {
    return predictingStockDao.findPredictingStockOrders(params);

  }

  /**
   * 获取预测备料查看
   */
  @Override
  public List<PredictingStockItem> getPredictingStockDetail(Map<String, Object> params) {
    return predictingStockDao.findPredictingStockDetails(params);
  }

  /**
   * 获取预测备料条数
   */
  @Override
  public Integer getPredictingStockDetailCNT(Map<String, Object> params) {
    return predictingStockDao.findPredictingStockDetailCNT(params);
  }

  /**
   * 获取预测备料提报一览
   */
  @Override
  public List<PredictingStockItem> getPredictingStockReport(Map<String, Object> params) {
    return predictingStockDao.findPredictingStockReport(params);

  }

  /**
   * 预测备料手工提报
   */
  @Override
  public List<PredictingStockItem> reportByHand(Map<String, Object> params) {

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = transactionManager.getTransaction(def);
    try {
      // 不分渠道预测备料情报取得
      List<PredictingStockItem> summyData = predictingStockDao
          .findPredictingStockWithChannel(params);

      // 根据接口的返回结果，进行DB更新
      for (PredictingStockItem predictingStockItem : summyData) {
        predictingStockItem.setFlow_flag(FLOW_FLAG_NOTREPORT);

        //插入到T+3预测备料信息汇总表HAIER_T3_PREDICTION_SUMMARY_T
        predictingStockDao.insertsummaryt(predictingStockItem);

      }
      //提交事务
      transactionManager.commit(status);

      //返回执行成功
      return summyData;
    } catch (Exception ex) {
      //回滚事务
      transactionManager.rollback(status);
      //记录日志
      log.error("[PredictingStockModel][reportByHand]:手工提交失败:", ex);
      return null;
    }

  }

  /**
   * 预测备料状态更新
   */
  @Override
  public Boolean updStatus(PredictingStockItem predictingStockItem) {

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = transactionManager.getTransaction(def);
    try {

      //T+3预测备料信息汇总表HAIER_T3_PREDICTION_SUMMARY_T的状态同步更新
      predictingStockDao.updateSummyStatus(predictingStockItem);

      //T+3预测备料信息表HAIER_T3_PREDICTION_T的状态同步更新
      predictingStockDao.updateStatus(predictingStockItem);
      //提交事务
      transactionManager.commit(status);
      //返回执行成功
      return true;
    } catch (Exception ex) {
      //回滚事务
      transactionManager.rollback(status);
      //记录日志
      log.error("[PredictingStockModel][reportByHand]:手工提交失败:", ex);
      return false;
    }

  }

  /**
   * 获取预测备料查询
   */
  @Override
  public List<PredictingStockItem> getPredictingStockList(Map<String, Object> params) {
    return predictingStockDao.findPredictingStockList(params);
  }

  /**
   * 获取预测备料件数
   */
  @Override
  public Integer getPredictingStockListCNT(Map<String, Object> params) {
    return predictingStockDao.findPredictingStockListCNT(params);
  }

  /**
   * 根据渠道，产品组，物料编号和上报周年查询验证是否存在T+3预测备料信息表明细记录
   */
  @Override
  public Boolean isExistPredictingStock(String productGroupId, String materialsId,
      String reportYearWeek, String edChannelId) {
    int i = predictingStockDao.isExistPredictingStock(productGroupId, materialsId,
        reportYearWeek, edChannelId);
    return i > 0;
  }

  /**
   * 创建T+3预测备料信息表单
   */
  @Override
  public Boolean insertPredictingStock(PredictingStockItem predictingStockItem) {

    if (predictingStockItem == null) {
      throw new BusinessException(
          "[Predicting_Stock_model][PredictingStockItem]:predictingStockItem对象不能为空");
    }

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = transactionManager.getTransaction(def);
    try {
      //插入到T+3预测备料信息表haier_t3_prediction_t

      //提报状态（1：已导入）
      predictingStockItem.setFlow_flag(FLOW_FLAG_NOTREPORT);
      predictingStockItem.setDelete_flag(DELETE_FLAG_UNDELETE);
      predictingStockDao.insert(predictingStockItem);

      //提交事务
      transactionManager.commit(status);
      //返回执行成功
      return true;
    } catch (Exception ex) {
      //回滚事务
      transactionManager.rollback(status);
      //记录日志
      log.error("[Predicting_Stock_model][insertPredictingStock]:创建T+3预测备料信息表单发生未知异常:", ex);
            /* result
                 .setMessage("[Predicting_Stock_model][insertPredictingStock]:创建T+3预测备料信息表单发生未知异常:"
                             + ex.getMessage());*/
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * 更新T+3预测备料信息表单
   */
  @Override
  public Boolean updatePredictingStock(PredictingStockItem predictingStockItem) {

    if (predictingStockItem == null) {
      throw new BusinessException(
          "[Predicting_Stock_model][predictingStockItem]:predictingStockItem对象不能为空");
    }

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = transactionManager.getTransaction(def);
    try {
      //更新到T+3预测备料信息表haier_t3_prediction_t

      //提报状态（1：已导入）
      predictingStockItem.setFlow_flag(FLOW_FLAG_NOTREPORT);
      predictingStockDao.update(predictingStockItem);

      //提交事务
      transactionManager.commit(status);
      //返回执行成功
      return true;
    } catch (Exception ex) {
      //回滚事务
      transactionManager.rollback(status);
      //记录日志
      log.error("[Predicting_Stock_model][updatePredictingStock]:创建T+3预测备料信息表单发生未知异常:", ex);
      return false;
    }
  }

  /**
   * 获得条数
   */
  @Override
  public Integer getRowCnts() {
    return predictingStockDao.getRowCnts();
  }

  /**
   * 供应链保障分析表预测备料查询
   */
  @Override
  public List<PredictingStockItem> getPredictingStockForAnalyzeReport(Map<String, Object> params) {
    return predictingStockDao.getPredictingStockForAnalyzeReport(params);
  }

  /**
   * 滚动T+3预测备料信息
   */
  @Override
  public Boolean rollT3Data() {
    log.info("[PredictingStockModel][rollT3Data()]:计算滚动前后周数......");
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Calendar lastWeekCal = Calendar.getInstance();
    lastWeekCal.setTime(date);
    lastWeekCal.add(Calendar.DATE, -7);
    String current_week = getWeekOfYear_Sunday(df.format(date), null, "0");
    String last_week = getWeekOfYear_Sunday(df.format(lastWeekCal.getTime()), null, "0");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("report_year_week", last_week);
    params.put("flow_flag", "2");//滚动已提交状态的备料
    log.info("[PredictingStockModel][rollT3Data()]:查询需要滚动的预测备料数据......");
    List<PredictingStockItem> predictingStockList = predictingStockDao
        .findPredictingStockDetails(params);
    log.info("[PredictingStockModel][rollT3Data()]:循环插入滚动后的预测备料数据......");
    boolean b = false;
    for (PredictingStockItem predictingStockItem : predictingStockList) {
      predictingStockItem.setReport_year_week(current_week);
      predictingStockItem.setFlow_flag(1);
      predictingStockItem.setT3_require_prediction(predictingStockItem
          .getT4_require_prediction());
      predictingStockItem.setT4_require_prediction(predictingStockItem
          .getT5_require_prediction());
      predictingStockItem.setT5_require_prediction(predictingStockItem
          .getT6_require_prediction());
      predictingStockItem.setT6_require_prediction(predictingStockItem
          .getT7_require_prediction());
      predictingStockItem.setT7_require_prediction(predictingStockItem
          .getT8_require_prediction());
      predictingStockItem.setT8_require_prediction(predictingStockItem
          .getT9_require_prediction());
      predictingStockItem.setT9_require_prediction(predictingStockItem
          .getT10_require_prediction());
      predictingStockItem.setT10_require_prediction(predictingStockItem
          .getT11_require_prediction());
      predictingStockItem.setT11_require_prediction(predictingStockItem
          .getT12_require_prediction());
      predictingStockItem.setT12_require_prediction(predictingStockItem
          .getT13_require_prediction());
      predictingStockItem.setT13_require_prediction(0);
      predictingStockItem.setCreate_user("systemAuto");
      int a = predictingStockDao.insert(predictingStockItem);
      if (a > 0) {
        b = true;
      }
    }
    return b;
  }

  /**
   * 根据日期计算属于第几周(周四是一周的第一天)
   *
   * @param date 格式 yyyy-MM-dd dispflg:0 返回yyyyww;1 返回yyyy年ww周
   * @throws ParseException return 返回空表示异常，或日期为空
   */
  public static String getWeekOfYear_Sunday(String date, String pattern, String dispflg) {
    if (date == null) {
      return "";
    }
    if (pattern == null) {
      pattern = DATE_FORMAT_YYYY_MM_DD;
    }
    try {
      SimpleDateFormat df = new SimpleDateFormat(pattern);
      Calendar cal = Calendar.getInstance();
      cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置每周的第一天为星期日
      //cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
      cal.setMinimalDaysInFirstWeek(1); // 设置每周最少为1天
      cal.setTime(df.parse(date));
      if (cal.get(Calendar.DAY_OF_WEEK) > 4) {
        cal.add(Calendar.DATE, 7);
      }
      int year = cal.get(Calendar.YEAR);//获得当前年
      int month = cal.get(Calendar.MONTH);//获得当前月
      int week = cal.get(Calendar.WEEK_OF_YEAR);//获得周数
      if (month + 1 == 12 && week == 1) {
        year += 1;//如果当前月是12月并且周数是1，作为明年的第一周
      }
      if (dispflg.equals("0")) {
        return year + "" + (week < 10 ? "0" + week : week);//返回yyyyww格式
      } else if (dispflg.equals("1")) {
        return year + "年" + (week < 10 ? "0" + week : week) + "周";//返回yyyyww格式
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}
