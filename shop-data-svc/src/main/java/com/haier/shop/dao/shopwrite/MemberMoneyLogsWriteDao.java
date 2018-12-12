package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.MemberMoneyLogs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMoneyLogsWriteDao {
    int insert(MemberMoneyLogs logs);
}
