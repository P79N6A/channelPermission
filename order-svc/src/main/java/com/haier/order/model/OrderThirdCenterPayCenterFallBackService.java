package com.haier.order.model;

import com.haier.common.ServiceResult;
import org.springframework.stereotype.Service;

@Service
public interface OrderThirdCenterPayCenterFallBackService {

    /**
     * 支付中心回退接口
     * @param foreignKey 记录接口日志的数据id,一般记录订单或网单id
     * @param content 调用接口数据
     * @return
     */
    ServiceResult<String> fallBack(String foreignKey, String content);

}
