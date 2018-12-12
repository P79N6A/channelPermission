package com.haier.distribute.data.dao.distribute;

import java.util.List;

import com.haier.distribute.data.model.Channels;
import com.haier.distribute.data.model.DistributionInfo;




public interface ChannelsDao {
    int deleteByPrimaryKey(Integer id);


    int updateByPrimaryKey(Channels record);
    List<Channels> selectChannels();
    List<Integer> selectId(String distributionCode);
}