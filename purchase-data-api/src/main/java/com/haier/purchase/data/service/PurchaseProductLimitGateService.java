package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.ProductLimitGate;


/**
 * 
 * 
 * @Filename: ProductLimitGateDao.java
 * @Version: 1.0
 * @Author: naxiang 那响
 * @Email: xiang.na@dhc.com.cn
 * 
 */
public interface PurchaseProductLimitGateService {

	/**
	 * 获取T+2上市闸口，下单设置信息,闸口校验
	 * findProductLimitGate
	 * @param params
	 * @return
	 */
	public List<ProductLimitGate> getProductLimitGateList(
			Map<String, Object> params);

	
}