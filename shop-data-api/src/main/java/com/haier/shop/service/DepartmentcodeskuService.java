package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.DepartMentCodeSku;




public interface DepartmentcodeskuService {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartMentCodeSku record);

    int insertSelective(DepartMentCodeSku record);

    DepartMentCodeSku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartMentCodeSku record);

    int updateByPrimaryKey(DepartMentCodeSku record);
    
    List<DepartMentCodeSku>selectDepartMentCodeSku(String sku);
}