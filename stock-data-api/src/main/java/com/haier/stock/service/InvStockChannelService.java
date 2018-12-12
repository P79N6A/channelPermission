package com.haier.stock.service;

import com.haier.stock.model.InvStockChannel;

import java.util.List;

public interface InvStockChannelService {
    /**
     * 获取全部渠道信息
     * @return
     */
    List<InvStockChannel> getAll();

    InvStockChannel getByCode(String channel_code);
}
