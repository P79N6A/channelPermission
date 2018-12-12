package com.haier.distribute.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.distribute.data.dao.distribute.CommissionTargetDao;
import com.haier.distribute.data.model.CommissionTarget;
import com.haier.distribute.data.service.CommissionTargetService;
import org.springframework.stereotype.Service;

@Service
public class CommissionTargetServiceImpl implements CommissionTargetService {
    @Autowired
    CommissionTargetDao commissionTargetDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return commissionTargetDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CommissionTarget record) {
        // TODO Auto-generated method stub
        return commissionTargetDao.insert(record);
    }

    @Override
    public int insertSelective(CommissionTarget record) {
        // TODO Auto-generated method stub
        return commissionTargetDao.insertSelective(record);
    }

    @Override
    public CommissionTarget selectByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return commissionTargetDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CommissionTarget record) {
        // TODO Auto-generated method stub
        return commissionTargetDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CommissionTarget record) {
        // TODO Auto-generated method stub
        return commissionTargetDao.updateByPrimaryKey(record);
    }

    @Override
    public List<CommissionTarget> selectCommission_productListf(CommissionTarget entity, int start, int rows) {
        // TODO Auto-generated method stub
        return commissionTargetDao.selectCommission_productListf(entity, start, rows);
    }

    @Override
    public int getPagerCountS(CommissionTarget entity) {
        // TODO Auto-generated method stub
        return commissionTargetDao.getPagerCountS(entity);
    }

    @Override
    public List<CommissionTarget> selectCommission_target_invoice(int start, int rows, String categoryId,
                                                                  String startTime, String endTime, String policyYear, int channelId, int channelType, int brandId) {
        // TODO Auto-generated method stub
        return commissionTargetDao.selectCommission_target_invoice(start, rows, categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);
    }

    @Override
    public List<CommissionTarget> selectCommission_target_invoice1(int start, int rows, String categoryId,
                                                                   String startTime, String endTime, String policyYear, int channelId, int channelType, int brandId) {
        // TODO Auto-generated method stub
        return commissionTargetDao.selectCommission_target_invoice1(start, rows, categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);
    }

    @Override
    public List<CommissionTarget> selectCommission_target_invoice3(String categoryId, String policyYear, int channelId,
                                                                   int channelType, int brandId) {
        // TODO Auto-generated method stub
        return commissionTargetDao.selectCommission_target_invoice3(categoryId, policyYear, channelId, channelType, brandId);
    }

    @Override
    public List<CommissionTarget> selectCommission_target_invoice2(String categoryId, String policyYear, int channelId,
                                                                   int channelType, int brandId) {
        // TODO Auto-generated method stub
        return commissionTargetDao.selectCommission_target_invoice2(categoryId, policyYear, channelId, channelType, brandId);
    }

    @Override
    public List<CommissionTarget> jiaoyan(CommissionTarget entity) {
        // TODO Auto-generated method stub
        return commissionTargetDao.jiaoyan(entity);
    }

    @Override
    public int getPagerCountInvoice(String categoryId, String startTime, String endTime, String policyYear,
                                    int channelId, int channelType, int brandId) {
        // TODO Auto-generated method stub
        return commissionTargetDao.getPagerCountInvoice(categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);
    }


}