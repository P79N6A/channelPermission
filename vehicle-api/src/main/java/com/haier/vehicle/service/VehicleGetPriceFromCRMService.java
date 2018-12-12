package com.haier.vehicle.service;

import java.util.Map;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月12日}
 * map.put("custCode客户编码","8800044838");
 * map.put("regionCode地区编码","QD001");
 * map.put("invCode产品编码","CA0JN0B06");
 * map.put("corpCode日日顺公司编码","2130");
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface VehicleGetPriceFromCRMService {
    public String getPriceFromCrm(Map<String, String> map);
}
