package com.haier.stock.service;

import com.haier.stock.model.InvThTransaction;

import java.util.List;
import java.util.Map;


public interface InvThTransactionService {

    public Integer insertTrans( InvThTransaction thTrans);

    public Integer updateTrans(InvThTransaction thTrans);

    /**
     * 查询单件list
     * @return
     */
    public List<InvThTransaction> getInDataList(String channel);

    /**
     * 查询套机列表
     * @return
     */
    public List<InvThTransaction> getInDataMachineSetList(String channel);

    public List<Map<String, Object>> getOutDataList(String channel);

    public Integer updateInStatusByOrderSns(  Map<String, Object> params);

    public Integer updateInStatusByKeyProductNo(Map<String, Object> params);

    public List<InvThTransaction> querySubList(  Map<String, Object> params);

    public Integer updateOutStatusByVbelnSos(  Map<String, Object> params);

    public InvThTransaction getMaxTransId();

    public InvThTransaction getTrans(InvThTransaction thTrans);

    /**
     * 获取退货单没完结数据
     * @return
     */
    public List<InvThTransaction> getRepairIncompleteList( Integer repairStatus);

    /**
     * 根据退货单号更新repair_status状态
     * @param channelOrderSn
     * @param repairStatus
     * @return
     */
    public Integer updateRepairStatus( String channelOrderSn,
                                        Integer repairStatus,
                                       String message);

    /**
     * 统帅彩电不良品更新repair_status状态
     * @return
     */
    public Integer updateJlRepairStatus(  String channelOrderSn,
                                         Integer repairStatus,
                                         String message,
                                         String channel);

    /**
     * 通过ID更新
     * @param thTrans
     * @return
     */
    public Integer updateById(InvThTransaction thTrans);

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    public InvThTransaction get(Integer id);

    /**
     * hp数据节点
     * @param thTrans
     * @return
     */
    public List<InvThTransaction> getHpNodesList();

    /**
     * 统帅彩电不良品虚入
     * @return
     */
    public List<InvThTransaction> getInvThOrderInDataList(String channel);

    /**
     * 统帅彩电不良品虚出
     * @return
     */
    public List<InvThTransaction> getInvThOrderOutDataList(String channel);

    /**
     * 获取退货单没完结数据
     * @return
     */
    public List<InvThTransaction> queryInvThOrderRepairCloseData(  Integer repairStatus,
                                                                   String channel);

    public InvThTransaction getInvThTransactionByProductNoAndChannelSn(  String productNo,
                                                                        String channelOrderSn);

}
