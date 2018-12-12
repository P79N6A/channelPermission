/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.order.model;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.multithread.IExcute;
import com.haier.order.multithread.MultiThreadTool;
import com.haier.order.multithread.ThreadHelper;
import com.haier.order.services.OrderCenterItemServiceImpl;
import com.haier.order.services.OrderCenterOrderBizHelper;
import com.haier.order.util.SpringContextUtil;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *                       
 * @Filename: OrderMarkBuilderModel.java
 * @Version: 1.0
 * @Author: weiyunjun
 * @Email: weiyunjun@ehaier.com
 *
 */
@Service("orderMarkBuilderModel")
public class OrderMarkBuilderModel {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(OrderMarkBuilderModel.class);

    @Autowired
    private OrdersNewService ordersNewService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;
    @Autowired
    private RegionsService regionsService;
    @Autowired
    private InvoicesWwwLogsService invoicesWwwLogsService;
    @Autowired
    private OrderProductsAttributesService orderProductsAttributesService;
    @Autowired
    private OrderCenterItemServiceImpl orderThirdCenterItemService;

    /** 过滤备注指定特殊关键字 */
    private String[] remarkKeyWord = new String[] { "票", "抬", "头", "公司", "增", "税" };
    /** 过滤行政区指定特殊地区id 1263:东胜区;1587:萝北县;1787:富锦市;1795:永红区;2445:黄岛区;2453:市北区;4523355:滨海新区
     *  －－行政区过滤条件都去掉了 2015-09-28.王征需求
     * */
    //    private Integer[] specialRegionId = new Integer[] {};

    /**
     * 自动标建，转人工或自动处理
     */
    public void autoMarkBuilderOrder() {
        // 取所有订单 （数据库中的备注字段有个空格） 每次获取1000条
             List<OrdersNew> orderList = ordersNewService.getBySmConfirmStatus();
        if (null == orderList || orderList.isEmpty()) {
            log.info("自动标建,没有需要处理的数据");
            return;
        }
        execute(orderList);
    }

    private void execute(List<OrdersNew> orderList) {
        ThreadHelper threadHelper = (ThreadHelper) SpringContextUtil.getBean("threadHelper");
        //加入多线程执行
        ExecuteMarkBuilder executeMarkBuilder = new ExecuteMarkBuilder();
        //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
        int splitSize = 100;
        int size = orderList.size();
        if (size > 10 && size <= 100) {
            splitSize = size / 2 + 1;
        }
        new MultiThreadTool<OrdersNew>().processJobs(executeMarkBuilder, threadHelper, log, orderList,
            splitSize, 3);
    }

