package com.haier.distribute.data.service;

import java.util.List;

import com.haier.distribute.data.model.CommissionProduct;


public interface CommissionProductService {
    int deleteByPrimaryKey(Integer id);

    int insert(CommissionProduct record);

    int insertSelective(CommissionProduct record);

    CommissionProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommissionProduct record);

    int updateByPrimaryKey(CommissionProduct record);

    int getPagerCountS(CommissionProduct entity);

    //根据条件查询结果List
    List<CommissionProduct> selectCommission(CommissionProduct entity);

    List<CommissionProduct> selectCommission_productListf(CommissionProduct entity, int start, int rows);

    List<CommissionProduct> selectexportCommission_productListf(CommissionProduct entity);

    List<CommissionProduct> skuAll(String sku, Integer channtype, Integer policyYear);

    List<CommissionProduct> jiaoyan(CommissionProduct commission);

}