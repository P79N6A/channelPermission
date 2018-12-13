package com.haier.purchase.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.PurchaseTmallCAMachineDAO;
import com.haier.purchase.data.model.vehcile.TmallCAMachineDTO;
import com.haier.purchase.data.service.PurchaseTmallCAMachineService;

/**
 * 天猫CA套机
 * @author zhangming
 *
 */
@Service
public class PurchaseTmallCAMachineServiceImpl implements PurchaseTmallCAMachineService {

	@Autowired
	private PurchaseTmallCAMachineDAO dao;

	@Override
	public void save(List<TmallCAMachineDTO> list) {
		for(TmallCAMachineDTO dto: list){
			dao.save(dto);
		}
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
	}

	@Override
	public void deleteAllMarked() {
		dao.deleteAllMarked();
	}

	@Override
	public void updateDeleteMark() {
		dao.updateDeleteMark();
	}

	@Override
	public void restoreDeleted() {
		dao.deleteTmpData();
		dao.restoreDeleted();
	}

	@Override
	public List<TmallCAMachineDTO> getByMaterialCode(String vbelnDn1) {
		return dao.getByMaterialCode(vbelnDn1);
	}
}
