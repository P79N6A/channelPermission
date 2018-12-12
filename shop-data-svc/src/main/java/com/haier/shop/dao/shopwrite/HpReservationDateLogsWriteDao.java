package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.HpReservationDateLogs;
import org.apache.ibatis.annotations.Mapper;

/*
* 作者:张波
* 2017/12/25
*/
@Mapper
public interface HpReservationDateLogsWriteDao {
    int insert(HpReservationDateLogs hpReservationDateLogs);
}
