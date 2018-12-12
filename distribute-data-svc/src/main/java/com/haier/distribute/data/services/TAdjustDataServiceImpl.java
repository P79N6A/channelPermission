package com.haier.distribute.data.services;


import java.util.List;

import com.haier.distribute.data.dao.distribute.TAdjustDataDao;
import com.haier.distribute.data.model.TAdjustData;
import com.haier.distribute.data.service.TAdjustDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TAdjustDataServiceImpl implements TAdjustDataService {

    @Autowired
    TAdjustDataDao tAdjustDataDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tAdjustDataDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TAdjustData record) {
        return tAdjustDataDao.insert(record);
    }

    @Override
    public int insertSelective(TAdjustData record) {
        return tAdjustDataDao.insertSelective(record);
    }

    @Override
    public TAdjustData selectByPrimaryKey(Integer id) {
        return tAdjustDataDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TAdjustData record) {
        return tAdjustDataDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TAdjustData record) {
        return tAdjustDataDao.updateByPrimaryKey(record);
    }

    @Override
    public String getVehicleAdjustNo(String begin) {
        return tAdjustDataDao.getVehicleAdjustNo(begin);
    }

    @Override
    public int updateSelectiveByAdjustNo(TAdjustData entity) {
        return tAdjustDataDao.updateSelectiveByAdjustNo(entity);
    }

    @Override
    public List<TAdjustData> exportAdjustList(TAdjustData entity) {
        return tAdjustDataDao.exportAdjustList(entity);
    }

	@Override
	public List<TAdjustData> getPageByCondition(TAdjustData entity, int start, int rows) {
		// TODO Auto-generated method stub
		return tAdjustDataDao.getPageByCondition((TAdjustData) entity, start, rows);
	}

	@Override
	public long getPagerCount(TAdjustData entity) {
		// TODO Auto-generated method stub
		return tAdjustDataDao.getPagerCount((TAdjustData) entity);
	}

}