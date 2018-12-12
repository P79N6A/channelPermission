package com.haier.distribute.data.services;

import java.util.List;
import java.util.Map;

import com.haier.distribute.data.dao.distribute.RegionsDao;
import com.haier.distribute.data.model.Regions;
import com.haier.distribute.data.service.RegionsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionsServiceImpl implements RegionsService{

    @Autowired
    RegionsDao regionsDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return regionsDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Regions record) {
        return regionsDao.insert(record);
    }

    @Override
    public int insertSelective(Regions record) {
        return regionsDao.insertSelective(record);
    }

    @Override
    public Regions selectByPrimaryKey(Integer id) {
        return regionsDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Regions record) {
        return regionsDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Regions record) {
        return regionsDao.updateByPrimaryKey(record);
    }

    @Override
    public List<Regions> selectByParentId(Integer parentid) {
        return regionsDao.selectByParentId(parentid);
    }

    @Override
    public List<Regions> selectByParentPatchId(Integer patchId) {
        return regionsDao.selectByParentPatchId(patchId);
    }

    @Override
    public List<Regions> selectregionprovince(int id) {
        return regionsDao.selectregionprovince(id);
    }

    @Override
    public List<Regions> selectregioncity(int id) {
        return regionsDao.selectregioncity(id);
    }

    @Override
    public List<Regions> selectregioncounty(int id) {
        return regionsDao.selectregioncounty(id);
    }

    @Override
    public int getRegionType(int id) {
        return regionsDao.getRegionType(id);
    }

    @Override
    public String selectPatchId(int id) {
        return regionsDao.selectPatchId(id);
    }

    @Override
    public String selectregionName(int id) {
        return regionsDao.selectregionName(id);
    }

    @Override
    public List<Regions> getRegionsAll() {
        return regionsDao.getRegionsAll();
    }

    @Override
    public Regions get(Integer id) {
        return regionsDao.get(id);
    }

    @Override
    public List<Regions> getByIds(String ids) {
        return regionsDao.getByIds(ids);
    }
    
    @Override
    public Regions getRegions(Map<String, Object> params) {
        return regionsDao.getRegions(params);
    }
}