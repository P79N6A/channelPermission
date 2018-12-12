package com.haier.eis.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.haier.eis.dao.eis.EisInterfaceFinanceQueryInfoDao;
import com.haier.eis.model.EisInterfaceFinanceQueryInfo;
import com.haier.eis.service.EisInterfaceFinanceQueryInfoService;
@Service
public class EisInterfaceFinanceQueryInfoServiceImpl implements EisInterfaceFinanceQueryInfoService {
    @Autowired
    EisInterfaceFinanceQueryInfoDao EisInterfaceFinanceQueryInfoDao;

    @Override
    public Integer insert(EisInterfaceFinanceQueryInfo eisInterfaceFinanceQueryInfo) {
        // TODO Auto-generated method stub
        return EisInterfaceFinanceQueryInfoDao.insert(eisInterfaceFinanceQueryInfo);
    }
}
