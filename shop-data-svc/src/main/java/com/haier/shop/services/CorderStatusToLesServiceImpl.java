package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.CorderStatusToLesReadDao;
import com.haier.shop.dao.shopwrite.CorderStatusToLesWriteDao;
import com.haier.shop.model.CorderStatusToLes;
import com.haier.shop.service.CorderStatusToLesService;


@Service
public class CorderStatusToLesServiceImpl implements CorderStatusToLesService {
    @Autowired
    CorderStatusToLesWriteDao corderStatusToLesWriteDao;
    @Autowired
    CorderStatusToLesReadDao corderStatusToLesReadDao;

    @Override
    public int updateByPrimaryKey(CorderStatusToLes record) {
        // TODO Auto-generated method stub
        return corderStatusToLesWriteDao.updateByPrimaryKey(record);
    }

    @Override
    public List<CorderStatusToLes> findNeedSendToLes(Integer limit) {
        // TODO Auto-generated method stub
        return corderStatusToLesReadDao.findNeedSendToLes(limit);
    }

    @Override
    public int insert(CorderStatusToLes corderStatusToLes) {
        // TODO Auto-generated method stub
        return corderStatusToLesWriteDao.insert(corderStatusToLes);
    }


}