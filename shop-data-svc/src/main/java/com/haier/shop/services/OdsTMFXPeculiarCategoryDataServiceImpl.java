package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.settleCenter.OdsTMFXPeculiarCategoryDao;
import com.haier.shop.model.OdsTMFXPeculiarCategory;
import com.haier.shop.service.OdsTMFXPeculiarCategoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OdsTMFXPeculiarCategoryDataServiceImpl implements OdsTMFXPeculiarCategoryDataService {

    @Autowired
    OdsTMFXPeculiarCategoryDao odsTMFXPeculiarCategoryDao;

    @Override
    public int deleteByPrimaryKey(String id) {

        return odsTMFXPeculiarCategoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OdsTMFXPeculiarCategory record) {
        return odsTMFXPeculiarCategoryDao.insertSelective(record);
    }

    @Override
    public OdsTMFXPeculiarCategory selectByPrimaryKey(String id) {
        return odsTMFXPeculiarCategoryDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OdsTMFXPeculiarCategory record) {
        return odsTMFXPeculiarCategoryDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public OdsTMFXPeculiarCategory queryBySkuCategory(OdsTMFXPeculiarCategory odsTMFXPeculiarCategory) {
        return odsTMFXPeculiarCategoryDao.queryBySkuCategory(odsTMFXPeculiarCategory);
    }

    @Override
    public List<OdsTMFXPeculiarCategory> queryPeculiarCategoryAllList() {
        return odsTMFXPeculiarCategoryDao.queryPeculiarCategoryAllList();
    }

    @Override
    public JSONObject paging(OdsTMFXPeculiarCategory param, PagerInfo page) {
        JSONObject result = new JSONObject();
        List<OdsTMFXPeculiarCategory> odsTMFXPeculiarCategoryList = odsTMFXPeculiarCategoryDao.paging(param,page);
        if(odsTMFXPeculiarCategoryList == null){
            odsTMFXPeculiarCategoryList = new ArrayList<OdsTMFXPeculiarCategory>();
        }
        result.put("rows", odsTMFXPeculiarCategoryList);
        result.put("total", odsTMFXPeculiarCategoryDao.count(param));
        return result;
    }

    @Override
    public JSONObject export(OdsTMFXPeculiarCategory map) {
        JSONObject result = new JSONObject();
        List<OdsTMFXPeculiarCategory> list = odsTMFXPeculiarCategoryDao.export(map);
        if(list == null){
            list = new ArrayList<>();
        }
        result.put("rows", list);
        return result;
    }

    @Override
    public int delBatch(List<String> ids) {
        return odsTMFXPeculiarCategoryDao.delBatch(ids);
    }
}
