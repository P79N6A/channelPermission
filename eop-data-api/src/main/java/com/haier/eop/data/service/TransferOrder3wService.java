package com.haier.eop.data.service;

import com.haier.eop.data.model.TransferOrder;
import com.taobao.pac.sdk.cp.dataobject.response.TAOBAO_QIMEN_TRANSFERORDER_QUERY.TransferOrderDetail;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 3W间调拨单处理接口
 */
public interface TransferOrder3wService {

    /**
     * 获取调拨单列表
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> getReplOrdersByPage(Map<String, Object> paramMap) throws ParseException;

    /**
     * 根据调拨单号查询调拨单列表
     *
     * @param transferOrderCode
     * @return
     */
    List<TransferOrder> getByTransferOrderCode(String transferOrderCode);

    /**
     * 查询是否存在调拨日志
     *
     * @param name
     * @return
     */
    int getTransferOrderLogByName(String name);

    /**
     * 更新调拨单日志
     * @param name
     * @param updateouttime
     * @param updateintime
     * @param querytime
     * @param out_msg
     * @param in_msg
     * @param query_msg
     */
    void updateTransferLog(String name, String updateouttime, String updateintime, String querytime, String out_msg,
                           String in_msg, String query_msg);

    /**
     * 生成调拨单日志
     * @param name
     * @param updateouttime
     * @param updateintime
     * @param querytime
     * @param out_msg
     * @param in_msg
     * @param query_msg
     */
    void insertTransferLog(String name, String updateouttime, String updateintime, String querytime, String out_msg,
                           String in_msg, String query_msg);

    /**
     * 同步数据更新
     * @param oldTransferOrderList
     * @param detail
     * @param beSyncJob
     */
    void handleSyncUpdate(List<TransferOrder> oldTransferOrderList, TransferOrderDetail detail, boolean beSyncJob);

    /**
     * 查询异常调拨单数量
     * @return
     */
    Integer getManualOrderCount();

    /**
     * 分页查询人工介入的调拨单
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<String> getManualOrderCodes(Integer pageNo, Integer pageSize);

    /**
     * 获取调拨单导出数据
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> getExportTransferOrderOutList(Map<String, Object> paramMap);

    /**
     * 获取已经存在的IBC调拨单号
     * @param orderCodesMap IBC调拨单号
     * @return
     */
    List<String> getExistTransferOrderCodes(Map<String, Object> orderCodesMap);

    /**
     * 创建调拨单
     * @param detail
     * @return
     */
    String createTransferOrder(TransferOrderDetail detail);
}
