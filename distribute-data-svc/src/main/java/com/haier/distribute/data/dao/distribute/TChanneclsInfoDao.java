package com.haier.distribute.data.dao.distribute;/**
 * Created by Administrator on 2017/11/7 0007.
 */

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.distribute.data.model.TChannelsInfo;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 10:39
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface TChanneclsInfoDao extends BaseDao<TChannelsInfo> {
    List<TChannelsInfo> getList();
    TChannelsInfo selectOneByCode(String channelcode);

    TChannelsInfo selectOneByName(String channelcname);

    TChannelsInfo checkNameForEdit(@Param("channelcname")String channelcname, @Param("id")Long id);

    TChannelsInfo selectPriceByOrderId(String id);

    TChannelsInfo selectOneByThisId(String id);

    int updatePrice(@Param("id")String id,@Param("price")BigDecimal price);

    int addPrice(@Param("id")String id,@Param("price")BigDecimal price);

    int insertOne(TChannelsInfo tChanneclsInfo);

    int updateByPrimaryKeySelective(TChannelsInfo record);

    int deleteByPrimaryKey(Long id);

    TChannelsInfo getOneByCode(String channelCode);
}
