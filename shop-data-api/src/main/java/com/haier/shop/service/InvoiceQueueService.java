package com.haier.shop.service;

import com.haier.shop.model.InvoiceQueue;

import java.util.List;

/*
* 作者:张波
* 2017/12/20
* */
public interface InvoiceQueueService {
    List<InvoiceQueue> getByOrderProductId(int orderProductId);
    Integer insert(InvoiceQueue invoiceQueue);

    /**
     * 根据状态，获取列表
     * @param success
     * @return
     */
    List<InvoiceQueue> getBySuccess(Integer success);
    
    /**
     * 处理完成后，更新队列相关信息
     * @param queue
     * @return
     */
    Integer updateAfterProccess(InvoiceQueue queue);

    /**
     * 根据网单id查询invoice_queue中是否存在
     * @param opId
     * @return
     */
    int getInvoiceQueueExistFlag(Integer opId);

    /**
     * 插入发票队列记录
     * @param orderProductId
     * @return
     */
    int insertInvoiceQueue(Integer orderProductId);

    /**
     *跟新队列相关信息,设置success为否
     * @param queue
     * @return
     */
    Integer updateInvoiceQueueSuccessByOrderProductId(InvoiceQueue queue);

    Integer updateInvoiceQueueSuccessByOrderProductIds(List<Integer> ids);
}
