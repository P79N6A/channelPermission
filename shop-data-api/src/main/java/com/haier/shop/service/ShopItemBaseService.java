package com.haier.shop.service;


import com.haier.common.ServiceResult;
import com.haier.shop.model.ItemBase;


import java.util.List;
import java.util.Map;

public interface ShopItemBaseService {
	
	/**
	 * 根据物料ID取得品牌code和型号 & 根据物料号取得产品组code 李超
	 * @param material_id 物料ID 物料号
	 * @return
	 */
	List<ItemBase> findItemBaseByMaterialId( String material_id);
	
	/**
	 * 根据物料SKU取得物料基本信息 李超
	 * @param subSku 物料 
	 * @return
	 */
	List<ItemBase> getBySku(ItemBase param);
	
	Integer update(ItemBase base);
	
	Integer insert(ItemBase base);
	
	List<ItemBase> getIncompleteItemBaseList();
	
	List<ItemBase> queryItemBaseByParamWithLike(ItemBase base);
	Integer updateNotNull(ItemBase base);
	
	Integer countItemBaseByParamWithLike(ItemBase base);
	
	/**
     * 查询 -- 按照产品组查询
     * @param depList
     * @return
     */
    List<ItemBase> getItemListByDepList(List<String> depList);

	List<ItemBase> getType();

	List<ItemBase> getProductBaseData(Map<String, Object> params);

	int getRowCnts(Map<String, Object> params);


}
