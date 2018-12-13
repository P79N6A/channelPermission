package com.haier.purchase.data.dao.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.haier.purchase.data.model.vehcile.TmallCAMachineDTO;

/**
 * 天猫CA套机
 * @author zhangming
 *
 */
@Component
public interface PurchaseTmallCAMachineDAO {

	void save(TmallCAMachineDTO dto);
	
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
	 * 出现异常，将已标记为删除的部分恢复正常（deleted=1 => deleted=0）
	 */
	void restoreDeleted();

	/**
	 * 出现异常，将下载的部分删掉（delete where deleted=0），
	 */
	void deleteTmpData();

	List<TmallCAMachineDTO> getByMaterialCode(@Param("vbeln") String vbelnDn1);

}
