package com.haier.shop.dao.shopwrite;



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
public interface ItemAttributeWriteDao {

    Integer insert(ItemAttribute attribute);

    Integer update(ItemAttribute attribute);

    Integer updateNotNull(ItemAttribute attribute);
    
    List<String> getProductTypesTo2();

}