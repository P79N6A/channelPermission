package com.haier.stock.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvStockChannelDao;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.service.StockInvStockChannelService;
@Service
public class StockInvStockChannelServiceImpl implements StockInvStockChannelService{
	@Autowired
	private InvStockChannelDao invStockChannelDao;
	 /**
     * 获取全部渠道信息
     * @return	
     */
	@Override
	public List<InvStockChannel> getAll() {
		// TODO Auto-generated method stub
		return invStockChannelDao.getAll();
	}

	@Override
	public InvStockChannel getByCode(String channel_code) {
		// TODO Auto-generated method stub
		return invStockChannelDao.getByCode(channel_code);
	}

}
