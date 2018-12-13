package com.haier.distribute.data.dao.distribute;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.distribute.data.model.ProductBase;
import com.haier.distribute.data.model.Products;





public interface ProductsDao {
 
    List<Products>selectProducts1(@Param("sku")String sku , @Param("productName")String productname);

//    List<Products>selectProducts(Products products);
    /**
     * 根据sku获取产品对象
     * @param sku
     * @return
     */
    Products getBySku(String sku);

    /**
     * 根据sku更新销量
     * @param products
     * @return
     */
    int updateSaleNumBySku(Products products);
    

    /**
     * 获得所有上架或下架商品
     * @return
     */
    List<ProductBase> getAllSkusListBySale(@Param("onSale") String onSale);
    
    /**
     * 查询所有有scode的Products
     * @return
     */
    List<ProductBase> getAllProductsBysCode(@Param("sCode") String sCode);
    
    /**
     * 根据sku，获取产品基础对象
     * @param sku
     * @return
     */
    ProductBase getBaseBySku(String sku);
    /**
     * 根据id获取产品对象
     * @param id
     * @return
     */
    Products get(Integer id);
    


     /* 获取所有产品
     * @return
     */
    List<Products> getAllProductInfo();
    
    /**
     * 根据sku列表，获取对应的产品列表(只查询上架的sku)
     * @return
     */
    List<ProductBase> getListBySkus(@Param("skuList") List<String> skuList);
    
    /**
     * 获取大家电的商品信息
     * @return
     */
    List<Products> getOnSaleBigProducts();
    
    /**
     * 获得所有上架或下架商品
     * @return
     */
    List<ProductBase> getAllSkusList(Map<String, Object> paramMap);

    Products checkSkuFromProducts(String sku);
}