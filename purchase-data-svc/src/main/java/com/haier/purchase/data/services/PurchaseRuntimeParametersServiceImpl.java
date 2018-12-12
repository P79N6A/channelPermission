package com.haier.purchase.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.RuntimeParametersDAO;
import com.haier.purchase.data.model.RuntimeParametersVO;
import com.haier.purchase.data.service.PurchaseRuntimeParametersService;

@Service
public class PurchaseRuntimeParametersServiceImpl implements PurchaseRuntimeParametersService{

	@Autowired
	RuntimeParametersDAO runtimeParametersDAO;
	
	@Override
	public RuntimeParametersVO getRuntimeParameterByKey(String key){
		return runtimeParametersDAO.getRuntimeParameterByKey(key);
	}
	@Override
	public List<RuntimeParametersVO> getRuntimeParameters(){
		return runtimeParametersDAO.getRuntimeParameters();
	}
	@Override
	public void insertRuntimeParameters(RuntimeParametersVO vo){
		runtimeParametersDAO.insertRuntimeParameters(vo);
	}
	@Override
	public void updateRuntimeParameters(RuntimeParametersVO vo){
		runtimeParametersDAO.updateRuntimeParameters(vo);
	}
}
