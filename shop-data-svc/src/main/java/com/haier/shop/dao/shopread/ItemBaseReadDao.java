package com.haier.shop.dao.shopread;


import com.haier.shop.model.ItemBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface ItemBaseReadDao {
	
	/**
	 * 根据物料ID取得品牌code和型号 & 根据物料号取得产品组code 李超
	 * @param material_id 物料ID 物料号
	 * @return
	 */
	List<ItemBase> findItemBaseByMaterialId(@Param("material_id") String material_id);
	
	/**
	 * 根据物料SKU取得物料基本信息 李超
	 * @param param 物料
	 * @return
	 */
	List<ItemBase> getBySku(ItemBase param);
	
	List<ItemBase> getIncompleteItemBaseList();
	
	List<ItemBase> queryItemBaseByParamWithLike(ItemBase base);

	Integer countItemBaseByParamWithLike(ItemBase base);
	
	/**
     * 查询 -- 按照产品组查询
     * @param depList
     * @return
     */
    List<ItemBase> getItemListByDepList(@Param("depList") List<String> depList);

	String getByMaterialCode(@Param("materialCode") String materialCode);

	List<ItemBase> getType();

	List<ItemBase> getProductBaseData(Map<String, Object> params);

	int getRowCnts(Map<String, Object> params);


}
