package com.haier.shop.services;
import com.haier.shop.dao.shopread.OrderProductsReadDao;
import com.haier.shop.service.TBOrderSnExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
/**
 * @Author:JinXueqian
 * @Date: 2018/8/6 18:03
 */
@Service
public class TBOrderSnExportServiceImpl implements TBOrderSnExportService {
    @Autowired
    OrderProductsReadDao orderProductsReadDao;
    @Override
    public List<Map<String, Object>> getTBOrderSnBySourceOrderSn(Map<String, Object> paramMap) {
        return orderProductsReadDao.getTBOrderSnBySourceOrderSn(paramMap);
    }
}