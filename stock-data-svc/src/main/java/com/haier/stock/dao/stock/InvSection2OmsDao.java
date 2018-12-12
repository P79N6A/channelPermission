package com.haier.stock.dao.stock;


import com.haier.stock.model.InvSection2Oms;

import java.util.List;

public interface InvSection2OmsDao {

    /**
     * 获取所有OMS库位匹配WA库位码
     * @return
     */
    public List<InvSection2Oms> getAllOMSSectionInfo();
}
