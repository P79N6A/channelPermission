package com.haier.shop.service;


import com.haier.shop.model.SuningGroups;

import java.util.List;

public interface ShopSuningGroupsService {
    int deleteByPrimaryKey(Integer id);
    int insert(SuningGroups record);
    int updateByPrimaryKeySelective(SuningGroups record);
    List<SuningGroups> Listf(SuningGroups entity, int start, int rows);
    int getPagerCountS(SuningGroups entity);
}
