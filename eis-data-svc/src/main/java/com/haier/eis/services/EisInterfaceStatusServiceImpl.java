package com.haier.eis.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.EisInterfaceStatusDao;
import com.haier.eis.model.EisInterfaceStatus;
import com.haier.eis.service.EisInterfaceStatusService;

@Service
public class EisInterfaceStatusServiceImpl implements EisInterfaceStatusService {
    @Autowired
    EisInterfaceStatusDao eisInterfaceStatusDao;

    @Override
    public EisInterfaceStatus getByInterfaceCode(String interfaceCode) {
        // TODO Auto-generated method stub
        return eisInterfaceStatusDao.getByInterfaceCode(interfaceCode);
    }

    @Override
    public Integer update(EisInterfaceStatus eisInterfaceStatus) {
        // TODO Auto-generated method stub
        return eisInterfaceStatusDao.update(eisInterfaceStatus);
    }
}
