package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.CostPools;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface CostPoolsWriteDao {

    int updateCouponCostPool(@Param("id") Integer id,
                             @Param("couponAmount") BigDecimal couponAmount);

    int updateBanlacnAmount(@Param("id") Integer id,
                            @Param("slPrices") BigDecimal slPrices);

    int addCostPool(CostPools costPools);

    Integer updateCostPools(CostPools costPools);

    Integer deleteCostPools(@Param("id") String id);

    int reverseUpdateBanlacnAmount(@Param("id") Integer id, @Param("slPrices") BigDecimal amount);
}