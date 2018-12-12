package com.haier.logistics.Model;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.logistics.Helper.ReadWriteRoutingDataSourceHolder;
import com.haier.logistics.Util.DateFormatUtil;
import com.haier.logistics.Util.HttpResult;
import com.haier.logistics.service.LESService;
import com.haier.shop.model.OrderQueues;
import com.haier.shop.model.CancelInputType;
import com.haier.shop.model.LesQueues;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderWorkflows;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.LesQueuesService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrderQueuesService;
import com.haier.shop.service.OrdersNewService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.shop.service.ShopOrderWorkflowsService;
import com.haier.stock.model.OrderProductStatus;
import com.haier.stock.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;
@Service
public class LesToOrderModel {
    @Autowired
    private OrderProductsNewService orderProductsDao;
    @Autowired
    private OrdersNewService ordersDao;
    @Autowired
    private AccessExternalInterface accessExternalInterface;
    @Autowired
    private ShopOrderOperateLogsService orderOperateLogsDao;
    @Autowired
    private LesQueuesService lesQueuesDao;
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private OrderQueuesService orderQueuesDao;
    @Autowired
    private ShopOrderWorkflowsService orderWorkflowsDao;
    @Autowired
    private LESService lesService;
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(LesToOrderModel.class);

    /**
     * 更新开提单les回传状态信息
     * @param orderProductId 网单id
     * @param statusflag  成功标志，false失败，true成功
     */
    public int updateLesStatusToOrder(Integer orderProductId, boolean statusflag) {
        try {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            if (orderProductId == null || orderProductId == 0) {
                log.error("参数数据错误，网单id为空或为0");
                return -100;
            }
            LesQueues lesQueues = new LesQueues();
            lesQueues.setOrderProductId(orderProductId);
            if (statusflag) {
                lesQueues.setSuccess(1);//成功
            } else {
                lesQueues.setSuccess(0);//失败
            }
            int result = this.updateLesStatusToOrder(lesQueues);
            return result;
        } catch (Exception e) {
            log.error("VOM开提单更新返回状态信息时出错", e);
            return -101;
        } finally {
            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
        }
    }

