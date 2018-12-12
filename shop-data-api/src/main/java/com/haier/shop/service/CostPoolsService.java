package com.haier.shop.service;


import com.haier.shop.model.CostPools;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CostPoolsService {
    List<CostPools> getCouponCostPoolByChannelAndChanYeAndYearMonth(Integer channel, String chanYe, Integer yearMonth);

    int updateCouponCostPool(Integer id, BigDecimal couponAmount);

    List<Map<String, Object>> getProductCate();

    List<Map<String, Object>> getBrand();

}