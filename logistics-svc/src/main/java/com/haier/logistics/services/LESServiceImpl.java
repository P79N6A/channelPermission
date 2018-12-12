package com.haier.logistics.services;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.logistics.Util.HttpServiceUtil;
import com.haier.logistics.service.LESService;
import org.springframework.stereotype.Service;

@Service
public class LESServiceImpl implements LESService {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(LESServiceImpl.class);
    private String                         orderToLesUrl="http://58.56.128.10:19001/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=EAI_INT_1353";
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
