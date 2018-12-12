package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.Producttypes;

public interface ProductTypesService {

	List<Producttypes> selectByProducttypesSku(int id);
	List<Producttypes> selectByProducttypes();
	ProductTypesNew getById( int typeId);
    ProductTypesNew getByIdNew(  int typeId);
}
