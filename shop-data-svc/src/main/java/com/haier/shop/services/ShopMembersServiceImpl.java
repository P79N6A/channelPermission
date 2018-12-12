package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.MembersReadDao;
import com.haier.shop.dao.shopwrite.MembersWriteDao;
import com.haier.shop.model.Members;
import com.haier.shop.service.ShopMembersService;

/**
 * Created by 李超 on 2018/1/9.
 */
@Service
public class ShopMembersServiceImpl implements ShopMembersService {

    @Autowired
    MembersWriteDao membersWriteDao;
    @Autowired
    MembersReadDao membersReadDao;

    @Override
    public Members get(Integer id) {
        return membersReadDao.get(id);
    }

    @Override
    public String getMemberMobile(Integer id) {
        return membersReadDao.getMemberMobile(id);
    }

    @Override
    public int update(Members members) {
        return membersWriteDao.update(members);
    }
}
