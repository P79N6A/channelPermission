package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvoiceQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lichunsheng
 * @create 2018-01-10
 **/
@Mapper
public interface InvoiceQueueWriteDao {

    /**
     * 插入发票队列记录
     * @param orderProductId
     * @return
     */
    int insertInvoiceQueue(Integer orderProductId);

    Integer insert(InvoiceQueue invoiceQueue);

    /**
     * 处理完成后，更新队列相关信息
     * @param queue
     * @return
     */
    Integer updateAfterProccess(InvoiceQueue queue);
}
