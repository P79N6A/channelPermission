package com.haier.svc.services;

import com.haier.purchase.data.service.PurchaseSequenceService;
import com.haier.svc.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private PurchaseSequenceService purchaseSequenceService;

    @Override
    public void clearSequence(Map map) {
        purchaseSequenceService.clearSequence(map);
    }

    @Override
    public Integer selectSequence(Map map) {
        return purchaseSequenceService.selectSequence(map);
    }

    @Override
    public void insertSequence(Map map) {
        purchaseSequenceService.insertSequence(map);
    }

    @Override
    public void updateSequence(Map map) {
        purchaseSequenceService.updateSequence(map);
    }

    @Override
    public void insertOrUpdateSequence(Map map) {
        purchaseSequenceService.insertOrUpdateSequence(map);
    }
}
