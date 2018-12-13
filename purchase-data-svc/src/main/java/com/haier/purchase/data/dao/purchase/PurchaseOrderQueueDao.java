package com.haier.purchase.data.dao.purchase;

import com.haier.common.PagerInfo;
import com.haier.purchase.data.model.PurchaseOrderQueue;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurchaseOrderQueueDao {

    Integer insert(PurchaseOrderQueue purchaseOrderQueue);

    Integer getRowCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
        @Param("status") int status);

    List<PurchaseOrderQueue> findPurchaseOrderQueueByCondition(@Param("startTime") Date startTime,
        @Param("endTime") Date endTime,
        @Param("status") int status,
        @Param("pager") PagerInfo pager);

    PurchaseOrderQueue getByPoItemId(int poItemId);

    List<PurchaseOrderQueue> findPurchaseOrderQueue();

    PurchaseOrderQueue get(int id);

    Integer updatePurchaseOrderQueueStatus(PurchaseOrderQueue purchaseOrderQueue);
}
