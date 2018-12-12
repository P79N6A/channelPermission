package com.haier.shop.service;

import com.haier.shop.model.InvoiceChangeLogs;

public interface InvoiceChangeLogsService {

	InvoiceChangeLogs get(Integer id);

	int insert(InvoiceChangeLogs invoiceChangeLogs);

	int update(InvoiceChangeLogs invoiceChangeLogs);

}