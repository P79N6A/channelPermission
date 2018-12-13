package com.haier.shop.dao.shopread;


import com.haier.shop.model.CostPools;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
public interface CostPoolsReadDao {

    List<CostPools> getCouponCostPoolByChannelAndChanYeAndYearMonth(@Param("channel") Integer channel,
                                                                    @Param("chanYe") String chanYe,
                                                                    @Param("yearMonth") Integer yearMonth);

    List<Map<String, Object>> getProductCate();

    List<Map<String, Object>> getBrand();

    List<CostPools> findCostPoolsByPage(Map<String, Object> params);

    int getTotal();

    //按照产品 渠道 费用类型 时间 查询
    List<CostPools> findCostPoolsByChannel(
            @Param("chanYe") String chanYe, @Param("channel") Integer channel, @Param("type") String type, @Param("yearMonth") String yearMonth);

    //按照产品 渠道 费用类型 时间 查询总数
    Integer findCostPoolsCount(
            @Param("chanYe") String chanYe, @Param("channel") Integer channel, @Param("type") String type, @Param("yearMonth") String yearMonth);

    CostPools findCostPoolsByChannelAndBatchAsc(@Param("chanYe") String chanye,
                                                @Param("channel") Integer channelValue,
                                                @Param("type") String type,
                                                @Param("yearMonth") String yearMonth,
                                                @Param("slPrice") BigDecimal slPrice);

    List<Map<String,Object>> getExportCostPoolsList(Map<String, Object> paramMap);

    CostPools findCostPoolsByBatch(CostPools costPools);

    CostPools getId(@Param("id") String id);

    CostPools findcostPoolsByTYBC(CostPools costPools);

    CostPools getReverseCostPools(String chanye, Integer channel, Integer type, String yearMonth);
}