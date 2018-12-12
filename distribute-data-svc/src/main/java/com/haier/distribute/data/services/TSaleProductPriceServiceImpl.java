package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.TSaleProductPriceDao;
import com.haier.distribute.data.model.TSaleProductPrice;
import com.haier.distribute.data.service.TSaleProductPriceService;

import org.omg.CORBA.TCKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 胡万来 on 2018/1/13 0013.
 */
@Service
public class TSaleProductPriceServiceImpl implements TSaleProductPriceService {
    @Autowired
    TSaleProductPriceDao tSaleProductPriceDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return tSaleProductPriceDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TSaleProductPrice record) {
        return tSaleProductPriceDao.insert(record);
    }

    @Override
    public int insertSelective(TSaleProductPrice record) {
        return tSaleProductPriceDao.insertSelective(record);
    }

    @Override
    public TSaleProductPrice selectByPrimaryKey(Integer id) {
        return tSaleProductPriceDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TSaleProductPrice record) {
        return tSaleProductPriceDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TSaleProductPrice record) {
        return tSaleProductPriceDao.updateByPrimaryKey(record);
    }

    @Override
    public List<TSaleProductPrice> selectBySaleId(Integer saleId) {
        return tSaleProductPriceDao.selectBySaleId(saleId);
    }

    @Override
    public List<TSaleProductPrice> selectCount(String startDateTime, String endDateTime, Integer saleId) {
        return tSaleProductPriceDao.selectCount(startDateTime,endDateTime,saleId);
    }

    @Override
    public int deleteAuto(Integer saleid) {
        return tSaleProductPriceDao.deleteAuto(saleid);
    }

    @Override
    public long checkPriceTime(int id, int saleId, String startTime, String endTime) {
        return tSaleProductPriceDao.checkPriceTime(id,saleId,startTime,endTime);
    }

    @Override
    public List<TSaleProductPrice> getPageByCondition(TSaleProductPrice entity, int start, int rows) {
        return tSaleProductPriceDao.getPageByCondition(entity,start,rows);
    }

    @Override
    public long getPagerCount(TSaleProductPrice entity) {
        return tSaleProductPriceDao.getPagerCount(entity);
    }

    @Override
    public List<TSaleProductPrice> getExportData(TSaleProductPrice condition) {
        return tSaleProductPriceDao.getExportData(condition);
    }
}
