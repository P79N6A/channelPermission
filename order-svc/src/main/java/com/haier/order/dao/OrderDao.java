package com.haier.order.dao;

import org.springframework.stereotype.Repository;
import com.haier.order.model.Order;



@Repository
public class OrderDao  {

   public Order findById(Long id) {
        return new Order();
   }
}
