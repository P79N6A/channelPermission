package com.haier.eis.model;

import com.haier.common.BusinessException;

/**
 * Created by 钊 on 2014/7/21.
 */
public interface VomContentHandler {

    /**
     * 可处理的通知类型
     * @return 通知类型
     */
    String getBuType();

    /**
     * 预处理content
     * @param vomReceivedQueue
     * @return 处理结果
     */
    boolean reprocess(VomReceivedQueue vomReceivedQueue) throws BusinessException;
}
