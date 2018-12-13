package com.haier.distribute.data.dao.shop;

import java.util.List;

import com.haier.distribute.data.model.DepartmentProductType;
import com.haier.distribute.data.model.ProductCenterDTO;
import com.haier.distribute.data.model.Products;
import com.haier.distribute.data.model.Producttypes;

public interface ProductsShopDao {

    List<Products>selectProducts(Products products);

	List<ProductCenterDTO> selectBySku(List<String> list);
	
	int getProductTypesIdBySKU(String sku);

	Producttypes getProducttypesById(int id);

	DepartmentProductType getDepartment(Integer productTypeId);
}