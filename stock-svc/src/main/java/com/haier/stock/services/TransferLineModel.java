package com.haier.stock.services;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.QueryPayTimeToLes;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.model.InvBaseStock;
import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.model.InvReservedToRelease;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStock;
import com.haier.stock.model.InvStockBatch;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InvTransferLineCancelQueues;
import com.haier.stock.model.InvTransferLog;
import com.haier.stock.model.InvWarehouseInfo;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.model.VomInterData;
import com.haier.stock.service.InvReservedToReleaseService;
import com.haier.stock.service.InvStockService;
import com.haier.stock.service.InvTransferLineCancelQueuesService;
import com.haier.stock.service.InvWarehouseInfoService;
import com.haier.stock.service.StockInvBaseStockService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockInvStockBatchService;
import com.haier.stock.service.StockTransferLineService;
import com.haier.stock.service.TransferLogService;
import com.haier.stock.util.AccessExternalInterface;
import com.haier.stock.util.HttpResult;

/**
 * 货物调拨管理model类
 * @param <T>
 *                       
 * @Filename: TransferModel.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 *
 */
@Service
public class TransferLineModel<T> {

    private static Logger                  log = LogManager.getLogger(TransferLineModel.class);
    @Autowired
    private StockServiceImpl                   stockService;
    @Autowired
    private StockCenterItemServiceImplNew                    itemService;
    @Autowired
    private com.haier.stock.service.StockTransferLineService transferLineDao;
    private DataSourceTransactionManager   transactionManagerStock;
    @Autowired
    private TransferLogService                 transferLogDao;
    @Autowired
    private InvStockService                    invStockDao;
    @Autowired
    private com.haier.stock.service.StockInvStockBatchService               invStockBatchDao;
    @Autowired
    private com.haier.stock.service.InvReservedToReleaseService        invReservedToReleaseDao;
    @Autowired
    private com.haier.stock.service.StockInvBaseStockService                invBaseStockDao;
    @Autowired
    private com.haier.stock.service.StockInvSectionService                 invSectionDao;
    @Autowired
    private AccessExternalInterface        accessExternalInterface;
    @Autowired
    private LESServiceImpl                     lesService;
    @Autowired
    private com.haier.stock.service.InvWarehouseInfoService            invWarehouseInfoDao;
    @Autowired
    private com.haier.stock.service.InvTransferLineCancelQueuesService invTransferLineCancelQueuesDao;

