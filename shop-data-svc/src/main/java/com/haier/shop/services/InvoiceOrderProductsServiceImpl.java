package com.haier.shop.services;

import com.haier.shop.dao.shopread.InvoiceOrderProductsDao;
import com.haier.shop.service.InvoiceOrderProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:Pineapple
 * @Date: 2018/5/15 15:06
 */
@Service
public class InvoiceOrderProductsServiceImpl implements InvoiceOrderProductsService{

    @Autowired
    private InvoiceOrderProductsDao invoiceOrderProductsDao;

    /**
     * 根据条件查询条数
     * @param
     * @param paramMap
     * @return
     */
    @Override
    public int getCountByParams(Map<String, Object> paramMap) {
        return invoiceOrderProductsDao.getCountByParams(paramMap);
    }

    /**
     * 查询商品订单发票列表
     * @param paramMap
     * @return
     */
    @Override
    public List<Map<String, Object>> getListByParams(Map<String, Object> paramMap) {
        return invoiceOrderProductsDao.getListByParams(paramMap);
    }
}
