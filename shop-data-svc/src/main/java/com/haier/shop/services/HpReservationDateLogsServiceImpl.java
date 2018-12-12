package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.HpReservationDateLogsWriteDao;
import com.haier.shop.model.HpReservationDateLogs;
import com.haier.shop.service.HpReservationDateLogsService;

/*
* 作者:张波
* 2017/12/25
*/
@Service
public class HpReservationDateLogsServiceImpl implements HpReservationDateLogsService {
    @Autowired
    HpReservationDateLogsWriteDao hpReservationDateLogsWriteDao;

    @Override
    public int insert(HpReservationDateLogs hpReservationDateLogs) {
        // TODO Auto-generated method stub
        return hpReservationDateLogsWriteDao.insert(hpReservationDateLogs);
    }
}
