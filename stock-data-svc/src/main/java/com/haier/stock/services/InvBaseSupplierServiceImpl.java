package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import com.haier.stock.dao.stock.InvBaseSupplierDao;
import com.haier.stock.model.InvBaseElectBussinessChannel;
import com.haier.stock.model.InvBaseSupplier;
import com.haier.stock.model.InvBaseSupplierForBaseSend;
import com.haier.stock.service.InvBaseSupplierService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvBaseSupplierServiceImpl implements InvBaseSupplierService {
    @Autowired
    InvBaseSupplierDao invBaseSupplierDao;

    @Override
    public List<InvBaseSupplier> getSuppliperInfo(String sale_org_id) {
        // TODO Auto-generated method stub
        return invBaseSupplierDao.getSuppliperInfo(sale_org_id);
    }

    @Override
    public List<InvBaseSupplierForBaseSend> getSuppliperInfoForBaseSend(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return invBaseSupplierDao.getSuppliperInfoForBaseSend(params);
    }

    @Override
    public List<InvBaseElectBussinessChannel> getSAPChannCode(String cbs_channel_code) {
        // TODO Auto-generated method stub
        return invBaseSupplierDao.getSAPChannCode(cbs_channel_code);
    }


}
