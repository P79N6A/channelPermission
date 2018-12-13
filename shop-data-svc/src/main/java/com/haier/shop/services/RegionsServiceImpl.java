package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.RegionsReadDao;
import com.haier.shop.dao.shopwrite.RegionsWriteDao;
import com.haier.shop.model.Regions;
import com.haier.shop.service.RegionsService;

@Service
public class RegionsServiceImpl implements RegionsService{

    @Autowired
    RegionsWriteDao regionsWriteDao;
    @Autowired
    RegionsReadDao regionsReadDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return regionsWriteDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Regions record) {
        return regionsWriteDao.insert(record);
    }

    @Override
    public int insertSelective(Regions record) {
        return regionsWriteDao.insertSelective(record);
    }

    @Override
    public Regions selectByPrimaryKey(Integer id) {
        return regionsReadDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Regions record) {
        return regionsWriteDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Regions record) {
        return regionsWriteDao.updateByPrimaryKey(record);
    }

    @Override
    public int updateByParentId(Regions record) {
        return regionsWriteDao.updateByParentId(record);
    }

    @Override
    public List<Regions> selectByParentId(Integer parentid) {
        return regionsReadDao.selectByParentId(parentid);
    }

    @Override
    public List<Regions> selectByParentPatchId(Integer patchId) {
        return regionsReadDao.selectByParentPatchId(patchId);
    }

    @Override
    public List<Regions> selectregionprovince(int id) {
        return regionsReadDao.selectregionprovince(id);
    }

    @Override
    public List<Regions> selectregioncity(int id) {
        return regionsReadDao.selectregioncity(id);
    }

    @Override
    public List<Regions> selectregioncounty(int id) {
        return regionsReadDao.selectregioncounty(id);
    }

    @Override
    public int getRegionType(int id) {
        return regionsReadDao.getRegionType(id);
    }

    @Override
    public String selectPatchId(int id) {
        return regionsReadDao.selectPatchId(id);
    }

    @Override
    public String selectregionName(int id) {
        return regionsReadDao.selectregionName(id);
    }

    @Override
    public List<Regions> getRegionsAll() {
        return regionsReadDao.getRegionsAll();
    }

    @Override
    public Regions get(Integer id) {
        return regionsReadDao.get(id);
    }

    @Override
    public List<Regions> getByIds(String ids) {
        return regionsReadDao.getByIds(ids);
    }

	@Override
	public Regions getRegions(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return regionsReadDao.getRegions(params);
	}

	@Override
	public String selectCode(String id) {
		// TODO Auto-generated method stub
		return regionsReadDao.selectCode(id);
	}

    @Override
    public List<Regions> Listf(Regions entity, int start, int rows) {
        try {
            return regionsReadDao.Listf(entity,start,rows);
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public int getPagerCountS(Regions entity) {
        try {
            return regionsReadDao.getPagerCountS(entity);

        }catch (Exception e){

        }
        return 0;
    }
    @Override
    public List<Regions> getRegion(int id){
        return regionsReadDao.getRegion(id);
    }
}