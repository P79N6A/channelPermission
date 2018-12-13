package com.haier.shop.service;

import java.util.List;
import java.util.Map;

/**
 * @Author:Pineapple
 * @Date: 2018/5/15 15:00
 */
public interface InvoiceOrderProductsService {

    /**
     * 查询条数
     * @param
     * @param paramMap
     * @return
     */
     int  getCountByParams(Map<String, Object> paramMap);

    /**
     *分页查询
     * @param paramMap
     * @return
     */
     List<Map<String,Object>> getListByParams(Map<String, Object> paramMap);
}
