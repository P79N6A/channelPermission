package com.haier.vehicle.service;

import javax.jws.WebService;

import com.haier.common.ServiceResult;
import com.haier.vehicle.model.InsertOrderToOMSItem;

/**
 * <p>
 * Description: [下單]
 * </p>
 * Created on 2017年09月05日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface VehicleInsertOrderToOMSService {
     ServiceResult<String> sendOrderToOms(InsertOrderToOMSItem item);
}
