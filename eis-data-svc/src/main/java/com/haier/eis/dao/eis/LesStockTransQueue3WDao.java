package com.haier.eis.dao.eis;

import java.util.List;
import java.util.Map;

import com.haier.eis.model.InventoryListOutput;
import com.haier.eis.model.LesStockTransQueue3W;
import org.apache.ibatis.annotations.Param;


public interface LesStockTransQueue3WDao {
    LesStockTransQueue3W getByLesBillNo(@Param("lesBillNo") String billNo);

    Integer insert(LesStockTransQueue3W stockTransQueues);

    Integer batchInsert(@Param("stockTransQueues") List<LesStockTransQueue3W> stockTransQueues);

    List<Integer> getNotProcessedLesBatchId();

    List<Integer> getNotConvertedLesBatchId();

    Integer update(LesStockTransQueue3W stockTransQueue);

    Integer updateAfterConvert(LesStockTransQueue3W stockTransQueue);

    List<LesStockTransQueue3W> getNotProcessByBatchId(@Param("les_batch_id") Integer batchId);

    List<LesStockTransQueue3W> getNotConvertedByBatchId(@Param("les_batch_id") Integer batchId);

    LesStockTransQueue3W getById(@Param("id") Integer id);

    /**
     * 获取指定状态的队列
     * @param status
     * @param topx 数量
     * @return
     */
    List<LesStockTransQueue3W> getByStatus(@Param("status") Integer status,
                                         @Param("topx") Integer topx);

    List<LesStockTransQueue3W> getForFinance();

    Integer updateAfterDoFinance(@Param("id") Integer id);

    Integer updateStatus(@Param("id") Integer id, @Param("status") Integer status,
                         @Param("oldStatus") Integer oldStatus, @Param("msg") String msg);

    Integer updateSKU(@Param("id") Integer id, @Param("sku") String sku);

    /**
     * 指定是否可以手工修正渠道
     * @param id
     * @param status
     * @param oldStatus
     * @param msg
     * @param isManualSetChannel
     * @return
     */
    Integer updateIsManualSetChannel(@Param("id") Integer id, @Param("status") Integer status,
                                     @Param("oldStatus") Integer oldStatus,
                                     @Param("msg") String msg,
                                     @Param("isManualSetChannel") Integer isManualSetChannel);

    Integer updateChannel(@Param("id") Integer id, @Param("channel") String channel,
                          @Param("user") String user);

    LesStockTransQueue3W getByOrderSn(@Param("corder_sn") String orderSn, @Param("sku") String sku);

    List<InventoryListOutput> queryLesStockTrans(Map<String, Object> paramMap);

    /**
     * 获得条数
     * 
     * @return
     */
    public int getRowCnts();

    List<LesStockTransQueue3W> getLesStockTransQueue3WByCorderSn(@Param("corderSn") String corderSn);

    Integer delete(@Param("id") Integer id);

    List<LesStockTransQueue3W> getBySnSku(@Param("corder_sn") String orderSn,
                                        @Param("sku") String sku);

    Integer updateLesStatus(Map<String, Object> paramMap);
    
    
    String querySecCode(String corderSn,String sku);//根据网单号 和sku查询库位
}
