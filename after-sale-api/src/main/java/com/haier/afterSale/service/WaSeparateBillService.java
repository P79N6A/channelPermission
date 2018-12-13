package com.haier.afterSale.service;

import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProducts;

import java.math.BigDecimal;
import java.util.List;

/**
 * com.haier.afterSale.service
 *
 * @Auther: 付振兴
 * @Date: 2018/7/26 14:38
 * @Version: V1.0
 * @Description: WA拆单
 */
public interface WaSeparateBillService {

    List<Invoices> findInvoices(String orderProductId);

    OrderProducts queryGetId(String orderProductId);

    void insertOrderProducts(OrderProducts orderProducts,String userName);

    int updateNum(Long newNum, BigDecimal productAmount,String orderProductId);

    void insertLog(String userName, OrderProducts orderProducts);
}
