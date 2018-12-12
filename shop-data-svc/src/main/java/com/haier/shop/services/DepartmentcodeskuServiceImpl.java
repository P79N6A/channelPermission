package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopread.DepartMentCodeSkuReadDao;
import com.haier.shop.dao.shopwrite.DepartMentCodeSkuWriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.DepartMentCodeSku;
import com.haier.shop.service.DepartmentcodeskuService;


@Service
public class DepartmentcodeskuServiceImpl implements DepartmentcodeskuService {
    @Autowired
    DepartMentCodeSkuWriteDao departMentCodeSkuWriteDao;

    @Autowired
    DepartMentCodeSkuReadDao departMentCodeSkuReadDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return departMentCodeSkuWriteDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DepartMentCodeSku record) {
        // TODO Auto-generated method stub
        return departMentCodeSkuWriteDao.insert(record);
    }

    @Override
    public int insertSelective(DepartMentCodeSku record) {
        // TODO Auto-generated method stub
        return departMentCodeSkuWriteDao.insertSelective(record);
    }

    @Override
    public DepartMentCodeSku selectByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return departMentCodeSkuReadDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(DepartMentCodeSku record) {
        // TODO Auto-generated method stub
        return departMentCodeSkuWriteDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(DepartMentCodeSku record) {
        // TODO Auto-generated method stub
        return departMentCodeSkuWriteDao.updateByPrimaryKey(record);
    }

    @Override
    public List<DepartMentCodeSku> selectDepartMentCodeSku(String sku) {
        // TODO Auto-generated method stub
        return departMentCodeSkuReadDao.selectdepartmentcodesku(sku);
    }

}