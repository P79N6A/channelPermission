package com.haier.stock.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.stock.model.StorageLocMgt;

import java.util.List;

public interface StorageLocMgtDataService {

    JSONObject queryStorageLocMgt(StorageLocMgt storageLocMgt);

    List<StorageLocMgt> queryStorageLocMgtExcel(StorageLocMgt storageLocMgt);

}
