package com.haier.order.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.shop.model.ProductCates;
import com.haier.shop.model.Products;

public interface ProductsManageService {

	List<ProductCates> getProductCate(List<Map<String, Object>> returnList,Integer id);
	
	ServiceResult<List<Map<String, Object>>> getProductType();
	
	ServiceResult<List<Map<String, Object>>> getBrands();
	
	ServiceResult<List<Map<String, Object>>> queryProductsManageList(Map<String,Object> map);
	
	ServiceResult<Products> findProductsById(Integer id);
	
	ServiceResult<List<Map<String,Object>>> getOnSaleProductIds();
	
	Boolean productAdd(Products product);
	
	Boolean productUpdate(Products product);
	
	Boolean delProduct(Integer id);
	
	ServiceResult<Map<String, Object>> findProductBySku(String sku);
	
	ServiceResult<Map<String, Object>> findProductByName(String productName);

	ServiceResult<List<Map<String, Object>>> exportProductsManageList(Map<String, Object> map);
}
