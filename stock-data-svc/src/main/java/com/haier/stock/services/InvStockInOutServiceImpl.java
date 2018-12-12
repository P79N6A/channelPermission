package com.haier.stock.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvStockInOutDao;
import com.haier.stock.model.InvStockInOut;
import com.haier.stock.service.InvStockInOutService;
@Service
public class InvStockInOutServiceImpl implements InvStockInOutService{
	@Autowired
	private InvStockInOutDao invStockInOutDao;
	@Override
	public Integer getOutStock(String sku, String sCode, String channel, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return invStockInOutDao.getOutStock(sku, sCode, channel, fromDate, toDate);
	}

	@Override
	public Integer getInStock(String sku, String sCode, String channel, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return invStockInOutDao.getInStock(sku, sCode, channel, fromDate, toDate);
	}

	@Override
	public Integer insert(InvStockInOut invStockInOut) {
		// TODO Auto-generated method stub
		return invStockInOutDao.insert(invStockInOut);
	}

	@Override
	public Integer getCountByBillNo(String billNo) {
		// TODO Auto-generated method stub
		return invStockInOutDao.getCountByBillNo(billNo);
	}

	@Override
	public List<InvStockInOut> getReference(String sku, String secCode, Date from, Date to) {
		// TODO Auto-generated method stub
		return invStockInOutDao.getReference(sku, secCode, from, to);
	}

	@Override
	public List<InvStockInOut> getByAgeStatus(Integer ageStatus) {
		// TODO Auto-generated method stub
		return invStockInOutDao.getByAgeStatus(ageStatus);
	}

	@Override
	public Integer updateAgeStatus(Integer id, Integer status, Integer oldStatus) {
		// TODO Auto-generated method stub
		return invStockInOutDao.updateAgeStatus(id, status, oldStatus);
	}

}
