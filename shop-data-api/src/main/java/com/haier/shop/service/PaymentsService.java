package com.haier.shop.service;


import com.haier.shop.model.Payments;

public interface PaymentsService {
    Payments get(Integer id);

    Payments getByCode(String paymentCode);
}
