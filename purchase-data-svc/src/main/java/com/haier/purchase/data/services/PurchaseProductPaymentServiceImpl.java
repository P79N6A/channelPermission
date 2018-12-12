package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.ProductPaymentDao;
import com.haier.purchase.data.model.ProductPayment;
import com.haier.purchase.data.service.PurchaseProductPaymentService;


/**
 * Created by 解力 on 2016/7/27.
 */
@Service
public class PurchaseProductPaymentServiceImpl implements PurchaseProductPaymentService{

	@Autowired
	ProductPaymentDao productPaymentDao;
	/**
	 * 获取付款方
	 * @param Map<String, Object> params
	 * @return
	 */
	@Override
	public List<ProductPayment> findPaymentNameByCode(Map<String, Object> params){
		return productPaymentDao.findPaymentNameByCode(params);
	}

	
}
