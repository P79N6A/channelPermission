package com.haier.stock.service;


import com.haier.stock.model.InvTransferLog;

import java.util.List;

/**
 * 货物调拨操作历史DAO
 *                       
 * @Filename: TransferLogDao.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 *
 */
public interface TransferLogService {

    /**
     * 取得货物调拨操作历史列表
     * @param lineId
     * @return
     */
    List<InvTransferLog> getByLineId(Integer lineId);

    /**
     * 记录货物调拨操作历史
     * @param log
     */
    void insert(InvTransferLog log);
}
