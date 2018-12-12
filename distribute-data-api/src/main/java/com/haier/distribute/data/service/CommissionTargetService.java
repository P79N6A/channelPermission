package com.haier.distribute.data.service;

import java.util.List;

import com.haier.distribute.data.model.CommissionTarget;


public interface CommissionTargetService {
    int deleteByPrimaryKey(Integer id);

    int insert(CommissionTarget record);

    int insertSelective(CommissionTarget record);

    CommissionTarget selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommissionTarget record);

    int updateByPrimaryKey(CommissionTarget record);

    List<CommissionTarget> selectCommission_productListf(CommissionTarget entity, int start, int rows);

    int getPagerCountS(CommissionTarget entity);

    List<CommissionTarget> selectCommission_target_invoice(int start, int rows,
                                                           String categoryId, String startTime, String endTime,
                                                           String policyYear, int channelId, int channelType, int brandId);

    //月度
    List<CommissionTarget> selectCommission_target_invoice1(int start, int rows,
                                                            String categoryId, String startTime, String endTime,
                                                            String policyYear, int channelId, int channelType, int brandId);

    //月度导出
    List<CommissionTarget> selectCommission_target_invoice3(
            String categoryId,
            String policyYear, int channelId, int channelType, int brandId);

    //季度导出
    List<CommissionTarget> selectCommission_target_invoice2(
            String categoryId,
            String policyYear, int channelId, int channelType, int brandId);

    List<CommissionTarget> jiaoyan(CommissionTarget entity);

    int getPagerCountInvoice(String categoryId, String startTime, String endTime,
                             String policyYear, int channelId, int channelType, int brandId);


}