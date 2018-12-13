package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.CnT2PurchaseStockDao;
import com.haier.purchase.data.model.CnT2PurchaseStock;
import com.haier.purchase.data.service.CnT2PurchaseStockService;

/**
 * Created by yangchangliang on 2018/1/19.
 */
@Service
public class CnT2PurchaseStockServiceImpl implements CnT2PurchaseStockService{
	
	@Autowired
	CnT2PurchaseStockDao cnT2PurchaseStockDao;

	public void addPurchaseStock(CnT2PurchaseStock cnT2PurchaseStock){
		cnT2PurchaseStockDao.addPurchaseStock(cnT2PurchaseStock);
	}

	public void updateCnT2PurchaseStock(CnT2PurchaseStock cnT2PurchaseStock){
		cnT2PurchaseStockDao.updateCnT2PurchaseStock(cnT2PurchaseStock);
	}
	
	public void updateCnT2PurchaseStockById(CnT2PurchaseStock cnT2PurchaseStock){
		cnT2PurchaseStockDao.updateCnT2PurchaseStockById(cnT2PurchaseStock);
	}

	@Override
	public int getCnT2PurchaseStockCNT(Map<String, Object> params) {
		return cnT2PurchaseStockDao.getCnT2PurchaseStockCNT(params);
	}

	@Override
	public List<CnT2PurchaseStock> getPurchaseStockList(Map<String, Object> params) {
		return cnT2PurchaseStockDao.getPurchaseStockList(params);
	}

	public List<CnT2PurchaseStock> queryCnT2PurchaseStock(
			Map<String, Object> map){
		return cnT2PurchaseStockDao.queryCnT2PurchaseStock(map);
	}

}
