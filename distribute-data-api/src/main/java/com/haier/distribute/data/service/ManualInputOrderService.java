package com.haier.distribute.data.service;

import java.util.List;
import java.util.Map;

import com.haier.distribute.data.model.TOrdersManual;

public interface ManualInputOrderService {
	
	List<Map<String, Object>> getManualInputOrderAll(Map<String, Object> params);
	Integer getManualInputOrderAllCount(Map<String, Object> params);
	Integer inserManualInputOrder(TOrdersManual tOrdersManual);
	Integer updateManualInputOrder(TOrdersManual tOrdersManual);
	Integer insertManualInputOrder(TOrdersManual tOrdersManual);
}
