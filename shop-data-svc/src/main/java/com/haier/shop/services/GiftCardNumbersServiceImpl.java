package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.GiftCardNumbersReadDao;
import com.haier.shop.dao.shopwrite.GiftCardNumbersWriteDao;
import com.haier.shop.model.GiftCardNumbers;
import com.haier.shop.service.GiftCardNumbersService;

@Service
public class GiftCardNumbersServiceImpl implements GiftCardNumbersService {
    @Autowired
    GiftCardNumbersReadDao giftCardNumbersReadDao;
    @Autowired
    GiftCardNumbersWriteDao giftCardNumbersWriteDao;

    @Override
    public GiftCardNumbers getByGiftcardnumbersId(Integer giftCardNumberId) {
        // TODO Auto-generated method stub
        return giftCardNumbersReadDao.getByGiftcardnumbersId(giftCardNumberId);
    }

    @Override
    public int update(GiftCardNumbers giftCardNumbers) {
        // TODO Auto-generated method stub
        return giftCardNumbersWriteDao.update(giftCardNumbers);
    }
}
