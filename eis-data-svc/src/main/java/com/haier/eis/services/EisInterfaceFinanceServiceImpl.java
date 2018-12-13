package com.haier.eis.services;

import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.EisInterfaceFinanceDao;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.service.EisInterfaceFinanceService;
@Service
public class EisInterfaceFinanceServiceImpl implements EisInterfaceFinanceService{
	@Autowired
	private EisInterfaceFinanceDao interfaceFinanceDao;
	@Override
	public EisInterfaceFinance getByTransQueueId(Integer transQueueId) {
		// TODO Auto-generated method stub
		return interfaceFinanceDao.getByTransQueueId(transQueueId);
	}

	@Override
	public List<EisInterfaceFinance> getByStatus(Integer status) {
		// TODO Auto-generated method stub
		return interfaceFinanceDao.getByStatus(status);
	}

	@Override
	public List<EisInterfaceFinance> getByParams(Map<String,Object> params) {
		// TODO Auto-generated method stub
		return interfaceFinanceDao.getByParams(params);
	}

	@Override
	public Integer insert(EisInterfaceFinance interfaceFinance) {
		// TODO Auto-generated method stub
		return interfaceFinanceDao.insert(interfaceFinance);
	}

	@Override
	public Integer update(EisInterfaceFinance interfaceFinance) {
		// TODO Auto-generated method stub
		return interfaceFinanceDao.update(interfaceFinance);
	}
	@Override
	public List<Integer> getIdsByOrderSn(String cOrderSn) {
		// TODO Auto-generated method stub
		return interfaceFinanceDao.getIdsByOrderSn(cOrderSn);
	}
	@Override
	public Integer updateEisInterfaceFinance(List<Integer> les_stock_trans_queue_ids) {
		// TODO Auto-generated method stub
		return interfaceFinanceDao.updateEisInterfaceFinance(les_stock_trans_queue_ids);
	}

}
