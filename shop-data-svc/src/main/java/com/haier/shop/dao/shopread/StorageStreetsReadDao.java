package com.haier.shop.dao.shopread;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StorageStreetsReadDao {

	/**
	 * 根据街道id，获取对应的库位列表（清除重复）
	 * @param streetId 街道id
	 * @return
	 */
	List<String> getSCodeByStreet(@Param("streetId") Integer streetId);
	
	/**
	 * 根据省Id查询市Id和市名称
	 * @param provinceId
	 * @return
	 */
	List<Map<String,Object>> getCityIdByProvinceId(Integer provinceId);
	
	List<Map<String,Object>> getCityByCityIds(String[] CityIds);
}
