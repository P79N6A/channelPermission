package com.haier.distribute.data.services;

import java.util.List;

import com.haier.distribute.data.dao.distribute.ProductCatesDao;
import com.haier.distribute.data.model.ProductCates;
import com.haier.distribute.data.service.ProductCatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCatesServiceImpl implements ProductCatesService{

	@Autowired
	ProductCatesDao productCatesDao;

	@Override
	public List<ProductCates> selectByProducttypesId() {
		return productCatesDao.selectByProducttypesId();
	}

	@Override
	public List<ProductCates> selectByProducttypesSku(int id) {
		return productCatesDao.selectByProducttypesSku(id);
	}

	@Override
	public ProductCates get(Integer id) {
		return productCatesDao.get(id);
	}

	@Override
	public List<ProductCates> getAllProductCates() {
		return productCatesDao.getAllProductCates();
	}

	@Override
	public List<ProductCates> getAllChildren(Integer parentId) {
		return productCatesDao.getAllChildren(parentId);
	}
}