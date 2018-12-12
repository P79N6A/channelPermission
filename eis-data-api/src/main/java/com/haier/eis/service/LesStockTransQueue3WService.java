package com.haier.eis.service;

import com.haier.eis.model.InventoryListOutput;
import com.haier.eis.model.LesStockTransQueue3W;

import java.util.List;
import java.util.Map;



public interface LesStockTransQueue3WService {
    LesStockTransQueue3W getByLesBillNo(String billNo);

    Integer insert(LesStockTransQueue3W stockTransQueues);

    Integer batchInsert( List<LesStockTransQueue3W> stockTransQueues);

    List<Integer> getNotProcessedLesBatchId();

    List<Integer> getNotConvertedLesBatchId();

    Integer update(LesStockTransQueue3W stockTransQueue);

    Integer updateAfterConvert(LesStockTransQueue3W stockTransQueue);

    List<LesStockTransQueue3W> getNotProcessByBatchId(  Integer batchId);

    List<LesStockTransQueue3W> getNotConvertedByBatchId(  Integer batchId);

    LesStockTransQueue3W getById( Integer id);

    /**
     * 获取指定状态的队列
     * @param status
     * @param topx 数量
     * @return
     */
    List<LesStockTransQueue3W> getByStatus(  Integer status,
                                           Integer topx);

    List<LesStockTransQueue3W> getForFinance();

    Integer updateAfterDoFinance( Integer id);

    Integer updateStatus(  Integer id,   Integer status,
                         Integer oldStatus,   String msg);

    Integer updateSKU( Integer id,  String sku);

    /**
     * 指定是否可以手工修正渠道
     * @param id
     * @param status
     * @param oldStatus
     * @param msg
     * @param isManualSetChannel
     * @return
     */
    Integer updateIsManualSetChannel( Integer id,  Integer status,
                                      Integer oldStatus,
                                     String msg,
                                     Integer isManualSetChannel);

    Integer updateChannel(  Integer id,   String channel,
                           String user);

    LesStockTransQueue3W getByOrderSn(  String orderSn,  String sku);

    List<InventoryListOutput> queryLesStockTrans(Map<String, Object> paramMap);

    /**
     * 获得条数
     * 
     * @return
     */
    public int getRowCnts();

    List<LesStockTransQueue3W> getLesStockTransQueue3WByCorderSn(  String corderSn);

    Integer delete( Integer id);

    List<LesStockTransQueue3W> getBySnSku( String orderSn,
                                         String sku);

    Integer updateLesStatus(Map<String, Object> paramMap);
    String querySecCode(String corderSn,String sku);//根据网单号 和sku查询库位
}
