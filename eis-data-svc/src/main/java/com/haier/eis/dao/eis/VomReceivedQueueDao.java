package com.haier.eis.dao.eis;


import com.haier.eis.model.VomReceivedQueue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 吴坤洋 2017-12-22
 */
public interface VomReceivedQueueDao {

    Integer insert(VomReceivedQueue receivedQueue);

    List<VomReceivedQueue> getByStatus(@Param("status") Integer status);

    Integer outCode(@Param("out_code") String outCode);

    Integer updateDone(@Param("id") Integer id);

    Integer updateError(@Param("id") Integer id, @Param("errorMessage") String msg);

    List<VomReceivedQueue> getByIdStatus(@Param("id") Integer id, @Param("status") Integer status);

    Integer updateStatusById(@Param("id") Integer id, @Param("status") Integer status);
}
