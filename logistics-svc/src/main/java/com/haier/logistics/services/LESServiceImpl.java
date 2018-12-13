package com.haier.logistics.services;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.logistics.Util.HttpServiceUtil;
import com.haier.logistics.service.LESService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ConfigurationProperties(prefix = "url")
@Service
public class LESServiceImpl implements LESService {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(LESServiceImpl.class);
    private String                         orderToLesUrl;
    @Override
    public ServiceResult<String> orderToLes(String foreignKey, final String content) {
        final ServiceResult<String> result = new ServiceResult<String>();
        try {
            return HttpServiceUtil.executeService(foreignKey,
                    EisInterfaceDataLog.INTERFACE_CODE_ORDER_TO_LES, content, getOrderToLesUrl());
        } catch (Exception e) {
            log.error("调用les开提单接口出错", e);
        }
        return result;
    }
    public String getOrderToLesUrl() {
        return orderToLesUrl;
    }
}
