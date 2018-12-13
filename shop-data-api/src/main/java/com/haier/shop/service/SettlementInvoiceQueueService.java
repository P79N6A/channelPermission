package com.haier.shop.service;

import com.haier.shop.model.Invoices;
import com.haier.shop.model.SettlementInvoiceQueue;

import java.util.List;

/**
 * @Author:JinXueqian
 * @Date: 2018/6/1 11:29
 */
public interface SettlementInvoiceQueueService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SettlementInvoiceQueue get(Integer id);

    /**
     * 插入
     * @param settlementInvoiceQueue
     * @return
     */
    int insert(SettlementInvoiceQueue settlementInvoiceQueue);

    /**
     * 修改
     * @param settlementInvoiceQueue
     * @return
     */
    int update(SettlementInvoiceQueue settlementInvoiceQueue);

    /**
     * 根据id和发票状态查询
     * @param invoicesId
     * @param statusType
     * @return
     */
    SettlementInvoiceQueue getByInvoicesIdAndStatuszType(Integer invoicesId,Integer statusType);

    /**
     * 获取处理队列
     * @return
     */
    List<SettlementInvoiceQueue> getBySuccess();

    /**
     * 写佣金核算发票队列
     * @param invoice
     * @param i
     */
    void addSettlementInvoiceQueue(Invoices invoice, int i);
}
