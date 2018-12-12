/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.vehicle.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.service.PurchaseDataDictionaryService;
import com.haier.vehicle.service.VehicleDataDictionaryService;



/**
 *                       
 * @Filename: DataDictionaryDao.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */
@Service
public class VehicleDataDictionaryServiceImpl implements VehicleDataDictionaryService{
	
	@Autowired
	PurchaseDataDictionaryService purchaseDataDictionaryService;
    /**
     * 数据字典分类检索
     * @param params
     * @return
     */
	@Override
    public List<DataDictionary> getByValueSetId(Map<String, Object> params){
		return purchaseDataDictionaryService.getByValueSetId(params);
    }
}
