package com.haier.distribute.data.service;

import java.util.List;
import java.util.Map;

import com.haier.distribute.data.model.TOrdersManualProducts;

public interface ManualInputOrderProductService {
	List<Map<String, Object>> getManualInputOrderProductsbyOrderId(int orderId) ;
	int inserManualInputOrderProducts(TOrdersManualProducts tOrdersManualProducts);
	int updeteManualInputOrderProducts(TOrdersManualProducts tOrdersManualProducts);
	int deleteManualInputOrderProducts(Integer id);
	Integer getManualInputOrderProductsbyOrderIdCount(Integer id);
	TOrdersManualProducts findProduct(int id);

}
