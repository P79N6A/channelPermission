package com.haier.svc.service;


import com.alibaba.fastjson.JSONObject;
import com.haier.stock.model.StorageLocMgt;

import java.util.List;

/**
 * @author 甄硕鑫
 */
public interface StorageLocMgtService {
    JSONObject queryStorageLocMgt(StorageLocMgt storageLocMgt);

    List<StorageLocMgt> queryStorageLocMgtExcel(StorageLocMgt storageLocMgt);

}
