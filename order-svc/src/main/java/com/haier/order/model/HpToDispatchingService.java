package com.haier.order.model;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.order.util.HttpInvokeUtil;
import com.haier.order.util.HttpServiceUtil;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/12/14 0014.
 */
@Service
public class HpToDispatchingService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(HpToDispatchingService.class);
    private String dispatchingByHPUrl;

    public String getDispatchingByHPUrl() {
        return dispatchingByHPUrl;
    }

    public ServiceResult<String> sendHpDispatchNew(String foreignKey, String content) {
        try {
            return HttpServiceUtil.executeServiceAndReturnId(foreignKey,
                    EisInterfaceDataLog.INTERFACE_CODE_DISPATCH_ORDER_TO_HP, content,
                    getDispatchingByHPUrl(), HttpInvokeUtil.METHOD_POST);
        } catch (Exception e) {
            log.error("调用新HP派工接口出错", e);
        }
        return null;
    }
}