    /**
     * 更新传les订单
     * @param
     */
    private int updateLesStatusToOrder(LesQueues lq){
        String message = "";
        if (lq == null || lq.getOrderProductId() == null || lq.getOrderProductId() == 0) {
            message = "参数错误，数据为空或网单id为空";
            log.error("参数错误，数据为空或网单id为空");
            return -102;//返回参数错误，网单id不存在
        }
        Integer orderProductId = lq.getOrderProductId();
        OrderProductsNew orderProduct = orderProductsDao.get(orderProductId);
        if (orderProduct == null) {
            message = "网单不存在";
            log.error(this.logPrefix("网单id：" + orderProductId) + "网单" + orderProductId + "不存在");
            return -103;//网单不存在
        }
        OrdersNew order = ordersDao.get(orderProduct.getOrderId());
        if (order == null) {
            message = "订单不存在";
            log.error(this.logPrefix("订单id：" + orderProduct.getOrderId()) + "订单"
                    + orderProduct.getOrderId() + "不存在");
            return -104;//订单不存在
        }
        //网单取消不能放在订单取消之后,否则同一个订单下有个一个网单取消时不能回传VOM，直接返回
        if (orderProduct.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
            if(lq.getSuccess() != null && lq.getSuccess() == 1){
                //用户取消订单回传VOM
                int code=cancelOrderToles(orderProduct,order,message,lq);
                return code;
            }else{
                message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                        + "，由于网单取消，不在更新网单状态信息";
                this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
                log.info(this.logPrefix("网单id：" + orderProductId) + message);
                return -106;//网单取消，不在更新
            }
        }
        //检测订单和网单信息是否还需要更新返回状态         网单或订单取消，不在更新网单信息
        if (order.getOrderStatus().equals(OrderStatus.OS_CANCEL.getCode())) {
            message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                    + "，由于订单取消，不在更新网单状态信息";
            this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
            log.info(this.logPrefix("订单id：" + orderProduct.getOrderId()) + message);
            return -105;//订单取消，不在更新
        }

        //当网单状态为非取消，并且网单状态大于等于8，不在更新网单状态，否则会有流程状态顺序会乱
        boolean lesStatusFlag = Boolean.TRUE;//是否更新网单状态标志
        if (orderProduct.getStatus().equals(OrderProductStatus.COMPLETED_CLOSE)) {//网单已是完成关闭，取消推送
            message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                    + "，由于网单已是完成关闭，不在更新网单状态信息，其他信息还会更新";
            log.info(this.logPrefix("网单id：" + orderProductId) + message);
            lesStatusFlag = Boolean.FALSE;
        } else if (orderProduct.getStatus() != null
                && orderProduct.getStatus() >= OrderProductStatus.LES_SHIPPING.getCode()) {//已开过提单
            message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                    + "，由于网单已开过提单，不在更新网单状态，其他信息还会更新";
            log.info(this.logPrefix("网单id：" + orderProductId) + message);
            lesStatusFlag = Boolean.FALSE;
        }

        //新版开提单不在更新cbs库位编码
        if (lq.getSuccess() != null && lq.getSuccess().equals(1)) {//1成功
            //事务处理创建发票信息等逻辑
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            //TransactionStatus status = transactionManager.getTransaction(def);
            try {
                //            lq.setSuccess(1);
                //            lq.setSuccessTime(new Date().getTime() / 1000);
                lq.setIsLock(0);
                lq.setLastMessage("开提单成功");
                lesQueuesDao.updateByOpId(lq);

                //更新网单表
                if (lesStatusFlag) {
                    orderProduct.setStatus(OrderProductStatus.LES_SHIPPING.getCode());
                }
                orderProduct.setWaitGetLesShippingInfo(1);
                orderProduct.setLessOrderSn(orderProduct.getCOrderSn());//当发送VOM更新提单号失败时这里的更新会起作用
                orderProductsDao.updateAfterSyncLes(orderProduct);

                //记录流程日志
                OrderWorkflows flow = new OrderWorkflows();
                flow.setOrderProductId(orderProduct.getId());
                flow.setLesShipping(new Date().getTime() / 1000);
                orderWorkflowsDao.updateAfterSyncOrderToLes(flow);

                //                //添加发票队列，开提单时开发票-----后面修改成出库时开发票，有两部分出库代码修改，采销联动出库和VOM出库，等VOM开提单上线在修改这个上线
                //                InvoiceQueue invoiceQueue = new InvoiceQueue();
                //                invoiceQueue.setOrderProductId(orderProduct.getId());
                //                invoiceQueue.setSuccess(0);
                //                invoiceQueue.setAddTime(new Date());
                //                invoiceQueue.setProcessTime(new Date());
                //                List<InvoiceQueue> inv_queue_list = invoiceQueueDao
                //                    .getByOrderProductId(orderProduct.getId());
                //                if (inv_queue_list == null || inv_queue_list.size() == 0) {//如果已经存在就不在插入
                //                    invoiceQueueDao.insert(invoiceQueue);
                //                }

                //开提单成功后，插入开提单成功队列，要给les同步定金尾款时间使用  ---新加  2015-03-12
                insertOrderQueues(orderProduct);

                //调用日志
                message = "开提单成功";//记录订单操作日志
                //提交事务
                //transactionManager.commit(status);
                log.info(this.logPrefix("网单id:" + orderProduct.getId() + "") + message);
            } catch (Exception ex) {
                //回滚事务
                //transactionManager.rollback(status);

                message = "更新返回开提单成功时异常";//记录订单操作日志
                //记录日志
                log.error(
                        "开提单更新返回状态：(opId:" + orderProduct.getId() + ",orderId:"
                                + orderProduct.getOrderId() + ")，出现未知异常:", ex);
                this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
                return -101;
            }
        } else {//0不成功
            lq.setSuccess(0);
            lq.setSuccessTime(0L);
            lq.setLastMessage("开提单失败");
            lesQueuesDao.updateAfterSyncLes(lq);
            //调用日志
            message = "开提单失败";//记录订单操作日志
            log.error(this.logPrefix("网单id:" + orderProduct.getId() + "") + message);
        }
        this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");

        return 0;
    }
    private String logPrefix(String lesQueueId) {
        return "[vom-to-ord] [" + lesQueueId + "]";
    }
    /**
     * 用户取消订单回传VOM
     * @param orderProduct
     * @param order
     * @param message
     * @param lq
     * @return
     */
    private int cancelOrderToles(OrderProductsNew orderProduct,OrdersNew order,String message,LesQueues lq){
        CancelInputType input = new CancelInputType();
        input.setOrderno(orderProduct.getCOrderSn());
        input.setCanceltype("2");
        String content =accessExternalInterface.getCancelContentXml(input);
        VomInterData vomInterData = new VomInterData();
        vomInterData.setNotifyid(orderProduct.getId() + "");
        vomInterData.setNotifytime(DateFormatUtil.format(new Date()));
        vomInterData.setContent(content);
        //VOM开提单，生成开提单加密参数
        String paramLes_tem = accessExternalInterface.cancelOrderToLesParam(content, vomInterData);
        String resultXml = "";
        try {
            message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                    + "，由于网单取消，不在更新网单状态信息，取消订单作废VOM成功";

            if (paramLes_tem == null || paramLes_tem.equals("")) {
                message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                        + "，由于网单取消，不在更新网单状态信息，取消订单作废VOM时生成VOM参数为空，请联系相关开发技术人员";
                log.error(this.logPrefix("网单id：" + lq.getOrderProductId()) + message);
                this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
                return -106;
            }

            //VOM新接口，调用VOM开提单    --vom
            ServiceResult<String> result = lesService.orderToLes(orderProduct.getCOrderSn(), paramLes_tem);
            if (result == null || !result.getSuccess()) {//调用les出异常
                message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                        + "，由于网单取消，不在更新网单状态信息，取消订单作废VOM时调用返回失败，VOM错误信息:" + result != null ? result.getMessage()
                        : "开提单调用接口返回为null，请联系相关开发技术人员";
                log.error(this.logPrefix("网单id：" + lq.getOrderProductId()) + message);
                this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
                return -106;
            }

            resultXml = result.getResult();
            if (resultXml == null) {
                message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                        + "，由于网单取消，不在更新网单状态信息，取消订单作废VOM时调用返回结果为空，请联系相关开发技术人员";
                log.error(this.logPrefix("网单id：" + lq.getOrderProductId()) + message);
                this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
                return -106;
            }

        } catch (Exception ex) {
            message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                    + "，由于网单取消，不在更新网单状态信息，取消订单作废VOM时，opid:" + orderProduct.getId() + "，发生未知异常:"
                    + ex.getMessage()+"，请联系相关开发技术人员";
            log.error(this.logPrefix("网单id：" + lq.getOrderProductId()) + message);
            this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
            return -106;
        }

        //处理les返回结果，解析xml
        HttpResult<String> httpresult = accessExternalInterface.getLesToOrderResult(resultXml);

        //les返回结果
        if (httpresult == null || httpresult.getSuccess() == null) {//调用接口发生异常，说明resultXml=""
            message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                    + "，由于网单取消，不在更新网单状态信息，取消订单作废VOM，解析返回结果时发生异常,网单号id：" + lq.getOrderProductId() + ", 接口返回为空，HttpResult is null";
            log.error(this.logPrefix("网单id：" + lq.getOrderProductId()) + message);
            this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
            return -106;
        }

        // false:失败
        if (!httpresult.getSuccess()) { //调用接口返回false
            message = "开提单返回结果：" + (lq.getSuccess() != null && lq.getSuccess() == 1 ? "成功" : "失败")
                    + "，由于网单取消，不在更新网单状态信息，取消订单作废VOM返回结果失败：" + httpresult.getMessage();
            log.error(this.logPrefix("网单id：" + lq.getOrderProductId()) + message);
            this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
            return -106;
        }

        log.info(this.logPrefix("网单id：" + lq.getOrderProductId()) + message);
        this.insertOrderProductLog(order, orderProduct, message, "同步VOM返回");
        return -106;//网单取消，不在更新
    }
    /**
     * 新增 订单明细操作日志
     * @param order
     * @param orderProduct
     * @param remark
     * @param changeLog
     */
    public void insertOrderProductLog(OrdersNew order, OrderProductsNew orderProduct, String remark,
                                      String changeLog){
        if (remark != null && remark.length() > 250) {
            remark = remark.substring(0, 250) + "...";
        }
        OrderOperateLogs log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setLogTime(((Long) (new Date().getTime() / 1000)).intValue());
        log.setNetPointId(orderProduct.getNetPointId());
        log.setOperator("系统");
        log.setOrderId(orderProduct.getOrderId());
        log.setOrderProductId(orderProduct.getId());
        log.setPaymentStatus(order.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        log.setSiteId(1);
        log.setStatus(orderProduct.getStatus());

        orderOperateLogsDao.insert(log);
    }
    /**
     * 新增 开提单后插入订单队列表
     * @param orderProduct
     */
    public void insertOrderQueues(OrderProductsNew orderProduct) {
        //添加发票队列，开提单时开发票-----后面修改成出库时开发票，有两部分出库代码修改，采销联动出库和VOM出库，等VOM开提单上线在修改这个上线
        OrderQueues orderQueues = new OrderQueues();
        orderQueues.setOrderProductId(orderProduct.getId());
        orderQueues.setAction("VomReturn");
        orderQueues.setSendLesSuccess(0);
        orderQueues.setCount(0);
        orderQueues.setAddTime((new Date().getTime() / 1000));
        orderQueues.setLastMessage("");
        orderQueues.setIsLock(0);
        orderQueues.setIsStop(0);
        orderQueues.setSuccessTime(0L);
        orderQueues.setLastTryTime(0L);
        List<OrderQueues> queue_list = orderQueuesDao.getByOrderProductId(orderProduct.getId());
        if (queue_list == null || queue_list.size() == 0) {//如果已经存在就不在插入
            orderQueuesDao.insert(orderQueues);
        }
    }
}
