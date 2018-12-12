package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.GiftCardUsedLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GiftCardUsedLogsWriteDao {

    int insert(GiftCardUsedLogs giftCardUsedLogs);
}
