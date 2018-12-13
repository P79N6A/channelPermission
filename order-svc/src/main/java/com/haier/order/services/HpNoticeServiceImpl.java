package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.model.HpNoticeModel;
import com.haier.order.service.HpNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HpNoticeServiceImpl implements HpNoticeService {
    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
                                                      .getLogger(HpNoticeServiceImpl.class);
    @Autowired
    private HpNoticeModel hpNoticeModel;

    @Override
    public ServiceResult<Boolean> syncNoticeToHp() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            hpNoticeModel.syncNoticeDataToHp();
            result.setSuccess(true);
            result.setMessage("执行成功");
        } catch (Exception e) {
            logger.error("", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

}
