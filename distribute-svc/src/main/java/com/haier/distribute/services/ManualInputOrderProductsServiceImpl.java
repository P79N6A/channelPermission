package com.haier.distribute.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.distribute.data.model.TOrdersManualProducts;
import com.haier.distribute.data.service.ManualInputOrderProductService;
import com.haier.distribute.service.ManualInputOrderProductsService;
@Service
public class ManualInputOrderProductsServiceImpl implements ManualInputOrderProductsService{
	
	@Autowired
	ManualInputOrderProductService  manualInputOrderProductService;

	@Override
	public int inserManualInputOrderProducts(TOrdersManualProducts tOrdersManualProducts) {
		return manualInputOrderProductService.inserManualInputOrderProducts(tOrdersManualProducts);
	}

	@Override
	public int updeteManualInputOrderProducts(TOrdersManualProducts tOrdersManualProducts) {
		return manualInputOrderProductService.updeteManualInputOrderProducts(tOrdersManualProducts);
	}

	@Override
	public int deleteManualInputOrderProducts(Integer id) {
		return manualInputOrderProductService.deleteManualInputOrderProducts(id);
	}

	@Override
	public List<Map<String, Object>> getManualInputOrderProductsbyOrderId(int orderId) {
		return manualInputOrderProductService.getManualInputOrderProductsbyOrderId(orderId);
	}

	@Override
	public Integer getManualInputOrderProductsbyOrderIdCount(Integer id) {
		// TODO Auto-generated method stub
		return manualInputOrderProductService.getManualInputOrderProductsbyOrderIdCount(id);
	}
	@Override
	public JSONObject findProduct(int id) {
		TOrdersManualProducts tOrdersManualProducts = manualInputOrderProductService.findProduct(id);		
		return (JSONObject) JSONObject.toJSON(tOrdersManualProducts);
	}

}
