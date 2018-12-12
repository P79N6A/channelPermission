package com.haier.order.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.order.model.TransferLineModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InvTransferLog;

/**
 * 货物调拨管理实现类
 *
 * @Filename: TransferServiceImpl.java
 * @Version: 1.0
 * @Author: 吴坤洋
 * @Email: mqianger@163.com
 */
@Service
public class OrderCenterTransferLineServiceImpl {

    private static Logger log = LoggerFactory.getLogger(OrderCenterTransferLineServiceImpl.class);
    @Autowired
    private TransferLineModel transferLineModel;

    public void setTransferLineModel(TransferLineModel transferLineModel) {
        this.transferLineModel = transferLineModel;
    }

    /**
     * 取得货物调拨列表
     *
     * @param params
     * @param pagerInfo
     * @return
     */
    
    public ServiceResult<List<InvTransferLine>> getTransferLines(Map<String, Object> params,
                                                                 PagerInfo pagerInfo) {
        ServiceResult<List<InvTransferLine>> result = new ServiceResult<List<InvTransferLine>>();
        try {
            int count = transferLineModel.getTransferLinesCount(params);
            result.setResult(transferLineModel.getTransferLines(params, pagerInfo));
            pagerInfo.setRowsCount(count);
            result.setPager(pagerInfo);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage("无法获取货物调拨列表:" + e.getMessage());
        } catch (Exception e) {
            log.error("获取货物调拨列表时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("获取货物调拨列表时发生未知异常");
        }
        return result;
    }

    /**
     * 取得货物调拨历史操作列表
     *
     * @param lineId
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#getTransferOperationHistory(java.lang.Integer)
     */
    
    public ServiceResult<List<InvTransferLog>> getTransferOperationHistory(Integer lineId) {
        ServiceResult<List<InvTransferLog>> result = new ServiceResult<List<InvTransferLog>>();
        try {
            List<InvTransferLog> list = transferLineModel.getTransferOperationHistory(lineId);
            result.setResult(list);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage("无法获取货物调拨历史操作列表:" + e.getMessage());
        } catch (Exception e) {
            log.error("货物调拨历史操作列表时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("获取货物调拨列表时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨管理-提交
     *
     * @param lineIds
     * @param user
     * @return
     */
    
    public ServiceResult<Boolean> submitTransfer(String lineIds, String user, String isFirst) {
        ServiceResult<Boolean> result;
        try {
            Integer[] lineIdArr = getLineIds(lineIds);
            result = transferLineModel.submitBatchTransfer(lineIdArr, user, isFirst);
        } catch (Exception e) {
            log.error("调拨管理-提交时发生未知异常：" + e);
            result = new ServiceResult<Boolean>();
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    private Integer[] getLineIds(String lineIds) {
        String[] lineIdStrArr = lineIds.split(",");
        Integer[] lineIdArr = new Integer[lineIdStrArr.length];
        for (int i = 0; i < lineIdStrArr.length; i++) {
            lineIdArr[i] = Integer.parseInt(lineIdStrArr[i]);
        }
        return lineIdArr;
    }

    /**
     * 调拨管理-删除
     *
     * @param lineIds
     * @param user
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#removeTransfer(java.lang.String, java.lang.String)
     */
    
    public ServiceResult<Boolean> removeTransfer(String lineIds, String user) {
        ServiceResult<Boolean> result;
        try {
            Integer[] lineIdArr = getLineIds(lineIds);
            result = transferLineModel.removeTransfer(lineIdArr, user);
        } catch (Exception e) {
            log.error("调拨管理-删除时发生未知异常：" + e);
            result = new ServiceResult<Boolean>();
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨管理-取消
     *
     * @param lineIds
     * @param user
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#cancelTransfer(java.lang.String, java.lang.String)
     */
    
    public ServiceResult<Boolean> cancelTransfer(String lineIds, String user) {
        ServiceResult<Boolean> result;
        try {
            Integer[] lineIdArr = getLineIds(lineIds);
            result = transferLineModel.cancelTransfer(lineIdArr, user);
        } catch (Exception e) {
            log.error("调拨管理-取消时发生未知异常：" + e);
            result = new ServiceResult<Boolean>();
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨管理-导入平铺/缺货调货记录
     *
     * @param lines
     * @return
     */
    
    public ServiceResult<Integer> uploadTransferRecored(List<InvTransferLine> lines) {
        ServiceResult<Integer> result;
        try {
            result = transferLineModel.uploadTransferRecored(lines);
        } catch (Exception e) {
            log.error("调拨管理-导入调货记录时发生未知异常：" + e);
            result = new ServiceResult<Integer>();
            result.setSuccess(false);
            result.setMessage("调拨管理-导入调货记录时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨管理-导入平铺/缺货调货记录-库存数量校验：WA库存数-商城锁定库存数>=调拨数量
     *
     * @param secCode
     * @param sku
     * @param transferQty
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#checkStorageForUploadTransferRecord(java.lang.String, java.lang.String, int)
     */
    
    public ServiceResult<String> checkStorageForUploadTransferRecord(String secCode, String sku,
                                                                     int transferQty) {
        ServiceResult<String> ret = new ServiceResult<String>();
        try {
            transferLineModel.checkStorageForUploadTransferRecord(secCode, sku, transferQty);
        } catch (BusinessException e) {
            ret.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("调拨管理-库存数量校验时发生未知异常：" + e);
            ret.setSuccess(false);
            ret.setMessage("调拨管理-库存数量校验时发生未知异常");
        }
        return ret;
    }

    public ServiceResult<Integer> checkStorageForBaseStock(String secCode, String sku,
                                                           int transferQty) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            StringBuffer error = new StringBuffer();
            int freeCnt = transferLineModel.checkStorageForBaseStock(secCode, sku, transferQty,
                    error);
            //1.结果为成功
            result.setSuccess(true);
            //2.返回freeCnt
            result.setResult(freeCnt);
            result.setMessage(error.toString());

        } catch (BusinessException e) {
            log.error("虚拟调拨出现异常：" + e.getMessage());
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        } catch (Exception e) {
            log.error("虚拟调拨管理-库存数量校验时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("虚拟调拨管理-库存数量校验时发生未知异常");
        }
        return result;
    }

    /**
     * 根据调拨网单号码取得调拨网单
     *
     * @param lineNum
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#getInvTransferLineByLineNum(java.lang.String)
     */
    
    public ServiceResult<InvTransferLine> getInvTransferLineByLineNum(String lineNum) {
        ServiceResult<InvTransferLine> result = new ServiceResult<InvTransferLine>();
        try {
            InvTransferLine line = transferLineModel.getInvTransferLineByLineNum(lineNum);
            result.setResult(line);
            result.setSuccess(true);
        } catch (Throwable e) {
            log.error("根据调拨网单号码取得调拨网单时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据调拨网单号码取得调拨网单时发生未知异常");
        }
        return result;
    }

    /**
     * 根据销售网单号取得调拨网单
     *
     * @param soLineNum
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#getInvTransferLineBySoLineNum(java.lang.String)
     */
    
    public ServiceResult<InvTransferLine> getInvTransferLineBySoLineNum(String soLineNum) {
        ServiceResult<InvTransferLine> result = new ServiceResult<InvTransferLine>();
        try {
            InvTransferLine line = transferLineModel.getInvTransferLineBySoLineNum(soLineNum);
            result.setResult(line);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("根据销售网单号取得调拨网单时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据销售网单号取得调拨网单时发生未知异常");
        }
        return result;
    }

    
    public ServiceResult<InvTransferLine> getInvTransferLineByLineID(Integer lineId) {
        ServiceResult<InvTransferLine> result = new ServiceResult<InvTransferLine>();
        try {
            InvTransferLine line = transferLineModel.getInvTransferLineByLineID(lineId);
            result.setResult(line);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("根据调拨网单ID取得调拨网单时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据调拨网单ID取得调拨网单时发生未知异常");
        }
        return result;
    }

    /**
     * 根据网单ID更新调拨网单
     *
     * @param line
     * @return
     */
    
    public ServiceResult<Integer> updateTransferLineAfterLesInput(InvTransferLine line,
                                                                  Date billTime, String remark) {
        ServiceResult<Integer> result;
        try {
            result = transferLineModel.updateTransferLineAfterLesInput(line, billTime, remark);
        } catch (Exception e) {
            log.error("LES入库后更新调拨网单时发生未知异常：" + e);
            result = new ServiceResult<Integer>();
            result.setSuccess(false);
            result.setMessage("LES入库后更新调拨网单时发生未知异常");
        }
        return result;
    }

    
    public ServiceResult<Integer> updateTransferLineAfterLesOut(InvTransferLine line, Date billTime,
                                                                String remark) {
        ServiceResult<Integer> result;
        try {
            result = transferLineModel.updateTransferLineAfterLesOut(line, billTime, remark);
        } catch (Exception e) {
            log.error("LES出库后更新调拨网单时发生未知异常：" + e);
            result = new ServiceResult<Integer>();
            result.setSuccess(false);
            result.setMessage("LES出库后更新调拨网单时发生未知异常" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<Integer> updateTransferLineAfterSyncToLes(InvTransferLine line,
                                                                   Date billTime, String remark) {
        ServiceResult<Integer> result;
        try {
            result = transferLineModel.updateTransferLineAfterSyncToLes(line, billTime, remark);
        } catch (Exception e) {
            log.error("LES开提单后更新调拨网单时发生未知异常：" + e);
            result = new ServiceResult<Integer>();
            result.setSuccess(false);
            result.setMessage("LES开提单后更新调拨网单时发生未知异常");
        }
        return result;
    }

    /**
     * 根据网单状态取得调拨网单
     *
     * @param lineStatus
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#getTransferLineByLineStatus(java.lang.Integer)
     */
    
    public ServiceResult<List<InvTransferLine>> getTransferLineByLineStatus(Integer lineStatus) {
        ServiceResult<List<InvTransferLine>> result = new ServiceResult<List<InvTransferLine>>();
        try {
            List<InvTransferLine> lines = transferLineModel.getTransferLineByLineStatus(lineStatus);
            result.setResult(lines);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("根据网单状态取得调拨网单时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据网单状态取得调拨网单时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨费用审核
     *
     * @param lineId
     * @param operation
     * @param reason
     * @param user
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#transferFeeAudit(int, java.lang.String, java.lang.String, java.lang.String)
     */
    
    public ServiceResult<Boolean> transferFeeAudit(int lineId, String operation, String reason,
                                                   String user) {
        ServiceResult<Boolean> result;
        try {
            result = transferLineModel.transferFeeAudit(lineId, operation, reason, user);
        } catch (Exception e) {
            log.error("调拨费用审核时发生未知异常：" + e);
            result = new ServiceResult<Boolean>();
            result.setSuccess(false);
            result.setMessage("提交时发生未知异常");
        }
        return result;
    }

    /**
     * 调拨管理-导入缺货调货记录-销售网单号码唯一性校验
     *
     * @param lineNum
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#checkQHLineNumForUploadTransferRecord(java.lang.String)
     */
    
    public ServiceResult<Boolean> checkQHLineNumForUploadTransferRecord(String lineNum) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean bool = transferLineModel.checkQHLineNumForUploadTransferRecord(lineNum);
            result.setSuccess(true);
            result.setResult(bool);
        } catch (Exception e) {
            log.error("销售网单号码唯一性校验时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("销售网单号码唯一性校验时发生未知异常");
        }
        return result;
    }

    /**
     * LES传CBS调货费用接口
     *
     * @param transfers
     * @return
     */
    
    public ServiceResult<Boolean> saveTransferFee(List<InvTransferLine> transfers) {
        ServiceResult<Boolean> result;
        try {
            result = transferLineModel.saveTransferFee(transfers);
        } catch (Exception e) {
            log.error("LES传CBS调货费用时发生未知异常：" + e);
            result = new ServiceResult<Boolean>();
            result.setResult(false);
            result.setError("", "LES传CBS调货费用时发生未知异常");
        }
        return result;
    }

    /**
     * 传递用于录入费用的调拨单信息到LES后更新调拨单状态为待录入费用
     *
     * @param line
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#updateTransferLineAfterToLesForFeeInput(com.haier.cbs.stock.entity.InvTransferLine)
     */
    
    public ServiceResult<Integer> updateTransferLineAfterToLesForFeeInput(InvTransferLine line) {
        ServiceResult<Integer> result;
        try {
            result = transferLineModel.updateTransferLineAfterToLesForFeeInput(line);
        } catch (Exception e) {
            log.error("传递用于录入费用的调拨单信息到LES后更新调拨单状态时发生未知异常：" + e);
            result = new ServiceResult<Integer>();
            result.setSuccess(false);
            result.setMessage("传递用于录入费用的调拨单信息到LES后更新调拨单状态时发生未知异常");
        }
        return result;
    }

    public ServiceResult<Boolean> autoInnerTransferForReservedStock() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result = transferLineModel.autoInnerTransfer();
        } catch (Exception e) {
            log.error("自动释放预留发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("自动释放预留发生未知异常");
        }
        return result;
    }

    
    public ServiceResult<String> checkStockQty(String secCode, String sku, int transferQty) {
        // TODO Auto-generated method stub
        return null;
    }

    
    public ServiceResult<Integer> saveInnerTransfers(List<InvTransferLine> lines) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result = transferLineModel.saveInnerTransfers(lines);
        } catch (Exception e) {
            log.error("手动释放预留发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("手动释放预留发生未知异常");
        }
        return result;
    }

    public ServiceResult<String> saveInnerTransfer(InvTransferLine transferLine, boolean onSubmit) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            String lineNum = transferLineModel.saveInnerTransfer(transferLine, onSubmit);
            result.setResult(lineNum);
        } catch (Exception e) {
            log.error("保存调拨单发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存调拨单发生未知异常");
        }
        return result;

    }

    
    public ServiceResult<Boolean> updateLineStatusByLineId(Integer lineId, int status) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();

        try {
            boolean isSuc = transferLineModel.updateLineStatusByLineId(lineId, status);
            result.setMessage("更新状态[" + status + "]成功");
            result.setSuccess(isSuc);
        } catch (Exception e) {
            log.error("更新调拨单状态发生异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新调拨单状态发生异常");
        }
        return result;
    }

    /**
     * 3w-3w调拨单传vom，定时任务
     *
     * @return
     * @see com.haier.StockTransferLineService.stock.service.TransferLineService#syncInnerTransfersToVom()
     */
    
    public ServiceResult<Boolean> syncInnerTransfersToVom() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean isSuc = transferLineModel.syncInnerTransfersToVom();
            result.setMessage("更新状态成功");
            result.setSuccess(isSuc);
        } catch (Exception e) {
            log.error("更新调拨单状态发生异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新调拨单状态发生异常");
        }
        return result;
    }

    /**
     * 更新备注
     *
     * @return
     */
    
    public ServiceResult<Integer> updateRemarkByLineId(Integer id, String remark) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(transferLineModel.updateRemarkByLineId(id, remark));
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("根据ID更新调拨单备注时发生未知异常：" + e);
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("根据ID更新调拨单备注时发生未知异常");
        }
        return result;
    }

    
    public ServiceResult<Boolean> cancelTransferLineToLesOnTime(String lineNum) {
        ServiceResult<Boolean> result;
        try {
            result = transferLineModel.cancelTransferLineToLesOnTime(lineNum);
        } catch (Exception e) {
            log.error("3W调拨取消同步取消物流时发生未知异常：", e);
            result = new ServiceResult<Boolean>();
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("3W调拨取消同步取消物流时发生异常");
        }
        return result;
    }

    
    public ServiceResult<Boolean> addCancelQueue(String lineNums, String user) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setSuccess(true);
            result.setResult(transferLineModel.addCancelQueue(lineNums, user));
        } catch (Exception e) {
            log.error("添加3W调拨取消队列发生异常：", e);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("添加3W调拨取消队列发生异常");
        }
        return result;
    }

    
    public ServiceResult<Boolean> cancelTransferLineToLesJob() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            transferLineModel.cancelTransferLineToLesJob();
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            log.error("3W调拨取消同步取消物流定时任务发生未知异常：", e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("3W调拨取消同步取消物流定时任务发生异常");
        }
        return result;
    }
}
