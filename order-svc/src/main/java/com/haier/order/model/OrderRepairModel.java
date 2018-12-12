package com.haier.order.model;

import com.google.gson.GsonBuilder;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.order.services.*;
import com.haier.purchase.data.model.PurchaseGdQueue;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.model.PaymentStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class OrderRepairModel {
    private static Logger log             = LogManager.getLogger(OrderModel.class);
    private static final List<String> ALLOWSOURCES    = Arrays.asList("COS", "DBJ");           // 可受理的订单数据源列表
    private static final boolean          OTO_FLOW_SWITCH = true;
//    @Autowired
//    private DataSourceTransactionManager transactionManager;
    @Autowired
    private OrderRepairLogsNewService orderRepairLogsNewService;
    @Autowired
    private OrderRepairHPQueuesService orderRepairHPQueuesService;
    @Autowired
    private OrderRepairLESQueuesService orderRepairLESQueuesService;
    @Autowired
    private CorderStatusToLesService corderStatusToLesService;
    @Autowired
    private OrderCenterGoodsReturnToCancelHpServiceImpl goodsReturnToCancelHpServiceImpl;
    @Autowired
    private OrderCenterPurchaseGdServiceImpl purchaseGdServiceImpl;
    @Autowired
    private GroupOrdersService groupOrdersService;
    @Autowired
    private HPFailedsService hpFailedsService;
    @Autowired
    private OrderCenterHopStockServiceImpl orderThirdCenterHopStockServiceImpl;
    @Autowired
    private OrderCenterItemServiceImplNew itemService;
    @Autowired
    private RegionsService regionsService;
    @Autowired
    private InvoiceModel                  invoiceModel;
    @Autowired
    private ShopOrderOperateLogsService orderOperateLogsShopService;
    @Autowired
    private OrdersNewService ordersNewService;
    @Autowired
    private OrderDifferencePriceRefundService orderDifferencePriceRefundService;
    @Autowired
    private OrderCenterEStoreServiceImpl eStoreServiceImpl;
    @Autowired
    private ShopOrderWorkflowsService shopOrderWorkflowsService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;
    @Autowired
    private OrderQueueExtendService orderQueueExtendService;
    @Autowired
    private WwwHpRecordsService wwwHpRecordsService;
    @Autowired
    MemberMoneyLogsService orderThirdCenterMemberMoneyLogsDao;
    @Autowired
    private OrderRepairTcLogsService orderRepairTcLogsService;
    @Autowired
    private GiftCardNumbersService giftCardNumbersService;
    @Autowired
    private OrderRepairTcRecordsService orderRepairTcRecordsService;
    @Autowired
    private OrderRepairTcsService orderRepairTcsService;
    @Autowired
    private ShopMembersService shopMembersService;
    @Autowired
    private GiftCardUsedLogsService giftCardUsedLogsService;
    private static HashSet<String>        corderSns       = new HashSet<String>();
    private synchronized boolean add(String corderSn) {
        if (corderSns.contains(corderSn))
            return false;
        corderSns.add(corderSn);
        return true;
    }

    private synchronized void del(String corderSn) {
        corderSns.remove(corderSn);
    }

    /**
     * 退货单 待检入库（包括存性变更）
     *
     * @param cOrderSn
     *            记录单号
     * @param lesIoNo
     *            入库单号
     * @return
     */
    @Autowired
    private OrderRepairsNewService orderRepairsNewService;
    @Autowired
    private OrderRepairHPRecordsnNewService orderRepairHPRecordsnNewService;
    @Autowired
    private OrderRepairLESRecordsService orderRepairLESRecordsService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    public ServiceResult<Boolean> repairOrderIn(String cOrderSn, String lesIoNo){
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        // 获取LES退货单信息
        OrderRepairLESRecords lesRecord = orderRepairLESRecordsService.getByRecordSn(cOrderSn);
        if (lesRecord == null) {
            log.error("退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo + ")，LES退货单为null");
            result.setResult(true);
            return result;
        }
        if (!StringUtil.isEmpty(lesRecord.getLesOutPing())) {// 已处理过，不再处理
            result.setResult(true);
            return result;
        }
        // 获取商城退货单信息
        OrderRepairsNew repairOrder = orderRepairsNewService.get(lesRecord.getOrderRepairId());
        if (repairOrder == null) {
            log.error("退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo + ",OrderRepairId:"
                    + lesRecord.getOrderRepairId() + ")，商城退货单为null");
            result.setResult(true);
            return result;
        }
        // 组装数据 - LES退货单
        lesRecord.setLesOutPing(lesIoNo);
        // 组装数据 - 退货单操作日志
        OrderRepairLogsNew repairlog = new OrderRepairLogsNew();
        repairlog.setOperate("les_return");
        repairlog.setOperator("系统");
        repairlog.setOrderRepairId(repairOrder.getId());
        repairlog.setRemark("接收LES回传" + this.getLESRecordOprateName(lesRecord.getOperate())
                + "结果，批次：" + lesRecord.getStorageType() + "，凭证号："
                + lesRecord.getLesOutPing());
        repairlog.setSiteId(1);
        // 组装数据 - 商城退货单
        if (OrderRepairLESRecords.STORAGE_TYPE_BAD.equals(lesRecord.getStorageType())) {// 不良品入库
            repairOrder.setStorageStatus(OrderRepairsNew.STORAGE_STATUS_IN21);
        }
        if (OrderRepairLESRecords.STORAGE_TYPE_GOOD.equals(lesRecord.getStorageType())) {// 良品入库
            repairOrder.setStorageStatus(OrderRepairsNew.STORAGE_STATUS_IN10);
            if (OrderRepairsNew.TYPE_ACTUAL_CHANGE.equals(repairOrder.getTypeActual())) {
                // 退货不退款的情况
                repairOrder.setHandleStatus(OrderRepairsNew.HANDLE_STATUS_CONFIRM);
                repairOrder.setFinishTime((new Date().getTime() / 1000));
            }
        }
        if (OrderRepairLESRecords.STORAGE_TYPE_SAMPLE.equals(lesRecord.getStorageType())) {// 样品入库
            repairOrder.setStorageStatus(OrderRepairsNew.STORAGE_STATUS_IN40);
            if (OrderRepairsNew.TYPE_ACTUAL_CHANGE.equals(repairOrder.getTypeActual())) {
                // 退货不退款的情况
                repairOrder.setHandleStatus(OrderRepairsNew.HANDLE_STATUS_CONFIRM);
                repairOrder.setFinishTime((new Date().getTime() / 1000));
            }
        }

        if (OrderRepairLESRecords.STORAGE_TYPE_SAMPLE_SHOP.equals(lesRecord.getStorageType())) {// 夺宝机入库
            repairOrder.setStorageStatus(OrderRepairsNew.STORAGE_STATUS_IN41);
            if (OrderRepairsNew.TYPE_ACTUAL_CHANGE.equals(repairOrder.getTypeActual())) {
                // 退货不退款的情况
                repairOrder.setHandleStatus(OrderRepairsNew.HANDLE_STATUS_CONFIRM);
                repairOrder.setFinishTime((new Date().getTime() / 1000));
            }
        }
        // 是否需要二次质检
        Boolean needSecondInspect = false;
        if (OrderRepairLESRecords.STORAGE_TYPE_OPENED.equals(lesRecord.getStorageType())
                && (OrderRepairLESRecords.OPERATE_NETPOINT_DONOTINSPECT.equals(lesRecord.getOperate()) || OrderRepairLESRecords.OPERATE_NETPOINT_INSPECT
                .equals(lesRecord.getOperate()))) {// 开箱产品入库
            repairOrder.setStorageStatus(OrderRepairsNew.STORAGE_STATUS_IN22);
            needSecondInspect = true;
        }
        // 组装数据 - HP二次质检队列
        OrderRepairHPQueues hpQueue = new OrderRepairHPQueues();
        if (needSecondInspect) {
            OrderRepairHPRecordsNew hpRecord = orderRepairHPRecordsnNewService.getByRepairIdAndCheckType(
                    repairOrder.getId(), 1);
            OrderProductsNew orderProduct = orderProductsNewService.get(repairOrder.getOrderProductId());
            if (hpRecord == null || orderProduct == null) {
                log.error("退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo
                        + ",OrderRepairId:" + lesRecord.getOrderRepairId() + ",OrderProductId:"
                        + repairOrder.getOrderProductId() + ")，HP退货单为null或者网单为NULL");
                needSecondInspect = false;
            } else {
                Map<String, Object> pushData = new HashMap<String, Object>();
                pushData.put("orderNo", repairOrder.getRepairSn() + "_2");
                pushData.put("counts", repairOrder.getCount());
                pushData.put("createdDate", this.getISO8601Format(new Date()));
                pushData.put("prodtypeId", orderProduct.getSku());
                pushData.put("type", "");
                pushData.put("checkType", "");
                pushData.put("requestServiceRemark", repairOrder.getRequestServiceRemark());
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, 1);// 当前时间加一天
                String requestServiceDate = this.getISO8601Format(cal.getTime());
                pushData.put("requestServiceDate", requestServiceDate);
                pushData.put("oldOrder", repairOrder.getRepairSn());
                pushData.put("remark", "");
                pushData.put("ifTk", "");
                pushData.put("tkReason", "");
                pushData.put("tkje", "");
                pushData.put("tkRemark", "");
                pushData.put("adressNew", "");
                pushData.put("customerName", "");
                pushData.put("mobilePhone", "");
                pushData.put("telephone", "");
                pushData.put("mopPoi", "");
                pushData.put("county", "");
                pushData.put("ifEcjd", "2");
                pushData.put("tcCode", lesRecord.getSCode());
                pushData.put("productNo", hpRecord.getMachineNum());
                pushData.put("types", "");
                if (orderProduct.getStockType() != null
                        && orderProduct.getStockType().equalsIgnoreCase("3W")) {
                    pushData.put("types", "W8");
                }
                GsonBuilder gb = new GsonBuilder();
                String sPushData = gb.create().toJson(pushData);

                hpQueue.setOrderProductId(repairOrder.getOrderProductId());
                hpQueue.setOrderRepairId(repairOrder.getId());
                hpQueue.setPushData(sPushData);
                hpQueue.setSiteId(1);
            }
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 更新LES退货单
            orderRepairLESRecordsService.updateAfterLesInStorage(lesRecord);
            // 记录日志
            orderRepairLogsNewService.insert(repairlog);
            // 向HP发送二次质检请求
            if (needSecondInspect) {
                hpQueue.setVomReturnData("");
                hpQueue.setVomSuccess(0);
                hpQueue.setVomCount(0);
                hpQueue.setVomLastMessage("");
                hpQueue.setVomSuccessTime(0);
                hpQueue.setTypeFlag(0);
                orderRepairHPQueuesService.insert(hpQueue);
            }
            // 更新商城退货单
            orderRepairsNewService.updateAfterLesInStorage(repairOrder);
            // 提交事务
//            transactionManager.commit(status);
            // 返回执行成功
            result.setResult(true);
            return result;
        } catch (Exception ex) {
            // 回滚事务
//            transactionManager.rollback(status);
            // 记录日志
            log.error("退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo + ")，出现未知异常:", ex);
            result.setResult(false);
            result.setMessage("发生未知异常：" + ex.getMessage());
            return result;
        }
    }
    /*==============================================================*/
    /**
     * 根据操作获取退货单入库操作描述
     *
     * @param operate
     * @return
     */
    private String getLESRecordOprateName(Integer operate) {
        if (OrderRepairLESRecords.OPERATE_CHANGE_IN.equals(operate)) {
            return "入库-存性变更";
        }
        if (OrderRepairLESRecords.OPERATE_CHANGE_OUT.equals(operate)) {
            return "出库-存性变更";
        }
        if (OrderRepairLESRecords.OPERATE_NETPOINT_DONOTINSPECT.equals(operate)) {
            return "入库-网点不质检";
        }
        if (OrderRepairLESRecords.OPERATE_NETPOINT_INSPECT.equals(operate)) {
            return "入库-网点质检";
        }
        return operate.toString();
    }
    /**
     * 将指定日期转换为ISO8601格式
     *
     * @param myDate
     * @return
     */
    private String getISO8601Format(Date myDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String nowAsString = df.format(myDate);
        return new StringBuilder(nowAsString).insert(nowAsString.length() - 2, ':').toString();
    }
    public boolean updateAfterVomAccepted(String corderSn, Date acceptTime, String expNo){
        OrderRepairLESRecords orderRepairLESRecords = orderRepairLESRecordsService
                .getByRecordSn(corderSn);
        if (orderRepairLESRecords == null) {
            log.warn("VOM接单后更新退货单失败：关联的记录不存在，单号：" + corderSn);
            return true;
        }

        OrderRepairLESQueues queues = orderRepairLESQueuesService
                .getByOrderProductId(orderRepairLESRecords.getOrderProductId());

        OrderRepairLogsNew orderRepairLogs = new OrderRepairLogsNew();
        orderRepairLogs.setAddTime((int) (new Date().getTime() / 1000));
        orderRepairLogs.setOperator("系统");
        orderRepairLogs.setOperate("提交入库-网点不检验请求到vom，批次：" + orderRepairLESRecords.getStorageType());
        orderRepairLogs.setOrderRepairId(orderRepairLESRecords.getOrderRepairId());
        orderRepairLogs.setSiteId(1);
        orderRepairLogs.setRemark("已同步到VOM,快递单号：" + expNo);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (queues == null) {
                log.warn("VOM接单后更新OrderRepairLESQueues失败：关联的记录不存在，网单ID："
                        + orderRepairLESRecords.getOrderProductId());
            } else {
                queues.setVomSuccess(2);
                queues.setVomSuccessTime(acceptTime.getTime() / 1000);
                queues.setVomLastMessage("VOM已接单,运单号：" + expNo);
                queues.setVomReturnData("");
                orderRepairLESQueuesService.updateVomResult(queues);
            }

            orderRepairLESRecords.setLesOrderSn(expNo);
            orderRepairLESRecords.setLesOrderSnTime(acceptTime.getTime() / 1000);
            orderRepairLESRecordsService.updateAfterVomAccepted(orderRepairLESRecords);

            orderRepairLogsNewService.insert(orderRepairLogs);

//            transactionManager.commit(status);
        } catch (Exception e) {
//            transactionManager.rollback(status);
            log.error("VOM接单后更新退货信息失败：", e);
            throw new BusinessException("未知错误：" + e.getMessage());
        }
        return true;
    }
    public boolean updateAfterVomRejected(String corderSn, Date rejectedTime, String reason) {

        OrderRepairLESRecords orderRepairLESRecords = orderRepairLESRecordsService
            .getByRecordSn(corderSn);

        if (orderRepairLESRecords == null) {
            log.warn("VOM据单后后更新退货单失败：关联的记录不存在，单号：" + corderSn);
            return true;
        }

        OrderRepairLESQueues queues = orderRepairLESQueuesService
            .getByOrderProductId(orderRepairLESRecords.getOrderProductId());

        OrderRepairLogsNew orderRepairLogs = new OrderRepairLogsNew();
        orderRepairLogs.setAddTime((int) (new Date().getTime() / 1000));
        orderRepairLogs.setOperator("系统");
        orderRepairLogs.setOperate("提交入库-网点不检验请求到vom，批次：10");
        orderRepairLogs.setOrderRepairId(orderRepairLESRecords.getOrderRepairId());
        orderRepairLogs.setSiteId(1);
        orderRepairLogs.setRemark("VOM据单：" + reason);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            if (queues == null) {
                log.warn("VOM据单后更新OrderRepairLESQueues失败：关联的记录不存在，网单ID："
                         + orderRepairLESRecords.getOrderProductId());
            } else {
                queues.setVomSuccess(0);
                queues.setVomLastMessage("VOM已据单：" + reason);
                queues.setVomSuccessTime(rejectedTime.getTime() / 1000);
                queues.setVomReturnData("");
                orderRepairLESQueuesService.updateVomResult(queues);
            }
            orderRepairLogsNewService.insert(orderRepairLogs);

//            transactionManager.commit(status);
        } catch (Exception e) {
//            transactionManager.rollback(status);
            log.error("VOM据单后更新退货信息失败：", e);
            throw new BusinessException("未知错误：" + e.getMessage());
        }
        return true;
    }
    
    /**
     * 21批次不良品退货单关闭
     */
    public void closeOrderRepairForComplete() {
        List<OrderRepairsNew> orderRepairsList = orderRepairsNewService.queryWaitCloseForCompleteList();
        Integer statusTo = OrderRepairsConst.HS_CONFIRM;// 受理完成
        Map<Integer, List<Integer>> statusFromToMap = new HashMap<Integer, List<Integer>>();
        List<Integer> reviewList = new ArrayList<Integer>();
        reviewList.add(OrderRepairsConst.HS_HANDLE);
        reviewList.add(OrderRepairsConst.HS_CANCEL);
        statusFromToMap.put(OrderRepairsConst.HS_REVIEW, reviewList);
        List<Integer> handleList = new ArrayList<Integer>();
        handleList.add(OrderRepairsConst.HS_CONFIRM);
        handleList.add(OrderRepairsConst.HS_STOP);
        handleList.add(OrderRepairsConst.HS_WAITSTOP);
        handleList.add(OrderRepairsConst.HS_REFUNDEDOFFLINE);
        statusFromToMap.put(OrderRepairsConst.HS_HANDLE, handleList);
        List<Integer> confirmList = new ArrayList<Integer>();
        confirmList.add(OrderRepairsConst.HS_CLOSE);
        statusFromToMap.put(OrderRepairsConst.HS_CONFIRM, confirmList);
        statusFromToMap.put(OrderRepairsConst.HS_CLOSE, null);
        statusFromToMap.put(OrderRepairsConst.HS_CANCEL, null);
        statusFromToMap.put(OrderRepairsConst.HS_STOP, null);
        List<Integer> waitStopList = new ArrayList<Integer>();
        waitStopList.add(OrderRepairsConst.HS_STOP);
        statusFromToMap.put(OrderRepairsConst.HS_WAITSTOP, waitStopList);
        statusFromToMap.put(OrderRepairsConst.HS_REFUNDEDOFFLINE, null);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = null;
        for (OrderRepairsNew orderRepairs : orderRepairsList) {
            Integer modifyFrom = orderRepairs.getHandleStatus();
            List<Integer> nextHandlerList = statusFromToMap.get(modifyFrom);
            if (modifyFrom == null || statusFromToMap.get(modifyFrom) == null
                || !nextHandlerList.contains(statusTo)) {
                log.warn("当前状态handleStatus[" + modifyFrom + "]没有对应的下一个状态orderRepairId["
                         + orderRepairs.getId() + "]");
                continue;
            }
            OrderRepairsNew tempOrderRepair = new OrderRepairsNew();
            tempOrderRepair.setHandleStatus(statusTo);
            tempOrderRepair.setId(orderRepairs.getId());
            if (orderRepairs.getHandleStatus() == OrderRepairsConst.HS_HANDLE
                || orderRepairs.getHandleStatus() == OrderRepairsConst.HS_CANCEL) {
                // tempOrderRepair.setHandleTime(new Date().getTime() / 1000);
                tempOrderRepair.setFinishTime(new Date().getTime() / 1000);
            }
            if (orderRepairs.getHandleStatus() == OrderRepairsConst.HS_CONFIRM
                || orderRepairs.getHandleStatus() == OrderRepairsConst.HS_STOP) {
                tempOrderRepair.setFinishTime((new Date().getTime() / 1000));
            }
            try {
//                status = transactionManager.getTransaction(def);
                orderRepairsNewService.updateForComplete(tempOrderRepair);
                OrderRepairLogsNew orderRepairLogs = new OrderRepairLogsNew();
                orderRepairLogs.setAddTime((int) (new Date().getTime() / 1000));
                orderRepairLogs.setOperator("hs_系统");
                orderRepairLogs.setOrderRepairId(orderRepairs.getId());
                orderRepairLogs.setSiteId(1);
                orderRepairLogs.setRemark("修改申请状态从"
                                          + OrderRepairsConst.HANDLESTATUSMAP.get(modifyFrom) + "到"
                                          + OrderRepairsConst.HANDLESTATUSMAP.get(statusTo)
                                          + "符合条件，系统自动受理完成");
                orderRepairLogsNewService.insert(orderRepairLogs);
//                transactionManager.commit(status);
            } catch (Exception e) {
                if (status != null) {
//                    transactionManager.rollback(status);
                    log.error("更新不良品退货受理完成状态失败！repairSn=" + orderRepairs.getRepairSn(), e);
                }
            }
        }
    }

    public Integer insertCorderStatusToLes(CorderStatusToLes corderStatusToLes) {
        return corderStatusToLesService.insert(corderStatusToLes);
    }
    
    public boolean cancelHpOrder(OrderProductsNew orderProducts, OrdersNew order) {
        try {
            if (orderProducts.getStatus() == OrderProductStatus.START_STATUS.getCode()
                || orderProducts.getStatus() == OrderProductStatus.STATUS_FROZEN_STOCK.getCode()) {
                log.info("退货：无需作废HP工单, orderId=" + order.getId() + ", cOrderSn="
                         + orderProducts.getCOrderSn());
                return true;
            }
            Integer orderProductId = orderProducts.getId();
            HPFaileds hpFaileds = hpFailedsService.getByOrderProductId(orderProductId);
            if (hpFaileds != null) {
                if (hpFaileds.getSuccess() == 1) {
                    log.info("退货：作废HP工单成功, orderId=" + order.getId() + ", cOrderSn="
                             + orderProducts.getCOrderSn());
                    return true;
                }
            } else {
                // 表中无数据
                writeHPFailedLog(null, orderProducts, "", 0, 0);
            }
            Map<String, String> pushData = new HashMap<String, String>();
            pushData.put("cOrderSn", orderProducts.getCOrderSn());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            pushData.put("cancelDate", dateFormat.format(new Date()));
            ServiceResult<Map<String, String>> result = goodsReturnToCancelHpServiceImpl
                .pushOrderCancelToHp(orderProducts.getCOrderSn(), pushData);
            if (result.getSuccess()) {
                Map<String, String> resultMap = result.getResult();
                String errorMsg = resultMap.get("error");
                if (StringUtil.isEmpty(errorMsg)) {
                    String flag = resultMap.get("FLAG");
                    String lastMessage = resultMap.get("MSG") == null ? "" : resultMap.get("MSG");
                    if ("S".equals(flag)) {

                        updateHPFailedLog(pushData, orderProducts, lastMessage, 1);
                        String operator = "系统";
                        String changeLog = "系统.做了联动取消网单的操作：作废HP成功";
                        String remark = "";
                        OrderOperateLogs logs = this.getOrderOperateLog(order, orderProducts,
                            operator, changeLog, remark);
                        orderOperateLogsShopService.insert(logs);
                        return true;
                    } else {
                        updateHPFailedLog(pushData, orderProducts, lastMessage, 0);
                        String operator = "系统";
                        String changeLog = "系统.做了联动取消网单的操作：作废HP失败";
                        String remark = "";
                        OrderOperateLogs logs = this.getOrderOperateLog(order, orderProducts,
                            operator, changeLog, remark);
                        orderOperateLogsShopService.insert(logs);
                    }

                } else {
                    updateHPFailedLog(pushData, orderProducts, "", 0);
                    // 记录失败
                    String operator = "系统";
                    String changeLog = "系统.做了联动取消网单的操作:作废HP失败,失败原因:接口有异常,异常信息:";
                    String remark = "调用接口发生异常";
                    OrderOperateLogs orderOperateLogs = getOrderOperateLog(order, orderProducts,
                        operator, changeLog, remark);
                    orderOperateLogsShopService.insert(orderOperateLogs);
                }

            }
            return false;
        } catch (Exception e) {
            log.error("网单ID：" + orderProducts.getId() + ",退货：推送作废HP工单发生异常,", e);
            return false;
        }

    }
    
    private void writeHPFailedLog(Map<String, String> pushData, OrderProductsNew orderProducts,
    		String lastMessage, Integer suc, Integer count) {
    	HPFaileds hpFailed = new HPFaileds();
    	hpFailed.setAddTime(new Date().getTime() / 1000);
    	hpFailed.setSuccessTime(0l);
    	hpFailed.setCount(count);
    	hpFailed.setCreateType(0);
    	hpFailed.setPushData(pushData == null ? "" : JsonUtil.toJson(pushData));
    	hpFailed.setSuccess(suc);
    	hpFailed.setOrderProductId(orderProducts.getId());
    	hpFailed.setLastMessage(lastMessage);
    	hpFailed.setOperator("系统");
        hpFailedsService.insert(hpFailed);
    }
    
    /**
     * 通过repairSn更新退货单号
     * 
     * @return
     */
    public void updateForRepairSns(String repairSn, String operator) {
        OrderRepairsNew orderRepairs = orderRepairsNewService.getByRepairSn(repairSn);
        if (orderRepairs.getStorageStatus() == OrderRepairsConst.SS_IN21) {
            log.info("线上不良品入库操作已做，返回, repairSn=" + orderRepairs.getRepairSn());
            return;
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = null;
        try {
//            status = transactionManager.getTransaction(def);

            Integer row = orderRepairsNewService.updateForRepairSn(repairSn, OrderRepairsConst.SS_INRRS21);
            if (row > 0) {
            	OrderRepairLogsNew orderRepairLogs = new OrderRepairLogsNew();
                orderRepairLogs.setAddTime((int) (new Date().getTime() / 1000));
                orderRepairLogs.setOperator(operator);
                orderRepairLogs.setOperate("入库");
                orderRepairLogs.setOrderRepairId(orderRepairs.getId());
                orderRepairLogs.setSiteId(1);
                orderRepairLogs.setRemark("不良品退货入21库成功:" + orderRepairs.getReason());
                orderRepairLogsNewService.insert(orderRepairLogs);
            }
//            transactionManager.commit(status);
        } catch (Exception e) {
            if (status != null) {
//                transactionManager.rollback(status);
                log.error("更新不良品退货状态失败！repairSn=" + repairSn, e);
                throw new BusinessException("更新不良品退货状态失败！repairSn=" + repairSn + ","
                                            + e.getMessage());
            }

        }

    }
    private void updateHPFailedLog(Map<String, String> pushData, OrderProductsNew orderProducts,
    		String lastMessage, Integer suc) {
    	HPFaileds entity = new HPFaileds();
    	entity.setOrderProductId(orderProducts.getId());
    	entity.setPushData(JsonUtil.toJson(pushData));
    	entity.setLastMessage(lastMessage);
    	entity.setSuccessTime(new Date().getTime() / 1000);
    	entity.setSuccess(suc);
        hpFailedsService.updateHpFailed(entity);
    }
    

    /**
     * 根据网单信息，返回网单操作日志
     * 
     * @param order
     *            订单
     * @param orderProduct
     *            网单
     * @param operator
     *            操作人，可为null
     * @param changeLog
     *            变化日志
     * @param remark
     *            备注
     * @return 如果为null，应为传入参数不对
     */
    private OrderOperateLogs getOrderOperateLog(OrdersNew order, OrderProductsNew orderProduct,
                                                String operator, String changeLog, String remark) {
        Assert.notNull(order, "订单不能为空");

        OrderOperateLogs log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setNetPointId(orderProduct == null ? 0 : orderProduct.getNetPointId());
        log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
        log.setOrderId(Integer.parseInt(order.getId()));
        log.setOrderProductId(orderProduct == null ? 0 : orderProduct.getId());
        log.setPaymentStatus(order.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        log.setSiteId(1);
        log.setStatus(orderProduct == null ? order.getOrderStatus() : orderProduct.getStatus());

        return log;
    }
    

    public GroupOrders getByTailOrderId(Integer tailOrderId) {
        return groupOrdersService.getByTailOrderId(tailOrderId);
    }
    public Integer getCountByOrderProductId(Integer orderProductId) {
        return orderRepairsNewService.getCountByOrderProductId(orderProductId);
    }
    
    /**
     * 退换货申请记录
     * 
     * @param orderRepairs
     *            退换货单
     * @param userName
     *            操作人
     * @return
     */
    public Integer recordReturnGoodsInfo(OrderRepairsNew orderRepairs, String userName) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        Integer countRow = 0;
        try {
            log.info("[recordReturnGoodsInfo]登记退换货信息:" + JsonUtil.toJson(orderRepairs));
            countRow = orderRepairsNewService.insert(orderRepairs);

            OrderRepairLogsNew orderRepairLogs = new OrderRepairLogsNew();
            orderRepairLogs.setAddTime((int) (new Date().getTime() / 1000));
            orderRepairLogs.setOperator(userName);
            if (OrderRepairsNew.TYPE_ACTUAL_RETURN.equals(orderRepairs.getTypeActual())) {
                orderRepairLogs.setOperate("退货退款");
            } else if (OrderRepairsNew.TYPE_ACTUAL_CHANGE.equals(orderRepairs.getTypeActual())) {
                orderRepairLogs.setOperate("退货不退款");
            } else {
                orderRepairLogs.setOperate(" ");
            }
            orderRepairLogs.setOrderRepairId(orderRepairs.getId());
            orderRepairLogs.setSiteId(1);
            orderRepairLogs.setRemark(orderRepairs.getReason());
            orderRepairLogsNewService.insert(orderRepairLogs);

//            transactionManager.commit(status);
        } catch (Exception e) {
//            transactionManager.rollback(status);
            log.error("保存退换货单失败！", e);
            throw new BusinessException("保存退换货单失败！" + e.getMessage());
        }

        return countRow;
    }
    
    /**
     * 退/换货申请入口
     * 
     * @return
     * @throws Exception
     */
    public ServiceResult<Integer> applyReturnGoodsFilter(Integer typeActual, String orderProductSn,
                                                         String contactName, String contactMobile,
                                                         Integer count, String reason,
                                                         String description, String userName)
                                                                                             throws Exception {
        log.info("[applyReturnGoodsFilter]退/换货申请接口被调用参数：" + typeActual + "," + orderProductSn + ","
                 + contactName + "," + contactMobile + "," + count + "," + reason + ","
                 + description + "," + userName);

        ServiceResult<Integer> result = new ServiceResult<Integer>();
        if (StringUtil.isEmpty(orderProductSn)) {
            result.setResult(0);
            result.setSuccess(false);
            result.setMessage("网单号不能为空!");
            return result;
        }
        if (!orderProductSn.startsWith("WD") || orderProductSn.length() > 28) {
            result.setResult(0);
            result.setSuccess(false);
            result.setMessage("网单号输入有误!");
            return result;
        }
        OrderProductsNew orderProducts = orderProductsNewService.getByCOrderSn(orderProductSn);
        if (orderProducts == null) {
            result.setResult(0);
            result.setSuccess(false);
            result.setMessage("网单不存在!");
            return result;
        }
        OrdersNew orders = ordersNewService.get(orderProducts.getOrderId());
        if (orders == null) {
            result.setResult(0);
            result.setSuccess(false);
            result.setMessage("订单不存在!");
            return result;
        }
        // - TODO 临时取消数据源校验
        /*
         * if (!ALLOWSOURCES.contains(orders.getSource())) {
         * result.setResult(0); result.setSuccess(false);
         * result.setMessage("暂不受理“" + orders.getSource() + "”的数据源!"); return
         * result; }
         */
        return applyReturnGoods(typeActual, orderProducts, orders, contactName, contactMobile,
            count, reason, description, userName);
    }
    
    /**
     * 退/换货申请处理
     * 
     * @param userName
     * @return
     * @throws Exception
     */
    private ServiceResult<Integer> applyReturnGoods(Integer typeActual,
                                                    OrderProductsNew orderProducts, OrdersNew orders,
                                                    String contactName, String contactMobile,
                                                    Integer count, String reason,
                                                    String description, String userName)
                                                                                        throws Exception {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        result.setResult(0);
        result.setSuccess(false);
        if (typeActual == null || typeActual == 0 || typeActual == -1) {
            result.setMessage("实际处理方式不能为空!");
            return result;
        }
        if (typeActual > 2 || typeActual < 1) {
            result.setMessage("实际处理方式输入输入有误!");
            return result;
        }
        if ((!StringUtil.isEmpty(contactName)) && contactName.length() >= 10) {
            result.setMessage("联系人输入有误!");
            return result;
        }
        if (!StringUtil.isEmpty(contactMobile) && !isMobile(contactMobile)) {
            result.setMessage("联系人手机号输入有误!");
            return result;
        }
        if (count == null || count == 0) {
            result.setMessage("申请退货数量为空!");
            return result;
        }
        if (StringUtil.isEmpty(userName)) {
            result.setMessage("操作人姓名不能为空!");
            return result;
        }
        if (userName.length() >= 50) {
            result.setMessage("操作人姓名输入长度超过限制范围!");
            return result;
        }
        if (count > 999) {
            result.setMessage("申请退货数量超出上限!");
            return result;
        }
        if (StringUtil.isEmpty(reason)) {
            result.setMessage("退款原因不能为空!");
            return result;
        }
        if (reason.length() >= 50) {
            result.setMessage("退款原因输入长度超过限制范围!");
            return result;
        }

        if ((!StringUtil.isEmpty(description)) && description.length() >= 255) {
            result.setMessage("描述信息输入长度超过限制范围!");
            return result;
        }

        if (orderProducts.getNumber() < count) {
            result.setMessage("数量不能大于网单实际台数！");
            return result;
        }
        if (orderProducts.getNumber() != count) {
            result.setMessage("数量与网单实际台数不符！");
            return result;
        }
        Integer alreadyCount = this.getCountByOrderProductId(orderProducts.getId());
        if (((alreadyCount == null ? 0 : alreadyCount) + count) > orderProducts.getNumber()) {
            result.setMessage("申请的数量已经超过网单实际台数！");
            return result;
        }
        //差价退款验证
        int orderDifferencePriceRefundCount = orderDifferencePriceRefundService
            .getValidCountByCorderSn(orderProducts.getCOrderSn());
        if (orderDifferencePriceRefundCount > 0) {
            result.setMessage("申请的差价退款进行中不允许申请退款！");
            return result;
        }
        GroupOrders groupOrders = groupOrdersService.getByTailOrderId(Integer.parseInt(orders.getId()));
        if (groupOrders != null && orders.getOrderType() == OrderType.TYPE_GROUP_TAIL.getValue()
            && groupOrders.getStatus() != 20 && (Integer.parseInt(orders.getId()) != 5438405)) {
            result.setMessage("尾款订单已合并不允许申请退款！");
            return result;
        }
        // 初始化 款、票、货状态
        Integer paymentStat = OrderRepairsConst.PS_PAID;
        if (orderProducts.getCPaymentStatus() == 200) {
            paymentStat = OrderRepairsConst.PS_DONOTREFUND;
        }
        Integer receiptStatus = orderProducts.getIsMakeReceipt() == InvoiceConst.MR_STATE_MAKED ? OrderRepairsConst.RS_MAKEED
            : OrderRepairsConst.RS_UNMAKE;
        Integer storageStatus = StringUtil.isEmpty(orderProducts.getOutping()) ? OrderRepairsConst.SS_NOTSTOCKOUT
            : OrderRepairsConst.SS_STOCKOUT;
        Integer typeFlag = 0; // 普通网单 （目前不支持基地库网单退/换货）

        // 检查是否是基地库，如是基地库，则判断出库状态
        String jdkuCorderSn = StringUtil.isEmpty(orderProducts.getSplitRelateCOrderSn()) ? orderProducts
            .getCOrderSn() : orderProducts.getSplitRelateCOrderSn();
        ServiceResult<PurchaseGdQueue> resultPurchaseGdQueue = purchaseGdServiceImpl
            .getByOrderSn(jdkuCorderSn);
        PurchaseGdQueue purchaseGdQueue;
        if (result != null && !result.getSuccess()) {
            purchaseGdQueue = resultPurchaseGdQueue.getResult();
            if (purchaseGdQueue != null) {
                if ("3".equals(purchaseGdQueue.getStatus())
                    || "4".equals(purchaseGdQueue.getStatus())
                    || "5".equals(purchaseGdQueue.getStatus())) {
                    storageStatus = OrderRepairsConst.SS_STOCKOUT;// 1-已出库；
                } else {
                    storageStatus = OrderRepairsConst.SS_NOTSTOCKOUT;// 2-未出库；
                }
                typeFlag = 1;
            }
        }

        if ("COS".equals(orders.getSource()) && storageStatus != OrderRepairsConst.SS_NOTSTOCKOUT) {
            result.setMessage("已出库样品机订单不能申请退换货！");
            return result;
        }
        if ("DBJ".equalsIgnoreCase(orders.getSource())
            && storageStatus != OrderRepairsConst.SS_NOTSTOCKOUT) {
            result.setMessage("已出库夺宝机订单不能申请退换货！");
            return result;
        }

        // 是否是延保卡
        if (orderProducts.getProductType() == 2844) {
            typeFlag = 2;
        }

        // 店铺网单
        if (OTO_FLOW_SWITCH && orderProducts.getStockType().equals(OrderProductsNew.TYPE_STORE)) {
            typeFlag = 3;
        }
        // 退货单数据填充
        OrderRepairsNew orderRepairs = new OrderRepairsNew();
        orderRepairs.setSiteId(1);
        orderRepairs.setMemberId(orders.getMemberId());
        orderRepairs.setOrderId(Integer.parseInt(orders.getId()));
        orderRepairs.setOrderProductId(orderProducts.getId());
        orderRepairs.setRefundAmount(orderProducts.getProductAmount());// 退款金额
        orderRepairs.setAddTime((int) (new Date().getTime() / 1000));
        orderRepairs.setHandleStatus(OrderRepairsConst.HS_REVIEW);// 1-审核中
        orderRepairs.setPaymentStatus(paymentStat);// 1-已付款
        orderRepairs.setReceiptStatus(receiptStatus);
        orderRepairs.setContactName(StringUtil.isEmpty(contactName) ? orders.getConsignee()
            : contactName);
        orderRepairs.setContactMobile(StringUtil.isEmpty(contactMobile) ? orders.getMobile()
            : contactMobile);
        orderRepairs.setStorageStatus(storageStatus);
        orderRepairs.setTypeFlag(typeFlag);// 普通网单、基地库网单、延保卡标识（0-普通网单；1-基地库网单；2-延保卡;4-店铺网单）
        orderRepairs.setHpFirstAddTime(0);// HP一次质检推送时间
        orderRepairs.setHpSecondAddTime(0);// HP二次质检推送时间
        orderRepairs.setPaymentName(orders.getPaymentName());// 支付方式
        orderRepairs.setHandleRemark("");// 受理备注
        orderRepairs.setReason(reason); // 退款原因
        orderRepairs.setDescription(description); // 描述信息

        orderRepairs.setRequestServiceRemark("");// 要求描述
        orderRepairs.setRequestServiceDate(0L);// 要求服务时间
        orderRepairs.setOfflineRemark("");// 退款金额备注
        orderRepairs.setCount(count);
        if (typeFlag == 3) {
            orderRepairs.setHandleStatus(OrderRepairsConst.HS_HANDLE);
            orderRepairs.setHandleTime((Long) (System.currentTimeMillis() / 1000));
        }

        // 生成退款单号，单号规则是 网单号加上'TH'加上count(退款单数量)+1
        String repairSn = orderProducts.getCOrderSn() + "TH"
                          + (this.getCountRepairSn(orderProducts.getCOrderSn()) + 2);
        orderRepairs.setRepairSn(repairSn);

        // 根据付款方式判断是否是线下退款
        orderRepairs.setOfflineFlag(2);
        String paymentCode = orders.getPaymentCode();
        if ("chinapay".equals(paymentCode) || "alipay".equals(paymentCode)
            || "tenpay".equals(paymentCode) || "cod".equals(paymentCode)
            || "ccb".equals(paymentCode) || "lejia".equals(paymentCode)
            || "balance".equals(paymentCode) || "giftCard".equals(paymentCode)) {
            orderRepairs.setOfflineFlag(OrderRepairsConst.OFFLINEFLAG_NO);// 是否线下退款
            orderRepairs.setOfflineReason("");// 要求线下退款原因
            orderRepairs.setOfflineAmount(new BigDecimal(0));// 退款金额
        } else if ("prepaid".equals(paymentCode) || "offline".equals(paymentCode)) {
            orderRepairs.setOfflineFlag(OrderRepairsConst.OFFLINEFLAG_YES);
            String offlineReason = "订单支付方式为[" + itemService.getPaymentByCode(paymentCode)
                                   + "]，走线下退款";
            orderRepairs.setOfflineReason(offlineReason);
            orderRepairs.setOfflineAmount(orderProducts.getProductAmount());
        } else {
            orderRepairs.setOfflineFlag(OrderRepairsConst.OFFLINEFLAG_YES);
            String offlineReason = "订单支付方式为空或不能识别，走线下退款";
            orderRepairs.setOfflineReason(offlineReason);
            orderRepairs.setOfflineAmount(orderProducts.getProductAmount());
        }

        // 发票金额不等于网单金额的一律线上退款
        Invoices invoice = invoiceModel.getInvoicesByCOrderSn(orderProducts.getCOrderSn());
        if (invoice == null || invoice.getAmount().compareTo(orderProducts.getProductAmount()) != 0) {
            orderRepairs.setOfflineFlag(OrderRepairsConst.OFFLINEFLAG_NO);
            orderRepairs.setOfflineReason("发票金额不等于网单金额，走线上退款");
            orderRepairs.setOfflineAmount(new BigDecimal(0));
        }
        orderRepairs.setTypeActual(typeActual); // 退换货类型
        orderRepairs.setCOrderSnStatus(orderProducts.getStatus());

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Integer resultNum = orderRepairsNewService.insert(orderRepairs);
            log.info("[recordreturngoodsinfo]登记退换货成功" + resultNum + "条，退货信息:"
                     + JsonUtil.toJson(orderRepairs));

            if (resultNum > 0 && typeFlag == 3 && OTO_FLOW_SWITCH) {
                if (orderProducts.getStatus() < 3) {
                    insertLog(orderRepairs, userName, "登记", "");
                    insertLog(orderRepairs, userName, "审核通过", "EC订单退换货申请系统自动审核通过");
                    if (corderCancel(orderProducts, orders, result, userName, orderRepairs.getId())) {
//                        transactionManager.commit(status);
                        return result;
                    }
//                    transactionManager.rollback(status);
                    return result;
                }

                if (orderProducts.getStatus() >= 3
                    && orderProducts.getStatus() != OrderProductsNew.STATUS_CANCEL_CLOSE) {
                    orderRepairs.setTypeActual(2);
                    orderRepairs.setPaymentStatus(5);
                    orderRepairsNewService.update(orderRepairs);
                    insertLog(orderRepairs, userName, "登记", "客服人员登记退款申请");
                    insertLog(orderRepairs, userName, "审核通过", "EC订单退换货申请系统自动审核通过");
                    OrderRepairLESQueues orderRepairLESQueues = addOto(orderRepairs, orderProducts,
                        orders);
//                    if (sendOto(orderRepairLESQueues, orderRepairs)) {
//                        insertLog(orderRepairs, "系统", "提交退款申请到EC", "退货类型锁定为退货不退款");
//                        transactionManager.commit(status);
//                        result.setResult(1);
//                        result.setSuccess(true);
//                        result.setMessage("退款申请添加成功!");
//                        return result;
//                    } else {
//                        String resultMsg = orderRepairLESQueues.getLastMessage();
//                        transactionManager.rollback(status);
//                        result.setResult(0);
//                        result.setSuccess(false);
//                        result.setMessage("退款申请失败!"
//                                          + (StringUtil.isEmpty(resultMsg) ? "" : "EC返回结果失败:"
//                                                                                  + resultMsg));
//                        return result;
//                    }
                }
            }
            insertLog(orderRepairs, userName, "登记", "客服人员登记退款申请");
//            transactionManager.commit(status);
            result.setResult(1);
            result.setSuccess(true);
            result.setMessage("退款申请添加成功!");
            return result;
        } catch (Exception ex) {
//            transactionManager.rollback(status);
            log.error("[applyReturnGoods]申请退/换货(网单号:" + orderProducts.getCOrderSn() + ")，出现未知异常:",
                ex);
            throw new BusinessException("申请退/换货(网单号:" + orderProducts.getCOrderSn() + ")，出现未知异常:"
                                        + ex);
        }
    }
    public boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    public Integer getCountRepairSn(String cOrderSn) {
        Integer count = orderRepairsNewService.getCountRepairSn(cOrderSn);
        return count == null ? 0 : count;
    }
    
    /**
     * [PHP移植]退货单日志
     * 
     * @param orderRepairs
     * @param operator
     * @param operate
     * @param remark
     */
    private void insertLog(OrderRepairsNew orderRepairs, String operator, String operate, String remark) {
        OrderRepairLogsNew orderRepairLogs = new OrderRepairLogsNew();
        orderRepairLogs.setSiteId(1);
        orderRepairLogs.setAddTime(((Long) (System.currentTimeMillis() / 1000)).intValue());
        orderRepairLogs.setOrderRepairId(orderRepairs.getId());
        orderRepairLogs.setOperate("[CBS]" + (StringUtil.isEmpty(operate) ? "退货申请" : operate));
        orderRepairLogs.setOperator((StringUtil.isEmpty(operator) ? "系统" : operator));
        orderRepairLogs.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        orderRepairLogsNewService.insert(orderRepairLogs);
    }
    
    /**
     * [PHP移植]020网单取消
     * 
     * @param repairId
     * @param order
     * @param result
     */
    private boolean corderCancel(OrderProductsNew orderProduct, OrdersNew order,
                                 ServiceResult<Integer> result, String userName, Integer repairId) {
        boolean isAuto = false;
        if (orderProduct == null || orderProduct.getStatus() == OrderProductsNew.STATUS_CANCEL_CLOSE) {
            failLog(result, orderProduct.getCOrderSn(), "网单自动取消失败!");
            return false;
        }
        if (orderProduct.getStatus() > 2 && !isAuto) {
            failLog(result, orderProduct.getCOrderSn(), "订单已转EC,网单取消失败!");
            return false;
        }
        try {
            List<OrderProductsNew> orderProductsList = orderProductsNewService.getByOrderId(orderProduct.getOrderId());
            if (!order.getPaymentCode().equals("cod")
                && (orderProductsList != null && orderProductsList.size() > 1)
                && order.getPaymentStatus() == PaymentStatus.PS_UNPAID.getCode()) {
                failLog(result, orderProduct.getCOrderSn(), "该订单有多网单且未付款，不能取消！");
                return false;
            }
            String oldStatus = getStatusName(orderProduct.getStatus());
            orderProduct.setStatus(OrderProductsNew.STATUS_CANCEL_CLOSE);
            orderProduct.setIsNotice(0);
            orderProduct.setNoticeTime("");
            if (orderProduct.getCPaymentStatus() == 201
                || (orderProduct.getGiftCardNumberId() > 0 && orderProduct.getUsedGiftCardAmount()
                    .compareTo(BigDecimal.ZERO) == 1)
                || orderProduct.getBalanceAmount().compareTo(BigDecimal.ZERO) == 1) {
                orderProduct.setCPaymentStatus(206); // 待退款
            }

            // 如果网单已占用库存大于0，已释放库存等于0，方可释放库存
            // 如果是日日单订单，则只有在入库后方可释放库存
            if (orderProduct.getLockedNumber() > 0 && orderProduct.getLockedNumber() < 123456789
                && orderProduct.getUnlockedNumber() == 0
                && (orderProduct.getPdOrderStatus() == 0 || orderProduct.getPdOrderStatus() >= 50)) {
                String channel = getOrderChannel(order.getSource()); // 订单数据源渠道
                boolean flag = frozeStockFromCbsBySCode(orderProduct, channel, order.getSource(),
                    repairId);
                insertOrderProductLog(orderProduct, order, userName, userName + "做了联动释放CBS库存的操作,"
                                                                     + flag, "");
                if (flag == false) {
                    insertOrderProductLog(orderProduct, order, userName, "网单取消失败", "");
                    failLog(result, orderProduct.getCOrderSn(), "网单取消失败");
                    return false;
                }
                orderProduct.setUnlockedNumber(orderProduct.getUnlockedNumber()
                                               + orderProduct.getNumber());
            }

            if (order.getOrderType() == 1 && order.getIsProduceDaily() == 1) {
                orderProduct.setPdOrderStatus(OrderProductsNew.STATUS_CANCEL_CLOSE);
                OrdersNew tailOrder = ordersNewService.getByOrderSn(order.getRelationOrderSn());
                if (tailOrder != null) {
                    ordersNewService.updateOrderStatus(Integer.parseInt(tailOrder.getId()), 202);
                    List<OrderProductsNew> tailOrderProducts = orderProductsNewService.getByOrderId(Integer.parseInt(tailOrder.getId()));
                    for (OrderProductsNew tailOrderProduct : tailOrderProducts) {
                        tailOrderProduct.setStatus(OrderProductsNew.STATUS_CANCEL_CLOSE);
                        tailOrderProduct.setPdOrderStatus(202);
                        orderProductsNewService.update(tailOrderProduct);
                        insertOrderProductLog(tailOrderProduct, tailOrder, userName,
                            userName + "做了日日单定金订单取消的操作，同步取消尾款订单！", "");
                    }
                }
            }
            orderProductsNewService.update(orderProduct);
            insertOrderProductLog(orderProduct, order, userName, "状态由 “'" + oldStatus
                                                                 + "” 变成 “取消关闭”(联动取消)", "");
            if (!restoreBalance(userName, order, orderProduct)) { // 退返已支付的金额
                result.setSuccess(false);
                result.setMessage("退返已支付的金额失败!");
                return false;
            }
            OrderWorkflows orderWorkflows = shopOrderWorkflowsService.getByOrderProductId(orderProduct
                .getId());
            if (orderWorkflows != null) {
                orderWorkflows.setCancelPeople(userName);
                orderWorkflows.setCancelTime((Long) (System.currentTimeMillis() / 1000));
                shopOrderWorkflowsService.updateForCancelOrder(orderWorkflows);
            }
        } catch (Exception e) {
            log.error("[corderCancel]020网单取消异常：" + e);
            result.setSuccess(false);
            result.setMessage("退换货申请失败!");
            return false;
        }
        if (isAuto) {
            log.info("[corderCancel]EC同意退款,商品未出库,网单自动取消");
            result.setSuccess(true);
            result.setMessage("退换货申请成功!");
            return true;
        }
        log.info("[corderCancel]EC订单未同步EC,网单自动取消");
        result.setSuccess(true);
        result.setMessage("退换货申请成功!");
        return true;
    }
    

    /**
     * 取消网单记录失败日志
     * 
     * @param result
     * @param corderSn
     * @param message
     */
    private void failLog(ServiceResult<Integer> result, String corderSn, String message) {
        log.error("[corderCancel]网单号：" + corderSn + message);
        result.setMessage(message);
        result.setSuccess(false);
        result.setResult(0);
    }
    

    /**
     * [PHP移植]取状态名称
     * 
     * @param key
     * @return
     */
    public String getStatusName(Object key) {
        Map<String, String> names = new HashMap<String, String>();
        names.put("STRAT_STATUS", "处理中");
        names.put("SYNC_CBS", "已占用库存");
        names.put("SYNC_HP", "同步到HP");
        names.put("SYNC_EC", "同步到EC");
        names.put("DISPATCH_NETPOINT", "分配网点");
        names.put("LES_SHIPPING", "待出库");
        names.put("WAIT_VERIFY", "待审核");
        names.put("WAIT_TRANSSHIPIN", "待转运入库");
        names.put("WAIT_TRANSSHIPOUT", "待转运出库");
        names.put("WAIT_DELIVERY", "待发货");
        names.put("NETPOINT_REFUSE", "网点拒绝");
        names.put("WAIT_DELIVER", "待交付");
        names.put("USER_SIGN", "用户签收");
        names.put("COMPLETED_CLOSE", "完成关闭");
        names.put("USER_REJECTION", "用户拒收");
        names.put("CANCEL_CLOSE", "取消关闭");
        String statusName = names.get(key);
        return statusName == null ? "未知" : statusName;
    }
    
