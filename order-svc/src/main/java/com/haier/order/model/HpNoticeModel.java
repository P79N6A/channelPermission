package com.haier.order.model;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.VomwwwOutinstockAnalysis;
import com.haier.eis.service.EisInterfaceFinanceService;
import com.haier.eis.service.EisVomwwwOutinstockAnalysisService;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.multithread.IExcute;
import com.haier.order.multithread.MultiThreadTool;
import com.haier.order.multithread.ThreadHelper;
import com.haier.order.services.HpToNoticeServiceImpl;
import com.haier.order.services.OrderCenterItemServiceImpl;
import com.haier.order.services.OrderCenterOrderBizHelper;
import com.haier.order.services.OrderCenterPurchaseGdServiceImpl;
import com.haier.order.services.OrderCenterStockCommonServiceImpl;
import com.haier.order.util.DateFormatUtil;
import com.haier.shop.model.GroupOrders;
import com.haier.shop.model.HpNoticeQueues;
import com.haier.shop.model.O2OOrderTailendQueues;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProductsAttributes;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.model.Regions;
import com.haier.shop.model.ReservationShipping;
import com.haier.shop.model.TaoBaoGroups;
import com.haier.shop.service.AccountCenterService;
import com.haier.shop.service.GroupOrdersService;
import com.haier.shop.service.HPQueuesService;
import com.haier.shop.service.HpNoticeQueuesService;
import com.haier.shop.service.InvoiceQueueService;
import com.haier.shop.service.LesQueuesService;
import com.haier.shop.service.OrderProductsAttributesService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrderQueueExtendService;
import com.haier.shop.service.OrdersNewService;
import com.haier.shop.service.RegionsService;
import com.haier.shop.service.ReservationShippingService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.shop.service.ShopOrderWorkflowsService;
import com.haier.shop.service.ShopTaoBaoGroupsService;
import com.haier.stock.model.PaymentStatus;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class HpNoticeModel {
    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
        .getLogger(HpNoticeModel.class);
    @Autowired
    private OrderProductsAttributesService orderProductsAttributesDao;
    @Autowired
    private HpNoticeQueuesService hpNoticeQueuesService;
    @Autowired
    private HpToNoticeServiceImpl hpToNoticeService;
    @Autowired
    private OrderProductsNewService orderProductsDao;
    @Autowired
    private GroupOrdersService groupOrdersDao;
    @Autowired
    private OrdersNewService ordersDao;
    @Autowired
    private ReservationShippingService reservationShippingDao;
    @Autowired
    private RegionsService regionsDao;
    @Autowired
    private ShopOrderOperateLogsService orderOperateLogsDao;
    @Autowired
    private InvoiceQueueService invoiceQueueDao;
    @Autowired
    private OrderCenterItemServiceImpl itemService;
    @Autowired
    private OrderCenterStockCommonServiceImpl stockCommonService;
    @Autowired
    private OrderCenterPurchaseGdServiceImpl purchaseGdService;
    @Autowired
    private LesQueuesService lesQueuesDao;
    @Autowired
    private HPQueuesService hpQueuesDao;
    @Autowired
    private ShopOrderWorkflowsService orderWorkflowsDao;
    @Autowired
    private OrderQueueExtendService                    orderQueueExtendDao;
    @Autowired
    private ShopTaoBaoGroupsService taoBaoGroupsDao;
    @Autowired
    private AccountCenterService accountCenterDao;
    @Autowired
    private EisInterfaceFinanceService eisInterfaceFinanceDao;
    @Autowired
    private EisVomwwwOutinstockAnalysisService vomwwwOutinstockAnalysisDao;
    @Autowired
    private ThreadHelper threadHelper;
//    DataSourceTransactionManager           transactionManager;

    public void syncNoticeDataToHp() throws Exception {
        try {
            List<HpNoticeQueues> noticeList = hpNoticeQueuesService.getNoticeQueuesList(1000);
            if (noticeList == null || noticeList.size() == 0) {
                logger.info("[syncNoticeDataToHp]没有需要同步到HP的发货通知队列数据");
                return;
            }
            //加入多线程执行
            ExecuteNoticeToHp execute = new ExecuteNoticeToHp();
            //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
            int splitSize = 100;
            int size = noticeList.size();
            if (size > 10 && size <= splitSize) {
                splitSize = size / 2 + 1;
            }
            new MultiThreadTool<HpNoticeQueues>().processJobs(execute, threadHelper, logger,
                noticeList, splitSize, 3);
        } catch (Exception e) {
            logger.error("[syncNoticeDataToHp]同步通知到HP接口异常：", e);
            throw new Exception(e);
        }
    }

    private class ExecuteNoticeToHp implements IExcute {
        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            try {
                List<HpNoticeQueues> list = (List<HpNoticeQueues>) obj;
                syncNoticeToHpThread(list);
            } catch (Exception e) {
                logger.error("[ExecuteNoticeToHp]通知到HP对接多线程处理发生异常：", e);
            }
        }
    }

    private void syncNoticeToHpThread(List<HpNoticeQueues> hpNoticeQueuesList) {
        if (hpNoticeQueuesList != null && hpNoticeQueuesList.size() > 0) {
            for (HpNoticeQueues hpNoticeQueues : hpNoticeQueuesList) {
                if (hpNoticeQueues == null) {
//                    logger.error("[syncNoticeToHpThread]hpNoticeQueues对象为空");
                    continue;
                }
                try {
                    ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                    this.createNoticeToHp(hpNoticeQueues);
                } catch (Exception e) {
                    logger.error("[syncNoticeToHpThread]调用通知HP接口出现异常：", e);
                } finally {
                    ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                }
            }
        } else {
            logger.info("[syncNoticeToHpThread]通知队列列表为空");
        }
    }

    /**
     * 同步通知到HP让网点发货
     * @param hpNoticeQueues
     * @return
     * @throws Exception 
     */
    private boolean createNoticeToHp(HpNoticeQueues hpNoticeQueues) throws Exception {

        int tailFlag = 0;//尾款标记，判断是否需要特殊处理
        GroupOrders groupOrders = groupOrdersDao
            .getByDepositOrderProductId(hpNoticeQueues.getOrderProductId());
        Integer lastTryTime = Long.valueOf(System.currentTimeMillis()/1000).intValue();
        if (groupOrders == null) {
            editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime, "未找到匹配的尾款订单[GroupOrders]数据",
                tailFlag);
            return false;
        }

        if (groupOrders.getStatus() != null && groupOrders.getStatus().equals(30)) {
            editHpNoticeQueuesSuccess(hpNoticeQueues, true, "", "", lastTryTime,
                Long.valueOf(System.currentTimeMillis()/1000).intValue(), "已合并同步过HP的订单，无需在次处理",
                tailFlag);
            return false;
        }

        if (groupOrders.getType().equals(2)
            && groupOrders.getDepositOrderId().equals(groupOrders.getTailOrderId())) {
            editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime, "双订单，定金尾款号一样，不处理", tailFlag);
            return false;
        }

        OrdersNew depositOrder = ordersDao.get(groupOrders.getDepositOrderId());
        if (null == depositOrder) {
            editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime, "定金订单不存在", tailFlag);
            return false;
        }
        OrderProductsNew depositOrderProduct = orderProductsDao.get(groupOrders.getDepositOrderProductId());
        if (null == depositOrderProduct) {
            editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime, "定金网单已不存在", tailFlag);
            return false;
        }

        //已付尾款后,更新status,出库才会推送SAP
        List<Integer> lesStockTransQueueIds = eisInterfaceFinanceDao
            .getIdsByOrderSn(depositOrderProduct.getCOrderSn());
        if (lesStockTransQueueIds != null && lesStockTransQueueIds.size() > 0) {
            eisInterfaceFinanceDao.updateEisInterfaceFinance(lesStockTransQueueIds);
        }

