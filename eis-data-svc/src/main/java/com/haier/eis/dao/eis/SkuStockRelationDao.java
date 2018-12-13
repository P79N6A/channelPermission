package com.haier.eis.dao.eis;


import com.haier.eis.model.SkuStockRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hanhaoyang@ehaier.com on 2017/11/27 0027.
 */
@Repository
public interface SkuStockRelationDao {

    public List<SkuStockRelation> queryStockSyncLogList(
            @Param("skuList") List skuList,
            @Param("addTimeStart") String addTimeStart,
            @Param("scodeList") List scodeList
    );

    public void insertList(List<SkuStockRelation> list);

	public List<SkuStockRelation> get3WskuToNum(String getTime);
}
