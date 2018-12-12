package com.haier.distribute.data.services;/**
 * Created by Administrator on 2tChanneclsInfoDao17/11/7 tChanneclsInfoDaotChanneclsInfoDaotChanneclsInfoDao7.
 */

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.TChanneclsInfoDao;
import com.haier.distribute.data.model.TChannelsInfo;
import com.haier.distribute.data.service.TChanneclsInfoService;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2tChanneclsInfoDao17/11/7 tChanneclsInfoDaotChanneclsInfoDaotChanneclsInfoDao7
 * \* Time: 1tChanneclsInfoDao:39
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class TChanneclsInfoServiceImpl implements TChanneclsInfoService {
    @Autowired
    TChanneclsInfoDao tChanneclsInfoDao;

    @Override
    public List<TChannelsInfo> getList() {
        return tChanneclsInfoDao.getList();
    }

    @Override
    public TChannelsInfo selectOneByCode(String channelcode) {
        return tChanneclsInfoDao.selectOneByCode(channelcode);
    }

    @Override
    public TChannelsInfo selectOneByName(String channelcname) {
        return tChanneclsInfoDao.selectOneByName(channelcname);
    }

    @Override
    public TChannelsInfo checkNameForEdit(String channelcname, Long id) {
        return tChanneclsInfoDao.checkNameForEdit(channelcname, id);
    }

    @Override
    public TChannelsInfo selectPriceByOrderId(String id) {
        return tChanneclsInfoDao.selectPriceByOrderId(id);
    }

    @Override
    public TChannelsInfo selectOneByThisId(String id) {
        return tChanneclsInfoDao.selectOneByThisId(id);
    }

    @Override
    public int updatePrice(String id, BigDecimal price) {
        return tChanneclsInfoDao.updatePrice(id, price);
    }

    @Override
    public int addPrice(String id, BigDecimal price) {
        return tChanneclsInfoDao.addPrice(id, price);
    }

    @Override
    public int insertOne(TChannelsInfo tChanneclsInfo) {
        return tChanneclsInfoDao.insertOne(tChanneclsInfo);
    }

    @Override
    public int updateByPrimaryKeySelective(TChannelsInfo record) {
        return tChanneclsInfoDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return tChanneclsInfoDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<TChannelsInfo> getPageByCondition(TChannelsInfo entity, int start, int rows) {
        // TODO Auto-generated method stub
        return tChanneclsInfoDao.getPageByCondition((TChannelsInfo) entity, start, rows);
    }

    @Override
    public long getPagerCount(TChannelsInfo entity) {
        // TODO Auto-generated method stub
        return tChanneclsInfoDao.getPagerCount((TChannelsInfo) entity);
    }

    @Override
    public TChannelsInfo getOneByCode(String channelCode) {
        return tChanneclsInfoDao.getOneByCode(channelCode);
    }
}
