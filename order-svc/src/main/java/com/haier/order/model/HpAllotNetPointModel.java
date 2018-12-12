package com.haier.order.model;

import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 接收HP分配网点信息逻辑处理
 *
 * @Filename: HpAllotNetPointModel.java
 * @Version: 1.0
 */
@Service("hpAllotNetPointModel")
public class HpAllotNetPointModel {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(HpAllotNetPointModel.class);
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private OrdersNewService ordersNewService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;
    @Autowired
    private LesQueuesService lesQueuesService;
    @Autowired
    private HPQueuesService hpQueuesService;
//    @Autowired
//    private DataSourceTransactionManager transactionManager;
    @Autowired
    private ShopOrderWorkflowsService shopOrderWorkflowsService;
    @Autowired
    private AllotNetPointService allotNetPointService;
    @Autowired
    private ItemModel itemModel;


    public void allotNetPoint() {
        long startTime = System.currentTimeMillis();
        List<AllotNetPoint> allotNetPoints = allotNetPointService.getByStatus(AllotNetPoint.STATUS_INITIAL, 1000);
        if (allotNetPoints == null || allotNetPoints.isEmpty()) {
            return;
        }
        for (AllotNetPoint netPoint : allotNetPoints) {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//            TransactionStatus status = transactionManager.getTransaction(def);
            try {
                process(netPoint);
                //提交事务
//                transactionManager.commit(status);
            } catch (Exception e) {
                //回滚事务
//                transactionManager.rollback(status);
                log.error("处理HP分配网点信息出现异常", e);
            }
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        log.info("处理HP分配网点信息条数:" + allotNetPoints.size() + ",总共执行时间:" + time + "毫秒,平均每条"
                + time / allotNetPoints.size() + "毫秒");
    }

    private void process(AllotNetPoint netPoint) {

        String cOrderSn = netPoint.getORDER_NO();//网单编号
        if (StringUtil.isEmpty(cOrderSn, true)) {
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_FAIL, "网单编号为空");
            return;
        }
        OrderProductsNew orderProducts = orderProductsNewService.getByCOrderSn(cOrderSn);
        if (orderProducts == null) {
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_FAIL, "网单编号不存在");
            return;
        }
        if (orderProducts.getStatus().intValue() == OrderProductStatus.COMPLETED_CLOSE.getCode()
                .intValue()
                || orderProducts.getStatus().intValue() == OrderProductStatus.CANCEL_CLOSE.getCode()
                .intValue()) {
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_FAIL, "网单已关闭");
            return;
        }
        OrdersNew orders = ordersNewService.get(orderProducts.getOrderId());
        if (orders == null) {
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_FAIL, "不存在对应的订单");
            return;
        }
        if (OrderStatus.OS_CANCEL.getCode().intValue() == orders.getOrderStatus()) {
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_FAIL, "订单已取消");
            return;
        }

        String customerCode = netPoint.getCUSTOMER_CODE();
        if (StringUtil.isEmpty(customerCode, true)) {
            String procRemark = netPoint.getPROC_REMARK();
            if ("型号解析错误".equals(procRemark)) {
                procRemark = procRemark + "(请商城相关人员尽快到HP系统中维护此型号)";
            } else {
                procRemark = procRemark + "网点8码为空";
            }
            saveOrderProductLog(orderProducts, procRemark, "HP分配网点", orders.getPaymentStatus());//PHP有记录
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_FAIL, procRemark);
            return;
        }

        //未分配网点，则分配网点
        if (orderProducts.getNetPointId().intValue() == 0) {
            NetPoints netPoints = getByNetPointN8(customerCode);//由网点8码netPointN8查询
            if (netPoints == null) {
                String message = customerCode + "网点在海尔商城不存在，请及时维护";
                HPQueues hpQueues = hpQueuesService.getByOrderProductId(Math.toIntExact(orderProducts.getId()));
                if (hpQueues != null) {
                    hpQueues.setLastMessage(message);
                    hpQueuesService.updateHPAllotNetPoint(hpQueues);
                }
                //记录订单操作日志
                saveOrderProductLog(orderProducts, message, "HP分配网点", orders.getPaymentStatus());
                allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_INITIAL,
                        message);
                return;
            }
            orderProducts.setNetPointId(netPoints.getId());
            String remark = "";
            if (orderProducts.getStatus().intValue() >= OrderProductStatus.LES_SHIPPING.getCode()
                    .intValue()) {
                //获取网单状态对应的状态名称
                String orderProductStatusName = OrderProductStatus
                        .getByCode(Integer.valueOf(orderProducts.getStatus())).getName();
                remark = "网单状态已为" + orderProductStatusName + "，则不修改为已分配网点";
            } else {
                orderProducts.setStatus(OrderProductStatus.DISPATCH_NET_POINT.getCode().intValue());
                remark = "已分配给网点" + netPoints.getNetPointName() + "。登记时间："
                        + DateParseToString(netPoint.getENTER_TIME()) + "，派工失败时间："
                        + DateParseToString(netPoint.getSB_DATE()) + "，派工成功时间："
                        + DateParseToString(netPoint.getASSIGN_DATE()) + "。";
            }
            orderProducts.setHpRegisterDate(DateParseToInteger(netPoint.getENTER_TIME()));//登记时间
            orderProducts.setHpFailDate(DateParseToInteger(netPoint.getSB_DATE()));//派工失败时间
            orderProducts.setHpFinishDate(DateParseToInteger(netPoint.getASSIGN_DATE()));//派工成功时间
            //更新网单
            orderProductsNewService.updateHPAllotNetPoint(orderProducts);
            //更新订单全流程表
            updateOrderWorkflows(orderProducts, orders);
            //添加les队列
            addLesQueues(Math.toIntExact(orderProducts.getId()), "createOrder");
            //记录订单操作日志
            saveOrderProductLog(orderProducts, remark, "HP分配网点", orders.getPaymentStatus());
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_SUCCESS, "成功！");
            return;
        }

        //网单开提单前允许改配网点
        if (!StringUtil.isEmpty(orderProducts.getOutping(), true)) {//已分配网点且已出库，则不再分配网点
            //记录订单操作日志
            saveOrderProductLog(orderProducts, "已分配过网点", "HP分配网点", orders.getPaymentStatus());
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_SUCCESS, "已分配过网点");
            return;
        }
        //已分配网点，但未出库，则可以改配网点
        NetPoints netPoints = getByNetPointN8(customerCode);//由网点8码netPointN8查询
        if (netPoints == null) {
            String message = "网点" + customerCode + "在海尔商城不存在，请及时维护";
            //记录订单操作日志
            saveOrderProductLog(orderProducts, message, "HP改配网点", orders.getPaymentStatus());
            allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_INITIAL, message);
            return;
        }
        orderProducts.setNetPointId(netPoints.getId());
        orderProducts.setHpRegisterDate(DateParseToInteger(netPoint.getENTER_TIME()));//登记时间
        orderProducts.setHpFailDate(DateParseToInteger(netPoint.getSB_DATE()));//派工失败时间
        orderProducts.setHpFinishDate(DateParseToInteger(netPoint.getASSIGN_DATE()));//派工成功时间
        //更新网单
        orderProductsNewService.updateHPAllotNetPoint(orderProducts);
        //更新订单全流程表
        updateOrderWorkflows(orderProducts, orders);
        //记录订单操作日志
        String remark = "改派网点" + netPoints.getNetPointName() + "。登记时间："
                + DateParseToString(netPoint.getENTER_TIME()) + "，派工失败时间："
                + DateParseToString(netPoint.getSB_DATE()) + "，派工成功时间："
                + DateParseToString(netPoint.getASSIGN_DATE()) + "。";
        saveOrderProductLog(orderProducts, remark, "HP改配网点", orders.getPaymentStatus());
        //返回处理结果
        allotNetPointService.updateById(netPoint.getId(), AllotNetPoint.STATUS_SUCCESS, "成功！");
    }

    /**
     * 记录网单操作日志
     *
     * @param orderProducts
     * @param remark
     * @param changeLog
     * @param paymentStatus
     */
    private void saveOrderProductLog(OrderProductsNew orderProducts, String remark, String changeLog,
                                     Integer paymentStatus) {
        OrderOperateLogs orderOperateLog = new OrderOperateLogs();
        orderOperateLog.setSiteId(1);
        orderOperateLog.setOrderId(orderProducts.getOrderId());
        orderOperateLog.setOrderProductId(Math.toIntExact(orderProducts.getId()));
        orderOperateLog.setNetPointId(orderProducts.getNetPointId());
        orderOperateLog.setOperator("CBS系统");
        orderOperateLog.setChangeLog(changeLog);
        orderOperateLog.setRemark(remark);
        orderOperateLog.setStatus(Integer.valueOf(orderProducts.getStatus()));
        orderOperateLog.setPaymentStatus(paymentStatus);
        shopOrderOperateLogsService.insert(orderOperateLog);

    }

    /**
     * 转换时间格式为整型
     *
     * @param dateStr
     * @return
     */
    private Integer DateParseToInteger(String dateStr) {
        if (StringUtil.isEmpty(dateStr, true)) {
            return 0;
        }
        Date date = DateUtil.parse(dateStr, "yyyy-MM-dd'T'HH:mm:ss");
        Long longDate = date.getTime() / 1000;
        return longDate.intValue();
    }

    /**
     * 转换时间格式为字符型
     *
     * @param dateStr
     * @return
     */
    private String DateParseToString(String dateStr) {
        if (StringUtil.isEmpty(dateStr, true)) {
            return "";
        }
        Date date = DateUtil.parse(dateStr, "yyyy-MM-dd'T'HH:mm:ss");
        return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 更新订单全流程表
     *
     * @param orderProducts
     * @param orders
     */
    private void updateOrderWorkflows(OrderProductsNew orderProducts, OrdersNew orders) {

        long allTime = 0;
        if (orders.getIsCod().intValue() == 1) {
            allTime = orders.getCodConfirmTime();
        } else if (OrderType.TYPE_GROUP_ADVANCE.getIntValue() == orders.getOrderType()
                || OrderType.TYPE_GROUP_ADVANCE_TAIL.getIntValue() == orders.getOrderType()) {
            allTime = orders.getTailPayTime();
        } else {
            allTime = orders.getPayTime();
        }
        //计算应/预警派工完成时间
        OrderWorkflows orderWorkflows = shopOrderWorkflowsService
                .getByOrderProductId(Math.toIntExact(orderProducts.getId()));
        if (orderWorkflows.getMustHpAllotNetPointTime().intValue() == 0) {
            orderWorkflows.setMustHpAllotNetPointTime(allTime + 3000);
        }
        if (orderWorkflows.getEwHpAllotNetPointTime().intValue() == 0) {
            orderWorkflows.setEwHpAllotNetPointTime(allTime + 2400);
        }
        //修改HP分配网点时间
        orderWorkflows.setHpAllotNetPointTime(new Date().getTime() / 1000);
        shopOrderWorkflowsService.updateForPubCountTime(orderWorkflows);

    }

    /**
     * 添加les队列
     *
     * @param orderProductId
     * @param action
     */
    private void addLesQueues(Integer orderProductId, String action) {
        int count = lesQueuesService.getCountByOpId(orderProductId);
        if (count == 0) {
            LesQueues lesQueues = new LesQueues();
            lesQueues.setOrderProductId(orderProductId);
            lesQueues.setAction(action);
            lesQueues.setPushData("");
            lesQueues.setSuccess(0);
            lesQueues.setCount(0);
            lesQueues.setAddTime(Long.valueOf(System.currentTimeMillis() / 1000L).intValue());
            lesQueues.setLastMessage("");
            lesQueues.setIsLock(0);
            lesQueues.setIsStop(0);
            lesQueues.setSuccessTime(0L);
            lesQueues.setLastTryTime(0L);
            List<LesQueues> lesQueuesList = new ArrayList<LesQueues>();
            lesQueuesList.add(lesQueues);
            lesQueuesService.insert(lesQueuesList);
        }
    }

    /**
     * 由网点8码查询网点信息
     *
     * @param netPointN8
     * @return
     */
    private NetPoints getByNetPointN8(String netPointN8) {
        ServiceResult<NetPoints> result = getsByNetPointN8(netPointN8);
        if (result == null || !result.getSuccess()) {
            return null;
        }
        return result.getResult();
    }

    public ServiceResult<NetPoints> getsByNetPointN8(String netPointN8) {
        ServiceResult<NetPoints> result = new ServiceResult<NetPoints>();
        try {
            NetPoints netPoints = itemModel.getByNetPointN8(netPointN8);
            result.setResult(netPoints);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("根据netPointN8查询网点时，发生未知异常：", e);
            result.setMessage("根据netPointN8查询网点发生未知异常：" + e.getMessage());
            result.setResult(null);
            result.setSuccess(false);
        }
        return result;
    }
}
