package com.haier.shop.dao.shopread;


import com.haier.shop.model.CostPools;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
public interface CostPoolsReadDao {

    List<CostPools> getCouponCostPoolByChannelAndChanYeAndYearMonth(@Param("channel") Integer channel,
                                                                    @Param("chanYe") String chanYe,
                                                                    @Param("yearMonth") Integer yearMonth);

    List<Map<String, Object>> getProductCate();

    List<Map<String, Object>> getBrand();

}