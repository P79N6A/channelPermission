package com.haier.logistics.service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvTransferLine;

public interface TransferLineService {
    /**
     * 根据调拨网单号码取得调拨网单
     * @param lineNum
     * @return
     */
    ServiceResult<InvTransferLine> getInvTransferLineByLineNum(String lineNum);
}
