package com.haier.stock.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvStockBatchDao;
import com.haier.stock.model.InvStockBatch;
import com.haier.stock.service.StockInvStockBatchService;
@Service
public class StockInvStockBatchServiceImpl implements StockInvStockBatchService{
	@Autowired
	private InvStockBatchDao invStockBatchDao;
	 /**
     * 获取最先的可出库记录
     * @param sku
     * @param secCode
     * @return
     */
	@Override
	public InvStockBatch getFrontAvailable(String sku, String secCode) {
		// TODO Auto-generated method stub
		return invStockBatchDao.getFrontAvailable(sku, secCode);
	}
	 /**
     * 获取最大的批次号
     * @return
     */
	@Override
	public String getLastBatchNum() {
		// TODO Auto-generated method stub
		return invStockBatchDao.getLastBatchNum();
	}

	@Override
	public List<InvStockBatch> queryInvStockBatch(Integer lastBatchId, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return invStockBatchDao.queryInvStockBatch(lastBatchId, startIndex, pageSize);
	}

	@Override
	public List<InvStockBatch> queryInvReleaseStock(Date now, int startIndex, int pageSize) {
		List<InvStockBatch> list=invStockBatchDao.queryInvReleaseStock(now, startIndex, pageSize);
		System.out.println(list.size());
		return list;
	}

	@Override
	public Integer getSumStockBySku(String sku, String sec_code, Integer id) {
		// TODO Auto-generated method stub
		return invStockBatchDao.getSumStockBySku(sku, sec_code, id);
	}

	@Override
	public Integer insert(InvStockBatch stockBatch) {
		// TODO Auto-generated method stub
		return invStockBatchDao.insert(stockBatch);
	}

	@Override
	public Integer updateQty(InvStockBatch stockBatch) {
		// TODO Auto-generated method stub
		return invStockBatchDao.updateQty(stockBatch);
	}

	@Override
	public Integer update(InvStockBatch stockBatch) {
		// TODO Auto-generated method stub
		return invStockBatchDao.update(stockBatch);
	}

}
