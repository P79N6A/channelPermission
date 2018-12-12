package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.GiftCardNumbers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GiftCardNumbersWriteDao {

    int update(GiftCardNumbers giftCardNumbers);
}
