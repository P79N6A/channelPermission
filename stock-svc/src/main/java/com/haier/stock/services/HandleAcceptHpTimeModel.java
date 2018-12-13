package com.haier.stock.services;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.OrderInterfaceDataLog;
import com.haier.logistics.service.OrderInterfaceDataLogService;
import com.haier.shop.model.HpSignTimeInterface;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.model.SystemTo3wCbs;
import com.haier.logistics.service.OrderService;
import com.haier.stock.service.SystemTo3wCbsDaoService;
import com.haier.stock.services.Helper.ThreadHelper;
import com.haier.stock.util.SpringContextUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/28 18:21
 * @modificed by:
 */
@Service("handleAcceptHpTimeModel")
public class HandleAcceptHpTimeModel {

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(HandleAcceptHpTimeModel.class);

  private static final int STATUS_ONE = 1;
  private static final int STATUS_TWO = 2;
  private static final int STATUS_THREE = 3;
  @Autowired
  private SystemTo3wCbsDaoService systemTo3wCbsDaoService;

  @Autowired
  private OrderInterfaceDataLogService orderInterfaceDataLogService;

  @Autowired
  private OrderService logisticsOrderService;
  /**
   * 处理旧cbs推送的VOM数据
   *
   */
  public void autoHandleVomData() {
    try {
      List<SystemTo3wCbs> to3wCbsList = systemTo3wCbsDaoService.queryList(STATUS_THREE);
      if (null == to3wCbsList || to3wCbsList.isEmpty()) {
        log.info("处理旧cbs推送的用户签收数据,没有需要处理的初始数据，去执行失败数据重试操作");
        //失败重试数据
        to3wCbsList = systemTo3wCbsDaoService.queryRetryList(STATUS_THREE);
        if (CollectionUtils.isEmpty(to3wCbsList)){
          log.info("处理旧cbs推送的用户签收数据,没有需要处理的失败重试数据");
          return;
        }
      }
      execute(to3wCbsList);
    } catch (Exception e) {
      log.error("获取旧cbs推送数据失败" + e.getMessage());
    }
  }

  private void execute(List<SystemTo3wCbs> to3wCbsList) {
    ThreadHelper threadHelper = (ThreadHelper) SpringContextUtil.getBean("threadHelper");
    //加入多线程执行
    ExecuteAcceptHpTime executeAcceptHpTime = new ExecuteAcceptHpTime();
    //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
    int splitSize = 100;
    int size = to3wCbsList.size();
    if (size > 10 && size <= 100) {
      splitSize = size / 2 + 1;
    }
    new MultiThreadTool<SystemTo3wCbs>()
        .processJobs(executeAcceptHpTime, threadHelper, log, to3wCbsList,
            splitSize, 3);
  }

