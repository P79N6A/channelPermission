package com.haier.stock.service;

import com.haier.stock.model.InvStoreSynch;

import java.util.List;

public interface InvStoreSynchService {

    /**
     * 根据id查询InvStoreSynch
     * @param id
     * @return
     */
    public InvStoreSynch get(Integer id);

    /**
     * 插入InvStoreSynch
     * @param invStoreSynch
     * @return
     */
    public Integer insert(InvStoreSynch invStoreSynch);

    /**
     * 批量插入InvStoreSynch
     * @param invStoreSynchList
     * @return
     */
    public Integer batchInsert( List<InvStoreSynch> invStoreSynchList);

    /**
     * 根据ID更新状态
     * @param id
     * @param status
     * @param message
     * @return
     */
    public Integer updateStatusById(  Integer id,   Integer status,
                                      String message);

    /**
     * 更新InvStoreSynch
     * @param invStoreSynch
     * @return
     */
    public Integer update(InvStoreSynch invStoreSynch);

    /**
     * 查询InvStoreSynch
     * @return
     */
    public List<InvStoreSynch> queryStoreList( Integer topx);
}
