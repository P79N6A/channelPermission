package com.haier.shop.services;

import com.haier.shop.dao.shopwrite.HPIdentificationResultWriteDao;
import com.haier.shop.model.OrderRepairshpLogs;
import com.haier.shop.service.HPIdentificationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HPIdentificationResultServiceImpl implements HPIdentificationResultService {
    @Autowired
    private HPIdentificationResultWriteDao hpIdentificationResultWriteDao;
    public int insert(OrderRepairshpLogs record){
        return hpIdentificationResultWriteDao.insert(record);
    }
}
