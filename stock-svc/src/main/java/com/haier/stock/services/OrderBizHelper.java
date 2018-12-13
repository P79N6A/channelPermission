package com.haier.stock.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.distribute.data.model.Regions;
import com.haier.distribute.data.service.RegionsService;
import com.haier.purchase.data.model.PurchaseGdQueue;
import com.haier.shop.model.EcQueues;
import com.haier.shop.model.GroupOrders;
import com.haier.shop.model.HPQueues;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.InvoiceQueue;
import com.haier.shop.model.InvoiceSAPLogs;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.LesQueues;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderQueueExtend;
import com.haier.shop.model.OrderWorkflows;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.model.ProductsNew;
import com.haier.stock.model.Stock;
import com.haier.shop.service.EcQueuesService;
import com.haier.shop.service.GroupOrdersService;
import com.haier.shop.service.HPQueuesService;
import com.haier.shop.service.InvoiceQueueService;
import com.haier.shop.service.InvoicesService;
import com.haier.shop.service.LesQueuesService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrderQueueExtendService;
import com.haier.shop.service.OrdersNewService;
import com.haier.shop.service.ShopInvoiceSAPLogsService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.shop.service.ShopOrderWorkflowsService;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.OrderProductStatus;
import com.haier.stock.model.OrderStatus;
import com.haier.stock.service.StockCenterItemService;
import com.haier.stock.service.StockCenterPurchaseGdService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.util.DateFormatUtil;
import com.haier.stock.util.ReadFile;

