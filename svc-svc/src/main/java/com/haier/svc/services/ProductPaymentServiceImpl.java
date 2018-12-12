package com.haier.svc.services;

import com.haier.purchase.data.model.ProductPayment;
import com.haier.purchase.data.service.PurchaseProductPaymentService;
import com.haier.svc.service.ProductPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
@Service
public class ProductPaymentServiceImpl implements ProductPaymentService {

    @Autowired
    private PurchaseProductPaymentService purchaseProductPaymentService;

    @Override
    public List<ProductPayment> findPaymentNameByCode(Map<String, Object> params) {
        return purchaseProductPaymentService.findPaymentNameByCode(params);
    }
}
