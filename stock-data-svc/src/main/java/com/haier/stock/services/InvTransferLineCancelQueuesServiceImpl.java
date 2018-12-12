package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvTransferLineCancelQueuesDao;
import com.haier.stock.model.InvTransferLineCancelQueues;
import com.haier.stock.service.InvTransferLineCancelQueuesService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvTransferLineCancelQueuesServiceImpl implements InvTransferLineCancelQueuesService {
    @Autowired
    InvTransferLineCancelQueuesDao invTransferLineCancelQueuesDao;

    @Override
    public InvTransferLineCancelQueues get(String lineNum) {
        // TODO Auto-generated method stub
        return invTransferLineCancelQueuesDao.get(lineNum);
    }

    @Override
    public List<InvTransferLineCancelQueues> getUnSend(Integer count) {
        // TODO Auto-generated method stub
        return invTransferLineCancelQueuesDao.getUnSend(count);
    }

    @Override
    public int insert(InvTransferLineCancelQueues invTransferLineCancelQueues) {
        // TODO Auto-generated method stub
        return invTransferLineCancelQueuesDao.insert(invTransferLineCancelQueues);
    }

    @Override
    public int update(InvTransferLineCancelQueues invTransferLineCancelQueues) {
        // TODO Auto-generated method stub
        return invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
    }

}