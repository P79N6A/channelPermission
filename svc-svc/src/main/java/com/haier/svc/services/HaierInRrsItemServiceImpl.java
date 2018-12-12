package com.haier.svc.services;

import com.haier.purchase.data.service.PurchaseHaierInRrsItemService;
import com.haier.svc.service.HaierInRrsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
@Service
public class HaierInRrsItemServiceImpl implements HaierInRrsItemService {

    @Autowired
    private PurchaseHaierInRrsItemService purchaseHaierInRrsItemService;

    @Override
    public int getOrderNum(Map<String, Object> params) {
        return purchaseHaierInRrsItemService.getOrderNum(params);
    }
}
