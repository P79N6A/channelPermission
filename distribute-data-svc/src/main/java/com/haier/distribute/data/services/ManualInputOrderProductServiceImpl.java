package com.haier.distribute.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.ManualInputOrderProductsDao;
import com.haier.distribute.data.model.TOrdersManualProducts;
import com.haier.distribute.data.service.ManualInputOrderProductService;

@Service
public class ManualInputOrderProductServiceImpl implements ManualInputOrderProductService{
	
	@Autowired
	ManualInputOrderProductsDao manualInputOrderProductsDao;

	@Override
	public int inserManualInputOrderProducts(TOrdersManualProducts tOrdersManualProducts) {
		return manualInputOrderProductsDao.inserManualInputOrderProducts(tOrdersManualProducts);
	}

	@Override
	public int updeteManualInputOrderProducts(TOrdersManualProducts tOrdersManualProducts) {
		return manualInputOrderProductsDao.updeteManualInputOrderProducts(tOrdersManualProducts);
	}

	@Override
	public int deleteManualInputOrderProducts(Integer id) {
		return manualInputOrderProductsDao.deleteManualInputOrderProducts(id);
	}

	@Override
	public List<Map<String, Object>> getManualInputOrderProductsbyOrderId(int orderId) {
		return manualInputOrderProductsDao.getManualInputOrderProductsbyOrderId(orderId);
	}

	@Override
	public Integer getManualInputOrderProductsbyOrderIdCount(Integer id) {
		// TODO Auto-generated method stub
		return manualInputOrderProductsDao.getManualInputOrderProductsbyOrderIdCount(id);
	}

	@Override
	public TOrdersManualProducts findProduct(int id) {
		// TODO Auto-generated method stub
		return manualInputOrderProductsDao.findProduct(id);
	}
	

	
	

}
