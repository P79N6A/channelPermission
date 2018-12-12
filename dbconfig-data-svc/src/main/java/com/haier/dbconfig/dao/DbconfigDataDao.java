package com.haier.dbconfig.dao;

import org.springframework.stereotype.Repository;
import com.haier.dbconfig.model.DbconfigData;



@Repository
public class DbconfigDataDao  {

   public DbconfigData findById(Long id) {
        return new DbconfigData();
   }
}
