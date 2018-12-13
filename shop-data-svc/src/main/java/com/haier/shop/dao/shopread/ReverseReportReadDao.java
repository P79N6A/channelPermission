package com.haier.shop.dao.shopread;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface ReverseReportReadDao {

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
     * @return
     */
    Integer getOntimeRateReverseDetailCount(Map<String, Object> paramMap);

    /**
     * 获取及时率报表逆向网单明细
     * @return cOrderSn网单号数组,pager分页信息
     */
    List<Map<String, Object>> getOntimeRateReverseDetail(Map<String, Object> paramMap);
}
