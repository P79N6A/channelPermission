package com.haier.afterSale.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.model.InvoiceModel;
import com.haier.afterSale.model.MakeOutInvoiceModel;
import com.haier.afterSale.model.OrderModel;
import com.haier.afterSale.model.OrderRepairCheckModel;
import com.haier.afterSale.model.OrderRepairModel;
import com.haier.afterSale.service.OrderRepairService;
import com.haier.afterSale.util.Ustring;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.CorderStatusToLes;
import com.haier.shop.model.GroupOrders;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.stock.model.InvThTransaction;
import com.haier.stock.model.OrderProductStatus;
@Service
public class OrderRepairServiceImpl implements OrderRepairService {
    private static Logger log = LoggerFactory.getLogger(OrderRepairServiceImpl.class);
    @Autowired
    private OrderRepairCheckModel orderRepairCheckModel;
    @Autowired
    private OrderRepairModel orderRepairModel;
    @Autowired
    private ThTransactionServiceImpl thTransactionService;
    @Autowired
    private OrderModel orderModel;
    @Autowired
    private MakeOutInvoiceModel invoiceMakeOutModel;
    @Autowired
    private InvoiceModel invoiceModel;

    public OrderRepairCheckModel getOrderRepairCheckModel() {
        return orderRepairCheckModel;
    }

    public void setOrderRepairCheckModel(OrderRepairCheckModel orderRepairCheckModel) {
        this.orderRepairCheckModel = orderRepairCheckModel;
    }

