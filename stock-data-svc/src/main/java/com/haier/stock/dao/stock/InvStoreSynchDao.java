package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.InvStoreSynch;
import org.apache.ibatis.annotations.Param;


public interface InvStoreSynchDao {

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
    public Integer batchInsert(@Param("invStoreSynchList") List<InvStoreSynch> invStoreSynchList);

    /**
     * 根据ID更新状态
     * @param id
     * @param status
     * @param message
     * @return
     */
    public Integer updateStatusById(@Param("id") Integer id, @Param("status") Integer status,
                                    @Param("message") String message);

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
    public List<InvStoreSynch> queryStoreList(@Param("topx") Integer topx);
}
