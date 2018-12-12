package com.haier.eis.services;

import com.haier.eis.dao.eis.GQGYSStockDao;
import com.haier.eis.model.GQGYSStock;
import com.haier.eis.service.EisGQGYSStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EisGQGYSStockServiceImpl implements EisGQGYSStockService {
    @Autowired
    private GQGYSStockDao gqgysStockDao;
    @Override
    public List<GQGYSStock> getAll(){
        return gqgysStockDao.getAll();
    }
    @Override
    public List<GQGYSStock> getBySku(String sku){
        return gqgysStockDao.getBySku(sku);
    }
}