    /**
     * 系统定时任务， 1.渠道到期释放库存；2.释放至共享库
     * @return
     */
    public ServiceResult<Boolean> autoInnerTransfer() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        Date now = new Date();
        int pageSize = 1000;
        int pageIndex = 1;
        int startIndex = 0;
        List<InvStockBatch> releaseRows;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        StringBuffer message = new StringBuffer();
        while (true) {
            startIndex = (pageIndex - 1) * pageSize;
            releaseRows = invStockBatchDao.queryInvReleaseStock(now, startIndex, pageSize);
            if (releaseRows == null || releaseRows.size() <= 0) {
                break;
            }
            beginTransferLine(releaseRows, def, message);
            pageIndex++;
        }
        result.setMessage(message.toString());
        return result;
    }

    private void beginTransferLine(List<InvStockBatch> releaseRows,
                                   DefaultTransactionDefinition def, StringBuffer message) {
        InvTransferLine transferLine;
        TransactionStatus status;
        InvSection section;
        ServiceResult<Boolean> frozenResult;
        InvReservedToRelease reservedToRelease;
        for (InvStockBatch row : releaseRows) {
            //status = transactionManagerStock.getTransaction(def);
            try {
                reservedToRelease = new InvReservedToRelease();
                reservedToRelease.setStatus(InvReservedToRelease.STATUS_DONE);
                reservedToRelease.setBatchId(row.getId());
                if (row.getStockQty() <= 0) {
                    reservedToRelease.setRemark("stock_qty[0],无需释放");
                    this.invReservedToReleaseDao.updateReserved(reservedToRelease);
                    //transactionManagerStock.commit(status);
                    message.append("没有可调拨的库存，batchid:" + row.getId()).append(",");
                    log.debug("没有可调拨的库存，batchid:" + row.getId());
                    continue;
                }
                int releaseCnt = getReleaseCnt(row, reservedToRelease);
                log.debug("虚拟调拨计算出的释放数：" + releaseCnt);
                if (releaseCnt <= 0) {
                    this.invReservedToReleaseDao.updateReserved(reservedToRelease);
                    //transactionManagerStock.commit(status);
                    continue;
                }
                section = invSectionDao.getBySecCode(row.getSecCode());
                transferLine = new InvTransferLine();
                transferLine.setSecFrom(row.getSecCode());
                transferLine.setItemCode(row.getSku());
                transferLine.setTransferQty(releaseCnt);
                transferLine.setQty(releaseCnt);
                transferLine.setChannelFrom(section.getChannelCode());
                transferLine.setChannelTo(InvSection.CHANNEL_CODE_WA);
                /* transferLine.setChannelId(section.getChannelCode());*/
                setTransferLineSetTo(transferLine, section);
                //2.设置物料名称
                ServiceResult<ItemBase> base = itemService.getItemBaseBySku(row.getSku());
                if (base.getSuccess()) {
                    transferLine.setItemName(base.getResult().getMaterialDescription());
                } else {
                    log.info("beginTransferLine：没有或获取到物料名称, sku=" + row.getSku());
                }
                transferLine = this.insertTransferLine(transferLine, "System",
                    InvTransferLine.LINE_STATUS_LES);
                log.debug("虚拟调拨编号(batchid):" + row.getId() + "，调拨编号：" + transferLine.getLineId()
                          + ", 调拨单号：" + transferLine.getLineNum());
                this.recordTransferLog(transferLine.getLineId(), InvTransferLog.LOG_TYPE_NEW,
                    "System", new Date(), "自动释放");
                frozenResult = this.stockService.frozeStockQty(row.getSku(), row.getSecCode(),
                    transferLine.getLineNum(), releaseCnt,
                    InventoryBusinessTypes.FROZEN_BY_TRANSFER, "auto");
                boolean isSuc = frozenResult.getSuccess();
                if (isSuc) {
                    reservedToRelease.setRemark("释放成功" + ",refno=" + transferLine.getLineNum());
                    this.invReservedToReleaseDao.updateReserved(reservedToRelease);
                    //transactionManagerStock.commit(status);
                    message.append(
                        "自动虚拟调拨提交,冻结库存数" + row.getStockQty() + "成功， batchid:" + row.getId())
                        .append(",");
                    log.debug("自动虚拟调拨提交, " + frozenResult.getMessage());
                } else {
                    //transactionManagerStock.rollback(status);
                    message.append(
                        "自动虚拟调拨回滚，冻结库存数" + row.getStockQty() + "失败， batchid:" + row.getId())
                        .append(",");
                    log.debug("自动虚拟调拨回滚，" + frozenResult.getMessage());
                }

            } catch (Exception e) {
                //transactionManagerStock.rollback(status);
                message.append("自动虚拟调拨回滚， 出现异常" + e.getMessage() + ", batchID:" + row.getId())
                    .append(";");
                log.error("自动虚拟调拨回滚， 出现异常" + e.getMessage() + ", batchId:" + row.getId() + ","
                          + e.getMessage());
            }
        }
    }

    /**
     *  支持：
     *  
     *  2. 虚拟调拨： 同一仓库下调拨（渠道间调拨）
     * @param transfers：成功行数
     * @return
     */
    public ServiceResult<Integer> saveInnerTransfers(List<InvTransferLine> transfers) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        if (transfers == null || transfers.size() <= 0) {
            result.setMessage("手动虚拟调拨记录为空");
            result.setResult(0);
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status;
        InvTransferLine transferLine;
        int i = 0;
        StringBuffer message = new StringBuffer();
        for (InvTransferLine transfer : transfers) {
            status = transactionManagerStock.getTransaction(def);
            try {

                transferLine = this.insertTransferLine(transfer, transfer.getCreateUser(),
                    InvTransferLine.LINE_STATUS_INIT);
                if (log.isDebugEnabled()) {
                    log.debug("调拨编号：" + transferLine.getLineId() + ", 调拨单号："
                              + transferLine.getLineNum());
                }

                this.recordTransferLog(transferLine.getLineId(), InvTransferLog.LOG_TYPE_NEW,
                    transfer.getCreateUser(), new Date(), transfer.getRemark());
                i++;
                transactionManagerStock.commit(status);
            } catch (Exception e) {
                transactionManagerStock.rollback(status);
                log.error("虚拟调拨管理-导入调货记录时发生未知异常：", e);
                message.append("第").append(i).append("行调拨失败;");
            }
        }
        result.setResult(i);
        result.setMessage(message.toString());
        return result;
    }

    /**
     * 虚拟调拨保存单个调拨记录
     * @param transfer
     * @return
     */
    public String saveInnerTransfer(InvTransferLine transfer, boolean submit) {
        if (transfer == null) {
            return null;
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status;
        InvTransferLine transferLine = new InvTransferLine();
//        status = transactionManagerStock.getTransaction(def);
        try {
            transferLine = this.insertTransferLine(transfer, transfer.getCreateUser(),
                InvTransferLine.LINE_STATUS_INIT);
            if (submit) {
                log.info("虚拟调拨，单号：" + transfer.getLineNum() + ",启动立即提交");
                submitTransferLine(transferLine, transfer.getCreateUser());
            }
            log.info("调拨编号：" + transferLine.getLineId() + ", 调拨单号：" + transferLine.getLineNum());
            this.recordTransferLog(transferLine.getLineId(), InvTransferLog.LOG_TYPE_NEW,
                transfer.getCreateUser(), new Date(), transfer.getRemark());

//            transactionManagerStock.commit(status);
        } catch (Exception e) {
//            transactionManagerStock.rollback(status);
            log.error("虚拟调拨管理-调货记录时发生未知异常：", e);
        }
        return transferLine.getLineNum();
    }

    /**
     *  1. 平铺、缺货调拨： 不同仓库下
     * @param lines
     * @return :返回成功行数
     */
    public ServiceResult<Integer> uploadTransferRecored(List<InvTransferLine> lines) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        //在一个事务中处理变更
        String user = "";
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            Integer[] lineIdArr = new Integer[lines.size()];
            // List<Integer> lineIdlist = new ArrayList<Integer>();
            int cnt = 0;
            for (InvTransferLine line : lines) {
                //平铺、缺货调拨时不支持‘WA共享’作为‘调入渠道’ 2014-09-29
                if (line != null && "WA".equalsIgnoreCase(line.getChannelTo())) {
                    log.error("调拨管理-导入平铺、缺货调拨记录时存在不支持的‘WA共享’调入渠道，单据号:" + line.getLineNum()
                              + "，调入渠道:" + line.getChannelTo());
                    continue;
                }
                if (InvTransferLine.TRANSFER_REASON_PP.equals(line.getTransferReason())) {
                    line.setLineNum(this.getPPLineNum(line.getSecFrom()));// WD + {YYMMDD} + {2位调出库位} + {4位序列号} +DH，例如 WD130724BJ0001DH
                }
                if (InvTransferLine.TRANSFER_REASON_3W.equals(line.getTransferReason())) {
                    line.setLineNum(this.getPPOr3WLineNum(line.getSecFrom(), "WDX"));
                }
                transferLineDao.insert(line);
                lineIdArr[cnt++] = line.getLineId();
                // lineIdlist.add(line.getLineId());
                if (StringUtil.isEmpty(user)) {
                    user = line.getCreateUser();
                }
            }
            //            this.recordTransferLogs((Integer[]) lineIdlist.toArray(), InvTransferLog.LOG_TYPE_NEW,user, new Date(), "导入");
            this.recordTransferLogs(lineIdArr, InvTransferLog.LOG_TYPE_NEW, user, new Date(), "导入");
            transactionManagerStock.commit(status);
            result.setSuccess(true);
            result.setResult(lineIdArr.length);//lines.size()
            int noexport = lines.size() - lineIdArr.length;
            if (noexport > 0) {
                result.setMessage("应导入" + lines.size() + "条，实际" + noexport + "条未导入，调入渠道不支持‘WA共享’");
            }
        } catch (Throwable e) {
            transactionManagerStock.rollback(status);
            log.error("调拨管理-导入调货记录时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("调拨管理-导入调货记录时发生未知异常");
        }
        return result;
    }

    /**
     * 通过预留配置产生调拨
     * @param stockBatch
     * @param invSection
     * @param reserveConfig
     */
    public boolean transferLineByReservedConfig(InvStockBatch stockBatch, InvSection invSection,
                                                InvReservedConfig reserveConfig) {
        if (stockBatch == null || stockBatch.getStockQty() <= 0) {
            log.debug("调拨数为0，refno:" + stockBatch.getRefno());
            return false;
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            InvTransferLine transferLine = new InvTransferLine();
            //1.设置物料编码
            transferLine.setItemCode(stockBatch.getSku());
            //2.设置物料名称
            ServiceResult<ItemBase> base = itemService.getItemBaseBySku(stockBatch.getSku());
            if (base.getSuccess()) {
                transferLine.setItemName(base.getResult().getMaterialDescription());
            } else {
                log.info("transferLineByReservedConfig：没有或获取到物料名称, sku=" + stockBatch.getSku());
            }
            //入库的库位
            transferLine.setSecFrom(stockBatch.getSecCode());
            int transferCnt = getReservedCnt(stockBatch);
            if (transferCnt <= 0) {
                transactionManagerStock.rollback(status);
                log.info("transferLineByReservedConfig：虚拟调拨, refno=" + stockBatch.getRefno()
                         + ",无预留库存");
                return true;
            }
            transferLine.setTransferQty(transferCnt);
            transferLine.setQty(transferCnt);
            transferLine.setChannelFrom(InvSection.CHANNEL_CODE_WA);
            /*    transferLine.setChannelId(invSection.getChannelCode());*/
            //单号预留的渠道
            InvSection sectionTo = invSectionDao.getByLesSecCodeAndChannelCode(
                invSection.getLesSecCode(), reserveConfig.getChannelCode());
            transferLine.setSecTo(sectionTo.getSecCode());
            transferLine.setChannelTo(sectionTo.getChannelCode());
            transferLine.setRemark("单号：" + stockBatch.getRefno());
            //生成调拨，1.生成调拨记录，2.状态为初始
            transferLine = insertTransferLine(transferLine, "job reserved",
                InvTransferLine.LINE_STATUS_INIT);
            //提交调拨：1.冻结库存， 2.改变调拨状态
            submitTransferLine(transferLine, "job");
            InvReservedConfig config = new InvReservedConfig();
            config.setAllowUpdate(1);
            config.setChannelCode(reserveConfig.getChannelCode());
            config.setCreateUser("job transfer");
            config.setLockHours(reserveConfig.getLockHours());
            config.setRef(transferLine.getLineNum());
            config.setStatus(InvReservedConfig.STATUS_ON);
            config.setUpdateUser("job transfer");

            //保存新的预留配置
            this.invReservedToReleaseDao.insertReservedConfig(config);
            transactionManagerStock.commit(status);
            return true;
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            throw new BusinessException("根据预留配置走调拨流程失败。");
        }
    }

    /**
     * 虚拟调拨，生成虚拟调拨单
     * sec_to为共享库存编码
     * @param row 原始对象
     * @return
     */
    public InvTransferLine insertTransferLine(InvTransferLine row, String createUser,
                                              Integer lineStatus) {
        InvTransferLine transferLine = getInitTransferLine(lineStatus);
        transferLine.setTransferQty(row.getTransferQty());
        transferLine.setCreateUser(createUser);
        transferLine.setSecFrom(row.getSecFrom());
        transferLine.setSecTo(row.getSecTo());
        //transferLine.setChannelId(row.getChannelId());
        transferLine.setChannelFrom(row.getChannelFrom());
        transferLine.setChannelTo(row.getChannelTo());
        transferLine.setLineNum(this.getPPLineNum(row.getSecFrom()));
        transferLine.setItemCode(row.getItemCode());
        transferLine.setRemark(row.getRemark());
        transferLine.setItemCode(row.getItemCode());
        transferLine.setItemId(row.getItemId());
        transferLine.setItemName(row.getItemName());
        transferLine.setTransferQty(row.getTransferQty());
        transferLine.setQty(row.getQty());
        this.transferLineDao.insert(transferLine);
        return transferLine;
    }

    /**
     * 调拨管理-提交
     * @param lineIdArr
     * @param user
     */
    public ServiceResult<Boolean> submitBatchTransfer(Integer[] lineIdArr, String user,
                                                      String isFirst) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        ServiceResult<List<InvTransferLine>> checkRet = this.checkLineStatus(lineIdArr,
            new Integer[] { InvTransferLine.LINE_STATUS_INIT });
        if (checkRet.getResult().size() <= 0) {
            result.setSuccess(false);
            result.setMessage("无调拨单需提交");
            return result;
        }
        if (!checkRet.getSuccess()) {
            result.setSuccess(false);
            result.setMessage("只有初始状态的调拨网单能执行该操作");
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status;

        ServiceResult<Boolean> ret;
        Map<String, Object> params;
        Map<String, Object> transferQtyParam;
        int cnt = 0;
        int errorCount = 0;
        Integer qty;
        StringBuffer msgBuffer = new StringBuffer();
        StringBuffer msgFailBuffer = new StringBuffer();
        StringBuffer errorBuffer;
        for (InvTransferLine transferLine : checkRet.getResult()) {
            status = transactionManagerStock.getTransaction(def);
            try {
                if ("true".equals(isFirst)) {
                    //首次期望
                    qty = transferLine.getQty();
                } else {
                    //二次实际
                    qty = transferLine.getTransferQty();
                }
                errorBuffer = new StringBuffer();
                ret = this.stockService.frozeStockQty(transferLine.getItemCode(),
                    transferLine.getSecFrom(), transferLine.getLineNum(), qty,
                    InventoryBusinessTypes.FROZEN_BY_TRANSFER, user);
                if (!ret.getSuccess()) {
                    cnt++;
                    msgFailBuffer.append(transferLine.getLineId()).append(",");
                    transactionManagerStock.rollback(status);
                    if ("true".equals(isFirst)) {
                        //首次失败，将实际的0更新为当前真正库存或者在又有库存时的期望值
                        transferQtyParam = getTransferQtyParam(
                            transferLine.getLineId(),
                            checkStorageForBaseStock(transferLine.getSecFrom(),
                                transferLine.getItemCode(), qty, errorBuffer), false,
                            transferLine.getRemark() + "||" + errorBuffer.toString());
                    } else {
                        //二次失败，将实际直接更新成0
                        transferQtyParam = getTransferQtyParam(transferLine.getLineId(), 0, false,
                            transferLine.getRemark());
                    }
                    transferLineDao.updateLineTransferQtyByLineId(transferQtyParam);
                    log.info("冻结失败， 已回滚," + ret.getMessage() + ",调拨单号：" + transferLine.getLineNum()
                             + ",isFirst" + isFirst);
                    continue;
                }
                if (transferLine.getTransferReason().equals(InvTransferLine.TRANSFER_REASON_XN)) {
                    params = getUpdateLineStatusParams(new Integer[] { transferLine.getLineId() },
                        InvTransferLine.LINE_STATUS_LES);
                } else if (transferLine.getTransferReason().equals(
                    InvTransferLine.TRANSFER_REASON_3W)) {
                    params = getUpdateLineStatusParams(new Integer[] { transferLine.getLineId() },
                        InvTransferLine.LINE_STATUS_3W_CONFIRM);
                } else {
                    params = getUpdateLineStatusParams(new Integer[] { transferLine.getLineId() },
                        InvTransferLine.LINE_STATUS_CONFIRM);
                }
                transferQtyParam = getTransferQtyParam(transferLine.getLineId(), qty, true,
                    transferLine.getRemark());
                transferLineDao.updateLineTransferQtyByLineId(transferQtyParam);
                transferLineDao.updateLineStatusByLineIds(params);
                this.recordTransferSubmitLogs(new Integer[] { transferLine.getLineId() }, user);
                transactionManagerStock.commit(status);
            } catch (Exception e) {
                errorCount++;
                msgFailBuffer.append(transferLine.getLineId()).append(",");
                transactionManagerStock.rollback(status);
                log.error("调拨管理-提交时发生未知异常：", e);
                result.setSuccess(false);
                result.setMessage("提交时发生未知异常");
            }

        }
        //        if (cnt == lineIdArr.length) {
        //            result.setSuccess(false);
        //            msgBuffer.append("未能成功提交调拨单");
        //        } else {
        result.setSuccess(true);
        msgBuffer.append("提交调拨总数为" + lineIdArr.length + "条，成功"
                         + (lineIdArr.length - cnt - errorCount) + "条，失败" + cnt + "条，异常"
                         + errorCount + "条！\n");
        //        }
        if (msgFailBuffer.length() > 0) {
            result.setMessage(msgBuffer.toString() + "@"
                              + msgFailBuffer.deleteCharAt(msgFailBuffer.length() - 1));
        } else {
            result.setMessage(msgBuffer.toString());
        }
        return result;
    }

    private void submitTransferLine(InvTransferLine transferLine, String optUser) {
        ServiceResult<Boolean> ret = this.stockService.frozeStockQty(transferLine.getItemCode(),
            transferLine.getSecFrom(), transferLine.getLineNum(), transferLine.getTransferQty(),
            InventoryBusinessTypes.FROZEN_BY_TRANSFER, optUser);
        if (!ret.getSuccess()) {
            log.info("冻结失败， 已回滚," + ret.getMessage() + ",调拨单号：" + transferLine.getLineNum());
            throw new BusinessException("冻结失败， 已回滚," + ret.getMessage() + ",调拨单号："
                                        + transferLine.getLineNum());
        }
        Map<String, Object> params;
        if (transferLine.getTransferReason().equals(InvTransferLine.TRANSFER_REASON_XN)) {
            params = getUpdateLineStatusParams(new Integer[] { transferLine.getLineId() },
                InvTransferLine.LINE_STATUS_LES);
        } else {
            params = getUpdateLineStatusParams(new Integer[] { transferLine.getLineId() },
                InvTransferLine.LINE_STATUS_CONFIRM);
        }
        transferLineDao.updateLineStatusByLineIds(params);
        this.recordTransferSubmitLogs(new Integer[] { transferLine.getLineId() }, optUser);

    }

    /**
     * 取得货物调拨信息列表
     * @param params
     * @param pagerInfo
     * @return
     */
    public List<InvTransferLine> getTransferLines(Map<String, Object> params, PagerInfo pagerInfo) {
        if (pagerInfo == null) {
            throw new BusinessException("请指定分页信息");
        } else {
            if (pagerInfo.getPageSize() > 5000)
                throw new BusinessException("最大的每页记录数为5000");
            if (pagerInfo.getPageIndex() < 0)
                throw new BusinessException("不正确的页数");
        }
        if (params == null)
            params = new HashMap<String, Object>();
        params.put("start", pagerInfo.getStart());
        params.put("size", pagerInfo.getPageSize());
        return transferLineDao.getTransferLines(params);
    }

    /**
     * 取得货物调拨信息数量
     * @param params
     * @return
     */
    public Integer getTransferLinesCount(Map<String, Object> params) {
        return transferLineDao.getCount(params);
    }

    /**
     * 取得货物调拨操作历史列表
     * @param lineId
     * @return
     */
    public List<InvTransferLog> getTransferOperationHistory(Integer lineId) {
        return transferLogDao.getByLineId(lineId);
    }

    /**
     * 定时任务自动计算可用调拨数, 确保只能调本库位的
     * @return
     */
    private int getReservedCnt(InvStockBatch row) {
        InvBaseStock baseStock = (InvBaseStock) this.invBaseStockDao.get(row.getSku(),
            row.getSecCode());
        if (baseStock == null) {
            log.error("getReservedCnt:sku[" + row.getSku() + "], secCode[" + row.getSecCode()
                      + "]对应的库存不存在");
            return -1;
        }
        if (baseStock.getFrozenQty() <= 0) {
            return row.getStockQty();
        }
        Integer sumCnt = this.invStockBatchDao.getSumStockBySku(row.getSku(), row.getSecCode(),
            row.getId());
        int num = (sumCnt == null ? 0 : sumCnt) - baseStock.getFrozenQty();
        if (num >= 0) {

            return row.getStockQty();
        } else {
            int releaseCnt = row.getStockQty() + num;
            if (releaseCnt > 0) {
                return releaseCnt;
            }
        }
        return 0;
    }

    /**
     * 定时任务自动计算释放库存数
     * @param row
     * @return
     */
    private int getReleaseCnt(InvStockBatch row, InvReservedToRelease reservedToRelease) {
        InvBaseStock baseStock = (InvBaseStock) this.invBaseStockDao.get(row.getSku(),
            row.getSecCode());
        if (baseStock == null) {
            reservedToRelease.setRemark("找不到库位对应的库存");
            log.error("sku[" + row.getSku() + "], secCode[" + row.getSecCode() + "]对应的库存不存在");
            return -1;
        }
        if (baseStock.getFrozenQty() <= 0) {
            return row.getStockQty();
        }
        Integer sumCnt = this.invStockBatchDao.getSumStockBySku(row.getSku(), row.getSecCode(),
            row.getId());
        int num = (sumCnt == null ? 0 : sumCnt) - baseStock.getFrozenQty();
        if (num >= 0) {
            //如果之前的库存总数减冻结数大于0，则表示之前的没有出库完， 因此当前批次的全部释放
            return row.getStockQty();
        } else {
            int releaseCnt = row.getStockQty() + num;
            if (releaseCnt > 0) {
                return releaseCnt;
            }
        }
        reservedToRelease.setRemark("计算出释放数为0");
        return 0;

    }

    private void setTransferLineSetTo(InvTransferLine transferLine, InvSection section) {
        String whCode = section.getWhCode();
        transferLine.setSecTo(whCode + InvSection.CHANNEL_CODE_WA);
    }

    private InvTransferLine getInitTransferLine(Integer lineStatus) {
        InvTransferLine transferLine = new InvTransferLine();
        transferLine.setTransferReason(InvTransferLine.TRANSFER_REASON_XN);
        transferLine.setSoLineNum("");
        transferLine.setLineStatus(lineStatus);
        transferLine.setTransferFee(new BigDecimal(0));
        transferLine.setTransferFeeUser("");
        transferLine.setHaulCycle(0);
        Date initDate = null;
        try {
            initDate = new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
        } catch (ParseException e) {
        	log.error(e.getMessage());
        }
        transferLine.setTransferFeeTime(initDate);
        transferLine.setApproveStatus(InvTransferLine.APPROVE_STATUS_INIT);
        transferLine.setApproveTime(initDate);
        transferLine.setApproveUser("");
        transferLine.setApproveRemark("");
        transferLine.setLesNum("");
        transferLine.setCreateTime(new Date());
        transferLine.setCreateUser("job");
        transferLine.setRemark("");
        return transferLine;

    }

    /**
     * 调拨管理-提交
     * 检查调拨状态：只有初始状态的调拨网单能执行提交操作
     * @param lineIdArr
     * @return
     */
    private ServiceResult<List<InvTransferLine>> checkLineStatus(Integer[] lineIdArr,
                                                                 Integer[] valideStatusArr) {
        ServiceResult<List<InvTransferLine>> result = new ServiceResult<List<InvTransferLine>>();
        List<InvTransferLine> ret = transferLineDao.getByLineIds(lineIdArr);
        if (ret == null || ret.size() == 0 || lineIdArr.length != ret.size()) {
            result.setSuccess(false);
            return result;
        }
        List<Integer> validList = Arrays.asList(valideStatusArr);
        for (InvTransferLine line : ret) {
            if (!validList.contains(line.getLineStatus())) {
                result.setSuccess(false);
                return result;
            }
        }
        result.setSuccess(true);
        result.setResult(ret);
        return result;
    }

    /**
     * 根据lineId更新调拨单状态并记录日志
     * @param lineId
     * @param lineStatus
     * @return
     */
    public boolean updateLineStatusByLineId(Integer lineId, int lineStatus) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        boolean isSuc = true;
        try {
            Map<String, Object> params = getUpdateLineStatusParams(new Integer[] { lineId },
                lineStatus);
            Integer line = this.transferLineDao.updateLineStatusByLineIds(params);
            if (line > 0 && InvTransferLine.LINE_STATUS_STORE_OUT == lineStatus) {
                this.recordTransferLog(lineId, InvTransferLog.LOG_TYPE_LES_TRANSFER, "CBS",
                    new Date(), "待出库");
            }

            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("更新调拨单状态成功" + e.getMessage());
            isSuc = false;
        }
        return isSuc;
    }

    private void recordTransferSubmitLogs(Integer[] lineIdArr, String user) {
        for (Integer lineId : lineIdArr) {
            InvTransferLine line = transferLineDao.get(lineId);
            if (line != null) {
                this.recordTransferLog(line.getLineId(), InvTransferLog.LOG_TYPE_CONFIRM, user,
                    new Date(), "提交：锁定库存,数量为" + line.getTransferQty());
            }
        }
    }

    private void recordTransferLogs(Integer[] lineIdArr, Integer logType, String user, Date opTime,
                                    String remark) {
        for (Integer lineId : lineIdArr) {
            this.recordTransferLog(lineId, logType, user, opTime, remark);
        }
    }

    private void recordTransferLog(Integer lineId, Integer logType, String user, Date opTime,
                                   String remark) {
        if (opTime == null) {
            opTime = new Date();
        }
        InvTransferLog log = new InvTransferLog();
        log.setLineId(lineId);
        log.setLogTime(new Date());
        log.setLogType(logType);
        log.setLogUser(user);
        /*  if (opTime == null) {
              try {
                  opTime = new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
              } catch (ParseException e) {
        
              }
          }*/
        log.setOptTime(opTime);
        if (!StringUtil.isEmpty(remark)) {
            log.setLogRemark(remark);
        }
        transferLogDao.insert(log);
    }

    private Map<String, Object> getUpdateLineStatusParams(Integer[] lineIdArr, Integer status) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("lineIdArr", lineIdArr);
        params.put("status", status);
        return params;
    }

    private Map<String, Object> getTransferQtyParam(Integer line_id, Integer transfer_qty,
                                                    Boolean flag, String remark) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("line_id", line_id);
        param.put("transfer_qty", transfer_qty);
        if (flag) {
            param.put("remark", remark);
        } else {
            param.put("remark", remark.contains("期望与实际数量不符") ? remark : "期望与实际数量不符," + remark);
        }
        return param;
    }

    /**
     * 调拨管理-删除
     * @param lineIdArr
     * @param user
     * @return
     */
    public ServiceResult<Boolean> removeTransfer(Integer[] lineIdArr, String user) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();

        ServiceResult<List<InvTransferLine>> checkRet = this.checkLineStatus(lineIdArr,
            new Integer[] { InvTransferLine.LINE_STATUS_INIT });
        if (!checkRet.getSuccess()) {
            result.setSuccess(false);
            result.setMessage("只有初始状态的调拨网单能执行该操作");
            return result;
        }

        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            Map<String, Object> params = this.getUpdateLineStatusParams(lineIdArr,
                InvTransferLine.LINE_STATUS_DEL);
            transferLineDao.updateLineStatusByLineIds(params);
            this.recordTransferLogs(lineIdArr, InvTransferLog.LOG_TYPE_DEL, user, new Date(), "删除");
            result.setSuccess(true);
            result.setMessage("操作成功");
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManagerStock.rollback(status);
            log.error("调拨管理-删除时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨管理-取消
     * @param lineIdArr
     * @param user
     * @return
     */
    public ServiceResult<Boolean> cancelTransfer(Integer[] lineIdArr, String user) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();

        ServiceResult<List<InvTransferLine>> checkRet = this.checkLineStatus(lineIdArr,
            new Integer[] { InvTransferLine.LINE_STATUS_FEE_INPUT,
                    InvTransferLine.LINE_STATUS_FEE_ADUIT, InvTransferLine.LINE_STATUS_LES,
                    InvTransferLine.LINE_STATUS_STORE_OUT });

        if (!checkRet.getSuccess()) {
            result.setSuccess(false);
            result.setMessage("只有状态为待录费用、待审核费用、待传LES、待出库的调拨网单能执行该操作");
            return result;
        }
        ServiceResult<Boolean> releaseRet = this.releaseFrozenQty(checkRet.getResult());
        if (!releaseRet.getSuccess()) {
            result.setSuccess(false);
            result.setMessage(releaseRet.getMessage());
            return result;
        }

        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            Map<String, Object> params = this.getUpdateLineStatusParams(lineIdArr,
                InvTransferLine.LINE_STATUS_CANCEL);
            transferLineDao.updateLineStatusByLineIds(params);
            this.recordTransferCancelLogs(lineIdArr, user);
            result.setSuccess(true);
            result.setMessage("操作成功");
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("调拨管理-提交时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    private void recordTransferCancelLogs(Integer[] lineIdArr, String user) {
        for (Integer lineId : lineIdArr) {
            InvTransferLine line = transferLineDao.get(lineId);
            if (line != null) {
                this.recordTransferLog(line.getLineId(), InvTransferLog.LOG_TYPE_CANCEL, user,
                    null, "释放调拨单" + line.getLineNum() + "锁定的库存,数量为" + line.getTransferQty());
            }
        }
    }

    private ServiceResult<Boolean> releaseFrozenQty(List<InvTransferLine> transferLines) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        ServiceResult<Boolean> releaseResult;
        for (InvTransferLine line : transferLines) {
            releaseResult = stockService.releaseFrozenStockQty(line.getItemCode(),
                line.getLineNum(), line.getTransferQty(),
                InventoryBusinessTypes.RELEASE_BY_TRANSFER);
            if (!releaseResult.getSuccess()) {
                result.setSuccess(false);
                result.setMessage(releaseResult.getMessage());
                return result;
            }
        }
        result.setSuccess(true);
        return result;
    }

    private String getPPLineNum(String secFrom) {
        return getPPOr3WLineNum(secFrom, "WD");
    }

    private String getPPOr3WLineNum(String secFrom, String title) {
        InvSection invSection = invSectionDao.getBySecCode(secFrom);
        if (invSection == null)
            throw new BusinessException("未找到对应的库位");
        StringBuilder sb = new StringBuilder(title);
        String yyMMdd = new SimpleDateFormat("yyMMdd").format(new Date());
        sb.append(yyMMdd).append(invSection.getWhCode());
        if ("WDX".equals(title)) {
            sb.append(this.get3WTransferLineNumStringSequence(sb.toString()));
        } else {
            sb.append(this.getPPTransferLineNumStringSequence(sb.toString()));
        }
        sb.append("DH");
        return sb.toString();
    }

    /**
     * 取得平铺调货调拨网单号码(lineNum)的序列号
     * @param param
     * @return
     */
    private Integer getPPTransferLineNumIntSequence(String param) {
        List<InvTransferLine> lines = transferLineDao.getPPTransferLineNum(param);
        Integer ret = 1;
        if (lines != null && lines.size() > 0) {
            InvTransferLine line = lines.get(0);
            ret = Integer.parseInt(line.getLineNum().substring(10, 14)) + 1;
        }
        return ret;
    }

    private Integer get3WTransferLineNumIntSequence(String param) {
        List<InvTransferLine> lines = transferLineDao.getPPTransferLineNum(param);
        Integer ret = 1;
        if (lines != null && lines.size() > 0) {
            InvTransferLine line = lines.get(0);
            ret = Integer.parseInt(line.getLineNum().substring(11, 15)) + 1;
        }
        return ret;
    }

    private String getPPTransferLineNumStringSequence(String param) {
        Integer intSeq = this.getPPTransferLineNumIntSequence(param);
        String req = String.valueOf(intSeq);
        StringBuilder sb = new StringBuilder();
        if (req.length() < 4) {
            for (int i = 4 - req.length(); i > 0; i--) {
                sb.append("0");
            }
            sb.append(req);
            req = sb.toString();
        }
        return req;
    }

    private String get3WTransferLineNumStringSequence(String param) {
        Integer intSeq = this.get3WTransferLineNumIntSequence(param);
        String req = String.valueOf(intSeq);
        StringBuilder sb = new StringBuilder();
        if (req.length() < 4) {
            for (int i = 4 - req.length(); i > 0; i--) {
                sb.append("0");
            }
            sb.append(req);
            req = sb.toString();
        }
        return req;
    }

    /**
     * 调拨管理-导入平铺/缺货调货记录-库存数量校验：WA库存数-商城锁定库存数>=调拨数量
     * 支持渠道调拨检查
     * @param secCode
     * @param sku
     * @param transferQty
     */
    public void checkStorageForUploadTransferRecord(String secCode, String sku, int transferQty) {
        InvStock stock = invStockDao.getBySecCodeAndSku(secCode, sku);
        if (stock == null)
            throw new BusinessException("根据调出库位[" + secCode + "]和物料编码[" + sku + "]找不到对应的库存");
        Integer waQty = stock.getStockQty();
        if (waQty - transferQty < 0) {
            throw new BusinessException("库存不足，当前WA库存数为" + waQty + ",调拨数量为" + transferQty);
        }

        /*  StorageProducts sp = storageProductsDao.getBySCodeAndSku(secCode, sku);
          if (sp == null)
              throw new BusinessException("根据调出库位[" + secCode + "]和物料编码[" + sku + "]找不到对应的商城库存");
          Integer currentForzenQty = sp.getUsedStock();
          if ((waQty - currentForzenQty - transferQty) < 0)
              throw new BusinessException("库存不足，当前WA库存数为" + waQty + "商城锁定库存数为" + currentForzenQty
                                          + "调拨数量为" + transferQty);*/
    }

    /**
     * 虚拟调拨检查库存数量：
     *  1. 先检查基础库存数量， 如果基础库存无，则说明是套机
     *  2. 基础库存无， 检查销售库存
     *  3. 使用基础库存时，需减去冻结数
     * @param secCode
     * @param sku
     * @param transferQty
     */
    public int checkStorageForBaseStock(String secCode, String sku, int transferQty,
                                        StringBuffer error) {
        if (transferQty <= 0) {
            error.append("不支持的调拨数量");
            if (log.isDebugEnabled()) {
                log.debug("checkStorageForBaseStock:不支持的调拨数量");
            }
            return 0;
        }
        InvBaseStock baseStock = (InvBaseStock) invBaseStockDao.get(sku, secCode);
        Integer channelQty = 0;
        int resultQty = 0;
        boolean useBaseStock = false;
        if (baseStock != null) {
            resultQty = baseStock.getStockQty();
            useBaseStock = resultQty > 0;
        } else {
            InvStock stock = invStockDao.getBySecCodeAndSku(secCode, sku);
            if (stock != null) {
                resultQty = stock.getStockQty();
            }
        }
        channelQty = useBaseStock ? resultQty - baseStock.getFrozenQty() : resultQty;
        if (channelQty <= 0) {
            error.append("库存不足");
            return 0;
        }

        if ((channelQty - transferQty) < 0) {
            return channelQty;
        }
        return transferQty;

    }

    /**
     * 根据调拨网单号码取得调拨网单
     * @param lineNum
     * @return
     */
    public InvTransferLine getInvTransferLineByLineNum(String lineNum) {
        if (StringUtil.isEmpty(lineNum))
            return null;
        InvTransferLine line = transferLineDao.getInvTransferLineByLineNum(lineNum);
        return line;
    }

    /**
     * 根据销售网单号取得调拨网单
     * @param soLineNum
     * @return
     */
    public InvTransferLine getInvTransferLineBySoLineNum(String soLineNum) {
        if (StringUtil.isEmpty(soLineNum))
            return null;
        InvTransferLine line = transferLineDao.getInvTransferLineBySoLineNum(soLineNum);
        return line;
    }

    /**
     * 根据调拨网单ID取得调拨网单
     * @param lineId
     * @return
     */
    public InvTransferLine getInvTransferLineByLineID(Integer lineId) {
        if (null == lineId)
            return null;
        InvTransferLine line = transferLineDao.get(lineId);
        return line;
    }

    /**
     * 根据网单ID更新调拨网单
     * @param line
     * @return
     */
    public ServiceResult<Integer> updateTransferLine(InvTransferLine line) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            Integer cnt = transferLineDao.update(line);
            result.setSuccess(true);
            result.setMessage("操作成功");
            result.setResult(cnt);
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("根据网单ID更新调拨状态时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据网单ID更新调拨状态时发生未知异常");
        }
        return result;
    }

    /**
     * LES入库后更新调拨单状态为已完成
     * @param transferLine
     * @return
     */
    public ServiceResult<Integer> updateTransferLineAfterLesInput(InvTransferLine transferLine,
                                                                  Date billTime, String remark) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            transferLine.setLineStatus(InvTransferLine.LINE_STATUS_COMPLETE);

            InvTransferLog transferLog = new InvTransferLog();
            transferLog.setLineId(transferLine.getLineId());
            transferLog.setLogTime(DateUtil.currentDateTime());
            transferLog.setLogType(InvTransferLog.LOG_TYPE_LES_IN);
            transferLog.setOptTime(billTime);
            transferLog.setLogRemark(remark);
            transferLog.setLogUser("CBS");

            int cnt = transferLineDao.update(transferLine);
            transferLogDao.insert(transferLog);

            result.setSuccess(true);
            result.setMessage("操作成功");
            result.setResult(cnt);
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("LES入库更新调拨状态时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("LES入库更新调拨状态时发生未知异常");
        }
        return result;
    }

    /**
     * LES出库后更新调拨单状态为待入库
     * @param transferLine
     * @return
     */
    public ServiceResult<Integer> updateTransferLineAfterLesOut(InvTransferLine transferLine,
                                                                Date billTime, String remark) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            transferLine.setLineStatus(InvTransferLine.LINE_STATUS_STORE_IN);
            InvTransferLog transferLog = new InvTransferLog();
            transferLog.setLineId(transferLine.getLineId());
            transferLog.setLogTime(DateUtil.currentDateTime());
            transferLog.setLogType(InvTransferLog.LOG_TYPE_LES_OUT);
            transferLog.setOptTime(billTime);
            transferLog.setLogRemark(remark);
            transferLog.setLogUser("CBS");

            int cnt = transferLineDao.update(transferLine);
            transferLogDao.insert(transferLog);

            result.setSuccess(true);
            result.setMessage("操作成功");
            result.setResult(cnt);
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("LES入库更新调拨状态时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("LES入库更新调拨状态时发生未知异常");
        }
        return result;
    }

    /**
     * 向LES开提单后更新调拨单状态为已完成
     * @param transferLine
     * @return
     */
    public ServiceResult<Integer> updateTransferLineAfterSyncToLes(InvTransferLine transferLine,
                                                                   Date billTime, String remark) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            transferLine.setLineStatus(InvTransferLine.LINE_STATUS_STORE_OUT);

            InvTransferLog transferLog = new InvTransferLog();
            transferLog.setLineId(transferLine.getLineId());
            transferLog.setLogTime(DateUtil.currentDateTime());
            transferLog.setLogType(InvTransferLog.LOG_TYPE_LES_TRANSFER);
            transferLog.setOptTime(billTime);
            transferLog.setLogRemark(remark);
            transferLog.setLogUser("CBS");

            int cnt = transferLineDao.update(transferLine);
            transferLogDao.insert(transferLog);

            result.setSuccess(true);
            result.setMessage("操作成功");
            result.setResult(cnt);
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("LES入库更新调拨状态时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("LES入库更新调拨状态时发生未知异常");
        }
        return result;
    }

    /**
     * 根据网单ID更新调拨网单
     * @param transferLine
     * @return
     */
    public ServiceResult<Integer> updateTransferLineAfterLesOutput(InvTransferLine transferLine,
                                                                   Date billTime, String remark) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            transferLine.setLineStatus(InvTransferLine.LINE_STATUS_STORE_OUT);

            InvTransferLog transferLog = new InvTransferLog();
            transferLog.setLineId(transferLine.getLineId());
            transferLog.setLogTime(DateUtil.currentDateTime());
            transferLog.setLogType(InvTransferLog.LOG_TYPE_LES_OUT);
            transferLog.setOptTime(billTime);
            transferLog.setLogRemark(remark);
            transferLog.setLogUser("CBS");

            int cnt = transferLineDao.update(transferLine);
            transferLogDao.insert(transferLog);

            result.setSuccess(true);
            result.setMessage("操作成功");
            result.setResult(cnt);
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("LES出库后更新调拨状态时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("LES出库后更新调拨状态时发生未知异常");
        }
        return result;
    }

    /**
     * 根据网单状态取得调拨网单
     * @param lineStatus
     * @return
     */
    public List<InvTransferLine> getTransferLineByLineStatus(Integer lineStatus) {
        return transferLineDao.getTransferLineByLineStatus(lineStatus);
    }

    /**
     * 调拨费用审核
     * @param lineId
     * @param operation
     * @param reason
     * @param user
     * @return
     */
    public ServiceResult<Boolean> transferFeeAudit(Integer lineId, String operation, String reason,
                                                   String user) {
        ServiceResult<Boolean> result;
        InvTransferLine line = transferLineDao.get(lineId);
        if (line == null) {
            throw new RuntimeException("参数lineId没有对应的调拨网单-->lineId=" + lineId);
        }
        if (InvTransferLine.TRANSFER_FEE_AUDIT_OPERATION_AGREE.equals(operation)) {
            result = this.transferFeeAduitAgree(line, user);
        } else if (InvTransferLine.TRANSFER_FEE_AUDIT_OPERATION_REJECT.equals(operation)) {
            result = this.transferFeeAduitReject(line, reason, user);
        } else if (InvTransferLine.TRANSFER_FEE_AUDIT_OPERATION_CANCEL.equals(operation)) {
            result = this.transferFeeAduitCancel(line, reason, user);
        } else {
            throw new RuntimeException("参数operation错误-->operation=" + operation);
        }
        return result;
    }

    /**
     * 调拨费用审核-取消
     * @param line
     * @param reason
     * @param user
     * @return
     */
    private ServiceResult<Boolean> transferFeeAduitCancel(InvTransferLine line, String reason,
                                                          String user) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        List<InvTransferLine> lines = new ArrayList<InvTransferLine>();
        lines.add(line);
        ServiceResult<Boolean> releaseRet = this.releaseFrozenQty(lines);
        if (!releaseRet.getSuccess()) {
            result.setSuccess(false);
            result.setMessage(releaseRet.getMessage());
            return result;
        }
        //针对物流录入的调货费用不合理，商城审核不通过时，需要把审核拒绝的原因回传给物流，从而实现物流系统的信息闭环
        List<QueryPayTimeToLes> param = new ArrayList<QueryPayTimeToLes>();
        QueryPayTimeToLes qptl = new QueryPayTimeToLes();
        qptl.setBSTNK(line.getLineNum());//调拨单号
        qptl.setBSTKD(line.getLineNum());//调拨单号
        qptl.setPOSNR("DX");
        qptl.setSOURCE("HAIERSC");
        qptl.setSOURCESN(line.getLineNum());//调拨单号
        qptl.setCRDAT(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//日期
        qptl.setCRZET(new SimpleDateFormat("HH:mm:ss").format(new Date()));//时间
        qptl.setNAME1("HAIERSC");
        qptl.setMESSAGE(reason);
        param.add(qptl);
        ServiceResult<Boolean> lesResult = lesService.paytimeToLes(line.getLineNum(), param);
        if (lesResult == null || !lesResult.getSuccess()) {
            String message = "给LES同步调拨取消信息时，接口错误信息:" + lesResult != null ? lesResult.getMessage()
                : "调用接口返回为null";
            log.error("调拨单" + line.getLineNum() + message);
            result.setSuccess(false);
            result.setMessage("给LES同步调拨取消信息时接口错误");
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            line.setLineStatus(InvTransferLine.LINE_STATUS_CANCEL);
            //line.setApproveStatus(InvTransferLine.APPROVE_STATUS_REJECT);
            line.setApproveTime(new Date());
            line.setApproveUser(user);
            line.setApproveRemark(StringUtil.isEmpty(reason, true) ? "" : reason);
            transferLineDao.update(line);
            this.recordTransferLog(line.getLineId(), InvTransferLog.LOG_TYPE_CANCEL, user,
                new Date(), "释放调拨单" + line.getLineNum() + "锁定的库存,数量为" + line.getTransferQty()
                            + ".取消原因：" + reason);
            result.setSuccess(true);
            result.setMessage("操作成功");
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("调拨单" + line.getLineNum() + "调拨费用审核-取消时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨费用审核-同意
     * @param line
     * @param user
     * @return
     */
    private ServiceResult<Boolean> transferFeeAduitAgree(InvTransferLine line, String user) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            line.setLineStatus(InvTransferLine.LINE_STATUS_LES);
            line.setApproveStatus(InvTransferLine.APPROVE_STATUS_OK);
            line.setApproveTime(new Date());
            line.setApproveUser(user);
            transferLineDao.update(line);
            this.recordTransferLog(line.getLineId(), InvTransferLog.LOG_TYPE_FEE_AUDIT, user,
                new Date(), "同意,金额为" + line.getTransferFee());
            result.setSuccess(true);
            result.setMessage("操作成功");
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("调拨费用审核-同意时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨费用审核-驳回
     * @param line
     * @param reason
     * @param user
     * @return
     */
    private ServiceResult<Boolean> transferFeeAduitReject(InvTransferLine line, String reason,
                                                          String user) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            line.setLineStatus(InvTransferLine.LINE_STATUS_FEE_INPUT);
            line.setApproveStatus(InvTransferLine.APPROVE_STATUS_REJECT);
            line.setApproveTime(new Date());
            line.setApproveUser(user);
            line.setApproveRemark(StringUtil.isEmpty(reason, true) ? "" : reason);
            transferLineDao.update(line);
            this.recordTransferLog(line.getLineId(), InvTransferLog.LOG_TYPE_FEE_AUDIT, user,
                new Date(), "驳回：" + reason);
            result.setSuccess(true);
            result.setMessage("操作成功");
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("调拨费用审核时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨管理-导入缺货调货记录-销售网单号码唯一性校验
     * @param lineNum
     * @return
     */
    public boolean checkQHLineNumForUploadTransferRecord(String lineNum) {
        InvTransferLine line = transferLineDao.getInvTransferLineByLineNum(lineNum);
        return line == null;
    }

    /**
     * LES传CBS调货费用接口
     * @param transfers
     */
    public ServiceResult<Boolean> saveTransferFee(List<InvTransferLine> transfers) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        for (InvTransferLine transfer : transfers) {
            InvTransferLine line = transferLineDao.getInvTransferLineByLineNum(transfer
                .getLineNum());
            if (line == null) {
                result.setResult(false);
                result.setError(transfer.getLineNum(), "提单号" + transfer.getLineNum() + "不正确");
                break;
            }
            if (line.getLineStatus() != InvTransferLine.LINE_STATUS_FEE_INPUT.intValue()) {
                continue;
            }
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManagerStock.getTransaction(def);
            try {
                BigDecimal oldFee = line.getTransferFee();
                line.setTransferFeeTime(transfer.getTransferFeeTime());
                line.setTransferFee(transfer.getTransferFee());
                line.setTransferFeeUser(transfer.getTransferFeeUser());
                line.setHaulCycle(transfer.getHaulCycle());
                line.setLineStatus(InvTransferLine.LINE_STATUS_FEE_ADUIT);
                transferLineDao.update(line);

                if (oldFee.compareTo(line.getTransferFee()) != 0) {//有价格变动
                    this.recordTransferLog(line.getLineId(), InvTransferLog.LOG_TYPE_FEE_INPUT,
                        line.getTransferFeeUser(), null,
                        "变更前金额为" + oldFee + ",变更后金额为" + line.getTransferFee());
                }
                this.recordTransferLog(line.getLineId(), InvTransferLog.LOG_TYPE_FEE_SUBMIT,
                    line.getTransferFeeUser(), null, "提交调拨费用，金额为" + line.getTransferFee());
                result.setSuccess(true);
                result.setResult(true);
                result.setMessage("操作成功");
                transactionManagerStock.commit(status);
            } catch (Exception e) {
                transactionManagerStock.rollback(status);
                log.error("LES传CBS调货费用时发生未知异常：" + e);
                result.setResult(false);
                result.setError(transfer.getLineNum(), "LES传CBS调货费用时发生未知异常");
                break;
            }
        }
        return result;
    }

    /**
     * 传递用于录入费用的调拨单信息到LES后更新调拨单状态为待录入费用
     * @param line
     * @return
     */
    public ServiceResult<Integer> updateTransferLineAfterToLesForFeeInput(InvTransferLine line) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            line.setLineStatus(InvTransferLine.LINE_STATUS_FEE_INPUT);
            int cnt = transferLineDao.update(line);

            this.recordTransferLog(line.getLineId(),
                InvTransferLog.LOG_TYPE_LOGISTICS_CENTER_SUBMIT, "系统", null,
                "提交单号为" + line.getLineNum() + "的调拨单到LES待录入费用");

            result.setSuccess(true);
            result.setMessage("操作成功");
            result.setResult(cnt);
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("传递用于录入费用的调拨单信息到LES后更新调拨单状态时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("传递用于录入费用的调拨单信息到LES后更新调拨单状态时发生未知异常");
        }
        return result;
    }

    public void setTransferLineDao(StockTransferLineService transferLineDao) {
        this.transferLineDao = transferLineDao;
    }

    public void setTransactionManagerStock(DataSourceTransactionManager transactionManagerStock) {
        this.transactionManagerStock = transactionManagerStock;
    }

    public void setTransferLogDao(TransferLogService transferLogDao) {
        this.transferLogDao = transferLogDao;
    }

    public void setStockService(StockServiceImpl stockService) {
        this.stockService = stockService;
    }

    public void setItemService(StockCenterItemServiceImplNew itemService) {
        this.itemService = itemService;
    }

    public void setInvStockDao(InvStockService invStockDao) {
        this.invStockDao = invStockDao;
    }

    public void setInvReservedToReleaseDao(InvReservedToReleaseService invReservedToReleaseDao) {
        this.invReservedToReleaseDao = invReservedToReleaseDao;
    }

    public void setInvStockBatchDao(StockInvStockBatchService invStockBatchDao) {
        this.invStockBatchDao = invStockBatchDao;
    }

    public void setInvBaseStockDao(StockInvBaseStockService invBaseStockDao) {
        this.invBaseStockDao = invBaseStockDao;
    }

    public void setInvSectionDao(StockInvSectionService<T> invSectionDao) {
        this.invSectionDao = invSectionDao;
    }

    public void setInvTransferLineCancelQueuesDao(InvTransferLineCancelQueuesService invTransferLineCancelQueuesDao) {
        this.invTransferLineCancelQueuesDao = invTransferLineCancelQueuesDao;
    }

    /**
     * 3w-日志格式
     * @param
     */
    private String logPrefix(String lineId) {
        return "[3WPP-to-vom] [" + lineId + "]";
    }

    /**
     * 3w-注入对象
     * @param
     */
    public void setAccessExternalInterface(AccessExternalInterface accessExternalInterface) {
        this.accessExternalInterface = accessExternalInterface;
    }

    /**
     * 3w-注入lesService对象
     * @param lesService
     */
    public void setLesService(LESServiceImpl lesService) {
        this.lesService = lesService;
    }

    /**
     * 3w-注入invWarehouseInfoDao对象
     * @param invWarehouseInfoDao
     */
    public void setInvWarehouseInfoDao(InvWarehouseInfoService invWarehouseInfoDao) {
        this.invWarehouseInfoDao = invWarehouseInfoDao;
    }

    /**
     * 3w-往Les创建3W转库单
     * @param
     */
    public boolean syncInnerTransfersToVom() {
        try {
            List<InvTransferLine> invTransferLineList = getSendInnerTransfersToVomQueues();
            if (invTransferLineList != null && invTransferLineList.size() > 0) {
                for (InvTransferLine invTransferLine : invTransferLineList) {
                    if (invTransferLine == null) {
                        continue;
                    }
                    try {
                        ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                        String[] message = new String[1];
                        int result = this.createInnerTransfersToVom(invTransferLine, message);

                        this.recordTransferLog(invTransferLine.getLineId(),
                            InvTransferLog.LOG_TYPE_3W_CBSTOVOM, "CBS_3W_ZK", new Date(),
                            message[0]);
                    } catch (Exception e) {
                        log.error(this.logPrefix(invTransferLine.getLineId().toString())
                                  + "3W转库单同步到VOM出错", e);
                        this.recordTransferLog(invTransferLine.getLineId(),
                            InvTransferLog.LOG_TYPE_3W_CBSTOVOM, "CBS_3W_ZK", new Date(),
                            this.logPrefix(invTransferLine.getLineId().toString())
                                    + "3W转库单同步到VOM出错");
                        return false;
                    } finally {
                        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                    }
                }
            } else {
                log.info("接收同步到VOM的3W转库单列表数据为空。");
                return false;
            }
        } catch (Exception e) {
            log.error("接收同步到VOM的3W转库单列表数据为空,出现异常：", e);
            return false;
        }
        return true;
    }

    /**
     * 3w-往Les创建3W转库单
     * 
     * 成功后，line_status=30变为line_status=40（待出库）
     * 
     * 下一步待vom返回，计算库存，并推sap
     * 
     * @param invTransferLine
     */
    private int createInnerTransfersToVom(InvTransferLine invTransferLine, String[] message) {
        //查询
        //参数检测
        if (invTransferLine == null) {
            message[0] = "参数invTransferLine为null";
            log.error(message[0]);
            return -1;
        }
        //其它渠道直接调拨3W库，库位需要转化
        String secFrom = invTransferLine.getSecFrom();
        if (!secFrom.endsWith("WA")) {
            secFrom = secFrom.substring(0, 2) + "WA";
        }
        //获取调出仓库数据
        InvWarehouseInfo invWarehouseInfo = invWarehouseInfoDao.getBySecCode(secFrom);
        if (invWarehouseInfo == null) {
            message[0] = "invWarehouseInfo表没有" + secFrom + "库位";
            log.error(invTransferLine.getLineNum() + "," + message[0]);
            return -1;
        }

        //生成业务数据xml
        String content = this.getContentXml(invTransferLine, invWarehouseInfo);//生成xml格式的content参数
        VomInterData vomInterData = new VomInterData();
        vomInterData.setNotifyid(invTransferLine.getLineNum() + "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vomInterData.setNotifytime(sdf.format(new Date()));
        vomInterData.setContent(content);

        //VOM开提单，生成3W调拨单加密参数
        String paramLes_tem = accessExternalInterface.orderToLesParam(content, vomInterData);
        String resultXml = "";
        try {
            if (paramLes_tem == null || paramLes_tem.equals("")) {
                message[0] = "生成VOM参数为空";
                log.error(message[0]);
                return -9;
            }
            //VOM新接口，调用VOM开提单    --vom
            ServiceResult<String> result = lesService.orderToLes(invTransferLine.getLineNum(),
                paramLes_tem);
            if (result == null || !result.getSuccess()) {//调用les出异常
                log.error("LineNum:" + invTransferLine.getLineNum() + "VOM调用返回失败，VOM错误信息:" + result != null ? result
                    .getMessage() : "调用接口返回为null");
                message[0] = "VOM调用返回失败";
                return -9;
            }
            resultXml = result.getResult();
            if (resultXml == null) {
                message[0] = "VOM调用返回结果为空";
                log.error(message[0]);
                return -9;
            }
        } catch (Exception ex) {
            log.error(
                "开提单，调用VOM接口时，LineNum:" + invTransferLine.getLineNum() + "，发生未知异常:"
                        + ex.getMessage(), ex);
            message[0] = "调用VOM接口时发生未知异常";
            return -9;
        }
        //处理les返回结果，解析xml
        HttpResult<String> httpresult = accessExternalInterface.getLesToOrderResult(resultXml);

        //les返回结果
        if (httpresult == null || httpresult.getSuccess() == null) {//调用接口发生异常，说明resultXml=""
            log.error(this.logPrefix(invTransferLine.getLineNum() + "")
                      + "调用VOM接口异常, HttpResult is null");
            message[0] = "调用VOM接口异常, 3W调拨单LineNum：" + invTransferLine.getLineNum()
                         + ", 接口返回为空，HttpResult is null";
            return -9;
        }

        // false:失败
        if (!httpresult.getSuccess()) { //调用接口返回false
            message[0] = "VOM调用结果：" + httpresult.getMessage();
            return -11;
        } else {// true：成功    新的开提单不返回开提单号，开提单号直接更新网单号，在发送成功以后就更新
            //处理状态
            try {
                Map<String, Object> params = getUpdateLineStatusParams(
                    new Integer[] { invTransferLine.getLineId() }, 40);//变成待出库状态
                this.transferLineDao.updateLineStatusByLineIds(params);
                message[0] = "3W转库传VOM出库成功，待VOM回传‘已出库’状态";
                //--调用接口成功

                return 0;
            } catch (Exception ex) {
                log.error("VOM调用后，更新3W调拨单号异常, 3W调拨单LineNum：" + invTransferLine.getLineNum()
                          + "，发生未知异常:" + ex.getMessage(), ex);
                message[0] = "VOM调用后，更新3W调拨单号异常";
                return -18;
            }
        }
    }

    /**
     * 3w－返回待发送到les的队列
     * @param
     * @return
     */
    public List<InvTransferLine> getSendInnerTransfersToVomQueues() {
        List<InvTransferLine> lq = null;
        try {
            lq = transferLineDao
                .getTransferLine3WByStatusReason(InvTransferLine.LINE_STATUS_3W_CONFIRM);//210:3w转库待推vom  推完后变40
        } catch (Exception e) {
            log.error("获取开提单队列数据出现异常：", e);
        }
        return lq;
    }

    /**
     * 3w－生成centent的xml格式数据
     * input  调拨信息
     * map 仓库信息
     * @return
     */
    public String getContentXml(InvTransferLine input, InvWarehouseInfo invWarehouseInfo) {
        StringBuffer sb = new StringBuffer();
        sb.append("<Order>");

        if (input == null) {
            sb.append("</Order>");
            return sb.toString();
        }

        sb.append("<orderno>" + input.getLineNum() + "</orderno>");//网单号
        sb.append("<sourcesn>" + input.getSoLineNum() + "</sourcesn>");//订单号
        sb.append("<ordertype>5</ordertype>");//订单类型：1.采购入库 2.销售出库 3.退货入库 4.取件 5.普通出库（自提）6.调拨 7.第三方运输订单  8客户调货 9.客户调货入库 10.网点取货 11.拒收入库
        sb.append("<bustype>2</bustype>");//业务类型：入库订单：1 送货到仓库 70 提货 ;出库订单：1 自提2 网点 70 直配 调货：1.送货到HUB库 2.HUB库自提
        String orderdate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderdate = sdf.format(new Date());
        sb.append("<orderdate>" + orderdate + "</orderdate>");//订单时间（格式：YYYY-MM-DD HH:MM:SS）
        sb.append("<storecode><![CDATA[" + invWarehouseInfo.getCenterCode() + "]]></storecode>");//scode转  中心代码     仓库编码：按日日顺C码
        sb.append("<province><![CDATA[" + invWarehouseInfo.getProvince() + "]]></province>");//收货人所在省
        sb.append("<city><![CDATA[" + invWarehouseInfo.getCity() + "]]></city>");//收货人所在市
        sb.append("<county><![CDATA[" + invWarehouseInfo.getRegion() + "]]></county>");//收货人所在县/区
        sb.append("<addr><![CDATA[" + invWarehouseInfo.getSecAddress() + "]]></addr>");//详细地址
        sb.append("<name><![CDATA[" + "物流宝仓记账员" + "]]></name>");//送达方姓名：收货人姓名
        sb.append("<mobile>" + invWarehouseInfo.getMobile() + "</mobile>");//联系电话
        sb.append("<reorder>" + input.getSoLineNum() + "</reorder>");//前续订单号：关联单号    --不填
        sb.append("<busflag>1</busflag>");//默认值1

        //应该循环，可能有套机的情况
        sb.append("<items>");
        if (input != null) {
            //判断是不是套机的情况
            List<String> subSkuList = getSubSkuListByMainSku(input.getItemCode());

            if (subSkuList != null && subSkuList.size() > 0) {//套机情况
                for (int i = 0; i < subSkuList.size(); i++) {
                    sb.append("<Item>");//stock服务，main_sku=sku and status=0   stock.inv_machine_set表查询到多条记录    
                    //                sb.append("<itemno>" + ims.getPosnr() + "</itemno>");//行号：订单中有多行物料时，物料所在的行数     -- 默认为10    套机查询表的BOM项目号字段
                    sb.append("<itemno>" + (i + 1) + "</itemno>");//行号传1,2,3.。。   不用BOM项目号字段了
                    sb.append("<storagetype>10</storagetype>");//批次 产品状态:10 正品 21 不良 22 破箱 40 样品 L0礼品    --10
                    sb.append("<productcode><![CDATA[" + subSkuList.get(i) + "]]></productcode>");//客户产品编码      -- sku   套机填sub_sku
                    sb.append("<hrcode><![CDATA[" + subSkuList.get(i) + "]]></hrcode>");//海尔产品编码 日日顺物流生成
                    sb.append("<prodes><![CDATA[" + input.getItemName() + "]]></prodes>");//产品描述      ---型号productname 都填统一的  
                    sb.append("<number>" + input.getTransferQty() + "</number>");//网单数量乘以组件数量     不是套机填网单数量
                    sb.append("</Item>");
                }
            } else {//非套机情况
                sb.append("<Item>");//stock服务，main_sku=sku and status=0   stock.inv_machine_set表查询到多条记录    
                //                sb.append("<itemno>" + ims.getPosnr() + "</itemno>");//行号：订单中有多行物料时，物料所在的行数     -- 默认为10    套机查询表的BOM项目号字段
                sb.append("<itemno>1</itemno>");//行号传1,2,3.。。   不用BOM项目号字段了
                sb.append("<storagetype>10</storagetype>");//批次 产品状态:10 正品 21 不良 22 破箱 40 样品 L0礼品    --10
                sb.append("<productcode><![CDATA[" + input.getItemCode() + "]]></productcode>");//客户产品编码      -- sku   套机填sub_sku
                sb.append("<hrcode><![CDATA[" + input.getItemCode() + "]]></hrcode>");//海尔产品编码 日日顺物流生成
                sb.append("<prodes><![CDATA[" + input.getItemName() + "]]></prodes>");//产品描述      ---型号productname 都填统一的  
                sb.append("<number>" + input.getTransferQty() + "</number>");//网单数量乘以组件数量     不是套机填网单数量
                sb.append("</Item>");
            }
        }
        sb.append("</items>");
        sb.append("</Order>");

        return sb.toString();
    }

    private List<String> getSubSkuListByMainSku(String mainSku) {
        ServiceResult<List<String>> SubSkuList = stockService.getSubSkuListByMainSku(mainSku);
        if (!SubSkuList.getSuccess())
            throw new BusinessException("通过服务根据套机sku查询子机sku列表失败");
        return SubSkuList.getResult();
    }

    public Integer updateRemarkByLineId(Integer id, String remark) {
        return transferLineDao.updateRemarkByLineId(id, remark);
    }

    public ServiceResult<Boolean> cancelTransferLineToLesOnTime(String lineNum) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            //生成业务数据xml
            String content = this.get3WTranferCancelContentXml(lineNum);//生成xml格式的content参数
            VomInterData vomInterData = new VomInterData();
            vomInterData.setNotifyid(lineNum);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vomInterData.setNotifytime(sdf.format(new Date()));
            vomInterData.setContent(content);

            //生成3W调拨单加密参数
            String paramLes_tem = accessExternalInterface.orderToLesParam(content, vomInterData);
            String resultXml = "";
            if (StringUtil.isEmpty(paramLes_tem)) {
                result.setSuccess(false);
                result.setResult(false);
                result.setMessage("生成VOM参数为空");
                log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + ",生成VOM参数为空");
                return result;
            }
            //调用VOM接口
            ServiceResult<String> vomResult = lesService.orderToLes(lineNum, paramLes_tem);
            //            ServiceResult<String> vomResult = new ServiceResult<String>();
            //            vomResult.setSuccess(true);
            //            vomResult.setResult("<request><flag>F</flag><msg>成功</msg></request>");
            if (vomResult == null || !vomResult.getSuccess()) {//调用VOM出异常
                result.setSuccess(false);
                result.setResult(false);
                result.setMessage("调用VOM接口失败");
                log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + "调用VOM接口失败"
                          + JsonUtil.toJson(vomResult));
                return result;
            }
            resultXml = vomResult.getResult();
            if (resultXml == null) {
                result.setSuccess(false);
                result.setResult(false);
                result.setMessage("调用VOM接口返回结果为空");
                log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + "调用VOM接口返回结果为空"
                          + JsonUtil.toJson(vomResult));
                return result;
            }
            //处理VOM返回结果，解析xml
            HttpResult<String> httpresult = accessExternalInterface.getLesToOrderResult(resultXml);

            //VOM返回结果
            if (httpresult == null || httpresult.getSuccess() == null) {
                result.setSuccess(false);
                result.setResult(false);
                result.setMessage("调用VOM接口返回结果解析错误");
                log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + "调用VOM接口返回结果解析错误" + resultXml);
                return result;
            }

            // false:失败
            if (!httpresult.getSuccess()) { //调用接口返回false
                result.setSuccess(true);
                result.setResult(false);
                result.setMessage("3W同步取消物流失败");
                log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + "3W同步取消物流失败"
                          + JsonUtil.toJson(httpresult));
                return result;
            } else {// true：成功    
                result.setSuccess(true);
                result.setResult(true);
            }
        } catch (Exception e) {
            log.error("3W调拨取消同步物流时发生异常：" + "单号：" + lineNum, e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("3W调拨取消同步物流时发生异常");
        }
        return result;
    }

    public void cancelTransferLineToLesJob() {
        //暂时定为30分钟调用一次
        int exeCount = 2 * 24 * 60 / 30;
        List<InvTransferLineCancelQueues> list = invTransferLineCancelQueuesDao.getUnSend(exeCount);
        String lineNum = null;
        for (InvTransferLineCancelQueues invTransferLineCancelQueues : list) {
            try {
                lineNum = invTransferLineCancelQueues.getLineNum();

                InvTransferLine line = transferLineDao.getInvTransferLineByLineNum(lineNum);
                if (line == null) {
                    invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                        .intValue() + 1);
                    invTransferLineCancelQueues.setLastMessage("调拨单号不存在");
                    invTransferLineCancelQueues.setSuccess(InvTransferLineCancelQueues.ERROR);
                    invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                    continue;
                }
                if (line.getLineStatus().intValue() > InvTransferLine.LINE_STATUS_STORE_OUT
                    .intValue()) {
                    invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                        .intValue() + 1);
                    invTransferLineCancelQueues.setLastMessage("状态:" + line.getLineStatus()
                                                               + ",拦截失败,不再取消");
                    invTransferLineCancelQueues.setSuccess(InvTransferLineCancelQueues.ERROR);
                    invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                    continue;
                }

                //生成业务数据xml
                String content = this.get3WTranferCancelContentXml(lineNum);//生成xml格式的content参数
                VomInterData vomInterData = new VomInterData();
                vomInterData.setNotifyid(lineNum);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                vomInterData.setNotifytime(sdf.format(new Date()));
                vomInterData.setContent(content);

                //生成3W调拨单加密参数
                String paramLes_tem = accessExternalInterface.orderToLesParam(content,
                    vomInterData);
                String resultXml = "";
                if (StringUtil.isEmpty(paramLes_tem)) {
                    invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                        .intValue() + 1);
                    invTransferLineCancelQueues.setLastMessage("生成VOM参数为空");
                    invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                    log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + ",生成VOM参数为空");
                    continue;
                }
                invTransferLineCancelQueues.setPushData(content);
                //调用VOM接口
                ServiceResult<String> vomResult = lesService.orderToLes(lineNum, paramLes_tem);
                //                ServiceResult<String> vomResult = new ServiceResult<String>();
                //                vomResult.setSuccess(true);
                //                vomResult.setResult("<request><flag>F</flag><msg>成功</msg></request>");
                if (vomResult == null || !vomResult.getSuccess()) {//调用VOM出异常
                    invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                        .intValue() + 1);
                    invTransferLineCancelQueues.setLastMessage("调用VOM接口失败");
                    invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                    log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + "调用VOM接口失败"
                              + JsonUtil.toJson(vomResult));
                    continue;
                }
                resultXml = vomResult.getResult();
                if (resultXml == null) {
                    invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                        .intValue() + 1);
                    invTransferLineCancelQueues.setLastMessage("调用VOM接口返回结果为空");
                    invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                    log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + "调用VOM接口返回结果为空"
                              + JsonUtil.toJson(vomResult));
                    continue;
                }
                //处理VOM返回结果，解析xml
                HttpResult<String> httpresult = accessExternalInterface
                    .getLesToOrderResult(resultXml);

                //VOM返回结果
                if (httpresult == null || httpresult.getSuccess() == null) {
                    invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                        .intValue() + 1);
                    invTransferLineCancelQueues.setLastMessage("调用VOM接口返回结果解析错误");
                    invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                    log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + "调用VOM接口返回结果解析错误" + resultXml);
                    continue;
                }

                invTransferLineCancelQueues.setReturnData(JsonUtil.toJson(httpresult));
                // false:失败
                if (!httpresult.getSuccess()) { //调用接口返回false
                    invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                        .intValue() + 1);
                    invTransferLineCancelQueues.setLastMessage("3W同步取消物流失败");
                    invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                    log.error("3W调拨取消同步物流时异常：" + "单号：" + lineNum + "3W同步取消物流失败"
                              + JsonUtil.toJson(httpresult));
                    continue;
                } else {// true：成功 
                    invTransferLineCancelQueues.setSuccess(InvTransferLineCancelQueues.SUCCESS);
                    invTransferLineCancelQueues
                        .setSuccessTime(((Long) (System.currentTimeMillis() / 1000)).intValue());
                    invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                        .intValue() + 1);
                    invTransferLineCancelQueues.setLastMessage("取消成功");
                    invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                    //更新调拨单状态
                    cancelTransfer(new Integer[] { line.getLineId() },
                        invTransferLineCancelQueues.getModifyUser());
                }
            } catch (Exception e) {
                invTransferLineCancelQueues.setCount(invTransferLineCancelQueues.getCount()
                    .intValue() + 1);
                invTransferLineCancelQueues.setLastMessage("3W调拨取消同步物流时发生异常" + e.getMessage());
                invTransferLineCancelQueuesDao.update(invTransferLineCancelQueues);
                log.error("3W调拨取消同步物流时发生异常：" + "单号：" + lineNum, e);
            }
        }
    }

    /**
     * 调拨管理-添加3W调拨取消队列
     */
    public boolean addCancelQueue(String lines, String user) {
        String[] lineArr = lines.split("@");
        String[] lineNumArr = lineArr[0].split(",");
        String[] lineIdStrArr = lineArr[1].split(",");
        Integer[] lineIdArr = new Integer[lineIdStrArr.length];
        for (int i = 0; i < lineIdStrArr.length; i++) {
            lineIdArr[i] = Integer.parseInt(lineIdStrArr[i]);
        }

        //在一个事务中处理变更
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            for (String lineNum : lineNumArr) {
                if (invTransferLineCancelQueuesDao.get(lineNum) == null) {
                    InvTransferLineCancelQueues q = new InvTransferLineCancelQueues();
                    q.setLineNum(lineNum);
                    q.setAddTime(((Long) (System.currentTimeMillis() / 1000)).intValue());
                    q.setPushData("");
                    q.setReturnData("");
                    q.setSuccess(0);
                    q.setCount(0);
                    q.setSuccessTime(0);
                    q.setLastMessage("");
                    q.setModifyUser(user);
                    invTransferLineCancelQueuesDao.insert(q);
                    InvTransferLine line = transferLineDao.getInvTransferLineByLineNum(lineNum);
                    if (line != null && StringUtil.isEmpty(line.getRemark())) {
                        line.setRemark("");
                    }
                    if (line != null && !line.getRemark().contains("加入3W取消物流队列")) {
                        transferLineDao.updateRemarkByLineId(line.getLineId(),
                            "加入3W取消物流队列，自动调用物流接口," + line.getRemark());
                    }
                }
            }

            this.recordTransferLogs(lineIdArr, InvTransferLog.LOG_TYPE_3W_CANCEL_QUEUE, user,
                new Date(), "加入3W取消物流队列，自动调用物流接口");
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            log.error("调拨管理-添加3W调拨取消队列发生异常：", e);
            return false;
        }
        return true;
    }

    private String get3WTranferCancelContentXml(String lineNum) {
        StringBuffer sb = new StringBuffer();
        sb.append("<CancelCode>");
        sb.append("<orderno>" + lineNum + "</orderno>");//单号
        sb.append("<canceltype>1</canceltype>");//取消类型：1.出库前取消 2.拦截订单
        sb.append("<cancelexplain></cancelexplain>");//取消说明
        sb.append("<attributes></attributes>");//属性备注
        sb.append("</CancelCode>");
        return sb.toString();
    }

}
