package com.haier.eis.dao.eis;

import com.haier.eis.model.EisInterfaceDataLog;

public interface EisInterfaceDataLogDao {

    Integer insert(EisInterfaceDataLog log);

    Integer insertAndReturnId(EisInterfaceDataLog log);


}
