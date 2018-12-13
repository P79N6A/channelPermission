package com.haier.shop.dao.settleCenter;

import java.util.List;
import java.util.Map;

import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.OrderWorkflowRegion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderWorkflowsDao {
    /**
     * 获取及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRate(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverse(Map<String, Object> paramMap);

    /**
     * 查询时间范围内退换货单id集合
     * @param paramMap
     * @return
     */
    List<Long> getOntimeRateReverseOrsIds(Map<String, Object> paramMap);

    /**
     * 获取及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateByOrderProductIds(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseByOrsIds(Map<String, Object> paramMap);

    /**
     * 根据区id查询配送时效
     * @param id 区id
     * @return
     */
    List<Map<String, Object>> getShippingTimeByRegionId(Long id);

    /**
     * 查询区县对应的区域中心工贸
     * @return
     */
    List<OrderWorkflowRegion> getOwfRegion();

    /**
     * 获取库位
     * @return 库位列表
     */
    List<Map<String, String>> getStorages();

    /**
     * 获取大家电多层级列表
     * @return
     */
    List<BigStoragesRela> getBigStoragesRelaList();

    /**
     * 查询所有工贸对人员关系
     */
    List<Map<String, String>> getTradePersonCfg();

    /**
     * 获取存性变更数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getGaiyueInfo(@Param("orderProductId") Long orderProductId);

    /**
     * 获取区域表配送距离
     */
    String getDistances(@Param("regionId") Long regionId);
}
