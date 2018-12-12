package com.haier.stock.dao;

import org.springframework.stereotype.Repository;
import com.haier.stock.model.Stock;



@Repository
public class StockDao  {

   public Stock findById(Long id) {
        return new Stock();
   }
}