    @Override
    public ServiceResult<Boolean> checkPass(Integer id, String operator, String remark) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            orderRepairCheckModel.checkPass(id, operator, remark);
            result.setMessage("退款申请已受理，进入退款流程！");
            result.setResult(true);
        } catch (BusinessException be) {
            log.error("[OrderRepairServiceImpl]审核通过时业务错误,orderRepairId=" + id, be.getMessage());
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("[OrderRepairServiceImpl]审核通过时发生未知异常,orderRepairId=" + id, e);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("审核通过时发生未知异常,请联系管理员！");
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> checkNotPass(Integer id, String operator, String remark) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            orderRepairCheckModel.checkNotPass(id, operator, remark);
            result.setMessage("退款申请已驳回！");
            result.setResult(true);
        } catch (Exception e) {
            log.error("[OrderRepairServiceImpl]审核驳回时发生未知异常,orderRepairId=" + id, e);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("审核驳回时发生未知异常,请联系管理员！");
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> closeOrderRepairForComplete() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            orderRepairModel.closeOrderRepairForComplete();
        } catch (Exception e) {
            log.error("不良品退货单自动受理完成失败", e);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("不良品退货单自动受理完成失败");
        }
        return result;
    }

    private void updateSucStatus(List<InvThTransaction> thTransList) {
        if (thTransList == null || thTransList.size() <= 0) {
            log.info("退货:更新不良品退货，关闭网单订单及写作废工单无数据");
            return;
        }
        Integer repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_SUC;
        for (InvThTransaction entity : thTransList) {
            try {
                repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_SUC;
                String repairSn = entity.getChannelOrderSn();
                // String remark = "退换货已入库，网单同步取消关闭";
                String cOrderSn = repairSn.replaceAll("TH.*", "");
                OrderProductsNew orderProducts = orderModel.getOrderProductByCOrderSn(cOrderSn);
                if (orderProducts == null) {
                    log.warn("[不良品退货][不存在对应的网单cOrderSn=" + cOrderSn + "]");
                    repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_ORDER_CANCEL_UNDONE;
                    thTransactionService.updateRepairStatus(repairSn, repairStatus, "网单不存在");
                    continue;
                }
                OrdersNew orders = orderModel.getOrderById(orderProducts.getOrderId());
                if (orders == null) {
                    log.warn("[不良品退货][不存在对应的订单orderId=" + orderProducts.getOrderId() + "]");
                    repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_ORDER_CANCEL_UNDONE;
                    thTransactionService.updateRepairStatus(repairSn, repairStatus, "订单不存在");
                    continue;
                }

                /**
                 * 1.取消关闭订单,如果已经取消的单无需取消
                 */
                int opStatus = orderProducts.getStatus().intValue();
                if (!orderProducts.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
                    boolean closeOk = orderModel.forceCancelClose(orderProducts, orders);
                    repairStatus = !closeOk
                        ? InvThTransaction.TRANS_IN_REPAIR_STATUS_ORDER_CANCEL_UNDONE
                        : InvThTransaction.TRANS_IN_REPAIR_STATUS_ORDER_CANCEL_DONE;
                    String closeMsg = "取消关闭退货单成功";
                    if (!closeOk) {
                        closeMsg = "取消关闭退货单失败";
                    } else {
                        //2016-10-02 3W订单不退货不推送HP和LES
                        //待审核，待发货，待交付，待转运入库，待转运出库这5种状态需要推送LES
                        if ((opStatus == 10 || opStatus == 11 || opStatus == 12 || opStatus == 40
                             || opStatus == 70)
                            && !"3W".equalsIgnoreCase(orderProducts.getStockType())) {
                            CorderStatusToLes corderStatusToLes = new CorderStatusToLes();
                            corderStatusToLes.setOrderproductid(orderProducts.getId());
                            corderStatusToLes.setCordersn(orderProducts.getCOrderSn());
                            corderStatusToLes.setPushdata("");
                            corderStatusToLes.setSuccess(0);
                            corderStatusToLes.setCount(0);
                            corderStatusToLes.setAddtime(
                                Long.valueOf(System.currentTimeMillis() / 1000L).intValue());
                            corderStatusToLes.setLastmessage("");
                            corderStatusToLes.setSuccesstime(0);
                            corderStatusToLes.setCreatetype(4);//固定值 4 【1.网点买单，2，物理买单3，用户要货 4，不良品入库取消网单，】
                            corderStatusToLes.setCorderflag("GS");//固定值 GS 【正向单手工关闭 GS, 逆向单 TS】
                            orderRepairModel.insertCorderStatusToLes(corderStatusToLes);
                        }
                    }
                    thTransactionService.updateRepairStatus(repairSn, repairStatus, closeMsg);
                }
                if (InvThTransaction.TRANS_IN_REPAIR_STATUS_ORDER_CANCEL_UNDONE
                    .equals(repairStatus)) {
                    //关单没成功，先不作废HP工单
                    continue;
                }

                //2017-07-21调用支付中心回退接口
                String[] message = { "" };
                boolean payBackFlag = orderModel.payCenterFallBack(orderProducts, orders, repairSn,
                    message);
                if (!payBackFlag) {
                    repairStatus = 8;//调用支付中心接口失败，实体在stock里，不想release,暂时先写固定值
                    thTransactionService.updateRepairStatus(repairSn, repairStatus, message[0]);
                    continue;
                }

                /**
                 * 2.作废HP工单
                 */
                String hpMsg = "HP作废工单成功";
                //2016-10-02 3W订单不退货不推送HP和LES
                if (!"3W".equalsIgnoreCase(orderProducts.getStockType())) {
                    boolean cancelHpOk = orderRepairModel.cancelHpOrder(orderProducts, orders);
                    repairStatus = !cancelHpOk
                        ? InvThTransaction.TRANS_IN_REPAIR_STATUS_CANCEL_HP_UNDONE
                        : InvThTransaction.TRANS_IN_REPAIR_STATUS_CANCELHP;
                    if (!cancelHpOk) {
                        hpMsg = "作废HP工单失败";
                    }
                } else {
                    repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_CANCELHP;
                    hpMsg = "3W不作废HP,直接改状态";
                }
                /**
                 * 3.更新退货列表中的退货状态
                 */
                thTransactionService.updateRepairStatus(repairSn, repairStatus, hpMsg);

            } catch (Exception e) {
                log.error("退货:更新不良品退货，关闭网单订单及写作废工单数据失败，" + e.getMessage());
            }
        }
    }

    private void updateOrderRepairStatusSuc(List<InvThTransaction> thTransList) {
        if (thTransList == null || thTransList.size() <= 0) {
            log.info("不良品退货:更新不良品作废发票及入日日顺21库数据为空");
            return;
        }
        try {
            Integer repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_INIT;
            for (InvThTransaction invTrans : thTransList) {
                repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_INIT;
                String repairSn = invTrans.getChannelOrderSn();
                Integer orderStatus = OrderProductStatus.CANCEL_CLOSE.getCode();
                // String remark = "退换货已入库，网单同步取消关闭";
                String cOrderSn = repairSn.replaceAll("TH.*", "");

                OrderProductsNew orderProducts = orderModel.getOrderProductByCOrderSn(cOrderSn);
                if (orderProducts == null) {
                    log.warn("[不良品退货][不存在对应的网单cOrderSn=" + cOrderSn + "]");
                    repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_FAILD;
                    thTransactionService.updateRepairStatus(repairSn, repairStatus, "网单不存在");
                    continue;
                }
                OrdersNew orders = orderModel.getOrderById(orderProducts.getOrderId());
                if (orders == null) {
                    log.warn("[不良品退货][不存在对应的订单orderId=" + orderProducts.getOrderId() + "]");
                    repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_FAILD;
                    thTransactionService.updateRepairStatus(repairSn, repairStatus, "订单不存在");
                    continue;
                }

                /**
                 * 2.修改货物状态为入
                 */
                // if (invTrans.getInStatus() > InvThTransaction.TRANS_IN_STATUS_HPLES) {
                orderRepairModel.updateForRepairSns(repairSn, "系统");
                //}
                /**
                 * 5.作废发票
                 */
                if (orderStatus == OrderProductStatus.CANCEL_CLOSE.getCode()) {
                    //作废发票
                    Invoices invoices = invoiceModel.getInvoicesByCorderSn(cOrderSn);
                    if (invoices == null) {//发票不存在
                        repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_SUC;
                        thTransactionService.updateRepairStatus(repairSn, repairStatus,
                            "没找到发票,更新成功");

                        continue;
                    }
                    if (Ustring.getString(invoices.getState()) != Ustring.getString(InvoiceConst.COMPLETE_STATE)) {//已开票的发票才可以作废
                        repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_FAILD;
                        thTransactionService.updateRepairStatus(repairSn, repairStatus, "发票状态不对");
                        continue;
                    }
                    boolean suc = false;
                    if (Ustring.getString(invoices.getStatusType()) == Ustring.getString(InvoiceConst.INVALID_KIND)
                        && Ustring.getString(invoices.getSuccess()) == Ustring.getString(InvoiceConst.SUCCESS)) {//已经作废成功
                        //repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_FAILD;
                        thTransactionService.updateRepairStatus(repairSn, repairStatus, "发票已作废");
                        suc = true;
                        //continue;
                    }
                    if (!suc) {
                        invoices.setStatusType(InvoiceConst.INVALID_KIND.intValue());//推送作废
                        invoices.setSuccess(0);//待推送
                        invoices.setTryNum(0);
                        invoices.setInvalidReason("不良品退货");
                        invoiceMakeOutModel.updateInvoice(invoices, false);
                    }
                }

                repairStatus = InvThTransaction.TRANS_IN_REPAIR_STATUS_SUC;
                /**
                 * 3.更新退货列表中的退货状态
                 */
                thTransactionService.updateRepairStatus(repairSn, repairStatus, "更新成功");

            }
        } catch (Exception e) {
            log.error("不良品作废发票，更新不良品入库状态失败", e);
        }
    }

    @Override
    public ServiceResult<Boolean> updateOrderRepairEnterIn21() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();

        ServiceResult<List<InvThTransaction>> closeThTransListService = thTransactionService
            .queryRepairTransData(InvThTransaction.TRANS_IN_REPAIR_STATUS_ORDER_CANCEL_DONE);
        List<InvThTransaction> thTransList = closeThTransListService.getResult();
        updateSucStatus(thTransList);
        ServiceResult<List<InvThTransaction>> cancelHPTransListService = thTransactionService
            .queryRepairTransData(InvThTransaction.TRANS_IN_REPAIR_STATUS_CANCEL_HP_UNDONE);
        thTransList = cancelHPTransListService.getResult();
        updateSucStatus(thTransList);
        ServiceResult<List<InvThTransaction>> thTransListService = thTransactionService
            .queryRepairTransData(InvThTransaction.TRANS_IN_REPAIR_STATUS_SUC);
        thTransList = thTransListService.getResult();
        updateSucStatus(thTransList);

        //2017-07-21调用结算中心回退接口
        ServiceResult<List<InvThTransaction>> payFallBackListService = thTransactionService
            .queryRepairTransData(8);
        thTransList = payFallBackListService.getResult();
        updateSucStatus(thTransList);

        thTransListService = thTransactionService
            .queryRepairTransData(InvThTransaction.TRANS_IN_REPAIR_STATUS_INIT);
        thTransList = thTransListService.getResult();
        updateOrderRepairStatusSuc(thTransList);

        return result;
    }

    @Override
    public ServiceResult<GroupOrders> getByTailOrderId(Integer tailOrderId) {
        ServiceResult<GroupOrders> result = new ServiceResult<GroupOrders>();
        try {
            GroupOrders groupOrders = orderRepairModel.getByTailOrderId(tailOrderId);
            result.setSuccess(true);
            result.setResult(groupOrders);
            result.setMessage("查询成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询失败");
            log.error("查询失败" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> getCountByOrderProductId(Integer orderProductId) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            Integer alreadyCount = orderRepairModel.getCountByOrderProductId(orderProductId);
            result.setSuccess(true);
            result.setResult(alreadyCount);
            result.setMessage("查询成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询失败");
            log.error("查询失败" + e.getMessage());
        }
        return result;
    }

    /**
     * 退换货申请逻辑未完成，弃用
     * @param orderRepairs
     * @param userName
     * @return
     * @see com.haier.cbs.order.service.OrderRepairService#recordReturnGoodsInfo()
     */
    @Override
    public ServiceResult<Integer> recordReturnGoodsInfo(OrderRepairsNew orderRepairs,
                                                        String userName) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            Integer countRow = orderRepairModel.recordReturnGoodsInfo(orderRepairs, userName);
            result.setSuccess(true);
            result.setResult(countRow);
            result.setMessage("保存退换货信息成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(0);
            result.setMessage("保存退换货信息失败");
            log.error("保存退换货信息失败" + e.getMessage());
        }
        return result;
    }

    /**
     * 退换货申请新方法，以此为准
     * @param typeActual
     * @param orderProductSn
     * @param contactName
     * @param contactMobile
     * @param count
     * @param reason
     * @param description
     * @param userName
     * @return
     * @see com.haier.cbs.order.service.OrderRepairService#applyReturnGoods(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ServiceResult<Integer> applyReturnGoods(Integer typeActual, String orderProductSn,
                                                   String contactName, String contactMobile,
                                                   Integer count, String reason, String description,
                                                   String userName) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result = orderRepairModel.applyReturnGoodsFilter(typeActual, orderProductSn,
                contactName, contactMobile, count, reason, description, userName);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(-1);
            result.setMessage("申请退换货失败!");
            log.error("[applyReturnGoods]单号：" + orderProductSn + " 申请退换货失败:" + e.getMessage());
        }
        return result;
    }

    public void setOrderRepairModel(OrderRepairModel orderRepairModel) {
        this.orderRepairModel = orderRepairModel;
    }

    public void setThTransactionService(ThTransactionServiceImpl thTransactionService) {
        this.thTransactionService = thTransactionService;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public void setInvoiceMakeOutModel(MakeOutInvoiceModel invoiceMakeOutModel) {
        this.invoiceMakeOutModel = invoiceMakeOutModel;
    }

    public void setInvoiceModel(InvoiceModel invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    @Override
    public ServiceResult<Integer> getCountRepairSn(String cOrderSn) {

        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            Integer countRepairSn = orderRepairModel.getCountRepairSn(cOrderSn);
            result.setSuccess(true);
            result.setResult(countRepairSn);
            result.setMessage("查询成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询失败");
            log.error("查询失败" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> goodsReturnCancelHp(OrderProductsNew orderProducts, OrdersNew order) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            Boolean cancelOk = orderRepairModel.cancelHpOrder(orderProducts, order);
            result.setSuccess(true);
            result.setResult(cancelOk);
            result.setMessage("作废HP成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("作废HP失败");
            log.error("作废HP失败" + e.getMessage());
        }
        return result;
    }

    /**
     * 不良品退货日志
     */
    public ServiceResult<Boolean> badNessReturnGoodsLogs() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            ServiceResult<List<InvThTransaction>> nodesListResult = thTransactionService
                .getHpNodesList();
            if (nodesListResult.getSuccess()) {
                if (nodesListResult.getResult() != null) {
                    List<InvThTransaction> nodesList = nodesListResult.getResult();
                    for (InvThTransaction invThTransaction : nodesList) {
                        try {
                            OrderRepairsNew orderRepairs = orderRepairModel
                                .getOrderRepairs(invThTransaction.getChannelOrderSn());
                            if (orderRepairs == null) {
                                log.error("不良品退货，退货单号不存在，channelOrderSn="
                                          + invThTransaction.getChannelOrderSn());
                                //2017-04-27
                                invThTransaction
                                    .setHp_states(InvThTransaction.HP_STATUS_HPLESID_UNDON);
                                thTransactionService.updateById(invThTransaction);
                                continue;
                            }
                            //当HP_STATES=0时，此时instatus为已提单状态，写orderRepairLESRecodes日志
                            if (invThTransaction.getHp_states() == 0
                                && invThTransaction.getInStatus() >= -1) {
                                /**
                                 * 根据order repair记录获取OrderRepairs
                                 *
                                 */
                            	OrderRepairLESRecords existLesRecords = orderRepairModel
                                    .getByLesOrderSn(invThTransaction.getHpLesId(),
                                        invThTransaction.getChannelOrderSn());
                                if (existLesRecords == null) {
                                	OrderRepairLESRecords lesRecords = setOrderRepairLESRecords(
                                        invThTransaction, orderRepairs);
                                    //2017-04-27
                                    //                                            new OrderRepairLESRecords();
                                    //                                    lesRecords.setSiteId(1);
                                    //                                    lesRecords.setAddTime(new Date().getTime() / 1000);
                                    //                                    lesRecords.setRecordSn(invThTransaction.getChannelOrderSn());
                                    //                                    lesRecords.setOperate(12);
                                    //                                    lesRecords.setStorageType(21);
                                    //                                    if (orderRepairs != null) {
                                    //                                        lesRecords
                                    //                                            .setOrderProductId(orderRepairs.getOrderProductId());
                                    //                                        lesRecords.setOrderRepairId(orderRepairs.getId());
                                    //                                    }
                                    //                                    lesRecords.setLesOrderSn(invThTransaction.getHpLesId() == null
                                    //                                        ? " " : invThTransaction.getHpLesId());
                                    //                                    Date date0 = invThTransaction.getHpLesDate();
                                    //                                    if (date0 != null) {
                                    //                                        lesRecords.setLesOrderSnTime(date0.getTime() / 1000);
                                    //                                    } else {
                                    //                                        lesRecords.setLesOrderSnTime(0l);
                                    //                                    }
                                    //                                    lesRecords.setLesOutPing(invThTransaction.getInwhId());
                                    //                                    Date date = invThTransaction.getInwhDate();
                                    //                                    if (date != null) {
                                    //                                        lesRecords.setLesOutPingTime(date.getTime() / 1000);
                                    //                                    } else {
                                    //                                        lesRecords.setLesOutPingTime(0l);
                                    //                                    }
                                    //                                    lesRecords.setSCode(invThTransaction.getSecCode());
                                    //                                    lesRecords.setSuccess(1);
                                    orderRepairModel.saveOrderRepairLESRecodes(lesRecords);
                                    //需要更新hp_states状态
                                    invThTransaction
                                        .setHp_states(InvThTransaction.HP_STATUS_HPLESID);
                                    thTransactionService.updateById(invThTransaction);
                                }
                                //2017-04-27
                                else {
                                    boolean tempFlag = false;
                                    if (StringUtil.isEmpty(existLesRecords.getLesOutPing())
                                        && !StringUtil.isEmpty(invThTransaction.getInwhId())) {
                                        existLesRecords.setLesOutPing(invThTransaction.getInwhId());
                                        tempFlag = true;
                                    }
                                    if (existLesRecords.getLesOutPingTime().intValue() == 0
                                        && invThTransaction.getInwhDate() != null) {
                                        existLesRecords.setLesOutPingTime(
                                            invThTransaction.getInwhDate().getTime() / 1000);
                                        tempFlag = true;
                                    }
                                    if (tempFlag) {
                                        orderRepairModel.updateLesRecordAfterJLIN(existLesRecords);
                                    }
                                    //需要更新hp_states状态
                                    invThTransaction
                                        .setHp_states(InvThTransaction.HP_STATUS_HPLESID);
                                    thTransactionService.updateById(invThTransaction);
                                }

                            }
                            //当HP_STATES=1时，此时instatus为已入库状态，写orderRepairRecodes日志
                            if (invThTransaction.getHp_states() == 1
                                && invThTransaction.getInStatus() > -1) {
                            	OrderRepairLESRecords lesRecords = orderRepairModel.getByLesOrderSn(
                                    invThTransaction.getHpLesId(),
                                    invThTransaction.getChannelOrderSn());
                                if (lesRecords == null) {
                                    log.error("没找对应的les record, thCorderSn="
                                              + invThTransaction.getChannelOrderSn());
                                    //2017-04-27
                                    lesRecords = setOrderRepairLESRecords(invThTransaction,
                                        orderRepairs);
                                    orderRepairModel.saveOrderRepairLESRecodes(lesRecords);
                                    continue;
                                }
                                lesRecords.setLesOutPing(invThTransaction.getInwhId());
                                Date date = invThTransaction.getInwhDate();
                                if (date != null) {
                                    lesRecords.setLesOutPingTime(date.getTime() / 1000);
                                } else {
                                    lesRecords.setLesOutPingTime(0l);
                                }
                                //2017-04-27
                                //                                boolean isSuc = orderRepairModel.updateAfterHpAccepted(lesRecords);
                                orderRepairModel.updateLesRecordAfterJLIN(lesRecords);
                                //                                if (isSuc) {
                                //                                    log.info("les已经入库，更新成功");
                                //                                }
                                /* OrderRepairLogs repairlog = new OrderRepairLogs();
                                 repairlog.setOperate("退货");
                                 repairlog.setOperator("系统");
                                 repairlog.setOrderRepairId(orderRepairs.getId());
                                 repairlog.setRemark("已入库");
                                 repairlog.setSiteId(1);
                                 orderRepairModel.saveOrderRepairLogs(repairlog);*/
                                invThTransaction
                                    .setHp_states(InvThTransaction.HP_STATUS_HPLESID_INWH);
                                thTransactionService.updateById(invThTransaction);
                            }
                        } catch (Exception e) {
                            log.error("不良品入库写日志发生异常,单号[" + invThTransaction.getChannelOrderSn()
                                      + "]：" + e.getMessage());
                        }
                    }

                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("不良品退货日志失败");
            log.error("不良品退货日志失败" + e.getMessage());
        }
        return result;
    }

    private OrderRepairLESRecords setOrderRepairLESRecords(InvThTransaction invThTransaction,
                                                           OrderRepairsNew orderRepairs) {
    	OrderRepairLESRecords lesRecords = new OrderRepairLESRecords();
        lesRecords.setSiteId(1);
        lesRecords.setAddTime(new Date().getTime() / 1000);
        lesRecords.setRecordSn(invThTransaction.getChannelOrderSn());
        lesRecords.setOperate(12);
        lesRecords.setStorageType(21);
        if (orderRepairs != null) {
            lesRecords.setOrderProductId(orderRepairs.getOrderProductId());
            lesRecords.setOrderRepairId(orderRepairs.getId());
        }
        lesRecords.setLesOrderSn(
            invThTransaction.getHpLesId() == null ? " " : invThTransaction.getHpLesId());
        Date date0 = invThTransaction.getHpLesDate();
        if (date0 != null) {
            lesRecords.setLesOrderSnTime(date0.getTime() / 1000);
        } else {
            lesRecords.setLesOrderSnTime(0l);
        }
        lesRecords.setLesOutPing(
            invThTransaction.getInwhId() == null ? "" : invThTransaction.getInwhId());
        Date date = invThTransaction.getInwhDate();
        if (date != null) {
            lesRecords.setLesOutPingTime(date.getTime() / 1000);
        } else {
            lesRecords.setLesOutPingTime(0l);
        }
        lesRecords.setSCode(invThTransaction.getSecCode());
        lesRecords.setSuccess(1);

        return lesRecords;
    }

    @Override
    public ServiceResult<Boolean> genRepairCancelCorder() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            orderRepairModel.genRepairCancelCorder();
            result.setSuccess(true);
            result.setResult(null);
            result.setMessage("生成已入库日日单尾款订单成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("生成已入库日日单尾款订单失败");
            log.error("", e);
            log.error("生成已入库日日单尾款订单失败" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<OrderRepairsNew> getByVomRepairSn(String vomRepairSn, String sku) {
        ServiceResult<OrderRepairsNew> result = new ServiceResult<OrderRepairsNew>();
        try {

            result.setSuccess(true);
            result.setResult(orderRepairModel.getByVomRepairSn(vomRepairSn, sku));
            result.setMessage("");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("根据 3Wvom退货单号 获取退换货网单异常:" + e.getMessage());
            log.error("根据 3Wvom退货单号 获取退换货网单异常", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> repairAfterVomAccepted3W(String corderSn, String expNo,
                                                           Date acceptTime) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result
                .setResult(orderRepairModel.updateAfterVomAccepted3W(corderSn, acceptTime, expNo));
        } catch (Exception e) {
            log.error("退货单-VOM接单后处理失败（3W正品退仓）：", e);
            result.setResult(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> repairAfterVomRejected3W(String corderSn, String reason,
                                                           Date rejectedTime) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(
                orderRepairModel.updateAfterVomRejected3W(corderSn, rejectedTime, reason));
        } catch (Exception e) {
            log.error("退货单-VOM据单后处理失败(3W正品退货)：", e);
            result.setResult(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> repairOrderIn3W(String cOrderSn, String lesIoNo, Date billTime,
                                                  String secCode, Integer num) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result = orderRepairModel.repairOrderIn3W(cOrderSn, lesIoNo, billTime, secCode, num);
        } catch (Exception e) {
            log.error("（3W正品退仓）退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo + ",billTime:"
                      + billTime + ",secCode:" + secCode + ",num+" + num + ")，出现未知异常:",
                e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    
    
}