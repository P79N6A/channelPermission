package com.haier.shop.service;


import com.haier.shop.model.CostPools;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.Orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CostPoolsService {
    List<CostPools> getCouponCostPoolByChannelAndChanYeAndYearMonth(Integer channel, String chanYe, Integer yearMonth);

    int updateCouponCostPool(Integer id, BigDecimal couponAmount);

    List<Map<String, Object>> getProductCate();

    List<Map<String, Object>> getBrand();

    List<CostPools> findCostPoolsByPage(Map<String, Object> params);

    int getTotal();

    List<CostPools> findCostPoolsByChannel(String industrys, Integer channelValue, String type, String yearMonth);

    Integer findCostPoolsCount(String industrys, Integer channelValue, String type, String yearMonth);

    int updateBanlacnAmount(Integer id, BigDecimal slPrices);

    CostPools findCostPoolsByChannelAndBatchAsc(String chanye, Integer channelValue, String s, String yearMonth,BigDecimal slPrice);

    List<Map<String,Object>> getExportCostPoolsList(Map<String, Object> paramMap);

    int addCostPool(CostPools costPools);

    CostPools findCostPoolsById(String id);

    Integer updateCostPools(CostPools costPools);

    Integer deleteCostPools(String id);

    CostPools findcostPoolsByTYBC(CostPools costPools);

    int reverseUpdateBanlacnAmount(Orders orders, OrderProducts orderProducts, String userName);
}