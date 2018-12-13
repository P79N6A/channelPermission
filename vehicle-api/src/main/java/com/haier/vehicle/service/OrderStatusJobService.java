package com.haier.vehicle.service;

import com.haier.common.BusinessException;

public interface OrderStatusJobService {

	public void updateZqVbeln();
	
	public void updateZkVbeln();

	public void updateVbelnDnStatus();

	public void saleMaterielBill();

	public void cnPurchaseStorageToSap();

	public void saleBill() throws BusinessException;

	public void vehicleTest();

	public void pushLogistics();

	public void addLbx();
	
	public void gainMateriel();
	

}
