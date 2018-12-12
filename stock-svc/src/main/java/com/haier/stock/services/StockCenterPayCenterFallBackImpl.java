package com.haier.stock.services;

import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.stock.service.StockCenterPayCenterFallBackService;
import com.haier.stock.util.HttpSecInvokeUtil;
import com.haier.stock.util.HttpSecServiceUtil;
@Service
public class StockCenterPayCenterFallBackImpl implements StockCenterPayCenterFallBackService {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(StockCenterPayCenterFallBackImpl.class);

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
