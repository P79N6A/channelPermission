package com.haier.order.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;

public interface StorageStreetsService {

	ServiceResult<List<Map<String, Object>>> getCityIdByProvinceId(Integer provinceId);
	
	ServiceResult<List<Map<String, Object>>> getCityByCityIds(String[] cityIds);
}	

