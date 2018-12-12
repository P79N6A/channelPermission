package com.haier.shop.dao.shopread;


import com.haier.shop.model.OrderPriceCostPoolUseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface OrderPriceCostPoolUseInfoReadDao {

    OrderPriceCostPoolUseInfo getByCorderSn(@Param("cOrderSn") String cOrderSn);

}