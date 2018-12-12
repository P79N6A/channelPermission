package com.haier.shop.services;

import com.haier.shop.dao.shopread.MsLinkageReadDao;
import com.haier.shop.model.MsLinkage;
import com.haier.shop.service.MsLinkageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class MsLinkageServiceImpl implements MsLinkageService {
    @Autowired
    MsLinkageReadDao msLinkageReadDao;

    @Override
    public MsLinkage getMsLinkage(String sku, String orderSource) {
        // TODO Auto-generated method stub
        return msLinkageReadDao.getMsLinkage(sku, orderSource);
    }

}
