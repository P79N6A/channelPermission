package com.haier.eis.service;

import java.util.List;
import java.util.Map;

import com.haier.eis.model.InventoryListOutput;
import com.haier.eis.model.LesStockTransQueue;


public interface EisLesStockTransQueueService {
    LesStockTransQueue getByLesBillNo( String billNo);

    Integer insert(LesStockTransQueue stockTransQueues);

    Integer batchInsert(List<LesStockTransQueue> stockTransQueues);

    List<Integer> getNotProcessedLesBatchId();

    List<Integer> getNotConvertedLesBatchId();

    Integer update(LesStockTransQueue stockTransQueue);

    Integer updateAfterConvert(LesStockTransQueue stockTransQueue);

    List<LesStockTransQueue> getNotProcessByBatchId(Integer batchId);

    List<LesStockTransQueue> getNotConvertedByBatchId(Integer batchId);

    LesStockTransQueue getById(Integer id);

    /**
     * 获取指定状态的队列
     * @param status
     * @param topx 数量
     * @return
     */
    List<LesStockTransQueue> getByStatus( Integer status, Integer topx);

    List<LesStockTransQueue> getForFinance();

    Integer updateAfterDoFinance(Integer id);

    Integer updateStatus(Integer id,Integer status, Integer oldStatus,String msg);

    Integer updateSKU(Integer id,String sku);

    /**
     * 指定是否可以手工修正渠道
     * @param id
     * @param status
     * @param oldStatus
     * @param msg
     * @param isManualSetChannel
     * @return
     */
    Integer updateIsManualSetChannel(Integer id,Integer status,
                                     Integer oldStatus,
                                    String msg,
                                   Integer isManualSetChannel);

    Integer updateChannel(Integer id, String channel,
                         String user);

    LesStockTransQueue getByOrderSn(String orderSn,String sku);

    List<InventoryListOutput> queryLesStockTrans(Map<String, Object> paramMap);

    /**
     * 获得条数
     * 
     * @return
     */
    public int getRowCnts();

    List<LesStockTransQueue> getLesStockTransQueueByCorderSn(String corderSn);

    Integer delete(Integer id);

    List<LesStockTransQueue> getBySnSku(String orderSn, String sku);

    Integer updateLesStatus(Map<String, Object> paramMap);
    
    List<LesStockTransQueue> getDelayTrans(int page,int size);
    
    int getDelayTransCount();
}
