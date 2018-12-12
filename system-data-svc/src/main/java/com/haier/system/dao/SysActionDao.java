package com.haier.system.dao;

import com.haier.system.model.SysAction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysActionDao {
    List<SysAction> find(@Param("actKey") String actKey, @Param("actName") String actName,
                         @Param("remark") String remark, @Param("status") Integer status,
                         @Param("menuName") String menuName, @Param("start") Integer start,
                         @Param("size") Integer size);

    Integer findCount(@Param("actKey") String actKey, @Param("actName") String actName,
                      @Param("remark") String remark, @Param("status") Integer status,
                      @Param("menuName") String menuName, @Param("start") Integer start,
                      @Param("size") Integer size);

    List<SysAction> findByFn(@Param("fnId") String fnId);

    SysAction findByUrl(@Param("url") String url);

    SysAction findByActKey(@Param("actKey") String actKey);

    SysAction get(int actId);

    int update(SysAction action);

    void create(SysAction action);
}