//        OrderProductsAttributes orderProductsAttributes = orderProductsAttributesDao
//            .getByOrderProductId(depositOrderProduct.getId());
        //3W订单，已付尾款后，跟新sap_status，出库才会推送SAP
        if (depositOrderProduct.getStockType() != null
            && depositOrderProduct.getStockType().equalsIgnoreCase("3W")) {
            try {
                Map<String, Object> queryMap = new HashMap<String, Object>();
                queryMap.put("trade_no", depositOrder.getSourceOrderSn());//外部来源订单号
                queryMap.put("sub_trade_no", depositOrderProduct.getOid());//子订单号
                queryMap.put("tb_no", depositOrderProduct.getTbOrderSn());//tb单号
                queryMap.put("sku", depositOrderProduct.getSku());//sku物料编码
                queryMap.put("bus_type", 1);//业务类型：1、出库；2、入库
                queryMap.put("sap_status", 3);//财务状态
                queryMap.put("handle_status", 1);//出入库处理状态
                List<VomwwwOutinstockAnalysis> list1 = vomwwwOutinstockAnalysisDao
                    .getByCondition(queryMap,1);
                VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis = null;
                if (CollectionUtils.isNotEmpty(list1)){
                    vomwwwOutinstockAnalysis = list1.get(0);
                }
                if (vomwwwOutinstockAnalysis == null) {
                    queryMap.put("sku", null);//sku物料编码
                    List<VomwwwOutinstockAnalysis> list = vomwwwOutinstockAnalysisDao.getByCondition(queryMap,1);
                    if (CollectionUtils.isNotEmpty(list)){
                        vomwwwOutinstockAnalysis = list.get(0);
                    }
                }
                if (vomwwwOutinstockAnalysis != null) {
                    vomwwwOutinstockAnalysis.setSapStatus(0);
                    vomwwwOutinstockAnalysis.setMessage("定金尾款订单，已付尾款后，出库可以推送SAP");
                    vomwwwOutinstockAnalysisDao.updateSapStatusById(vomwwwOutinstockAnalysis);
                }
            } catch (Exception e) {
                logger.error(
                    "3W订单，定金尾款订单(op.id=" + depositOrderProduct.getId() + ")，已付尾款后，出库推送SAP,出现异常", e);
            }
        }

        //插入 o2o已付尾款订单队列 标记
        boolean flag = true;
        if (depositOrder.getOrderType().intValue() != 1) {//是否为定金订单
            flag = false;
        }
        if (depositOrder.getPayTime() == null || depositOrder.getPayTime().intValue() <= 0
            || depositOrder.getPaymentStatus() == null
            || depositOrder.getPaymentStatus().intValue() != 101) {//是否为已付款
            flag = false;
        }
        int o2oType = depositOrderProduct.getO2oType().intValue();
        if (o2oType != 2 && o2oType != 3 && o2oType != 4 && o2oType != 5 && o2oType != 50
            && o2oType != 60) {//是不是o2o网单
            flag = false;
        }
        //自营专单
        if (depositOrderProduct != null) {
            int isSelfSell = depositOrderProduct.isSelfSell();
            if (isSelfSell == 1) {
                flag = true;
            }
        }

        if (depositOrder.getSource().equalsIgnoreCase("SCFX")
            && depositOrderProduct.getStockType().equalsIgnoreCase("WA")) {//商城分销
            flag = true;
        }
        OrdersNew tailOrder = ordersDao.get(groupOrders.getTailOrderId());
        if (tailOrder == null) {//尾单是否存在
            flag = false;
        }
        if (tailOrder.getPayTime() == null || tailOrder.getPayTime().intValue() <= 0
            || tailOrder.getPaymentStatus() == null
            || tailOrder.getPaymentStatus().intValue() != 101) {//是否为已付款
            flag = false;
        }
        if (depositOrder.getOrderAmount().compareTo(tailOrder.getOrderAmount()) < 1) {
            flag = false;
        }
        //插入 o2o已付尾款订单队列
        if (flag) {
            O2OOrderTailendQueues o2oOrderTailendQueues = accountCenterDao
                .getTailendToAccountCenterByDepositOrderProductId(depositOrderProduct.getId());
            if (o2oOrderTailendQueues != null) {
                logger.info("定金网单号[" + depositOrderProduct.getCOrderSn() + "]在o2o已付尾款订单队列表中已存在");
            } else {
                try {
                    o2oOrderTailendQueues = new O2OOrderTailendQueues();
                    o2oOrderTailendQueues.setDepositOrderId(groupOrders.getDepositOrderId());
                    o2oOrderTailendQueues
                        .setDepositOrderProductId(groupOrders.getDepositOrderProductId());
                    o2oOrderTailendQueues.setTailOrderId(groupOrders.getTailOrderId());
                    OrderProductsNew op = orderProductsDao
                        .getByCOrderSn(groupOrders.getTailCOrderSn());
                    o2oOrderTailendQueues.setTailOrderProductId(op.getId());
                    o2oOrderTailendQueues.setAddTime(System.currentTimeMillis()/1000);
                    o2oOrderTailendQueues.setSuccess(0);
                    o2oOrderTailendQueues.setCount(0);
                    o2oOrderTailendQueues.setPushData("");
                    o2oOrderTailendQueues.setReturnData("");
                    o2oOrderTailendQueues.setLastTryTime(0L);
                    o2oOrderTailendQueues.setSuccessTime(0L);
                    o2oOrderTailendQueues.setLastMessage("");

                    int t = accountCenterDao
                        .insertTailendToAccountCenterList(o2oOrderTailendQueues);
                    if (t <= 0) {
                        logger.error("尾款通知HP发货，O2O网单号[" + groupOrders.getTailCOrderSn()
                                     + "]异常：插入o2o已付尾款订单队列表失败");
                    }
                } catch (Exception e) {
                    logger.error("尾款通知HP发货，O2O网单号[" + groupOrders.getTailCOrderSn() + "]异常：", e);
                }
            }
        }

        if (OrderStatus.OS_COMPLETE.getCode().equals(depositOrder.getOrderStatus())) {//完成关闭
            editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime,
                "订单[" + depositOrder.getId() + "]完成关闭", tailFlag);
            return false;
        }
        if (OrderStatus.OS_CANCEL.getCode().equals(depositOrder.getOrderStatus())) {//取消关闭
            editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime,
                "订单[" + depositOrder.getId() + "]取消关闭", tailFlag);
            return false;
        }
        if (!OrderStatus.OS_CONFIRM.getCode().equals(depositOrder.getOrderStatus())
            && !depositOrderProduct.getStockType().equalsIgnoreCase("3W")) {//未确认，不执行
            editHpNoticeQueuesException(hpNoticeQueues, "", lastTryTime,
                "定金订单[" + depositOrder.getId() + "]未确认");
            return false;
        }

        if (OrderProductStatus.CANCEL_CLOSE.getCode().equals(depositOrderProduct.getStatus())) {//取消关闭
            editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime,
                "网单[" + depositOrderProduct.getId() + "]取消关闭", tailFlag);
            return false;
        }
        if (OrderProductStatus.COMPLETED_CLOSE.getCode().equals(depositOrderProduct.getStatus())) {//完成关闭
            editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime,
                "网单[" + depositOrderProduct.getId() + "]完成关闭", tailFlag);
            return false;
        }

        // 天猫定金加尾款模式日日单
        if (depositOrder.getIsProduceDaily().equals(1)
            && isTBChannel(depositOrder.getSource())) {
            if (!depositOrderProduct.getPdOrderStatus().equals(OrderProductsNew.RRSSTATUS_STOCK_IN)) {
                groupOrders.setErrorMessage("天猫日日单未入库");
                groupOrders.setStatus(80);
                groupOrdersDao.update(groupOrders);

                editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime,
                    "定金网单[" + depositOrderProduct.getId() + "]天猫日日单未入库", tailFlag);
                return false;
            }
        }

        OrderProductsNew tailOrderProduct = orderProductsDao
            .getByCOrderSn(groupOrders.getTailCOrderSn());
        Integer payTime = 0;
        if (groupOrders.getType().equals(2)) {//双订单模式
            if (tailOrderProduct == null) {
                groupOrders.setStatus(70);
                groupOrders.setLastErrorMsg("尾款网单不存在");
                groupOrdersDao.update(groupOrders);
                editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime, "尾款网单不存在", tailFlag);
                return false;
            }

            if (tailOrder == null) {
                groupOrders.setStatus(70);
                groupOrders.setLastErrorMsg("尾款订单不存在");
                groupOrdersDao.update(groupOrders);
                editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime, "尾款订单不存在", tailFlag);
                return false;
            }

            if (!tailOrder.getPaymentStatus().equals(PaymentStatus.PS_PAID.getCode())) {
                groupOrders.setLastErrorMsg("尾款订单未付款");
                groupOrdersDao.update(groupOrders);
                editHpNoticeQueuesException(hpNoticeQueues, "", lastTryTime, "尾款订单未付款");
                return false;
            }
            payTime = tailOrder.getPayTime() == 0 ? lastTryTime : tailOrder.getPayTime();

        } else {
            //单订单
            payTime = lastTryTime;
            //$payTime = $depositOrder->payTime == 0 ? time() : $depositOrder->payTime;
        }

        //设置尾款付款时间
        if (depositOrder.getTailPayTime() == 0) {
            depositOrder.setTailPayTime(payTime);
            ordersDao.updateForTailPayTime(depositOrder);
        }

        //淘宝订单的一些限制
        if (groupOrders.getFrom().equals("taobao")) {
            TaoBaoGroups taoBaoGroups = taoBaoGroupsDao.get(groupOrders.getGroupId());
            BigDecimal totalShouldPay = new BigDecimal(0);
            String productSpecs = taoBaoGroups.getProductSpecs();

            if (productSpecs != null && !productSpecs.equals("")
                && !productSpecs.trim().equals("[]")) {
                List<Map<String, Object>> list = JsonUtil.fromJson(productSpecs);
                if (list != null && list.size() > 0) {//字段不为空，并且长度大于1才认为是套装，否则识别为非套装
                    BigDecimal depositAmount = new BigDecimal(0);
                    BigDecimal tailAmount = new BigDecimal(0);
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("sku") != null && depositOrderProduct.getSku()
                            .equals(list.get(i).get("sku").toString())) {
                            depositAmount = new BigDecimal(
                                list.get(i).get("depositAmount").toString());
                            tailAmount = new BigDecimal(list.get(i).get("tailAmount").toString());
                            totalShouldPay = depositAmount.add(tailAmount);
                            break;
                        }
                    }
                } else {
                    totalShouldPay = taoBaoGroups.getDepositAmount()
                        .add(taoBaoGroups.getBalanceAmount());
                }
            } else {
                totalShouldPay = taoBaoGroups.getDepositAmount()
                    .add(taoBaoGroups.getBalanceAmount());
            }
            // 不管是否套装，totalShouldPay均需有值，否则一定是设置有误
            if (totalShouldPay.compareTo(BigDecimal.ZERO) == 0) {
                groupOrders.setLastErrorMsg("总金额设置有误");
                groupOrdersDao.update(groupOrders);
                editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime, "总金额设置有误", tailFlag);
                return false;
            }
            if (!hpNoticeQueues.getLastMessage().equalsIgnoreCase("send")) {//特殊处理，强制推送
                if (groupOrders.getType().equals(2)
                    && depositOrderProduct.getPrice().compareTo(totalShouldPay) >= 0) {
                    groupOrders.setLastErrorMsg("双订单模式商品价格大于总金额，设置有误");
                    groupOrdersDao.update(groupOrders);
                    editHpNoticeQueuesFinal(hpNoticeQueues, lastTryTime, "双订单模式商品价格大于总金额，设置有误",
                        tailFlag);
                    return false;
                }
                BigDecimal coupon = depositOrderProduct.getCouponAmount() == null ? BigDecimal.ZERO
                    : depositOrderProduct.getCouponAmount();
                long points = depositOrderProduct.getPoints() == null ? 0
                    : depositOrderProduct.getPoints();
                BigDecimal pointAmount = new BigDecimal(points).divide(new BigDecimal(100), 2,
                    BigDecimal.ROUND_HALF_UP);
                BigDecimal b1 = depositOrderProduct.getProductAmount().add(coupon).add(pointAmount);
                BigDecimal b2 = totalShouldPay
                    .multiply(new BigDecimal(depositOrderProduct.getNumber()));
                if (groupOrders.getType().equals(1) && b1.compareTo(b2) != 0) {
                    tailFlag = 1;
                    BigDecimal b3 = b1.subtract(b2);
                    groupOrders.setLastErrorMsg("单订单模式商品价格加优惠金额不等于金额乘以数量；网单总金额：" + b1.doubleValue()
                                                + "；活动价格总金额：" + b2.doubleValue() + "；差值：" + b3);
                    groupOrdersDao.update(groupOrders);
                    editHpNoticeQueuesSucc(hpNoticeQueues, lastTryTime,
                        "单订单模式商品价格加优惠金额不等于金额乘以数量，已传输；网单总金额：" + b1.doubleValue() + "；活动价格总金额："
                                                                        + b2.doubleValue() + "；差值："
                                                                        + b3);//success=3需要特殊处理，把优惠金额和积分在网单表中处理
                    //                    return false;// 暂时不能去掉注释
                }
            }
        }

        NoticeToHpInputType pushData = getPushData(groupOrders, depositOrder, depositOrderProduct); //HP接口参数实体

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String[] message = new String[1];
            boolean isOk = OrderCenterOrderBizHelper.addToHpOrLesQueue(depositOrderProduct.getId(), "系统",
                message, itemService, stockCommonService, purchaseGdService, lesQueuesDao,
                hpQueuesDao, ordersDao, orderProductsDao, orderOperateLogsDao,orderWorkflowsDao,orderQueueExtendDao
                );
            if (!isOk) {
                groupOrders.setErrorMessage("[HBDM]插入HP或LES队列未成功:" + message[0]);
                groupOrders.setStatus(40);
                groupOrdersDao.update(groupOrders);

                // 修改通知HP队列失败状态字段
                editHpNoticeQueuesException(hpNoticeQueues, JsonUtil.toJson(pushData), lastTryTime,
                    "插入HP或LES队列未成功:" + message[0]);
//                transactionManager.commit(status);//提交日志，不能回滚
                return false;
            }
            //把这段代码提到前面去了
            //            OrderProductsAttributes orderProductsAttributes = orderProductsAttributesDao
            //                .getByOrderProductId(depositOrderProduct.getId());
            // 天猫定金发货尾款合并写开票队列
            isOk = OrderCenterOrderBizHelper.invoiceQueueAdd(depositOrderProduct.getId(), orderOperateLogsDao,
                groupOrdersDao, orderProductsDao, invoiceQueueDao, ordersDao
            );

            if (!isOk) {
                groupOrders.setErrorMessage("[HBDM]天猫定金发货尾款合并写开票队列未成功");
                groupOrders.setStatus(40);
                groupOrdersDao.update(groupOrders);

                // 修改通知HP队列失败状态字段
                editHpNoticeQueuesException(hpNoticeQueues, JsonUtil.toJson(pushData), lastTryTime,
                    "天猫定金发货尾款合并写开票队列未成功");
//                transactionManager.commit(status);//提交日志，不能回滚
                return false;
            }

            ServiceResult<String> serviceResult = null;

            //3W网单收到尾款，不通知HP发货
            if (depositOrderProduct.getStockType() != null
                && depositOrderProduct.getStockType().equalsIgnoreCase("3W")) {
                serviceResult = new ServiceResult<String>();
                serviceResult.setResult("");
                serviceResult.setMessage("3W-CAINIAO");
            } else {
                // 通知HP发货接口调用
                serviceResult = hpToNoticeService
                    .noticeHpToSend(String.valueOf(hpNoticeQueues.getId()), pushData);
            }

            if (serviceResult != null && serviceResult.getSuccess()) { //消息推送HP成功
                if (depositOrderProduct.getStockType() != null
                    && depositOrderProduct.getStockType().equalsIgnoreCase("3W")) {
                    insertLog(depositOrderProduct, depositOrder, "3W定金尾款订单-尾款收到");
                } else {
                    insertLog(depositOrderProduct, depositOrder, "定金尾款订单-尾款收到-通知HP让网点发货");
                }

                groupOrders.setStatus(30);
                groupOrders.setErrorMessage("");
                groupOrdersDao.update(groupOrders);

                // 修改通知HP队列成功状态字段
                Integer successTime = Long.valueOf(System.currentTimeMillis()/1000).intValue();
                editHpNoticeQueuesSuccess(hpNoticeQueues, false, JsonUtil.toJson(pushData),
                    serviceResult.getResult(), lastTryTime, successTime, serviceResult.getMessage(),
                    tailFlag);

//                transactionManager.commit(status);
                return true;
            } else { //推送HP失败

                insertLog(depositOrderProduct, depositOrder, "定金尾款订单-通知HP发货失败");
                groupOrders.setErrorMessage(
                    "[HBDM]" + serviceResult != null ? serviceResult.getMessage() : "推送消息到HP失败");
                groupOrders.setStatus(40);
                groupOrdersDao.update(groupOrders);

                // 修改通知HP队列失败状态字段
                String returnData = (serviceResult != null ? serviceResult.getResult() : "");
                editHpNoticeQueuesFail(hpNoticeQueues, JsonUtil.toJson(pushData), returnData,
                    lastTryTime, groupOrders.getErrorMessage());
//                transactionManager.commit(status);//提交日志，不能回滚
                return false;
            }
        } catch (Exception ex) {
            logger.error("[createNoticeToHp]程序出现异常:", ex);

//            transactionManager.rollback(status);

            groupOrders.setErrorMessage("[HBDM]" + ex.getMessage());
            groupOrders.setStatus(40);
            groupOrdersDao.update(groupOrders);

            // 修改通知HP队列异常状态字段
            editHpNoticeQueuesException(hpNoticeQueues, JsonUtil.toJson(pushData), lastTryTime,
                ex.getMessage());

            insertLog(depositOrderProduct, depositOrder, groupOrders.getErrorMessage());
            return false;
        }
    }

    /**
     * 成功-修改通知HP队列
     */
    private void editHpNoticeQueuesSuccess(HpNoticeQueues hpNoticeQueues, boolean special,
                                           String pushData, String returnData, Integer lastTryTime,
                                           Integer successTime, String lastMessage, int tailFlag) {
        int count = hpNoticeQueues.getCount();

        if (tailFlag != 1) {
            hpNoticeQueues.setSuccess(1);
        } else {
            hpNoticeQueues.setSuccess(3);
        }
        hpNoticeQueues.setLastMessage(lastMessage);
        hpNoticeQueues.setCount(count + 1);
        hpNoticeQueues.setSuccessTime(successTime);
        hpNoticeQueues.setLastTryTime(lastTryTime);
        if (!special) { // 除特殊处理外，这些字段需要修改
            hpNoticeQueues.setPushData(pushData);
            hpNoticeQueues.setReturnData(returnData);
        }
        hpNoticeQueuesService.update(hpNoticeQueues);
    }

    /**
     * 失败-修改通知HP队列
     */
    private void editHpNoticeQueuesFail(HpNoticeQueues hpNoticeQueues, String pushData,
                                        String returnData, Integer lastTryTime,
                                        String lastMessage) {
        int count = hpNoticeQueues.getCount();
        hpNoticeQueues.setPushData(pushData);
        hpNoticeQueues.setSuccess(0);
        hpNoticeQueues.setLastMessage(lastMessage);
        hpNoticeQueues.setCount(count + 1);
        hpNoticeQueues.setReturnData(returnData);
        hpNoticeQueues.setLastTryTime(lastTryTime);
        hpNoticeQueuesService.update(hpNoticeQueues);
    }

    /**
     * 异常-修改通知HP队列
     */
    private void editHpNoticeQueuesException(HpNoticeQueues hpNoticeQueues, String pushData,
                                             Integer lastTryTime, String lastMessage) {
        int count = hpNoticeQueues.getCount();
        hpNoticeQueues.setPushData(pushData);
        hpNoticeQueues.setSuccess(0);
        hpNoticeQueues.setLastMessage(lastMessage);
        hpNoticeQueues.setCount(count + 1);
        hpNoticeQueues.setLastTryTime(lastTryTime);
        hpNoticeQueuesService.update(hpNoticeQueues);
    }

    /**
     * 终结-修改通知HP队列
     */
    private void editHpNoticeQueuesFinal(HpNoticeQueues hpNoticeQueues, Integer lastTryTime,
                                         String lastMessage, int tailFlag) {
        int count = hpNoticeQueues.getCount();
        if (tailFlag != 1) {
            hpNoticeQueues.setSuccess(2);
        } else {
            hpNoticeQueues.setSuccess(3);
        }
        hpNoticeQueues.setLastMessage(lastMessage);
        hpNoticeQueues.setCount(count + 1);
        hpNoticeQueues.setLastTryTime(lastTryTime);
        hpNoticeQueuesService.update(hpNoticeQueues);
    }

    /**
     * 特殊-修改通知HP队列
     */
    private void editHpNoticeQueuesSucc(HpNoticeQueues hpNoticeQueues, Integer lastTryTime,
                                        String lastMessage) {
        //        int count = hpNoticeQueues.getCount();//避免重复计数
        hpNoticeQueues.setSuccess(3);//特殊值
        hpNoticeQueues.setLastMessage(lastMessage);
        //        hpNoticeQueues.setCount(count + 1);//避免重复计数
        hpNoticeQueues.setLastTryTime(lastTryTime);
        hpNoticeQueuesService.update(hpNoticeQueues);
    }

    /**
     * 通知HP接口参数
     * @param groupOrders
     * @param depositOrder
     * @param depositOrderProduct
     * @return
     * @throws Exception
     */
    private NoticeToHpInputType getPushData(GroupOrders groupOrders, OrdersNew depositOrder,
                                            OrderProductsNew depositOrderProduct) throws Exception {
        ReservationShipping reservationShipping = reservationShippingDao.get(Integer.valueOf(depositOrder.getId()));
        NoticeToHpInputType pushData = new NoticeToHpInputType();
        pushData.setOrderNo(groupOrders.getDepositCOrderSn());//订金订单网单号
        pushData.setTailSectionNo(groupOrders.getTailCOrderSn()); //尾款网单号
        pushData.setTailSectionDate(getPayTime(groupOrders.getType(), depositOrder.getPayTime().intValue())); //尾款支付时间
        pushData.setReserverDate(
            getReserverDate(reservationShipping, depositOrderProduct, depositOrder));

        //        Calendar cl = Calendar.getInstance();
        //        cl.setTime(new Date());
        //        XMLGregorianCalendar creatTime = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
        //            cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1, cl.get(Calendar.DAY_OF_MONTH),
        //            DatatypeConstants.FIELD_UNDEFINED);
        //        creatTime.setHour(cl.get(Calendar.HOUR_OF_DAY));
        //        creatTime.setMinute(cl.get(Calendar.MINUTE));
        //        creatTime.setSecond(cl.get(Calendar.SECOND));
        pushData.setCreatedDate(null);
        pushData.setUserCode("");
        return pushData;
    }

    /**
     * 网单日志记录
     * @param orderProduct
     * @param orders
     * @param remark
     */
    private void insertLog(OrderProductsNew orderProduct, OrdersNew orders, String remark) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("remark", "[HBDM]" + remark);
        data.put("changeLog", "定金订单-通知HP让网点发货");
        insertOrderProductLog(orderProduct, orders, data);
    }

    private void insertOrderProductLog(OrderProductsNew orderProduct, OrdersNew orders,
                                       Map<String, String> data) {
        OrderOperateLogs orderOperateLog = new OrderOperateLogs();
        orderOperateLog.setSiteId(1);
        orderOperateLog.setOrderId(orderProduct.getOrderId());
        orderOperateLog.setOrderProductId(orderProduct.getId());
        orderOperateLog.setNetPointId(orderProduct.getNetPointId());
        orderOperateLog.setOperator("系统");
        orderOperateLog.setChangeLog(String.valueOf(data.get("changeLog")));
        orderOperateLog.setRemark(String.valueOf(data.get("remark")));
        orderOperateLog.setLogTime(Long.valueOf(System.currentTimeMillis()/1000).intValue());
        orderOperateLog.setStatus(orderProduct.getStatus());
        orderOperateLog.setPaymentStatus(orders.getPaymentStatus());
        orderOperateLogsDao.insert(orderOperateLog);
    }

    private static String getPayTime(Integer type, Integer payTime) {
        if (type == 2) { //双订单模式
            if (payTime > 0) {
                return DateFormatUtil.formatTime("yyyy-MM-dd'T'HH:mm:ss", payTime);
            }
        }
        Date current = new Date();
        return DateFormatUtil.formatByType("yyyy-MM-dd'T'HH:mm:ss", current);
    }

    private String getReserverDate(ReservationShipping reservationShipping,
                                   OrderProductsNew depositOrderProduct,
                                   OrdersNew depositOrder) throws Exception {
        String reserverDate = "";
        if (reservationShipping != null) {
            Date date = DateFormatUtil.parseByType("yyyyMMddHHmmss",
                reservationShipping.getDate() + reservationShipping.getTime());
            reserverDate = DateFormatUtil.formatByType("yyyy-MM-dd'T'HH:mm:ss", date);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2015-02-07");
        if (depositOrderProduct.getSku().equals("BA0A7209H") && (date.after(new Date()))) {
            Regions region = regionsDao.get(depositOrder.getRegion());
            if (region != null) {
                if (StringUtils.isEmpty(reserverDate)
                    && dtd3hourRegions.contains(region.getCode())) {
                    Long temp = depositOrder.getTailPayTime() + 3600 * 3;
                    reserverDate = DateFormatUtil.formatTime("yyyy-MM-dd'T'HH:mm:ss", temp);
                }
            }
        }
        return reserverDate;
    }

//    public DataSourceTransactionManager getTransactionManager() {
//        return transactionManager;
//    }

    /*public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }*/


    final static List<String> dtd3hourRegions = Arrays.asList("659004", "659002", "659001",
        "652901", "652327", "652324", "652323", "652302", "652301", "652101", "650121", "650109",
        "650107", "650106", "650105", "650104", "650103", "650102", "640521", "640502", "640381",
        "640324", "640323", "640303", "640302", "640221", "640205", "640202", "640181", "640122",
        "640121", "640106", "640105", "640104", "630222", "630221", "630123", "630121", "630105",
        "630104", "630103", "630102", "623027", "622927", "622926", "622925", "622924", "622923",
        "622922", "622921", "622901", "621228", "621227", "621226", "621225", "621221", "621202",
        "621125", "621124", "621123", "621122", "621102", "620826", "620825", "620824", "620823",
        "620802", "620623", "620622", "620525", "620524", "620523", "620522", "620521", "620503",
        "620502", "620423", "620422", "620421", "620403", "620402", "620123", "620122", "620121",
        "620111", "620105", "620104", "620103", "620102", "611026", "611025", "611024", "611023",
        "611022", "611021", "610929", "610928", "610926", "610925", "610924", "610922", "610921",
        "610902", "610831", "610830", "610829", "610828", "610827", "610826", "610825", "610823",
        "610724", "610632", "610629", "610628", "610627", "610626", "610625", "610624", "610623",
        "610622", "610621", "610602", "610582", "610581", "610528", "610527", "610526", "610525",
        "610524", "610523", "610522", "610521", "610502", "610481", "610431", "610430", "610429",
        "610427", "610426", "610425", "610424", "610423", "610422", "610404", "610403", "610402",
        "610329", "610328", "610327", "610326", "610324", "610323", "610322", "610304", "610303",
        "610302", "610222", "610204", "610203", "610202", "610126", "610125", "610124", "610122",
        "610116", "610115", "610114", "610113", "610112", "610111", "610104", "610103", "610102",
        "532927", "532926", "532924", "532923", "532901", "532502", "532331", "532329", "532325",
        "532301", "530722", "530721", "530702", "530502", "530425", "530423", "530402", "530322",
        "530321", "530302", "530181", "530129", "530128", "530127", "530126", "530125", "530124",
        "530122", "530114", "530112", "530111", "530103", "530102", "522732", "522731", "522730",
        "522729", "522728", "522726", "522725", "522723", "522702", "522701", "522635", "522630",
        "522625", "522623", "522622", "522601", "522325", "522324", "522323", "522322", "522301",
        "520626", "520524", "520523", "520522", "520424", "520423", "520422", "520421", "520402",
        "520400", "520382", "520329", "520328", "520327", "520324", "520323", "520322", "520321",
        "520303", "520302", "520221", "520203", "520201", "520181", "520123", "520122", "520121",
        "520115", "520113", "520112", "520111", "520103", "520102", "512081", "512022", "512002",
        "511803", "511802", "511725", "511724", "511703", "511702", "511681", "511623", "511622",
        "511621", "511602", "511529", "511528", "511527", "511526", "511525", "511524", "511523",
        "511521", "511503", "511502", "511425", "511424", "511423", "511422", "511421", "511402",
        "511381", "511325", "511324", "511323", "511322", "511321", "511304", "511303", "511302",
        "511181", "511126", "511124", "511123", "511112", "511111", "511102", "510923", "510922",
        "510921", "510904", "510903", "510823", "510812", "510811", "510802", "510781", "510726",
        "510725", "510724", "510723", "510722", "510704", "510703", "510683", "510682", "510681",
        "510626", "510623", "510603", "510525", "510524", "510522", "510521", "510504", "510503",
        "510502", "510184", "510183", "510182", "510181", "510132", "510131", "510129", "510124",
        "510122", "510121", "510115", "510114", "510113", "510112", "510108", "510107", "510106",
        "510105", "510104", "500231", "500227", "500226", "500224", "500223", "500119", "500118",
        "500117", "500116", "500115", "500113", "500112", "500111", "500110", "500109", "500108",
        "500107", "500106", "500105", "500104", "500103", "500102", "469030", "469028", "469024",
        "469023", "469022", "469021", "469006", "469005", "469003", "469002", "460200", "460108",
        "460107", "460106", "460105", "451481", "451424", "451423", "451421", "451381", "451324",
        "451323", "451322", "451321", "451302", "451281", "451229", "451228", "451227", "451226",
        "451225", "451202", "451027", "451023", "451022", "451002", "450981", "450923", "450922",
        "450921", "450902", "450881", "450804", "450803", "450802", "450722", "450721", "450703",
        "450702", "450681", "450621", "450603", "450602", "450521", "450512", "450503", "450502",
        "450481", "450422", "450421", "450406", "450405", "450404", "450403", "450331", "450326",
        "450323", "450311", "450305", "450304", "450303", "450302", "450226", "450225", "450224",
        "450222", "450221", "450205", "450204", "450203", "450202", "450127", "450126", "450125",
        "450124", "450123", "450122", "450109", "450108", "450107", "450105", "450103", "450102",
        "445323", "445321", "445302", "445281", "445224", "445222", "445203", "445202", "445122",
        "445102", "442000", "441900", "441881", "441821", "441803", "441802", "441781", "441723",
        "441721", "441702", "441581", "441523", "441502", "441481", "441427", "441426", "441424",
        "441423", "441422", "441421", "441402", "441322", "441284", "441283", "441224", "441223",
        "441203", "441202", "440983", "440982", "440981", "440923", "440903", "440902", "440883",
        "440882", "440881", "440823", "440811", "440804", "440803", "440802", "440784", "440783",
        "440781", "440705", "440704", "440703", "440608", "440607", "440606", "440605", "440604",
        "440523", "440515", "440514", "440513", "440512", "440511", "440507", "440403", "440402",
        "440307", "440306", "440305", "440304", "440303", "440183", "440115", "440114", "440113",
        "440112", "440111", "440106", "440105", "440104", "440103", "431321", "431302", "431122",
        "431121", "431103", "431102", "431081", "431028", "431023", "431022", "431021", "431003",
        "431002", "430981", "430922", "430921", "430903", "430902", "430821", "430802", "430781",
        "430726", "430725", "430724", "430723", "430722", "430721", "430703", "430702", "430681",
        "430626", "430624", "430621", "430611", "430603", "430602", "430523", "430522", "430521",
        "430511", "430503", "430502", "430482", "430481", "430426", "430424", "430423", "430422",
        "430421", "430412", "430408", "430407", "430406", "430405", "430382", "430381", "430321",
        "430304", "430302", "430281", "430221", "430211", "430204", "430203", "430202", "430181",
        "430124", "430121", "430112", "430111", "430105", "430104", "430103", "430102", "429006",
        "429005", "429004", "422801", "421381", "421303", "421281", "421224", "421223", "421222",
        "421221", "421202", "421182", "421181", "421127", "421126", "421125", "421124", "421123",
        "421122", "421121", "421102", "421087", "421083", "421024", "421023", "421022", "421003",
        "421002", "420984", "420982", "420981", "420923", "420922", "420921", "420902", "420881",
        "420822", "420821", "420804", "420802", "420704", "420703", "420702", "420684", "420683",
        "420682", "420626", "420625", "420624", "420607", "420606", "420602", "420583", "420582",
        "420581", "420529", "420528", "420527", "420526", "420525", "420506", "420505", "420504",
        "420503", "420502", "420381", "420303", "420302", "420281", "420222", "420205", "420204",
        "420203", "420202", "420117", "420116", "420115", "420114", "420113", "420112", "420111",
        "420107", "420106", "420105", "420104", "420103", "420102", "419001", "411729", "411728",
        "411727", "411726", "411725", "411724", "411723", "411722", "411721", "411702", "411681",
        "411628", "411627", "411626", "411625", "411624", "411623", "411622", "411621", "411602",
        "411528", "411527", "411526", "411525", "411524", "411523", "411522", "411521", "411503",
        "411502", "411481", "411426", "411425", "411424", "411423", "411422", "411421", "411403",
        "411402", "411381", "411330", "411329", "411328", "411327", "411326", "411325", "411324",
        "411323", "411322", "411321", "411303", "411302", "411282", "411281", "411222", "411221",
        "411202", "411122", "411121", "411104", "411103", "411102", "411082", "411081", "411025",
        "411024", "411023", "411002", "410928", "410926", "410923", "410922", "410902", "410883",
        "410882", "410825", "410823", "410822", "410821", "410811", "410804", "410803", "410802",
        "410782", "410781", "410728", "410727", "410726", "410725", "410724", "410721", "410711",
        "410704", "410703", "410702", "410622", "410621", "410611", "410603", "410602", "410581",
        "410527", "410526", "410523", "410522", "410506", "410505", "410503", "410502", "410482",
        "410481", "410425", "410423", "410422", "410421", "410411", "410404", "410403", "410402",
        "410381", "410329", "410328", "410327", "410326", "410325", "410324", "410323", "410322",
        "410311", "410306", "410305", "410304", "410303", "410302", "410225", "410224", "410223",
        "410222", "410221", "410211", "410205", "410204", "410203", "410202", "410185", "410184",
        "410183", "410182", "410181", "410122", "410108", "410106", "410105", "410104", "410103",
        "410102", "371728", "371727", "371726", "371725", "371724", "371723", "371722", "371721",
        "371702", "371626", "371625", "371624", "371623", "371622", "371621", "371602", "371581",
        "371526", "371525", "371524", "371523", "371522", "371521", "371502", "371482", "371481",
        "371428", "371427", "371426", "371425", "371424", "371423", "371422", "371421", "371402",
        "371329", "371328", "371327", "371326", "371325", "371324", "371323", "371322", "371321",
        "371312", "371311", "371302", "371203", "371202", "371122", "371121", "371103", "371102",
        "371083", "371082", "371081", "371002", "370983", "370982", "370923", "370921", "370911",
        "370902", "370883", "370882", "370881", "370832", "370831", "370830", "370829", "370828",
        "370827", "370826", "370811", "370802", "370786", "370785", "370784", "370783", "370782",
        "370781", "370725", "370724", "370705", "370704", "370703", "370702", "370687", "370686",
        "370685", "370684", "370683", "370682", "370681", "370613", "370612", "370611", "370602",
        "370523", "370522", "370521", "370503", "370502", "370481", "370406", "370405", "370404",
        "370403", "370402", "370323", "370322", "370321", "370306", "370305", "370304", "370303",
        "370302", "370285", "370283", "370282", "370281", "370214", "370213", "370212", "370211",
        "370203", "370202", "370181", "370126", "370125", "370124", "370113", "370112", "370105",
        "370104", "370103", "370102", "361181", "361127", "361126", "361125", "361124", "361123",
        "361122", "361121", "361102", "361029", "361027", "361026", "361024", "361021", "361002",
        "360983", "360982", "360981", "360925", "360924", "360923", "360922", "360921", "360828",
        "360827", "360826", "360824", "360823", "360821", "360803", "360802", "360782", "360781",
        "360733", "360732", "360731", "360730", "360729", "360728", "360727", "360726", "360725",
        "360724", "360723", "360722", "360721", "360702", "360681", "360622", "360602", "360502",
        "360482", "360481", "360429", "360427", "360426", "360425", "360424", "360423", "360421",
        "360403", "360402", "360281", "360124", "360123", "360122", "360121", "360111", "360105",
        "360104", "360103", "360102", "350982", "350981", "350926", "350925", "350922", "350921",
        "350902", "350881", "350825", "350824", "350823", "350822", "350821", "350802", "350784",
        "350783", "350782", "350781", "350725", "350724", "350723", "350722", "350721", "350702",
        "350681", "350629", "350628", "350627", "350625", "350623", "350622", "350603", "350602",
        "350583", "350582", "350581", "350525", "350524", "350521", "350505", "350504", "350481",
        "350429", "350428", "350427", "350426", "350425", "350424", "350423", "350421", "350403",
        "350402", "350322", "350304", "350303", "350302", "350213", "350211", "350206", "350205",
        "350203", "350182", "350181", "350128", "350125", "350124", "350123", "350122", "350121",
        "350111", "350105", "350104", "350103", "350102", "341802", "341623", "341622", "341621",
        "341525", "341524", "341523", "341522", "341521", "341503", "341502", "341324", "341323",
        "341302", "341282", "341226", "341225", "341222", "341221", "341204", "341203", "341202",
        "341182", "341126", "341125", "341124", "341122", "341103", "341102", "340881", "340828",
        "340827", "340826", "340825", "340824", "340822", "340811", "340803", "340802", "340621",
        "340604", "340603", "340602", "340523", "340522", "340521", "340504", "340503", "340421",
        "340406", "340405", "340404", "340403", "340402", "340323", "340322", "340321", "340311",
        "340304", "340303", "340302", "340225", "340223", "340222", "340221", "340208", "340207",
        "340203", "340202", "340181", "340124", "340123", "340122", "340121", "340111", "340104",
        "340103", "340102", "331122", "331121", "331102", "331082", "331081", "331024", "331023",
        "331022", "331021", "331004", "331003", "331002", "330881", "330825", "330824", "330822",
        "330803", "330802", "330784", "330783", "330782", "330781", "330727", "330726", "330723",
        "330703", "330702", "330683", "330682", "330624", "330621", "330602", "330523", "330522",
        "330521", "330503", "330502", "330483", "330482", "330481", "330424", "330421", "330411",
        "330402", "330382", "330381", "330328", "330327", "330326", "330324", "330322", "330304",
        "330303", "330302", "330283", "330282", "330281", "330226", "330225", "330212", "330211",
        "330206", "330205", "330204", "330203", "330185", "330183", "330182", "330122", "330110",
        "330109", "330108", "330106", "330105", "330104", "330103", "330102", "321324", "321323",
        "321322", "321311", "321302", "321203", "321202", "321183", "321182", "321181", "321112",
        "321111", "321102", "321084", "321081", "321012", "321003", "321002", "320982", "320925",
        "320924", "320923", "320922", "320921", "320903", "320902", "320831", "320830", "320829",
        "320826", "320811", "320804", "320803", "320802", "320724", "320723", "320722", "320684",
        "320682", "320681", "320621", "320612", "320611", "320602", "320585", "320583", "320582",
        "320581", "320509", "320508", "320507", "320506", "320505", "320482", "320481", "320412",
        "320411", "320405", "320404", "320402", "320382", "320381", "320324", "320322", "320321",
        "320312", "320311", "320305", "320303", "320302", "320282", "320281", "320211", "320206",
        "320205", "320204", "320203", "320202", "320118", "320117", "320116", "320115", "320114",
        "320113", "320111", "320106", "320105", "320104", "320102", "310120", "310118", "310117",
        "310116", "310115", "310114", "310113", "310112", "310110", "310109", "310108", "310107",
        "310106", "310105", "310104", "310101", "231283", "231282", "231281", "231226", "231225",
        "231224", "231223", "231222", "231221", "231202", "231083", "230921", "230904", "230903",
        "230902", "230882", "230881", "230828", "230826", "230822", "230811", "230805", "230804",
        "230803", "230781", "230624", "230623", "230622", "230621", "230606", "230605", "230604",
        "230603", "230602", "230523", "230522", "230521", "230506", "230505", "230503", "230502",
        "230422", "230421", "230407", "230406", "230405", "230404", "230403", "230402", "230231",
        "230204", "230203", "230202", "230184", "230183", "230182", "230129", "230128", "230127",
        "230126", "230125", "230124", "230123", "230112", "230111", "230110", "230109", "230108",
        "230104", "230103", "230102", "220882", "220881", "220822", "220821", "220802", "220723",
        "220722", "220721", "220702", "220622", "220621", "220605", "220602", "220581", "220524",
        "220523", "220521", "220503", "220502", "220422", "220421", "220403", "220402", "220382",
        "220381", "220323", "220322", "220303", "220302", "220284", "220283", "220282", "220281",
        "220221", "220211", "220204", "220203", "220202", "220183", "220182", "220181", "220122",
        "220112", "220106", "220105", "220104", "220103", "220102", "211481", "211422", "211421",
        "211404", "211403", "211402", "211382", "211381", "211322", "211321", "211303", "211302",
        "211282", "211281", "211224", "211223", "211221", "211204", "211202", "211122", "211121",
        "211103", "211102", "211081", "211021", "211011", "211005", "211004", "211003", "211002",
        "210911", "210905", "210904", "210903", "210902", "210882", "210881", "210811", "210804",
        "210803", "210802", "210782", "210781", "210727", "210726", "210711", "210703", "210702",
        "210681", "210521", "210505", "210504", "210503", "210502", "210423", "210421", "210411",
        "210404", "210403", "210402", "210381", "210321", "210311", "210304", "210303", "210302",
        "210283", "210282", "210281", "210213", "210212", "210211", "210204", "210203", "210202",
        "210181", "210124", "210123", "210122", "210114", "210113", "210112", "210111", "210106",
        "210105", "210104", "210103", "210102", "152921", "150981", "150927", "150926", "150925",
        "150921", "150902", "150826", "150823", "150822", "150821", "150802", "150627", "150625",
        "150621", "150602", "150304", "150303", "150302", "150222", "150221", "150207", "150205",
        "150204", "150203", "150202", "150124", "150123", "150122", "150121", "150105", "150104",
        "150103", "150102", "141182", "141181", "141125", "141122", "141121", "141102", "141082",
        "141081", "141034", "141033", "141029", "141028", "141027", "141026", "141025", "141024",
        "141023", "141022", "141021", "141002", "140981", "140926", "140925", "140924", "140923",
        "140922", "140921", "140902", "140882", "140881", "140830", "140829", "140828", "140827",
        "140826", "140825", "140824", "140823", "140822", "140821", "140802", "140781", "140729",
        "140728", "140727", "140726", "140725", "140724", "140723", "140722", "140721", "140702",
        "140624", "140623", "140622", "140621", "140603", "140602", "140429", "140423", "140421",
        "140411", "140402", "140322", "140321", "140311", "140303", "140302", "140227", "140226",
        "140225", "140224", "140223", "140222", "140221", "140212", "140211", "140203", "140202",
        "140181", "140123", "140122", "140121", "140110", "140109", "140108", "140107", "140106",
        "140105", "131182", "131181", "131128", "131127", "131126", "131125", "131124", "131123",
        "131122", "131121", "131102", "131082", "131081", "131028", "131026", "131025", "131024",
        "131023", "131022", "131003", "131002", "130984", "130983", "130982", "130981", "130930",
        "130929", "130928", "130927", "130926", "130925", "130924", "130923", "130922", "130921",
        "130903", "130902", "130733", "130732", "130731", "130730", "130729", "130728", "130727",
        "130726", "130725", "130724", "130723", "130722", "130721", "130706", "130705", "130703",
        "130702", "130684", "130683", "130682", "130681", "130638", "130637", "130636", "130635",
        "130634", "130633", "130632", "130631", "130630", "130629", "130628", "130627", "130626",
        "130625", "130624", "130623", "130622", "130621", "130604", "130603", "130602", "130582",
        "130581", "130535", "130533", "130532", "130531", "130530", "130529", "130528", "130527",
        "130526", "130525", "130524", "130523", "130522", "130521", "130503", "130502", "130481",
        "130435", "130434", "130433", "130432", "130431", "130430", "130429", "130428", "130427",
        "130426", "130425", "130424", "130423", "130421", "130406", "130404", "130403", "130402",
        "130324", "130322", "130321", "130302", "130283", "130281", "130230", "130229", "130227",
        "130225", "130224", "130223", "130209", "130208", "130207", "130205", "130204", "130203",
        "130202", "130185", "130184", "130183", "130182", "130181", "130133", "130132", "130131",
        "130130", "130129", "130128", "130127", "130126", "130125", "130124", "130123", "130121",
        "130108", "130107", "130105", "130104", "130103", "130102", "120225", "120223", "120221",
        "120116", "120115", "120114", "120113", "120112", "120111", "120110", "120106", "120105",
        "120104", "120103", "120102", "120101", "110229", "110228", "110117", "110116", "110115",
        "110114", "110113", "110112", "110111", "110109", "110108", "110107", "110106", "110105",
        "110102", "110101");

    public static Boolean isTBChannel(String orderSource) {
        if (orderSource.equals("TBSC") || orderSource.equals("TOPBOILER")
            || orderSource.equals("TOPFENXIAO") || orderSource.equals("TONGSHUAI")
            || orderSource.equals("TONGSHUAIFX") || orderSource.equals("TOPMOBILE")
            || orderSource.equals("TOPFENXIAODHSC") || orderSource.equals("TOPSHJD")
            || orderSource.equals("TOPBUYBANG") || orderSource.equals("ZPTH")
            || orderSource.equals("TOPXB") || orderSource.equals("TOPFENXIAOXB")
            || orderSource.equals("BLPMS")) {
            return true;
        }
        return false;
    }

}
