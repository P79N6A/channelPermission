/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.eis.services;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.SkuStockRelationDao;
import com.haier.eis.model.SkuStockRelation;
import com.haier.eis.service.SkuStockRelationService;

@Service
public class SkuStockRelationServiceImpl implements SkuStockRelationService {

    private static final Logger log = LogManager.getLogger(SkuStockRelationServiceImpl.class);

    @Autowired
    private SkuStockRelationDao skuStockRelationDao;
//    


    /**
     *
     * @param getTime
     * @return
     */
    @Override
    public List<SkuStockRelation> get3WskuToNum(String getTime) {
        return skuStockRelationDao.get3WskuToNum(getTime);
    }

}
