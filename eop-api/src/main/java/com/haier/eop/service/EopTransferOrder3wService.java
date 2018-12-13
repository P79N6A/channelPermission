package com.haier.eop.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 3w间调拨处理接口
 */
public interface EopTransferOrder3wService {

    /**
     * 获取3w间调拨单列表
     *
     * @param params
     * @return
     */
    Map<String, Object> findOrderList(Map<String, Object> params) throws ParseException;

    /**
     * 从菜鸟同步调拨数据
     * @param transferOrderCode
     */
    void syncOrderFromCn(String transferOrderCode);

    /**
     * 从菜鸟同步所有异常调拨单
     */
    void syncOrdersFromCn();

    /**
     * 补录数据
     * @param totalArray IBC单号
     * @return
     */
    String readdRecords(String[] totalArray);


    /**
     * 获取调拨单导出数据
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> getExportTransferOrderOutList(Map<String, Object> paramMap);
}
