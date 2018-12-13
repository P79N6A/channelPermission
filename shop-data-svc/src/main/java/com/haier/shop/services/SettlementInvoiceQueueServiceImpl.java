package com.haier.shop.services;

import com.haier.shop.dao.shopread.SettlementInvoiceQueueReadDao;
import com.haier.shop.dao.shopwrite.SettlementInvoiceQueueWriteDao;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.SettlementInvoiceQueue;
import com.haier.shop.service.SettlementInvoiceQueueService;
import com.haier.shop.util.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author:JinXueqian
 * @Date: 2018/6/1 11:34
 */
@Service
public class SettlementInvoiceQueueServiceImpl implements SettlementInvoiceQueueService {
    private static Logger LOGGER = LoggerFactory.getLogger(SettlementInvoiceQueueServiceImpl.class);


    @Autowired
    private SettlementInvoiceQueueReadDao settlementInvoiceQueueReadDao;
    @Autowired
    private SettlementInvoiceQueueWriteDao settlementInvoiceQueueWriteDao;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public SettlementInvoiceQueue get(Integer id) {
        return settlementInvoiceQueueReadDao.get(id);
    }

    /**
     * 插入
     * @param settlementInvoiceQueue
     * @return
     */
    @Override
    public int insert(SettlementInvoiceQueue settlementInvoiceQueue) {
        return settlementInvoiceQueueWriteDao.insert(settlementInvoiceQueue);
    }

    /**
     * 修改
     * @param settlementInvoiceQueue
     * @return
     */
    @Override
    public int update(SettlementInvoiceQueue settlementInvoiceQueue) {
        return settlementInvoiceQueueWriteDao.update(settlementInvoiceQueue);
    }

    /**
     * 根据id和发票状态查询
     * @param invoicesId
     * @param statusType
     * @return
     */
    @Override
    public SettlementInvoiceQueue getByInvoicesIdAndStatuszType(Integer invoicesId, Integer statusType) {
        return settlementInvoiceQueueReadDao.getByInvoicesIdAndStatuszType(invoicesId,statusType);
    }

    /**
     * 获取处理队列
     * @return
     */
    @Override
    public List<SettlementInvoiceQueue> getBySuccess() {
        return settlementInvoiceQueueReadDao.getBySuccess();
    }

    /**
     * 写佣金核算发票队列
     * @param invoice
     * @param statusType
     */
    @Override
    public void addSettlementInvoiceQueue(Invoices invoice, int statusType) {

        //调用settlementInvoiceQueueDao根据发票的id和状态查询
        SettlementInvoiceQueue settlementInvoiceQueue = settlementInvoiceQueueReadDao.getByInvoicesIdAndStatuszType(invoice.getId(), statusType);
        try {
            if(settlementInvoiceQueue!=null){
                LOGGER.info("佣金核算发票队列已存在[invoiceId=" + invoice.getId() + ",statusType="
                        + statusType + "]");
                //超过20次就更新count为0
                if(settlementInvoiceQueue.getSuccess().equals(0) && settlementInvoiceQueue.getCount() >=20){
                    settlementInvoiceQueue.setCount(0);
                    settlementInvoiceQueue.setLastMessage("[" + DateFormatUtil.format(new Date()) + "]同步程序自动重置count值");
                    settlementInvoiceQueueWriteDao.update(settlementInvoiceQueue);
                }
            }else {
                settlementInvoiceQueue = new SettlementInvoiceQueue();
                settlementInvoiceQueue.setInvoicesId(invoice.getId());
                //1:开票,4:作废
                settlementInvoiceQueue.setStatusType(statusType);
                //'是否成功,0:否,1:是,2:不再处理',
                settlementInvoiceQueue.setSuccess(0);
                //'推送次数, 超过20就不在处理',
                settlementInvoiceQueue.setCount(0);
                int row = settlementInvoiceQueueWriteDao.insert(settlementInvoiceQueue);
                if (row < 1) {
                    LOGGER.error("写入佣金核算发票队列失败[invoiceId=" + invoice.getId() + ",statusType="
                            + statusType + "]");
                }
            }

        }catch (Exception e){
            LOGGER.error("写入佣金核算发票队列异常[invoiceId=" + invoice.getId() + ",statusType=" + statusType
                    + "]:", e);
        }

    }
}
