package com.haier.stock.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvWarehouseDao;
import com.haier.stock.dao.stock.InvWarehouseInfoDao;
import com.haier.stock.dao.stock.PopInvWarehouseDao;
import com.haier.stock.model.InvWarehouseInfo;
import com.haier.stock.model.PopInvWarehouse;
import com.haier.stock.service.StockPopInvWarehouseService;

@Service
public class StockPopInvWarehouseServiceImpl implements StockPopInvWarehouseService{
	@Autowired
	private PopInvWarehouseDao popInvWarehouseDao;
	@Autowired
	private InvWarehouseDao invWarehouseDao;
	@Override
	public int deleteByPrimaryKey(String whCode) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.deleteByPrimaryKey(whCode);
	}

	@Override
	public int insert(PopInvWarehouse record) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.insert(record);
	}

	@Override
	public int insertSelective(PopInvWarehouse record) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.insertSelective(record);
	}

	@Override
	public PopInvWarehouse selectByPrimaryKey(String whCode) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.selectByPrimaryKey(whCode);
	}

	@Override
	public int updateByPrimaryKeySelective(PopInvWarehouse record) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PopInvWarehouse record) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.updateByPrimaryKey(record);
	}

	@Override
	public long checkCodeSame(String whCode) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.checkCodeSame(whCode);
	}

	@Override
	public long checkNameSame(String whName) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.checkNameSame(whName);
	}

	@Override
	public List<PopInvWarehouse> exportWarehouse(PopInvWarehouse entity) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.exportWarehouse(entity);
	}

	@Override
	public List getPageByCondition(Object entity, int start, int rows) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.getPageByCondition((PopInvWarehouse) entity, start, rows);
	}

	@Override
	public long getPagerCount(Object entity) {
		// TODO Auto-generated method stub
		return popInvWarehouseDao.getPagerCount((PopInvWarehouse) entity);
	}

	@Override
	public List<InvWarehouseInfo> findCenter() {
		// TODO Auto-generated method stub
		return invWarehouseDao.findCenter();
	}

}
