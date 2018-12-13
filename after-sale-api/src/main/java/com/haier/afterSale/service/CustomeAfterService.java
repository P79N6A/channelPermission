package com.haier.afterSale.service;

import java.net.MalformedURLException;
import java.text.ParseException;

public interface CustomeAfterService {
	void generateOutOfStorage();
	void notOpenBoxOutOfStorage();
	void notOutBoxQuality() throws MalformedURLException;
	void notOutBoxStockPishSAP();
	void exchangeGoods_3W ();
	void exchangeTable () throws ParseException;
	void returnTable() throws ParseException;
	void returnGoodsPushSAP() throws MalformedURLException, ParseException;
//	void intercept_3w() throws MalformedURLException, ParseException;
	void signinInvalidatedInvoice();
	/**
	 * 小家电物流状态更新
	 */
	public void b2cstateOfgoods();
}
