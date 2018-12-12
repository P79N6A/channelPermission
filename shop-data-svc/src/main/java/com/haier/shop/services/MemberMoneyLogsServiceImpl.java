package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.MemberMoneyLogsWriteDao;
import com.haier.shop.model.MemberMoneyLogs;
import com.haier.shop.service.MemberMoneyLogsService;

/**
 * Created by 胡万来 on 2018/1/11 0011.
 */
@Service
public class MemberMoneyLogsServiceImpl implements MemberMoneyLogsService {

    @Autowired
    MemberMoneyLogsWriteDao memberMoneyLogsWriteDao;

    @Override
    public int insert(MemberMoneyLogs logs) {
        return memberMoneyLogsWriteDao.insert(logs);
    }
}
