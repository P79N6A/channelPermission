package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.StorageStreetsReadDao;
import com.haier.shop.service.StorageStreetsService;

@Service
public class StorageStreetsServiceImpl implements StorageStreetsService {
    /**
     * 根据街道id，获取对应的库位列表（清除重复）
     * @param streetId 街道id
     * @return
     */
    @Autowired
    private StorageStreetsReadDao storageStreetsReadDao;

    public List<String> getSCodeByStreet(Integer streetId){
        return storageStreetsReadDao.getSCodeByStreet(streetId);
    }

	@Override
	public List<Map<String, Object>> getCityIdByProvinceId(Integer provinceId) {
		return storageStreetsReadDao.getCityIdByProvinceId(provinceId);
	}

	@Override
	public List<Map<String, Object>> getCityByCityIds(String[] CityIds) {
		return storageStreetsReadDao.getCityByCityIds(CityIds);
	}
}
