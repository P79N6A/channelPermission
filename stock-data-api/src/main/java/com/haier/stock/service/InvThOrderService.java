package com.haier.stock.service;

import com.haier.stock.model.InvThOrder;

import java.util.List;

public interface InvThOrderService {

    InvThOrder getInvThOrderByChannelSnAndRepairSn(String channelSn,
                                                   String orderRepairSn);

    List<InvThOrder> queryInvThOrderData();

    int updateInvThOrder(InvThOrder invThOrder);

}