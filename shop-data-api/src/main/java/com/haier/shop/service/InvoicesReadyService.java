package com.haier.shop.service;

import com.haier.shop.model.InvoicesReady;

/*
* 作者:张波
* 2017/12/20
* */
public interface InvoicesReadyService {
    /**
     * 根据网单id获取 转运开票队列
     * @param orderProductId
     * @return
     */
    InvoicesReady getByOrderProductId(Integer orderProductId);
    Integer insert(InvoicesReady invoicesReady);
}
