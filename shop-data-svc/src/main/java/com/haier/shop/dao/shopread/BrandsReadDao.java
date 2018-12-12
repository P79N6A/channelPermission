package com.haier.shop.dao.shopread;

import com.haier.shop.model.Brands;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandsReadDao {

	List<Brands> selectBrandsList();

	List<Brands>selectBrandsIdList(int id);

	List<Brands> getAllBrands();

	Brands get(Integer id);
}