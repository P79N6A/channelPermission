package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.InvThOrder;
import org.apache.ibatis.annotations.Param;

public interface InvThOrderDao {

    InvThOrder getInvThOrderByChannelSnAndRepairSn(@Param("channelSn") String channelSn,
                                                   @Param("orderRepairSn") String orderRepairSn);

    List<InvThOrder> queryInvThOrderData();

    int updateInvThOrder(InvThOrder invThOrder);

}