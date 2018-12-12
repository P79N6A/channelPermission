package com.haier.distribute.data.services;

import java.util.List;

import com.haier.distribute.data.dao.distribute.PopProductDao;
import com.haier.distribute.data.model.Product;
import com.haier.distribute.data.service.PopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PopProductServiceImpl implements PopProductService {

	@Autowired
	PopProductDao popProductDao;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return popProductDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Product record) {
		return popProductDao.insert(record);
	}

	@Override
	public int insertSelective(Product c) {
//		return popProductDao.insertSelective(c);
		popProductDao.insertSelective(c);
		return c.getId();
	}

	@Override
	public int updateByPrimaryKeySelective(Product record) {
		return popProductDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Product record) {
		return popProductDao.updateByPrimaryKey(record);
	}

	@Override
	public Product selectByPrimaryKey(Integer id) {
		return popProductDao.selectByPrimaryKey(id);
	}

	@Override
	public int getPagerCountS(Product entity) {
		return popProductDao.getPagerCountS(entity);
	}

	@Override
	public List<Product> selectProductList(Product entity) {
		return popProductDao.selectProductList(entity);
	}

	@Override
	public List<Product> selectProductListf(Product entity, int start, int rows) {
		return popProductDao.selectProductListf(entity,start,rows);
	}

	@Override
	public int selectByproductCode(int channelId, String productCode, int id) {
		return popProductDao.selectByproductCode(channelId,productCode,id);
	}

	@Override
	public Product checkSkuAndChannel(String sku, String channelCode) {
		return popProductDao.checkSkuAndChannel(sku,channelCode);
	}
}