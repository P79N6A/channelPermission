package com.haier.order.service;

import com.haier.common.ServiceResult;
import com.haier.order.model.NoticeToHpInputType;

/**
 * 合并尾款通知hp发货接口
 *                       
 * @Filename: HpToNoticeService.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public interface HpToNoticeService {

    /**
     * HP通知发货推送数据
     * foreignKey 标识
     * pushData 接口参数
     */
    ServiceResult<String> noticeHpToSend(String foreignKey, NoticeToHpInputType pushData);

}
