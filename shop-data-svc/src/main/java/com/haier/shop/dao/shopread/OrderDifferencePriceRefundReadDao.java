package com.haier.shop.dao.shopread;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface OrderDifferencePriceRefundReadDao {

    /**
     * 根据网单号获取有效申请差价单数量
     * @param cOrderSn
     * @return
     */
    int getValidCountByCorderSn(@Param("cOrderSn") String cOrderSn);

}