package com.haier.shop.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.StoragesRelaReadDao;
import com.haier.shop.model.StoragesRela;
import com.haier.shop.service.StoragesRelaService;

@Service
public class StoragesRelaServiceImpl implements StoragesRelaService {

    @Autowired
    StoragesRelaReadDao storagesRelaReadDao;

    @Override
    public List<StoragesRela> getList() {
        return storagesRelaReadDao.getList();
    }

    @Override
    public List<StoragesRela> getListByCodes(List<String> codeList) {
        return storagesRelaReadDao.getListByCodes(codeList);
    }
}
