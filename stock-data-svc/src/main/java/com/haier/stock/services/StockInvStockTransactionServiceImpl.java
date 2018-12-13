package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvStockTransactionDao;
import com.haier.stock.model.InvStockTransaction;
import com.haier.stock.service.StockInvStockTransactionService;

@Service
public class StockInvStockTransactionServiceImpl implements StockInvStockTransactionService{
	@Autowired
	private InvStockTransactionDao invStockTransactionDao;
	@Override
	public Integer insert(InvStockTransaction stockTransaction) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.insert(stockTransaction);
	}

	@Override
	public Integer insertAll(List<InvStockTransaction> transactions) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.insertAll(transactions);
	}

	@Override
	public List<InvStockTransaction> getNotProcessBusiness() {
		// TODO Auto-generated method stub
		return invStockTransactionDao.getNotProcessBusiness();
	}

	@Override
	public Integer updateBusinessProcessStatus(Integer id, Integer businessProcessStatus) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.updateBusinessProcessStatus(id, businessProcessStatus);
	}

	@Override
	public Integer updateProcessStatus(Integer id, Integer processStatus, Integer oldProcessStatus, Integer isDelay,
			String message) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.updateProcessStatus(id, processStatus, oldProcessStatus, isDelay, message);
	}

	@Override
	public Integer updateToDelay(Integer id, Integer isDelay, String message) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.updateToDelay(id, isDelay, message);
	}

	@Override
	public List<InvStockTransaction> getByProcessStatus(Integer status) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.getByProcessStatus(status);
	}

	@Override
	public List<InvStockTransaction> getByIsDelay(Integer isDelay) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.getByIsDelay(isDelay);
	}

	@Override
	public List<InvStockTransaction> getByRefNo(String refNo) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.getByRefNo(refNo);
	}

	@Override
	public List<InvStockTransaction> queryData(Integer id, Integer num) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.queryData(id, num);
	}

	@Override
	public int insertSelective(InvStockTransaction record) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.insertSelective(record);
	}

	@Override
	public List<Map<String, Object>> queryList(Map params) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.queryList(params);
	}

	@Override
	public int getListRowCnt(Map params) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.getListRowCnt(params);
	}

	@Override
	public List<Map<String, Object>> queryStockTransList(Map params) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.queryStockTransList(params);
	}

	@Override
	public int getStockTransListRowCnt(Map params) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.getStockTransListRowCnt(params);
	}

	@Override
	public List<Map<String, Object>> query(Map params) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.query(params);
	}

	@Override
	public int getRowCnt(Map params) {
		// TODO Auto-generated method stub
		return invStockTransactionDao.getRowCnt(params);
	}

}
