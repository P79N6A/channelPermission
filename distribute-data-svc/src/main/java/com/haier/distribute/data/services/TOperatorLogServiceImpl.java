package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.TOperatorLogDao;
import com.haier.distribute.data.model.TOperatorLog;
import com.haier.distribute.data.service.TOperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TOperatorLogServiceImpl implements TOperatorLogService {
    @Autowired
    TOperatorLogDao tOperatorLogDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tOperatorLogDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TOperatorLog record) {
        return tOperatorLogDao.insert(record);
    }

    @Override
    public int insertSelective(TOperatorLog record) {
        return tOperatorLogDao.insertSelective(record);
    }

    @Override
    public TOperatorLog selectByPrimaryKey(Integer id) {
        return tOperatorLogDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TOperatorLog record) {
        return tOperatorLogDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TOperatorLog record) {
        return tOperatorLogDao.updateByPrimaryKey(record);
    }
}