/*
    * [PHP移植]根据订单来源，判断库存渠道
    * 
    * @param orderSource
    *            网单来源
    * @return
    */
   private String getOrderChannel(String orderSource) {
       if (orderSource.equals("TBSC") || orderSource.equals("TOPBOILER")
           || orderSource.equals("TOPFENXIAO") || orderSource.equals("TONGSHUAI")
           || orderSource.equals("TONGSHUAIFX") || orderSource.equals("TOPMOBILE")
           || orderSource.equals("TOPFENXIAODHSC") || orderSource.equals("TOPSHJD")
           || orderSource.equals("TOPBUYBANG") || orderSource.equals("ZPTH")
           || orderSource.equals("TOPXB") || orderSource.equals("TOPFENXIAOXB")
           || orderSource.equals("ORIGIN_BLPMS")) {
           return "TB"; // 淘宝官方旗舰店渠道
       } else if (Arrays.asList("1", "DXS", "MOBILE", "C2BWASHER", "CHINAUNICOM", "RRS", "COS",
           "CK", "CK_MOBILE", "SCFX", "DBJ", "SQXW", "GMZX", "TSMOBILE", "TSPC", "SNYG", "XPZC",
           "BLPHH", "PCNEW", "YDYZ").contains(orderSource)) {
           return "SC"; // 商城
       } else if (Arrays.asList("CORPORATE", "DALOU", "CCBSC", "CCBSR", "YIHAODIAN", "YHD",
           "CORPORATE_SJMG", "115", "114", "113", "112", "95533", "ICBC", "YMX", "YHDZY", "ZSH",
           "DDW", "YLW", "DCYH").contains(orderSource)) {
           return "DKH"; // 企业渠道
       } else if (Arrays.asList("JDMK").contains(orderSource)) {
           return "JD";
       } else if (Arrays.asList("TMMK").contains(orderSource)) {
           return "MK";
       } else {
           return "";
       }
   }
   
   /**
    * [PHP移植]调用释放冻结接口
    * 
    * @return
    */
   private boolean frozeStockFromCbsBySCode(OrderProductsNew orderProducts, String source,
                                            String channel, Integer repairId) {
       if (orderProducts.getNumber() == 0) {
           log.error("[frozeStockFromCbsBySCode]占用或释放数量不能为0");
           return false;
       }
       if (StringUtil.isEmpty(channel)) {
           log.error("[frozeStockFromCbsBySCode]渠道不能为空");
           return false;
       }
       if (StringUtil.isEmpty(orderProducts.getCOrderSn())) {
           log.error("[frozeStockFromCbsBySCode]网单号不能为空");
           return false;
       }

       if (orderProducts.getStockType() != "WA") {
           return releaseStockBystoreCode(orderProducts, repairId);
       }
       return releaseFrozenStock(orderProducts.getSku(), orderProducts.getSCode(),
           orderProducts.getCOrderSn(), "", "", orderProducts.getNumber(), repairId);
   }

   /**
    * [PHP移植]订单日志
    * 
    * @param orderProduct
    * @param orders
    * @param operator
    * @param changeLog
    * @param remark
    */
   void insertOrderProductLog(OrderProductsNew orderProduct, OrdersNew orders, String operator,
                              String changeLog, String remark) {
       OrderOperateLogs orderOperateLog = new OrderOperateLogs();
       orderOperateLog.setOrderId(orderProduct.getOrderId());
       orderOperateLog.setOrderProductId(orderProduct.getId());
       orderOperateLog.setNetPointId(orderProduct.getNetPointId());
       orderOperateLog.setOperator(!StringUtil.isEmpty(operator) ? operator : "系统");
       orderOperateLog.setChangeLog(!StringUtil.isEmpty(changeLog) ? changeLog : "");
       orderOperateLog.setRemark(!StringUtil.isEmpty(remark) ? remark : "");
       orderOperateLog.setLogTime(((Long) (System.currentTimeMillis() / 1000)).intValue());
       orderOperateLog.setStatus(orderProduct.getStatus());
       orderOperateLog.setPaymentStatus(orders.getPaymentStatus());
       orderOperateLogsShopService.insert(orderOperateLog);
   }
   /**
    * [PHP移植]OTO释放库存
    * 
    * @param orderProducts
    * @return
    */
   private boolean releaseStockBystoreCode(OrderProductsNew orderProducts, Integer repairId) {
       String cOrderSn = "";
       if (orderProducts.getNumber() == 0) {
           log.error("[releaseStockBystoreCode]释放数量不能为0");
           return false;
       }
       if (StringUtil.isEmpty(orderProducts.getCOrderSn())) {
           log.error("[releaseStockBystoreCode]网单号不能为空");
           return false;
       }
       cOrderSn = orderProducts.getCOrderSn();
       if (!StringUtil.isEmpty(orderProducts.getSplitRelateCOrderSn())) {
           cOrderSn = orderProducts.getSplitRelateCOrderSn();
       }
       return releaseFrozenStock(orderProducts.getSku(), "", cOrderSn, "EC",
           orderProducts.getSCode(), orderProducts.getNumber(), repairId);
   }
   /**
    * [CBS-WEB] 释放库存接口
    * 
    * @return
    */
   public boolean releaseFrozenStock(String sku, String secCode, String billNo, String stockType,
                                     String storeCode, Integer quantity, Integer repairId) {
       Map<String, Object> paramMap = new HashMap<String, Object>();
       paramMap.put("billNo", billNo);
       paramMap.put("storeCode", storeCode);
       paramMap.put("secCode", secCode);
       paramMap.put("sku", sku);
       paramMap.put("stockType", stockType);
       paramMap.put("quantity", quantity);
       if (BaseStock.STOCKTYPE_EC.equalsIgnoreCase(stockType)) {
           boolean successFlag = releaseStoreStock(billNo, storeCode, sku, quantity, repairId);
           if (successFlag) {
               return true;
           } else {
               log.error("[releaseFrozenStock] EC库存释放失败");
               this.recordLog("EC库存释放失败", repairId);
               return false;
           }
       }

       if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(secCode) || StringUtil.isEmpty(billNo)
           || quantity <= 0) {
           String message = "输入参数不正确，无法释放冻结库存";
           log.error("[releaseFrozenStock]" + message);
           this.recordLog(message, repairId);
           return false;
       }

       if (!add(billNo)) {
           String message = "不能重复调用此接口";
           log.error("[releaseFrozenStock]" + message);
           this.recordLog(message, repairId);
           return false;
       }

       // 调用释放冻结接口
       try {
           InventoryBusinessTypes billType = InventoryBusinessTypes.RELEASE_BY_ZBCC;
           ServiceResult<Boolean> result = orderThirdCenterHopStockServiceImpl.releaseFrozenStockQty(sku, quantity,
               billNo, billType);
           if (!result.getResult()) {// 释放冻结库存
               log.error("[releaseFrozenStock]释放冻结库存失败！result:" + result.getMessage());
               this.recordLog(result.getMessage(), repairId);
               return false;
           } else {// 释放冻结库存成功
               log.info("[releaseFrozenStock]释放冻结库存成功!");
               updatePurchaseGdQueue(billNo);
               orderQueueExtendService.cancelOrderExt(billNo, OrderQueueExtend.CANCEL_STATUS);
               return true;
           }
       } catch (Exception ex) {
           log.error("[releaseFrozenStock]释放冻结库存失败！exception:", ex);
           this.recordLog("释放冻结库存发生未知异常：" + ex.getMessage(), repairId);
           return false;
       } finally {
           del(billNo);
       }
   }
   /**
    * [CBS-WEB移植]
    * 
    * @return
    */
   private boolean releaseStoreStock(String billNo, String storeCode, String sku,
                                     Integer releaseQty, Integer repairId) {
       Map<String, Object> paramMap = new HashMap<String, Object>();
       paramMap.put("billNo", billNo);
       paramMap.put("storeCode", storeCode);
       paramMap.put("sku", sku);
       paramMap.put("releaseQty", releaseQty);
       String message = null;
       if (StringUtil.isEmpty(billNo) || StringUtil.isEmpty(storeCode) || StringUtil.isEmpty(sku)) {
           message = "输入参数不正确，无法释放冻结库存";
           log.error("[releaseStoreStock]" + message);
           this.recordLog(message, repairId);
           return false;
       }
       ServiceResult<Boolean> result = eStoreServiceImpl.releaseByRefNo(billNo, sku, storeCode,
           releaseQty);
       if (!result.getResult()) {// 释放冻结库存
           log.error("[releaseStoreStock]" + result.getMessage());
           this.recordLog(result.getMessage(), repairId);
           return false;
       } else {// 释放冻结库存成功
           message = "释放冻结库存成功";
           log.info("[releaseStoreStock]" + message);
           this.recordLog(message, repairId);
           return true;
       }
   }
   
   /**
    * 接口记录日志
    * 
    * @param remark
    */
   private void recordLog(String remark, Integer orderRepairs) {
       // 记录接口日志
       try {
           OrderRepairLogsNew orderRepairLogs = new OrderRepairLogsNew();
           orderRepairLogs.setSiteId(1);
           orderRepairLogs.setOrderRepairId(orderRepairs);
           orderRepairLogs.setOperate("cbs释放库存");
           orderRepairLogs.setOperator("cbs退换货");
           orderRepairLogs.setAddTime(((Long) (System.currentTimeMillis() / 1000)).intValue());
           orderRepairLogs.setRemark(remark);
           orderRepairLogsNewService.insert(orderRepairLogs);
       } catch (Exception e) {
           log.error("get-available-stock接口发生异常，记录接口日志失败:", e);
       }
   }
   /**
    * [php移植]添加oto数据
    * 
    * @return
    */
   private OrderRepairLESQueues addOto(OrderRepairsNew orderRepairs, OrderProductsNew orderProducts,
                                       OrdersNew orders) throws Exception {
       String repairSn = orderRepairs.getRepairSn(); // 退货单号
       String yCOrderSn;
       MemberInvoices memberInvoice = memberInvoicesService.get(orders.getMemberInvoiceId());
       String receiptInfo = orders.getReceiptInfo();
//       Receipt receipt = JsonUtil.fromJson(receiptInfo);
       String province = regionsService.get(orders.getProvince()).getCode();
       String city = regionsService.get(orders.getCity()).getCode();
       String region = regionsService.get(orders.getRegion()).getCode();
       if (!StringUtil.isEmpty(orderProducts.getSplitRelateCOrderSn())) {
           yCOrderSn = orderProducts.getSplitRelateCOrderSn();
       } else {
           yCOrderSn = orderProducts.getCOrderSn();
       }
       // 订单
       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Map<String, Object> pushData = new HashMap<String, Object>();
       pushData.put("signId", "ehaier"); // 调用者签名
       pushData.put("orderNo", orders.getOrderSn()); // 订单编号
       pushData.put("originalNetOrderNo", orders.getOrderSn()); // 原订单编号
       pushData.put("sellerNick", "海尔商城"); // 卖家昵称
       pushData.put("orderNoTmall", orders.getOrderSn()); // 平台单号（天猫京东等平台单号）
       pushData.put("orderType", "Y"); // 订单类型 N正常、Y退货单
       pushData.put("created", df.format(orders.getAddTime()));
       pushData.put("payTime", df.format(orders.getPayTime()));
       pushData.put("payment", orders.getPaidAmount());
       pushData.put("postFee", "0.00"); // 运费
       pushData.put("creditCardFee", "0.00"); // 使用信誉卡支付金额
       pushData.put("invoiceName", memberInvoice == null ? "" : memberInvoice.getInvoiceTitle()); // 发票抬头

       String invoiceType = "";
//       if (receipt != null && !StringUtil.isEmpty(receipt.receiptType)) {
//           invoiceType = receipt.receiptType.equals(2) ? "3" : "2";
//       }
       pushData.put("invoiceType", invoiceType); // 发票类型
       pushData.put("Invoice", memberInvoice == null ? "" : memberInvoice.getInvoiceTitle()); // 发票信息
       pushData.put("receiverName", orders.getConsignee()); // 收货人姓名
       pushData.put("receiverState", province); // 收货人所在省
       pushData.put("receiverCity", city); // 收货人所在城市
       pushData.put("receiverDistrict", region); // 收货人所在地区
       pushData.put("receiverAddress", orders.getAddress()); // 收货人地址
       pushData.put("receiverZip", orders.getZipcode()); // 收货人邮编
       pushData.put("receiverMobile", orders.getMobile()); // 收货人手机号
       pushData.put("receiverPhone", "");// 收货人电话
       pushData.put("buyerMessage", orderRepairs.getReason());// 买家留言
       pushData.put("remarks", orderRepairs.getReason());// 备注
       pushData.put("type", orders.getPaymentCode().equals("cod") ? "cod" : "fixed");// 类型 fixed一口价step预售cod 货到付款

       // 网单
       List<Map<String, Object>> corderPushDataList = new ArrayList<Map<String, Object>>();
       Map<String, Object> corderPushData = new HashMap<String, Object>();
       corderPushData.put("netOrderNo", repairSn);
       corderPushData.put("originalNetOrderNo", yCOrderSn);
       corderPushData.put("itemNo", orderProducts.getSku());
       corderPushData.put("price", orderProducts.getPrice());
       corderPushData.put("number", orderRepairs.getCount()); // 商品数量
       corderPushData.put("payment", orderRepairs.getRefundAmount()); // 金额
       corderPushData.put("arAmount",
           orders.getPaymentCode().equals("cod") ? orderRepairs.getRefundAmount() : "0");// 应收金额(货到付款用)
       corderPushData.put("storeId", orderProducts.getSCode()); // 分配门店
       corderPushData.put("remarks", ""); // 备注
       corderPushDataList.add(corderPushData);
       pushData.put("orderItems", corderPushDataList);

       OrderRepairLESQueues orderRepairLESQueues = new OrderRepairLESQueues();
       orderRepairLESQueues.setRecordId(0);
       orderRepairLESQueues.setSiteId(1);
       orderRepairLESQueues.setAddTime(((Long) (System.currentTimeMillis() / 1000)));
       orderRepairLESQueues.setOrderProductId(orderRepairs.getOrderProductId());
       orderRepairLESQueues.setOrderRepairId(orderRepairs.getId());
       orderRepairLESQueues.setCount(50);
       orderRepairLESQueues.setPushData(JsonUtil.toJson(pushData));
       orderRepairLESQueues.setReturnData("");
       orderRepairLESQueues.setSuccessTime(0l);
       orderRepairLESQueues.setSuccess(0);
       orderRepairLESQueues.setLastMessage("");
       orderRepairLESQueues.setVomCount(50);
       orderRepairLESQueues.setVomSuccess(0);
       orderRepairLESQueues.setVomSuccessTime(0l);
       orderRepairLESQueues.setVomReturnData("");
       orderRepairLESQueues.setVomSuccess(0);
       orderRepairLESQueues.setVomLastMessage("");
       orderRepairLESQueues.setVomPushData("");
       orderRepairLESQueues.setFlag(3);
       orderRepairLESQueuesService.insert(orderRepairLESQueues);
       return orderRepairLESQueues;
   }
   /**
    * 根据退货 repairSn查询退货信息
    * 
    * @param repairSn
    * @return
    */
   public OrderRepairsNew getOrderRepairs(String repairSn) {
       return orderRepairsNewService.getByRepairSn(repairSn);

   }
   public OrderRepairLESRecords getByLesOrderSn(String lesOrderSn, String cOrderSn) {
       return orderRepairLESRecordsService.getByLesOrderSn(lesOrderSn, cOrderSn);
   }
   /**
    * 记录orderRepairLESRecodes日志
    * 
    * @param orderRepairLESRecodes
    */
   public void saveOrderRepairLESRecodes(OrderRepairLESRecords orderRepairLESRecodes) {
       orderRepairLESRecordsService.insert(orderRepairLESRecodes);
   }
   

   public boolean updateLesRecordAfterJLIN(OrderRepairLESRecords orderRepairLESRecords) {
       return orderRepairLESRecordsService.updateLesRecordAfterJLIN(orderRepairLESRecords) > 0;
   }
   /**
    * 生成已入库日日单尾款订单
    */
   public void genRepairCancelCorder() {
       List<OrderRepairLESRecords> orderRepairLESRecords = orderRepairLESRecordsService
           .getWaitforCancelOP(100);
       for (OrderRepairLESRecords orderRepairLESRecord : orderRepairLESRecords) {
           OrderProductsNew orderProduct = orderProductsNewService.get(orderRepairLESRecord
               .getOrderProductId());
           if (null == orderProduct) {
               orderRepairLESRecord.setOpCancelFlag(2);
               orderRepairLESRecordsService.updateOpCancelFlag(orderRepairLESRecord);
               continue;
           }
           if (orderProduct.getStatus() == OrderProductsNew.STATUS_CANCEL_CLOSE) {
               orderRepairLESRecord.setOpCancelFlag(3);
               orderRepairLESRecordsService.updateOpCancelFlag(orderRepairLESRecord);
               continue;
           }

           // 仅第一次入10库及22库在处理范围内
           if (orderRepairLESRecord.getOperate() == OrderRepairLESRecords.OPERATE_CHANGE_OUT
               || orderRepairLESRecord.getOperate() == OrderRepairLESRecords.OPERATE_CHANGE_IN) {
               orderRepairLESRecord.setOpCancelFlag(4);
               orderRepairLESRecordsService.updateOpCancelFlag(orderRepairLESRecord);
               continue;
           }
           if (orderRepairLESRecord.getStorageType() != 10
               && orderRepairLESRecord.getStorageType() != 22) {
               orderRepairLESRecord.setOpCancelFlag(5);
               orderRepairLESRecordsService.updateOpCancelFlag(orderRepairLESRecord);
               continue;
           }
           Map<String, Object> cancel2Hp = this.cancel2Hp(orderProduct, "系统");
           this.doEditCOrderStatus(orderProduct.getId(), OrderProductsNew.STATUS_CANCEL_CLOSE);
           orderRepairLESRecord.setOpCancelFlag(1);
           orderRepairLESRecordsService.updateOpCancelFlag(orderRepairLESRecord);
       }

   }
   
   /**
    * 根据 3Wvom退货单号、sku 获取退换货网单
    * @param vomRepairSn
    * @return
    */
   public OrderRepairsNew getByVomRepairSn(String vomRepairSn, String sku) {
       WwwHpRecords wwwHpRecords = wwwHpRecordsService.getByVomRepairSnAndSku(vomRepairSn, sku);
       if (wwwHpRecords == null) {
           throw new BusinessException("[OrderRepairModel]:3W-菜鸟退换货入库出现异常,根据(vomRepairSn:"
                                       + vomRepairSn + ",sku:" + sku + ")没有找到wwwHpRecords记录");
       }
       return orderRepairsNewService.get(wwwHpRecords.getOrderRepairId());
   }
   
   public boolean updateAfterVomAccepted3W(String corderSn, Date acceptTime, String expNo) {
   	OrderRepairTcRecords orderRepairTcRecords = orderRepairTcRecordsService
           .getByRecordSn(corderSn);
       if (orderRepairTcRecords == null) {
           log.warn("VOM接单后更新退货单失败(3W正品退仓)：关联的记录不存在，单号：" + corderSn);
           return true;
       }
       
       //针对套机处理
       List<OrderRepairTcRecords> tcRecords= orderRepairTcRecordsService.queryByOrderRepairTcId(orderRepairTcRecords.getOrderRepairTcId());
       if(tcRecords ==null || tcRecords.size()==0){
       	log.warn("VOM接单后更新退货单失败(3W正品退仓)：根据退仓单id查询退仓记录信息，退仓单id：" + orderRepairTcRecords.getOrderRepairTcId());
           return true;
       }
        for(OrderRepairTcRecords record:tcRecords){
       	 OrderRepairTcLogs orderRepairTcLogs = new OrderRepairTcLogs();
            orderRepairTcLogs.setAddTime((int) (new Date().getTime() / 1000));
            orderRepairTcLogs.setOperator("CBS系统");
            orderRepairTcLogs.setOperate("(3W正品退仓)vom开提单成功");
            orderRepairTcLogs.setOrderRepairTcId(record.getOrderRepairTcId());
            orderRepairTcLogs.setTcRecordsId(record.getId());
            orderRepairTcLogs.setRemark("(3W正品退货)已同步到VOM,快递单号：" + expNo);
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//            TransactionStatus status = transactionManager.getTransaction(def);
            try {
            	 long lesSnTime=acceptTime.getTime()/1000;
            	 record.setLesOrderSn(expNo);
            	 record.setLesOrderSnTime(new Long(lesSnTime).intValue());
                orderRepairTcRecordsService.updateAfterVomAccepted(record);
                orderRepairTcLogsService.insert(orderRepairTcLogs);
                //更改退仓单状态 表 OrderRepairTcs 字段 caiNiaoTcExtStatus = 1 已接单 
                orderRepairTcsService.updateTcExtStatus(record.getOrderRepairTcId(),1);
//                transactionManager.commit(status);
            } catch (Exception e) {
//                transactionManager.rollback(status);
                log.error("VOM接单后更新退货信息失败(3W正品退货)：", e);
                throw new BusinessException("未知错误：" + e.getMessage());
            }
       }
       return true;
   }
   public boolean updateAfterVomRejected3W(String corderSn, Date rejectedTime, String reason) {

   	OrderRepairTcRecords orderRepairTcRecords = orderRepairTcRecordsService
               .getByRecordSn(corderSn);
       if (orderRepairTcRecords == null) {
           log.warn("VOM接单后更新退货单失败(3W正品退仓)：关联的记录不存在，单号：" + corderSn);
           return true;
       }
       OrderRepairTcLogs orderRepairTcLogs = new OrderRepairTcLogs();
       orderRepairTcLogs.setAddTime((int) (new Date().getTime() / 1000));
       orderRepairTcLogs.setOperator("系统");
       orderRepairTcLogs.setOperate("(3W正品退货)提交入库-网点不检验请求到vom，退仓单号：" + orderRepairTcRecords.getVomTcSn());
       orderRepairTcLogs.setOrderRepairTcId(orderRepairTcRecords.getOrderRepairTcId());
       orderRepairTcLogs.setTcRecordsId(orderRepairTcRecords.getId());
       orderRepairTcLogs.setRemark("VOM拒单(3W正品退货)：" + reason);
       DefaultTransactionDefinition def = new DefaultTransactionDefinition();
       def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//       TransactionStatus status = transactionManager.getTransaction(def);
       try {
           orderRepairTcLogsService.insert(orderRepairTcLogs);
       	//更改退仓单状态 表 OrderRepairTcs 字段 caiNiaoTcExtStatus = 3 已取消 
           orderRepairTcsService.updateTcExtStatus(orderRepairTcRecords.getOrderRepairTcId(),3);
//           transactionManager.commit(status);
       } catch (Exception e) {
//       	transactionManager.rollback(status);
           log.error("VOM据单后更新退货信息失败(3W正品退货)：", e);
           throw new BusinessException("未知错误：" + e.getMessage());
       }
       return true;
   }
   
   /**退货单 待检入库（3W正品退仓）
    * @param cOrderSn 记录单号
    * @param lesIoNo 入库单号
    * @param billTime 出入库时间
    * @param secCode 库位
    * @param num 数量
    * @return
    */
    public ServiceResult<Boolean> repairOrderIn3W(String cOrderSn, String lesIoNo,Date billTime,String secCode,Integer num) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        OrderRepairTcRecords orderRepairTcRecords=orderRepairTcRecordsService.getByRecordSn(cOrderSn);
        if (orderRepairTcRecords == null) {
            log.error("（3W正品退仓）退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo + ",billTime:"+ billTime +",secCode:"+ secCode +",num+"+ num +")，LES退货单为null");
            result.setResult(true);
            return result;
        }
       //针对套机处理
        List<OrderRepairTcRecords> records= orderRepairTcRecordsService.queryByOrderRepairTcId(orderRepairTcRecords.getOrderRepairTcId());
        if(records ==null || records.size()==0){
        	 log.warn("VOM接单后更新退货单失败(3W正品退仓)：根据退仓单id查询退仓记录信息，退仓单id：" + orderRepairTcRecords.getOrderRepairTcId());
        	 result.setResult(true);
            return result;
        }
         for(OrderRepairTcRecords tcRecords:records){
       	  if (!StringUtil.isEmpty(tcRecords.getLesOutPing())) {// 已处理过，不再处理
                 result.setResult(true);
                 return result; 
             }
             tcRecords.setLesOutPing(lesIoNo);
             tcRecords.setLesOutPingTime(new Long(billTime.getTime()/1000).intValue());
             tcRecords.setWwwScode(secCode);
             tcRecords.setVomNum(num);
             // 组装数据 - 退货单操作日志
             OrderRepairTcLogs orderRepairTcLogs = new OrderRepairTcLogs();
             orderRepairTcLogs.setAddTime((int) (new Date().getTime() / 1000));
             orderRepairTcLogs.setOperator("CBS系统");
             orderRepairTcLogs.setOperate("vom回传退仓入库结果");
             orderRepairTcLogs.setOrderRepairTcId(tcRecords.getOrderRepairTcId());
             orderRepairTcLogs.setTcRecordsId(tcRecords.getId());
             orderRepairTcLogs.setRemark("凭证号："+ tcRecords.getLesOutPing());

             DefaultTransactionDefinition def = new DefaultTransactionDefinition();
             def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//             TransactionStatus status = transactionManager.getTransaction(def);
             try {
                 // 更新LES退货单
                 orderRepairTcRecordsService.updateAfterLesInStorage(tcRecords);
                 // 记录日志
                 orderRepairTcLogsService.insert(orderRepairTcLogs);
             	  //更改退仓单状态 表 OrderRepairTcs 字段 caiNiaoTcExtStatus = 2  已出库
                 orderRepairTcsService.updateTcExtStatus(tcRecords.getOrderRepairTcId(),2);
                 // 提交事务
//                 transactionManager.commit(status);
             } catch (Exception ex) {
                 // 回滚事务
//                 transactionManager.rollback(status);
                 log.error("（3W正品退仓）退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo + ",billTime:"+ billTime +",secCode:"+ secCode +",num+"+ num +")，出现未知异常:", ex);
                 // 记录日志
                 result.setResult(false);
                 result.setMessage("发生未知异常：" + ex.getMessage());
                 return result;
             }
         }
         // 返回执行成功
         result.setResult(true);
         return result;
    }
    

    /**
     * [PHP移植]oto日志
     * 
     */
    private void insertLogOto(OrderRepairsNew orderRepair, String remark) {
        insertLog(orderRepair, "", "同步到EC系统", remark);
    }
    
    /**
     * [PHP移植]退返已支付的金额（一般在取消订单时调用）
     * 
     * @param order
     * @return
     */
    public boolean restoreBalance(String userName, OrdersNew order, OrderProductsNew orderProducts) {
        int balanceAmountCt = orderProducts.getBalanceAmount().compareTo(BigDecimal.ZERO);
        try {
            if (balanceAmountCt == 1) { // 网单余额退还
                Members members = shopMembersService.get(order.getMemberId());
                members.setMoney(orderProducts.getBalanceAmount().add(members.getMoney()));
                createcOrderLog(orderProducts, order, members, 3);
                shopMembersService.update(members);
                insertOrderProductLog(orderProducts, order, userName, "取消网单自动余额退款",
                    "取消网单自动余额退还“" + orderProducts.getBalanceAmount() + "”元");
            }
            int usedGiftCardAmount = orderProducts.getUsedGiftCardAmount().compareTo(
                BigDecimal.ZERO);
            if (orderProducts.getGiftCardNumberId() != null && usedGiftCardAmount == 1) {
                GiftCardUsedLogs giftCardUsedLogs = getByOrderProductId(
                    orderProducts.getGiftCardNumberId(), orderProducts.getId(), 2);
                if (giftCardUsedLogs == null) {
                    GiftCardNumbers giftCardNumbers = giftCardNumbersService
                        .getByGiftcardnumbersId(orderProducts.getGiftCardNumberId());
                    BigDecimal cardNumber = giftCardNumbers.getBalance().add(
                        orderProducts.getUsedGiftCardAmount());
                    giftCardNumbers.setBalance(cardNumber);
                    giftCardNumbersService.update(giftCardNumbers);
                    giftCardUsedLogs = new GiftCardUsedLogs();
                    giftCardUsedLogs.setGiftCardId(giftCardNumbers.getProductId());
                    giftCardUsedLogs.setGiftCardNumberId(giftCardNumbers.getId());
                    giftCardUsedLogs.setMemberId(order.getMemberId());
                    giftCardUsedLogs.setMemberName("");
                    giftCardUsedLogs.setMemberEmail(order.getMemberEmail());
                    giftCardUsedLogs.setUsedType(2);
                    giftCardUsedLogs.setBeforeBalanceAmount(giftCardNumbers.getBalance());
                    giftCardUsedLogs.setAmount(orderProducts.getUsedGiftCardAmount());
                    BigDecimal afterBalanAmount = giftCardNumbers.getBalance().add(
                        orderProducts.getUsedGiftCardAmount());
                    giftCardUsedLogs.setAfterBalanceAmount(afterBalanAmount);
                    giftCardUsedLogs.setOrderSn(order.getOrderSn());
                    giftCardUsedLogs.setOrderProductId(orderProducts.getId());
                    giftCardUsedLogs.setSystemRemark("前台下单使用礼品卡");
                    giftCardUsedLogsService.insert(giftCardUsedLogs);

                    insertOrderProductLog(orderProducts, order, userName, "取消网单自动礼品卡金额退款",
                        "取消网单自动礼品卡金额退还“" + orderProducts.getUsedGiftCardAmount() + "”元");
                }
            }
            return true;
        } catch (Exception e) {
            log.error("[restoreBalance]020网单取消退返已支付的金额异常:", e);
            return false;
        }
    }
    /**
     * [CBS-WEB移植]更新基地库采购
     * 
     * @param orderSn
     */
    private void updatePurchaseGdQueue(String orderSn) {
        PurchaseGdQueue gdQueue = new PurchaseGdQueue();
        gdQueue.setOrdersn(orderSn);
        gdQueue.setStatus(PurchaseGdQueue.GD_STATUS_CANCEL);
        ServiceResult<Boolean> result = purchaseGdServiceImpl.updatePurchaseGdQueue(gdQueue);
        if (!result.getSuccess()) {
            log.error("updatePurchaseGdQueue:更新基地库采购状态失败");
        }
    }
    private Map<String, Object> cancel2Hp(OrderProductsNew orderProducts, String sysName) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	OrdersNew order = null;
    	if (orderProducts.getStatus() == OrderProductsNew.STATUS_STRAT_STATUS
    			|| orderProducts.getStatus() == OrderProductsNew.STATUS_FROZEN_STOCK
    			|| "STORE".equals(orderProducts.getStockType())) {
    		order = ordersNewService.get(orderProducts.getOrderId());
    		if (null != order && !"BLPHH".equals(order.getSource())) {
    			map.put("flag", true);
    			return map;
    		}
    	}
    	HPFaileds hpFaileds = hpFailedsService.getByOrderProductId(orderProducts.getId());
    	if (null != hpFaileds) {
    		if (1 == hpFaileds.getSuccess()) {
    			map.put("flag", true);
    			return map;
    		}
    	}
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("orderNo", orderProducts.getCOrderSn());
    	paramMap.put("cancelDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    			.replace(" ", "T"));
    	ServiceResult<String> result = null;
    	try {
    		//            result = transOrderCancelFromEHAIERToHPService.noticeHpToSend(paramMap);
    	} catch (Exception e) {
    		this.addHpFailed(hpFaileds, orderProducts.getId(), paramMap);
    		Map<String, Object> orderLogMap = new HashMap<String, Object>();
    		orderLogMap.put("operator", "系统");
    		orderLogMap.put("changeLog", "系统做了联动取消网单的操作:作废HP失败,失败原因:接口有异常,异常信息:'" + e.getMessage());
    		orderLogMap.put("remark", "");
    		this.insertOrderOperateLogs(orderProducts, orderLogMap,
    				order == null ? 0 : order.getPaymentStatus());
    		map.put("flag", false);
    		map.put("message", "作废HP失败,失败原因:接口有异常,异常信息:" + e.getMessage());
    		map.put("hpFlag", 0);
    		return map;
    	}
    	Map<String, Object> fromJson = JsonUtil.fromJson(result.getResult());
    	if (result.getSuccess() == true) {
    		if ("S".equals(fromJson.get("flag"))) {
    			this.addHpSuccess(hpFaileds, orderProducts.getId(), paramMap);
    			Map<String, Object> orderLogMap = new HashMap<String, Object>();
    			orderLogMap.put("operator", "系统");
    			orderLogMap.put("changeLog", "系统做了联动取消网单的操作:作废HP成功");
    			orderLogMap.put("remark", "");
    			this.insertOrderOperateLogs(orderProducts, orderLogMap,
    					order == null ? 0 : order.getPaymentStatus());
    			map.put("flag", true);
    			map.put("message", fromJson.get("msg"));
    			map.put("hpFlag", "S");
    			return map;
    		}

    	}
    	this.addHpFailed(hpFaileds, orderProducts.getId(), paramMap);
    	Map<String, Object> orderLogMap = new HashMap<String, Object>();
    	orderLogMap.put("operator", "系统");
    	orderLogMap.put("changeLog", "做了联动取消网单的操作:作废HP失败");
    	orderLogMap.put("remark", "");
    	this.insertOrderOperateLogs(orderProducts, orderLogMap,
    			order == null ? 0 : order.getPaymentStatus());
    	map.put("flag", true);
    	map.put("message", fromJson.get("msg"));
    	map.put("hpFlag", "S");
    	return map;
    }
    private void addHpFailed(HPFaileds hpFaileds, Integer orderProductId,
    		Map<String, Object> paramMap) {
    	if (null == hpFaileds) {
    		hpFaileds = new HPFaileds();
    		hpFaileds.setOrderProductId(orderProductId);
    		hpFaileds.setPushData(JsonUtil.toJson(paramMap));
    		hpFaileds.setCount(-1);
    		hpFaileds.setCreateType(0);
    		hpFaileds.setOperator("");
    		hpFaileds.setSuccess(0);
    		hpFaileds.setAddTime(new Date().getTime() / 1000);
    		hpFaileds.setSuccessTime(new Date().getTime() / 1000);
    		hpFaileds.setLastMessage("");
            hpFailedsService.insert(hpFaileds);
    	} else if (new Integer(0).equals(hpFaileds.getSuccess())) {
    		hpFaileds.setCount(hpFaileds.getCount() + 1);
    		hpFaileds.setAddTime(new Date().getTime() / 1000);
    		hpFaileds.setSuccess(0);
    		hpFaileds.setSuccessTime(new Date().getTime() / 1000);
    		hpFaileds.setLastMessage("SOAP接口请求失败");
            hpFailedsService.updateHpFailed(hpFaileds);
    	}
    }

    public void doEditCOrderStatus(Integer orderProductId, Integer corderStatus) {
        OrderProductsNew orderProducts = orderProductsNewService.get(orderProductId);
        if (orderProducts == null) {
            log.error("对不起，网单不存在！");
            return;
        }
        OrdersNew orders = ordersNewService.get(orderProducts.getOrderId());
        if (orders == null) {
            log.error("订单不存在！");
            return;
        }
        /*
         * Map<String,Object> map = new HashMap<String, Object>();
         * map.put("STRAT_STATUS","处理中"); map.put("SYNC_CBS","已占用库存");
         * map.put("SYNC_HP","同步到HP"); map.put("SYNC_EC","同步到EC");
         * map.put("DISPATCH_NETPOINT","分配网点"); map.put("LES_SHIPPING","待出库");
         * map.put("WAIT_VERIFY","待审核"); map.put("WAIT_TRANSSHIPIN","待转运入库");
         * map.put("WAIT_TRANSSHIPOUT","待转运出库"); map.put("WAIT_DELIVERY","待发货");
         * map.put("NETPOINT_REFUSE","网点拒绝"); map.put("WAIT_DELIVER","待交付");
         * map.put("USER_SIGN","用户签收"); map.put("COMPLETED_CLOSE","完成关闭");
         * map.put("USER_REJECTION","用户拒收"); map.put("CANCEL_CLOSE","取消关闭");
         */
        String[] statusNames = new String[] { "处理中", "已占用库存", "同步到HP", "同步到EC", "分配网点", "待出库",
                "待审核", "待转运入库", "待转运出库", "待发货", "网点拒绝", "待交付", "用户签收", "完成关闭", "用户拒收", "用户取消",
                "无法执行", "取消关闭" };
        /* 数据安全判断 */
        Integer[] statusselect = new Integer[] { 0, 110, 130 };
        String[] orderStatusNames = new String[] { "未确认", "部分缺货", "已确认", "已完成", "已取消" };
        Integer oldOrderStatus = orders.getOrderStatus();
        String oldStatus = orderStatusNames[oldOrderStatus];
        String oldStatuProducts = statusNames[orderProducts.getStatus()];
        if (!Arrays.asList(statusselect).contains(corderStatus)) {
            log.error("对不起，数据有误请重新提交！");
            return;
        }
        if (corderStatus == OrderProductsNew.STATUS_CANCEL_CLOSE) {
            // 网单取消，取消或作废开票
//            this.cancelInvoice(orderProducts);
        }
        if (corderStatus == OrderProductsNew.STATUS_STRAT_STATUS
            || corderStatus == OrderProductsNew.STATUS_COMPLETED_CLOSE) {
            // 网单还原到初始状态，发票状态也还原。
            if (orderProducts.getIsMakeReceipt() == 10) {
                orderProducts.setIsMakeReceipt(9);
                // $orderProducts->isMakeReceipt =
                // OrderProducts::MR_STATE_NEEDMAKE;
                // self::message('发票状态已从取消开票改为待开票。', 'notice');
            }
        }
        orderProductsNewService.update(orderProducts);
        Map<String, Object> orderLogMap = new HashMap<String, Object>();
        orderLogMap.put("operator", "系统");
        orderLogMap.put("changeLog", "网单状态强制修改：网单状态由" + oldStatuProducts + "变成"
                                     + statusNames[corderStatus]);
        orderLogMap.put("remark", "");
        this.insertOrderOperateLogs(orderProducts, orderLogMap, orders.getPaymentStatus());

        List<OrderProductsNew> byOrderId = orderProductsNewService.getByOrderId(orderProducts.getOrderId());
        List<Integer> statusList = new ArrayList<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (OrderProductsNew orderP : byOrderId) {
            statusList.add(orderP.getStatus());
            if (map.containsKey(orderP.getStatus())) {
                map.put(orderP.getStatus(), map.get(orderP.getStatus()) + 1);
            } else {
                map.put(orderP.getStatus(), 1);
            }
        }
        if (map.get(OrderProductsNew.STATUS_CANCEL_CLOSE)
            + map.get(OrderProductsNew.STATUS_COMPLETED_CLOSE) == statusList.size()) {
            Integer orderStatus = null;
            if (map.containsKey(OrderProductsNew.STATUS_COMPLETED_CLOSE)) {
                orderStatus = 203;
            } else if (map.containsKey(OrderProductsNew.STATUS_COMPLETED_CLOSE)) {
                orderStatus = 202;
            }
            if (oldOrderStatus == orderStatus) {
                log.error("网单状态强制修改：网单已修改为“" + statusNames[orderProducts.getStatus()] + "”，订单状态重复！");
                return;
            }
            orders.setOrderStatus(orderStatus);
//            ordersNewService.updateOrderStatus(orders);
            Map<String, Object> orderLogMap2 = new HashMap<String, Object>();
            orderLogMap.put("operator", "系统");
            orderLogMap
                .put("changeLog", "网单状态强制修改：订单变更由“" + oldStatus + "”变成“" + orderStatus + "”");
            orderLogMap.put("remark", "");
            this.insertOrderLog(orders, orderLogMap2);
        }
    }
    /**
     * 保存子订单记录
     * 
     * @param orderProducts
     * @param order
     * @param members
     * @param logType
     */
    public void createcOrderLog(OrderProductsNew orderProducts, OrdersNew order, Members members,
                                Integer logType) {
        BigDecimal expense = new BigDecimal(0);
        BigDecimal b1 = new BigDecimal(0);
        switch (logType) {
            case 2:
                expense = b1.subtract(orderProducts.getBalanceAmount());
                break;
            case 3:
                expense = order.getBalanceAmount();
                break;
            case 4:
                expense = b1.subtract(orderProducts.getBalanceAmount());
                break;
            default:
                expense = new BigDecimal(0);
                break;
        }
        MemberMoneyLogs memberMoneyLogs = new MemberMoneyLogs();
        memberMoneyLogs.setMemberId(members.getId());
        memberMoneyLogs.setMemberName(members.getUsername());
        memberMoneyLogs.setOrderId(Integer.parseInt(order.getId()));
        memberMoneyLogs.setOrderSn(orderProducts.getCOrderSn());
        memberMoneyLogs.setExpense(expense);
        memberMoneyLogs.setMoney(members.getMoney());
        memberMoneyLogs.setLogType(logType);
        orderThirdCenterMemberMoneyLogsDao.insert(memberMoneyLogs);
    }
    
    /**
     * [PHP移植]根据网单号查询金额增减记录
     * 
     * @param giftCardNumberId
     * @param orderProductId
     * @param usedType
     * @return
     */
    private GiftCardUsedLogs getByOrderProductId(Integer giftCardNumberId, Integer orderProductId,
                                                 Integer usedType) {
        if (usedType == 1 || usedType == 2) {
            return giftCardUsedLogsService.getByGiftCardNumberIdAndOrderProductId(giftCardNumberId,
                orderProductId, usedType);
        }
        return null;
    }
    

    private void insertOrderOperateLogs(OrderProductsNew orderProducts, Map<String, Object> map,
                                        Integer paymentStatus) {
        OrderOperateLogs orderOperateLogs = new OrderOperateLogs();
        orderOperateLogs.setOrderId(orderProducts.getOrderId());
        orderOperateLogs.setOrderProductId(orderProducts.getId());
        orderOperateLogs.setNetPointId(orderProducts.getNetPointId());
        orderOperateLogs.setOperator((String) map.get("operator"));
        orderOperateLogs.setChangeLog((String) map.get("changeLog"));
        orderOperateLogs.setRemark((String) map.get("remark"));
        orderOperateLogs.setLogTime((int) (new Date().getTime() / 1000));
        orderOperateLogs.setPaymentStatus(paymentStatus);
        orderOperateLogs.setSiteId(0);
        orderOperateLogs.setStatus(0);
        orderOperateLogsShopService.insert(orderOperateLogs);
    }
    
    private void addHpSuccess(HPFaileds hpFaileds, Integer orderProductId,
    		Map<String, Object> paramMap) {
    	boolean insertFlag = false;
    	if (null == hpFaileds) {
    		hpFaileds = new HPFaileds();
    		insertFlag = true;
    	}
    	hpFaileds.setOperator("sys");
    	hpFaileds.setCreateType(0);
    	hpFaileds.setOrderProductId(orderProductId);
    	hpFaileds.setPushData(JsonUtil.toJson(paramMap));
    	hpFaileds.setCount(hpFaileds.getCount() == null ? 0 : hpFaileds.getCount() + 1);
    	hpFaileds.setSuccess(1);
    	hpFaileds.setAddTime(new Date().getTime() / 1000);
    	hpFaileds.setSuccessTime(new Date().getTime() / 1000);
    	hpFaileds.setLastMessage("");
    	if (insertFlag) {
            hpFailedsService.insert(hpFaileds);
    	} else {
            hpFailedsService.updateHpFailed(hpFaileds);
    	}
    }
    

    private void insertOrderLog(OrdersNew orders, Map<String, Object> map) {
        OrderOperateLogs orderOperateLogs = new OrderOperateLogs();
        orderOperateLogs.setOrderId(Integer.parseInt(orders.getId()));
        orderOperateLogs.setOperator("系统");
        orderOperateLogs.setChangeLog((String) map.get("changeLog"));
        orderOperateLogs.setRemark((String) map.get("remark"));
        orderOperateLogs.setLogTime((int) (new Date().getTime() / 1000));
        orderOperateLogs.setStatus(orders.getOrderStatus());
        orderOperateLogs.setPaymentStatus(orders.getPaymentStatus());
        orderOperateLogsShopService.insert(orderOperateLogs);
    }
    
  
}
