package com.haier.stock.services;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/5/23 16:56
 * @modificed by:
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.OrderInterfaceDataLog;
import com.haier.logistics.service.EisInterfaceDataLogApiService;
import com.haier.logistics.service.HpDispatchService;
import com.haier.logistics.service.OrderInterfaceDataLogService;
import com.haier.logistics.service.OrderRebackService;
import com.haier.logistics.service.OrderService;
import com.haier.shop.model.AllotNetPoint;
import com.haier.shop.model.DateFormatUtilNew;
import com.haier.shop.model.HpAllotNetPoint;
import com.haier.shop.model.HpSignTimeInterface;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.service.ShopOrderProductsService;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.model.SystemTo3wCbs;
import com.haier.stock.service.SystemTo3wCbsDaoService;
import com.haier.stock.services.Helper.ThreadHelper;
import com.haier.stock.util.SpringContextUtil;
import com.haier.stock.util.XmlUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("handleVomDataModel")
public class HandleVomDataModel {

  private static final int STATUS_ONE = 1;
  private static final int STATUS_TWO = 2;
  private static final int STATUS_THREE = 3;

  private static String INTERFACE_HP_HOP_SHOP_API = "hp_hop_shop_api";

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(HandleVomDataModel.class);

  @Autowired
  private SystemTo3wCbsDaoService systemTo3wCbsDaoService;
  @Autowired
  private HpDispatchService hpDispatchService;
  @Autowired
  private OrderInterfaceDataLogService orderInterfaceDataLogService;

  @Autowired
  private OrderService logisticsOrderService;

  @Autowired
  private OrderRebackService orderRebackService;

  @Autowired
  private EisInterfaceDataLogApiService eisInterfaceDataLogApiService;

  @Autowired
  private ShopOrderProductsService shopOrderProductsService;

  /**
   * 处理旧cbs推送的VOM数据
   *
   * @param type 表示获取不同数据，调用不同处理
   */
  public void autoHandleVomData(int type) {
    try {
      List<SystemTo3wCbs> to3wCbsList = systemTo3wCbsDaoService.queryList(type);
      if (null == to3wCbsList || to3wCbsList.isEmpty()) {
        log.info("处理旧cbs推送的VOM数据,没有需要处理的数据");
        return;
      }
      execute(to3wCbsList, type);
    } catch (Exception e) {
      log.error("获取旧cbs推送数据失败" + e.getMessage());
    }
  }

  private void execute(List<SystemTo3wCbs> to3wCbsList, int type) {
    ThreadHelper threadHelper = (ThreadHelper) SpringContextUtil.getBean("threadHelper");
    //加入多线程执行
    ExecuteVomData executeVomData = new ExecuteVomData(type);
    //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
    int splitSize = 100;
    int size = to3wCbsList.size();
    if (size > 10 && size <= 100) {
      splitSize = size / 2 + 1;
    }
    new MultiThreadTool<SystemTo3wCbs>()
        .processJobs(executeVomData, threadHelper, log, to3wCbsList,
            splitSize, 3);
  }

  private class ExecuteVomData implements IExcute {

    private int type;

    public ExecuteVomData(int type) {
      this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Object obj) {
      ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
      try {
        List<SystemTo3wCbs> to3wCbsList = (List<SystemTo3wCbs>) obj;
        if (null == to3wCbsList || to3wCbsList.isEmpty()) {
          return;
        }
        handleVomDataProcess(to3wCbsList, type);
      } catch (Exception e) {
        log.error("[OrderMarkBuilderModel]发生异常", e);
      } finally {
        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
      }
    }
  }

  public void handleVomDataProcess(List<SystemTo3wCbs> list, int type) {
    if (type == STATUS_ONE) {
      handleHpAllotNetPoint(list);
    }
    if (type == STATUS_TWO) {
      handleReservationGoodsDate(list);
    }
    if (type == STATUS_THREE) {
      handleAcceptHpTime(list);
    }
  }

