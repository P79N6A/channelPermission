package com.haier.logistics.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.BusinessException;
import com.haier.eis.model.VomContentHandler;
import com.haier.eis.model.VomReceivedQueue;
import com.haier.eis.service.EisVomReceivedQueueService;

/**
 * Abstract VOMContentHandler
 * Created by 钊 on 2014/7/21.
 */
public abstract class AbstractVomContentHandler implements VomContentHandler {

    private static final Logger          LOGGER = LoggerFactory
                                                    .getLogger(AbstractVomContentHandler.class);

//    private final String                 buType;
    @Autowired
    private EisVomReceivedQueueService          vomReceivedQueueDao;

//    private DataSourceTransactionManager transactionManagerEis;

//    public AbstractVomContentHandler(String buType) {
//        Assert.notNull(buType, "BuType must not be null");
//        this.buType = buType;
//    }

//    @Override
//    public final String getBuType() {
//        return buType;
//    }

    @Override
//    @Transactional
    public boolean reprocess(VomReceivedQueue vomReceivedQueue) {

        boolean flag = true;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        try {
            int c = vomReceivedQueueDao.updateDone(vomReceivedQueue.getId());
            if (c <= 0) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("预处理VOM消息（id=" + vomReceivedQueue.getId()
                                + "），更新状态为已完成失败，可能出现并发情况或数据问题，不再处理。");
                }
                return true;
            }
            processContent(vomReceivedQueue);
        } catch (Exception e) {
            LOGGER.error("预处理VOM消息（id=" + vomReceivedQueue.getId() + "）失败：", e);
            flag = false;
        }

        return flag;
    }

    protected final void setError(Integer id, String msg) {
        vomReceivedQueueDao.updateError(id, msg);
    }

    /**
     * 解析contend内容，并处理。
     * 子类需实现此方法
     * 无法处理或者延后处理时请抛出{@code BusinessException}异常，会回滚事物等待下次迭代再进行处理
     * 如果无法处理，（e.g. xml格式不正确），则需要主动调用{@link AbstractVomContentHandler#setError(java.lang.Integer,String)}方法，会修改此记录为失败
     * @param vomReceivedQueue
     * @throws BusinessException
     */
    protected abstract void processContent(VomReceivedQueue vomReceivedQueue)
                                                                             throws BusinessException;

}
