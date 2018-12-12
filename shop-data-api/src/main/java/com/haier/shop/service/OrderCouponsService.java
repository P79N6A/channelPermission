package com.haier.shop.service;

import com.haier.shop.model.OrderCoupons;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by 李超 on 2018/1/10.
 */
public interface OrderCouponsService {
    OrderCoupons getOrderCouponsByCOrderSn(@Param("cOrderSn") String cOrderSn);

    Map<String, Object> getLpTotalFeeNum(@Param("orderId") Integer orderId);
}
