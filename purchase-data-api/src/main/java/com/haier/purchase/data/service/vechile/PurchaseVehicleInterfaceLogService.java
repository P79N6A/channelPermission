package com.haier.purchase.data.service.vechile;

import com.haier.purchase.data.model.vehcile.InterfaceLog;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月13日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface PurchaseVehicleInterfaceLogService {

    public int insertLog(String interfaceName,String interfaceMessage);

    List<InterfaceLog> findInterfaceLog(Map<String,Object> map);

    int getInterfaceLogRow(Map<String, Object> map);
}
