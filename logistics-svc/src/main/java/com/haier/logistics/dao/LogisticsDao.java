package com.haier.logistics.dao;

import org.springframework.stereotype.Repository;
import com.haier.logistics.model.Logistics;



@Repository
public class LogisticsDao  {

   public Logistics findById(Long id) {
        return new Logistics();
   }
}
