package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.Order2ths;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Order2thsWriteDao {

    int update(Order2ths order2ths);

    int updateForsynInvoices(Order2ths order2ths);

}
