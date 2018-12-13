package com.haier.eop.services;

import com.haier.eop.data.model.StoreCodeMapping;
import com.haier.eop.data.service.StoreCodeService;
import com.haier.eop.service.EopStoreCodeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EopStoreCodeServiceImpl implements EopStoreCodeService{

    private static final Logger logger = LogManager.getLogger(EopStoreCodeServiceImpl.class);

    @Autowired
    private StoreCodeService storeCodeService;

    @Override
    public Map<String, Object> findStoreCodeList(Map<String, Object> params) {
        Map<String, Object> retMap = storeCodeService.getStoreCodeByPage(params);
        return retMap;
    }

    @Override
    public StoreCodeMapping findByCainiaoStoreCode(String cainiaoStoreCode) {
        return storeCodeService.findByCainiaoStoreCode(cainiaoStoreCode);
    }

    @Override
    public int createStoreCode(StoreCodeMapping storeCode) {
        return storeCodeService.insertStoreCode(storeCode);
    }

    @Override
    public int updateStoreCode(StoreCodeMapping storeCode) {
        int row =0;
        StoreCodeMapping storeCodeMapping = storeCodeService.findByStoreCodeId(storeCode.getId());
        StoreCodeMapping cainiaoStoreCode =  storeCodeService.findByCainiaoStoreCode(storeCode.getCainiaoStoreCode());
        if(storeCodeMapping==null){
            row = -1;
            return row;
        }else if(cainiaoStoreCode!=null){
            if(!cainiaoStoreCode.getId().equals(storeCodeMapping.getId())) {
                row = -2;
                return row;
            }
        }

        return storeCodeService.updateStoreCode(storeCode);

    }

    @Override
    public int deleteStoreCodeById(String id) {
        int row =0;
        StoreCodeMapping storeCodeMapping = storeCodeService.findByStoreCodeId(Integer.parseInt(id));
        if(storeCodeMapping==null){
            row = -1;
            return row;
        }
        return storeCodeService.deleteStoreCodeById(Integer.parseInt(id));
    }
}
