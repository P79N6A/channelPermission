/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import com.haier.shop.dao.shopread.LesFiveYardsDao;
import com.haier.shop.model.LesFiveYardInfo;
import com.haier.shop.service.PurchaseLesFiveYardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PurchaseLesFiveYardsServiceImpl implements
		PurchaseLesFiveYardsService {

	@Autowired
	LesFiveYardsDao lesFiveYardsDao;

	@Override
	public List<LesFiveYardInfo> selectLesFiveYards(Map<String, Object> params) {
		return lesFiveYardsDao.selectLesFiveYards(params);
	}

	@Override
	public LesFiveYardInfo get(Integer id) {
		return lesFiveYardsDao.get(id);
	}

	@Override
	public LesFiveYardInfo getBySCode(String sCode) {
		return lesFiveYardsDao.getBySCode(sCode);
	}

}
