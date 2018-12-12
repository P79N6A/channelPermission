package com.haier.stock.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvParamDao;
import com.haier.stock.model.InvParam;
import com.haier.stock.service.InvParamService;
@Service
public class InvParamServiceImpl implements InvParamService{
	@Autowired
	private InvParamDao invParamDao;
	@Override
	public List<InvParam> queryParams(String group) {
		// TODO Auto-generated method stub
		return invParamDao.queryParams(group);
	}

}
