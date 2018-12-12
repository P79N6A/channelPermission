package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.InvStoreLock;
import org.apache.ibatis.annotations.Param;

public interface InvStoreLockDao {

    public Integer insert(InvStoreLock storeLock);

    /**
     * 通过单据号、物料编码和店铺码查询没有释放的库存
     * @param refNo
     * @param sku
     * @param storeCode
     * @return
     */
    public List<InvStoreLock> getNotReleased(@Param("refNo") String refNo, @Param("sku") String sku,
                                             @Param("storeCode") String storeCode,
                                             @Param("itemProperty") String itemProperty);

    /**
     * 通过单据号、物料编码和店铺码查询没有释放的库存（只取一条）
     * @param refNo
     * @param sku
     * @param storeCode
     * @return
     */
    public InvStoreLock getNotReleasedForUpdate(@Param("refNo") String refNo,
                                                @Param("sku") String sku,
                                                @Param("storeCode") String storeCode,
                                                @Param("itemProperty") String itemProperty);

    /**
     * 更新店铺库存占用表中的库存释放数量
     * @param id
     * @param releaseQty
     * @param optUser
     * @return
     */
    public Integer updateReleaseQty(@Param("id") Integer id,
                                    @Param("releaseQty") Integer releaseQty,
                                    @Param("optUser") String optUser,
                                    @Param("itemProperty") String itemProperty);

    /**
     * 查询一段时间后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
    public List<InvStoreLock> getNoReleaseByLockTime(@Param("lockTime") String lockTime,
                                                     @Param("topx") Integer topx);

}
