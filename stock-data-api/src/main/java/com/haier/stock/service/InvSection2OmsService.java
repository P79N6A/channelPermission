package com.haier.stock.service;


import com.haier.stock.model.InvSection2Oms;

import java.util.List;


public interface InvSection2OmsService {

    /**
     * 获取所有OMS库位匹配WA库位码
     * @return
     */
    public List<InvSection2Oms> getAllOMSSectionInfo();
}
