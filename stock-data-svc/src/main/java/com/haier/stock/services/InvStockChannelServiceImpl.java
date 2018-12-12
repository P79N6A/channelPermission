package com.haier.stock.services;

import com.haier.stock.dao.stock.InvStockChannelDao;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.service.InvStockChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InvStockChannelServiceImpl implements InvStockChannelService {
    @Autowired
            private InvStockChannelDao invStockChannelDao;
    /**
     * 获取全部渠道信息
     * @return
     */
   public List<InvStockChannel> getAll(){
        return invStockChannelDao.getAll();
    }

   public InvStockChannel getByCode(String channel_code){
       return invStockChannelDao.getByCode(channel_code);
    }
}
