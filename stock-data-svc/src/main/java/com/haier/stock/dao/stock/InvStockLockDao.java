package com.haier.stock.dao.stock;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvStockLock;


public interface InvStockLockDao {

    InvStockLock getNotReleased(@Param("refNo") String refNo, @Param("sku") String sku,
                                @Param("sec_code") String secCode);

    InvStockLock getLast(@Param("refNo") String refNo, @Param("sku") String sku,
                         @Param("sec_code") String secCode);

    /**
     * 根据单据号获取冻结记录
     * @param refNo
     * @return
     */
    List<InvStockLock> getNotReleasedByRefNo(@Param("refNo") String refNo);

    List<InvStockLock> getNotReleaseBySkuAndWh(@Param("whCode") String whCode,
                                               @Param("sku") String sku,
                                               @Param("channelCode") String channelCode);

    /**
     * 获得需要重新分配渠道的记录
     * @return
     */
    List<InvStockLock> getProcessStockLock();

    /**
     * 根据单据号获取冻结记录
     * @param refNo
     * @return
     */
    List<InvStockLock> getNotReleasedByRefNoSku(@Param("refNo") String refNo,
                                                @Param("sku") String sku);

    List<InvStockLock> getByRefNoAndSku(@Param("refNo") String refNo, @Param("sku") String sku);

    Integer insert(InvStockLock stockLock);

    Integer update(InvStockLock stockLock);

    /**
     * 增加释放数
     * @param id
     * @param releaseQty
     * @return
     */
    Integer updateReleaseQty(@Param("id") Integer id, @Param("releaseQty") Integer releaseQty,
                             @Param("optUser") String optUser);

    /**
    * 增加占用数
    * @param id
    * @param lockQty
    * @return
    */
    Integer updateLockQty(@Param("id") Integer id, @Param("lockQty") Integer lockQty,
                          @Param("optUser") String optUser);

    /**
     * 查询一段时间后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
    List<InvStockLock> getNoReleaseByLockTime(@Param("lockTime") String lockTime,
                                              @Param("topx") Integer topx);
}