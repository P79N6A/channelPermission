package com.haier.eis.dao.eis;

import com.haier.common.PagerInfo;
import com.haier.eis.model.InventoryListOutput;
import com.haier.eis.model.LesStockTransQueue;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface LesStockTransQueueDao {
    LesStockTransQueue getByLesBillNo(@Param("lesBillNo") String billNo);

    Integer insert(LesStockTransQueue stockTransQueues);

    Integer batchInsert(@Param("stockTransQueues") List<LesStockTransQueue> stockTransQueues);

    List<Integer> getNotProcessedLesBatchId();

    List<Integer> getNotConvertedLesBatchId();

    Integer update(LesStockTransQueue stockTransQueue);

    Integer updateAfterConvert(LesStockTransQueue stockTransQueue);

    List<LesStockTransQueue> getNotProcessByBatchId(@Param("les_batch_id") Integer batchId);

    List<LesStockTransQueue> getNotConvertedByBatchId(@Param("les_batch_id") Integer batchId);

    LesStockTransQueue getById(@Param("id") Integer id);
    
    LesStockTransQueue queryCorderSn(@Param("orderSn")String orderSn,@Param("mark")String mark,@Param("charg")String charg); //根据退货单号查询入库信息

    /**
     * 获取指定状态的队列
     * @param status
     * @param topx 数量
     * @return
     */
    List<LesStockTransQueue> getByStatus(@Param("status") Integer status,
                                         @Param("topx") Integer topx);

    List<LesStockTransQueue> getForFinance();

    List<LesStockTransQueue> getForFinanceByParams(Map<String,Object> params);

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

    LesStockTransQueue getByOrderSn(@Param("corder_sn") String orderSn, @Param("sku") String sku);

    List<InventoryListOutput> queryLesStockTrans(Map<String, Object> paramMap);

    /**
     * 获得条数
     * 
     * @return
     */
    public int getRowCnts();

    List<LesStockTransQueue> getLesStockTransQueueByCorderSn(@Param("corderSn") String corderSn);

    Integer delete(@Param("id") Integer id);

    List<LesStockTransQueue> getBySnSku(@Param("corder_sn") String orderSn,
                                        @Param("sku") String sku);

    Integer updateLesStatus(Map<String, Object> paramMap);
    
    
    List<LesStockTransQueue> getDelayTrans(@Param("start") int page, @Param("size") int size);
    
    int getDelayTransCount();

    /**
     * 根据单号查询数据
     * @param lineNumList
     * @return
     */
	List<LesStockTransQueue> getByLineNums(@Param("lineNumList") List<String> lineNumList);

    LesStockTransQueue findInStockCode(@Param("corderSn") String corderSn);

    List<LesStockTransQueue> getLesStockTransQueueByCorderSnBillType(@Param("corderSn") String corderSn);

    List<LesStockTransQueue> getPushSapResult(@Param("startTime") String startTime, @Param("endTime") String endTime,
        @Param("corderSn")  String corderSn, @Param("status") String status,
        @Param("billType") String billType, @Param("pager") PagerInfo pagerInfo);

    Integer getPushSapResultCount(@Param("startTime") String startTime, @Param("endTime") String endTime,
        @Param("corderSn")  String corderSn, @Param("status") String status,
        @Param("billType") String billType);
}
