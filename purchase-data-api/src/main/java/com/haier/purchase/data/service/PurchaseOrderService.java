package com.haier.purchase.data.service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;
import com.haier.purchase.data.model.PurchaseOrderQueue;
import com.haier.purchase.data.model.PurchaseOrderQueue4Daily;
import com.haier.purchase.data.model.PurchaseOrderQueueWrapper;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface PurchaseOrderService {

  PurchaseOrder get(int poId);

  public List<PurchaseOrderInfoItem> getOrderInfoByDnSi(Map params);

  /**
   * 根据订单来源号，产品Sku，库位编码查询验证是否存在采购订单明细记录,防止重复导入数据
   */
  ServiceResult<Boolean> isExistPurchaseItem(String sourceSn, String sku, String secCode);

  /**
   * 创建采购订单，采购订单明细，采购订单队列
   *
   * @param purchaseOrder 采购订单
   * @param purchaseItemList 采购网单
   */
  ServiceResult<Boolean> createPurchaseOrder(PurchaseOrder purchaseOrder,
      List<PurchaseItem> purchaseItemList);


  /**
   * 根据条件查询采购订单明细列表
   */
  ServiceResult<List<PurchaseOrderQueueWrapper>> findPurchaseOrderQueueByCondition(Date startTime,
      Date endTime, String poItemNo, int status_, PagerInfo pagerInfo);

  ServiceResult<List<PurchaseOrder>> findPurchaseOrder(Date startTime, Date endTime, String poNo,
      int status, PagerInfo pager);

  /**
   * 确认采购订单
   *
   * @param poId
   * @return
   */
  ServiceResult<Boolean> confirmPurchaseOrder(int poId);

  /**
   * 取消采购订单
   *
   * @param poId
   * @return
   */
  ServiceResult<Boolean> cancelPurchaseOrder(int poId);

  ServiceResult<List<PurchaseItem>> findPurchaseItemByPoId(int poId);

  ServiceResult<Boolean> updatePurchaseProduceDailyOrderAfterInput(String sku, String corderSn, Integer quantity, Date billTime);

  ServiceResult<Boolean> updatePurchaseOrderAfterInput(PurchaseItem purchaseItem_);

  ServiceResult<PurchaseItem> getPurchaseItemByPoItemNo(String poItemNo);

  /**
   * 根据采购订单ID查询采购订单详情
   * @param poId
   * @return
   */
  ServiceResult<PurchaseOrder> getPurchaseOrder(int poId);

  /**
   * 确认采购网单
   *
   * @param poItemId
   * @return
   */
  ServiceResult<Boolean> confirmPurchaseItem(int poItemId);

  /**
   * 取消采购网单
   *
   * @param poItemId
   *            PurchaseOrderId
   * @return
   */
  ServiceResult<Boolean> cancelPurchaseItem(int poItemId);

  /**
   * 查询一段时间内没有处理或处理出错即状态不为1的采购队列记录，查询时间以更新时间字段updateTime为准
   * @return
   */
  ServiceResult<List<PurchaseOrderQueueWrapper>>  findPurchaseOrderQueue();

  /**
   * 采购订单数据同步到Les后更新采购订单队列状态并记录Les响应信息
   * @param purchaseOrderQueue
   * @return
   * @see com.haier.purchase.data.service.PurchaseOrderService#updatePurchaseOrderQueueAfterSynced(com.haier.purchase.data.model.PurchaseOrderQueue)
   */
  ServiceResult<Boolean> updatePurchaseOrderQueueAfterSynced(PurchaseOrderQueue purchaseOrderQueue);

  /**
   * 更新日日单采购单的OMS相关信息，生产信息
   *
   * @param queue
   * @param params
   * @return
   */
  ServiceResult<Boolean> updatePurchaseProduceDailyFromOms(PurchaseOrderQueue4Daily queue,
      Map<String, String> params);

  /**
   * 更新日日单采购队列的信息
   *
   * @param queueId
   * @param msg
   * @return
   */
  ServiceResult<Boolean> updatePurchaseOrderQueue4DailyMessage(Integer queueId, String msg);

  /**
   * 获取日日单采购单队列：根据采购队列{@code status}获取
   *
   * @param queueStatus
   *            队列状态
   * @return
   */
  ServiceResult<List<PurchaseOrderQueue4Daily>> getPurchaseOrderQueue4Daily(Integer queueStatus);

  /**
   * 添加到日日单采购单队列
   *
   * @param queue4Daily
   * @return
   */
  ServiceResult<Boolean> addToPurchaseOrderQueue4Daily(PurchaseOrderQueue4Daily queue4Daily);

  /**
   * 检查日日单所属的日日单采购队列是否已经存在
   *
   * @param refNos
   * @return
   */
  ServiceResult<Map<String, Boolean>> checkProduceDailyQueueIsExist(List<String> refNos);

  /**
   * 生成日日单采购单并更新日日单采购单队列状态为待更新生产数据
   *
   * @param queue
   * @param purchaseOrder
   * @param purchaseItems
   * @return
   */
  ServiceResult<Boolean> createPurchaseProduceDaily(PurchaseOrderQueue4Daily queue,
      PurchaseOrder purchaseOrder,
      List<PurchaseItem> purchaseItems);

  /**
   * 是否已入库
   *
   * @param corderSn
   * @return
   */
  ServiceResult<Boolean> isPurchaseProduceDailyHasEntryWAWareHouse(String corderSn);

  /**
   * 获取日日单采购单队列：更新网单状态
   *
   * @return 日日单采购队列
   */
  ServiceResult<List<PurchaseOrderQueue4Daily>> getPurchaseOrderQueue4DailyForUpdateOrders();

  /**
   * 更新日日单采购单队列的网单更新状态
   *
   * @param queueId
   * @param newStatus
   * @param oldStatus
   * @return
   */
  ServiceResult<Boolean> updateProduceDailyQueueOrderStatus(Integer queueId, Integer newStatus,
      Integer oldStatus);

  /**
   * 获取日日单采购单队列：同步生产信息到商城
   *
   * @return 队列
   */
  ServiceResult<List<PurchaseOrderQueue4Daily>> getPurchaseOrderQueue4DailyForSyncDetailInfo();

  /**
   * 获取日日单采购单
   *
   * @param corderSn
   * @return
   */
  ServiceResult<PurchaseItem> getPurchaseProduceDailyItem(String corderSn);

  /**
   * 更新日日单采购单队列的同步详细信息状态
   *
   * @param queueId
   * @param newStatus
   * @param oldStatus
   * @return
   */
  ServiceResult<Boolean> updateProduceDailyQueueSyncStatus(Integer queueId, Integer newStatus,
      Integer oldStatus);

  /**
   * 更新日日单采购单的CRM开提单信息
   *
   * @param queue4Daily
   * @param lesPing
   * @param createOrderToLessDate
   * @return
   */
  ServiceResult<Boolean> updatePurchaseProduceDailyFromLess(PurchaseOrderQueue4Daily queue4Daily,
      String lesPing,
      Date createOrderToLessDate);

  /**
   * 获取日日单采购队列：同步生产/下线日期
   *
   * @return 日日单采购队列
   */
  ServiceResult<List<PurchaseOrderQueue4Daily>> getPurchaseOrderQueue4DailyForSyncProdDateFromEDW();

  /**
   * 更新日日单采购单的产品生产/下线日期
   *
   * @param queue
   *            队列
   * @param prodDate
   *            生产日期
   * @return
   */
  ServiceResult<Boolean> updatePurchaseProduceDailyPrdDateFromEDW(PurchaseOrderQueue4Daily queue,
      Date prodDate);
}
