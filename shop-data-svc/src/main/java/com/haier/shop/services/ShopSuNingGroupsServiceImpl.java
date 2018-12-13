package com.haier.shop.services;


import com.haier.shop.dao.shopread.SuNingGroupsReadDao;
import com.haier.shop.dao.shopwrite.SuNingGroupsWriteDao;
import com.haier.shop.model.SuningGroups;
import com.haier.shop.service.ShopSuningGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopSuNingGroupsServiceImpl implements ShopSuningGroupsService {
    @Autowired
    SuNingGroupsReadDao suNingGroupsReadDao;
    @Autowired
    SuNingGroupsWriteDao suNingGroupsWriteDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return suNingGroupsWriteDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SuningGroups record) {
        return  suNingGroupsWriteDao.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(SuningGroups record) {
        return suNingGroupsWriteDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SuningGroups> Listf(SuningGroups entity, int start, int rows) {
        return suNingGroupsReadDao.Listf(entity, start, rows);
    }

    @Override
    public int getPagerCountS(SuningGroups entity) {
        return suNingGroupsReadDao.getPagerCountS(entity);
    }
}
