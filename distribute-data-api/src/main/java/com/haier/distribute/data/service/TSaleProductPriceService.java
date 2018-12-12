package com.haier.distribute.data.service;

import com.haier.distribute.data.model.TSaleProductPrice;

import java.util.List;

public interface TSaleProductPriceService {
    int deleteByPrimaryKey(Integer id);

    int insert(TSaleProductPrice record);

    int insertSelective(TSaleProductPrice record);

    TSaleProductPrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSaleProductPrice record);

    int updateByPrimaryKey(TSaleProductPrice record);

    List<TSaleProductPrice> selectBySaleId(Integer saleId);

    List<TSaleProductPrice> selectCount(String startDateTime, String endDateTime, Integer saleId);

    int deleteAuto(Integer saleid);

    long checkPriceTime(int id, int saleId, String startTime, String endTime);

    List<TSaleProductPrice> getPageByCondition( TSaleProductPrice entity, int start,  int rows);

    long getPagerCount(TSaleProductPrice entity);

    List<TSaleProductPrice> getExportData(TSaleProductPrice condition);
}