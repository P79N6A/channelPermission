package com.haier.shop.service;

import com.haier.shop.model.Order4Invoices;

public interface Order4InvoicesService {
    /**
     * 根据id获取专项开票对象
     * @param id
     * @return
     */
    Order4Invoices get(Integer id);

    int update(Order4Invoices order4Invoice);

    int updateForsynInvoices(Order4Invoices order4Invoice);

}
