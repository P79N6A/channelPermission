

package com.haier.distribute.data.service;

import java.util.List;
import java.util.Map;

import com.haier.distribute.data.model.Channels;
import com.haier.distribute.data.model.PushData;
import com.haier.distribute.data.model.TsendInfoLog;


public interface ChannelsService {
    int deleteByPrimaryKey(Integer id);


    int updateByPrimaryKey(Channels record);

    List<Channels> selectChannels();

    List<Integer> selectId(String distributionCode);


	List<PushData> findPushData(String channelName);


	List<TsendInfoLog> channelCodeSelect(Map<String, Object> params);

}