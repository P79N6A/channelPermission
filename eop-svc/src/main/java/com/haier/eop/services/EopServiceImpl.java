package com.haier.eop.services;
import com.haier.eop.dao.EopDao;
import com.haier.eop.service.EopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.eop.model.Eop;


@Service
public class EopServiceImpl implements EopService {
    @Autowired
    private EopDao dao;

    @Override
    public Eop findById(Long id) {
        return dao.findById(id);
    }
}
