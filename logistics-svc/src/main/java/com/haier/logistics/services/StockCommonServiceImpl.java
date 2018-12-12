package com.haier.logistics.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.logistics.Model.StockCommonModel;
import com.haier.logistics.service.StockCommonService;
import com.haier.stock.model.InvSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockCommonServiceImpl implements StockCommonService {
    private static Logger log = LoggerFactory.getLogger(StockCommonServiceImpl.class);
    @Autowired
    private StockCommonModel stockCommonModel;
    @Override
    public ServiceResult<InvSection> getSectionByCode(String secCode) {
        ServiceResult<InvSection> result = new ServiceResult<InvSection>();
        try {
            result.setResult(stockCommonModel.getSectionByCode(secCode));
        } catch (BusinessException be) {
            log.warn(be.getMessage());
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("根据库位编码(secCode:" + secCode + ")获取库位时，出现未知异常：", e);
            result.setSuccess(false);
            result.setMessage("出现未知异常:" + e.getMessage());
        }
        return result;
    }
    @Override
    public ServiceResult<String> getWhCodeByCenterCode(String centerCode) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            result.setResult(stockCommonModel.getWhCodeByCenterCode(centerCode));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }
}
