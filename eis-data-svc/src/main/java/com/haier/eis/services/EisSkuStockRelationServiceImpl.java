package com.haier.eis.services;

import com.haier.eis.dao.eis.SkuStockRelationDao;
import com.haier.eis.model.SkuStockRelation;
import com.haier.eis.service.EisSkuStockRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EisSkuStockRelationServiceImpl implements EisSkuStockRelationService {
    @Autowired
    private SkuStockRelationDao skuStockRelationDao;
    @Override
    public List<SkuStockRelation> queryStockSyncLogList(List skuList, String addTimeStart, List scodeList){
    return skuStockRelationDao.queryStockSyncLogList(skuList, addTimeStart, scodeList);
    }
    @Override
    public void insertList(List<SkuStockRelation> list){
         skuStockRelationDao.insertList(list);
    }
}
