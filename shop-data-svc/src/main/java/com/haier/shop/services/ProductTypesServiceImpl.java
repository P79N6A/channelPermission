package com.haier.shop.services;


import com.haier.shop.dao.shopread.ProductTypesReadDao;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.Producttypes;
import com.haier.shop.service.ProductTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductTypesServiceImpl implements ProductTypesService {


	@Autowired
	ProductTypesReadDao productTypesReadDao;

	@Override
	public List<Producttypes> selectByProducttypesSku(int id) {
		return productTypesReadDao.selectByProducttypesSku(id);
	}

	@Override
	public List<Producttypes> selectByProducttypes() {
		return productTypesReadDao.selectByProducttypes();
	}

	@Override
	public ProductTypesNew getById(int typeId) {
		return productTypesReadDao.getById(typeId);
	}

	@Override
	public ProductTypesNew getByIdNew(int typeId) {
		return productTypesReadDao.getByIdNew(typeId);
	}

	@Override
	public List<Map<String, Object>> getProducttypesList() {
		return productTypesReadDao.getProducttypesList();
	}
	@Override
	public List<Producttypes> getProducttypes() {
		return productTypesReadDao.getProducttypes();
	}

	@Override
	public List<Map<String, Object>> getProductttypesByTypeName(String typeName) {
		return productTypesReadDao.getProductttypesByTypeName(typeName);

	}
}
