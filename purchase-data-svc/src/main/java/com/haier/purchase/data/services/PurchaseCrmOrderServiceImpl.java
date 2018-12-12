/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.CrmOrderDao;
import com.haier.purchase.data.model.CrmOrderVO;
import com.haier.purchase.data.service.PurchaseCrmOrderService;

@Service
public class PurchaseCrmOrderServiceImpl implements PurchaseCrmOrderService {

	@Autowired
	CrmOrderDao crmOrderDao;

	@Override
	public Integer selectCRMOrder(Map<String, Object> params) {
		return crmOrderDao.selectCRMOrder(params);
	}

	@Override
	public Integer insertCRMOrder(Map<String, Object> params) {
		return crmOrderDao.insertCRMOrder(params);
	}

	@Override
	public void updateCRMOrder(Map<String, Object> params) {
		crmOrderDao.updateCRMOrder(params);
	}

	@Override
	public void updateCRMCancelOrder(Map<String, Object> params) {
		crmOrderDao.updateCRMCancelOrder(params);
	}

	@Override
	public List<CrmOrderVO> findCRMOrder(Map map) {
		return crmOrderDao.findCRMOrder(map);
	}

	@Override
	public void updateStatusForSOPO(Map map) {
		crmOrderDao.updateStatusForSOPO(map);
	}

	@Override
	public Integer getMaxFlowFlagInOrder(Map map) {
		return crmOrderDao.getMaxFlowFlagInOrder(map);
	}

	@Override
	public Integer selectCrmOrderCount(String wp_order_id) {
		return crmOrderDao.selectCrmOrderCount(wp_order_id);
	}
}
