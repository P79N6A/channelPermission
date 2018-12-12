package com.haier.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.common.PagerInfo;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvBaseStock;
import com.haier.stock.model.InvBaseStockEx;
import com.haier.stock.model.InvBaseStockLog;
import com.haier.stock.model.InvStore;

public interface StockInvBaseStockService {
    /**
     * 获取并锁定
     *
     * @param sku
     * @param secCode
     * @return
     */
    public InvBaseStock getForUpdate(String sku, String secCode);

    public Integer insert(InvBaseStock baseStock);

    public Integer update(InvBaseStock baseStock);

    public Integer updateQtyForFrozen(InvBaseStock baseStock);

    /**
     * 更新stockQty 和 frozenQty
     *
     * @param sto_id
     * @param qty
     * @param releaseQty
     * @param time
     * @return
     */
    public Integer updateQty(Integer sto_id, Integer qty,
                             Integer releaseQty, Date time);

    /**
     * 更新stockQty
     *
     * @param sto_id
     * @param qty
     * @param time
     * @return
     */
    public Integer updateStockQty(Integer sto_id, Integer qty,
                                  Date time);

    /**
     * 冻结库存
     *
     * @param stoId
     * @param frozenQty
     * @param time
     * @return
     */
    public Integer frozenQty(Integer stoId, Integer frozenQty,
                             Date time);

    /**
     * 释放冻结库存
     *
     * @param stoId
     * @param releaseQty
     * @param time
     * @return
     */
    public Integer releaseQty(Integer stoId, Integer releaseQty,
                              Date time);

    /**
     * 根据物料编码和库存编码查询库存，批次10
     *
     * @param sku
     * @param lesSecCode
     * @return
     */
    public InvBaseStock queryBySkuAndLesSecCode(String sku, String lesSecCode);

    public BaseStock get(String sku, String code);

    public BaseStock getByItemProperty(String sku, String code, String itemProperty);

    List<InvBaseStock> getPageByCondition(InvBaseStock entity, int start, int rows);

    long getPagerCount(InvBaseStock entity);

    List<InvBaseStock> getMachinePageByCondition(InvBaseStock invBaseStock, int start, int rows);

    long getMachinePagerCount(InvBaseStock invBaseStock);

    List<InvBaseStockLog> getLogPageByCondition(InvBaseStockLog condition, int start, int pageSize);

    long getLogPagerCount(InvBaseStockLog condition);

    List<InvStore> getStorePageByCondition(InvStore condition, int start, int pageSize);

    long getStorePagerCount(InvStore condition);
    
    List<InvBaseStockEx> queryInvBaseStockList(InvBaseStockEx invBaseStock,
             PagerInfo pager);
    
    int getRowCnt();
    
    List<InvBaseStockEx> queryInvStockList(InvBaseStockEx invBaseStock,
             PagerInfo pager);

    List<InvBaseStockLog> queryInvBaseStockLogList(InvBaseStockLog log,
                    PagerInfo pager);
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
}
