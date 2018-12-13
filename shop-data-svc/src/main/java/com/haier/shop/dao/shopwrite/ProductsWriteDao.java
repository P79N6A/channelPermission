package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.Products;
import com.haier.shop.model.ProductsNew;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductsWriteDao {

    /**
     * 根据sku更新销量
     * @param products
     * @return
     */
    int updateSaleNumBySku(ProductsNew products);
    /**
     * 根据id获取产品对象
     * @param id
     * @return
     */
    ProductsNew get(Integer id);

    /**
     * 根据sku获取产品对象
     * @param sku
     * @return
     */
    List<Map<String,Object>> getBySku1(String sku);
    
    Integer addProduct(Products products);
    
    Integer updateProduct(Products products);
    
    Integer delProduct(Integer id);
}