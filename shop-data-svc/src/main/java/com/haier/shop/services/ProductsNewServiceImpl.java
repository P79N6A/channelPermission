package com.haier.shop.services;

import com.haier.shop.dao.shopread.ProductsReadDao;
import com.haier.shop.dao.shopwrite.Untreated.ProductsNewDao;

import com.haier.shop.dao.shopwrite.ProductsWriteDao;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.Products;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.service.ProductsNewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductsNewServiceImpl implements ProductsNewService {
    @Autowired
    private ProductsNewDao productsNewDao;
    @Autowired
    ProductsReadDao productsReadDao;
    @Autowired
    ProductsWriteDao productsWriteDao;
    /**
     * 根据id获取产品对象
     * @param id
     * @return
     */
    public Products get(Integer id){
        return productsReadDao.get(id);
    }
    /**
     * 根据id获取产品对象
     * @param id
     * @return
     */
    public ProductsNew get1(Integer id){
        return productsReadDao.get1(id);
    }

    /**
     * 根据sku获取产品对象
     * @param sku
     * @return
     */
    public ProductsNew getBySku(String sku){
        ProductsNew x=productsReadDao.getBySku1(sku);
        return x;
    }

    /**
     * 根据sku，获取产品基础对象
     * @param sku
     * @return
     */
    public ProductBase getBaseBySku(String sku){
        return productsReadDao.getBaseBySku(sku);
    }

    /**
     * 获取所有产品
     * @return
     */
    public List<ProductsNew> getAllProductInfo(){
        return productsNewDao.getAllProductInfo();
    }

    /**
     * 根据sku列表，获取对应的产品列表(只查询上架的sku)
     * @return
     */
    public List<ProductBase> getListBySkus( List<String> skuList){
        return productsNewDao.getListBySkus(skuList);
    }

    /**
     * 获得所有上架或下架商品
     * @return
     */
    public  List<ProductBase> getAllSkusListBySale(String onSale){
        return productsNewDao.getAllSkusListBySale(onSale);
    }

    /**
     * 查询所有有scode的Products
     * @return
     */
    public   List<ProductBase> getAllProductsBysCode(String sCode){
        return productsNewDao.getAllProductsBysCode(sCode);
    }

    /**
     * 根据sku更新销量
     * @param products
     * @return
     */
    public  int updateSaleNumBySku(ProductsNew products){
        return productsWriteDao.updateSaleNumBySku(products);
    }

    /**
     * 获取大家电的商品信息
     * @return
     */
    public  List<ProductsNew> getOnSaleBigProducts(){
        return productsNewDao.getOnSaleBigProducts();
    }

    /**
     * 获得所有上架或下架商品
     * @return
     */
    public  List<ProductBase> getAllSkusList(Map<String, Object> paramMap){
        return productsNewDao.getAllSkusList(paramMap);
    }
}
