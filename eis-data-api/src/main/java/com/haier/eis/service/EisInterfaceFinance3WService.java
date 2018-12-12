package com.haier.eis.service;

import java.util.List;

import com.haier.eis.model.EisInterfaceFinance3w;



public interface EisInterfaceFinance3WService {
    EisInterfaceFinance3w getByTransQueue3WId(Integer transQueue3WId,String interfaceCode);

    List<EisInterfaceFinance3w> getByStatus(Integer status);

    Integer insert(EisInterfaceFinance3w interfaceFinance);

    Integer update(EisInterfaceFinance3w interfaceFinance);
}
