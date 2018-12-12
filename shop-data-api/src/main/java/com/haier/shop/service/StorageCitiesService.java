package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.StorageCities;


public interface StorageCitiesService {
    /**
     * 根据城市id，获取对应的库位列表（已清除重复）
     * @param cityId
     * @return
     */
    List<String> getCodeListByCity(Integer cityId);

    /**
     * 根据区县id，获取对应的库位列表（已清除重复）
     * @param regionId
     * @return
     */
    List<String> getCodeListByRegion(Integer regionId);

    /**
     * 根据条件查询城市列表
     * @param city
     * @return
     */
    List<StorageCities> getStorageCities(StorageCities city);

    StorageCities getStorageCityByIds( Integer provinceId,
                                        Integer cityId,
                                      Integer regionId);

    /**
     * 查询所有省份
     * @return
     */
    List<StorageCities> getAllProvince();

    /**
     * 查询所有城市
     * @return
     */
    List<StorageCities> getAllCityIds();

    /**
     * 根据省份查询城市
     * @param provinceId
     * @return
     */
    List<StorageCities> getAllCityByProvId( Integer provinceId);

    /**
     * 根据城市查询区县
     * @param cityId
     * @return
     */
    List<StorageCities> getAllRegionByCityId( Integer cityId);

    List<StorageCities> getAllCities();
    /**
     * 获取所有区县信息
     * @return
     */
    List<StorageCities> getAllRegions();
}
