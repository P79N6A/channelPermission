package com.haier.shop.services;

import com.haier.shop.dao.shopread.ExternalOrdersReadDao;
import com.haier.shop.dao.shopwrite.ExternalOrdersWriteDao;
import com.haier.shop.dao.shopwrite.ExternalOrdersWriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import com.haier.shop.service.ExternalOrdersService;
import org.springframework.stereotype.Service;

@Service
public class ExternalOrdersServiceImpl implements ExternalOrdersService {

    @Autowired
    private ExternalOrdersReadDao externalOrdersDao;
    @Autowired
    private ExternalOrdersWriteDao externalOrdersWriteDao;

   /* @Override
    public ServiceResult<String> sendForwardReverseToAccount(String foreignKey, String content) {
        // TODO Auto-generated method stub
        return externalOrdersDao.sendForwardReverseToAccount(foreignKey, content);
    }*/


    @Override
    public Integer updateAtferShipped(Integer orderId) {
        return externalOrdersWriteDao.updateAtferShipped(orderId);
    }
}
