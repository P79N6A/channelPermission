package com.haier.eop.dao;

import org.springframework.stereotype.Repository;
import com.haier.eop.model.Eop;



@Repository
public class EopDao  {

   public Eop findById(Long id) {
        return new Eop();
   }
}
