package com.haier.shop.dao.shopread;

import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.Producttypes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductTypesReadDao {


	List<Producttypes> selectByProducttypesSku(int id);
	List<Producttypes> selectByProducttypes();
	ProductTypesNew getById(int typeId);
	ProductTypesNew getByIdNew(int typeId);
	List<ProductTypesNew> findsort();
	
    List<Map<String,Object>> getProducttypesList();

    List<Producttypes> getProducttypes();

    List<Map<String,Object>> getProductttypesByTypeName(@Param("typeName")String typeName);
}
