package com.haier.eis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.EisInterfaceFinance3WDao;
import com.haier.eis.model.EisInterfaceFinance3w;
import com.haier.eis.service.EisInterfaceFinance3WService;

@Service
public class EisInterfaceFinance3WServiceImpl implements EisInterfaceFinance3WService {
    @Autowired
    EisInterfaceFinance3WDao eisInterfaceFinance3WDao;

    @Override
    public EisInterfaceFinance3w getByTransQueue3WId(Integer transQueue3WId, String interfaceCode) {
        // TODO Auto-generated method stub
        return eisInterfaceFinance3WDao.getByTransQueue3WId(transQueue3WId, interfaceCode);
    }

    @Override
    public List<EisInterfaceFinance3w> getByStatus(Integer status) {
        // TODO Auto-generated method stub
        return eisInterfaceFinance3WDao.getByStatus(status);
    }

    @Override
    public Integer insert(EisInterfaceFinance3w interfaceFinance) {
        // TODO Auto-generated method stub
        return eisInterfaceFinance3WDao.insert(interfaceFinance);
    }

    @Override
    public Integer update(EisInterfaceFinance3w interfaceFinance) {
        // TODO Auto-generated method stub
        return eisInterfaceFinance3WDao.update(interfaceFinance);
    }

}
