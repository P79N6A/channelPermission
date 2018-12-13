package com.haier.stock.service.job;

import com.haier.common.ServiceResult;

public interface TransferLineTimingService {
    /**
     * 3W调拨取消同步取消物流－定时任务
     * @return
     */
    ServiceResult<Boolean> cancelTransferLineToLesJob();
    /**
     * job，更新调拨单状态
     * 调拨单推送LES后，状态转为待出库。通过定时任务将调拨单状态更改为待入库和已完成。
     * 在EIS模块，对LES提供接口，接收调拨单状态数据，存放在db_eis.les_stock_trans_queue，直接从这个表取数。
     * @author zhangming
     */
    ServiceResult<Boolean> updateStatusFromLES();
    /**
     * job,已完成的调拨单推送到SAP
     * @return
     */
    ServiceResult<Boolean> orderFinishedPushToSAP();

    /**
     * job,入3W库推送SAP，用于3W调拨
     * @return
     */
    ServiceResult<Boolean> orderIn3WPushToSAP();

    /**
     * job，获取调拨单费用
     * @return
     */
    ServiceResult<Boolean> queryTransferFeeFromHBDMToLES();

    /**
     * 3w－调拨单提交后传Vom
     * @param
     * @return
     */
    ServiceResult<Boolean> syncInnerTransfersToVom();

    /**
     * 优品－入库更新状态
     * @param
     * @return
     */
    ServiceResult<Boolean> sysorderInYPupdate();
}
