package com.haier.shop.dao.shopwrite;


import com.haier.shop.model.InvoicesWwwLogs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InvoicesWwwLogsWriteDao {

    int insert(InvoicesWwwLogs invoicesWwwLogs);

    int updateInvoiceWwwLogs(InvoicesWwwLogs invoicesWwwLogs);
}