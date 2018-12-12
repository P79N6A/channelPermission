package com.haier.shop.services;



import com.haier.shop.dao.shopread.LesShippingInfosRradDao;
import com.haier.shop.model.LesShippingInfos;
import com.haier.shop.service.LesShippingInfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LesShippingInfosServiceImpl implements LesShippingInfosService {
    @Autowired
    private LesShippingInfosRradDao lesShippingInfosDao;
    public List<LesShippingInfos> getByCorderSn(String corderSn){
        return lesShippingInfosDao.getByCorderSn(corderSn);
    }

    public Integer insert(LesShippingInfos lesShippingInfos){
        return lesShippingInfosDao.insert(lesShippingInfos);
    }
}
