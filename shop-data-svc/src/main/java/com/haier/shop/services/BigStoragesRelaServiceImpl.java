package com.haier.shop.services;

import com.haier.shop.dao.shopread.BigStoragesRelaReadDao;
import com.haier.shop.dao.shopwrite.BigStoragesRelaWriteDao;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.service.BigStoragesRelaService;
import java.util.Map;
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
    @Autowired
    BigStoragesRelaWriteDao bigStoragesRelaWriteDao;

    @Override
    public List<BigStoragesRela> getList() {
        return bigStoragesRelaReadDao.getList();
    }

    @Override
    public List<BigStoragesRela> getListByCodes(List<String> codeList) {
        return bigStoragesRelaReadDao.getListByCodes(codeList);
    }

    @Override
    public List<BigStoragesRela> getListByParam(Map<String, Object> params) {
        return bigStoragesRelaReadDao.getListByParam(params);
    }

    @Override
    public Integer getListCountByParam(Map<String, Object> params) {
        return bigStoragesRelaReadDao.getListCountByParam(params);
    }

    @Override
    public int createBigStoragesRela(BigStoragesRela bigStoragesRela) {
        return bigStoragesRelaWriteDao.createBigStoragesRela(bigStoragesRela);
    }

    @Override
    public int updateBigStoragesRela(BigStoragesRela bigStoragesRela) {
        return bigStoragesRelaWriteDao.updateBigStoragesRela(bigStoragesRela);
    }

    @Override
    public int selectByCode(BigStoragesRela bigStoragesRela) {
        return bigStoragesRelaReadDao.selectByCode(bigStoragesRela);
    }
}
