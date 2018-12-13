package com.haier.eop.data.services;

import java.util.List;

import com.haier.eop.data.dao.TmStockSyncLogDao;
import com.haier.eop.data.model.TmStockSyncLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eop.data.dao.StockSyncLogDao;
import com.haier.eop.data.dao.StocksynCstorageDao;
import com.haier.eop.data.dao.StocksyncproductsDao;
import com.haier.eop.data.model.StockSyncLog;
import com.haier.eop.data.model.StocksynCstorage;
import com.haier.eop.data.model.Stocksyncproducts;
import com.haier.eop.data.service.StockSyncLogService;
import com.haier.eop.data.service.StocksynCstorageService;
import com.haier.eop.data.service.StocksyncproductsService;
@Service
public class StockSyncLogServiceImpl implements StockSyncLogService {
	@Autowired
	private StockSyncLogDao stockSyncLogDao;
	@Autowired
	private TmStockSyncLogDao tmStockSyncLogDao;


@Override
public List<StockSyncLog> LogListf(int start, int rows, String sse, String sku,
		String sourceProductId, String sCode, String sourceStoreCode, String stockSyncResult, String addTimeStart,
		String addTimeEnd) {
	// TODO Auto-generated method stub
	return stockSyncLogDao.Listf(start,rows, sse, sku, sourceProductId, sCode, sourceStoreCode, stockSyncResult, addTimeStart, addTimeEnd);
}

@Override
public int getPagerCountS( String sse, String sku, String sourceProductId, String sCode,
		String sourceStoreCode, String stockSyncResult, String addTimeStart, String addTimeEnd) {
	// TODO Auto-generated method stub
	return stockSyncLogDao.getPagerCountS(sse, sku, sourceProductId, sCode, sourceStoreCode, stockSyncResult, addTimeStart, addTimeEnd);
}

	@Override
	public List<TmStockSyncLog> TmLogListf(int start, int rows, String sse, String sku, String sCode, String stockSyncResult, String addTimeStart, String addTimeEnd) {
		return tmStockSyncLogDao.TmListf(start,rows,sse,sku,sCode,stockSyncResult,addTimeStart,addTimeEnd);
	}

	@Override
	public int getTmPagerCountS(String sse, String sku, String sCode, String stockSyncResult, String addTimeStart, String addTimeEnd) {
		return tmStockSyncLogDao.getTmPagerCountS(sse,sku,sCode,stockSyncResult,addTimeStart,addTimeEnd);
	}


}