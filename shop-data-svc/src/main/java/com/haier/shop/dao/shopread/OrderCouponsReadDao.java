package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderCoupons;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
@Mapper
public interface OrderCouponsReadDao {

    OrderCoupons getOrderCouponsByCOrderSn(@Param("cOrderSn") String cOrderSn);

    Map<String, Object> getLpTotalFeeNum(@Param("orderId") Integer orderId);

}