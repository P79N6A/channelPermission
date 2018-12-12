package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.ProductLimitGateDao;
import com.haier.purchase.data.model.ProductLimitGate;
import com.haier.purchase.data.service.PurchaseProductLimitGateService;


/**
 * 
 * 
 * @Filename: ProductLimitGateDao.java
 * @Version: 1.0
 * @Author: naxiang 那响
 * @Email: xiang.na@dhc.com.cn
 * 
 */
@Service
public class PurchaseProductLimitGateServiceImpl implements PurchaseProductLimitGateService{

	@Autowired
	ProductLimitGateDao productLimitGateDao;
	
	/**
	 * 获取T+2上市闸口，下单设置信息,闸口校验
	 * findProductLimitGate
	 * @param params
	 * @return
	 */
	@Override
	public List<ProductLimitGate> getProductLimitGateList(
			Map<String, Object> params){
		return productLimitGateDao.getProductLimitGateList(params);
	}

	
}