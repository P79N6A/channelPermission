package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import com.haier.stock.dao.stock.InvDbmBaseDao;
import com.haier.stock.model.InvDbmBase;
import com.haier.stock.service.InvDbmBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvDbmBaseServiceImpl implements InvDbmBaseService {
    @Autowired
    InvDbmBaseDao invDbmBaseDao;

    @Override
    public List<InvDbmBase> selectInvDbmBaseItem(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return invDbmBaseDao.selectInvDbmBaseItem(params);
    }

    @Override
    public List<String> findAllBaseStorage(Map params) {
        // TODO Auto-generated method stub
        return invDbmBaseDao.findAllBaseStorage(params);
    }

}
