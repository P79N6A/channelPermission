package com.haier.purchase.data.service;

import java.util.List;

import com.haier.purchase.data.model.vehcile.TmallCAMachineDTO;

public interface PurchaseTmallCAMachineService {

	void save(List<TmallCAMachineDTO> list);
	
	void deleteAll();
	
	/**
	 * 删除所有已标记为删除的数据（deleted=1）
	 */
	void deleteAllMarked();
	/**
	 * 添加删除标记（设置deleted=1）
	 */
	void updateDeleteMark();

	/**
	 * 出现异常，将下载的部分删掉（delete where deleted=0），将已标记为删除的部分恢复正常（deleted=1 => deleted=0）
	 */
	void restoreDeleted();

	List<TmallCAMachineDTO> getByMaterialCode(String vbelnDn1);

}
