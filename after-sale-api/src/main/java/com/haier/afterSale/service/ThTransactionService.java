package com.haier.afterSale.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvThOrder;
import com.haier.stock.model.InvThTransaction;


public interface ThTransactionService {

    /**
     * 京东不良品数据入库数据-FROM HP
     * @return
     */
    public ServiceResult<Boolean> saveHpTransIn(List<InvThTransaction> thTransList);

    /**
     * 京东不良品数据出库数据-From LES
     * @return
     */
    public ServiceResult<Boolean> saveLesTransOut(List<InvThTransaction> thTransList);

    /**
     * 京东不良品数据-获取数据-默认京东
     * @return
     */
    public ServiceResult<List<InvThTransaction>> getInDataList();

    /**
     * 京东不良品数据-获取入库数据
     * @return
     */
    public ServiceResult<List<InvThTransaction>> getInDataList(String channel);

    /**
     * 京东不良品数据-更新入库数据状态
     * @return
     */
    public ServiceResult<Integer> updateInStatusByOrderSns(Map<String, Object> params);

    /**
     * 京东不良品数据-获取出库数据-默认京东
     * @return
     */
    public ServiceResult<List<InvThTransaction>> getOutDataList();

    /**
     * 京东不良品数据-获取出库数据
     * @return
     */
    public ServiceResult<List<InvThTransaction>> getOutDataList(String channel);

    /**
     * 京东不良品数据-更新出库数据状态
     * @return
     */
    public ServiceResult<Integer> updateOutStatusByVbelnSos(Map<String, Object> params);

    /**
     * 京东不良品数据-更新入库数据已发SAP状态
     * @return
     */
    public ServiceResult<Integer> succReceiveInData(List<String> orderSnList);

    /**
     * 通过ID更新
     * @param thTrans
     * @return
     */
    public ServiceResult<Integer> updateById(InvThTransaction thTrans);

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    public ServiceResult<InvThTransaction> get(Integer id);

    /**
     * 查询需要关单的不良品数据
     * @return
     */
    public ServiceResult<List<InvThTransaction>> queryRepairTransData(Integer repairStatus);

    /**
     * 更新状态
     * @param repairSn
     * @return
     */
    public ServiceResult<Boolean> updateRepairStatus(String repairSn, Integer repairStatus,
                                                     String message);

    /**
     * 接收hp数据 查询 1.开提单节点，2.入库节点 分别记录日志
     * @return
     */
    public ServiceResult<List<InvThTransaction>> getHpNodesList();

    /**
     * 统帅彩电不良品数据-获取虚入数据
     * @return
     */
    public ServiceResult<List<InvThTransaction>> getInvThOrderInDataList(String channel);

    /**
     * 统帅彩电不良品数据-获取虚出数据
     * @return
     */
    public ServiceResult<List<InvThTransaction>> getInvThOrderOutDataList(String channel);

    /**
     * 查询需要关单的统帅彩电不良品数据
     * @return
     */
    public ServiceResult<List<InvThTransaction>> queryInvThOrderRepairCloseData(Integer repairStatus,
                                                                                String channel);

    /**
     * 统帅彩电不良品入库数据写入-FROM JL
     * @return
     */
    public ServiceResult<Boolean> saveInvThTransInFormJl(InvThTransaction invThTransaction);

    /**
     * 查询统帅彩电不良品PR信息
     * @return
     */
    public ServiceResult<List<InvThOrder>> queryInvThOrderData();

    /**
     * 更新统帅彩电不良品PR信息
     * @return
     */
    public ServiceResult<Boolean> updateInvThOrder(InvThOrder invThOrder);

    /**
     * 更新状态
     * @param repairSn
     * @return
     */
    public ServiceResult<Boolean> updateJlRepairStatus(String repairSn, Integer repairStatus,
                                                       String message, String channel);
}
