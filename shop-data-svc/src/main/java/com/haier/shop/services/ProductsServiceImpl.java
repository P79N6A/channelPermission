package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ProductsReadDao;
import com.haier.shop.dao.shopwrite.ProductsWriteDao;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.Products;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsReadDao productsReadDao;
    @Autowired
    ProductsWriteDao productsWriteDao;

    @Override
    public List<Products> selectProducts1(String sku, String productname) {
        return productsReadDao.selectProducts1(sku, productname);
    }

    @Override
    public List<Products> selectProducts(Products products) {
        return productsReadDao.selectProducts(products);
    }

    @Override
    public Products getBySku(String sku) {
        return productsReadDao.getBySku(sku);
    }

    @Override
    public int updateSaleNumBySku(ProductsNew products) {
        return productsWriteDao.updateSaleNumBySku(products);
    }

    @Override
    public List<ProductBase> getAllSkusListBySale(String onSale) {
        return productsReadDao.getAllSkusListBySale(onSale);
    }

    @Override
    public List<ProductBase> getAllProductsBysCode(String sCode) {
        return productsReadDao.getAllProductsBysCode(sCode);
    }

    @Override
    public ProductBase getBaseBySku(String sku) {
        return productsReadDao.getBaseBySku(sku);
    }

    @Override
    public Products get(Integer id) {
        return productsReadDao.get(id);
    }

    @Override
    public List<Products> getAllProductInfo() {
        return productsReadDao.getAllProductInfo();
    }

    @Override
    public List<ProductBase> getListBySkus(List<String> skuList) {
        return productsReadDao.getListBySkus(skuList);
    }

    @Override
    public List<Products> getOnSaleBigProducts() {
        return productsReadDao.getOnSaleBigProducts();
    }

    @Override
    public List<ProductBase> getAllSkusList(Map<String, Object> paramMap) {
        return productsReadDao.getAllSkusList(paramMap);
    }
}