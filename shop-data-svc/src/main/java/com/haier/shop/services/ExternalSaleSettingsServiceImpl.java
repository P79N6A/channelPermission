package com.haier.shop.services;

import com.haier.shop.dao.shopread.ExternalSaleSettingsReadDao;
import com.haier.shop.dao.shopwrite.ExternalSaleSettingsWriteDao;
import com.haier.shop.model.ExternalSaleSettings;
import com.haier.shop.service.ExternalSaleSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/12 16:51
 */
@Service
public class ExternalSaleSettingsServiceImpl implements ExternalSaleSettingsService{
    @Autowired
    private ExternalSaleSettingsReadDao externalSaleSettingsReadDao;
    @Autowired
    private ExternalSaleSettingsWriteDao externalSaleSettingsWriteDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return externalSaleSettingsWriteDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ExternalSaleSettings record) {
        return externalSaleSettingsWriteDao.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ExternalSaleSettings record) {
        return externalSaleSettingsWriteDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<ExternalSaleSettings> Listf(ExternalSaleSettings entity, int start, int rows) {
        return externalSaleSettingsReadDao.Listf(entity, start, rows);
    }

    @Override
    public int getPagerCountS(ExternalSaleSettings entity) {
        return externalSaleSettingsReadDao.getPagerCountS(entity);
    }

    @Override
    public ExternalSaleSettings findByWhere(String externalSkus) {
        return externalSaleSettingsReadDao.findByWhere(externalSkus);
    }
}
