package com.haier.stock.services;


import java.util.List;

import com.haier.stock.dao.stock.InvStock2ChannelDao;
import com.haier.stock.model.InvStock2Channel;
import com.haier.stock.service.InvStock2ChannelService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvStock2ChannelServiceImpl implements InvStock2ChannelService {
    @Autowired
    InvStock2ChannelDao invStock2ChannelDao;

    @Override
    public InvStock2Channel get(String sku, String secCode, String channel) {
        // TODO Auto-generated method stub
        return invStock2ChannelDao.get(sku, secCode, channel);
    }

    @Override
    public Integer insert(InvStock2Channel stock2Channel) {
        // TODO Auto-generated method stub
        return invStock2ChannelDao.insert(stock2Channel);
    }

    @Override
    public Integer update(InvStock2Channel stock2Channel) {
        // TODO Auto-generated method stub
        return invStock2ChannelDao.update(stock2Channel);
    }

    @Override
    public List<InvStock2Channel> getInvStockChannelLst() {
        // TODO Auto-generated method stub
        return invStock2ChannelDao.getInvStockChannelLst();
    }

    @Override
    public List<InvStock2Channel> getInvStockBomLst() {
        // TODO Auto-generated method stub
        return invStock2ChannelDao.getInvStockBomLst();
    }
}
