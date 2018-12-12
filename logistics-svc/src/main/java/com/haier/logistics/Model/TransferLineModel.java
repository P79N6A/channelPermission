package com.haier.logistics.Model;

import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.service.StockTransferLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferLineModel {
    @Autowired
    private StockTransferLineService transferLineDao;
    /**
     * 根据调拨网单号码取得调拨网单
     * @param lineNum
     * @return
     */
    public InvTransferLine getInvTransferLineByLineNum(String lineNum) {
        if (StringUtil.isEmpty(lineNum))
            return null;
        InvTransferLine line = transferLineDao.getInvTransferLineByLineNum(lineNum);
        return line;
    }
}
