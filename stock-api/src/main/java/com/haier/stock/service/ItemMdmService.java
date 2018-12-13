package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.ProductCates;
import com.haier.shop.model.*;
import com.haier.stock.model.InvStockAge;

/**
 * @author 李超
 */
public interface ItemMdmService {

    /**
     * 根据CBS品类查询产品组
     * @param cbsCategory
     * @return
     */
    ServiceResult<List<ItemAttribute>> queryProductGroupByCbsCategory(String cbsCategory);

    List<ItemBase> getIncompleteItemBaseList();

    int updateMtlInfoForStockAge(InvStockAge stockAge);

    ServiceResult<String> getRegionsCode(Map<String, Object> param);

	ServiceResult<Boolean> saveItemAttribute(ItemAttribute value);

	ServiceResult<Boolean> saveItemBase(ItemBase value);

	ServiceResult<Boolean> manualSyncMdmBySku(ItemBase mtl, String modifier);
	/**
	 * 根据value和valueSetId取得产品属性(ItemAttribute)
	 * @param valueSetId
	 * @param value
	 * @return
	 */
	ServiceResult<ItemAttribute> getItemAttributeByValueSetIdAndValue(String valueSetId,
			String value);
}
