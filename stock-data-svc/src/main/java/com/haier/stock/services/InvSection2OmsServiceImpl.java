package com.haier.stock.services;


import java.util.List;

import com.haier.stock.dao.stock.InvSection2OmsDao;
import com.haier.stock.model.InvSection2Oms;
import com.haier.stock.service.InvSection2OmsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvSection2OmsServiceImpl implements InvSection2OmsService {
    @Autowired
    InvSection2OmsDao invSection2OmsDao;

    /**
     * 获取所有OMS库位匹配WA库位码
     *
     * @return
     */
    @Override
    public List<InvSection2Oms> getAllOMSSectionInfo() {
        return invSection2OmsDao.getAllOMSSectionInfo();
    }
}
