package com.haier.eis.dao.eis;

import com.haier.eis.model.EisInterfaceDataLog;

import java.util.List;
import java.util.Map;

public interface EisInterfaceDataLogDao {

    Integer insert(EisInterfaceDataLog log);

    Integer insertAndReturnId(EisInterfaceDataLog log);


    List<EisInterfaceDataLog> getEisInterfaceList(Map<String, Object> params);

    int getEisInterfaceCNT(Map<String, Object> params);

    List<EisInterfaceDataLog> getEisInterfaceDataList(Map<String, Object> params);
}
