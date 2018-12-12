package com.haier.shop.service;

import com.haier.shop.model.GiftCardUsedLogs;

public interface GiftCardUsedLogsService {
    GiftCardUsedLogs get(Integer id);

    GiftCardUsedLogs getByGiftCardNumberIdAndOrderProductId( Integer giftCardNumberId,
                                                             Integer orderProductId,
                                                            Integer usedType);
    int insert(GiftCardUsedLogs giftCardUsedLogs);
}
