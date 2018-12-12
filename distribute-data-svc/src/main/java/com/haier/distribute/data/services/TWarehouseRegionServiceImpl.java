package com.haier.distribute.data.services;


import com.haier.distribute.data.dao.distribute.TWarehouseRegionDao;
import com.haier.distribute.data.model.TWarehouseRegion;
import com.haier.distribute.data.service.TWarehouseRegionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TWarehouseRegionServiceImpl implements TWarehouseRegionService {
    @Autowired
    TWarehouseRegionDao tWarehouseRegionDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tWarehouseRegionDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TWarehouseRegion record) {
        return tWarehouseRegionDao.insert(record);
    }

    @Override
    public int insertSelective(TWarehouseRegion record) {
        return tWarehouseRegionDao.insertSelective(record);
    }

    @Override
    public TWarehouseRegion selectByPrimaryKey(Integer id) {
        return tWarehouseRegionDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TWarehouseRegion record) {
        return tWarehouseRegionDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TWarehouseRegion record) {
        return tWarehouseRegionDao.updateByPrimaryKey(record);
    }

    @Override
    public int checkRegion(int channelId, int regionId, int id) {
        return tWarehouseRegionDao.checkRegion(channelId, regionId, id);
    }

	@Override
	public List<TWarehouseRegion> getPageByCondition(TWarehouseRegion entity, int start, int rows) {
		// TODO Auto-generated method stub
		return tWarehouseRegionDao.getPageByCondition((TWarehouseRegion) entity, start, rows);
	}

	@Override
	public long getPagerCount(TWarehouseRegion entity) {
		// TODO Auto-generated method stub
		return tWarehouseRegionDao.getPagerCount((TWarehouseRegion) entity);
	}
}