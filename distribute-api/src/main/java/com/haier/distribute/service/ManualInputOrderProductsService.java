package com.haier.distribute.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haier.distribute.data.model.TOrdersManualProducts;

public interface ManualInputOrderProductsService {
	List<Map<String, Object>> getManualInputOrderProductsbyOrderId(int orderId);
	int inserManualInputOrderProducts(TOrdersManualProducts tOrdersManualProducts);
	int updeteManualInputOrderProducts(TOrdersManualProducts tOrdersManualProducts);
	int deleteManualInputOrderProducts(Integer id);
	Integer getManualInputOrderProductsbyOrderIdCount(Integer id);
	
	JSONObject findProduct(int id);	

}
