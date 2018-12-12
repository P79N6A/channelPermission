package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.ProductPayment;


/**
 * Created by 解力 on 2016/7/27.
 */
public interface PurchaseProductPaymentService {

	/**
	 * 获取付款方
	 * @param Map<String, Object> params
	 * @return
	 */
	public List<ProductPayment> findPaymentNameByCode(Map<String, Object> params);

	
}
