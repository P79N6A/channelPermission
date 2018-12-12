package com.haier.shop.dao.shopread;

import com.haier.shop.model.GiftCardUsedLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GiftCardUsedLogsReadDao {

    GiftCardUsedLogs get(Integer id);

    GiftCardUsedLogs getByGiftCardNumberIdAndOrderProductId(@Param("giftCardNumberId") Integer giftCardNumberId,
                                                            @Param("orderProductId") Integer orderProductId,
                                                            @Param("usedType") Integer usedType);
}
