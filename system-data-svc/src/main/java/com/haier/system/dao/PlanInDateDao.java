package com.haier.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.system.model.PlanInDate;


public interface PlanInDateDao {
	
	List<PlanInDate> getPlanInDateList(@Param("start")Integer start, @Param("size")Integer size);
	
	PlanInDate getPlanInDateById(int id);
	
	int insert(PlanInDate planIndate);
	
	int update(@Param("entity")PlanInDate planIndate);

	int findPlanInDateCount();

	PlanInDate getPlanInDateByTypeId(int typeId);
}