package com.haier.system.dao;


import com.haier.system.model.SysSession;

public interface SysSessionDao {
    int create(SysSession session);

    int update(SysSession session);

    SysSession get(String sessionId);

    int delete(String sessionId);
}