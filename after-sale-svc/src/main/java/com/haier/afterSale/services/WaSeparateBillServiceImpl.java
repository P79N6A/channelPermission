package com.haier.afterSale.services;

import com.haier.afterSale.service.WaSeparateBillService;
import com.haier.afterSale.util.OrderSnUtil;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.Orders;
import com.haier.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * com.haier.afterSale.service
 *
 * @Auther: 付振兴
 * @Date: 2018/7/26 14:40
 * @Version: V1.0
 * @Description:WA拆单
 */
@Service
public class WaSeparateBillServiceImpl implements WaSeparateBillService {

    @Autowired
    private InvoicesService invoicesService;
    @Autowired
    private ShopOperationAreaService shopOperationAreaService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private OrdersService   ordersService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;
    @Override
    public List<Invoices> findInvoices(String orderProductId) {
        return invoicesService.getByOrderProductId(Integer.parseInt(orderProductId));
    }

    @Override
    public OrderProducts queryGetId(String orderProductId) {
        return shopOperationAreaService.queryGetId(orderProductId);
    }

    @Override
    public void insertOrderProducts(OrderProducts orderProducts,String userName) {
        orderProducts.setId(null);
        orderProducts.setNumber((long) 1);
        orderProducts.setProductAmount(orderProducts.getPrice());
        int orderProductId = orderProductsNewService.insert(orderProducts);
        String cOrderSn = OrderSnUtil.getCOrderSn(orderProductId);
        orderProducts.setId(orderProductId);
        orderProducts.setCOrderSn(cOrderSn);
        orderProductsNewService.updateCOrderSn(orderProducts);//更新网单号
        //记录网单操作日志
        Orders order = ordersService.get(orderProducts.getOrderId());
        OrderOperateLogs orderOperateLog = new OrderOperateLogs();
        orderOperateLog.setSiteId(1);
        orderOperateLog.setOrderId(orderProducts.getOrderId());
        orderOperateLog.setOrderProductId(orderProductId);
        orderOperateLog.setNetPointId(orderProducts.getNetPointId());
        orderOperateLog.setOperator(userName);
        orderOperateLog.setChangeLog("WA拆单");
        orderOperateLog.setRemark("生成新网单,网单号为:"+cOrderSn);
        orderOperateLog.setStatus(orderProducts.getStatus());
        orderOperateLog.setPaymentStatus(order.getPaymentStatus());
        shopOrderOperateLogsService.insert(orderOperateLog);
    }

    @Override
    public int updateNum(Long newNum, BigDecimal productAmount,String orderProductId) {
       return  orderProductsNewService.updateNum(newNum,productAmount,orderProductId);
    }

    @Override
    public void insertLog(String userName, OrderProducts orderProducts) {
        //记录网单操作日志
        Orders order = ordersService.get(orderProducts.getOrderId());
        OrderOperateLogs orderOperateLog = new OrderOperateLogs();
        orderOperateLog.setSiteId(1);
        orderOperateLog.setOrderId(orderProducts.getOrderId());
        orderOperateLog.setOrderProductId(orderProducts.getId());
        orderOperateLog.setNetPointId(orderProducts.getNetPointId());
        orderOperateLog.setOperator(userName);
        orderOperateLog.setChangeLog("WA拆单");
        orderOperateLog.setRemark("拆单,原网单号为:"+orderProducts.getcOrderSn());
        orderOperateLog.setStatus(orderProducts.getStatus());
        orderOperateLog.setPaymentStatus(order.getPaymentStatus());
        shopOrderOperateLogsService.insert(orderOperateLog);
    }
}
