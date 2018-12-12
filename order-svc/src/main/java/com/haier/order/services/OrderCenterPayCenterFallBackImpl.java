package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.order.util.HttpSecInvokeUtil;
import com.haier.order.util.HttpSecServiceUtil;

public class OrderCenterPayCenterFallBackImpl {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterPayCenterFallBackImpl.class);

    private String payCenterFallBackUrl;

    public String getPayCenterFallBackUrl() {
        return payCenterFallBackUrl;
    }

    public void setPayCenterFallBackUrl(String payCenterFallBackUrl) {
        this.payCenterFallBackUrl = payCenterFallBackUrl;
    }

    public ServiceResult<String> fallBack(String foreignKey, String content) {
        try {
            return HttpSecServiceUtil.executeService(foreignKey,
                    EisInterfaceDataLog.INTERFACE_CODE_PAYCENTER_FALLBACK, content,
                    getPayCenterFallBackUrl(), HttpSecInvokeUtil.METHOD_POST,
                    HttpSecInvokeUtil.CONTENT_TYPE_TEXT);
        } catch (Exception e) {
            log.error("调用支付中心回退接口出错", e);
        }
        return null;
    }

}
