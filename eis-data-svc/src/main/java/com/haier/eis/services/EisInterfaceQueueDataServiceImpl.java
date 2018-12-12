package com.haier.eis.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.EisInterfaceQueueDataDao;
import com.haier.eis.model.EisInterfaceQueueData;
import com.haier.eis.service.EisInterfaceQueueDataService;


@Service
public class EisInterfaceQueueDataServiceImpl implements EisInterfaceQueueDataService {
    @Autowired
    EisInterfaceQueueDataDao eisInterfaceQueueDataDao;

    @Override
    public Integer insert(EisInterfaceQueueData queueData) {
        // TODO Auto-generated method stub
        return eisInterfaceQueueDataDao.insert(queueData);
    }

    @Override
    public Integer update(EisInterfaceQueueData queueData) {
        // TODO Auto-generated method stub
        return eisInterfaceQueueDataDao.update(queueData);
    }

    @Override
    public List<EisInterfaceQueueData> queryEisInterfaceQueueData(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return eisInterfaceQueueDataDao.queryEisInterfaceQueueData(params);
    }


}
