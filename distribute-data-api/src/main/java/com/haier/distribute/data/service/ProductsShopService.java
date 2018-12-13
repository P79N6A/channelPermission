package com.haier.distribute.data.service;

import java.util.List;
import java.util.Map;

import com.haier.distribute.data.model.ProductBase;
import com.haier.distribute.data.model.ProductCenterDTO;
import com.haier.distribute.data.model.Products;


public interface ProductsShopService {

    List<Products> selectProducts(Products products);

	List<ProductCenterDTO> selectBySku(List<String> list);

}