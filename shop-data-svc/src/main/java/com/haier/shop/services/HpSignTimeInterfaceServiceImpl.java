package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.HpSignTimeInterfaceReadDao;
import com.haier.shop.dao.shopwrite.HpSignTimeInterfaceWriteDao;
import com.haier.shop.model.HpSignTimeInterface;
import com.haier.shop.service.HpSignTimeInterfaceService;

/*
* 作者:张波
* 2hpSignTimeInterfaceDao.17/12/26
*/
@Service
public class HpSignTimeInterfaceServiceImpl implements HpSignTimeInterfaceService {
    @Autowired
    HpSignTimeInterfaceReadDao hpSignTimeInterfaceReadDao;
    @Autowired
    HpSignTimeInterfaceWriteDao hpSignTimeInterfaceWriteDao;

    @Override
    public HpSignTimeInterface selectByTbNoAndLbx(HpSignTimeInterface record) {
        // TODO Auto-generated method stub
        return hpSignTimeInterfaceReadDao.selectByTbNoAndLbx(record);
    }

    @Override
    public int addCountBySkuAndLbx(HpSignTimeInterface record) {
        // TODO Auto-generated method stub
        return hpSignTimeInterfaceWriteDao.addCountBySkuAndLbx(record);
    }

    @Override
    public int insert(HpSignTimeInterface record) {
        // TODO Auto-generated method stub
        return hpSignTimeInterfaceWriteDao.insert(record);
    }
}
