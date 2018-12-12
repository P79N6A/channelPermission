package com.haier.eis.service;




import com.haier.eis.model.VomReceivedQueue;

import java.util.List;

/**
 * Created by 吴坤洋 2017-12-22
 */
public interface EisVomReceivedQueueService {

    Integer insert(VomReceivedQueue receivedQueue);

    List<VomReceivedQueue> getByStatus(Integer status);

    Integer outCode( String outCode);

    Integer updateDone( Integer id);

    Integer updateError(Integer id, String msg);

    List<VomReceivedQueue> getByIdStatus(Integer id, Integer status);

    Integer updateStatusById(Integer id,  Integer status);
}
