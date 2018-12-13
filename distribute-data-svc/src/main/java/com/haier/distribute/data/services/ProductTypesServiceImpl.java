package com.haier.distribute.data.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.ProductTypesDao;
import com.haier.distribute.data.dao.shop.ProductsShopDao;
import com.haier.distribute.data.model.ProductTypesNew;
import com.haier.distribute.data.model.Producttypes;
import com.haier.distribute.data.service.ProductTypesService;

@Service
public class ProductTypesServiceImpl implements ProductTypesService {

	@Autowired
	ProductTypesDao productTypesDao;
	
	@Autowired
	ProductsShopDao productsShopDao;

	@Override
	public List<Producttypes> selectByProducttypesSku(int id) {
		return productTypesDao.selectByProducttypesSku(id);
	}

	@Override
	public List<Producttypes> selectByProducttypes() {
		return productTypesDao.selectByProducttypes();
	}

	@Override
	public ProductTypesNew getById(int typeId) {
		return productTypesDao.getById(typeId);
	}

	@Override
	public ProductTypesNew getByIdNew(int typeId) {
		return productTypesDao.getByIdNew(typeId);
	}

	@Override
	public Producttypes getOneById(long l) {
		return productTypesDao.getOneById(l);
	}

	@Override
	public Producttypes getProductsTypeBySKU(String sku) {
		int id=productsShopDao.getProductTypesIdBySKU(sku);
		return productsShopDao.getProducttypesById(id);
	}
}
