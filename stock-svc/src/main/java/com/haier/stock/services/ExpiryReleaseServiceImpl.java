package com.haier.stock.services;

import com.haier.common.ServiceResult;
import com.haier.stock.service.ExpiryReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExpiryReleaseServiceImpl implements ExpiryReleaseService {
    private static Logger log = LoggerFactory.getLogger(ExpiryReleaseServiceImpl.class);
    @Autowired
    private TransferLineModel transferLineModel;
    @Override
    //@Scheduled(cron="0/10 * *  * * ?")
    public ServiceResult<Boolean> autoInnerTransferForReservedStock() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result = transferLineModel.autoInnerTransfer();
        } catch (Exception e) {
            log.error("自动释放预留发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("自动释放预留发生未知异常");
        }
        return result;
    }

}
