package com.haier.purchase.data.service.vechile;

import java.util.List;

import com.haier.purchase.data.model.vehcile.VehicleProductPaymentDTO;

/**
 * <p>Description: </p>
 * ClassName:VehicleProductPaymentDao
 * Created on 2017/9/8
 *
 * @author wsh
 * @version 1.0
 * Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
public interface PurchaseVehicleProductPaymentService extends BasService<VehicleProductPaymentDTO> {
    List<VehicleProductPaymentDTO> getList();

    List<VehicleProductPaymentDTO> listByCondition(VehicleProductPaymentDTO entity);
}
