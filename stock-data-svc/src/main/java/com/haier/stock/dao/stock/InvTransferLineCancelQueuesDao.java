package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.InvTransferLineCancelQueues;
import org.apache.ibatis.annotations.Param;


public interface InvTransferLineCancelQueuesDao {

    InvTransferLineCancelQueues get(@Param("lineNum") String lineNum);

    List<InvTransferLineCancelQueues> getUnSend(@Param("count") Integer count);

    int insert(InvTransferLineCancelQueues invTransferLineCancelQueues);

    int update(InvTransferLineCancelQueues invTransferLineCancelQueues);

}