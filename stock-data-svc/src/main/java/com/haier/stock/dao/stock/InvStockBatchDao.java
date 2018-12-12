package com.haier.stock.dao.stock;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvStockBatch;

public interface InvStockBatchDao {

    /**
     * 获取最先的可出库记录
     * @param sku
     * @param secCode
     * @return
     */
    InvStockBatch getFrontAvailable(@Param("sku") String sku, @Param("sec_code") String secCode);

    /**
     * 获取最大的批次号
     * @return
     */
    String getLastBatchNum();

    List<InvStockBatch> queryInvStockBatch ( @Param("lastBatchId") Integer lastBatchId, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize );

    List<InvStockBatch> queryInvReleaseStock (@Param("now") Date now, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize );
    Integer getSumStockBySku ( @Param("sku") String sku, @Param("sec_code") String sec_code, @Param("id") Integer id);


    Integer insert(InvStockBatch stockBatch);
    
    Integer updateQty(InvStockBatch stockBatch);

    Integer update(InvStockBatch stockBatch);
}
