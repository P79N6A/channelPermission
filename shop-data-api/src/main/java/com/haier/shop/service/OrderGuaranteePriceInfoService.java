package com.haier.shop.service;

import com.haier.shop.model.OrderGuaranteePriceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 李超 on 2018/1/10.
 */
public interface OrderGuaranteePriceInfoService {

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

    /**
     * 插入保本价信息<br/>
     * 渠道、物流模式，品类id、物料编码相同则不插入
     * @param orderGuaranteePriceInfo
     * @return
     */
    public int insert(OrderGuaranteePriceInfo orderGuaranteePriceInfo);

    public int insertNew(OrderGuaranteePriceInfo orderGuaranteePriceInfo);

    /**
     * 由渠道、物流模式，品类id、物料编码更新保本价信息
     * @param orderGuaranteePriceInfo
     * @return
     */
    public int update(OrderGuaranteePriceInfo orderGuaranteePriceInfo);

}
