package com.haier.system.service;

import com.haier.system.model.BaseErr;

import java.util.List;
import java.util.Map;

/**
 * Created by 李超 on 2018/1/12.
 */
public interface BaseErrService {
    Integer adderr(BaseErr base);

    List<BaseErr> queryErr(Map<String, Object> map);

    int getRowCnts(Map<String, Object> map);
}
