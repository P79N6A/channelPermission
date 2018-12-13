package com.haier.shop.dao.shopread;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderWorkflowsRunTimeReadDao {
    /**
     * 获取开单及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateLes(Map<String, Object> paramMap);

    /**
     * 获取到网点及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateNetpoint(Map<String, Object> paramMap);

    /**
     * 获取到用户及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateUser(Map<String, Object> paramMap);

    /**
     * 获取妥投及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateOwf(Map<String, Object> paramMap);

    /**
     * 获取妥投及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateOwfSecond(Map<String, Object> paramMap);

    /**
     * 获取多层级及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateTransport(Map<String, Object> paramMap);

    /**
     * 获取COD及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateCod(Map<String, Object> paramMap);

    /**
     * 获取及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRate(Map<String, Object> paramMap);

    /**
     * 获取及时率报表数据
     * @return 网单列表集合
     * @author 秦凯
     */
    List<Map<String, Object>> getO2oStoreOntimeRateList(Map<String, Object> paramMap);

    /**
     * 根据网单号获取及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateByOrderSn(Map<String, Object> paramMap);

    /**
     * 获取区县
     * @param parentId 上级id
     * @return 区县列表
     */
    List<Map<String, String>> getRegions(@Param("parentId") Integer parentId);

    /**
     * 获取库位
     * @return 库位列表
     */
    List<Map<String, String>> getStorages();

    /**
     * 获取及时率网单明细总数
     * @param cOrderSn网单号数组,pager分页信息
     * @return
     */
    Integer getOntimeRateDetailCount(Map<String, Object> paramMap);

    /**
     * 获取及时率网单明细列表
     * @param cOrderSn网单号数组,pager分页信息
     * @return
     */
    List<Map<String, Object>> getOntimeRateDetail(Map<String, Object> paramMap);

    /**
     * 根据区id查询配送时效
     * @param id 区id
     * @return
     */
    List<Map<String, Object>> getShippingTimeByRegionId(Integer id);

    /**
     * 获取audit审核及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseAudit(Map<String, Object> paramMap);

    /**
     * 获取hp质检及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseHp(Map<String, Object> paramMap);

    /**
     * 获取les入库及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseLes(Map<String, Object> paramMap);

    /**
     * 获取invoice冲红及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseInvoice(Map<String, Object> paramMap);

    /**
     * 获取orderclose关闭及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseOrderclose(Map<String, Object> paramMap);

    /**
     * 获取refund退款及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseRefund(Map<String, Object> paramMap);

    /**
     * 获取blp及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseBlp(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverse(Map<String, Object> paramMap);

    /**
     * 根据网单号获取及时率报表逆向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseByOrderSn(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向网单明细总数
     * @param
     * @return
     */
    Integer getOntimeRateReverseDetailCount(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向网单明细
     * @return cOrderSn网单号数组,pager分页信息
     */
    List<Map<String, Object>> getOntimeRateReverseDetail(Map<String, Object> paramMap);

    /**
     * 获取显差报表正向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getDisplayOutTimeList(@Param("startDate") Integer startDate,
                                                    @Param("endDate") Integer endDate);

    /**
     * 获取显差报表逆向向数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getDisplayOutTimeReverseList(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向数据(22库)
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverse22StoreHouse(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向数据(二次质检)
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseReCheckQuality(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向数据(转箱)
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseTransmitBox(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向数据(转库)
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseTransmitStock(Map<String, Object> paramMap);

    /**
     * 获取存性变更数据
     * @return 网单列表集合
     */
    Map<String, Object> get1041StoreExistInfo(@Param("orderRepairId") Integer orderRepairId);

    /**
     * 根据网单号获取及时率报表逆向数据(22库相关)
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRateReverseByOrderSn22Store(Map<String, Object> paramMap);

    /**
     * 获取存性变更数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getGaiyueInfo(Map<String, Object> paramMap);

    /**
     * 获取区域表
     */
    List<Map<String, Object>> getDistances();

    /**
     * 获取库位
     * @return 库位列表
     */
    List<Map<String, Object>> getProductCate();

}
