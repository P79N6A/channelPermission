package com.haier.eop.data.dao;

import org.springframework.stereotype.Repository;
import com.haier.eop.data.model.EopData;



@Repository
public class EopDataDao  {

   public EopData findById(Long id) {
        return new EopData();
   }
}
