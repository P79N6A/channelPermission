package com.haier.logistics.services;

import com.haier.common.ServiceResult;
import com.haier.logistics.Model.TransferLineModel;
import com.haier.logistics.service.TransferLineService;
import com.haier.stock.model.InvTransferLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferLineServiceImpl implements TransferLineService{
        private static Logger log = LoggerFactory.getLogger(TransferLineServiceImpl.class);
    @Autowired
    private TransferLineModel transferLineModel;
    @Override
    public ServiceResult<InvTransferLine> getInvTransferLineByLineNum(String lineNum) {
        ServiceResult<InvTransferLine> result = new ServiceResult<InvTransferLine>();
        try {
            InvTransferLine line = transferLineModel.getInvTransferLineByLineNum(lineNum);
            result.setResult(line);
            result.setSuccess(true);
        } catch (Throwable e) {
            log.error("根据调拨网单号码取得调拨网单时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据调拨网单号码取得调拨网单时发生未知异常");
        }
        return result;
    }
}
