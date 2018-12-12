package com.haier.distribute.data.services;


import com.haier.distribute.data.dao.distribute.TOrderLogisticsDao;
import com.haier.distribute.data.model.TOrderLogistics;
import com.haier.distribute.data.service.TOrderLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TOrderLogisticsServiceImpl implements TOrderLogisticsService {
    @Autowired
    TOrderLogisticsDao tOrderLogisticsDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tOrderLogisticsDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TOrderLogistics record) {
        return tOrderLogisticsDao.insert(record);
    }

    @Override
    public int insertSelective(TOrderLogistics record) {
        return tOrderLogisticsDao.insertSelective(record);
    }

    @Override
    public TOrderLogistics selectByPrimaryKey(Integer id) {
        return tOrderLogisticsDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TOrderLogistics record) {
        return tOrderLogisticsDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TOrderLogistics record) {
        return tOrderLogisticsDao.updateByPrimaryKey(record);
    }

    @Override
    public List<TOrderLogistics> checkExpressNo(String expressNo, String orderSn) {
        return tOrderLogisticsDao.checkExpressNo(expressNo, orderSn);
    }

    @Override
    public int editExpressNo(String expressNo, String orderSn) {
        return tOrderLogisticsDao.editExpressNo(expressNo, orderSn);
    }
}