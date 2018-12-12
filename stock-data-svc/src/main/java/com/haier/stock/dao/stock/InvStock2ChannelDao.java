package com.haier.stock.dao.stock;

import com.haier.stock.model.InvStock2Channel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvStock2ChannelDao {

    InvStock2Channel get(@Param("sku") String sku, @Param("sec_code") String secCode,
                         @Param("channel_code") String channel);

    Integer insert(InvStock2Channel stock2Channel);

    Integer update(InvStock2Channel stock2Channel);
    
    /**
     * 取得所有物料库存数据
     * @return
     */
    List<InvStock2Channel> getInvStockChannelLst();
    
    /**
     * 取得所有包含套机的sku(子件SKU)列表
     * @return
     */
    List<InvStock2Channel> getInvStockBomLst();
}
