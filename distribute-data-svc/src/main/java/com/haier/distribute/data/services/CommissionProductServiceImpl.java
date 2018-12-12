package com.haier.distribute.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.distribute.data.dao.distribute.CommissionProductDao;
import com.haier.distribute.data.model.CommissionProduct;
import com.haier.distribute.data.service.CommissionProductService;
import org.springframework.stereotype.Service;

@Service
public class CommissionProductServiceImpl implements CommissionProductService {
    @Autowired
    CommissionProductDao commissionProductDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return commissionProductDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CommissionProduct record) {
        // TODO Auto-generated method stub
        return commissionProductDao.insert(record);
    }

    @Override
    public int insertSelective(CommissionProduct record) {
        // TODO Auto-generated method stub
        return commissionProductDao.insertSelective(record);
    }

    @Override
    public CommissionProduct selectByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return commissionProductDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CommissionProduct record) {
        // TODO Auto-generated method stub
        return commissionProductDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CommissionProduct record) {
        // TODO Auto-generated method stub
        return commissionProductDao.updateByPrimaryKey(record);
    }

    @Override
    public int getPagerCountS(CommissionProduct entity) {
        // TODO Auto-generated method stub
        return commissionProductDao.getPagerCountS(entity);
    }

    @Override
    public List<CommissionProduct> selectCommission(CommissionProduct entity) {
        // TODO Auto-generated method stub
        return commissionProductDao.selectCommission(entity);
    }

    @Override
    public List<CommissionProduct> selectCommission_productListf(CommissionProduct entity, int start, int rows) {
        // TODO Auto-generated method stub
        return commissionProductDao.selectCommission_productListf(entity, start, rows);
    }

    @Override
    public List<CommissionProduct> selectexportCommission_productListf(CommissionProduct entity) {
        // TODO Auto-generated method stub
        return commissionProductDao.selectexportCommission_productListf(entity);
    }

    @Override
    public List<CommissionProduct> skuAll(String sku, Integer channtype, Integer policyYear) {
        // TODO Auto-generated method stub
        return commissionProductDao.skuAll(sku, channtype, policyYear);
    }

    @Override
    public List<CommissionProduct> jiaoyan(CommissionProduct commission) {
        // TODO Auto-generated method stub
        return commissionProductDao.jiaoyan(commission);
    }


}