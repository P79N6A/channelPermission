package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.Brands;




public interface BrandsService {
	List<Brands> selectBrandsList();
	List<Brands>selectBrandsIdList(int id);

	 List<Brands> getAllBrands();
	Brands get(Integer id);
}