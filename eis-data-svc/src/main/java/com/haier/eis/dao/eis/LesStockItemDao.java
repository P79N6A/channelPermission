package com.haier.eis.dao.eis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.eis.model.LesStockItem;

import java.util.List;

@Mapper
public interface LesStockItemDao {
    /**
     * 获取未处理的最小LES库存编号批次id
     * @return
     */
    LesStockItem getNotProcessMinLssId();

    /**
     * 获取待转换SKU的最小LES库存编号批次id
     * @return
     */
    List<LesStockItem> getNotConvertedLssId();

    /**
     * 获取未处理完成批次id列表:status = 0 or 10
     * @return
     */
    List<Integer> getNotProcessLssIds();

    /**
     * 根据批次id，获取未处理的队列
     * @return
     */
    List<LesStockItem> getNotProcessListByLssId(Integer lesStockSyncsId);

    /**
     * 根据批次id，获取未转换SKU的队列
     * @return
     */
    List<LesStockItem> getNotConvertedByLssId(Integer lesStockSyncsId);

    /**
     * 获取比指定批次库存记录更新的记录数目
     * @param sku
     * @param scode
     * @param batchId
     * @return
     */
    Integer getNewerItemCount(@Param("sku") String sku, @Param("scode") String scode,
                              @Param("batchId") Integer batchId);

    /**
     * 新增LES库存变化明细
     * @param lesStockItem
     * @return
     */
    Integer insertItem(LesStockItem lesStockItem);

    /**
     * 批量新增LES库存变化明细
     * @param stockItems
     * @return
     */
    Integer batchInsertItem(@Param("stockItems") List<LesStockItem> stockItems);

    /**
     * 处理结束后，更新相关信息
     * @param lesStockItem
     * @return 影响行数
     */
    Integer updateAfterProcessed(LesStockItem lesStockItem);

    /**
     * 转换结束后，更新相关信息
     * @param lesStockItem
     * @return 影响行数
     */
    Integer updateAfterConverted(LesStockItem lesStockItem);
}
