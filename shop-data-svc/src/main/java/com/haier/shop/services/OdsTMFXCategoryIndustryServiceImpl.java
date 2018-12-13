package com.haier.shop.services;

import com.haier.shop.dao.settleCenter.OdsTMFXCategoryIndustryDao;
import com.haier.shop.model.OdsTMFXCategoryIndustry;
import com.haier.shop.service.OdsTMFXCategoryIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xupeng on 2018/5/24.
 */
@Service
public class OdsTMFXCategoryIndustryServiceImpl implements OdsTMFXCategoryIndustryService {

    @Autowired
    OdsTMFXCategoryIndustryDao odsTMFXCategoryIndustryDao;

    @Override
    public List<OdsTMFXCategoryIndustry> queryOdsTMFXCategoryIndustry(OdsTMFXCategoryIndustry model) {
        return odsTMFXCategoryIndustryDao.list(model);
    }

    @Override
    public Map<String, Object> queryOdsTMFXCategoryIndustry(Map<String, Object> map) throws Exception {
        List<Map> paging = odsTMFXCategoryIndustryDao.paging(map);
        Long count = odsTMFXCategoryIndustryDao.count(map);
        Map<String, Object> result  = new HashMap<String, Object>();
        result.put("paging", paging);
        result.put("count", count);
        return result;

    }

    @Override
    public List<OdsTMFXCategoryIndustry> queryOdsTMFXCategoryIndustryById(String id) {
        return odsTMFXCategoryIndustryDao.load(id);
    }

    @Override
    public void create(OdsTMFXCategoryIndustry model) {
        odsTMFXCategoryIndustryDao.create(model);
    }

    @Override
    public void creates(List<OdsTMFXCategoryIndustry> modelList) {
        odsTMFXCategoryIndustryDao.creates(modelList);
    }

    @Override
    public void updateOdsTMFXCategoryIndustry(OdsTMFXCategoryIndustry model) {
        odsTMFXCategoryIndustryDao.update(model);
    }
}
