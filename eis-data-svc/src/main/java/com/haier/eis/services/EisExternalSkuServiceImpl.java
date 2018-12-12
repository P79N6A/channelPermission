package com.haier.eis.services;

import com.haier.eis.dao.eis.EisExternalSkuDao;
import com.haier.eis.model.EisExternalSku;
import com.haier.eis.service.EisExternalSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EisExternalSkuServiceImpl implements EisExternalSkuService {
    @Autowired
    private EisExternalSkuDao eisExternalSkuDao;
    @Override
    public List<EisExternalSku> getBySku(String sku){
        return eisExternalSkuDao.getBySku(sku);
    }
    @Override
    public EisExternalSku getBySkuType(String sku,String type){
        return eisExternalSkuDao.getBySkuType(sku,type);
    }
    @Override
    public EisExternalSku getByExternalSku(String externalSku){
        return eisExternalSkuDao.getByExternalSku(externalSku);
    }
    @Override
    public List<EisExternalSku> queryAllExternalSku(){
        return eisExternalSkuDao.queryAllExternalSku();
    }
}
