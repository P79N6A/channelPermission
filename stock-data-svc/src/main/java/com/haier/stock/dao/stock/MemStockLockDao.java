package com.haier.stock.dao.stock;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.common.PagerInfo;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InvStockLockEx;


public interface MemStockLockDao {
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
    List<InvStockLockEx> queryMemStockLockList(@Param("stockLock") InvStockLockEx stockLock,
                                               @Param("pager") PagerInfo pager);
    
    /**
     * 查询WD手动锁定没有释放的记录
     * @param stockLock
     * @return
     */
    List<InvStockLockEx> queryMemStockWDLockList(@Param("stockLock") InvStockLockEx stockLock,
                                               @Param("pager") PagerInfo pager);

    InvStockLockEx getMemStockLock(@Param("stockLock") InvStockLockEx stockLock);

    Integer updateStockLock(@Param("stockLock") InvStockLockEx stockLock);

    int getRowCnt();
    int queryMemStockLockListSum(InvStockLockEx stockLock);
}
