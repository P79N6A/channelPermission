package com.haier.invoice.service;

import com.haier.shop.model.InvoiceExceptionDispItem;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-08
 **/
public interface InvoiceExceptionService {

    /**
     * 查询异常发票展示列表
     * @param paramMap
     * @return
     */
    Map<String, Object> getInvoiceExceptionListByPage(Map<String, Object> paramMap);

    /**
     * 查询异常发票展示列表
     * @param paramMap
     * @return
     */
    List<InvoiceExceptionDispItem> getInvoiceExceptionList(Map<String, Object> paramMap);

    /**
     * 获得条数
     */
    public int getCount();
}
