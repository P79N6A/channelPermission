package com.haier.distribute.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.ManualInputOrderDao;
import com.haier.distribute.data.model.TOrdersManual;
import com.haier.distribute.data.service.ManualInputOrderService;

@Service
public class ManualInputOrderServiceImpl implements ManualInputOrderService{

	@Autowired
	ManualInputOrderDao manualInputOrderDao;
	@Override
	public List<Map<String, Object>> getManualInputOrderAll(Map<String, Object> params) {
		
		
		return manualInputOrderDao.getManualInputOrderAll(params);
	}
	@Override
	public Integer getManualInputOrderAllCount(Map<String, Object> params) {
		return manualInputOrderDao.getManualInputOrderAllCount(params);
	}
	@Override
	public Integer inserManualInputOrder(TOrdersManual tOrdersManual) {
		return manualInputOrderDao.inserManualInputOrder(tOrdersManual);
	}
	@Override
	public Integer updateManualInputOrder(TOrdersManual tOrdersManual) {
		return manualInputOrderDao.updateManualInputOrder(tOrdersManual);
	}
	@Override
	public Integer insertManualInputOrder(TOrdersManual tOrdersManual) {

		manualInputOrderDao.insertManualInputOrder(tOrdersManual);
		return tOrdersManual.getId();
	}

}

