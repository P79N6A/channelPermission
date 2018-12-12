package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.PaymentsReadDao;
import com.haier.shop.model.Payments;
import com.haier.shop.service.PaymentsService;
@Service
public class PaymentsServiceImpl implements PaymentsService {
    @Autowired
    private PaymentsReadDao paymentsReadDao;

    public Payments get(Integer id){
return paymentsReadDao.get(id);
    }

    public Payments getByCode(String paymentCode){
        return paymentsReadDao.getByCode(paymentCode);
    }
}
