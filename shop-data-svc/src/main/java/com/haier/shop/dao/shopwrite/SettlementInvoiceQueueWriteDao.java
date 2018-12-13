package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.SettlementInvoiceQueue;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:JinXueqian
 * @Date: 2018/6/1 10:31
 */
@Mapper
public interface SettlementInvoiceQueueWriteDao {
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
}
