package com.haier.distribute.data.services;


import java.util.List;
import java.util.Map;

import com.haier.distribute.data.dao.distribute.TWarehouseDao;
import com.haier.distribute.data.model.TWarehouse;
import com.haier.distribute.data.service.TWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TWarehouseServiceImpl implements TWarehouseService {
    @Autowired
    TWarehouseDao tWarehouseDao;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tWarehouseDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TWarehouse record) {
        return tWarehouseDao.insert(record);
    }

    @Override
    public int insertSelective(TWarehouse record) {
        return tWarehouseDao.insertSelective(record);
    }

    @Override
    public TWarehouse selectByPrimaryKey(Integer id) {
        return tWarehouseDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TWarehouse record) {
        return tWarehouseDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TWarehouse record) {
        return tWarehouseDao.updateByPrimaryKey(record);
    }

    @Override
    public List<TWarehouse> getAll() {
        return tWarehouseDao.getAll();
    }

    @Override
    public List<TWarehouse> getWareHouseServiceStart() {
        return tWarehouseDao.getWareHouseServiceStart();
    }

    @Override
    public List<Map<String, Object>> autoLoadPid(Integer channelId, Integer id) {
        return tWarehouseDao.autoLoadPid(channelId, id);
    }

	@Override
	public List<TWarehouse> getPageByCondition(TWarehouse entity, int start, int rows) {
		// TODO Auto-generated method stub
		return tWarehouseDao.getPageByCondition((TWarehouse) entity, start, rows);
	}

	@Override
	public long getPagerCount(TWarehouse entity) {
		// TODO Auto-generated method stub
		return tWarehouseDao.getPagerCount((TWarehouse) entity);
	}

	@Override
	public List<TWarehouse> checkCode(TWarehouse entity) {
		// TODO Auto-generated method stub
		return tWarehouseDao.checkCode((TWarehouse) entity);
	}
}