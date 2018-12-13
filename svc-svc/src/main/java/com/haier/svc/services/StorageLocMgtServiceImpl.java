package com.haier.svc.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.stock.model.StorageLocMgt;
import com.haier.stock.service.StorageLocMgtDataService;
import com.haier.svc.service.StorageLocMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageLocMgtServiceImpl implements StorageLocMgtService{
    @Autowired
    private StorageLocMgtDataService storageLocMgtDataService;

    @Override
    public JSONObject queryStorageLocMgt(StorageLocMgt storageLocMgt) {
        return storageLocMgtDataService.queryStorageLocMgt(storageLocMgt);
    }

    @Override
    public List<StorageLocMgt> queryStorageLocMgtExcel(StorageLocMgt storageLocMgt) {
        return storageLocMgtDataService.queryStorageLocMgtExcel(storageLocMgt);
    }
}
