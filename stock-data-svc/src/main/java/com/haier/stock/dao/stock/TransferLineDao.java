package com.haier.stock.dao.stock;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvTransferLine;



/**
 * 货物调拨管理DAO
 *                       
 * @Filename: TransferDao.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 *
 */
public interface TransferLineDao {

    InvTransferLine get(Integer lineId);

    /**
     * 取得货物调拨信息列表
     * @param params
     * @return
     */
    List<InvTransferLine> getTransferLines(Map<String, Object> params);

    /**
     * 取得货物调拨信息数量
     * @param params
     * @return
     */
    Integer getCount(Map<String, Object> params);

    /**
     * 根据调拨单明细（网单）ID数组取得货物调拨信息列表
     * @param lineIdArr
     * @return
     */
    List<InvTransferLine> getByLineIds(Integer[] lineIdArr);

    /**
     * 从菜鸟系统查询调拨入库信息队列
     * @return
     */
    List<InvTransferLine> getInTransferInfoFromCaiNiao();

    /**
     * 调拨管理-提交
     * @param params
     */
    Integer updateLineStatusByLineIds(Map<String, Object> params);

    /**
     * 插入一条调货记录
     * @param line
     */
    void insert(InvTransferLine line);

    /**
     * 取得平铺调货调拨网单号码(lineNum)的序列号
     * @param param
     * @return
     */
    List<InvTransferLine> getPPTransferLineNum(String param);

    /**
     * 根据调拨网单号码取得调拨网单
     * @param lineNum
     * @return
     */
    InvTransferLine getInvTransferLineByLineNum(String lineNum);

    /**
     * 根据销售网单号取得调拨网单
     * @param soLineNum
     * @return
     */
    InvTransferLine getInvTransferLineBySoLineNum(@Param("soLineNum") String soLineNum);

    /**
     * 根据网单状态取得调拨网单
     * @param lineStatus
     * @return
     */
    List<InvTransferLine> getTransferLineByLineStatus(Integer lineStatus);

    /**
     * 根据状态和原因类型取得调拨网单
     * @param lineStatus
     * @return
     */
    List<InvTransferLine> getTransferLine3WByStatusReason(Integer lineStatus);

    /**
     * 更新调拨网单
     * @param line
     * @return
     */
    Integer update(InvTransferLine line);

    /**
     * 调拨管理-更新实际调拨数量
     * @param params
     */
    Integer updateLineTransferQtyByLineId(Map<String, Object> params);

    /**
     * 更新备注
     * @return
     */
    Integer updateRemarkByLineId(@Param("id") Integer id, @Param("remark") String remark);

}
