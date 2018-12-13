package com.haier.shop.services;


import com.haier.shop.dao.shopread.InvoiceQueueReadDao;
import com.haier.shop.dao.shopwrite.InvoiceQueueWriteDao;
import com.haier.shop.model.InvoiceQueue;
import com.haier.shop.service.InvoiceQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceQueueServiceImpl implements InvoiceQueueService {
    @Autowired
    InvoiceQueueReadDao invoiceQueueReadDao;
    @Autowired
    InvoiceQueueWriteDao invoiceQueueWriteDao;

    @Override
    public List<InvoiceQueue> getByOrderProductId(int orderProductId) {
        return invoiceQueueReadDao.getByOrderProductId(orderProductId);
    }

    @Override
    public Integer insert(InvoiceQueue invoiceQueue) {
        return invoiceQueueWriteDao.insert(invoiceQueue);
    }

    @Override
    public List<InvoiceQueue> getBySuccess(Integer success) {
        return invoiceQueueReadDao.getBySuccess(success);
    }

    @Override
    public Integer updateAfterProccess(InvoiceQueue queue) {
        return invoiceQueueWriteDao.updateAfterProccess(queue);
    }

    @Override
    public int getInvoiceQueueExistFlag(Integer opId) {
        return invoiceQueueReadDao.getInvoiceQueueExistFlag(opId);
    }

    @Override
    public int insertInvoiceQueue(Integer orderProductId) {
        return invoiceQueueWriteDao.insertInvoiceQueue(orderProductId);
    }

    @Override
    public Integer updateInvoiceQueueSuccessByOrderProductId(InvoiceQueue queue) {
        return invoiceQueueWriteDao.updateInvoiceQueueSuccessByOrderProductId(queue);
    }

    @Override
    public Integer updateInvoiceQueueSuccessByOrderProductIds(List<Integer> ids) {
        return invoiceQueueWriteDao.updateInvoiceQueueSuccessByOrderProductIds(ids);
    }
}
