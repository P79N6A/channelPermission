package com.haier.stock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.ReturnReasonDao;
import com.haier.stock.service.ReturnReasonService;

/**
 * Created by 张晗 on 2014/8/18.
 */
@Service
public class ReturnReasonServiceImpl implements ReturnReasonService {

    @Autowired
    ReturnReasonDao returnReasonDao;

    @Override
    public String getIdByReturnReason(String returnReason) {
        return returnReasonDao.getIdByReturnReason(returnReason);
    }
}
