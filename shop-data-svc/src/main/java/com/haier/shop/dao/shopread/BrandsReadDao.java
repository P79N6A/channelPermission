package com.haier.shop.dao.shopread;

import com.haier.shop.dto.Merchandise;
import com.haier.shop.model.Brands;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BrandsReadDao {

	List<Brands> selectBrandsList();

	List<Brands>selectBrandsIdList(int id);

	List<Brands> getAllBrands();

	Brands get(Integer id);
	
	List<Map<String,Object>> getBrands();
	public List<Map<String,Object>> getProducts();
	public List<Map<String,Object>> getProductBy(@Param("map") Map<String,Object> map);
	public List<Map<String,Object>> getProductInfo(@Param("list") List<String> list);
	int addProduct(@Param("map")Map<String,Object> map);
	int addProduct1(@Param("map")Map<String,Object> map);
	int addProduct2(@Param("Merchandise")Merchandise mer);
	int insertSelective(@Param("mer")Merchandise mer);
	int insertSelective1(@Param("mer")Merchandise mer);
	int insertSelective2(@Param("mer")Merchandise mer);
	Integer province(@Param("province")String province,@Param("str")String str);
	Integer city(@Param("city")String city,@Param("str")String str);
	Integer region(@Param("region")String region,@Param("str")String str);

    String getRegionName(@Param("province") Integer province);
}