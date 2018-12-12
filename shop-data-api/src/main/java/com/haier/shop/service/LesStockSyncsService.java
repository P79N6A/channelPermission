package com.haier.shop.service;

import java.util.Date;

import com.haier.shop.model.LesStockSyncs;

public interface LesStockSyncsService {
	
	

	public void insert(LesStockSyncs lesStockSyncs);
	public int getIdbyDonetime(String donetime);
	
	
	
}
