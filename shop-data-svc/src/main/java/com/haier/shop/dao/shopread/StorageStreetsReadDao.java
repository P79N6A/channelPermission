package com.haier.shop.dao.shopread;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StorageStreetsReadDao {

	/**
	 * 根据街道id，获取对应的库位列表（清除重复）
	 * @param streetId 街道id
	 * @return
	 */
	List<String> getSCodeByStreet(@Param("streetId") Integer streetId);
}
