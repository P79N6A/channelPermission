package com.haier.logistics.Model;
import com.google.gson.GsonBuilder;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.OrderProductsNew;

import com.haier.shop.model.OrderRepairHPQueues;
import com.haier.shop.model.OrderRepairHPRecordsNew;

import com.haier.shop.model.OrderRepairLESQueues;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairLogsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.service.CorderStatusToLesService;
import com.haier.shop.service.GiftCardNumbersService;
import com.haier.shop.service.GiftCardUsedLogsService;
import com.haier.shop.service.GroupOrdersService;
import com.haier.shop.service.HPFailedsService;
import com.haier.shop.service.MemberInvoicesService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrderRepairHPQueuesService;
import com.haier.shop.service.OrderRepairHPRecordsnNewService;
import com.haier.shop.service.OrderRepairLESQueuesService;
import com.haier.shop.service.OrderRepairLESRecordsService;
import com.haier.shop.service.OrderRepairLogsNewService;
import com.haier.shop.service.OrderRepairTcLogsService;
import com.haier.shop.service.OrderRepairTcRecordsService;
import com.haier.shop.service.OrderRepairTcsService;
import com.haier.shop.service.OrderRepairsNewService;
import com.haier.shop.service.OrdersNewService;
import com.haier.shop.service.RegionsService;
import com.haier.shop.service.ShopMembersService;
import com.haier.shop.service.ShopOrderWorkflowsService;
import com.haier.shop.service.WwwHpRecordsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@Configuration
public class OrderRepairModel {
    private static Logger log             = LogManager.getLogger(OrderModel.class);
    private static final List<String> ALLOWSOURCES    = Arrays.asList("COS", "DBJ");           // 可受理的订单数据源列表
    private static final boolean          OTO_FLOW_SWITCH = true;
  /*  @Autowired
    private DataSourceTransactionManager transactionManager;*/
    @Autowired
    private OrderRepairLogsNewService orderRepairLogsNewService;
    @Autowired
    private OrderRepairHPQueuesService orderRepairHPQueuesService;
    @Autowired
    private OrderRepairLESQueuesService orderRepairLESQueuesService;
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
              
            // 返回执行成功
            result.setResult(true);
            return result;
        } catch (Exception ex) {
            // 回滚事务

            // 记录日志
            log.error("退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo + ")，出现未知异常:", ex);
            result.setResult(false);
            result.setMessage("发生未知异常：" + ex.getMessage());
            return result;
        }
    }
    /*======================11111========================================*/
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
    /*======================22222222222222=======================*/
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

              
        } catch (Exception e) {

            log.error("VOM接单后更新退货信息失败：", e);
            throw new BusinessException("未知错误：" + e.getMessage());
        }
        return true;
    }
    public boolean updateAfterVomRejected(String corderSn, Date rejectedTime, String reason){

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

              
        } catch (Exception e) {
            /*transactionManager.rollback(status);*/
            log.error("VOM据单后更新退货信息失败：", e);
            throw new BusinessException("未知错误：" + e.getMessage());
        }
        return true;
    }
}
