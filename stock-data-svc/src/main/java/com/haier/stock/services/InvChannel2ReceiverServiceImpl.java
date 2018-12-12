package com.haier.stock.services;

import com.haier.stock.dao.stock.InvChannel2ReceiverDao;
import com.haier.stock.model.InvChannel2Receiver;
import com.haier.stock.service.InvChannel2ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvChannel2ReceiverServiceImpl implements InvChannel2ReceiverService {
    @Autowired
    InvChannel2ReceiverDao invChannel2ReceiverDao;

    @Override
    public InvChannel2Receiver getByReceiverCode(String receiverCode) {
        // TODO Auto-generated method stub
        return invChannel2ReceiverDao.getByReceiverCode(receiverCode);
    }
}
