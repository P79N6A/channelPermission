package com.haier.distribute.data.dao.distribute;

import java.util.List;

import com.haier.distribute.data.model.DistributionInfo;





public interface DistributionInfoDao {
  
    List<DistributionInfo> selectChannelsInfo();
    List<Integer> selectId(String distributionCode);
}