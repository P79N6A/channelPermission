package com.haier.shop.dao.shopread;

import com.haier.shop.model.Order4Invoices;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Order4InvoicesReadDao {
    /**
     * 根据id获取专项开票对象
     * @param id
     * @return
     */
    Order4Invoices get(Integer id);

}
