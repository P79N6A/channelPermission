package com.haier.shop.service;


import com.haier.shop.model.ProductBase;
import com.haier.shop.model.Products;
import com.haier.shop.model.ProductsNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductsNewService {
    /**
     * 根据id获取产品对象
     * @param id
     * @return
     */
    Products get(Integer id);
    ProductsNew get1(Integer id);
    /**
     * 根据sku获取产品对象
     * @param sku
     * @return
     */
    ProductsNew getBySku(String sku);

    /**
     * 根据sku，获取产品基础对象
     * @param sku
     * @return
     */
    ProductBase getBaseBySku(String sku);

    /**
     * 获取所有产品
     * @return
     */
    List<ProductsNew> getAllProductInfo();

    /**
     * 根据sku列表，获取对应的产品列表(只查询上架的sku)
     * @return
     */
    List<ProductBase> getListBySkus(@Param("skuList") List<String> skuList);

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
     * 根据sku更新销量
     * @param products
     * @return
     */
    int updateSaleNumBySku(ProductsNew products);

    /**
     * 获取大家电的商品信息
     * @return
     */
    List<ProductsNew> getOnSaleBigProducts();

    /**
     * 获得所有上架或下架商品
     * @return
     */
    List<ProductBase> getAllSkusList(Map<String, Object> paramMap);
}
