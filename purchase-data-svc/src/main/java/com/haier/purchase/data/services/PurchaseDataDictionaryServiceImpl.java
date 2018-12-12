/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.DataDictionaryDao;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.service.PurchaseDataDictionaryService;



/**
 *                       
 * @Filename: DataDictionaryDao.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */
@Service
public class PurchaseDataDictionaryServiceImpl implements PurchaseDataDictionaryService{

	@Autowired
	DataDictionaryDao dataDictionaryDao;
    /**
     * 数据字典分类检索
     * @param params
     * @return
     */
	@Override
    public List<DataDictionary> getByValueSetId(Map<String, Object> params){
		return dataDictionaryDao.getByValueSetId(params);
    }
}
