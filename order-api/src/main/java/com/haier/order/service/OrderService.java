package com.haier.order.service;
import com.haier.common.ServiceResult;
import com.haier.order.model.Order;
import java.util.List;

public interface OrderService {
     Order findById(Long id);

     /**
      * 根据订单号，解锁保本价闸住的订单
      * @param orderSns 订单号
      * @param operator 操作人
      * @param responsiblePerson 责任人
      * @param reason 放行理由
      * @return
      */
     ServiceResult<String> unLockOrderGuaranteePricByOrderSns(List<String> orderSns,
         String operator,
         String responsiblePerson, String reason);
}
