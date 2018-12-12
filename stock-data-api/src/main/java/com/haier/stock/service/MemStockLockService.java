package com.haier.stock.service;

import java.util.List;


import com.haier.common.PagerInfo;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InvStockLockEx;

public interface MemStockLockService {
	  /**
     * 查询最新的手动锁定记录
     * @return
     */
    InvStockLock getLastMemStockLock();

    /**
     * 查询SD手动锁定没有释放的记录
     * @param stockLock
     * @return
     */
    List<InvStockLockEx> queryMemStockLockList( InvStockLockEx stockLock,
                                               PagerInfo pager);
    
    /**
     * 查询WD手动锁定没有释放的记录
     * @param stockLock
     * @return
     */
    List<InvStockLockEx> queryMemStockWDLockList(InvStockLockEx stockLock,
                                               PagerInfo pager);

    InvStockLockEx getMemStockLock(InvStockLockEx stockLock);

    Integer updateStockLock(InvStockLockEx stockLock);

    int getRowCnt();
}
