package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoiceQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-10
 **/
@Mapper
public interface InvoiceQueueReadDao {

    /**
     * 根据网单id查询invoice_queue中是否存在
     * @param opId
     * @return
     */
    int getInvoiceQueueExistFlag(@Param("opId") Integer opId);

    List<InvoiceQueue> getByOrderProductId(int orderProductId);

    /**
     * 根据状态，获取列表
     * @param success
     * @return
     */
    List<InvoiceQueue> getBySuccess(Integer success);
}
