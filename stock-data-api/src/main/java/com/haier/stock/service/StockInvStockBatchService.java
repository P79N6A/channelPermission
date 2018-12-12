package com.haier.stock.service;

import java.util.Date;
import java.util.List;

import com.haier.stock.model.InvStockBatch;

public interface StockInvStockBatchService {
	 /**
     * 获取最先的可出库记录
     * @param sku
     * @param secCode
     * @return
     */
 public  InvStockBatch getFrontAvailable( String sku,  String secCode);

    /**
     * 获取最大的批次号
     * @return
     */
 public  String getLastBatchNum();

 public  List<InvStockBatch> queryInvStockBatch ( Integer lastBatchId,int startIndex,  int pageSize );

 public  List<InvStockBatch> queryInvReleaseStock ( Date now, int startIndex, int pageSize );
 public  Integer getSumStockBySku ( String sku, String sec_code, Integer id);


 public  Integer insert(InvStockBatch stockBatch);
    
 public  Integer updateQty(InvStockBatch stockBatch);

 public Integer update(InvStockBatch stockBatch);
}
