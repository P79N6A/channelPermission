package com.haier.shop.service;



import com.haier.shop.model.ItemAttribute;

import java.util.List;
import java.util.Map;

/**
 * 产品属性DAO
 *                       
 * @Filename: ShopItemAttributeService.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 *
 */


public interface ShopItemAttributeService {

    ItemAttribute get(Integer id);


    Integer insert(ItemAttribute attribute);


    Integer update(ItemAttribute attribute);


    Integer updateNotNull(ItemAttribute attribute);

    List<ItemAttribute> list(ItemAttribute attribute);

    List<ItemAttribute> getByValueAndValueSetId(ItemAttribute attribute);

    List<String> getAllCbsCategory();

    Integer countItemAttributeWithLike(ItemAttribute itemAttribute);

    List<ItemAttribute> queryItemAttributeWithLike(ItemAttribute itemAttribute);

    List<ItemAttribute> queryProductGroupByCbsCategory(String cbsCategory);

    /**
     * 通过ValueSetId检索ItemAttribute List
     * @param valueSetId
     * @return
     */
    List<ItemAttribute> getByValueSetId(String valueSetId);

    /**
     * 基地直发类别下拉列表数据获取
     * @param map
     * @return
     */
    List<String> getCbsCategoryByProductGroup(Map<String, Object> map);

    List<ItemAttribute> getProductTypes();
    
    List<String> getProductTypesTo2();
}