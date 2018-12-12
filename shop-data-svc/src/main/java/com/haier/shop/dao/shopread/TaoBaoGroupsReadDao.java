package com.haier.shop.dao.shopread;

import com.haier.shop.model.TaoBaoGroups;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaoBaoGroupsReadDao {

	/**
	 * 通过SKU查询定金尾款信息
	 * @param sku
	 * @return
	 */
	public List<Map<String,Object>> getTaoBaoGroupsListBySku(@Param(value = "sku") String sku, @Param(value = "page") Integer page, @Param(value = "rows") Integer rows);

	/**
	 * 通过SKU查询定金尾款信息数量
	 * @param sku
	 * @return
	 */
	public Integer getTaoBaoGroupsListBySkuCount(@Param(value = "sku") String sku);

	/**
	 * 通过sku和商品名称查询
	 * @param sku
	 * @param groupName
	 * @return
	 */
	public Map<String,Object> getTaoBaoGroupsBySkuAndName(@Param(value = "sku") String sku, @Param(value = "groupName") String groupName);

	TaoBaoGroups get(Integer id);
    
	/**
	 * 通过id查询
	 * @param sku
	 * @param groupName
	 * @return
	 */
	public Map<String,Object> getTaoBaoGroupsById(@Param(value="id")Integer id);
}
