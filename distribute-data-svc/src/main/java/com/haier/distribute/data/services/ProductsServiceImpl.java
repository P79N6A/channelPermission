package com.haier.distribute.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.ProductsDao;
import com.haier.distribute.data.model.ProductBase;
import com.haier.distribute.data.model.Products;
import com.haier.distribute.data.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsDao productsDao;

    @Override
    public List<Products> selectProducts1(String sku, String productname) {
        return productsDao.selectProducts1(sku, productname);
    }

//    @Override
//    public List<Products> selectProducts(Products products) {
//        return productsDao.selectProducts(products);
//    }

    @Override
    public Products getBySku(String sku) {
        return productsDao.getBySku(sku);
    }

    @Override
    public int updateSaleNumBySku(Products products) {
        return productsDao.updateSaleNumBySku(products);
    }

    @Override
    public List<ProductBase> getAllSkusListBySale(String onSale) {
        return productsDao.getAllSkusListBySale(onSale);
    }

    @Override
    public List<ProductBase> getAllProductsBysCode(String sCode) {
        return productsDao.getAllProductsBysCode(sCode);
    }

    @Override
    public ProductBase getBaseBySku(String sku) {
        return productsDao.getBaseBySku(sku);
    }

    @Override
    public Products get(Integer id) {
        return productsDao.get(id);
    }

    @Override
    public List<Products> getAllProductInfo() {
        return productsDao.getAllProductInfo();
    }

    @Override
    public List<ProductBase> getListBySkus(List<String> skuList) {
        return productsDao.getListBySkus(skuList);
    }

    @Override
    public List<Products> getOnSaleBigProducts() {
        return productsDao.getOnSaleBigProducts();
    }

    @Override
    public List<ProductBase> getAllSkusList(Map<String, Object> paramMap) {
        return productsDao.getAllSkusList(paramMap);
    }

    @Override
    public Products checkSkuFromProducts(String sku) {
        return productsDao.checkSkuFromProducts(sku);
    }
}