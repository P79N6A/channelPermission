package com.haier.shop.services;



import com.haier.shop.dao.shopread.LesShippingInfosRradDao;
import com.haier.shop.dao.shopwrite.LesShippingInfosWriteDao;
import com.haier.shop.model.LesShippingInfos;
import com.haier.shop.service.LesShippingInfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LesShippingInfosServiceImpl implements LesShippingInfosService {
    @Autowired
    private LesShippingInfosRradDao lesShippingInfosDao;
    @Autowired
    private LesShippingInfosWriteDao lesShippingInfosWriteDao;
    @Override
    public List<LesShippingInfos> getByCorderSn(String corderSn){
        return lesShippingInfosDao.getByCorderSn(corderSn);
    }
    @Override
    public Integer insert(LesShippingInfos lesShippingInfos){
        return lesShippingInfosWriteDao.insert(lesShippingInfos);
    }
}
