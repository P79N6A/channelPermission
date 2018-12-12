package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.HaierInRrsItemDao;
import com.haier.purchase.data.service.PurchaseHaierInRrsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
@Service
public class PurchaseHaierInRrsItemServiceImpl implements PurchaseHaierInRrsItemService {

    @Autowired
    private HaierInRrsItemDao haierInRrsItemDao;

    @Override
    public int getOrderNum(Map<String, Object> params) {
        return haierInRrsItemDao.getOrderNum(params);
    }
}
