package com.haier.shop.dao.shopread;



import com.haier.shop.model.ItemAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
@Mapper
public interface ItemAttributeReadDao {

    ItemAttribute get(Integer id);

    List<ItemAttribute> list(ItemAttribute attribute);

    List<ItemAttribute> getByValueAndValueSetId(ItemAttribute attribute);

    List<String> getAllCbsCategory();

    Integer countItemAttributeWithLike(ItemAttribute itemAttribute);

    List<ItemAttribute> queryItemAttributeWithLike(ItemAttribute itemAttribute);

    List<ItemAttribute> queryProductGroupByCbsCategory(@Param(value = "cbsCategory") String cbsCategory);

    /**
     * 通过ValueSetId检索ItemAttribute List
     * @param valueSetId
     * @return
     */
    List<ItemAttribute> getByValueSetId(@Param(value = "valueSetId") String valueSetId);

    /**
     * 基地直发类别下拉列表数据获取
     * @param map
     * @return
     */
    List<String> getCbsCategoryByProductGroup(Map<String, Object> map);

    List<ItemAttribute> getProductTypes();
}