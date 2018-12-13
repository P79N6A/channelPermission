package com.haier.shop.dao.shopwrite;

import com.haier.shop.dto.Merchandise;
import com.haier.shop.model.Brands;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BrandsWriteDao {

    int addProduct(@Param("map")Map<String,Object> map);
    int addProduct1(@Param("map")Map<String,Object> map);
    int addProduct2(@Param("Merchandise")Merchandise mer);
    int insertSelective(@Param("mer")Merchandise mer);
    int insertSelective1(@Param("mer")Merchandise mer);
    int insertSelective2(@Param("mer")Merchandise mer);

}