package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.CustomerCodes;


public interface CustomerCodesService {
	CustomerCodes getByTitle( String title);

	/**
	 * 获取客户编码
	 * @param title
	 * @return
	 */
	List<String> getCustomerCode(String title);
}
