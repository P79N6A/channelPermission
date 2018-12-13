package com.haier.distribute.data.service;

import com.haier.distribute.data.model.ProductTiming;
import com.haier.distribute.data.model.Regions;

import java.util.List;


public interface DistributeProductDataService {
	List<ProductTiming> pushProductInfo(int channelId);

	List<Regions> pushAvailable();
}