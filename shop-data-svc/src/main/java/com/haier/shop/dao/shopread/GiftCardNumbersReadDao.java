package com.haier.shop.dao.shopread;

import com.haier.shop.model.GiftCardNumbers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GiftCardNumbersReadDao {

    GiftCardNumbers getByGiftcardnumbersId(@Param("giftCardNumberId") Integer giftCardNumberId);
}
