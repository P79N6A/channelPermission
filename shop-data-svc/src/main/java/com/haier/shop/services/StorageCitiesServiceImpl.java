package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.StorageCitiesReadDao;
import com.haier.shop.model.StorageCities;
import com.haier.shop.service.StorageCitiesService;

@Service
public class StorageCitiesServiceImpl implements StorageCitiesService{

    @Autowired
    StorageCitiesReadDao storageCitiesReadDao;

    @Override
    public List<String> getCodeListByCity(Integer cityId) {
        return storageCitiesReadDao.getCodeListByCity(cityId);
    }

    @Override
    public List<String> getCodeListByRegion(Integer regionId) {
        return storageCitiesReadDao.getCodeListByRegion(regionId);
    }

    @Override
    public List<StorageCities> getStorageCities(StorageCities city) {
        return storageCitiesReadDao.getStorageCities(city);
    }

    @Override
    public StorageCities getStorageCityByIds(Integer provinceId, Integer cityId, Integer regionId) {
        return storageCitiesReadDao.getStorageCityByIds(provinceId,cityId,regionId);
    }

    @Override
    public List<StorageCities> getAllProvince() {
        return storageCitiesReadDao.getAllProvince();
    }

    @Override
    public List<StorageCities> getAllCityIds() {
        return storageCitiesReadDao.getAllCityIds();
    }

    @Override
    public List<StorageCities> getAllCityByProvId(Integer provinceId) {
        return storageCitiesReadDao.getAllCityByProvId(provinceId);
    }

    @Override
    public List<StorageCities> getAllRegionByCityId(Integer cityId) {
        return storageCitiesReadDao.getAllRegionByCityId(cityId);
    }

    @Override
    public List<StorageCities> getAllCities() {
        return storageCitiesReadDao.getAllCities();
    }

    @Override
    public List<StorageCities> getAllRegions() {
        return storageCitiesReadDao.getAllRegions();
    }
}
