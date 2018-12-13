package com.haier.eop.data.dao;

import com.haier.eop.data.model.TransferOrder;
import com.haier.eop.data.model.TransferOrderDisplayItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TransferOrderDao {

    /**
     * 补货单列表分页查询
     *
     * @param paramMap
     * @return
     */
    List<TransferOrderDisplayItem> getOrders(Map<String, Object> paramMap);

    /**
     * 获取记录数
     *
     * @return
     */
    int getRowCnts();

    /**
     * 根据调拨单号查询调拨单列表
     *
     * @param transferOrderCode
     * @return
     */
    List<TransferOrder> getByTransferOrderCode(@Param("transferOrderCode") String transferOrderCode);

    /**
     * 同步信息更新调拨单
     *
     * @param transferOrder
     */
    void syncUpdate(TransferOrder transferOrder);

    /**
     * 生成调拨单
     *
     * @param transferOrder
     */
    void insert(TransferOrder transferOrder);

    /**
     * 批量插入调拨单
     * @param transferOrders
     */
    void createTransferOrders(List<TransferOrder> transferOrders);

    /**
     * 更新调拨单
     *
     * @param transferOrder
     */
    void update(TransferOrder transferOrder);

    /**
     * 获取人工介入调拨单数量
     *
     * @return
     */
    Integer getManualOrderCount();

    /**
     * 获取人工介入调拨单列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<String> getManualOrderCodes(@Param("pageNo") Integer pageNo,
                                     @Param("pageSize") Integer pageSize);

    /**
     * 获取调拨单导出数据
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> getExportTransferOrderOutList(Map<String, Object> paramMap);

    /**
     * 查找已经存在的IBC单号
     * @param paramMap
     * @return
     */
    List<String> getExistTransferOrderCodes(Map<String, Object> paramMap);
}
