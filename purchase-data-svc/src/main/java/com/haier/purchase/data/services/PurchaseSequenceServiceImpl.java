package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.SequenceDao;
import com.haier.purchase.data.service.PurchaseSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
@Service
public class PurchaseSequenceServiceImpl implements PurchaseSequenceService {

    @Autowired
    SequenceDao sequenceDao;

    @Override
    public void clearSequence(Map map) {
        sequenceDao.clearSequence(map);
    }

    @Override
    public Integer selectSequence(Map map) {
        return sequenceDao.selectSequence(map);
    }

    @Override
    public void insertSequence(Map map) {
        sequenceDao.insertSequence(map);
    }

    @Override
    public void updateSequence(Map map) {
        sequenceDao.updateSequence(map);
    }

    @Override
    public void insertOrUpdateSequence(Map map) {
        sequenceDao.insertOrUpdateSequence(map);
    }
}
