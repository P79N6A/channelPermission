package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoiceChangeLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-07
 **/
@Mapper
public interface InvoiceChangeLogsReadDao {

    /**
     * 根据invoiceId获取发票修改日志信息
     * memberInvoiceId=InvoiceChangeLogs.invoiceId
     * @param invoiceId
     * @return
     */
    List<InvoiceChangeLogs> getInvoiceChangeLogs(Integer invoiceId);

    InvoiceChangeLogs get(Integer id);

}
