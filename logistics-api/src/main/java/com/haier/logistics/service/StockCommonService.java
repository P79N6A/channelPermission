package com.haier.logistics.service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvSection;

public interface StockCommonService {
    /**
     * 根据库位编码获取库位对象
     *
     * @param secCode
     * @return
     */
    ServiceResult<InvSection> getSectionByCode(String secCode);
    /**
     * 通过日日顺C码获取仓库编码
     * 
     * @param centerCode
     * @return
     */
    ServiceResult<String> getWhCodeByCenterCode(String centerCode);
}
