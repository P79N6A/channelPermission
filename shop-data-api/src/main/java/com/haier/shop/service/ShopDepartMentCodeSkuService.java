package com.haier.shop.service;


import com.haier.shop.model.DepartMentCodeSku;

import java.util.List;

public interface ShopDepartMentCodeSkuService {

    int insert(DepartMentCodeSku record);
    
    Integer selectKt(String sku); //查询sku是否是空调
    String querySubsku(String sku); //根据网单sku查到子sku
    int deleteByPrimaryKey(Integer id);

    int insertSelective(DepartMentCodeSku record);

    DepartMentCodeSku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartMentCodeSku record);

    int updateByPrimaryKey(DepartMentCodeSku record);

    List<DepartMentCodeSku> selectDepartMentCodeSku(String sku);
}