package com.haier.order.services;
import com.haier.common.ServiceResult;
import com.haier.order.dao.OrderDao;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.model.OrderModel;
import com.haier.order.service.OrderService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.order.model.Order;


@Service
public class OrderServiceImpl implements OrderService {

    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao dao;

    @Autowired
    private OrderModel orderModel;

    @Override
    public Order findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public ServiceResult<String> unLockOrderGuaranteePricByOrderSns(List<String> orderSns,
        String operator, String responsiblePerson, String reason) {
        ServiceResult<String> result = new ServiceResult<String>();
        if (orderSns == null || orderSns.isEmpty()) {
            result.setSuccess(false);
            result.setMessage("解锁的订单号不能为空");
            StringBuffer sb = new StringBuffer();
            for (String sn : orderSns) {
                sb.append(sn).append(",");
            }
            log.error("解锁的订单号为空,orderSns:" + sb.toString());
            return result;
        }
        try {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            result.setResult(orderModel.unLockOrderGuaranteePricByOrderSns(orderSns, operator,
                responsiblePerson, reason));
            result.setSuccess(true);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setMessage("解锁失败，请联系管理员！");
            log.error("解锁订单失败：", ex);
        } finally {
            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
        }
        return result;
    }
}