  /**
   * 处理HP分配网点信息入口
   */
  private void handleHpAllotNetPoint(List<SystemTo3wCbs> list) {
    for (SystemTo3wCbs to3wCbs : list) {
      String requestXml = "";
      long startTime = System.currentTimeMillis();
      try {

        String jsonStr = to3wCbs.getContent();
        if (!"[".equals(jsonStr.substring(0, 1))) {
          jsonStr = "[" + jsonStr + "]";
        }
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        List<AllotNetPoint> netPoints = new ArrayList<AllotNetPoint>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < jsonArray.size(); i++) {
          JSONObject job = jsonArray.getJSONObject(i);
          AllotNetPoint allotNetPoint = new AllotNetPoint();
          allotNetPoint.setCUSTOMER_CODE((String) job.get("customerCode"));
          allotNetPoint.setORDER_NO((String) job.get("orderNo"));
          allotNetPoint.setCREATED_DATE((String) job.get("createdDate"));
          allotNetPoint.setPROC_REMARK((String) job.get("procRemark"));
          allotNetPoint.setENTER_TIME((String) job.get("enterTime"));
          allotNetPoint.setSB_DATE((String) job.get("sbDate"));
          allotNetPoint.setASSIGN_DATE((String) job.get("assignDate"));
          allotNetPoint.setId((Integer) job.get("id"));
          allotNetPoint.setStatus((Integer) job.get("status"));
          allotNetPoint.setMessage((String) job.get("message"));
          allotNetPoint.setApiLogsId((Integer) job.get("apiLogsId"));
          try {
            allotNetPoint.setCreateTime(sdf.parse(job.get("createTime").toString()));
            allotNetPoint.setUpdateTime(sdf.parse(job.get("updateTime").toString()));
          } catch (Exception e) {
            e.printStackTrace();
          }

          netPoints.add(allotNetPoint);
        }

        HpAllotNetPoint hpAllotNetPoint = new HpAllotNetPoint();
        hpAllotNetPoint.setItem(netPoints);
        requestXml = XmlUtil.jAXBmarshalString(hpAllotNetPoint);
      } catch (Exception e) {
        try {
          to3wCbs.setStatus(STATUS_TWO);
          to3wCbs.setErrorMessage("数据格式有误");
          setFailCounts(to3wCbs);
          this.systemTo3wCbsDaoService.updateById(to3wCbs);
        } catch (Exception exception) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + to3wCbs.getId() + "状态为处理失败");
        }
        continue;
      }
      //保存源数据
      ServiceResult<String> saveResult = hpDispatchService.saveNetPoint(requestXml);
      if (!saveResult.getSuccess()) {
        log.error("保存hp分配网点信息失败：" + saveResult.getMessage());
        //更新中转表状态
        try {
          to3wCbs.setStatus(STATUS_TWO);
          setFailCounts(to3wCbs);
          to3wCbs.setErrorMessage(saveResult.getMessage());
          this.systemTo3wCbsDaoService.updateById(to3wCbs);
        } catch (Exception e) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + to3wCbs.getId() + "状态为处理失败");
        }
      } else {
        //更新中转表状态
        try {
          to3wCbs.setStatus(STATUS_ONE);
          to3wCbs.setErrorMessage(saveResult.getResult());
          this.systemTo3wCbsDaoService.updateById(to3wCbs);
        } catch (Exception e) {
          log.error("更新中转数据失败，system_to_3w_cbs表中记录的id为" + to3wCbs.getId() + "状态为处理完成");
        }
      }
      long endTime = System.currentTimeMillis();
      long time = endTime - startTime;
      log.info("receive HP Allot netpoint,totalTime:" + time + "ms" + "system_to_3w_cbs表中记录的id为"
          + to3wCbs.getId());
    }

  }

  public void setFailCounts(SystemTo3wCbs to3wCbs){
    if (null == to3wCbs.getCounts()) {
      to3wCbs.setCounts(1);
    } else {
      int temp = to3wCbs.getCounts();
      to3wCbs.setCounts(++temp);
    }
  }

  /**
   * 处理HP系统回传预约送货时间
   */
  private void handleReservationGoodsDate(List<SystemTo3wCbs> ordersList) {
    String message =
        "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS + "</receiveFlag>";

    for (SystemTo3wCbs systemTo3wCbs : ordersList) {
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

  /**
   * 处理HP回传网点接单，网点出库，用户签收3个时间
   */
  private void handleAcceptHpTime(List<SystemTo3wCbs> ordersList) {
    for (SystemTo3wCbs to3wCbs : ordersList) {
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

  public String apiResponse(String errorCode, String msg, String row) {
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

  private boolean matchSku(String sku) {
    String[] skuArray = {"BB09B1094", "CB0N3V000", "DH1WB0D04", "HE082371", "FB1Q20M0K",
        "FC513D00L", "AAABT606V"};
    List<String> skuList = Arrays.asList(skuArray);
    if (skuList.contains(sku)) {
      return true;
    } else {
      return false;
    }
  }

}
