package com.haier.eop.data.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 调拨单日志操作接口
 */
public interface TransferOrderLogDao {

    int getByName(String name);

    void updateTransferLog(
            @Param("name") String name,
            @Param("updateouttime") String updateouttime,
            @Param("updateintime") String updateintime,
            @Param("querytime") String querytime,
            @Param("out_msg") String out_msg,
            @Param("in_msg") String in_msg,
            @Param("query_msg") String query_msg);

    void insertTransferLog(
            @Param("name") String name,
            @Param("updateouttime") String updateouttime,
            @Param("updateintime") String updateintime,
            @Param("querytime") String querytime,
            @Param("out_msg") String out_msg,
            @Param("in_msg") String in_msg,
            @Param("query_msg") String query_msg);
}
