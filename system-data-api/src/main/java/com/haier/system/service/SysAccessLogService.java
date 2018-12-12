package com.haier.system.service;

import com.haier.system.model.SysAccessLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
public interface SysAccessLogService {

    int create(SysAccessLog accessLog);

    List<SysAccessLog> find(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                            @Param("clientIp") String clientIp, @Param("visitUrl") String visitUrl,
                            @Param("userName") String userName,
                            @Param("sessionId") String sessionId, @Param("start") Integer start,
                            @Param("size") Integer size);

    Integer findCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                      @Param("clientIp") String clientIp, @Param("visitUrl") String visitUrl,
                      @Param("userName") String userName, @Param("sessionId") String sessionId);
}
