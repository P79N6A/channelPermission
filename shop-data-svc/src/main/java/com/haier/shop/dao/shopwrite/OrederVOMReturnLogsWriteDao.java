package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrederVOMReturnLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * 接收VOM主动推送过来的原始数据
 * @author wukunyang
 *
 */
@Mapper
public interface OrederVOMReturnLogsWriteDao {

    int insert(OrederVOMReturnLogs record);
}