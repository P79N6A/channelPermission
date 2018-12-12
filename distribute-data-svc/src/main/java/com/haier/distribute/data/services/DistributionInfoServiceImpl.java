package com.haier.distribute.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.distribute.data.dao.distribute.DistributionInfoDao;
import com.haier.distribute.data.model.DistributionInfo;
import com.haier.distribute.data.service.DistributionInfoService;
import org.springframework.stereotype.Service;


@Service
public class DistributionInfoServiceImpl implements DistributionInfoService {
    @Autowired
    DistributionInfoDao DistributionInfoDao;

    @Override
    public List<DistributionInfo> selectChannelsInfo() {
        // TODO Auto-generated method stub
        return DistributionInfoDao.selectChannelsInfo();
    }

    @Override
    public List<Integer> selectId(String distributionCode) {
        // TODO Auto-generated method stub
        return DistributionInfoDao.selectId(distributionCode);
    }

}