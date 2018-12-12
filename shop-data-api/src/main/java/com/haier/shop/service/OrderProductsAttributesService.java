package com.haier.shop.service;

import java.util.List;
import java.util.Map;


import com.haier.shop.model.OrderProductsAttributes;

public interface OrderProductsAttributesService {
	OrderProductsAttributes get(Integer id);

    int insert(OrderProductsAttributes orderProductsAttributes);

    int update(OrderProductsAttributes orderProductsAttributes);

    //根据网单id获取网单扩展属性表
    OrderProductsAttributes getByOrderProductId( Integer orderProductId);

    //根据订单id和天猫子网单号获取网单扩展信息
    List<OrderProductsAttributes> getByOrderIdAndOid(Integer orderId,String oid);

    List<OrderProductsAttributes> getByCondition(Map<String, Object> queryMap);

    //根据网单号判断是否为自营单据
    Integer isSelfSellByOrderSn(String orderSn);

    List<OrderProductsAttributes> getByOrderOrderId(Integer orderId);
}