  private class ExecuteAcceptHpTime implements IExcute {

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Object obj) {
      ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
      try {
        List<SystemTo3wCbs> to3wCbsList = (List<SystemTo3wCbs>) obj;
        if (null == to3wCbsList || to3wCbsList.isEmpty()) {
          return;
        }
        handleAcceptHpTime(to3wCbsList);
      } catch (Exception e) {
        log.error("[OrderMarkBuilderModel]发生异常", e);
      } finally {
        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
      }
    }
  }

  /**
   * 处理HP回传网点接单，网点出库，用户签收3个时间
   */
  private void handleAcceptHpTime(List<SystemTo3wCbs> ordersList) {
    for (SystemTo3wCbs to3wCbs : ordersList) {
      if(to3wCbs.getInterfaceFlag() != 3){
        continue;
      }
      String content = to3wCbs.getContent();
      if (!to3wCbs.getContent().contains("[") && !to3wCbs.getContent().contains("]")) {
        content = "[" + to3wCbs.getContent() + "]";
      }
      List<HpSignTimeInterface> entityList = (List<HpSignTimeInterface>) JSONArray
          .parseArray(content, HpSignTimeInterface.class);
      String message;

      long startTime = System.currentTimeMillis();
      if (entityList == null || entityList.size() == 0) {
        log.error("HP回传网点接单，网点出库，用户签收数据parseDoc后为空");
        message = this.apiResponse("F", "数据为空", "");
        this.insertDataLog(JsonUtil.toJson(entityList), message,
            "HP回传网点接单，网点出库，用户签收数据parseDoc后为空");

        //更新中转数据表接口状态
        try {
          to3wCbs.setStatus(STATUS_TWO);
          setFailCounts(to3wCbs);
          to3wCbs.setErrorMessage("用户签收json数据转对象后为空");
          this.systemTo3wCbsDaoService.updateById(to3wCbs);
        } catch (Exception e) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + to3wCbs.getId() + "状态为处理失败");
        }

      } else {
        StringBuilder massageBuilder = new StringBuilder();
        StringBuilder vomMessage = new StringBuilder();

        for (HpSignTimeInterface entity : entityList) {
          //如果是HR 及为3W的数据 需要判断物料号是否满足所需要的
          //2018-06-26 3W的数据全部处理
          /*if ("HR".equals(entity.getWwwMark())) {
            try {
              //根据tbNo查询网单表的物料号
              OrderProducts orderProducts = shopOrderProductsService
                  .getOrderProductsByTbNo(entity.getTbNo());
              //如果查询到网单信息 且网单的sku不满足下面条件则不处理该数据
              if (orderProducts != null && !this.matchSku(orderProducts.getSku())) {
                //【接受HP回传接口】每一个网单记录一次日志 xinm 2016-6-14
                this.insertDataLogSingle(JsonUtil.toJson(entity), "该数据为3W下不需要处理的商品",
                    entity.getOrderSn());
                continue;
              }
            } catch (Exception e) {
              log.error("查询网单表sku失败" + to3wCbs.getId());
            }

          }*/
          ServiceResult<String> result = logisticsOrderService.acceptTimeFromHp(entity);
          String apiResponse = this.apiResponse(result.getSuccess() ? "S" : "F",
              result.getResult(), String.valueOf(entity.getRowId()));
          massageBuilder.append(apiResponse);
          vomMessage.append(
              "json中id为" + entity.getRowId() + "的处理结果为" + (result.getSuccess() ? "SUCCESS" : "FAIL")
                  + "," +
                  result.getResult() + ".");
          //【接受HP回传接口】每一个网单记录一次日志 xinm 2016-6-14
          this.insertDataLogSingle(JsonUtil.toJson(entity), apiResponse, entity.getOrderSn());
        }
        this.insertDataLog(JsonUtil.toJson(entityList), massageBuilder.toString(), "");

        //更新中转表状态
        try {
          to3wCbs.setStatus(vomMessage.toString().contains("FAIL") ? STATUS_TWO : STATUS_ONE);
          if (vomMessage.toString().contains("FAIL")){
            setFailCounts(to3wCbs);
          }
          to3wCbs.setErrorMessage(vomMessage.toString());
          this.systemTo3wCbsDaoService.updateById(to3wCbs);
        } catch (Exception e) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + to3wCbs.getId());
        }

        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        log.info("HP回传网点接单，网点出库，用户签收数据条数:" + entityList.size() + ",总共执行时间:" + time
            + "毫秒,平均每条" + time / entityList.size() + "毫秒");
      }
    }

  }

  private String apiResponse(String errorCode, String msg, String row) {
    return "<ITEM><FLAG>" + errorCode + "</FLAG><MSG>" + msg + "</MSG><ROWID>" + row
        + "</ROWID></ITEM>";
  }

  private void insertDataLog(String requestData, String responseDate, String errMessage) {
    OrderInterfaceDataLog dataLog = new OrderInterfaceDataLog();
    dataLog.setForeignKey("");
    dataLog.setInterfaceCode("receive_hpTime_sync");
    dataLog.setRequestTime(new Date());
    dataLog.setRequestData(requestData);
    dataLog.setErrorMessage(errMessage);
    dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
    dataLog.setResponseData(responseDate);
    dataLog.setResponseTime(new Date());
    dataLog.setElapsedTime(0L);
    orderInterfaceDataLogService.record(dataLog);
  }

  private void setFailCounts(SystemTo3wCbs to3wCbs){
    if (null == to3wCbs.getCounts()) {
      to3wCbs.setCounts(1);
    } else {
      int temp = to3wCbs.getCounts();
      to3wCbs.setCounts(++temp);
    }
  }

  private void insertDataLogSingle(String requestData, String responseDate, String foreignKey) {
    OrderInterfaceDataLog dataLog = new OrderInterfaceDataLog();
    dataLog.setForeignKey(foreignKey);
    dataLog.setInterfaceCode("receive_hpTime_sync");
    dataLog.setRequestData(requestData);
    dataLog.setErrorMessage("");
    dataLog.setRequestTime(new Date());
    dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
    dataLog.setResponseData(responseDate);
    dataLog.setResponseTime(new Date());
    dataLog.setElapsedTime(0L);
    orderInterfaceDataLogService.record(dataLog);
  }
}
