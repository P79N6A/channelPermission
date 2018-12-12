package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TaoBaoGroupsWriteDao {

	/**
	 * 添加定金尾款
	 * @param map
	 * @return
	 */
	public Integer addTaoBaoGroups(Map<String, Object> map);

	/**
	 * 更新定金尾款
	 * @param map
	 * @return
	 */
	public Integer updateTaoBaoGroups(Map<String, Object> map);

	/**
	 * 删除定金尾款
	 * @param ids
	 * @return
	 */
	public Integer delTaoBaoGroups(Integer[] ids);

}
