package com.haier.eop.data.service;

import java.util.List;

import com.haier.eop.data.model.StocksyncProstorage;


public interface StocksyncProstorageService {
    int deleteByPrimaryKey(Integer id);
    int insert(StocksyncProstorage record);
    int updateByPrimaryKeySelective(StocksyncProstorage record);
    List<StocksyncProstorage> Listf(StocksyncProstorage entity, int start, int rows);
    int getPagerCountS(StocksyncProstorage entity);
}