    private class ExecuteMarkBuilder implements IExcute {

        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            try {
                List<OrdersNew> orderList = (List<OrdersNew>) obj;
                if (null == orderList || orderList.isEmpty()) {
                    return;
                }
                makeBuilderOrderProcess(orderList);
            } catch (Exception e) {
                log.error("[OrderMarkBuilderModel]发生异常", e);
            } finally {
                ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
            }
        }
    }

    public void makeBuilderOrderProcess(List<OrdersNew> ordersList) {
        if (ordersList == null || ordersList.size() == 0) {
            log.info("没有需要请求标建的订单");
            return;
        }
        log.info("此次共捞取 " + ordersList.size() + " 条待处理标建的初始状态数据 ");

        long start = System.currentTimeMillis();
        for (OrdersNew order : ordersList) {
            List<OrderProductsNew> orderProductsList = orderProductsNewService.getByOrderId(Integer.valueOf(order.getId()));
            //2016-11-07 没拆单的不处理
            boolean isCd = true;
            //2016-10-18 判断是否全都是3W单 发票信息处理
            boolean flag3W = false;
            int i3W = 0;
            for (OrderProductsNew tempOp : orderProductsList) {
                if ("3W".equalsIgnoreCase(tempOp.getStockType())) {
                    i3W++;
                    OrderProductsAttributes opa = null;
                    try {
                        opa = orderProductsAttributesService.getByOrderProductId(tempOp.getId());
                    } catch (Exception ex) {
                        log.error("3W标建在OrderProductsAttributes查询到多条，订单ID:" + order.getId()
                                  + ",网单ID:" + tempOp.getId(),
                            ex);
                        isCd = false;
                        break;
                    }

                    if (opa != null && opa.getIsCd() != null && opa.getIsCd().intValue() != 1) {
                        isCd = false;
                        break;
                    }
                }
                //更新网单的物流模式 根据产品的所属库类型
                if ("BLPHH".equalsIgnoreCase(order.getSource())) {
                    ServiceResult<ProductsNew> proResult = orderThirdCenterItemService
                        .getProductBySku(tempOp.getSku());
                    if (null != proResult && proResult.getSuccess()
                        && null != proResult.getResult()) {
                        String shippingMode = proResult.getResult().getShippingMode();
                        if (shippingMode == null) {
                            shippingMode = "";
                        }
                        if (!shippingMode.equals(tempOp.getShippingMode())) {
                            tempOp.setShippingMode(shippingMode);
                            orderProductsNewService.updateShippingModeById(tempOp);
                        }
                    }
                }

            }
            if (!isCd) {
                continue;
            }
            if (i3W == orderProductsList.size()) {
                flag3W = true;
            }
            //非待确认状态的，要特殊处理，统一改为待人工处理
            if (!OrderStatus.OS_UN_CONFIRM.getCode().equals(order.getOrderStatus()) && !flag3W) {
                //如果是取消状态，有可能在获取标建之前被取消
                //如果是其它状态，则原因未知
                try {
                    ordersNewService.updateSmConfirmStatusById(
                        SmConfirmStatusEnum.W_MANUAL_HANDLE.getCode(), Integer.valueOf(order.getId()),
                        SmConfirmStatusEnum.INIT.getCode(), (new Date()).getTime() / 1000,
                        "订单状态不是待确认状态：" + order.getOrderStatus());
                    OrderCenterOrderBizHelper.insertOperateLog(order, "CBS系统", "订单处理方式选择",
                        "获取标建时状态不是‘未确认’，统一改为待人工处理", orderProductsList, shopOrderOperateLogsService);
                } catch (Exception ex) {
                    log.error("更新订单获取标建状态时(订单状态不是待确认状态,orderId:" + order.getId() + ")，发生未知异常：", ex);
                }
                continue;//该记录不再处理
            }
            try {
                //都是3W单子不判断省市区
                if ((!flag3W && OrderCenterOrderBizHelper.orderRemarkAndRegionHandler(order, remarkKeyWord,
                    regionsService))
                    || (flag3W && OrderCenterOrderBizHelper.orderRemarkHandler3W(order, remarkKeyWord))) {//处理开发票和特殊地区设置人工处理
                    //设置手动处理
                    ordersNewService.updateSmConfirmStatusById(
                        SmConfirmStatusEnum.W_MANUAL_HANDLE.getCode(), Integer.valueOf(order.getId()),
                        SmConfirmStatusEnum.INIT.getCode(), (new Date()).getTime() / 1000,
                        "订单备注有特殊关键字或者省市区不全或者区县属于非显示区县");
                    OrderCenterOrderBizHelper.insertOperateLog(order, "CBS系统", "订单处理方式选择",
                        "获取标建成功，订单备注有特殊关键字或者省市区不全或者区县属于非显示区县，转人工处理", orderProductsList,
                        shopOrderOperateLogsService);
                    log.error("[OrderMarkBuilderModel]orderid:" + order.getId() + ",下含"
                              + orderProductsList.size() + "个网单");
                    //2016-10-18 3W网单发票信息特殊处理 设置初始值不开票
                    for (OrderProductsNew tempOp : orderProductsList) {
                        if ("3W".equalsIgnoreCase(tempOp.getStockType())) {
                            log.error("[OrderMarkBuilderModel]orderid:" + order.getId() + ",网单ID:"
                                      + tempOp.getId() + ",是3W网单");
                            tempOp.setMakeReceiptType(0);//开票类型 0 初始值 1 库房开票 2 共享开票
                            orderProductsNewService.updateAfterCreateInvoice(tempOp);
                            InvoicesWwwLogs invLog = invoicesWwwLogsService.get(tempOp.getId());
                            if (invLog == null) {
                                InvoicesWwwLogs invoiceLog = new InvoicesWwwLogs();
                                invoiceLog.setOrderProductId(tempOp.getId());
                                invoiceLog.setOrderId(Integer.valueOf(order.getId()));
                                invoiceLog.setOrderSn(order.getOrderSn());
                                invoiceLog.setSourceSn(order.getSourceOrderSn());
                                invoiceLog.setSource(order.getSource());
                                invoiceLog.setSuccess(0);
                                invoiceLog.setFlag(0);
                                invoiceLog.setAddTime((int) (System.currentTimeMillis() / 1000));
                                invoiceLog.setProcessTime(0);
                                invoiceLog.setLastMessage("");
                                int n = invoicesWwwLogsService.insert(invoiceLog);
                                if (n <= 0) {
                                    log.error("[OrderMarkBuilderModel]插入队列失败："
                                              + JsonUtil.toJson(invoiceLog));
                                }
                            } else {
                                log.error(
                                    "[OrderMarkBuilderModel]根据网单ID查询队列不为空，网单ID：" + tempOp.getId());
                            }
                        } else {
                            log.error("[OrderMarkBuilderModel]orderid:" + order.getId() + ",网单ID:"
                                      + tempOp.getId() + ",非3W网单");
                        }
                    }
                } else {
                    //设置自动处理，大小家电统一都按小家电方式自动处理
                    ordersNewService.updateSmConfirmStatusForAllProductsOrder(Integer.valueOf(order.getId()));
                    OrderCenterOrderBizHelper.insertOperateLog(order, "CBS系统", "订单处理方式选择", "获取标建成功，自动处理",
                        orderProductsList, shopOrderOperateLogsService);
                }
            } catch (Exception e) {
                log.error("订单标建转换为自动处理或人工处理时异常：orderid:" + order.getId(), e);
                //                StringBuilder sb = new StringBuilder();
                //                sb.append(e.toString()).append("\r\n");
                //                for (StackTraceElement key : e.getStackTrace()) {
                //                    sb.append(key.toString()).append("\r\n");
                //                }
                //                log.error("订单标建转换为自动处理或人工处理时异常：orderid:" + order.getId() + ":" + sb.toString());
                OrderCenterOrderBizHelper.insertOperateLog(order, "CBS系统", "订单处理方式选择",
                    "订单orderid:" + order.getId() + ",转换为自动处理或人工处理时异常：" + e.getMessage(),
                    orderProductsList, shopOrderOperateLogsService);
            }
        }
        log.info("获取此批订单进行标建处理耗时：" + (System.currentTimeMillis() - start) + "(ms)");

    }
}
