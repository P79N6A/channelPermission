package com.haier.shop.dao.shopread;

import com.haier.shop.model.SettlementInvoiceQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:JinXueqian
 * @Date: 2018/6/1 10:35
 */
@Mapper
public interface SettlementInvoiceQueueReadDao {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SettlementInvoiceQueue get(Integer id);

    /**
     * 根据id和发票状态查询
     * @param invoicesId
     * @param statusType
     * @return
     */
    SettlementInvoiceQueue getByInvoicesIdAndStatuszType(@Param("invoicesId") Integer invoicesId,
                                                         @Param("statusType") Integer statusType);

    /**
     * 获取处理队列
     * @return
     */
    List<SettlementInvoiceQueue> getBySuccess();
}
