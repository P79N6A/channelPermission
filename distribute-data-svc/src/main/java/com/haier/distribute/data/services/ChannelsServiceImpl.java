

package com.haier.distribute.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.distribute.data.dao.distribute.ChannelsDao;
import com.haier.distribute.data.dao.distribute.TsendInfoLogDao;
import com.haier.distribute.data.model.Channels;
import com.haier.distribute.data.model.PushData;
import com.haier.distribute.data.model.TsendInfoLog;
import com.haier.distribute.data.service.ChannelsService;
import org.springframework.stereotype.Service;


@Service
public class ChannelsServiceImpl implements ChannelsService {
    @Autowired
    ChannelsDao channelsDao;
    @Autowired
    TsendInfoLogDao tsendInfoLogDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return channelsDao.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Channels record) {
        // TODO Auto-generated method stub
        return channelsDao.updateByPrimaryKey(record);
    }

    @Override
    public List<Channels> selectChannels() {
        // TODO Auto-generated method stub
        return channelsDao.selectChannels();
    }

	@Override
	public List<Integer> selectId(String distributionCode) {
		// TODO Auto-generated method stub
		  return channelsDao.selectId(distributionCode);
	}

	@Override
	public List<PushData> findPushData(String channelName) {
		// TODO Auto-generated method stub
		return channelsDao.findPushData(channelName);
	}

	@Override
	public List<TsendInfoLog> channelCodeSelect(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tsendInfoLogDao.channelCodeSelect(params);
	}

}


