package com.haier.stock.service;



import com.haier.stock.model.InvStock2Channel;

import java.util.List;

public interface InvStock2ChannelService {

    InvStock2Channel get(String sku, String secCode,
                         String channel);

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
