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
}
