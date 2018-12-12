package com.haier.shop.dao.shopread;

import com.haier.shop.model.StorageCities;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StorageCitiesReadDao {
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

    StorageCities getStorageCityByIds(@Param("provinceId") Integer provinceId,
                                      @Param("cityId") Integer cityId,
                                      @Param("regionId") Integer regionId);

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
    List<StorageCities> getAllCityByProvId(@Param("provinceId") Integer provinceId);

    /**
     * 根据城市查询区县
     * @param cityId
     * @return
     */
    List<StorageCities> getAllRegionByCityId(@Param("cityId") Integer cityId);

    List<StorageCities> getAllCities();
    /**
     * 获取所有区县信息
     * @return
     */
    List<StorageCities> getAllRegions();
}
