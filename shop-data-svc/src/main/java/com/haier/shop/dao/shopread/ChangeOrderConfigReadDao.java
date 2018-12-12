package com.haier.shop.dao.shopread;

import com.haier.shop.model.ChangeOrderConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 转单关系配置表dao
 * @author wangp-c
 *
 */
@Mapper
public interface ChangeOrderConfigReadDao {

	/**
	 * 根据主键查询转单关系
	 * @param id
	 * @return
	 */
	ChangeOrderConfig get(@Param("id") Integer id);
	
	/**
	 * 根据订单来源，区域，品类，品牌查询唯一转单关系
	 * @return
	 */
	ChangeOrderConfig getBySourceAndBrandAndCateAndregion(@Param("orderSourceCode") String orderSourceCode, @Param("regionId") Integer regionId, @Param("brandId") Integer brandId, @Param("cateId") Integer cateId);
}
