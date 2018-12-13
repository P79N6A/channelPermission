package com.haier.distribute.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.model.TOrdersManual;
import com.haier.distribute.data.service.ManualInputOrderService;
import com.haier.distribute.service.ManualInputsOrderService;
@Service
public class ManualInputsOrderServiceImpl implements ManualInputsOrderService{

	@Autowired
	ManualInputOrderService manualInputOrderService;
	
	@Override
	public List<Map<String, Object>> getManualInputOrderAll(Map<String, Object> params) {
		return manualInputOrderService.getManualInputOrderAll(params);
	}

	@Override
	public Integer getManualInputOrderAllCount(Map<String, Object> params) {
		return manualInputOrderService.getManualInputOrderAllCount(params);
	}

	@Override
	public Integer inserManualInputOrder(TOrdersManual tOrdersManual) {
		return manualInputOrderService.inserManualInputOrder(tOrdersManual);
	}

	@Override
	public Integer updateManualInputOrder(TOrdersManual tOrdersManual) {
		return manualInputOrderService.updateManualInputOrder(tOrdersManual);
	}

	@Override
	public Integer insertManualInputOrder(TOrdersManual tOrdersManual) {
		return manualInputOrderService.insertManualInputOrder(tOrdersManual);
	}

}

