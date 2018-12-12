package com.haier.eis.service;




import com.haier.eis.model.SkuStockRelation;

import java.util.List;

/**
 * Created by hanhaoyang@ehaier.com on 2017/11/27 0027.
 */

public interface EisSkuStockRelationService {

    public List<SkuStockRelation> queryStockSyncLogList(
             List skuList,
             String addTimeStart,
            List scodeList
    );

    public void insertList(List<SkuStockRelation> list);
}
