package com.haier.shop.service;


import com.haier.common.BusinessException;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.InvoicesDispItem;

import java.util.List;
import java.util.Map;

/**
 * 发票业务操作接口（业务专用）
 */
public interface ShopInvoiceService {

    /**
     * 根据cOrderSn获取发票信息
     * @param cOrderSn
     * @return
     */
    Invoices getInvoicesByCOrderSn(String cOrderSn);

    /**
     * 更新发票信息
     * @param invoices
     */
    Integer updateInvoices(Invoices invoices);

    /**
     * 根据id获取发票信息
     * @param id
     * @return
     */
    Invoices getById(Integer id);

    /**
     * 获取开票列表
     * @param paramMap
     * @return 开票列表List
     */
    Map<String, Object> getInvoiceMakeOutListByPage(Map<String, Object> paramMap);

    /**
     * 获得条数
     *
     * @return
     */
    int getRowCnts();

    /**
     * 获取开票列表导出数据
     * @param paramMap
     * @return 开票列表导出数据
     */
    List<Map<String, Object>> getExportInvoiceMakeOutList(Map<String, Object> paramMap);

    /**
     * 根据cOrderId获得Invoice信息
     * @param id
     * @return Invoce信息
     */
    Invoices getUsableByOrderProductId(Integer id);

    /**
     * 电子发票批量作废
     * @param params cOrderSn列表
     * @return
     */
    int eInvoiceBatchInvalid(Map<String, Object> params);

    /**
     * 电子发票批量重推
     * @param params
     * @return
     */
    int eInvoiceBatchReBilling(Map<String, Object> params);

    /**
     * 重开、作废、取消发票时，更新发票信息 业务操作
     * @param invoice
     */
    void updateInvoiceOperate(Invoices invoice);

    /**
     * 更新发票信息（前提查询金税数据后）， 业务操作
     * @param edit
     * @param hasPushData
     * @throws BusinessException
     */
    void updateInvoiceStatus4Tax(Invoices edit, boolean hasPushData) throws BusinessException;

    /**
     * 更新发票信息（前提查询电子发票系统）， 业务操作
     * @param invoices
     * @return true:成功，用于批量操作时计数
     */
    boolean updateElecInvoice4ElecSys(Invoices invoices, Map<String, String> paramMap) throws BusinessException;

    /**
     * 更具ID查询，显示网单开票信息
     * @param id 发票id
     * @return
     */
    List<InvoicesDispItem> showInvoiceInfo(Integer id);

    /**
     * 发票批量修改
     * @param params
     * @return
     */
    int invoiceBatchModify(Map<String, Object> params);
}
