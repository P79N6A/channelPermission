package com.haier.stock.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.stock.dao.stock.StockReservedConfigDao;
import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.service.StockReservedConfigService;
@Service
public class StockReservedConfigServiceImpl implements StockReservedConfigService{
	@Autowired
	private StockReservedConfigDao stockReservedConfigDao;
	@Override
	public List<InvReservedConfig> getReservedConfig(InvReservedConfig config, PagerInfo pager) {
		// TODO Auto-generated method stub
		return stockReservedConfigDao.getReservedConfig(config, pager);
	}

	@Override
	public int getRowCnt() {
		// TODO Auto-generated method stub
		return stockReservedConfigDao.getRowCnt();
	}

}
