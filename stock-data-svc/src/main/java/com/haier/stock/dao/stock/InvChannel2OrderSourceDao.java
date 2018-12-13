package com.haier.stock.dao.stock;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvChannel2OrderSource;


public interface InvChannel2OrderSourceDao {
    public InvChannel2OrderSource getBySource(String source);

    /**
     * 取得所有订单渠道数据
     * @return
     */
    public List<InvChannel2OrderSource> getAllOrder2ChannelSource();

    public InvChannel2OrderSource getSapChannelCodeAndCustomerCode(@Param("ordeSourceCode") String ordeSourceCode);

    /**
     * 获取渠道代码与名称对应列表
     */
    List<Map<String, Object>> getChannelNames();

    /**
     * 根据渠道获取stock库订单来源下拉框
     * @param channelCode
     * @return
     */
    List<Map<String, String>> getInvChannel2OrderSource(@Param("channelCode") String channelCode);

    /**
     * 查询订单来源信息列表总数
     * @param name
     * @return Integer
     */
    Integer queryInvChannel2OrderSourceListCount(@Param("name") String name);

    /**
     *  查询订单来源信息列表List
     * @param name
     * @param start
     * @param size
     * @return List<InvChannel2OrderSource>
     */
    List<InvChannel2OrderSource> queryInvChannel2OrderSourceList(@Param("name") String name,
        @Param("start") Integer start,
        @Param("size") Integer size,
        @Param("id") Integer id);

    /**
     * 插入订单来源配置
     * @param tInvChannel2OrderSource
     * @return
     */
    void insert(InvChannel2OrderSource tInvChannel2OrderSource);

    /**
     * 更新订单来源配置
     * @param tInvChannel2OrderSource
     * @return
     */
    void update(InvChannel2OrderSource tInvChannel2OrderSource);

    /**
     * 获取stock库渠道下拉框
     * @return
     */
    List<Map<String, String>> getInvStockChannel();

}
