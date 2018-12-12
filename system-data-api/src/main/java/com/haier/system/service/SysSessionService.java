package com.haier.system.service;

import com.haier.system.model.SysSession;

/**
 * Created by 李超 on 2018/1/12.
 */
public interface SysSessionService {
    int create(SysSession session);

    int update(SysSession session);

    SysSession get(String sessionId);

    int delete(String sessionId);
}
