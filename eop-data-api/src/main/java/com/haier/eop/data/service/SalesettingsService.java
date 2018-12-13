package com.haier.eop.data.service;

import java.util.List;

import com.haier.eop.data.model.Salesettings;



public interface SalesettingsService {
    int deleteByPrimaryKey(Integer id);
    int insert(Salesettings record);
    int updateByPrimaryKeySelective(Salesettings record);
    List<Salesettings> Listf(Salesettings entity, int start, int rows);
    int getPagerCountS(Salesettings entity);
    Salesettings findByWhere(String externalSkus);
}