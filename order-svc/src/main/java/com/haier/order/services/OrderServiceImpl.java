package com.haier.order.services;
import com.haier.order.dao.OrderDao;
import com.haier.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.order.model.Order;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao dao;

    @Override
    public Order findById(Long id) {
        return dao.findById(id);
    }
}
