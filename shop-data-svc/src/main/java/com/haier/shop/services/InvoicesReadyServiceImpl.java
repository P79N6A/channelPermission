package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.InvoicesReadyReadDao;
import com.haier.shop.dao.shopwrite.InvoicesReadyWriteDao;
import com.haier.shop.model.InvoicesReady;
import com.haier.shop.service.InvoicesReadyService;

/*
* 作者:张波
* 2017/12/20
* */
@Service
public class InvoicesReadyServiceImpl implements InvoicesReadyService {
    @Autowired
    InvoicesReadyReadDao invoicesReadyReadDao;
    @Autowired
    InvoicesReadyWriteDao invoicesReadyWriteDao;

    @Override
    public InvoicesReady getByOrderProductId(Integer orderProductId) {
        // TODO Auto-generated method stub
        return invoicesReadyReadDao.getByOrderProductId(orderProductId);
    }

    @Override
    public Integer insert(InvoicesReady invoicesReady) {
        // TODO Auto-generated method stub
        return invoicesReadyWriteDao.insert(invoicesReady);
    }
}
