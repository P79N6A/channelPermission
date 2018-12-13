package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.SaleProductStockDataDao;
import com.haier.distribute.data.service.SaleProductStockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SaleProductStockDataServiceImpl implements SaleProductStockDataService {
    @Autowired
    private SaleProductStockDataDao  saleProductStockDataDao;
    //查询可售商品表数据
    public List<Map<String,Object>> selectBystatus(){
        return saleProductStockDataDao.selectBystatus();
    }
}
