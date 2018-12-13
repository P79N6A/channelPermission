package com.haier.shop.service;

import java.util.List;
import java.util.Map;

import com.haier.shop.dto.Merchandise;
import com.haier.shop.model.Brands;
import com.haier.shop.model.CostPools;
import com.haier.shop.model.GatePrice;




public interface BrandsService {
	List<Brands> selectBrandsList();
	List<Brands>selectBrandsIdList(int id);

	 List<Brands> getAllBrands();
	Brands get(Integer id);
	List<Map<String,Object>> getBrands();
	public List<Map<String,Object>> getProducts();
	public List<Map<String,Object>> getProductBy(Map<String,Object> map);
	public List<Map<String,Object>> getProductInfo(List<String> list);
	int addProduct(Map<String,Object> map);
	int addProduct1(Map<String,Object> map);
	int addProduct2(   Merchandise mer);

	int insertSelective( Merchandise mer);
	int insertSelective1( Merchandise mer);
	int insertSelective2(   Merchandise mer);
	Integer province( String mer,String str);
	Integer city(  String mer,String str);
	Integer region(   String mer,String str);

	String getRegionName(Integer province);

    void addOrdersAndOrderProducts(Merchandise inv, CostPools ncp, GatePrice gatePrice,Boolean idGift,String userName,String source);

   }