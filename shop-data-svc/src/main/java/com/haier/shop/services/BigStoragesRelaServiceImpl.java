package com.haier.shop.services;

import com.haier.shop.dao.shopread.BigStoragesRelaReadDao;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.service.BigStoragesRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 李超 on 2018/1/10.
 */
@Service
public class BigStoragesRelaServiceImpl implements BigStoragesRelaService {

    @Autowired
    BigStoragesRelaReadDao bigStoragesRelaReadDao;

    @Override
    public List<BigStoragesRela> getList() {
        return bigStoragesRelaReadDao.getList();
    }

    @Override
    public List<BigStoragesRela> getListByCodes(List<String> codeList) {
        return bigStoragesRelaReadDao.getListByCodes(codeList);
    }
}
