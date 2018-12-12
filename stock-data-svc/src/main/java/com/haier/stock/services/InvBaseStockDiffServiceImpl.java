package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvBaseStockDiffDao;
import com.haier.stock.model.InvBaseStockDiff;
import com.haier.stock.service.InvBaseStockDiffService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvBaseStockDiffServiceImpl implements InvBaseStockDiffService {
    @Autowired
    InvBaseStockDiffDao invBaseStockDiffDao;

    @Override
    public List<InvBaseStockDiff> queryInvBaseStockDiff(Integer maxId, int topX) {
        // TODO Auto-generated method stub
        return invBaseStockDiffDao.queryInvBaseStockDiff(maxId, topX);
    }

    @Override
    public Integer updateInvBaseStockDiff(InvBaseStockDiff invBaseStockDiff) {
        // TODO Auto-generated method stub
        return invBaseStockDiffDao.updateInvBaseStockDiff(invBaseStockDiff);
    }

}
