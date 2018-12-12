package com.haier.shop.service;


import com.haier.shop.model.TaoBaoGroups;


import java.util.List;
import java.util.Map;


public interface ShopTaoBaoGroupsService {

	/**
	 * 通过SKU查询定金尾款信息
	 * @param sku
	 * @return
	 */
	public List<Map<String,Object>> getTaoBaoGroupsListBySku(String sku, Integer page,Integer rows);

	/**
	 * 通过SKU查询定金尾款信息数量
	 * @param sku
	 * @return
	 */
	public Integer getTaoBaoGroupsListBySkuCount(String sku);

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
	 * @param id
	 * @return
	 */
	public Integer delTaoBaoGroups(Integer[] ids);

	/**
	 * 通过sku和商品名称查询
	 * @param sku
	 * @param groupName
	 * @return
	 */
	public Map<String,Object> getTaoBaoGroupsBySkuAndName(String sku,String groupName);

	TaoBaoGroups get(Integer id);
    
	/**
	 * 通过id查询
	 * @param sku
	 * @param groupName
	 * @return
	 */
	public Map<String,Object> getTaoBaoGroupsById(Integer id);
}
