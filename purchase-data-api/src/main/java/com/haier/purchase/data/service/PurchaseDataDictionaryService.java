/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.DataDictionary;



/**
 *                       
 * @Filename: DataDictionaryDao.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */

public interface PurchaseDataDictionaryService {

    /**
     * 数据字典分类检索
     * @param params
     * @return
     */
    public List<DataDictionary> getByValueSetId(Map<String, Object> params);
}
