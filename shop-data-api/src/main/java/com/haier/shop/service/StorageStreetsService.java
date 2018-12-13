package com.haier.shop.service;

import java.util.List;
import java.util.Map;

public interface StorageStreetsService {
    /**
     * 根据街道id，获取对应的库位列表（清除重复）
     * @param streetId 街道id
     * @return
     */
    List<String> getSCodeByStreet(Integer streetId);
    
	List<Map<String,Object>> getCityIdByProvinceId(Integer provinceId);
	
	List<Map<String,Object>> getCityByCityIds(String[] CityIds);
}
