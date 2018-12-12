package com.haier.dbconfig.services;
import com.haier.dbconfig.dao.DbconfigDataDao;
import com.haier.dbconfig.service.DbconfigDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.dbconfig.model.DbconfigData;


@Service
public class DbconfigDataServiceImpl implements DbconfigDataService {
    @Autowired
    private DbconfigDataDao dao;

    @Override
    public DbconfigData findById(Long id) {
        return dao.findById(id);
    }
}
