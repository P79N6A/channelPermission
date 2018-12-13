package com.haier.eis.dao.eis;

import com.haier.eis.model.EisInterfaceStatus;

public interface EisInterfaceStatusDao {
	
    EisInterfaceStatus getByInterfaceCode(String interfaceCode);

    Integer update(EisInterfaceStatus eisInterfaceStatus);

}
