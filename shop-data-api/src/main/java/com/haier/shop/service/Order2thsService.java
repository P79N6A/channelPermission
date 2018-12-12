package com.haier.shop.service;

import com.haier.shop.model.Order2ths;

public interface Order2thsService {
    /**
     * 根据id获取差异订单对象
     * @param id
     * @return
     */
    Order2ths get(Integer id);

    int update(Order2ths order2ths);

    int updateForsynInvoices(Order2ths order2ths);

}
