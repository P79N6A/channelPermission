package com.haier.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
//import com.haier.svc.bean.pop.InvTransferLine;
//import com.haier.svc.bean.pop.InvTransferLog;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InvTransferLog;

/**
 * 货物调拨管理接口
 *                       
 * @Filename: TransferService.java
 * @Version: 1.0
 * @Author: 吴坤洋
 * @Email: wkybeixi@163.com
 *
 */
public interface TransferLineService {

    /**
     * 取得货物调拨列表
     * @param params
     * @param pagerInfo
     * @return
     */
    ServiceResult<List<InvTransferLine>> getTransferLines(Map<String, Object> params,
                                                          PagerInfo pagerInfo);

    /**
     * 取得货物调拨历史操作列表
     * @param lineId
     * @return
     */
    ServiceResult<List<InvTransferLog>> getTransferOperationHistory(Integer lineId);

    /**
     * 调拨管理-提交
     * @param lineIds
     * @param user
     * @return
     */
    ServiceResult<Boolean> submitTransfer(String lineIds, String user, String isFirst);

    /**
     * 调拨管理-删除
     * @param lineIds
     * @param user
     * @return
     */
    ServiceResult<Boolean> removeTransfer(String lineIds, String user);

    /**
     * 调拨管理-取消
     * @param lineIds
     * @param user
     * @return
     */
    ServiceResult<Boolean> cancelTransfer(String lineIds, String user);

    /**
     * 根据lineIds更新状态
     * @param lineId
     * @param status
     * @return
     */
    ServiceResult<Boolean> updateLineStatusByLineId(Integer lineId, int status);

    /**
     * 调拨管理-导入平铺/缺货调货记录
     * @param lines
     * @return
     */
    ServiceResult<Integer> uploadTransferRecored(List<InvTransferLine> lines);

    /**
     * 调拨管理-导入平铺/缺货调货记录-库存数量校验：WA库存数-商城锁定库存数>=调拨数量
     * @param secCode
     * @param sku
     * @param transferQty
     * @return
     */
    ServiceResult<String> checkStorageForUploadTransferRecord(String secCode, String sku,
                                                              int transferQty);

    /**
     * 检查base stock 库存数
     * @param secCode
     * @param sku
     * @param transferQty
     * @return
     */
    ServiceResult<Integer> checkStorageForBaseStock(String secCode, String sku, int transferQty);

    /**
     * 根据调拨网单号码取得调拨网单
     * @param lineNum
     * @return
     */
    ServiceResult<InvTransferLine> getInvTransferLineByLineNum(String lineNum);

    /**
     * 根据销售网单号取得调拨网单
     * @param soLineNum
     * @return
     */
    ServiceResult<InvTransferLine> getInvTransferLineBySoLineNum(String soLineNum);

    /**
     * 根据调拨网单ID取得调拨网单
     * @param lineId
     * @return
     */
    ServiceResult<InvTransferLine> getInvTransferLineByLineID(Integer lineId);

    /**
     * LES调拨入库后更新调拨单状态为已完成
     * @param line
     * @param billTime
     * @param remark
     * @return
     */
    ServiceResult<Integer> updateTransferLineAfterLesInput(InvTransferLine line, Date billTime,
                                                           String remark);

    /**
     * LES调拨出库库后更新调拨单状态为待入库
     * @param line
     * @param billTime
     * @param remark
     * @return
     */
    ServiceResult<Integer> updateTransferLineAfterLesOut(InvTransferLine line, Date billTime,
                                                         String remark);

    /**
     * LES调拨入库后更新调拨单状态为已完成
     * @param line
     * @param billTime
     * @param remark
     * @return
     */
    ServiceResult<Integer> updateTransferLineAfterSyncToLes(InvTransferLine line, Date billTime,
                                                            String remark);

    /**
     * 根据网单状态取得调拨网单
     * @param lineStatus
     * @return
     */
    ServiceResult<List<InvTransferLine>> getTransferLineByLineStatus(Integer lineStatus);

    /**
     * 调拨费用审核
     * @param lineId
     * @param operation
     * @param reason
     * @param user
     * @return
     */
    ServiceResult<Boolean> transferFeeAudit(int lineId, String operation, String reason,
                                            String user);

    /**
     * 调拨管理-导入缺货调货记录-销售网单号码唯一性校验
     * @param lineNum
     * @return
     */
    ServiceResult<Boolean> checkQHLineNumForUploadTransferRecord(String lineNum);

    /**
     * LES传CBS调货费用接口
     * @param transfers
     * @return
     */
    ServiceResult<Boolean> saveTransferFee(List<InvTransferLine> transfers);

    /**
     * 传递用于录入费用的调拨单信息到LES后更新调拨单状态为待录入费用
     * @param line
     * @return
     */
    ServiceResult<Integer> updateTransferLineAfterToLesForFeeInput(InvTransferLine line);

    /**
     * 定时任务---自动释放锁定渠道库存
     * @return
     */
    ServiceResult<Boolean> autoInnerTransferForReservedStock();

    /**
     * 
     * @param secCode
     * @param sku
     * @param transferQty 调拨苏亮
     * @return ： 是否检查成功
     */
    ServiceResult<String> checkStockQty(String secCode, String sku, int transferQty);

    /**
     * 
     * @param lines： 导入数据
     * @return 返回导入成功条数
     */
    ServiceResult<Integer> saveInnerTransfers(List<InvTransferLine> lines);

    /**
     * 保存单个调拨记录
     * @param transferLine
     * @return
     */
    ServiceResult<String> saveInnerTransfer(InvTransferLine transferLine, boolean onSubmit);

    /**
     * 3w－调拨单提交后传Vom
     * @param transferLine
     * @return
     */
    ServiceResult<Boolean> syncInnerTransfersToVom();

    /**
    * 更新备注
    * @return
    */
    ServiceResult<Integer> updateRemarkByLineId(Integer id, String remark);

    /**
     * 3W调拨取消同步取消物流
     * @return
     */
    ServiceResult<Boolean> cancelTransferLineToLesOnTime(String lineNum);

    /**
     * 添加3W调拨取消队列
     * @return
     */
    ServiceResult<Boolean> addCancelQueue(String lineNums, String user);

    /**
     * 3W调拨取消同步取消物流－定时任务
     * @return
     */
    ServiceResult<Boolean> cancelTransferLineToLesJob();
}
