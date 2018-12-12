package com.haier.order.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.haier.common.util.StringUtil;
import com.haier.distribute.data.service.OrderProductsService;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.Orders;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrdersNewService;
import com.haier.shop.service.OrdersService;
import com.haier.shop.service.ShopOrderOperateLogsService;

@Service
public class AutoCodConfirmModel {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(AutoCodConfirmModel.class);

    @Autowired
    private OrdersService                    ordersDao;
    @Autowired
    private OrderProductsNewService          orderProductsDao;
    @Autowired
    private ShopOrderOperateLogsService      orderOperateLogsDao;
    
   // DataSourceTransactionManager     transactionManager;

    public void autoCodConfirm() {
        long startTime = System.currentTimeMillis();
        //查询货到付款未审核的订单getNotAutoConfirmOrders
        List<Orders> orderList = ordersDao.getCodNotConfirmOrders();
        if (null == orderList || orderList.isEmpty()) {
            log.info("货到付款未审核的订单,没有需要处理的数据");
            return;
        }
        autoConfirm(orderList);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        log.info("货到付款未审核条数:" + orderList.size() + ",总共执行时间:" + time + "毫秒,平均每条"
                 + time / orderList.size() + "毫秒");
    }

    private void autoConfirm(List<Orders> orderList) {
    	//DataSourceTransactionManager     transactionManager=new DataSourceTransactionManager();
        for (Orders order : orderList) {
            List<OrderProductsNew> orderProductsList = orderProductsDao.getByOrderId(order.getId());
            if (null == orderProductsList || orderProductsList.isEmpty()) {
                log.error("货到付款自动审核订单,订单ID:" + order.getId() + ",找不到对应网单");
                continue;
            }

            if (StringUtil.isEmpty(order.getMobile(), true)
                && StringUtil.isEmpty(order.getPhone(), true)) {
                insertOperateLog(order, orderProductsList, "手机和座机号码都为空");
                log.error("货到付款自动审核订单,订单ID:" + order.getId() + ",手机和座机号码都为空");
                continue;
            }

            if (!isPhone(order.getPhone()) && !isMobile(order.getMobile())) {
                insertOperateLog(order, orderProductsList, "手机或座机号码不正确");
                log.error("货到付款自动审核订单,订单ID:" + order.getId() + ",手机或座机号码不正确");
                continue;
            }

          //  DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    
        //    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

           // TransactionStatus status = transactionManager.getTransaction(def);
            try {
                //更新COD审核状态
                ordersDao.updateCodConfirmState(order.getId());
                //插入操作日志
                insertOperateLog(order, orderProductsList, "自动审核成功");
                //提交事务
               // transactionManager.commit(status);
            } catch (Exception e) {
                //回滚事务
               // transactionManager.rollback(status);
                log.error("货到付款自动审核订单,订单ID:" + order.getId() + ",发生异常：", e);
            }
        }
    }

    private void insertOperateLog(Orders order, List<OrderProductsNew> orderProductsList,
                                  String remark) {
        OrderOperateLogs log = null;
        List<OrderOperateLogs> orderOperateLogsList = new ArrayList<OrderOperateLogs>();
        for (OrderProductsNew orderProducts : orderProductsList) {
            orderOperateLogsList
                .add(this.constructOperateLog(order, orderProducts, "系统", "货到付款自动审核", remark, log));
        }
        if (!orderOperateLogsList.isEmpty()) {
            orderOperateLogsDao.batchInsert(orderOperateLogsList);
        }
    }

    private OrderOperateLogs constructOperateLog(Orders orders, OrderProductsNew orderProducts,
                                                 String operator, String changeLog, String remark,
                                                 OrderOperateLogs log) {
        log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setNetPointId(orderProducts.getNetPointId());
        log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
        log.setOrderId(orderProducts.getOrderId());
        log.setOrderProductId(orderProducts.getId());
        log.setPaymentStatus(null == orders ? 0 : orders.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        log.setSiteId(1);
        log.setStatus(orderProducts.getStatus());
        return log;
    }

    private boolean isPhone(String phone) {
        if (StringUtil.isEmpty(phone, true)) {
            return false;
        }
        phone = phone.trim();
        if ("-".equals(phone) || "--".equals(phone)) {
            return false;
        }
        return Pattern.compile("^(0[0-9]{2,3}\\-)?([0-9]{7,8})+(\\-[0-9]{2,4})?$").matcher(phone)
            .matches();
    }

    private boolean isMobile(String mobile) {
        if (StringUtil.isEmpty(mobile, true)) {
            return false;
        }
        mobile = mobile.trim();
        if ("-".equals(mobile) || "--".equals(mobile)) {
            return false;
        }
        return Pattern.compile("^1[\\d]{10}$").matcher(mobile).matches();
    }

}