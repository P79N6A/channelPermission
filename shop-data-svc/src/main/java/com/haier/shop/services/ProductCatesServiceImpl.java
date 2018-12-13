package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ProductCatesReadDao;
import com.haier.shop.model.ProductCates;
import com.haier.shop.service.ProductCatesService;

@Service
public class ProductCatesServiceImpl implements ProductCatesService{

	@Autowired
	ProductCatesReadDao productCatesReadDao;



	@Override
	public List<ProductCates> selectByProducttypesId() {
		return productCatesReadDao.selectByProducttypesId();
	}

	@Override
	public List<ProductCates> selectCateName() {
		return productCatesReadDao.selectCateName();
	}

	@Override
	public List<ProductCates> selectByProducttypesSku(int id) {
		return productCatesReadDao.selectByProducttypesSku(id);
	}

	@Override
	public ProductCates get(Integer id) {
		return productCatesReadDao.get(id);
	}

	@Override
	public List<ProductCates> getAllProductCates() {
		return productCatesReadDao.getAllProductCates();
	}

	@Override
	public List<ProductCates> getAllChildren(Integer parentId) {
		return productCatesReadDao.getAllChildren(parentId);
	}
}