package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.Order4Invoices;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Order4InvoicesWriteDao {

    int update(Order4Invoices order4Invoice);

    int updateForsynInvoices(Order4Invoices order4Invoice);
}
