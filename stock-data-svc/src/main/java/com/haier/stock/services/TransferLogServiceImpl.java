package com.haier.stock.services;


import java.util.List;

import com.haier.stock.dao.stock.TransferLogDao;
import com.haier.stock.model.InvTransferLog;
import com.haier.stock.service.TransferLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 货物调拨操作历史DAO
 *
 * @Filename: TransferLogDao.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 */
@Service
public class TransferLogServiceImpl implements TransferLogService {
    @Autowired
    TransferLogDao transferLogDao;

    @Override
    public List<InvTransferLog> getByLineId(Integer lineId) {
        return transferLogDao.getByLineId(lineId);
    }

    @Override
    public void insert(InvTransferLog log) {
        transferLogDao.insert(log);
    }
}
