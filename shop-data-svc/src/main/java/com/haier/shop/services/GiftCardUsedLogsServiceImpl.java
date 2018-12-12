package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.GiftCardUsedLogsReadDao;
import com.haier.shop.dao.shopwrite.GiftCardUsedLogsWriteDao;
import com.haier.shop.model.GiftCardUsedLogs;
import com.haier.shop.service.GiftCardUsedLogsService;

@Service
public class GiftCardUsedLogsServiceImpl implements GiftCardUsedLogsService {
    @Autowired
    GiftCardUsedLogsReadDao giftCardUsedLogsReadDao;

    @Autowired
    GiftCardUsedLogsWriteDao giftCardUsedLogsWriteDao;

    @Override
    public GiftCardUsedLogs get(Integer id) {
        // TODO Auto-generated method stub
        return giftCardUsedLogsReadDao.get(id);
    }

    @Override
    public GiftCardUsedLogs getByGiftCardNumberIdAndOrderProductId(Integer giftCardNumberId, Integer orderProductId,
                                                                   Integer usedType) {
        // TODO Auto-generated method stub
        return giftCardUsedLogsReadDao.getByGiftCardNumberIdAndOrderProductId(giftCardNumberId, orderProductId, usedType);
    }

    @Override
    public int insert(GiftCardUsedLogs giftCardUsedLogs) {
        // TODO Auto-generated method stub
        return giftCardUsedLogsWriteDao.insert(giftCardUsedLogs);
    }


}
