package com.haier.distribute.data.service;

import java.util.List;

import com.haier.distribute.data.model.Channels;
import com.haier.distribute.data.model.DistributionInfo;


public interface ChannelsService {
    int deleteByPrimaryKey(Integer id);


    int updateByPrimaryKey(Channels record);

    List<Channels> selectChannels();

    List<Integer> selectId(String distributionCode);
}