package com.haier.purchase.data.service.vechile;



import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;

/**
 * <p>Description: </p>
 * ClassName:VehicleOrderDao
 * Created on 2017/9/6
 *
 * @author wsh
 * @version 1.0
 * Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
public interface PurchaseVehicleOrderService extends BasService<VehicleOrderDTO> {

    String getVehicleOrderNo(String begin);

    int updateSelectiveByOrderNo(VehicleOrderDTO entity);
    
    List<ExportVehicleDTO> selectVehicleExport(Map<String, Object> params);

    String getWhCode (String code);
    
 

}
