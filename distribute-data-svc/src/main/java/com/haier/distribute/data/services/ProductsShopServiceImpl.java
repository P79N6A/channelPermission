package com.haier.distribute.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.ProductsDao;
import com.haier.distribute.data.dao.shop.ProductsShopDao;
import com.haier.distribute.data.model.ProductBase;
import com.haier.distribute.data.model.ProductCenterDTO;
import com.haier.distribute.data.model.Products;
import com.haier.distribute.data.service.ProductsService;
import com.haier.distribute.data.service.ProductsShopService;

@Service
public class ProductsShopServiceImpl implements ProductsShopService {

	@Autowired
    ProductsShopDao productsShopDao;
	
    @Override
    public List<Products> selectProducts(Products products) {
        return productsShopDao.selectProducts(products);
    }
    
    @Override
    public List<ProductCenterDTO> selectBySku(List<String> list){
        return productsShopDao.selectBySku(list);
    }

}