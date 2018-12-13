package com.haier.distribute.data.service;


import java.util.List;

import com.haier.distribute.data.model.ProductTypesNew;
import com.haier.distribute.data.model.Producttypes;


public interface ProductTypesService {

    List<Producttypes> selectByProducttypesSku(int id);

    List<Producttypes> selectByProducttypes();

    ProductTypesNew getById(int typeId);

    ProductTypesNew getByIdNew(int typeId);

    Producttypes getOneById(long l);

	Producttypes getProductsTypeBySKU(String sku);
}
