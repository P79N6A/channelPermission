package com.haier.svc.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.DataDictionary;

public interface DataDictionaryService {

	/**
     * 数据字典分类检索
     * @param params
     * @return
     */
    public ServiceResult<List<DataDictionary>> getByValueSetId(Map<String, Object> params);
}
