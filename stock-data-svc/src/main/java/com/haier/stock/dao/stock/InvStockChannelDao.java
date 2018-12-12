package com.haier.stock.dao.stock;



import java.util.List;

import com.haier.stock.model.InvStockChannel;

public interface InvStockChannelDao {
    /**
     * 获取全部渠道信息
     * @return	
     */
    List<InvStockChannel> getAll();

    InvStockChannel getByCode(String channel_code);

}
