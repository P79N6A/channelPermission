package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.HPQueues;
import com.haier.shop.model.SmsLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HpDispatchWriteDao {

    /**
     * 更新HPQueue信息
     * @return 更新条数
     */
    int updateHPQueue(Map<String, Object> params);

    /**
     * 更新OrderProduct信息
     * @return 更新条数
     */
    int updateOrderProductStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 更新全流程信息
     * @return 更新条数
     */
    int updateSyncHpTime(@Param("orderProductId") Integer orderProductId,
                         @Param("sendHpTime") Long sendHpTime);

    /**
     * 更新HPQueue信息(新版)
     * @return 更新条数
     */
    int update(HPQueues hPQueues);

    /**
     * 批量更新HPQueue信息(新版)
     * @return 更新条数
     */
    int updateHPQueueBatch(@Param("ids") String ids, @Param("lastMessage") String lastMessage);

}
