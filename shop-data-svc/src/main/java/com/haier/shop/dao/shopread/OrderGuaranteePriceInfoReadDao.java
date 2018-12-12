package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderGuaranteePriceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接收保本价信息 dao层
 * @Version: 1.0
 */
@Mapper
public interface OrderGuaranteePriceInfoReadDao {

    /**
     * 由id查询保本价信息
     * @param id
     * @return
     */
    public OrderGuaranteePriceInfo getById(@Param("id") Integer id);

    /**
     * 渠道、物流模式、物料编码查询保本价信息
     * @param channelCode 渠道
     * @param shippingMode 物料模式
     * @param sku 物料编码
     * @return
     */
    public OrderGuaranteePriceInfo getConditions(@Param("channelCode") String channelCode,
                                                 @Param("shippingMode") String shippingMode,
                                                 @Param("sku") String sku);

    public List<OrderGuaranteePriceInfo> getNewConditions(@Param("channelCode") String channelCode,
                                                          @Param("shippingMode") String shippingMode,
                                                          @Param("sku") String sku);

    public OrderGuaranteePriceInfo getOrderGuaranteePrice(OrderGuaranteePriceInfo orderGuaranteePriceInfo);

}