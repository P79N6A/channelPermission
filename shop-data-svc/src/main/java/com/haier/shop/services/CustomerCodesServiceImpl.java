package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.CustomerCodesReadDao;
import com.haier.shop.model.CustomerCodes;
import com.haier.shop.service.CustomerCodesService;

@Service
public class CustomerCodesServiceImpl implements CustomerCodesService {

    @Autowired
    private CustomerCodesReadDao customerCodesReadDao;
    
    @Override
    public List<String> getCustomerCode(String title) {
        return customerCodesReadDao.getCustomerCode(title);
    }

	@Override
	public CustomerCodes getByTitle(String title) {
		return customerCodesReadDao.getByTitle(title);
	}
}
