package com.haier.svc.service;

import com.haier.purchase.data.model.ProductPayment;

import java.util.List;
import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
public interface ProductPaymentService {
    /**
     * 获取付款方
     */
    public List<ProductPayment> findPaymentNameByCode(Map<String, Object> params);
}
