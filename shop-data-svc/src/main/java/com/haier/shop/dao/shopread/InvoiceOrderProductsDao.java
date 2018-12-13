package com.haier.shop.dao.shopread;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author:Pineapple
 * @Date: 2018/5/15 15:11
 */
public interface InvoiceOrderProductsDao {
    /**
     * 根据条件查询条数
     * @param paramMap
     * @return
     */
     int  getCountByParams(Map<String, Object> paramMap);

    /**
     *查询
     * @param paramMap
     * @return
     */
     List<Map<String,Object>> getListByParams(Map<String, Object> paramMap);

}
