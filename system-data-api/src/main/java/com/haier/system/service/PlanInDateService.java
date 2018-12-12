package com.haier.system.service;

import java.util.List;

import com.haier.system.model.PlanInDate;


public interface PlanInDateService {
	
	PlanInDate getPlanInDateById(int id);
	
	int insert(PlanInDate planIndate);
	
	int update(PlanInDate planIndate);

	int findPlanInDateCount();

	List<PlanInDate> getPlanInDateList(Integer start, Integer size);

	PlanInDate getPlanInDateByTypeId(int typeId);
}