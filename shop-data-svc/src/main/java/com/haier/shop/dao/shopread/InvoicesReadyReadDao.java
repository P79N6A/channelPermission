package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoicesReady;
import org.apache.ibatis.annotations.Mapper;

/*
* 作者:张波
* 2017/12/20
* */
@Mapper
public interface InvoicesReadyReadDao {
    /**
     * 根据网单id获取 转运开票队列
     * @param orderProductId
     * @return
     */
    InvoicesReady getByOrderProductId(Integer orderProductId);
}
