package com.haier.eop.data.services;

import com.haier.eop.data.dao.StoreCodeMappingDao;
import com.haier.eop.data.model.StoreCodeMapping;
import com.haier.eop.data.service.StoreCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreCodeServiceImpl implements StoreCodeService{

    @Autowired
    private StoreCodeMappingDao storeCodeMappingDao;

    @Override
    public StoreCodeMapping findByHaierStoreCode(String haierStoreCode) {
        return storeCodeMappingDao.findByHaierStoreCode(haierStoreCode);
    }

    @Override
    public StoreCodeMapping findByStoreCode(String StoreCode) {
        return storeCodeMappingDao.findByHaierStoreCode(StoreCode);
    }

    @Override
    public Map<String, Object> getStoreCodeByPage(Map<String, Object> params) {
        List<StoreCodeMapping> result = storeCodeMappingDao.getStoreCode(params);
        //获取总条数
        int resultcount = storeCodeMappingDao.getRowCnts();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);
        return retMap;
    }

    @Override
    public StoreCodeMapping findByCainiaoStoreCode(String cainiaoStoreCode) {
        return storeCodeMappingDao.findByCainiaoStoreCode(cainiaoStoreCode);
    }

    @Override
    public int insertStoreCode(StoreCodeMapping storeCode) {
        return storeCodeMappingDao.insertStoreCode(storeCode);
    }

    @Override
    public int updateStoreCode(StoreCodeMapping storeCode) {
        return storeCodeMappingDao.updateStoreCode(storeCode);
    }

    @Override
    public StoreCodeMapping findByStoreCodeId(Integer id) {
        return storeCodeMappingDao.findByStoreCodeId(id);
    }

    @Override
    public int deleteStoreCodeById(int id) {
        return storeCodeMappingDao.deleteStoreCodeById(id);
    }
}
