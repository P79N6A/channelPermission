package com.haier.afterSale.services;

import com.haier.afterSale.service.PayCenterFallBackService;
import com.haier.afterSale.util.HttpSecInvokeUtil;
import com.haier.afterSale.util.HttpSecServiceUtil;
import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import org.springframework.stereotype.Service;

@Service
public class PayCenterFallBackImpl implements PayCenterFallBackService {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(PayCenterFallBackImpl.class);

    private String payCenterFallBackUrl;

    public String getPayCenterFallBackUrl() {
        return payCenterFallBackUrl;
    }

    public void setPayCenterFallBackUrl(String payCenterFallBackUrl) {
        this.payCenterFallBackUrl = payCenterFallBackUrl;
    }

    @Override
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
