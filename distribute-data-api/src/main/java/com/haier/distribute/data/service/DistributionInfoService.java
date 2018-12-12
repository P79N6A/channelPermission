package com.haier.distribute.data.service;

import java.util.List;

import com.haier.distribute.data.model.DistributionInfo;


public interface DistributionInfoService {

    List<DistributionInfo> selectChannelsInfo();

    List<Integer> selectId(String distributionCode);
}