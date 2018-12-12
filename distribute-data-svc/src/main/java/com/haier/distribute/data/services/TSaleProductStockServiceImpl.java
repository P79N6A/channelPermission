package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.TSaleProductStockDao;
import com.haier.distribute.data.model.TSaleProductStock;
import com.haier.distribute.data.service.TSaleProductStockService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TSaleProductStockServiceImpl implements TSaleProductStockService {
    @Autowired
    TSaleProductStockDao tSaleProductStockDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tSaleProductStockDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TSaleProductStock record) {
        return tSaleProductStockDao.insert(record);
    }

    @Override
    public int insertSelective(TSaleProductStock record) {
        return tSaleProductStockDao.insertSelective(record);
    }

    @Override
    public TSaleProductStock selectByPrimaryKey(Integer id) {
        return tSaleProductStockDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TSaleProductStock record) {
        return tSaleProductStockDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TSaleProductStock record) {
        return tSaleProductStockDao.updateByPrimaryKey(record);
    }

	@Override
	public List<TSaleProductStock> getPageByCondition(TSaleProductStock entity, int start, int rows) {
		// TODO Auto-generated method stub
		return tSaleProductStockDao.getPageByCondition((TSaleProductStock) entity, start, rows);
	}

	@Override
	public long getPagerCount(TSaleProductStock entity) {
		// TODO Auto-generated method stub
		return tSaleProductStockDao.getPagerCount((TSaleProductStock) entity);
	}

	@Override
	public List<TSaleProductStock> checkCode(TSaleProductStock entity) {
		// TODO Auto-generated method stub
		return tSaleProductStockDao.checkCode((TSaleProductStock) entity);
	}
}