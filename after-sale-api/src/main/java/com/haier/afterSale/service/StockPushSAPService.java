package com.haier.afterSale.service;

import java.net.MalformedURLException;

public interface StockPushSAPService {
	void stockPishSapxTim() throws MalformedURLException;
	void emptyEnterSAP() throws MalformedURLException;
	void emptyOutSAP();
	void realOutofSAP();
}
