package com.haier.stock.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseItemStatus;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderQueue4Daily;
import com.haier.purchase.data.service.PurchaseOrderService;
import com.haier.shop.model.LesShippingInfos;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.LesShippingInfosService;
import com.haier.stock.eai.getTidan.ZWDTABLE2;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.EisProduceDailyService;
import com.haier.stock.service.OrderService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.services.Helper.EAIHandler;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EisProduceDailyServiceImpl implements EisProduceDailyService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(EisProduceDailyServiceImpl.class);

  @Resource
  private EAIHandler eaiHandler;

  @Resource
  private PurchaseOrderService purchaseOrderService;

  @Value("${url.vomSynOrderEaiUrl}")
  private String queryFromOmsUrl;

  private static final int LOAD_ORDER_PRODUCT_SIZE = 100;

  @Resource
  private OrderService orderService;

  @Resource
  private StockCommonService stockCommonService;

  @Resource
  private LesShippingInfosService lesShippingInfosService;

  /**
   * 更新日日单采购的生产信息
   *
   * @return boolean
   */
  @Override
  public ServiceResult<Boolean> updateProduceInformationFromOMS() {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    List<PurchaseOrderQueue4Daily> queues = getPurchaseOrderQueue4Daily(
        PurchaseOrderQueue4Daily.STATUS_PRODUCE);
    if (queues.size() <= 0) {
      LOGGER.info("更新日日单采购单的生产信息完成：没有记录");
      result.setResult(true);
      return result;
    }
    for (PurchaseOrderQueue4Daily queue : queues) {
      try {
        //根据日日单网单号从 oms 获取生产信息
        List<Map<String, String>> params = eaiHandler.queryProduceInformationFromOms(
            queue.getRefNo(), queryFromOmsUrl);
        if (params.size() <= 0) {//未获取到 continue
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(queue.getRefNo() + " 从OMS未获取到记录");
          }
          continue;
        }

        Map<String, String> param = params.get(0);
        String omsStatus = param.get("statusTypeName");

        if (queue.getOmsStatus().equals(omsStatus)) {//状态未变化 continue
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(queue.getRefNo() + " 对应OMS订单状态（" + queue.getOmsStatus()
                + "）未变化，不更新");
          }
          continue;
        }

        //更新状态信息
        ServiceResult<Boolean> rs = purchaseOrderService.updatePurchaseProduceDailyFromOms(
            queue, param);
        if (!rs.getSuccess() || !rs.getResult()) {
          purchaseOrderService.updatePurchaseOrderQueue4DailyMessage(queue.getId(),
              rs.getMessage());
          throw new BusinessException(rs.getMessage());
        }
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("更新日日单采购单（" + queue.getRefNo() + "）成功");
        }
      } catch (Exception e) {
        LOGGER.error("更新日日单采购单（" + queue.getRefNo() + "）生产信息失败：" + e);
      }
    }
    return result;
  }

  /**
   * 获取日日单队列，通过指定的{@code status}
   *
   * @param status status
   * @return 日日单队列
   */
  private List<PurchaseOrderQueue4Daily> getPurchaseOrderQueue4Daily(int status) {
    ServiceResult<List<PurchaseOrderQueue4Daily>> result = purchaseOrderService
        .getPurchaseOrderQueue4Daily(status);
    if (!result.getSuccess()) {
      throw new BusinessException("获取日日单采购单队列失败：" + result.getMessage());
    }
    return result.getResult();
  }

  /**
   * 生成日日单采购单队列
   *
   * @return boolean
   */
  @Override
  public ServiceResult<Boolean> createPurchaseOrderQueue4Daily() {
    long startTime = System.currentTimeMillis();
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    int minId = 0;
    long createQueueSize = 0;
    while (true) {
      List<OrderProductsNew> orderProducts = getOrderProductByPDStatusProduction(minId,
          LOAD_ORDER_PRODUCT_SIZE);
      if (orderProducts.size() <= 0) {
        break;
      }
      minId = orderProducts.get(orderProducts.size() - 1).getId();
      excludeTheExist(orderProducts);
      if (orderProducts.size() <= 0) {
        continue;
      }
      for (OrderProductsNew orderProduct : orderProducts) {
        PurchaseOrderQueue4Daily queue4Daily = new PurchaseOrderQueue4Daily();
        queue4Daily.setRefNo(orderProduct.getCOrderSn());
        queue4Daily.setOmsNo(orderProduct.getOmsOrderSn());
        queue4Daily.setStatus(PurchaseOrderQueue4Daily.STATUS_NEW);
        queue4Daily.setOrderStatus(PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING);
        queue4Daily
            .setDetailInfoSyncStatus(PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP1);
        queue4Daily.setOmsStatus("未获取");
        queue4Daily.setAddTime(new Date());
        ServiceResult<Boolean> rs = purchaseOrderService.addToPurchaseOrderQueue4Daily(queue4Daily);
        if (!rs.getSuccess())
          LOGGER.error("生成日日单采购单（" + orderProduct.getCOrderSn() + "）队列失败："
              + rs.getMessage());
        else
          createQueueSize++;
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("生成队列：" + orderProduct.getCOrderSn());
      }
    }
    LOGGER.info("根据日日单网单生成日日单采购单队列完成，共添加" + createQueueSize + "条记录，用时"
        + (System.currentTimeMillis() - startTime) + "ms");
    return result;
  }

  /**
   * 获取状态为生产中（10）的日日单列表
   *
   * @param minId 获取条件为 id > minId
   * @param size  获取的最大数量
   * @return 日日单列表
   */
  private List<OrderProductsNew> getOrderProductByPDStatusProduction(int minId, int size) {
    ServiceResult<List<OrderProductsNew>> result = orderService.getByPdOrderStatusPaging(
        OrderProductsNew.RRSSTATUS_PRODUCTION, minId, size);
    if (!result.getSuccess()) {
      throw new BusinessException("获取生产中的日日单失败：" + result.getMessage());
    }
    return result.getResult();
  }

  /**
   * 过滤掉已经生成队列的日日单记录
   *
   * @param orderProducts 日日单记录
   */
  private void excludeTheExist(List<OrderProductsNew> orderProducts) {
    if (orderProducts.size() <= 0) {
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("不存在生产中的日日单");
      return;
    }
    List<String> refNos = new ArrayList<String>();
    for (OrderProductsNew orderProduct : orderProducts)
      refNos.add(orderProduct.getCOrderSn());
    ServiceResult<Map<String, Boolean>> result = purchaseOrderService
        .checkProduceDailyQueueIsExist(refNos);
    if (!result.getSuccess())
      throw new BusinessException(result.getMessage());
    Map<String, Boolean> checkedMap = result.getResult();
    for (int i = orderProducts.size() - 1; i >= 0; i--) {
      OrderProductsNew orderProduct = orderProducts.get(i);
      Boolean isExist = checkedMap.get(orderProduct.getCOrderSn());
      if (isExist != null && isExist) {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("去除已经存在的单据：" + orderProduct.getCOrderSn());
        orderProducts.remove(i);
      }
    }
  }

  /**
   * 生成日日单采购单
   *
   * @return boolean
   */
  @Override
  public ServiceResult<Boolean> createPurchaseOrder() {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    int c = 0;
    long startTime = System.currentTimeMillis();
    try {
      List<PurchaseOrderQueue4Daily> queues = getPurchaseOrderQueue4Daily(PurchaseOrderQueue4Daily.STATUS_NEW);
      if (queues.size() <= 0) {
        LOGGER.info("生成日日单采购单完成，没有要生成采购单的记录");
        result.setResult(true);
        return result;
      }
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("开始生成采购单：要处理的记录数为 " + queues.size());
      for (PurchaseOrderQueue4Daily queue : queues) {
        try {
          OrderProductsNew orderProducts = getOrderProducts(queue.getRefNo());
          if (orderProducts == null) {
            if (LOGGER.isDebugEnabled())
              LOGGER.debug("网单不存在，调拨处理");
            continue;
          }
          createPurchaseOrder(orderProducts, queue);
          if (LOGGER.isDebugEnabled())
            LOGGER.debug("生成采购单完成，单号：" + queue.getRefNo());
          c++;
        } catch (Exception e) {
          LOGGER.error("生成日日单采购单失败（" + queue.getRefNo() + "）:", e);
        }
      }
      result.setResult(true);
    } catch (Exception e) {
      result.setResult(false);
      result.setMessage("生成日日单采购单出现未知错误：" + e.getMessage());
      LOGGER.error("生成日日单采购单失败：", e);
    }
    LOGGER.info("生成日日单采购单完成，新增" + c + "条记录，用时：" + (System.currentTimeMillis() - startTime)
        + "ms");
    return result;
  }

  private static final Map<String, Integer> OCRM = new HashMap<String, Integer>();

  static {
    OCRM.put("未获取", PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING);
    OCRM.put("订单未满足", PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING);
    OCRM.put("已撤销", PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING);
    OCRM.put("待审核", PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING);
    OCRM.put("已冻结", PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING);
    OCRM.put("待评审", PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING);
    OCRM.put("等待发货", PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING);
    OCRM.put("工厂已装车", PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
    OCRM.put("车辆已离园", PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
    OCRM.put("到达客户", PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
    OCRM.put("等待工贸发货", PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
    OCRM.put("工贸已发货", PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
    OCRM.put("已返单", PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
    OCRM.put("已开发票", PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
  }

  @Override
  public ServiceResult<Boolean> updateOrderProductPDStatus() {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    List<PurchaseOrderQueue4Daily> queues = getPurchaseOrderQueue4DailyForUpdateOrders();
    if (queues.size() <= 0) {
      LOGGER.info("更新网单的日日单状态完成：无需处理的记录");
      result.setResult(true);
      return result;
    }
    int orderStatus;
    String omsStatus;
    for (PurchaseOrderQueue4Daily queue : queues) {
      try {
        OrderProductsNew orderProducts = getOrderProducts(queue.getRefNo());
        if (orderProducts == null) {
          LOGGER.info("网单（" + queue.getRefNo() + "）不存在，无需处理");
          continue;
        }

        //网单已经取消或者完成关闭，不再更新日日单状态，直接修改队列网单状态为50已入WA库
        if (orderProducts.getStatus() >= OrderProductsNew.STATUS_CANCEL_CLOSE
            && orderProducts.getPdOrderStatus() >= OrderProductsNew.RRSSTATUS_FINISH_CLOSE) {
          LOGGER.info("网单（" + queue.getRefNo() + "）,已经取消或者完成关闭，不再更新日日单状态");
          updatePurchaseOrderQueue4DailyOrderStatus(queue,
              PurchaseOrderQueue4Daily.ORDER_STATUS_HAS_ENTRY_WA_WAREHOUSE);
          continue;
        }

        //判断是否已入WA库
        ServiceResult<Boolean> result1 = purchaseOrderService
            .isPurchaseProduceDailyHasEntryWAWareHouse(queue.getRefNo());
        if (!result.getSuccess())
          throw new BusinessException("判断日日单是否已入WA库失败：" + result.getMessage());
        boolean isHasEntryWAWareHouse = result1.getResult();

        if (isHasEntryWAWareHouse) {//更新为已入WA库
          updateOrderProductDailyStatus(orderProducts,
              PurchaseOrderQueue4Daily.ORDER_STATUS_HAS_ENTRY_WA_WAREHOUSE);
          updatePurchaseOrderQueue4DailyOrderStatus(queue,
              PurchaseOrderQueue4Daily.ORDER_STATUS_HAS_ENTRY_WA_WAREHOUSE);
        } else {
          orderStatus = queue.getOrderStatus();
          if (PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING == orderStatus) {
            omsStatus = queue.getOmsStatus();
            Integer newOrderStatus = OCRM.get(omsStatus);
            if (newOrderStatus == null)
              continue;
            if (newOrderStatus == PurchaseOrderQueue4Daily.ORDER_STATUS_PRODUCING) {
              if (LOGGER.isDebugEnabled())
                LOGGER.debug("无需更新网单状态：" + queue.getId() + "，omsStatus "
                    + omsStatus);
              continue;
            }
            updateOrderProductDailyStatus(orderProducts,
                PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
            updatePurchaseOrderQueue4DailyOrderStatus(queue,
                PurchaseOrderQueue4Daily.ORDER_STATUS_FACTORY_HAS_DELIVERY);
          }
        }
      } catch (Exception e) {
        if (e instanceof BusinessException) {
          LOGGER.error("更新网单（" + queue.getRefNo() + "）日日单状态失败：" + e.getMessage());
        } else {
          LOGGER.error("更新网单（" + queue.getRefNo() + "）日日单状态失败：", e);
        }
      }
    }
    return result;
  }

  /**
   * 获取待更新网单日日单状态的队列
   *
   * @return 日日单队列
   */
  private List<PurchaseOrderQueue4Daily> getPurchaseOrderQueue4DailyForUpdateOrders() {
    ServiceResult<List<PurchaseOrderQueue4Daily>> result = purchaseOrderService
        .getPurchaseOrderQueue4DailyForUpdateOrders();
    if (!result.getSuccess())
      throw new BusinessException("获取日日单采购单队列失败：" + result.getMessage());
    return result.getResult();
  }

  private void updatePurchaseOrderQueue4DailyOrderStatus(PurchaseOrderQueue4Daily queue4Daily,
      Integer status) {
    ServiceResult<Boolean> result = purchaseOrderService.updateProduceDailyQueueOrderStatus(
        queue4Daily.getId(), status, queue4Daily.getOrderStatus());
    if (!result.getSuccess() || !result.getResult())
      throw new BusinessException("更新日日单采购单队列的网单更新状态失败：" + result.getMessage());
  }

  private void updateOrderProductDailyStatus(OrderProductsNew orderProducts, Integer status) {

    if (orderProducts.getPdOrderStatus().equals(status))
      return;
    orderProducts.setPdOrderStatus(status);
    ServiceResult<Boolean> rs2 = orderService.updateRRSById(orderProducts);
    if (!rs2.getSuccess() || !rs2.getResult())
      throw new BusinessException("更新网单的日日单状态失败：" + rs2.getMessage());
  }

  @Override
  public ServiceResult<Boolean> syncProduceInformation() {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    List<PurchaseOrderQueue4Daily> queues = getPurchaseOrderQueue4DailyForSync();

    if (queues.size() <= 0) {
      LOGGER.info("同步日日单生成详细信息完成：没有要同步的记录");
      result.setResult(true);
      return result;
    }

    for (PurchaseOrderQueue4Daily queue : queues) {
      try {
        syncProduceInformationToShop(queue);
      } catch (Exception e) {
        LOGGER.error("同步日日单（" + queue.getRefNo() + "）生产信息到Shop失败：", e);
      }
    }

    LOGGER.info("同步生产信息到商城完成");

    return result;
  }

  /**
   * 同步生产信息到商城
   *
   * @param queue 日日单队列
   */
  private void syncProduceInformationToShop(PurchaseOrderQueue4Daily queue) {
    Integer status = queue.getDetailInfoSyncStatus();

    PurchaseItem purchaseItem = getPurchaseProduceDailyItem(queue.getRefNo());
    if (purchaseItem == null) {
      LOGGER.info("关联的采购单不存在，无法处理：" + queue.getRefNo());
      return;
    }

    Integer purchaseItemStatus = purchaseItem.getStatus();

    if (purchaseItemStatus == PurchaseItemStatus.PO_CANCEL.getValue()) {
      LOGGER.info("订单已取消，不再处理");
      return;
    }

    switch (status) {
      case PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP1://订单排产
        if (purchaseItemStatus == PurchaseItemStatus.PI_FINISH.getValue()
            || purchaseItemStatus >= PurchaseItemStatus.PD_WAIT_FOR_DELIVERY.getValue()) {//日日单状态为已发货后，更新为已排产，已排查时间为审核通过时间
          Date reviewDate = purchaseItem.getReviewDate();
          if (reviewDate != null) {
            syncToLesShippingInfos(queue.getRefNo(), "PP1", reviewDate, "工厂已经接单排入生产计划");
            updatePurchaseQueue4DailySyncStatus(queue,
                PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP2);
          }
        }
        break;
      case PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP2://生产下线
        if (queue.getSyncProdDateStatus().equals(
            PurchaseOrderQueue4Daily.SYNC_PROD_DATE_STATUS_DOWN)) {
          Date proDate = purchaseItem.getProdDate();
          if (proDate == null) {
            proDate = new Date();
          }
          syncToLesShippingInfos(queue.getRefNo(), "PP2", proDate,
              "生产下线：产品已经完成生产组装，已进入基地仓等待装车发运。");
          updatePurchaseQueue4DailySyncStatus(queue,
              PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP3);
        }
        break;
      case PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP3://事业部已发货
        if (purchaseItemStatus >= PurchaseItemStatus.PD_BIZ_DEPt_HAS_SHIPPED.getValue()
            || purchaseItemStatus == PurchaseItemStatus.PI_FINISH.getValue()) {
          Date custSendDate = purchaseItem.getCustSendDate();
          if (custSendDate == null) {
            custSendDate = new Date();
          } else {
            custSendDate = getFixSyncDateTime(custSendDate);
          }
          String msg = "事业部已发货：已经完成装车工序，进入基地仓到当地中心仓的中转状态，等待入"
              + getWareHouseName(purchaseItem.getSecCode()) + "中心仓。 ";
          syncToLesShippingInfos(queue.getRefNo(), "PP3", custSendDate, msg);
          updatePurchaseQueue4DailySyncStatus(queue,
              PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP4);
        }
        break;
      case PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP4://已入当地中心仓
        if ((purchaseItemStatus >= PurchaseItemStatus.PD_HAS_ENTER_RRS_WAREHOUSE.getValue() && purchaseItemStatus != PurchaseItemStatus.PD_HAS_CANCEL_ENTRY_WA_WAREHOUSE
            .getValue()) || purchaseItemStatus == PurchaseItemStatus.PI_FINISH.getValue()) {
          Date enterTime = purchaseItem.getPodDate();//返单日期
          if (enterTime == null) {
            enterTime = purchaseItem.getSignDate();//工贸签收日期
          }
          if (enterTime == null) {
            enterTime = new Date();
          } else {
            enterTime = getFixSyncDateTime(enterTime);
          }
          syncToLesShippingInfos(queue.getRefNo(), "PP4", enterTime,
              "已入当地中心仓:已入" + getWareHouseName(purchaseItem.getSecCode()) + "中心仓");
          updatePurchaseQueue4DailySyncStatus(queue,
              PurchaseOrderQueue4Daily.DETAIL_INFO_SYNC_STATUS_PP10);
        }
        break;
      default:
        break;
    }

  }

  private List<PurchaseOrderQueue4Daily> getPurchaseOrderQueue4DailyForSync() {
    ServiceResult<List<PurchaseOrderQueue4Daily>> result = purchaseOrderService
        .getPurchaseOrderQueue4DailyForSyncDetailInfo();
    if (!result.getSuccess())
      throw new BusinessException("获取日日单采购单队列失败：" + result.getMessage());
    return result.getResult();
  }

  private PurchaseItem getPurchaseProduceDailyItem(String corderSn) {
    ServiceResult<PurchaseItem> result = purchaseOrderService
        .getPurchaseProduceDailyItem(corderSn);
    if (!result.getSuccess())
      throw new BusinessException("获取日日单采购单失败：" + result.getMessage());
    return result.getResult();
  }

  private void syncToLesShippingInfos(String corderSn, String nodeCode, Date nodeTime, String msg) {
    List<LesShippingInfos> lesShippingInfos = lesShippingInfosService.getByCorderSn(corderSn);
    boolean isExist = false;
    for (LesShippingInfos lesShippingInfo : lesShippingInfos) {
      if (lesShippingInfo.getNodeCode().equals(nodeCode)) {
        isExist = true;
        break;
      }
    }
    if (!isExist) {
      OrderProductsNew orderProducts = getOrderProducts(corderSn);
      if (orderProducts == null) {
        LOGGER.info("同步日日单生产信息到lesShippingInfos失败：关联的网单（" + corderSn + "）不存在");
        return;
      }
      LesShippingInfos lesShippingInfo = new LesShippingInfos();
      lesShippingInfo.setOrderId(orderProducts.getOrderId());
      lesShippingInfo.setOrderProductId(orderProducts.getId());
      lesShippingInfo.setcOrderSn(corderSn);
      lesShippingInfo.setNodeTime(DateUtil.format(nodeTime, "yyyy-MM-dd HH:mm:ss"));
      lesShippingInfo.setDeliveryType("2");
      lesShippingInfo.setNodeCode(nodeCode);
      lesShippingInfo.setNodeDesc(msg);
      lesShippingInfo.setLogId(1);
      lesShippingInfo.setSyncTBStatus(0);
      lesShippingInfo.setAddTime((int) (new Date().getTime() / 1000));
      lesShippingInfosService.insert(lesShippingInfo);
    }
  }

  private void updatePurchaseQueue4DailySyncStatus(PurchaseOrderQueue4Daily queue4Daily,
      Integer status) {
    ServiceResult<Boolean> result = purchaseOrderService.updateProduceDailyQueueSyncStatus(
        queue4Daily.getId(), status, queue4Daily.getDetailInfoSyncStatus());
    if (!result.getSuccess() || !result.getResult())
      throw new BusinessException("更新日日单采购队列的同步状态失败：", result.getMessage());
  }

  /**
   * 指定日期加上当前时间减去45分钟，如指定时间2015-01-01 00：00：00，当前时间2015-02-03 12：46：12则返回2015-01-01 12：01：12
   *
   * @param dateTime 指定的时间
   * @return fixed dateTime
   */
  private Date getFixSyncDateTime(Date dateTime) {
    Calendar calendar = Calendar.getInstance();
    int h = calendar.get(Calendar.HOUR_OF_DAY);
    int m = calendar.get(Calendar.MINUTE);
    int s = calendar.get(Calendar.SECOND);
    calendar.setTime(dateTime);
    calendar.add(Calendar.SECOND, s + 60 * m + 60 * 60 * h - 60 * 45);
    return calendar.getTime();
  }

  private String getWareHouseName(String secCode) {
    if (StringUtil.isEmpty(secCode) || secCode.length() < 2)
      return "当地";
    ServiceResult<InvWarehouse> result = stockCommonService.getWarehouse(secCode
        .substring(0, 2));
    if (!result.getSuccess())
      throw new BusinessException("获取仓库信息失败：" + result.getMessage());
    InvWarehouse warehouse = result.getResult();
    return warehouse == null ? "当地" : warehouse.getWhName();
  }

  @Override
  public ServiceResult<Boolean> updateCRMLogisticsInfoFromLes() {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    List<PurchaseOrderQueue4Daily> queues = getPurchaseOrderQueue4Daily(PurchaseOrderQueue4Daily.STATUS_LOGISTICS);
    if (queues.size() <= 0) {
      LOGGER.info("更新日日单CRM开提单信息完成：没有要处理的记录");
      result.setResult(true);
      return result;
    }

    Date date = new Date();
    List<ZWDTABLE2> responses = eaiHandler.queryProduceCRMTidanInformationFromLess(DateUtil
        .truncateTime(date));

    String corderSn;
    for (ZWDTABLE2 response : responses) {
      corderSn = "W" + response.getADD4();
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("网单号LESS:" + corderSn);
      for (PurchaseOrderQueue4Daily queue : queues) {
        if (queue.getRefNo().equals(corderSn)) {
          String day = response.getERDAT();
          String time = response.getERZET();
          Date createOrderToLessDate = DateUtil.parse(day + " " + time,
              "yyyy-MM-dd HH:mm:ss");
          ServiceResult<Boolean> rs = purchaseOrderService
              .updatePurchaseProduceDailyFromLess(queue, response.getBSTKD(),
                  createOrderToLessDate);
          if (!rs.getSuccess() || !rs.getResult())
            LOGGER.warn("更新采购单的CRM开提单信息失败：" + result.getMessage());
          break;
        }
      }
    }
    result.setResult(true);
    return result;
  }

  @Override
  public ServiceResult<Boolean> syncProdDateFromEDW() {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();

    ServiceResult<List<PurchaseOrderQueue4Daily>> rs = purchaseOrderService
        .getPurchaseOrderQueue4DailyForSyncProdDateFromEDW();
    if (!rs.getSuccess()) {
      throw new BusinessException("获取日日单队列失败：" + rs.getMessage());
    }
    List<PurchaseOrderQueue4Daily> queues = rs.getResult();
    if (queues.size() == 0) {
      LOGGER.info("从EDW同步日日单下线日期完成：没有要处理的记录");
      result.setResult(true);
      return result;
    }

    for (PurchaseOrderQueue4Daily queue : queues) {
      PurchaseItem purchaseItem = getPurchaseProduceDailyItem(queue.getRefNo());
      if (purchaseItem == null) {
        LOGGER.info("采购单（" + queue.getRefNo() + "）已不存在，不再更新生产日期");
        continue;
      }
      Date date = eaiHandler.queryProdDateFromEDW(purchaseItem.getGvsOrderCode());
      if (date != null) {
        updateProduceDailyProdDate(queue, date);
      }
    }
    result.setResult(true);
    return result;
  }

  private void updateProduceDailyProdDate(PurchaseOrderQueue4Daily queue, Date prodDate) {
    ServiceResult<Boolean> result = purchaseOrderService
        .updatePurchaseProduceDailyPrdDateFromEDW(queue, prodDate);
    if (!result.getSuccess() || !result.getResult()) {
      LOGGER.error("更新日日单生产/下线日期失败" + result.getMessage());
    }
  }

  private OrderProductsNew getOrderProducts(String corderSn) {
    ServiceResult<OrderProductsNew> result = orderService.getOrderProductByCOrderSn(corderSn);
    if (!result.getSuccess())
      throw new BusinessException("获取网单失败：" + result.getMessage());
    return result.getResult();
  }

  /**
   * 生成日日单采购队列
   *
   * @param orderProduct 日日单
   * @param queue        日日单 queue
   */
  private void createPurchaseOrder(OrderProductsNew orderProduct, PurchaseOrderQueue4Daily queue) {
    PurchaseOrder purchaseOrder = generatePurchaseOrder(orderProduct);
    List<PurchaseItem> items = generatePurchaseItem(orderProduct);
    ServiceResult<Boolean> result = purchaseOrderService.createPurchaseProduceDaily(queue,
        purchaseOrder, items);
    if (!result.getSuccess())
      throw new BusinessException("添加采购单失败：" + result.getMessage());
  }

  private PurchaseOrder generatePurchaseOrder(OrderProductsNew orderProduct) {
    PurchaseOrder purchaseOrder = new PurchaseOrder();
    purchaseOrder.setSoldTo("");
    purchaseOrder.setChannelCode(assertChannel(orderProduct));
    purchaseOrder.setSuplier("");
    purchaseOrder.setDeliveryTo("");
    purchaseOrder.setAmount(new BigDecimal("0.00"));
    purchaseOrder.setType("ZBCR");
    purchaseOrder.setMobile("");
    purchaseOrder.setTelephone("");
    purchaseOrder.setReceiver("");
    purchaseOrder.setProvince("");
    purchaseOrder.setCity("");
    purchaseOrder.setCounty("");
    purchaseOrder.setAddress("");
    purchaseOrder.setPoType("RRD");
    return purchaseOrder;
  }

  private List<PurchaseItem> generatePurchaseItem(OrderProductsNew orderProducts) {
    List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
    List<InvMachineSet> machineSets = getBomInfo(orderProducts.getSku());
    PurchaseItem purchaseItem = new PurchaseItem();
    purchaseItem.setSourceSn(orderProducts.getOmsOrderSn());
    purchaseItem.setSku(orderProducts.getSku());
    purchaseItem.setBrand("");
    purchaseItem.setPoQty(orderProducts.getNumber());
    purchaseItem.setUnit(machineSets.size() > 0 ? "TAO" : "TAI");
    purchaseItem.setPrice(new BigDecimal("0.00"));
    purchaseItem.setPoItemAmount(new BigDecimal(0.00));
    purchaseItem.setSecCode(orderProducts.getSCode());
    purchaseItem.setCollect(new BigDecimal(0.00));
    purchaseItem.setItemType("10");
    purchaseItem.setSign("-1");
    purchaseItem.setPayStatus("P1");
    purchaseItem.setPoTime(new Date());
    purchaseItem.setStatus(10);
    purchaseItems.add(purchaseItem);
    for (InvMachineSet machineSet : machineSets) {
      PurchaseItem purchaseItem2 = new PurchaseItem();
      purchaseItem2.setSourceSn("");
      purchaseItem2.setSku(machineSet.getSubSku());
      purchaseItem2.setBrand("");
      purchaseItem2.setPoQty(machineSet.getMenge().intValue() * orderProducts.getNumber());
      purchaseItem2.setUnit("TAI");
      purchaseItem2.setPrice(new BigDecimal("0.00"));
      purchaseItem2.setPoItemAmount(new BigDecimal(0.00));
      purchaseItem2.setSecCode(orderProducts.getSCode());
      purchaseItem2.setCollect(new BigDecimal(0.00));
      purchaseItem2.setItemType("10");
      purchaseItem2.setSign("-1");
      purchaseItem2.setPayStatus("P1");
      purchaseItem2.setPoTime(new Date());
      purchaseItem2.setStatus(10);
      purchaseItems.add(purchaseItem2);
    }
    return purchaseItems;
  }

  /**
   * 确定日日单所属渠道
   *
   * @param orderProduct 日日单
   * @return 渠道编码
   */
  private String assertChannel(OrderProductsNew orderProduct) {
    ServiceResult<OrdersNew> result = orderService.getOrder(orderProduct.getOrderId());
    if (!result.getSuccess())
      throw new BusinessException("获取订单失败：" + result.getMessage());
    OrdersNew orders = result.getResult();
    if (orders == null)
      throw new BusinessException("订单已不存在，网单号" + orderProduct.getCOrderSn());
    String source = orders.getSource();
    ServiceResult<String> rs = stockCommonService.getChannelCodeByOrderSource(source);
    if (!rs.getSuccess())
      throw new BusinessException("根据订单来源获取渠道失败：" + rs.getMessage());
    String channel = rs.getResult();
    if (StringUtil.isEmpty(channel)) {
      channel = InvStockChannel.CHANNEL_SHANGCHENG;
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("订单来源“" + source + "+”关联的渠道不存在，指定为商城渠道");
    }
    return channel;
  }

  private List<InvMachineSet> getBomInfo(String sku) {
    InvMachineSet machineSet = new InvMachineSet();
    machineSet.setMainSku(sku);
    ServiceResult<List<InvMachineSet>> result = stockCommonService
        .getSubMachinesByMainSku(machineSet);
    if (!result.getSuccess())
      throw new BusinessException("获取套机关系失败：" + result.getMessage());
    return result.getResult();
  }
}
