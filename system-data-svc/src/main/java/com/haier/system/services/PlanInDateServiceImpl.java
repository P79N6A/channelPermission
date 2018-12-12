package com.haier.system.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.system.dao.PlanInDateDao;
import com.haier.system.model.PlanInDate;
import com.haier.system.service.PlanInDateService;

@Service
public class PlanInDateServiceImpl implements PlanInDateService {
	
	@Autowired
	PlanInDateDao planInDateDao;

	public PlanInDate getPlanInDateById(int id) {
		return planInDateDao.getPlanInDateById(id);
	}

	public int insert(PlanInDate planIndate) {
		return planInDateDao.insert(planIndate);
	}

	public int update(PlanInDate planIndate) {
		return planInDateDao.update(planIndate);
	}

	@Override
	public int findPlanInDateCount() {
		return planInDateDao.findPlanInDateCount();
	}

	@Override
	public List<PlanInDate> getPlanInDateList(Integer start, Integer size) {
		return planInDateDao.getPlanInDateList(start,size);
	}

	@Override
	public PlanInDate getPlanInDateByTypeId(int typeId) {
		return planInDateDao.getPlanInDateByTypeId(typeId);
	}
}