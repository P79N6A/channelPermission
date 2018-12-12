package com.haier.eop.data.services;
import com.haier.eop.data.dao.EopDataDao;
import com.haier.eop.data.service.EopDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.eop.data.model.EopData;


@Service
public class EopDataServiceImpl implements EopDataService {
    @Autowired
    private EopDataDao dao;

    @Override
    public EopData findById(Long id) {
        return dao.findById(id);
    }
}
