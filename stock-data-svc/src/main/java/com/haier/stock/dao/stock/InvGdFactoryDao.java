package com.haier.stock.dao.stock;

import com.haier.stock.model.InvGdFactory;

import java.util.List;



public interface InvGdFactoryDao {

    /**
     * 基地库--查询基地工厂编码
     * @return
     */
    List<InvGdFactory> queryGdFactory();

}
