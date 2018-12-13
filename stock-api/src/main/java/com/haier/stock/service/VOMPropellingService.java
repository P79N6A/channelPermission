package com.haier.stock.service;

public interface VOMPropellingService {

	void callVOMProprlling();

	public Integer modifyLesOrder(String id, String recordCn);

	public void stateOfgoods();

	public void updateRepairOrderLesAndOutPing();

	public void callVOMProprllingONline(String id);
}
