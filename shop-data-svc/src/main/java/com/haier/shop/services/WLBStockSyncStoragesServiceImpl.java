package com.haier.shop.services;

import com.haier.shop.dao.shopread.WLBStockSyncStoragesReadDao;
import com.haier.shop.dao.shopwrite.WLBStockSyncStoragesWriteDao;
import com.haier.shop.model.Wlbstocksyncstorages;
import com.haier.shop.model.WlbstocksyncstoragesVo;
import com.haier.shop.service.WLBStockSyncStoragesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/7/26 15:47
 */
@Service
public class WLBStockSyncStoragesServiceImpl implements WLBStockSyncStoragesService {


    @Autowired
    WLBStockSyncStoragesReadDao wlbStockSyncStoragesReadDao;

    @Autowired
    WLBStockSyncStoragesWriteDao wlbStockSyncStoragesWriteDao;
    @Override
    public Map<String, Object> getStockSyncStorageList(Map<String, Object> params) {
        List<WlbstocksyncstoragesVo> netPointListList =wlbStockSyncStoragesReadDao.getStockSyncStorageList(params);
        //查询总条数
        int resultCount = wlbStockSyncStoragesReadDao.getRowCnts();

        Map<String, Object> retMap = new HashMap<>();

        retMap.put("total", resultCount);
        retMap.put("rows", netPointListList);
        return retMap;
    }

    @Override
    public Integer insert(Wlbstocksyncstorages wlbstocksyncstorages) {
        return wlbStockSyncStoragesWriteDao.insert(wlbstocksyncstorages);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return wlbStockSyncStoragesWriteDao.deleteByPrimaryKey(id);
    }
}
