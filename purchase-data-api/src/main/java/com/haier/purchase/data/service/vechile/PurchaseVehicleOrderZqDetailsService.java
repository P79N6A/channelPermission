package com.haier.purchase.data.service.vechile;

import java.util.List;

import com.haier.purchase.data.model.vehcile.VehicleOrderZqDetailsDTO;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月15日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface PurchaseVehicleOrderZqDetailsService extends BasService<VehicleOrderZqDetailsDTO> {
    List<VehicleOrderZqDetailsDTO> listByCondition(VehicleOrderZqDetailsDTO entity);

    int updateSelectiveByZqItemNo(VehicleOrderZqDetailsDTO entity);
    
    int updateSelectiveByZqOrderNo(VehicleOrderZqDetailsDTO entity);

    int updateStatusDetail(String orderNo);

    int updateMessageDetail(String orderNo, String mesageg);

	List<VehicleOrderZqDetailsDTO> selectByStatus();

	List<VehicleOrderZqDetailsDTO> getListByOrderNo(String orderNo);
}
