package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.PopProductDao;
import com.haier.distribute.data.service.SaleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SaleProductServiceImpl implements SaleProductService {
    @Autowired
    private PopProductDao popProductDao;
    public List<String> selectAllSku(){
        return popProductDao.selectAllSku();
    }
}
