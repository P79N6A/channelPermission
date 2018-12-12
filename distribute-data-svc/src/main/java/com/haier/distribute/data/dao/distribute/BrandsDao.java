package com.haier.distribute.data.dao.distribute;

import java.util.List;

import com.haier.distribute.data.model.Brands;


public interface BrandsDao {
	List<Brands> selectBrandsList();
	List<Brands>selectBrandsIdList(int id);

	 List<Brands> getAllBrands();
	Brands get(Integer id);
}