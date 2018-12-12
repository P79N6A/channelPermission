/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.dao.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity;
import com.haier.purchase.data.model.GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity;


public interface LesStockInfoDao {

    public void updateInOutInfo(GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity info);

    public void insertInOutInfo(GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity info);

    public Integer selectInOutInfo(GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity info);

    public int selectStockInfo(GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity info);

    public void updateStockInfo(GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity info);

    public void insertStockInfo(GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity info);

    // 根据提单号查询WA出库信息
    public List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> selectInOutInfoByBSTNK(List<String> ids);

    // 根据Les订单号查询WA出库信息
    public List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> selectInOutInfoBySo(List<String> ids);

    //根据85DN号查询WA出库信息
    public List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> selectInOutInfoByDn(List<String> dn);

    //根据85DN号查询WA出库信息
    public List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> selectInOutInfoBySingleDn(@Param("dn") String dn);

    /**
     * 查询最后一次更新LES时间
     * @return
     */
    public String selectLastSyncTime();
}
