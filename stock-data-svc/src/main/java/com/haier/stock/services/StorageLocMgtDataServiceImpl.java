package com.haier.stock.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.stock.dao.stock.StorageLocMgtDao;
import com.haier.stock.model.StorageLocMgt;
import com.haier.stock.service.StorageLocMgtDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageLocMgtDataServiceImpl implements StorageLocMgtDataService {
    @Autowired
    private StorageLocMgtDao storageLocMgtDao;

    @Override
    public JSONObject queryStorageLocMgt(StorageLocMgt storageLocMgt) {
        JSONObject result = new JSONObject();
        storageLocMgt.setStart((storageLocMgt.getPage()-1)*100);
        storageLocMgt.setSize(storageLocMgt.getRows());
        result.put("total", storageLocMgtDao.countStorageLocMgtWithLike(storageLocMgt));
        result.put("rows", storageLocMgtDao.getInvWareHouseList(storageLocMgt));
        return result;
    }

    @Override
    public List<StorageLocMgt> queryStorageLocMgtExcel(StorageLocMgt storageLocMgt) {

        return storageLocMgtDao.queryStorageLocMgtExcel(storageLocMgt);
    }
}
