package com.haier.distribute.data.service;/**
 * Created by Administrator on 2017/11/7 0007.
 */

import com.haier.distribute.data.model.TChannelsInfo;

import java.math.BigDecimal;
import java.util.List;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 10:39
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface TChanneclsInfoService {
    List<TChannelsInfo> getList();

    TChannelsInfo selectOneByCode(String channelcode);

    TChannelsInfo selectOneByName(String channelcname);

    TChannelsInfo checkNameForEdit(String channelcname, Long id);

    TChannelsInfo selectPriceByOrderId(String id);

    TChannelsInfo selectOneByThisId(String id);

    int updatePrice(String id, BigDecimal price);

    int addPrice(String id, BigDecimal price);

    int insertOne(TChannelsInfo tChanneclsInfo);

    int updateByPrimaryKeySelective(TChannelsInfo record);

    int deleteByPrimaryKey(Long id);

    List<TChannelsInfo> getPageByCondition(TChannelsInfo entity, int start, int rows);

    long getPagerCount(TChannelsInfo entity);

    TChannelsInfo getOneByCode(String channelCode);
}
