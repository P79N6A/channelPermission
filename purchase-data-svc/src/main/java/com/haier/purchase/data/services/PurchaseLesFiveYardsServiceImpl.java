/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.LesFiveYardsDao;
import com.haier.purchase.data.model.LesFiveYardInfo;
import com.haier.purchase.data.service.PurchaseLesFiveYardsService;

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
