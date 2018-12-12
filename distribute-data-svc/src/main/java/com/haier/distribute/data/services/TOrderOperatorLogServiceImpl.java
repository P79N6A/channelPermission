package com.haier.distribute.data.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.TOrderOperatorLogDao;
import com.haier.distribute.data.model.TOrderOperatorLog;
import com.haier.distribute.data.service.TOrderOperatorLogService;

@Service
public class TOrderOperatorLogServiceImpl implements TOrderOperatorLogService {

    @Autowired
    TOrderOperatorLogDao tOrderOperatorLogDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tOrderOperatorLogDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TOrderOperatorLog record) {
        return tOrderOperatorLogDao.insert(record);
    }

    @Override
    public int insertSelective(TOrderOperatorLog record) {
        return tOrderOperatorLogDao.insertSelective(record);
    }

    @Override
    public TOrderOperatorLog selectByPrimaryKey(Integer id) {
        return tOrderOperatorLogDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TOrderOperatorLog record) {
        return tOrderOperatorLogDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TOrderOperatorLog record) {
        return tOrderOperatorLogDao.updateByPrimaryKey(record);
    }

    @Override
    public List<TOrderOperatorLog> historyList(String orderSn) {
        return tOrderOperatorLogDao.historyList(orderSn);
    }
}