package com.haier.shop.dao.shopread;

import com.haier.shop.model.HpNoticeQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface HpNoticeQueuesReadDao {

    List<HpNoticeQueues> getNoticeQueuesList(Integer max);
}
