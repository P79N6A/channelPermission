package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderPriceCostPoolUseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderPriceCostPoolUseInfoWriteDao {

    int batchInsert(List<OrderPriceCostPoolUseInfo> orderPriceCostPoolUseInfo);

    int insert(OrderPriceCostPoolUseInfo orderPriceCostPoolUseInfo);

}