package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface CostPoolsWriteDao {

    int updateCouponCostPool(@Param("id") Integer id,
                             @Param("couponAmount") BigDecimal couponAmount);
}