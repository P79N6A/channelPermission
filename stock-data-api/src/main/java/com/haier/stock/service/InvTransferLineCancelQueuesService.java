package com.haier.stock.service;

import com.haier.stock.model.InvTransferLineCancelQueues;

import java.util.List;

public interface InvTransferLineCancelQueuesService {

    InvTransferLineCancelQueues get(String lineNum);

    List<InvTransferLineCancelQueues> getUnSend(  Integer count);

    int insert(InvTransferLineCancelQueues invTransferLineCancelQueues);

    int update(InvTransferLineCancelQueues invTransferLineCancelQueues);

}