public class OrderBizHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderBizHelper.class);

    /**
     * 街道级订单来源
     */
    private static final Set<String> ORDER_STREET_SOURCE = new HashSet<String>() {
        {
            add("1");
            add("DALOU");
            add("DXS");
            add("MOBILE");
            add("BLPMS");
            add("SCFX");
            add("CK");
            add("CK_MOBILE");
            add("DBJ");
            add("PCNEW");
            add("YDYZ");
            add("SCHD");
        }
    };

    /**
     * 生成发票信息推送SAP队列
     *
     * @param
     *
     * @return
     */
    public static InvoiceSAPLogs sapGenerate(ShopInvoiceSAPLogsService invoiceSAPLogsDao,
                                             OrderProductsNewService orderProductsDao, Invoices invoice,
                                             Integer manual, String path) {
        if (manual == null) {
            manual = 0;
        }
        if (invoice == null) {
            return null;
        }

        // 查找SAP黑名单，如果本网单在黑名单内，则不传SAP
        if (!cOrderSnBlackListCheck(invoice, orderProductsDao, path)) {
            return null;
        }

        InvoiceSAPLogs invoiceSAPLogs = null;
        //获得pushType
        Integer pushType = getInvoiceSAPLogsOfPushType(invoice, orderProductsDao);
        LOGGER.info("组织invoiceSAPLogs信息，pushType:" + pushType);
        //组装日志数据
        invoiceSAPLogs = assemblyInvoiceSAPLogsParam(pushType, invoice, manual, invoiceSAPLogsDao);
        if (invoiceSAPLogs == null) {
            LOGGER.error("组织invoiceSAPLogs信息为空，发票id:" + invoice.getId());
            return null;
        }
        return invoiceSAPLogs;
    }

    /**
     * 查找SAP黑名单，如果本网单在黑名单内，则不传SAP
     *
     * @param
     * @return
     */
    public static boolean cOrderSnBlackListCheck(Invoices invoice,
                                                 OrderProductsNewService orderProductsDao, String path) {

        // 查找SAP黑名单，如果本网单在黑名单内，则不传SAP
        String wdfile = ReadFile.read(path);
        if (wdfile != null && !wdfile.equals("")) {
            String items[] = wdfile.split("\r\n");
            if (items != null && items.length > 0) {
                if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {
                    OrderProductsNew op = orderProductsDao.get(invoice.getOrderProductId());
                    if (op != null) {
                        for (int i = 0; i < items.length; i++) {
                            if (op.getCOrderSn().equals(items[i].trim())) {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 组装 发票SAP日志的PushType的值
     * @param invoice
     * @return
     */
    public static Integer getInvoiceSAPLogsOfPushType(Invoices invoice,
                                                      OrderProductsNewService orderProductsDao) {
        Integer pushType = 0;
        if (InvoiceConst.COMPLETE_STATE.equals(invoice.getState())) {

            boolean flag = true;
            // 9月1号之前的开票信息不传SAP
            //            if ($invoice->billingTime >= strtotime('2013-09-01')) {
            //                $flag = true;
            //            }

            if (flag) {
                if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoice.getCOrderType())) {
                    OrderProductsNew orderProduct = orderProductsDao.get(invoice.getOrderProductId());
                    if (orderProduct != null) {
                        // 同步向SAP传递开票信息
                        if (!InvoiceConst.SPLIT_NEW.equals(orderProduct.getSplitFlag())) {
                            if (!orderProduct.getCOrderSn().equals(invoice.getCOrderSn())) {
                                // 网单与发票的网单号不相等，代表是 作废/红冲 后重新开票
                                pushType = InvoiceConst.SAP_TYPE_VOUCHER;
                            } else {
                                pushType = InvoiceConst.SAP_TYPE_NORMAL;
                            }
                        } else {
                            // 拆单后开票，需要按二次开票推送
                            pushType = InvoiceConst.SAP_TYPE_VOUCHER;
                        }
                    }
                } else if (InvoiceConst.DIFF_CORDER_TYPE.equals(invoice.getCOrderType())) {
                    // 同步向SAP传递开票信息
                    // 差异订单开票前需要先将订单信息传SAP
                    //                    Order2ths::sendSAP($invoice->diffId);   //只在开票时传sap，作废时不需要再传
                    pushType = InvoiceConst.SAP_TYPE_NORMAL;
                } else {
                    // 同步向SAP传递开票信息
                    pushType = InvoiceConst.SAP_TYPE_VOUCHER;
                }
            }
        }
        if (InvoiceConst.INVALID_KP_STATE.equals(invoice.getEaiWriteState())
            || InvoiceConst.MONTH_INVALID_KP_STATE.equals(invoice.getEaiWriteState())) {
            // 同步向SAP传递发票作废/红冲信息
            pushType = InvoiceConst.SAP_TYPE_INVALID;
        }
        return pushType;
    }

    /**
     * 组装 发票SAP日志参数
     * @param invoice
     * @return
     */
    private static InvoiceSAPLogs assemblyInvoiceSAPLogsParam(Integer pushType, Invoices invoice,
                                                              Integer manual,
                                                              ShopInvoiceSAPLogsService invoiceSAPLogsDao) {
        if (pushType == null || (!pushType.equals(InvoiceConst.SAP_TYPE_NORMAL)
                                 && !pushType.equals(InvoiceConst.SAP_TYPE_INVALID)
                                 && !pushType.equals(InvoiceConst.SAP_TYPE_VOUCHER))) {
            LOGGER.error("组装InvoiceSAPLogs参数失败，pushType参数不在允许范围内，发票id:" + invoice.getId() + "");
            return null;
        }
        InvoiceSAPLogs invoiceSAPLogs = new InvoiceSAPLogs();
        invoiceSAPLogs.setInvoiceId(invoice.getId());
        invoiceSAPLogs.setPushType(pushType);
        List<InvoiceSAPLogs> isl = invoiceSAPLogsDao.getByInvoiceIdAndPushType(invoiceSAPLogs);
        if (manual.equals(0) && isl != null && isl.size() > 0) {
            LOGGER.error(
                "组装InvoiceSAPLogs参数失败，该日志表已经记录发票id为" + invoice.getId() + "类型为" + pushType + "的日志，");
            return null;
        }

        InvoiceSAPLogs log = new InvoiceSAPLogs();
        log.setAddTime((int) (new Date().getTime() / 1000));
        log.setPushData("");//json格式数据，在定时推送sap时有更新该字段
        log.setcOrderType(Integer.parseInt(invoice.getCOrderType().toString()));
        log.setOrderProductId(invoice.getOrderProductId());
        log.setDiffId(invoice.getDiffId());
        log.setInvoiceId(invoice.getId());
        log.setPushType(pushType);//在定时推送sap时有更新该字段
        log.setReturnData("");
        log.setSuccess(0);
        log.setCount(0);
        log.setLastTime(0);
        log.setLastMessage("");
        return log;
    }

    /**
     * 处理开票或作废后同步sap发票信息
     */
    public static boolean handelInvoiceSAPLogs(Invoices invoices, Integer pushType,
                                               OrderProductsNewService orderProductsDao,
                                               ShopInvoiceSAPLogsService invoiceSAPLogsDao, String path) {
        // 查找SAP黑名单，如果本网单在黑名单内，则不传SAP
        if (!cOrderSnBlackListCheck(invoices, orderProductsDao, path)) {
            return true;
        }
        InvoiceSAPLogs isap = new InvoiceSAPLogs();
        isap.setInvoiceId(invoices.getId());
        isap.setPushType(pushType);
        List<InvoiceSAPLogs> isaplist = invoiceSAPLogsDao.getByInvoiceIdAndPushType(isap);
        if (isaplist == null || isaplist.size() == 0) {
            isap.setAddTime((int) (new Date().getTime() / 1000));
            isap.setcOrderType(Integer.parseInt(invoices.getCOrderType().toString()));
            isap.setOrderProductId(invoices.getOrderProductId());
            isap.setDiffId(invoices.getDiffId());
            isap.setPushData("");
            isap.setReturnData("");
            isap.setSuccess(0);
            isap.setCount(0);
            isap.setLastTime(0);
            isap.setLastMessage("");
            invoiceSAPLogsDao.insert(isap);
        } else {
            for (int i = 0; i < isaplist.size(); i++) {
                if (isaplist.get(i).getSuccess().equals(0)
                    && isaplist.get(i).getCount().intValue() >= 15) {
                    InvoiceSAPLogs invoiceSAPLogs = isaplist.get(i);
                    invoiceSAPLogs.setCount(0);
                    invoiceSAPLogs.setLastMessage(
                        "[" + DateFormatUtil.format(new Date()) + "]同步程序自动重置count值");
                    invoiceSAPLogsDao.updateInvoiceSAPLogs(invoiceSAPLogs);
                }
            }
        }
        return true;
    }

    /**
     * 更新发票信息并写sap
     */
    public static boolean updateInvoiceInfo(Invoices invoices, Integer pushType,
                                            InvoicesService invoicesDao,
                                            OrderProductsNewService orderProductsDao,
                                            ShopInvoiceSAPLogsService invoiceSAPLogsDao, String path) {
        if (pushType == null
            || (!pushType.equals(1) && !pushType.equals(2) && !pushType.equals(3))) {
            return false;
        }
        try {
            if (invoicesDao.update(invoices) > 0) {
                return handelInvoiceSAPLogs(invoices, pushType, orderProductsDao, invoiceSAPLogsDao,
                    path);
            } else {
                //                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                //                invoicesDao.update(invoices);
                return false;
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.toString()).append("\r\n");
            for (StackTraceElement key : e.getStackTrace()) {
                sb.append(key.toString()).append("\r\n");
            }
            LOGGER.error("同步发票信息-作废时异常字符串:" + sb.toString());
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesDao.update(invoices);
            return false;
        }
    }

    /**
     * 更新作废发票信息，并且检查sap队列是否存在，不存在要写入，主要要比较日期，某个一个以后的才行，否则会把以前的也写入队列推到sap系统
     * 
     */
    @Deprecated
    public static boolean eInvoiceEntityToInvalidInvoicesOld(Map<String, String> src,
                                                             Invoices target) {
        //验证发票数据
        if (src == null || target == null || src.get("validTime") == null
            || src.get("validTime").equals("")) {
            return false;
        }
        if (src.get("status").equals("3")) {//作废红冲
            Long valid = Long.parseLong(src.get("validTime"));
            target.setInvalidTime(valid.longValue());
            target.setInvalidReason(src.get("validReason"));
            target.setTryNum((target.getTryNum() == null ? 1 : target.getTryNum()) + 1);
            target.setEaiWriteState(InvoiceConst.MONTH_INVALID_KP_STATE);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新作废发票信息，并且检查sap队列是否存在，不存在要写入，主要要比较日期，某个一个以后的才行，否则会把以前的也写入队列推到sap系统
     * 
     */
    public static boolean eInvoiceEntityToInvalidInvoices(Map<String, String> src,
                                                          Invoices target) {
        //验证发票数据
        if (src == null || target == null || src.get("validTime") == null
            || src.get("validTime").equals("")) {
            return false;
        }
        if (src.get("status").equals("3")) {//作废红冲
            Long valid = Long.parseLong(src.get("validTime"));
            target.setInvalidTime(valid.longValue());
            target.setInvalidReason(src.get("validReason"));
            target.setTryNum((target.getTryNum() == null ? 1 : target.getTryNum()) + 1);
            target.setEaiWriteState(InvoiceConst.MONTH_INVALID_KP_STATE);

            //作废发票,保留原发票信息
            //target.setInvoiceNumber(src.get("code") != null ? src.get("code") : "");
            //target.setEInvViewUrl(src.get("viewUrl") != null ? src.get("viewUrl") : "");
            //target.setFiscalCode(src.get("fiscalCode") != null ? src.get("fiscalCode") : "");
            //target.setCheckCode(src.get("checkCode") != null ? src.get("checkCode") : "");//V.20版本新增校验码
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新发票信息，并且检查sap队列是否存在，不存在要写入，主要要比较日期，某个日期以后的才行，否则会把以前的也写入队列推到sap系统
     * 
     */
    @Deprecated
    public static boolean eInvoiceEntityToInvoicesOld(Map<String, String> src, Invoices target) {
        //验证发票数据
        if (src == null || target == null) {
            return false;
        }
        if ((src.get("validTime") == null || src.get("validTime").equals(""))
            && (src.get("generateTime") == null || src.get("generateTime").equals(""))) {
            return false;
        }
        //status=3冲红     status=2作废   status=1开票   status=4被红冲
        if (src.get("status").equals("1")) {
            Long generate = Long.parseLong(src.get("generateTime"));
            target.setBillingTime(generate.longValue());
            target.setState( InvoiceConst.COMPLETE_STATE.intValue());
            if (target.getStatusType().equals(1) && src.get("generateTime") != null
                && !src.get("generateTime").equals("") && !target.getSuccess().equals(1)) {
                target.setSuccess(1);
            }
            target.setEaiWriteState(InvoiceConst.NORMAL_KP_STATE);
            target.setDrawer("CBS");
            target.setInvoiceNumber(src.get("code"));
            target.setEInvViewUrl(src.get("viewUrl"));
            target.setFiscalCode(src.get("fiscalCode"));
            target.setTryNum((target.getTryNum() == null ? 1 : target.getTryNum()) + 1);
        } else if (src.get("status").equals("3")) {//作废红冲  被红冲
            Long valid = Long.parseLong(src.get("validTime"));
            target.setInvalidTime(valid.longValue());
            target.setInvalidReason(src.get("validReason"));
            target.setEaiWriteState(InvoiceConst.MONTH_INVALID_KP_STATE);
            if (target.getStatusType().equals(4) && src.get("validTime") != null
                && !src.get("validTime").equals("") && !target.getSuccess().equals(1)) {
                target.setSuccess(1);
            }
            target.setTryNum((target.getTryNum() == null ? 1 : target.getTryNum()) + 1);
        }
        return true;
    }

    /**
     * 更新发票信息，并且检查sap队列是否存在，不存在要写入，主要要比较日期，某个日期以后的才行，否则会把以前的也写入队列推到sap系统
     * 
     */
    public static boolean eInvoiceEntityToInvoices(Map<String, String> src, Invoices target) {
        //验证发票数据
        if (src == null || target == null) {
            return false;
        }
        if ((src.get("validTime") == null || src.get("validTime").equals(""))
            && (src.get("generateTime") == null || src.get("generateTime").equals(""))) {
            return false;
        }
        //status=3冲红     status=2作废   status=1开票   status=4被红冲
        if (src.get("status").equals("1")) {
            Long generate = Long.parseLong(src.get("generateTime"));
            target.setBillingTime(generate.longValue());
            target.setState(InvoiceConst.COMPLETE_STATE.intValue());
            if (target.getStatusType().equals(1) && src.get("generateTime") != null
                && !src.get("generateTime").equals("") && !target.getSuccess().equals(1)) {
                target.setSuccess(1);
            }
            target.setEaiWriteState(InvoiceConst.NORMAL_KP_STATE);
            target.setDrawer("CBS");
            target.setInvoiceNumber(src.get("code"));
            target.setEInvViewUrl(src.get("viewUrl"));
            target.setFiscalCode(src.get("fiscalCode"));
            target.setCheckCode(src.get("checkCode"));
            target.setTryNum((target.getTryNum() == null ? 1 : target.getTryNum()) + 1);
        } else if (src.get("status").equals("3")) {//作废红冲  被红冲
            Long valid = Long.parseLong(src.get("validTime"));
            target.setInvalidTime(valid.longValue());
            target.setInvalidReason(src.get("validReason"));
            target.setEaiWriteState(InvoiceConst.MONTH_INVALID_KP_STATE);
            if (target.getStatusType().equals(4) && src.get("validTime") != null
                && !src.get("validTime").equals("") && !target.getSuccess().equals(1)) {
                target.setSuccess(1);
            }
            target.setTryNum((target.getTryNum() == null ? 1 : target.getTryNum()) + 1);
        }

        //作废发票,保留原发票信息
        //target.setInvoiceNumber(src.get("code") != null ? src.get("code") : "");
        //target.setEInvViewUrl(src.get("viewUrl") != null ? src.get("viewUrl") : "");
        //target.setFiscalCode(src.get("fiscalCode") != null ? src.get("fiscalCode") : "");
        //target.setCheckCode(src.get("checkCode") != null ? src.get("checkCode") : "");//V.20版本新增校验码

        return true;
    }

    /**
     * 校验省、市、区县信息是否完整,区县是否合法
     * @param orders
     * @param regionActiveMap
     * @return
     */
    public static boolean checkCanConfirm(OrdersNew orders, Map<Integer, Boolean> regionActiveMap,
                                          OrdersNewService ordersDao) {
        if (orders == null) {
            return false;
        }
        boolean judgeStreetOrder = OrderBizHelper.judgeStreetOrder(orders.getSource(),
            orders.getAddTime());
        if (judgeStreetOrder) {
            //省、市、区县、街道信息是否完整,Regions activeFlag=1否则转人工
            if (orders.getProvince().intValue() == 0 || orders.getCity().intValue() == 0
                || orders.getRegion().intValue() == 0 || orders.getStreet().intValue() == 0
                || !regionActiveMap.containsKey(orders.getStreet())
                || !regionActiveMap.get(orders.getStreet())) {
                orders.setSmConfirmStatus(3);//待人工确认
                //更新订单
                ordersDao.updateSmConfirmStatus(orders);
                LOGGER.error("订单" + orders.getOrderSn() + "省市区不全或区不存在,需要人工确认");
                return false;
            }
        } else {
            //省、市、区县信息是否完整,Regions activeFlag=1否则转人工
            if (orders.getProvince().intValue() == 0 || orders.getCity().intValue() == 0
                || orders.getRegion().intValue() == 0
                || !regionActiveMap.containsKey(orders.getRegion())
                || !regionActiveMap.get(orders.getRegion())) {
                orders.setSmConfirmStatus(3);//待人工确认
                //更新订单
                ordersDao.updateSmConfirmStatus(orders);
                LOGGER.error("订单" + orders.getOrderSn() + "省市区不全或区不存在,需要人工确认");
                return false;
            }
        }
        return true;
    }

    /**
     * 查询订单的行政区
     * @param unFrozenOpList
     * @param regionActiveMap
     * @param orderIdOrdersMap
     * @param ordersDao
     * @param regionsDao
     */
    public static void preQueryRegionAndOrders(List<OrderProductsNew> unFrozenOpList,
                                               Map<Integer, Boolean> regionActiveMap,
                                               Map<Integer, OrdersNew> orderIdOrdersMap,
                                               OrdersNewService ordersDao, RegionsService regionsDao) {
        Set<Integer> orderIdSet = new HashSet<Integer>();
        for (OrderProductsNew orderProducts : unFrozenOpList) {
            orderIdSet.add(orderProducts.getOrderId());
        }

        //根据订单id查询订单
        List<OrdersNew> ordersList = ordersDao.getByIdsForConfirm(new ArrayList<Integer>(orderIdSet));
        if (null == ordersList || ordersList.isEmpty()) {
            return;
        }
        //2016-11-12
        StringBuilder sb = new StringBuilder();
        for (OrdersNew orders : ordersList) {
            orderIdOrdersMap.put(Integer.valueOf(orders.getId()), orders);
            boolean judgeStreetOrder = OrderBizHelper.judgeStreetOrder(orders.getSource(),
                orders.getAddTime());
            if (judgeStreetOrder) {
                sb.append(orders.getStreet());
                sb.append(",");
            } else {
                sb.append(orders.getRegion());
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        //查询Regions表
        List<Regions> regionsList = regionsDao.getByIds(sb.toString());
        if (null == regionsList || regionsList.isEmpty()) {
            return;
        }
        for (Regions regions : regionsList) {
            regionActiveMap.put(regions.getId(), Boolean.parseBoolean(regions.getActiveFlag().toString()));
        }
    }

    /**
     * 处理未分配库位的网单
     * @param orderProducts
     * @param orders
     * @param stockResult
     * @param orderOperateLogsDao
     * @param orderProductsDao
     */
    public static void handleNoSCode(OrderProductsNew orderProducts, OrdersNew orders,
                                     ServiceResult<Stock> stockResult,
                                     ShopOrderOperateLogsService orderOperateLogsDao,
                                     OrderProductsNewService orderProductsDao) {
        orderOperateLogsDao.insert(constructOperateLog(orders, orderProducts, "系统", "占用库存",
            "占用库存时分配库位失败,来源:" + orders.getSource(), null));
        LOGGER.error(orderProducts.getCOrderSn() + ",占用库存时分配库位失败:" + stockResult.getMessage()
                     + ",来源:" + orders.getSource());
        //更新网单库位和占用库存数量,状态
        orderProducts.setLockedNumber(123456789);
        orderProducts.setStatus(OrderProductsNew.STATUS_STRAT_STATUS);
        orderProductsDao.updateForFrozenStock(orderProducts);
    }

    /**
     * 构造订单操作日志对象
     * @param orders
     * @param orderProducts
     * @param operator
     * @param changeLog
     * @param remark
     * @param log
     * @return
     */
    public static OrderOperateLogs constructOperateLog(OrdersNew orders, OrderProductsNew orderProducts,
                                                       String operator, String changeLog,
                                                       String remark, OrderOperateLogs log) {
        log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
        log.setPaymentStatus(
            null == orders || orders.getPaymentStatus() == null ? 0 : orders.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? ""
            : (remark.length() > 255 ? remark.substring(0, 255) : remark));
        log.setSiteId(1);
        log.setOrderId(Integer.valueOf(orders.getId()));
        if (orderProducts != null) {
            log.setNetPointId(orderProducts.getNetPointId());
            log.setOrderProductId(orderProducts.getId());
            log.setStatus(orderProducts.getStatus());
        } else {
            log.setNetPointId(0);
            log.setOrderProductId(0);
            log.setStatus(0);
        }
        return log;
    }

    /**
     * 更新销量
     * @param orderProducts
     */
    public static void updateSaleNum(OrderProductsNew orderProducts,
                                     StockCenterItemService itemService) throws Exception {
        ProductsNew products = new ProductsNew();
        products.setSku(orderProducts.getSku());
        products.setSaleNum(orderProducts.getNumber());
        try {
            ServiceResult<Boolean> result = itemService.updateSaleNumBySku(products);
            if (result == null) {
                LOGGER.error("占用库存时，更新销量失败（itemService.updateSaleNumBySku）,返回对象为空！");
                throw new BusinessException("占用库存时，更新销量失败（itemService.updateSaleNumBySku）,返回对象为空！");
            }
            if (!(result.getResult() && result.getSuccess())) {
                LOGGER.error("占用库存时，更新销量未成功（itemService.updateSaleNumBySku）" + result.getMessage());
                throw new BusinessException(
                    "占用库存时，更新销量未成功（itemService.updateSaleNumBySku）" + result.getMessage());
            }
        } catch (Exception e) {
            LOGGER.error("占用库存时，更新销量错误（OrderBizHelper.updateSaleNum）" + e.getMessage());
            throw new BusinessException(
                "占用库存时，更新销量错误（FrozenStockModel.updateSaleNum）" + e.getMessage());
        }
    }

    /**
     * 冻结库存失败更新网单
     */
    public static boolean updateOrderProductsForFail(ServiceResult<String> frozenResult,
                                                     OrderProductsNew orderProducts, OrdersNew orders,
                                                     OrderProductsNewService orderProductsDao,
                                                     ShopOrderOperateLogsService orderOperateLogsDao) {
        //更新网单库位和占用库存数量,状态
        orderProducts.setLockedNumber(123456789);
        orderProductsDao.updateForFrozenStock(orderProducts);
        String message = frozenResult.getMessage();
        if (message.length() > 50) {
            message = message.substring(0, 50);
        }
        orderOperateLogsDao.insert(
            constructOperateLog(orders, orderProducts, "系统", "占用库存", "占用库存失败:" + message, null));
        LOGGER.error(orderProducts.getCOrderSn() + ",占用库存失败:" + message);
        return false;
    }

    /**
     * 冻结库存成功更新网单
     */
    public static boolean updateOrderProductsForSuccess(Stock stock,
                                                        ServiceResult<String> frozenResult,
                                                        OrderProductsNew orderProducts, OrdersNew orders,
                                                        Set<OrderProductsNew> frozenStockOrderProductSet,
                                                        OrderProductsNewService orderProductsDao,
                                                        ShopOrderOperateLogsService orderOperateLogsDao) {
        //更新网单库位和占用库存数量,状态
        if (null != stock) {
            orderProducts.setSCode(stock.getStoreCode());
            String tsCode = StringUtils.isBlank(stock.getTsSecCode()) ? "" : stock.getTsSecCode();
            if (tsCode.equalsIgnoreCase(stock.getStoreCode())) {
                tsCode = "";
            }
            orderProducts.setTsCode(tsCode);
            orderProducts.setTsShippingTime(
                stock.getTsShippingTime() == null ? 0 : stock.getTsShippingTime());
        }
        orderProducts.setLockedNumber(orderProducts.getNumber());
        orderProducts.setStatus(OrderProductsNew.STATUS_FROZEN_STOCK);
        int updateNumber = orderProductsDao.updateForFrozenStock(orderProducts);
        //并发或取消订单网单
        if (updateNumber < 1) {
            LOGGER.info("占用库存失败:opid==" + orderProducts.getId() + ",已经取消或出现并发");
            return false;
        }
        frozenStockOrderProductSet.add(orderProducts);
        orderOperateLogsDao
            .insert(constructOperateLog(orders, orderProducts, "系统", "占用库存", "占用库存成功", null));
        LOGGER.info("占用库存成功:opid==" + orderProducts.getId() + ",sCode==" + orderProducts.getSCode()
                    + ",tsCode==" + orderProducts.getTsCode() + ",结果:" + frozenResult.getResult());
        return true;
    }

    /**
     * 冻结库存成功更新网单
     */
    public static boolean updateOrderProductsForSuccess(String storeCode,
                                                        ServiceResult<String> frozenResult,
                                                        OrderProductsNew orderProducts, OrdersNew orders,
                                                        Set<OrderProductsNew> frozenStockOrderProductSet,
                                                        OrderProductsNewService orderProductsDao,
                                                        ShopOrderOperateLogsService orderOperateLogsDao) {
        //更新网单库位和占用库存数量,状态
        if (null != storeCode && !StringUtil.isEmpty(storeCode)) {
            orderProducts.setSCode(storeCode);
            orderProducts.setTsShippingTime(0);
        }
        orderProducts.setLockedNumber(orderProducts.getNumber());
        orderProducts.setStatus(OrderProductsNew.STATUS_FROZEN_STOCK);
        int updateNumber = orderProductsDao.updateForFrozenStock(orderProducts);
        //并发或取消订单网单
        if (updateNumber < 1) {
            LOGGER.info("占用库存失败:opid==" + orderProducts.getId() + ",已经取消或出现并发");
            return false;
        }
        frozenStockOrderProductSet.add(orderProducts);
        orderOperateLogsDao
            .insert(constructOperateLog(orders, orderProducts, "系统", "占用库存", "占用库存成功", null));
        LOGGER.info("占用库存成功:opid==" + orderProducts.getId() + ",sCode==" + orderProducts.getSCode()
                    + ",tsCode==" + orderProducts.getTsCode() + ",结果:" + frozenResult.getResult());
        return true;
    }

    /**
     * 插入操作日志
     * @param orders
     * @param user
     * @param orderProductsList
     */
    public static void insertOperateLog(OrdersNew orders, String user,
                                        List<OrderProductsNew> orderProductsList,
                                        ShopOrderOperateLogsService orderOperateLogsDao) {
        OrderOperateLogs log = null;
        List<OrderOperateLogs> orderOperateLogsList = new ArrayList<OrderOperateLogs>();
        String remark = "订单状态从“未确认”改为“已确认”。";
        if (OrderStatus.OS_NOSTOCK.getCode().intValue() == orders.getOrderStatus().intValue()) {
            remark = "订单状态从“未确认”改为“部分缺货”。";
        }
        for (OrderProductsNew orderProducts : orderProductsList) {
            orderOperateLogsList
                .add(constructOperateLog(orders, orderProducts, user, "确认订单成功", remark, log));
        }
        if (!orderOperateLogsList.isEmpty()) {
            orderOperateLogsDao.batchInsert(orderOperateLogsList);
        }
    }

    /**
     * 插入操作日志
     * @param orders
     * @param user
     * @param orderProductsList
     */
    public static void insertOperateLog(OrdersNew orders, String user, String changelog, String remark,
                                        List<OrderProductsNew> orderProductsList,
                                        ShopOrderOperateLogsService orderOperateLogsDao) {
        OrderOperateLogs log = null;
        List<OrderOperateLogs> orderOperateLogsList = new ArrayList<OrderOperateLogs>();
        for (OrderProductsNew orderProducts : orderProductsList) {
            orderOperateLogsList
                .add(constructOperateLog(orders, orderProducts, user, changelog, remark, log));
        }
        if (!orderOperateLogsList.isEmpty()) {
            orderOperateLogsDao.batchInsert(orderOperateLogsList);
        }
    }

    public static Map<Integer, List<OrderProductsNew>> getOrderProducts(List<OrdersNew> orderList,
                                                                        OrderProductsNewService orderProductsDao) {
        Map<Integer, List<OrderProductsNew>> map = new HashMap<Integer, List<OrderProductsNew>>();
        Set<Integer> orderIdSet = new HashSet<Integer>();
        for (OrdersNew orders : orderList) {
            orderIdSet.add(Integer.valueOf(orders.getId()));
        }
        List<OrderProductsNew> orderProductsList = orderProductsDao.getByOrderIdsForConfirm(new ArrayList<Integer>(orderIdSet));
        if (null == orderProductsList || orderProductsList.isEmpty()) {
            return map;
        }
        List<OrderProductsNew> orderProductList = null;
        for (OrderProductsNew orderProducts : orderProductsList) {
            if (map.containsKey(orderProducts.getOrderId())) {
                orderProductList = map.get(orderProducts.getOrderId());
            } else {
                orderProductList = new ArrayList<OrderProductsNew>();
                map.put(orderProducts.getOrderId(), orderProductList);
            }
            orderProductList.add(orderProducts);
        }
        return map;
    }

    /**
     * 检查大家电类别是否全部占有库存，如果有一个未占有就去掉所有大家电，小家电只要占用库存的网单就加入队列
     * @param orderProductsList
     */
    public static List<OrderProductsNew> splitOrderProductList(List<OrderProductsNew> orderProductsList) {
        List<OrderProductsNew> b2cOrderProductsList = new ArrayList<OrderProductsNew>();
        List<OrderProductsNew> b2b2cOrderProductsList = new ArrayList<OrderProductsNew>();
        List<OrderProductsNew> subOrderProductsList = new ArrayList<OrderProductsNew>();
        boolean b2b2cStockFlag = true;//如果所有大家电都占用库存就保存队列，否则不保存
        for (OrderProductsNew orderProducts : orderProductsList) {
            if ("B2C".equalsIgnoreCase(orderProducts.getShippingMode())) {//‘B2C’为小家电
                if (OrderProductsNew.STATUS_FROZEN_STOCK.equals(orderProducts.getStatus())) {
                    b2cOrderProductsList.add(orderProducts);
                }
            } else {//为空和‘B2B2C’是大家电
                if (OrderProductsNew.STATUS_FROZEN_STOCK.equals(orderProducts.getStatus())) {
                    b2b2cOrderProductsList.add(orderProducts);
                } else {
                    b2b2cStockFlag = false;
                }
            }
        }
        subOrderProductsList.addAll(b2cOrderProductsList);
        if (!b2b2cStockFlag) {
            b2b2cOrderProductsList.clear();
        } else {
            subOrderProductsList.addAll(b2b2cOrderProductsList);
        }
        return subOrderProductsList;
    }

    /**
     * 检查网单列表中是否有指定网单类型并且已占库存，如果不是就移除
     * @param orderProductsList
     * @param type 网单类型（WA:自由库存网单；STORE:店铺网单；）
     */
    public static List<OrderProductsNew> filterOrderProductList(List<OrderProductsNew> orderProductsList,
                                                                String type) {
        List<OrderProductsNew> subOrderProductsList = new ArrayList<OrderProductsNew>();
        for (OrderProductsNew orderProducts : orderProductsList) {
            if (type.equalsIgnoreCase(orderProducts.getStockType())) {
                subOrderProductsList.add(orderProducts);
            }
        }
        return subOrderProductsList;
    }

    /**
     * 确认订单
     * @param orders
     * @param user
     * @param orderProductsList
     * @return
     */
    public static boolean doConfirm(OrdersNew orders, String user,
                                    List<OrderProductsNew> orderProductsList, OrdersNewService ordersDao,
                                    ShopOrderWorkflowsService orderWorkflowsDao) {
        Integer mysqlTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
        OrderWorkflows orderWorkflows = new OrderWorkflows();
        Integer status = OrderStatus.OS_CONFIRM.getCode();
        OrderProductsNew orderProducts = null;
        for (Iterator<OrderProductsNew> it = orderProductsList.iterator(); it.hasNext();) {
            orderProducts = it.next();
            if (orderProducts.getStatus().intValue() == 0) { // 如果存在网单未分配库存，订单状态需改为部分缺货。
                status = OrderStatus.OS_NOSTOCK.getCode();
                it.remove();
                break;
            }
            if (orderProducts.getStatus().intValue() != OrderProductsNew.STATUS_FROZEN_STOCK
                .intValue()) {//只处理网单状态为已分配库位（1）的记录
                it.remove();
            }
        }

        //如果以前已经是部分缺货,订单状态未改变,什么都不做
        if (orderProductsList.isEmpty()
            || status.intValue() == orders.getOrderStatus().intValue()) {
            return false;
        }

        orders.setConfirmTime(mysqlTime);
        if (null == orders.getFirstConfirmPerson()) {
            orders.setFirstConfirmPerson(user);
        }
        if (orders.getFirstConfirmTime().intValue() == 0) {
            orders.setFirstConfirmTime(mysqlTime);
        }
        orders.setOrderStatus(status);
        //更新订单
        int updateNumber = ordersDao.updateForConfirm(orders);
        //出现并发或取消订单
        if (updateNumber < 1) {
            orderProductsList.clear();
            LOGGER.info("出现并发或已取消订单,order id=" + orders.getId());
            return false;
        }
        //更新全流程表
        orderWorkflows.setOrderId(Integer.valueOf(orders.getId()));
        orderWorkflows.setConfirmPeople(user);
        orderWorkflows.setConfirmTime(mysqlTime.longValue());
        orderWorkflowsDao.updateForConformOrder(orderWorkflows);
        return true;
    }

    /**
     * 插入ec队列表
     * @param orderProductsList
     */
    public static int saveEcQueues(List<OrderProductsNew> orderProductsList, EcQueuesService ecQueuesService) {
        if (orderProductsList == null || orderProductsList.size() < 1) {
            return 0;
        }
        int count = 0;
        for (Iterator<OrderProductsNew> it = orderProductsList.iterator(); it.hasNext();) {
        	OrderProductsNew op = it.next();
            EcQueues eq = null;
            eq = ecQueuesService.getByOrderProductId(op.getId());
            if (eq == null) {
                eq = new EcQueues();
                eq.setOrderId(op.getOrderId());
                eq.setOrderProductId(op.getId());
                eq.setAddTime((int) (new Date().getTime() / 1000));
                eq.setPushData("");
                eq.setReturnData("");
                eq.setSuccess(0);
                eq.setCount(0);
                eq.setSuccessTime(0);
                eq.setLastTryTime(0);
                count = count + ecQueuesService.insert(eq);
            } else {
                it.remove();
            }
        }
        return count;
    }

    /**
     * 订单处理 备注和特殊行政区-返回true表示有特殊地区或者要开发票需要人工处理，
     * Integer[] specialRegionId 行政区过滤条件都去掉了 2015-09-28.王征需求
     * @param order
     * @return
     */
    public static boolean orderRemarkAndRegionHandler(OrdersNew order, String[] remarkKeyWord,
                                                      RegionsService regionsDao) {
        //商城PC,中国联通_广电,商城订单_海尔地产,商城PC_文化展体验店,商城PC_O2O,海朋转TC订单,C2B滚筒洗衣机,商城移动,不良品买损,
        //商城PC_样品机,日日顺,商城订单-分销渠道,顺逛PC,顺逛wap,商城PC_夺宝机,商圈小微,新品平台众筹订单,商城PC_不良品换货,商城PC_新品,
        //移动商城_有赞微商城,顺逛app,690创客,商城PC_海贝,移动商城_口袋购物,商城移动_app,商城移动_优家,微店_萌店,商城PC_恒大,商城PC_返利网,
        //不判断备注
        if (!"1".equalsIgnoreCase(order.getSource())
            && !"CHINAUNICOM".equalsIgnoreCase(order.getSource())
            && !"112".equalsIgnoreCase(order.getSource())
            && !"DALOU".equalsIgnoreCase(order.getSource())
            && !"DXS".equalsIgnoreCase(order.getSource())
            && !"HPB2B".equalsIgnoreCase(order.getSource())
            && !"C2BWASHER".equalsIgnoreCase(order.getSource())
            && !"MOBILE".equalsIgnoreCase(order.getSource())
            && !"BLPMS".equalsIgnoreCase(order.getSource())
            && !"COS".equalsIgnoreCase(order.getSource())
            && !"RRS".equalsIgnoreCase(order.getSource())
            && !"SCFX".equalsIgnoreCase(order.getSource())
            && !"CK".equalsIgnoreCase(order.getSource())
            && !"CK_MOBILE".equalsIgnoreCase(order.getSource())
            && !"DBJ".equalsIgnoreCase(order.getSource())
            && !"SQXW".equalsIgnoreCase(order.getSource())
            && !"XPZC".equalsIgnoreCase(order.getSource())
            && !"BLPHH".equalsIgnoreCase(order.getSource())
            && !"PCNEW".equalsIgnoreCase(order.getSource())
            && !"YDYZ".equalsIgnoreCase(order.getSource())
            && !"MSTORE".equalsIgnoreCase(order.getSource())
            && !"690CK".equalsIgnoreCase(order.getSource())
            && !"HBSC".equalsIgnoreCase(order.getSource())
            && !"KDGW".equalsIgnoreCase(order.getSource())
            && !"S_MOBILE".equalsIgnoreCase(order.getSource())
            && !"YJYD".equalsIgnoreCase(order.getSource())
            && !"VDCN".equalsIgnoreCase(order.getSource())
            && !"SCHD".equalsIgnoreCase(order.getSource())
            && !"FLW".equalsIgnoreCase(order.getSource())) {
            //判断备注是否有特殊指定关键字
            if (order.getRemark() != null && remarkKeyWord != null && remarkKeyWord.length > 0) {
                for (int i = 0; i < remarkKeyWord.length; i++) {
                    if (order.getRemark().contains(remarkKeyWord[i])) {
                        return true; //只要找到有特殊关键字就返回true
                    }
                }
            }
        }
        //判断行政区id是否包含特殊地区id
        if (order.getProvince() != null && order.getCity() != null && order.getRegion() != null) {
            //去掉了所有行政区的过滤
            //            if (specialRegionId != null && specialRegionId.length > 0) {
            //                for (int i = 0; i < specialRegionId.length; i++) {
            //                    if (order.getRegion().equals(specialRegionId[i])) {
            //                        return true; //只要找到有特殊地区id就返回true
            //                    }
            //                }
            //            }
            boolean judgeStreetOrder = OrderBizHelper.judgeStreetOrder(order.getSource(),
                order.getAddTime());
            if (judgeStreetOrder) {
                if (order.getStreet() != null) {
                    if (order.getProvince().equals(0) || order.getCity().equals(0)
                        || order.getRegion().equals(0) || order.getStreet().equals(0)) {//这种情况比较少见，所以放在for循环后面
                        return true; //如果找到地区id为0就返回true
                    }
                    Regions region = regionsDao.get(order.getStreet());
                    if (region == null || 0==region.getActiveFlag()) {
                        return true;//region不存在或者ActiveFlag＝0(前段不显示)就返回true
                    }
                }
            } else {
                if (order.getProvince().equals(0) || order.getCity().equals(0)
                    || order.getRegion().equals(0)) {//这种情况比较少见，所以放在for循环后面
                    return true; //如果找到地区id为0就返回true
                }
                Regions region = regionsDao.get(order.getRegion());
                if (region == null || 0==region.getActiveFlag()) {
                    return true;//region不存在或者ActiveFlag＝0(前段不显示)就返回true
                }
            }
        }
        return false;
    }

    /**
     * 订单处理 备注，3W开发票需要人工处理，
     * @param order
     * @return
     */
    public static boolean orderRemarkHandler3W(OrdersNew order, String[] remarkKeyWord) {
        //判断备注是否有特殊指定关键字
        if (order.getRemark() != null && remarkKeyWord != null && remarkKeyWord.length > 0) {
            for (int i = 0; i < remarkKeyWord.length; i++) {
                if (order.getRemark().contains(remarkKeyWord[i])) {
                    return true; //只要找到有特殊关键字就返回true
                }
            }
        }
        return false;
    }

    /**
     * 定金尾款订单合并尾款后，发送尾款通知前，插入HP或LES队列
     * @param order  定金尾款订单
     * @param userName
     * @param itemService
     * @return
     */
    public static boolean addToHpOrLesQueue(Integer orderProductId, String userName,
                                            String[] message, StockCenterItemService itemService,
                                            StockCommonService stockCommonService,
                                            StockCenterPurchaseGdService purchaseGdService,
                                            LesQueuesService lesQueuesService, HPQueuesService hpQueuesService,
                                            OrdersNewService ordersNewService, OrderProductsNewService orderProductsNewService,
                                            ShopOrderOperateLogsService orderOperateLogsService,
                                            ShopOrderWorkflowsService orderWorkflowsService,
                                            OrderQueueExtendService orderQueueExtendService) {
        if (orderProductId == null || orderProductId.equals(0)) {
            message[0] = "网单id为空";
            return false;
        }
        OrderProductsNew orderProduct = orderProductsNewService.get(orderProductId);
        if (orderProduct == null) {
            message[0] = "网单对象为空";
            return false;
        }
        //3W网单不传hp、les直接返回true
        if (orderProduct.getStockType() != null
            && orderProduct.getStockType().equalsIgnoreCase("3W")) {
            return true;
        }
        OrdersNew order = ordersNewService.get(orderProduct.getOrderId());
        if (order.getIsNotConfirm().equals(1)) {//无需确认的订单是不传HP或LES的
            message[0] = "无需确认的订单，不传HP或LES。";
            return false;
        }
        if (order.getOrderStatus().equals(OrderStatus.OS_CANCEL.getCode())) {
            message[0] = "订单取消关闭，不传HP或LES。";
            return false;
        }
        if (order.getOrderStatus().equals(OrderStatus.OS_COMPLETE.getCode())) {
            message[0] = "订单完成关闭，不传HP或LES。";
            return false;
        }
        if (!order.getOrderStatus().equals(OrderStatus.OS_CONFIRM.getCode())) {
            message[0] = "未确认订单，不传HP或LES。";
            return false;
        }
        userName = userName == null || userName.equals("") ? "系统" : userName;
        HPQueues hpQueues = null;
        LesQueues lesQueues = null;
        List<HPQueues> hpQueuesList = new ArrayList<HPQueues>();
        List<LesQueues> lesQueuesList = new ArrayList<LesQueues>();

        //取消的网单不添加HP,LES 'status'<>110
        if (orderProduct.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
            message[0] = "取消关闭的网单不添加HP,LES";
            return false;
        }
        if (orderProduct.getStatus().equals(OrderProductStatus.COMPLETED_CLOSE.getCode())) {
            message[0] = "完成关闭的网单不添加HP,LES";
            return false;
        }
        //测试的网单不添加HP,LES 'isTest'=0
        if (!orderProduct.getIsTest().equals(0)) {
            message[0] = "测试的网单不添加HP,LES";
            return false;
        }
        //淘宝小家电的网单不添加HP,LES 'isWmsSku'=0
        if (!orderProduct.getIsWmsSku().equals(0)) {
            message[0] = "淘宝小家电的网单不添加HP,LES";
            return false;
        }
        //虚拟商品不添加HP,LES
        ServiceResult<ProductsNew> result = itemService.getProductBySku(orderProduct.getSku());
        if (null != result && result.getSuccess() && null != result.getResult()) {
            if (result.getResult().getIsVirtual().intValue() == 1) {
                orderOperateLogsService.insert(constructOperateLog(order, orderProduct, userName,
                    "虚拟商品", "虚拟商品，不传HP或LES。", null));
                handleVirtualProduct(orderProduct, ordersNewService, orderProductsNewService, orderOperateLogsService,
                    orderWorkflowsService);
                message[0] = "虚拟商品，不传HP或LES。";
                return false;
            }
        }
        // 检查是否是基地库，如是基地库，则不添加HP,LES
        String jdkuCorderSn = StringUtil.isEmpty(orderProduct.getSplitRelateCOrderSn())
            ? orderProduct.getCOrderSn() : orderProduct.getSplitRelateCOrderSn();
        ServiceResult<PurchaseGdQueue> resultPurchaseGdQueue = purchaseGdService
            .getByOrderSn(jdkuCorderSn);
        PurchaseGdQueue purchaseGdQueue;
        if (result != null && !result.getSuccess()) {
            purchaseGdQueue = resultPurchaseGdQueue.getResult();
            if (purchaseGdQueue != null) {
                orderOperateLogsService.insert(constructOperateLog(order, orderProduct, userName,
                    "基地库直发订单", "基地库直发订单，不传HP或LES。", null));
                message[0] = "基地库直发订单，不传HP或LES。";
                return false;
            }
        }
        //海鹏发货订单不添加HP,LES
        List<OrderQueueExtend> orderQueueExtendList = orderQueueExtendService
            .getByCOrderSnAndOrderSource(orderProduct.getCOrderSn(), InvSection.CHANNEL_CODE_HAIP);
        if (orderQueueExtendList != null && orderQueueExtendList.size() > 0) {
            orderOperateLogsService.insert(constructOperateLog(order, orderProduct, userName, "海鹏发货订单",
                "海鹏发货订单，不传HP或LES。", null));
            message[0] = "海鹏发货订单，不传HP或LES。";
            return false;
        }
        Integer mysqlTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
        //根据物流模式判断插入哪个表
        if ("B2C".equalsIgnoreCase(orderProduct.getShippingMode())) {
            //加入LES队列
            if (lesQueuesService.getCountByOpId(orderProduct.getId()) > 0) {
                orderOperateLogsService.insert(constructOperateLog(order, orderProduct, userName,
                    "网单插入VOM队列", "VOM队列已经存在网单，不再插入。", null));
                return true;
            }
            lesQueues = new LesQueues();
            lesQueues.setAddTime(mysqlTime);
            lesQueues.setAction("createOrder");
            lesQueues.setCount(0);
            lesQueues.setLastMessage("");
            lesQueues.setOrderProductId(orderProduct.getId());
            lesQueues.setPushData("");
            lesQueues.setSuccess(0);
            lesQueues.setIsLock(0);
            lesQueues.setIsStop(0);
            lesQueues.setSuccessTime(0L);
            lesQueues.setLastTryTime(0L);
            lesQueuesList.add(lesQueues);

            if (!lesQueuesList.isEmpty()) {
                //保存LES队列
                lesQueuesService.insert(lesQueuesList);
            }
            //插入日志
            orderOperateLogsService
                .insert(constructOperateLog(order, orderProduct, userName, "待同步到LES",
                    "小库直接进les，写入到les同步队列，网单号:" + orderProduct.getCOrderSn() + "，网单ID:"
                                                                              + orderProduct.getId()
                                                                              + "。",
                    null));
        } else {
            //加入HP队列
            if (hpQueuesService.getCountByOpId(orderProduct.getId()) > 0) {
                orderOperateLogsService.insert(constructOperateLog(order, orderProduct, userName,
                    "网单插入HP队列", "HP队列已经存在网单，不再插入。", null));
                return true;
            }
            hpQueues = new HPQueues();
            hpQueues.setAddTime(mysqlTime);
            hpQueues.setCount(0);
            hpQueues.setLastMessage("");
            hpQueues.setOrderProductId(orderProduct.getId());
            hpQueues.setPushData("");
            hpQueues.setSuccess(0);
            hpQueues.setSuccessTime(0);
            hpQueuesList.add(hpQueues);

            if (!hpQueuesList.isEmpty()) {
                //保存HP队列
                hpQueuesService.insert(hpQueuesList);
            }
            //插入日志
            orderOperateLogsService
                .insert(constructOperateLog(order, orderProduct, userName,
                    "待同步到HP", "大库走HP，写入到HP同步队列，网单号:" + orderProduct.getCOrderSn() + "，SKU:"
                              + orderProduct.getSku() + ",数量:" + orderProduct.getNumber() + "。",
                    null));
        }
        return true;
    }

    /**
     * 发HP／LES队列，虚拟商品网单状态修改(doOrderStatus)
     * @param orderProducts
     * @param orderIdOrdersMap
     * @param ordersDao
     * @param orderProductsDao
     * @param orderOperateLogsDao
     */
    private static void handleVirtualProduct(OrderProductsNew orderProducts, OrdersNewService ordersNewService,
                                             OrderProductsNewService orderProductsNewService,
                                             ShopOrderOperateLogsService orderOperateLogsService,
                                             ShopOrderWorkflowsService orderWorkflowsService) {
        if (orderProducts == null) {
            LOGGER.error("处理发HP／LES队列时，虚拟商品网单对象为空");
            return;
        }
        List<OrderProductsNew> orderProductsList = orderProductsNewService
            .getByOrderId(orderProducts.getOrderId());
        boolean isCompleteOrder = true;
        for (OrderProductsNew orderProduct : orderProductsList) {
            if (orderProduct.getId().intValue() == orderProducts.getId().intValue()) {
                continue;
            }
            if (orderProduct.getStatus().intValue() != OrderProductsNew.STATUS_CANCEL_CLOSE.intValue()
                && orderProduct.getStatus().intValue() != OrderProductsNew.STATUS_COMPLETED_CLOSE
                    .intValue()) {
                isCompleteOrder = false;
                break;
            }
        }
        if (isCompleteOrder) {
          ordersNewService.updateOrderStatus(orderProducts.getOrderId(),OrderStatus.OS_COMPLETE.getCode());
            orderOperateLogsService
                .insert(constructOperateLog(ordersNewService.get(orderProducts.getOrderId()),
                    orderProducts, "系统", "虚拟商品订单", "虚拟商品网单关闭引起订单关闭", null));
            LOGGER.info("虚拟商品网单关闭引起订单关闭,orderId:" + orderProducts.getOrderId());
        }
        orderProductsNewService.updateStatus(orderProducts.getId(), OrderProductsNew.STATUS_COMPLETED_CLOSE);
        orderOperateLogsService.insert(constructOperateLog(ordersNewService.get(orderProducts.getOrderId()),
            orderProducts, "系统", "虚拟商品", "虚拟商品网单直接关闭", null));
        LOGGER.info("虚拟商品网单关闭,opId:" + orderProducts.getId());

        OrderWorkflows orderWorkflows = orderWorkflowsService
            .getByOrderProductId(orderProducts.getId());
        orderWorkflowsService.updateUserAcceptTime(orderWorkflows.getId(), new Date().getTime() / 1000);
    }

    /**
     * 天猫定金发货尾款合并写开票队列
     * @param orders
     * @param orderOperateLogsDao
     * @param groupOrdersDao
     * @param orderProductsDao
     * @param invoiceQueueDao
     */
    public static boolean invoiceQueueAdd(int orderProductId,
    		ShopOrderOperateLogsService orderOperateLogsService,
                                          GroupOrdersService groupOrdersService,
                                          OrderProductsNewService orderProductsNewService,
                                          InvoiceQueueService invoiceQueueService, OrdersNewService ordersNewService
    //                                        , OrderProductsAttributes orderProductsAttributes
    ) {
        //根据网单ID获取groupOrders 列表
        List<GroupOrders> groupOrdersList = groupOrdersService
            .getListByDepositOrderProductId(orderProductId);

        for (GroupOrders groupOrders : groupOrdersList) {
            int flag = 0;
            OrderProductsNew orderProduct = null;

            if (!groupOrders.getType().equals(2) && groupOrders.getFrom().equals("taobao")) {
                //根据groupOrder的 定金网单ID查询网单
                orderProduct = orderProductsNewService.get(groupOrders.getDepositOrderProductId());
                if (null != orderProduct && orderProduct.getShippingOpporunity().equals(0)) {
                    if (orderProduct.getStatus().intValue() >= 10) {//网单出库后，才插入发票队列
                        //2016-10-18 3W网单发票信息特殊处理
                        OrdersNew order = ordersNewService.get(orderProduct.getOrderId());
                        List<OrderProductsNew> orderProductsList = new ArrayList<OrderProductsNew>();
                        orderProductsList.add(orderProduct);
                        if ("3W".equalsIgnoreCase(orderProduct.getStockType())
                            && orderProduct.getMakeReceiptType().intValue() == 0) {
                            insertOperateLog(order, "系统", "添加网单到发票队列",
                                "定金发货模式，3W特殊网单人工客服没有处理，不开票！", orderProductsList,
                                orderOperateLogsService);
                        } else {
                            //  TODO 自营转单二期，去掉定金尾款限制
                            //                            if (orderProductsAttributes != null
                            //                                && orderProductsAttributes.getIsSelfSell() == 1) {
                            //                                insertOperateLog(order, "CBS系统", "添加网单到发票队列", "尾款发货模式，自营不开票",
                            //                                    orderProductsList, orderOperateLogsDao);
                            //                            } else {
                            flag = InvoiceQueueInsert(invoiceQueueService, orderProduct.getId());
                            if (flag == 0) {
                                insertOperateLog(order, "系统", "添加网单到发票队列", "定金发货模式，添加失败，已存在",
                                    orderProductsList, orderOperateLogsService);
                            } else {
                                insertOperateLog(order, "系统", "添加网单到发票队列", "定金发货模式，添加成功",
                                    orderProductsList, orderOperateLogsService);
                            }
                            //                            }
                        }
                        //                        flag = InvoiceQueueInsert(invoiceQueueDao, orderProduct.getId());
                        //
                        //                        List<OrderProducts> orderProductsList = new ArrayList<OrderProducts>();
                        //                        orderProductsList.add(orderProduct);
                        //                        Orders order = ordersDao.get(orderProduct.getOrderId());
                        //                        if (flag == 0) {
                        //                            insertOperateLog(order, "CBS系统", "添加网单到发票队列", "定金发货模式，添加失败，已存在",
                        //                                orderProductsList, orderOperateLogsDao);
                        //                        } else {
                        //                            insertOperateLog(order, "CBS系统", "添加网单到发票队列", "定金发货模式，添加成功",
                        //                                orderProductsList, orderOperateLogsDao);
                        //                        }
                    } else {
                        List<OrderProductsNew> orderProductsList = new ArrayList<OrderProductsNew>();
                        orderProductsList.add(orderProduct);
                        OrdersNew order = ordersNewService.get(orderProduct.getOrderId());
                        insertOperateLog(order, "系统", "添加网单到发票队列",
                            "定金发货模式，添加失败，网单已收尾款，但未出库，不插入开票队列", orderProductsList,
                            orderOperateLogsService);
                    }
                } else if (null != orderProduct && orderProduct.getShippingOpporunity().equals(2)) {//尾款发货模式，并且已付尾款 shippingOpporunity = 2。等于1时代表尾款发货模式未付尾款
                    if (orderProduct.getStatus().intValue() >= 10) {//网单出库后，才插入发票队列
                        //2016-10-18 3W网单发票信息特殊处理
                        OrdersNew order = ordersNewService.get(orderProduct.getOrderId());
                        List<OrderProductsNew> orderProductsList = new ArrayList<OrderProductsNew>();
                        orderProductsList.add(orderProduct);
                        if ("3W".equalsIgnoreCase(orderProduct.getStockType())
                            && orderProduct.getMakeReceiptType().intValue() == 0) {
                            insertOperateLog(order, "系统", "添加网单到发票队列",
                                "尾款发货模式，3W特殊网单人工客服没有处理，不开票！", orderProductsList,
                                orderOperateLogsService);
                        } else {
                            //  TODO 自营转单二期，去掉定金尾款限制
                            //                            if (orderProductsAttributes != null
                            //                                && orderProductsAttributes.getIsSelfSell() == 1) {
                            //                                insertOperateLog(order, "CBS系统", "添加网单到发票队列", "尾款发货模式，自营不开票",
                            //                                    orderProductsList, orderOperateLogsDao);
                            //                            } else {
                            flag = InvoiceQueueInsert(invoiceQueueService, orderProduct.getId());
                            if (flag == 0) {
                                insertOperateLog(order, "系统", "添加网单到发票队列", "尾款发货模式，添加失败，已存在",
                                    orderProductsList, orderOperateLogsService);
                            } else {
                                insertOperateLog(order, "系统", "添加网单到发票队列", "尾款发货模式，添加成功",
                                    orderProductsList, orderOperateLogsService);
                            }
                            //                            }
                        }
                        //                        flag = InvoiceQueueInsert(invoiceQueueDao, orderProduct.getId());
                        //
                        //                        List<OrderProducts> orderProductsList = new ArrayList<OrderProducts>();
                        //                        orderProductsList.add(orderProduct);
                        //                        Orders order = ordersDao.get(orderProduct.getOrderId());
                        //                        if (flag == 0) {
                        //                            insertOperateLog(order, "CBS系统", "添加网单到发票队列", "尾款发货模式，添加失败，已存在",
                        //                                orderProductsList, orderOperateLogsDao);
                        //                        } else {
                        //                            insertOperateLog(order, "CBS系统", "添加网单到发票队列", "尾款发货模式，添加成功",
                        //                                orderProductsList, orderOperateLogsDao);
                        //                        }
                    } else {
                        List<OrderProductsNew> orderProductsList = new ArrayList<OrderProductsNew>();
                        orderProductsList.add(orderProduct);
                        OrdersNew order = ordersNewService.get(orderProduct.getOrderId());
                        insertOperateLog(order, "系统", "添加网单到发票队列",
                            "尾款发货模式，添加失败，网单已收尾款，但未出库，不插入开票队列", orderProductsList,
                            orderOperateLogsService);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 插入开票队列
     * @param invoiceQueueDao
     * @param orderProductId 网单ID
     * @return
     */
    public static int InvoiceQueueInsert(InvoiceQueueService invoiceQueueService, int orderProductId) {
        //添加发票队列，开提单时开发票-----后面修改成出库时开发票，有两部分出库代码修改，采销联动出库和VOM出库，等VOM开提单上线在修改这个上线
        InvoiceQueue invoiceQueue = new InvoiceQueue();
        invoiceQueue.setOrderProductId(orderProductId);
        invoiceQueue.setSuccess(0);
        invoiceQueue.setAddTime(new Date());
        invoiceQueue.setProcessTime(new Date());
        List<InvoiceQueue> inv_queue_list = invoiceQueueService.getByOrderProductId(orderProductId);
        if (inv_queue_list == null || inv_queue_list.size() == 0) {//如果已经存在就不在插入
            return invoiceQueueService.insert(invoiceQueue);
        }
        return 0;
    }

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

    /**
     * 判断是否是指定订单来源且下单时间为2017-3-1之后的订单
     * @param source 订单来源
     * @param orderTime 下单时间
     * @return boolean true四级  false三级
     */
    public static boolean judgeStreetOrder(String source, Integer orderTime) {
        if (StringUtil.isEmpty(source))
            return false;
        if ("MSTORE".equalsIgnoreCase(source)) {
            return true;
        }
        //2017-3-26 05:10:00
        //TODO 下单时间上线时记得修改
        if (orderTime.intValue() >= 1490476200) {
            if (ORDER_STREET_SOURCE.contains(source)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

}
