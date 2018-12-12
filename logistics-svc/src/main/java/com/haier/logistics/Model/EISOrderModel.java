package com.haier.logistics.Model;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.LesStatusItem;
import com.haier.eis.model.OrdShippingStatusQueue;
import com.haier.eis.service.EisLesStatusItemService;
import com.haier.eis.service.EisShippingStatusQueueService;
import com.haier.logistics.service.OrderService;
import com.haier.shop.model.IcmsOutStore;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderShippedQueue;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.model.ShippingStatusSyncLogs;
import com.haier.shop.model.YiHaoDianOrderStateSyncLogs;
import com.haier.shop.service.ExternalOrdersService;
import com.haier.shop.service.IcmsOutStoreService;
import com.haier.shop.service.ShippingStatusSyncLogsService;
import com.haier.shop.service.YiHaoDianOrderStateSyncLogsService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class EISOrderModel {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(EISOrderModel.class);
    @Autowired
    private EisLesStatusItemService eisLesStatusItemService;
    @Autowired
    private OrderService orderService;
/*
    @Autowired
    private DataSourceTransactionManager transactionManagerShop;
*/
    @Autowired
    private IcmsOutStoreService icmsOutStoreDao;
    @Autowired
    private EisShippingStatusQueueService eisShippingStatusQueueService;
    @Autowired
    private ExternalOrdersService externalOrdersDao;
    @Autowired
        private YiHaoDianOrderStateSyncLogsService yiHaoDianOrderStateSyncLogsDao;
    @Autowired
    private ShippingStatusSyncLogsService shippingStatusSyncLogsDao;
    /**
     * LES出入库后，同步到CBS
     * @return
     */
    //2
    public ServiceResult<Boolean> processAfterLesShipped(){
        long tsEntering = System.currentTimeMillis();

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        //获取未处理的队列
        /*这是查询数据库获取数据信息*/
        //3
        List<LesStatusItem> statusList = eisLesStatusItemService.getNotProcessList();
        //判断数据库数据是否为空
        if (statusList == null || statusList.size() == 0) {
            long tsExiting = System.currentTimeMillis();
            log.info("LES出入库后，同步到CBS：没有待处理的记录。耗时:" + (tsExiting - tsEntering) + "ms。");
            result.setSuccess(true);
            result.setMessage("LES出入库后，同步到CBS：没有待处理的记录");
            return result;
        }
        //计算网单编号列表(去重)
        List<String> csnList = new ArrayList<String>();
        for (LesStatusItem item : statusList) {
            if (!csnList.contains(item.getCorderSn())) {
                csnList.add(item.getCorderSn());
            }
        }
        //获取网单列表
        List<OrderProductsNew> opList = null;
        try {
            //4
            opList = this.getOrderLines(csnList);
        } catch (Exception ex) {
            long tsExiting = System.currentTimeMillis();
            log.error("LES出入库后，同步到CBS：耗时:" + (tsExiting - tsEntering) + "ms。批量获取网单时，发生未知异常", ex);
            result.setSuccess(false);
            result.setMessage("LES出入库后，同步到CBS：批量获取网单时，发生未知异常(" + ex.getMessage() + ")");
            return result;
        }
        //计算订单id列表
        List<Integer> oidList = new ArrayList<Integer>();
        if (opList != null && opList.size() > 0) {
            for (OrderProductsNew op : opList) {
                if (!oidList.contains(op.getOrderId())) {
                    oidList.add(op.getOrderId());
                }
            }
        }
        //获取订单列表
        List<OrdersNew> oList = null;
        try {
            oList = this.getOrders(oidList);
        } catch (Exception ex) {
            long tsExiting = System.currentTimeMillis();
            log.error("LES出入库后，同步到CBS：耗时:" + (tsExiting - tsEntering) + "ms。批量获取订单时，发生未知异常", ex);
            result.setSuccess(false);
            result.setMessage("LES出入库后，同步到CBS：批量获取订单时，发生未知异常(" + ex.getMessage() + ")");
            return result;
        }

        //逐条处理
        int thCount = 0, thErrorCount = 0, jdCount = 0, jdErrorCount = 0, chCount = 0, chErrorCount = 0;
        for (LesStatusItem item : statusList) {
            String cOrderSn = item.getCorderSn();
            String tempCOrderSn = cOrderSn.toUpperCase();
            //退换货入库
            if (tempCOrderSn.indexOf("TH") == 14 || tempCOrderSn.indexOf("RB") == 14
                    || tempCOrderSn.indexOf("IB") == 14 || tempCOrderSn.indexOf("CX") == 14) {//第14位为特殊字母
                try {
                    ServiceResult<Boolean> myResult = orderService.repairOrderIn(cOrderSn,
                            item.getOutping());
                    if (myResult.getSuccess()) {
                        item.setStatus(LesStatusItem.STATUS_DONE);
                        item.setReleaseStatus(LesStatusItem.RELEASE_STATUS_DONE);
                    } else {
                        String error = myResult.getMessage();
                        log.info("LES出入库后，同步到CBS：：处理退换货入库(cOrderSn:" + cOrderSn + "Outping:"
                                + item.getOutping() + ")时，发生未知异常：" + myResult.getMessage());
                        if (error != null && error.length() > 500) {
                            error = error.substring(0, 500);
                        }
                        item.setErrorMessage(error);
                    }
                    eisLesStatusItemService.updateAfterProcessed(item);
                } catch (Exception ex) {
                    log.error(
                            "LES出入库后，同步到CBS：处理退换货入库(cOrderSn:" + cOrderSn + "Outping:"
                                    + item.getOutping() + ")时，发生未知异常：", ex);
                    thErrorCount++;
                }

                thCount++;
                continue;
            }
            //TODO 出库状态队列
            //京东网单出库
            if (tempCOrderSn.substring(tempCOrderSn.length() - 1).equals("J")) {//最后一位为特殊字母
                Boolean isSuccess = this.deliveryIcmsOrder(item, 0);
                if (!isSuccess) {
                    jdErrorCount++;
                }
                jdCount++;

                //加入发货状态队列
                this.addOrderShippingStatusQueue(item, null, null, "JINGDONG");

                continue;
            }
            //易迅网单出库
            if (tempCOrderSn.substring(tempCOrderSn.length() - 1).equals("Y")) {//最后一位为特殊字母
                Boolean isSuccess = this.deliveryIcmsOrder(item, 1);
                if (!isSuccess) {
                    jdErrorCount++;
                }
                jdCount++;

                //加入发货状态队列
                this.addOrderShippingStatusQueue(item, null, null, "YIXUN");

                continue;
            }

            //网单出库
            chCount++;
            OrderProductsNew orderProduct = this.getOrderProductsInList(opList, cOrderSn);
            if (orderProduct == null) {
                log.warn("LES出入库后，同步到CBS：处理网单出库时，出现异常(根据网单编号(" + cOrderSn + ")找不到对应的网单信息)");
                item.setStatus(LesStatusItem.STATUS_ERROR);
                item.setReleaseStatus(LesStatusItem.RELEASE_STATUS_DONE);
                item.setErrorMessage("找不到对应的网单信息");
                eisLesStatusItemService.updateAfterProcessed(item);

                chErrorCount++;
                continue;
            }
            OrdersNew order = this.getOrderInList(oList, orderProduct.getOrderId());
            if (order == null) {
                log.error("LES出入库后，同步到CBS：处理网单出库时，出现异常(根据订单id(" + orderProduct.getOrderId()
                        + ")找不到对应的订单信息)");
                item.setStatus(LesStatusItem.STATUS_ERROR);
                item.setReleaseStatus(LesStatusItem.RELEASE_STATUS_DONE);
                item.setErrorMessage("找不到对应的订单信息");
                eisLesStatusItemService.updateAfterProcessed(item);

                chErrorCount++;
                continue;
            }

            //加入发货状态队列
            this.addOrderShippingStatusQueue(item, orderProduct, order, "");

            try {
                Date lesShippingTime = new Date(item.getUpdTime() * 1000);
                //                ServiceResult<Boolean> myResult = orderService.processAfterLesShipped(order,
                //                    orderProduct, item.getItype(), item.getOutping(), lesShippingTime,
                //                    item.getInvoiceNumber(), item.getInvoiceName());
                //【耿顺波】转运的网单，同一个网单物流给我们传了两次运单号，一个是61的  一个是29开头的。29开头的是物流的财务记账凭证号。
                //2017-8-15 XinM 处理耿顺波提出的上述问题
                //转运的网单，从LES抓取的转运出库item.getOutping是物流的财务记账凭证号，item.getInvoiceNumber是物流运单号。
                //VOM回传的出入库状态处理中，都是按照物流运单号[61的]处理的，为了避免冲突，此处与VOM修改成一致
                ServiceResult<Boolean> myResult = orderService.processAfterLesShipped(order,
                        orderProduct, item.getItype(), item.getInvoiceNumber(), lesShippingTime,
                        item.getInvoiceNumber(), item.getInvoiceName());
                if (myResult.getResult()) {
                    item.setStatus(LesStatusItem.STATUS_DONE);
                } else {
                    item.setErrorMessage(myResult.getMessage());
                }
                eisLesStatusItemService.updateAfterProcessed(item);
                externalOrdersDao.updateAtferShipped(Integer.valueOf(order.getId()));//由于分销平台还需要使用这个标志判断是否已出库，故临时先用用
            } catch (Exception ex) {
                log.error(
                        "LES出入库后，同步到CBS：处理退换货入库(cOrderSn:" + cOrderSn + ", Outping:"
                                + item.getOutping() + ")时，发生未知异常：", ex);

                chErrorCount++;
            }
        }

        long tsExiting = System.currentTimeMillis();
        log.info("LES出库后，同步到CBS：总计" + statusList.size() + "条，其中退货记录" + thCount + "(error:"
                + thErrorCount + ")条，京东出库记录" + jdCount + "(error:" + jdErrorCount + ")条，普通网单出库"
                + chCount + "(error:" + chErrorCount + ")条。耗时:" + (tsExiting - tsEntering) + "ms。");
        return result;
    }

    /*================================================================================================================*/
    private List<OrderProductsNew> getOrderLines(List<String> snList) throws Exception{
        if (snList == null || snList.size() == 0) {
            return null;
        }

        Integer pageSize = 1000;

        //如果网单编号数量不超过pageSize，则可以直接调用
        if (snList.size() <= pageSize) {
            //5
            ServiceResult<List<OrderProductsNew>> result = orderService.getOrderLineBySnList(snList);
            if (result == null || !result.getSuccess()) {
                throw new Exception("获取网单列表时发生异常，请检查订单服务日志");
            } else {
                return result.getResult();
            }
        }

        List<OrderProductsNew> orderLines = new ArrayList<OrderProductsNew>();
        //先获取pageSize的整数
        List<String> tempList = new ArrayList<String>();
        for (String sn : snList) {
            if (tempList.size() == pageSize) {//当数目达到pageSize时，获取网单队列
                ServiceResult<List<OrderProductsNew>> result = orderService
                        .getOrderLineBySnList(tempList);
                if (result == null || !result.getSuccess()) {
                    throw new Exception("获取网单列表时发生异常，请检查订单服务日志");
                }
                //组装新队列
                List<OrderProductsNew> tempOPList = result.getResult();
                if (tempOPList != null && tempOPList.size() > 0) {
                    for (OrderProductsNew op : tempOPList) {
                        orderLines.add(op);
                    }
                }
                //重置临时队列
                tempList = new ArrayList<String>();
            }
            tempList.add(sn);
        }
        //再获取pageSize的余数
        if (tempList.size() > 0) {
            ServiceResult<List<OrderProductsNew>> result = orderService.getOrderLineBySnList(tempList);
            if (result == null || !result.getSuccess()) {
                throw new Exception("获取网单列表时发生异常，请检查订单服务日志");
            }
            //组装新队列
            List<OrderProductsNew> tempOPList = result.getResult();
            if (tempOPList != null && tempOPList.size() > 0) {
                for (OrderProductsNew op : tempOPList) {
                    orderLines.add(op);
                }
            }
        }
        //返回最终结果
        return orderLines;
    }
    /*================================================================================================================*/
    private List<OrdersNew> getOrders(List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 0) {
            return null;
        }

        Integer pageSize = 1000;

        //如果网单编号数量不超过pageSize，则可以直接调用
        if (ids.size() <= pageSize) {
            ServiceResult<List<OrdersNew>> result = orderService.getOrderByIds(ids);
            if (result == null || !result.getSuccess()) {
                throw new Exception("批量获取订单列表时发生异常，请检查订单服务日志");
            } else {
                return result.getResult();
            }
        }

        List<OrdersNew> orders = new ArrayList<OrdersNew>();
        //先获取pageSize的整数
        List<Integer> tempList = new ArrayList<Integer>();
        for (Integer id : ids) {
            if (tempList.size() == pageSize) {//当数目达到pageSize时，获取网单队列
                ServiceResult<List<OrdersNew>> result = orderService.getOrderByIds(tempList);
                if (result == null || !result.getSuccess()) {
                    throw new Exception("获取网单列表时发生异常，请检查订单服务日志");
                }
                //组装新队列
                List<OrdersNew> tempOrderList = result.getResult();
                if (tempOrderList != null && tempOrderList.size() > 0) {
                    for (OrdersNew order : tempOrderList) {
                        orders.add(order);
                    }
                }
                //重置临时队列
                tempList = new ArrayList<Integer>();
            }
            tempList.add(id);
        }
        //再获取pageSize的余数
        if (tempList.size() > 0) {
            ServiceResult<List<OrdersNew>> result = orderService.getOrderByIds(tempList);
            if (result == null || !result.getSuccess()) {
                throw new Exception("获取网单列表时发生异常，请检查订单服务日志");
            }
            //组装新队列
            List<OrdersNew> tempOrderList = result.getResult();
            if (tempOrderList != null && tempOrderList.size() > 0) {
                for (OrdersNew order : tempOrderList) {
                    orders.add(order);
                }
            }
        }
        //返回最终结果
        return orders;
    }
    /*================================================*/
    /**
     * 出库后，处理ICMS订单
     * @param lesStatusItem
     * @return
     */
    private Boolean deliveryIcmsOrder(LesStatusItem lesStatusItem, Integer orderType){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /*TransactionStatus status = transactionManagerShop.getTransaction(def);*/
        try {
            //添加到京东网单队列
            IcmsOutStore ios = new IcmsOutStore();
            ios.setCordersn(lesStatusItem.getCorderSn());
            ios.setOutping(lesStatusItem.getOutping());
            Date upDate = new Date(lesStatusItem.getUpdTime() * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ios.setUpddate(sdf.format(upDate));
            sdf = new SimpleDateFormat("HH:mm:ss");
            ios.setUpdtime(sdf.format(upDate));
            ios.setInvoicename(lesStatusItem.getInvoiceName());
            ios.setInvoicenumber(lesStatusItem.getInvoiceNumber());
            ios.setOrderType(orderType);
            icmsOutStoreDao.insert(ios);
            //更新队列
            lesStatusItem.setStatus(LesStatusItem.STATUS_DONE);
            lesStatusItem.setReleaseStatus(LesStatusItem.RELEASE_STATUS_DONE);
            eisLesStatusItemService.updateAfterProcessed(lesStatusItem);
            //提交事务
            /*transactionManagerShop.commit(status);*/
            //返回执行成功transactionManager
            return true;
        } catch (Exception ex) {
            //回滚事务
            /*transactionManagerShop.rollback(status);*/
            //记录日志
            log.error("出库后，处理京东订单()时，出现未知异常:", ex);
            return false;
        }
    }
    /*=========================*/
    /**
     * 新增发货状态队列，用于同步到第三方
     * @param item
     * @param op
     * @param order
     * @param source
     */
    private void addOrderShippingStatusQueue(LesStatusItem item, OrderProductsNew op, OrdersNew order,
                                             String source) {
        Integer orderId = 0;
        Integer orderProductId = 0;
        if (order != null) {
            orderId = Integer.valueOf(order.getId());
            if (StringUtils.isNullOrEmpty(source)) {
                source = order.getSource();
            }
        }
        if (op != null) {
            orderProductId = op.getId();
        }

        OrdShippingStatusQueue queue = new OrdShippingStatusQueue();
        queue.setCount(0);
        queue.setExpressName(item.getInvoiceName());
        queue.setExpressNumber(item.getInvoiceNumber());
        queue.setLesShipTime(new Date(item.getUpdTime() * 1000));
        queue.setOrderId(orderId);
        queue.setOrderProductId(orderProductId);
        queue.setcOrderSn(item.getCorderSn());
        queue.setOrderSource(source);
        queue.setOutping(item.getOutping());
        queue.setStatus(OrdShippingStatusQueue.STATUS_UN_PROCESSED);

        try {
            eisShippingStatusQueueService.insert(queue);
        } catch (Exception ex) {
            log.error("新增发货状态同步队列时发生未知异常：" + ex.getMessage());
        }
    }
    //---------------------------------------- private method ------------------------
    private OrderProductsNew getOrderProductsInList(List<OrderProductsNew> opList, String cOrderSn) {
        if (opList == null || opList.size() == 0) {
            return null;
        }
        for (OrderProductsNew op : opList) {
            if (op.getCOrderSn().equalsIgnoreCase(cOrderSn)) {
                return op;
            }
        }
        return null;
    }
    private OrderProductsNew getOrderProductsInList(List<OrderProductsNew> opList, Integer id) {
        if (opList == null || opList.size() == 0) {
            return null;
        }
        for (OrderProductsNew op : opList) {
            if (op.getId().equals(id)) {
                return op;
            }
        }
        return null;
    }
    /*========================================================*/
    private OrdersNew getOrderInList(List<OrdersNew> oList, Integer orderId) {
        if (oList == null || oList.size() == 0) {
            return null;
        }
        for (OrdersNew order : oList) {
            if (order.getId().equals(orderId.toString())) {
                return order;
            }
        }
        return null;
    }
    /**
     * 处理出库队列，通知第三方渠道出库信息
     * @return
     */
    public ServiceResult<Boolean> processShippedQueue(){
        long tsEntering = System.currentTimeMillis();

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        //获取待处理的出库队列
        List<OrderShippedQueue> list = null;
        try {
            list = orderService.getOrderShippedQueueForProcess(2000).getResult();//yes
        } catch (Exception ex) {
            long tsExiting = System.currentTimeMillis();
            log.error("CBS通知第三方出库信息：获取待处理的出库队列时(耗时:" + (tsExiting - tsEntering) + "ms)，出现异常：", ex);
            result.setResult(false);
            return result;
        }
        if (list == null || list.size() == 0) {
            long tsExiting = System.currentTimeMillis();
            log.info("CBS通知第三方出库信息：没有需要处理的出库队列记录.耗时:" + (tsExiting - tsEntering) + "ms");
            result.setResult(true);
            return result;
        }
        //计算网单id列表
        List<Integer> opIds = new ArrayList<Integer>();
        for (OrderShippedQueue q : list) {
            opIds.add(q.getOrderLineId());
        }
        //获取网单列表
        List<OrderProductsNew> opList = null;
        try {
            opList = this.getOrderProducts(opIds);//yes
        } catch (Exception ex) {
            long tsExiting = System.currentTimeMillis();
            log.error("CBS通知第三方出库信息：耗时:" + (tsExiting - tsEntering) + "ms，批量获取网单时，发生未知异常", ex);
            result.setResult(false);
            result.setMessage("CBS通知第三方出库信息：批量获取网单时，发生未知异常(" + ex.getMessage() + ")");
            return result;
        }
        //计算订单id列表
        List<Integer> oidList = new ArrayList<Integer>();
        if (opList != null && opList.size() > 0) {
            for (OrderProductsNew op : opList) {
                if (!oidList.contains(op.getOrderId())) {
                    oidList.add(op.getOrderId());
                }
            }
        }
        //获取订单列表
        List<OrdersNew> oList = null;
        try {
            oList = this.getOrders(oidList);
        } catch (Exception ex) {
            long tsExiting = System.currentTimeMillis();
            log.error("CBS通知第三方出库信息：耗时:" + (tsExiting - tsEntering) + "ms，批量获取订单时，发生未知异常", ex);
            result.setResult(false);
            result.setMessage("CBS通知第三方出库信息：批量获取订单时，发生未知异常(" + ex.getMessage() + ")");
            return result;
        }
        //逐条处理
        int ccbCount = 0, orderErrorCount = 0, yhdCount = 0, otherCount = 0, errorCount = 0, noErrorCount = 0;
        for (OrderShippedQueue q : list) {
            OrderProductsNew op = this.getOrderProductsInList(opList, q.getOrderLineId());
            if (op == null) {
                log.error("CBS通知第三方出库信息：发生严重错误，找不到orderproductid(" + q.getOrderLineId() + ")对应的网单");
                orderService.deleteOrderShippedQueue(q.getId());

                orderErrorCount++;
                continue;
            }
            OrdersNew order = this.getOrderInList(oList, op.getOrderId());
            if (order == null) {
                log.error("CBS通知第三方出库信息：发生严重错误，找不到orderId(" + op.getOrderId() + ")对应的网单");
                orderService.deleteOrderShippedQueue(q.getId());

                orderErrorCount++;
                continue;
            }
            try {
                //出库凭证
                String outPing = op.getOutping();
                if (StringUtil.isEmpty(outPing)) {
                    orderService.deleteOrderShippedQueue(q.getId());

                    noErrorCount++;
                    continue;
                }
                //订单来源
                String source = order.getSource();
                //建行
                if ("CCBSC".equalsIgnoreCase(source)) {
                    //表中设计orderid为唯一属性，先判断是否存在
                    ShippingStatusSyncLogs log = shippingStatusSyncLogsDao.getByOrderId(Integer.parseInt(order
                            .getId()));
                    if (log != null) {
                        ccbCount++;
                        continue;
                    }
                    //插入建行队列
                    log = new ShippingStatusSyncLogs();
                    log.setOrderId(Integer.parseInt(order.getId()));
                    log.setOrderProductId(op.getId());
                    log.setShippingNum(outPing);
                    log.setCustomerCode("CCB0001");
                    log.setCustomerName("建行龙卡商城");
                    shippingStatusSyncLogsDao.insert(log);
                    //删除CBS队列
                    orderService.deleteOrderShippedQueue(q.getId());

                    ccbCount++;
                    continue;
                }
                //一号店
                if ("YHD".equalsIgnoreCase(source)) {
                    //插入一号店队列
                    YiHaoDianOrderStateSyncLogs log = new YiHaoDianOrderStateSyncLogs();
                    log.setOrderSn(order.getSourceOrderSn());
                    log.setOutping(outPing);
                    yiHaoDianOrderStateSyncLogsDao.insert(log);
                    //删除CBS队列
                    orderService.deleteOrderShippedQueue(q.getId());

                    yhdCount++;
                    continue;
                }
                //其它订单来源，不用处理
                orderService.deleteOrderShippedQueue(q.getId());
                otherCount++;
            } catch (Exception ex) {
                log.error("CBS通知第三方出库信息：发生未知异常 - ", ex);
                errorCount++;
            }
        }
        long tsExiting = System.currentTimeMillis();
        log.info("CBS通知第三方出库信息：总计" + list.size() + "条记录。其中建行" + ccbCount + "条，一号店" + yhdCount
                + "条，其它渠道" + otherCount++ + "条，订单信息错误" + orderErrorCount + "条，出库凭证为空"
                + noErrorCount + "条，未知异常" + errorCount + "条。耗时:" + (tsExiting - tsEntering)
                + "ms。");
        //处理成功
        result.setResult(true);
        return result;
    }
    private List<OrderProductsNew> getOrderProducts(List<Integer> ids) throws Exception{
        if (ids == null || ids.size() == 0) {
            return null;
        }

        Integer pageSize = 1000;

        //如果网单编号数量不超过pageSize，则可以直接调用
        if (ids.size() <= pageSize) {
            ServiceResult<List<OrderProductsNew>> result = orderService.getOrderProductsByIds(ids);
            if (result == null || !result.getSuccess()) {
                throw new Exception("批量获取网单列表时发生异常，请检查订单服务日志");
            } else {
                return result.getResult();
            }
        }

        List<OrderProductsNew> objects = new ArrayList<OrderProductsNew>();
        //先获取pageSize的整数
        List<Integer> tempList = new ArrayList<Integer>();
        for (Integer id : ids) {
            if (tempList.size() == pageSize) {//当数目达到pageSize时，获取队列
                ServiceResult<List<OrderProductsNew>> result = orderService
                        .getOrderProductsByIds(tempList);
                if (result == null || !result.getSuccess()) {
                    throw new Exception("获取网单列表时发生异常，请检查订单服务日志");
                }
                //组装新队列
                List<OrderProductsNew> tempObjectList = result.getResult();
                if (tempObjectList != null && tempObjectList.size() > 0) {
                    for (OrderProductsNew order : tempObjectList) {
                        objects.add(order);
                    }
                }
                //重置临时队列
                tempList = new ArrayList<Integer>();
            }
            tempList.add(id);
        }
        //再获取pageSize的余数
        if (tempList.size() > 0) {
            ServiceResult<List<OrderProductsNew>> result = orderService
                    .getOrderProductsByIds(tempList);
            if (result == null || !result.getSuccess()) {
                throw new Exception("获取网单列表时发生异常，请检查订单服务日志");
            }
            //组装新队列
            List<OrderProductsNew> tempObjectList = result.getResult();
            if (tempObjectList != null && tempObjectList.size() > 0) {
                for (OrderProductsNew order : tempObjectList) {
                    objects.add(order);
                }
            }
        }
        //返回最终结果
        return objects;
    }
}
