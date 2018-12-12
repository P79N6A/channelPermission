package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvStoreSynchDao;
import com.haier.stock.model.InvStoreSynch;
import com.haier.stock.service.InvStoreSynchService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvStoreSynchServiceImpl implements InvStoreSynchService {
    @Autowired
    InvStoreSynchDao invStoreSynchDao;

    @Override
    public InvStoreSynch get(Integer id) {
        // TODO Auto-generated method stub
        return invStoreSynchDao.get(id);
    }

    @Override
    public Integer insert(InvStoreSynch invStoreSynch) {
        // TODO Auto-generated method stub
        return invStoreSynchDao.insert(invStoreSynch);
    }

    @Override
    public Integer batchInsert(List<InvStoreSynch> invStoreSynchList) {
        // TODO Auto-generated method stub
        return invStoreSynchDao.batchInsert(invStoreSynchList);
    }

    @Override
    public Integer updateStatusById(Integer id, Integer status, String message) {
        // TODO Auto-generated method stub
        return invStoreSynchDao.updateStatusById(id, status, message);
    }

    @Override
    public Integer update(InvStoreSynch invStoreSynch) {
        // TODO Auto-generated method stub
        return invStoreSynchDao.update(invStoreSynch);
    }

    @Override
    public List<InvStoreSynch> queryStoreList(Integer topx) {
        // TODO Auto-generated method stub
        return invStoreSynchDao.queryStoreList(topx);
    }
}
