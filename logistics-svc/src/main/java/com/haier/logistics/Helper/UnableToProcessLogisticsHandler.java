package com.haier.logistics.Helper;

import com.haier.common.BusinessException;
import com.haier.eis.model.VomShippingStatus;
import org.springframework.stereotype.Service;

@Service
public class UnableToProcessLogisticsHandler extends LogisticsHandler {
    @Override
    public void process(VomShippingStatus shippingStatus) throws BusinessException {
        setProcessError(shippingStatus.getId(), "无法处理物流数据");
    }
}
