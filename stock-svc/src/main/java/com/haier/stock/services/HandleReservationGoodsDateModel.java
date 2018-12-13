package com.haier.stock.services;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.logistics.service.EisInterfaceDataLogApiService;
import com.haier.logistics.service.OrderRebackService;
import com.haier.shop.model.DateFormatUtilNew;
import com.haier.shop.model.OrderProductsNew;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.model.SystemTo3wCbs;
import com.haier.stock.service.SystemTo3wCbsDaoService;
import com.haier.stock.services.Helper.ThreadHelper;
import com.haier.stock.util.SpringContextUtil;
import com.sun.org.apache.bcel.internal.generic.I2F;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/28 18:14
 * @modificed by:
 */
@Service("handleReservationGoodsDateModel")
public class HandleReservationGoodsDateModel {

  private static final int STATUS_ONE = 1;
  private static final int STATUS_TWO = 2;
  private static String INTERFACE_HP_HOP_SHOP_API = "hp_hop_shop_api";

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(HandleReservationGoodsDateModel.class);
  @Autowired
  private SystemTo3wCbsDaoService systemTo3wCbsDaoService;
  @Autowired
  private OrderRebackService orderRebackService;
  @Autowired
  private EisInterfaceDataLogApiService eisInterfaceDataLogApiService;
  /**
   * 处理旧cbs推送的VOM数据
   *
   */
  public void autoHandleReservationGoodsDate() {
    try {
      List<SystemTo3wCbs> to3wCbsList = systemTo3wCbsDaoService.queryList(STATUS_TWO);
      if (null == to3wCbsList || to3wCbsList.isEmpty()) {
        log.info("处理旧cbs推送的HP系统回传预约送货时间数据,没有需要处理的初始数据");
        to3wCbsList = systemTo3wCbsDaoService.queryRetryList(STATUS_TWO);
        if (CollectionUtils.isEmpty(to3wCbsList)){
          log.info("处理旧cbs推送的HP系统回传预约送货时间数据,没有需要处理的失败重试数据");
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
    ExecuteReservationGoods executeReservationGoods = new ExecuteReservationGoods();
    //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
    int splitSize = 100;
    int size = to3wCbsList.size();
    if (size > 10 && size <= 100) {
      splitSize = size / 2 + 1;
    }
    new MultiThreadTool<SystemTo3wCbs>()
        .processJobs(executeReservationGoods, threadHelper, log, to3wCbsList,
            splitSize, 3);
  }

  private class ExecuteReservationGoods implements IExcute {


    @SuppressWarnings("unchecked")
    @Override
    public void execute(Object obj) {
      ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
      try {
        List<SystemTo3wCbs> to3wCbsList = (List<SystemTo3wCbs>) obj;
        if (null == to3wCbsList || to3wCbsList.isEmpty()) {
          return;
        }
        handleReservationGoodsDate(to3wCbsList);
      } catch (Exception e) {
        log.error("[OrderMarkBuilderModel]发生异常", e);
      } finally {
        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
      }
    }
  }

  /**
   * 处理HP系统回传预约送货时间
   */
  private void handleReservationGoodsDate(List<SystemTo3wCbs> ordersList) {
    String message =
        "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS + "</receiveFlag>";

    for (SystemTo3wCbs systemTo3wCbs : ordersList) {
      if(systemTo3wCbs.getInterfaceFlag() != 2){
        continue;
      }

      StringBuffer msg = new StringBuffer();
      boolean status = true;
      Long startTime = System.currentTimeMillis();
      String content = systemTo3wCbs.getContent();
      if (!systemTo3wCbs.getContent().contains("[") && !systemTo3wCbs.getContent().contains("]")) {
        content = "[" + systemTo3wCbs.getContent() + "]";
      }
      List<OrderProductsNew> orderProductsNews = (List<OrderProductsNew>) JSONArray
          .parseArray(content, OrderProductsNew.class);

      if (orderProductsNews != null && orderProductsNews.size() > 0) {
        for (OrderProductsNew orderProducts : orderProductsNews) {
          OrderProductsNew orderProduct = orderRebackService
              .getOrderProductsByCOrderSn(orderProducts.getCOrderSn());
          if (orderProduct == null) {
            msg.append("网单号:" + orderProducts.getCOrderSn());
            continue;
          }
          //保存预约送货时间
          orderProduct.setHpReservationDate(orderProducts.getHpReservationDate());
          //保存订单日志
          boolean flag = orderRebackService.saveHpReservationDateRelation(
              orderProduct,
              "HP系统回传预约送货时间：" + DateFormatUtilNew.formatTime(orderProduct.getHpReservationDate()),
              "HP回传网单数据");
          //发送短信
          if (flag) {
            orderRebackService.sendSms(orderProduct);
          } else {
            status = false;
            msg.append("网单号:" + orderProducts.getCOrderSn());
          }
          //【接受HP回传接口】每一个网单记录一次日志 xinm 2016-6-14
          this.recordLog(JsonUtil.toJson(orderProducts), message,
              System.currentTimeMillis() - startTime, INTERFACE_HP_HOP_SHOP_API,
              orderProducts.getCOrderSn());
        }

        if (status) {
          systemTo3wCbs.setStatus(STATUS_ONE);
          systemTo3wCbs.setErrorMessage(msg.toString());
        } else {
          systemTo3wCbs.setStatus(STATUS_TWO);
          setFailCounts(systemTo3wCbs);
          systemTo3wCbs.setErrorMessage(msg.toString());
        }
        try {
          systemTo3wCbsDaoService.updateById(systemTo3wCbs);
        } catch (Exception e) {
          log.error("更新数据中转临时中间表（OMS回传CBS中转）发生异常：" + e);
        }

      } else {
        //更新中转数据表接口状态
        try {
          systemTo3wCbs.setStatus(STATUS_TWO);
          setFailCounts(systemTo3wCbs);
          systemTo3wCbs.setErrorMessage("预约、改约回传CBS数据为空");
          this.systemTo3wCbsDaoService.updateById(systemTo3wCbs);
        } catch (Exception e) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + systemTo3wCbs.getId() + "状态为处理失败");
        }
        message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_ERROR
            + "</receiveFlag>";
      }
      this.recordLog(systemTo3wCbs.getContent(), message, System.currentTimeMillis() - startTime,
          INTERFACE_HP_HOP_SHOP_API);

    }
  }
  /**
   * 记录接口日志
   */
  private void recordLog(String requestData, String responseData, Long elapsedTime,
      String interfaceCode, String cOrderSn) {
    //记录接口日志
    try {
      EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
      dataLog.setForeignKey(cOrderSn);
      dataLog.setInterfaceCode(interfaceCode);
      dataLog.setRequestData(requestData);
      dataLog.setRequestTime(new Date());
      dataLog.setErrorMessage("");
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
      dataLog.setResponseData(responseData);
      dataLog.setResponseTime(new Date());
      dataLog.setElapsedTime(elapsedTime);
      eisInterfaceDataLogApiService.record(dataLog);
    } catch (Exception e) {
      log.error("recordLog:接口发生异常，接口名称是:" + interfaceCode + "记录接口日志失败:" + e);
    }
  }

  /**
   * 记录接口日志
   */
  private void recordLog(String requestData, String responseData, Long elapsedTime,
      String interfaceCode) {
    //记录接口日志
    try {
      EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
      dataLog.setForeignKey("");
      dataLog.setInterfaceCode(interfaceCode);
      dataLog.setRequestData(requestData);
      dataLog.setRequestTime(new Date());
      dataLog.setErrorMessage("");
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
      dataLog.setResponseData(responseData);
      dataLog.setResponseTime(new Date());
      dataLog.setElapsedTime(elapsedTime);
      eisInterfaceDataLogApiService.record(dataLog);
    } catch (Exception e) {
      log.error("recordLog:接口发生异常，接口名称是:" + interfaceCode + "记录接口日志失败:" + e);
    }
  }

  private void setFailCounts(SystemTo3wCbs to3wCbs){
    if (null == to3wCbs.getCounts()) {
      to3wCbs.setCounts(1);
    } else {
      int temp = to3wCbs.getCounts();
      to3wCbs.setCounts(++temp);
    }
  }
}
