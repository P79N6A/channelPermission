package com.haier.afterSale.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.model.ThTransactionModel;
import com.haier.afterSale.service.ThTransactionService;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.stock.model.InvThOrder;
import com.haier.stock.model.InvThTransaction;
@Service
public class ThTransactionServiceImpl implements ThTransactionService {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(ThTransactionModel.class);
    @Autowired
    ThTransactionModel                     thTransactionModel;

    @Override
    public ServiceResult<Boolean> saveHpTransIn(List<InvThTransaction> thTransList) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean isSuc = thTransactionModel.saveTransInOut(thTransList, true);
            result.setSuccess(isSuc);
            result.setResult(isSuc);
            result.setMessage("京东不良品-记录入库记录成功");
        } catch (Exception e) {
            result.setResult(false);
            result.setMessage("京东不良品-添加入库记录时发生异常" + e.getMessage());
            log.error("saveHpTransIn:京东不良品-添加入库记录时发生异常", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveLesTransOut(List<InvThTransaction> thTransList) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean isSuc = thTransactionModel.saveTransInOut(thTransList, false);
            result.setSuccess(isSuc);
            result.setResult(isSuc);
            result.setMessage("记录出库记录成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("京东不良品-京东不良品-添加出库记录时发生异常" + e.getMessage());
            log.error("saveLesTransOut:京东不良品-添加出库记录时发生异常", e);
        }
        return result;
    }

    public ServiceResult<List<InvThTransaction>> getInDataList() {
        return this.getInDataList(InvThTransaction.CHANNEL_JINGDONG);
    }

    @Override
    public ServiceResult<List<InvThTransaction>> getInDataList(String channel) {
        ServiceResult<List<InvThTransaction>> result = new ServiceResult<List<InvThTransaction>>();
        try {
            List<InvThTransaction> allThTransList = new ArrayList<InvThTransaction>();
            List<InvThTransaction> thTransList = thTransactionModel.getInDataList(channel);
            List<InvThTransaction> thTransMachineSetList = thTransactionModel
                .getInDataMachineSetList(channel);
            allThTransList.addAll(thTransList);
            allThTransList.addAll(thTransMachineSetList);
            result.setResult(allThTransList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("京东不良品-查询入库数据记录异常");
            log.error("getInDataList:京东不良品-查询入库数据记录异常", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateInStatusByOrderSns(Map<String, Object> params) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(thTransactionModel.updateInStatusByOrderSns(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("京东不良品-更新入库数据状态异常");
            log.error("updateInStatusByOrderSns:京东不良品-更新入库数据状态异常", e);
        }
        return result;
    }

    public ServiceResult<List<InvThTransaction>> getOutDataList() {
        return this.getOutDataList(InvThTransaction.CHANNEL_JINGDONG);
    }

    public ServiceResult<List<InvThTransaction>> getHpNodesList() {
        ServiceResult<List<InvThTransaction>> result = new ServiceResult<List<InvThTransaction>>();
        try {
            result.setResult(thTransactionModel.getHpNodesList());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("商城不良品入库");
            log.error("商城不良品－接收hp数据，在开提单节点和入库节点分别记录日志", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvThTransaction>> getOutDataList(String channel) {
        ServiceResult<List<InvThTransaction>> result = new ServiceResult<List<InvThTransaction>>();
        try {
            result.setResult(thTransactionModel.getOutDataList(channel));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("京东不良品-获取出库数据异常");
            log.error("getOutDataList:京东不良品-京东不良品数据-获取出库数据", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateOutStatusByVbelnSos(Map<String, Object> params) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(thTransactionModel.updateOutStatusByVbelnSos(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("京东不良品-更新出库数据状态异常");
            log.error("updateOutStatusByVbelnSos:京东不良品数据-更新出库数据状态异常", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> succReceiveInData(List<String> orderSnList) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            if (orderSnList != null && orderSnList.size() > 0) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("inStatus", "4");//4=入库已发sap
                params.put("corder_sn", orderSnList);
                params.put("where_in_status", "2");//等于2的才更新
                result.setResult(thTransactionModel.updateInStatusByOrderSns(params));
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("京东不良品-更新入库数据已发SAP状态异常");
            log.error("succReceiveInData:京东不良品数据-更新入库数据已发SAP状态异常", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvThTransaction>> queryRepairTransData(Integer repairStatus) {
        ServiceResult<List<InvThTransaction>> resultTransList = new ServiceResult<List<InvThTransaction>>();
        try {
            List<InvThTransaction> queryTransList = thTransactionModel
                .getInCompleteThTransList(repairStatus);
            resultTransList.setResult(queryTransList);
        } catch (Exception e) {
            resultTransList.setResult(null);
            resultTransList.setMessage("查询商城不良品--需关闭的不良品查询失败");
            log.error("queryRepairTransData：查询商城不良品--需关闭的不良品查询失败", e);
        }
        return resultTransList;
    }

    @Override
    public ServiceResult<Boolean> updateRepairStatus(String repairSn, Integer repairStatus,
                                                     String message) {
        ServiceResult<Boolean> updateResult = new ServiceResult<Boolean>();
        try {
            Boolean isUpdate = thTransactionModel.updateRepairStatus(repairSn, repairStatus,
                message);
            updateResult.setResult(isUpdate);
            updateResult.setMessage("不良品更细repairStatus成功！");
        } catch (Exception e) {
            updateResult.setResult(false);
            updateResult.setMessage("商城不良品--更新repairStatus失败repairSn[" + repairSn + "]");
            log.error("updateRepairStatus：商城不良品--更新repairStatus失败repairSn[" + repairSn + "]", e);
        }
        return updateResult;
    }

    @Override
    public ServiceResult<Boolean> updateJlRepairStatus(String repairSn, Integer repairStatus,
                                                       String message, String channel) {
        ServiceResult<Boolean> updateResult = new ServiceResult<Boolean>();
        try {
            Boolean isUpdate = thTransactionModel.updateJlRepairStatus(repairSn, repairStatus,
                message, channel);
            updateResult.setResult(isUpdate);
            updateResult.setMessage("统帅彩电不良品更细repairStatus成功！");
        } catch (Exception e) {
            updateResult.setResult(false);
            updateResult.setMessage("统帅彩电不良品--更新repairStatus失败repairSn[" + repairSn + "]");
            log.error("updateRepairStatus：统帅彩电不良品--更新repairStatus失败repairSn[" + repairSn + "]", e);
        }
        return updateResult;
    }

    @Override
    public ServiceResult<Integer> updateById(InvThTransaction thTrans) {

        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            Integer count = thTransactionModel.updateById(thTrans);
            if (count != null && count > 0) {
                result.setSuccess(true);
                result.setMessage("更新成功");
            } else {
                result.setSuccess(false);
                result.setMessage("没有更新到相符合的数据");
            }
            result.setResult(count);
        } catch (Exception e) {
            log.error("更新不良品信息出现异常", e);
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("更新不良品信息出现异常");
        }
        return result;
    }

    @Override
    public ServiceResult<InvThTransaction> get(Integer id) {

        ServiceResult<InvThTransaction> result = new ServiceResult<InvThTransaction>();
        try {
            InvThTransaction transaction = thTransactionModel.get(id);
            if (transaction != null) {
                result.setResult(transaction);
                result.setMessage("查询不良品信息成功");
            } else {
                result.setResult(null);
                result.setMessage("不良品信息不存在");
            }
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("查询不良品信息出现异常", e);
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询不良品信息出现异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvThTransaction>> getInvThOrderInDataList(String channel) {
        ServiceResult<List<InvThTransaction>> result = new ServiceResult<List<InvThTransaction>>();
        try {
            List<InvThTransaction> thTransList = thTransactionModel
                .getInvThOrderInDataList(channel);
            result.setResult(thTransList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("统帅彩电不良品-查询虚入数据记录异常");
            log.error("[getInvThOrderInDataList]统帅彩电不良品-查询虚入数据记录异常", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvThTransaction>> getInvThOrderOutDataList(String channel) {
        ServiceResult<List<InvThTransaction>> result = new ServiceResult<List<InvThTransaction>>();
        try {
            List<InvThTransaction> thTransList = thTransactionModel
                .getInvThOrderOutDataList(channel);
            result.setResult(thTransList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("统帅彩电不良品-查询虚出数据记录异常");
            log.error("[getInvThOrderInDataList]统帅彩电不良品-查询虚出数据记录异常", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvThTransaction>> queryInvThOrderRepairCloseData(Integer repairStatus,
                                                                                String channel) {
        ServiceResult<List<InvThTransaction>> resultTransList = new ServiceResult<List<InvThTransaction>>();
        try {
            List<InvThTransaction> queryTransList = thTransactionModel
                .queryInvThOrderRepairCloseData(repairStatus, channel);
            resultTransList.setResult(queryTransList);
        } catch (Exception e) {
            resultTransList.setResult(null);
            resultTransList.setMessage("查询统帅彩电不良品--需关闭的不良品查询失败");
            log.error("queryInvThOrderRepairCloseData：查询统帅彩电不良品--需关闭的不良品查询失败", e);
        }
        return resultTransList;
    }

    @Override
    public ServiceResult<Boolean> saveInvThTransInFormJl(InvThTransaction invThTransaction) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean isSuc = thTransactionModel.saveInvThTransInFormJl(invThTransaction);
            result.setSuccess(true);
            result.setResult(isSuc);
            result.setMessage("统帅彩电不良品-记录入库记录成功");
        } catch (BusinessException bex) {
            result.setSuccess(true);
            result.setResult(false);
            result.setMessage(bex.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("统帅彩电不良品-添加入库记录时发生异常" + e.getMessage());
            log.error("[saveInvThTransInFormJl]统帅彩电不良品-添加入库记录时发生异常", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvThOrder>> queryInvThOrderData() {
        ServiceResult<List<InvThOrder>> resultTransList = new ServiceResult<List<InvThOrder>>();
        try {
            List<InvThOrder> queryTransList = thTransactionModel.queryInvThOrderData();
            resultTransList.setResult(queryTransList);
            resultTransList.setSuccess(true);
        } catch (Exception e) {
            resultTransList.setSuccess(false);
            resultTransList.setResult(null);
            resultTransList.setMessage("查询统帅彩电不良品PR信息失败");
            log.error("queryInvThOrderData：查询统帅彩电不良品PR信息失败", e);
        }
        return resultTransList;
    }

    @Override
    public ServiceResult<Boolean> updateInvThOrder(InvThOrder invThOrder) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(thTransactionModel.updateInvThOrder(invThOrder));
            result.setSuccess(true);
            result.setMessage("更新成功");
        } catch (Exception e) {
            log.error("更新统帅彩电不良品PR信息出现异常,id:" + invThOrder.getId(), e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("更新不良品信息出现异常");
        }
        return result;
    }

    public ThTransactionModel getThTransactionModel() {
        return thTransactionModel;
    }

    public void setThTransactionModel(ThTransactionModel thTransactionModel) {
        this.thTransactionModel = thTransactionModel;
    }

}
