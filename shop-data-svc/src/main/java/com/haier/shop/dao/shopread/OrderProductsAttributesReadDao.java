package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderProductsAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderProductsAttributesReadDao {

    OrderProductsAttributes get(Integer id);

    //根据网单id获取网单扩展属性表
    OrderProductsAttributes getByOrderProductId(@Param("orderProductId") Integer orderProductId);

    //根据订单id和天猫子网单号获取网单扩展信息
    List<OrderProductsAttributes> getByOrderIdAndOid(@Param("orderId") Integer orderId,
                                                     @Param("oid") String oid);

    List<OrderProductsAttributes> getByCondition(Map<String, Object> queryMap);

    //根据网单号判断是否为自营单据
    Integer isSelfSellByOrderSn(@Param("orderSn") String orderSn);

    List<OrderProductsAttributes> getByOrderOrderId(@Param("orderId") Integer orderId);
}