package com.haier.distribute.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.PopProductDao;
import com.haier.distribute.data.dao.distribute.RegionsDao;
import com.haier.distribute.data.model.ProductTiming;
import com.haier.distribute.data.model.Regions;
import com.haier.distribute.data.service.DistributeProductDataService;
@Service
public class DistributeProductDataServiceImpl implements DistributeProductDataService {

	@Autowired
	private PopProductDao popProductDao;
	@Autowired
	private RegionsDao regionsDao;
	@Override
	public List<ProductTiming> pushProductInfo(int channelId) {
		// TODO Auto-generated method stub
		return popProductDao.pushProductInfo(channelId);
	}
	@Override
	public List<Regions> pushAvailable() {
		// TODO Auto-generated method stub
		return regionsDao.pushAvailable();
	}

}
