package com.haier.shop.service;

import com.haier.shop.model.GiftCardNumbers;

public interface GiftCardNumbersService {
    GiftCardNumbers getByGiftcardnumbersId(Integer giftCardNumberId);

    int update(GiftCardNumbers giftCardNumbers);
}
