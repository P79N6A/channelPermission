package com.haier.stock.service;

import java.util.List;

import com.haier.stock.model.InvStockChannel;

public interface StockInvStockChannelService {
	 /**
     * 获取全部渠道信息
     * @return	
     */
   public  List<InvStockChannel> getAll();

   public InvStockChannel getByCode(String channel_code);
}
