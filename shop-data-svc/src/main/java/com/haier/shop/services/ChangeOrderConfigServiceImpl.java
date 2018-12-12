package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ChangeOrderConfigReadDao;
import com.haier.shop.dao.shopwrite.ChangeOrderConfigWriteDao;
import com.haier.shop.model.ChangeOrderConfig;
import com.haier.shop.service.ChangeOrderConfigService;

/**
 * Created by 李超 on 2018/1/10.
 */
@Service
public class ChangeOrderConfigServiceImpl implements ChangeOrderConfigService {

    @Autowired
    ChangeOrderConfigWriteDao changeOrderConfigWriteDao;
    @Autowired
    ChangeOrderConfigReadDao changeOrderConfigReadDao;

    @Override
    public int insert(ChangeOrderConfig changeOrderConfig) {
        return changeOrderConfigWriteDao.insert(changeOrderConfig);
    }

    @Override
    public int update(ChangeOrderConfig changeOrderConfig) {
        return changeOrderConfigWriteDao.update(changeOrderConfig);
    }

    @Override
    public ChangeOrderConfig get(Integer id) {
        return changeOrderConfigReadDao.get(id);
    }

    @Override
    public ChangeOrderConfig getBySourceAndBrandAndCateAndregion(String orderSourceCode, Integer regionId, Integer brandId, Integer cateId) {
        return changeOrderConfigReadDao.getBySourceAndBrandAndCateAndregion(orderSourceCode, regionId, brandId, cateId);
    }

    @Override
    public int delete(Integer id) {
        return changeOrderConfigWriteDao.delete(id);
    }
}
