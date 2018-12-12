package com.haier.eis.service;

import com.haier.eis.model.EisInterfaceStatus;

public interface EisInterfaceStatusService {
    EisInterfaceStatus getByInterfaceCode(String interfaceCode);

    Integer update(EisInterfaceStatus eisInterfaceStatus);

}
