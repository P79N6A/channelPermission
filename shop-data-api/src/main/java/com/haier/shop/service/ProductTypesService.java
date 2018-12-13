package com.haier.shop.service;


import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.Producttypes;

import java.util.List;
import java.util.Map;

public interface ProductTypesService {

	List<Producttypes> selectByProducttypesSku(int id);
	List<Producttypes> selectByProducttypes();
	ProductTypesNew getById( int typeId);
    ProductTypesNew getByIdNew(  int typeId);
    List<Map<String,Object>> getProducttypesList();

	List<Producttypes> getProducttypes();

	List<Map<String,Object>> getProductttypesByTypeName(String typeName);
}
