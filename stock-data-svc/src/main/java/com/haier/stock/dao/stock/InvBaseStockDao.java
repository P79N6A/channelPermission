package com.haier.stock.dao.stock;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.common.PagerInfo;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvBaseStockExcel;
import com.haier.stock.model.InvBaseStockLog;
import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvBaseStock;
import com.haier.stock.model.InvBaseStockEx;



public interface InvBaseStockDao {

    /**
     * 获取并锁定
     * @param sku
     * @param secCode
     * @return
     */
    InvBaseStock getForUpdate(@Param("sku") String sku, @Param("sec_code") String secCode);

    Integer insert(InvBaseStock baseStock);

    Integer update(InvBaseStock baseStock);

    Integer updateQtyForFrozen(InvBaseStock baseStock);

    /**
     * 更新stockQty 和 frozenQty
     * @param sto_id
     * @param qty
     * @param releaseQty
     * @param time
     * @return
     */
    Integer updateQty(@Param("id") Integer sto_id, @Param("qty") Integer qty,
                      @Param("releaseQty") Integer releaseQty, @Param("time") Date time);

    /**
     * 更新stockQty 
     * @param sto_id
     * @param qty
     * @param time
     * @return
     */
    Integer updateStockQty(@Param("id") Integer sto_id, @Param("qty") Integer qty,
                           @Param("time") Date time);

    /**
     * 冻结库存
     * @param stoId
     * @param frozenQty
     * @param time
     * @return
     */
    Integer frozenQty(@Param("id") Integer stoId, @Param("frozenQty") Integer frozenQty,
                      @Param("time") Date time);

    /**
     * 释放冻结库存
     * @param stoId
     * @param releaseQty
     * @param time
     * @return
     */
    Integer releaseQty(@Param("id") Integer stoId, @Param("releaseQty") Integer releaseQty,
                       @Param("time") Date time);
    
    
    /**
     * 根据物料编码和库存编码查询库存，批次10
     * @param sku
     * @param lesSecCode
     * @return
     */
    InvBaseStock queryBySkuAndLesSecCode(@Param("sku") String sku, @Param("lesSecCode") String lesSecCode);
    BaseStock get(@Param("sku") String sku, @Param("code") String code);

    BaseStock getByItemProperty(@Param("sku") String sku, @Param("code") String code,
                                @Param("itemProperty") String itemProperty);

    List<InvBaseStock> getPageByCondition(@Param("entity")InvBaseStock entity, @Param("start")int start, @Param("rows")int rows);

    long getPagerCount(@Param("entity")InvBaseStock entity);

    List<InvBaseStock> getMachinePageByCondition(@Param("entity")InvBaseStock invBaseStock, @Param("start")int start, @Param("rows")int rows);

    long getMachinePagerCount(@Param("entity")InvBaseStock invBaseStock);
    
    List<InvBaseStockEx> queryInvBaseStockList(@Param("baseStock") InvBaseStockEx invBaseStock,
            @Param("pager") PagerInfo pager);
    List<InvBaseStockExcel> queryInvBaseStockList(@Param("baseStock") Map<String,String> map, @Param("pager") PagerInfo pager);
    int getRowCnt();
    
    List<InvBaseStockEx> queryInvStockList(@Param("invStock") InvBaseStockEx invBaseStock,
            @Param("pager") PagerInfo pager);

    List<InvBaseStockLog> queryInvBaseStockLogList(@Param("log") InvBaseStockLog log,
                    @Param("pager") PagerInfo pager);
    /**
     * 查询占用数量大于总库存量的数据（后来改成查询所有数据）
     * @param invBaseStock
     * @param pager
     * @return
     */
    List<Map<String, Object>> queryByfrozenQtyGtStockQty(Map<String, Object> params);
    /**
     * 查询占用数量大于总库存量的数据数量（后来改成查询所有数据数量）
     * @param invBaseStock
     * @return
     */
    Integer queryByfrozenQtyGtStockQtyCount(Map<String, Object> params);
    List<InvBaseStockExcel> queryInvBaseStockCountList(@Param("baseStock") Map<String,String> map);
    List<InvBaseStockExcel> queryInvBaseStockList1(@Param("baseStock") InvBaseStockExcel invBaseStock, @Param("pager") PagerInfo pager);

}
