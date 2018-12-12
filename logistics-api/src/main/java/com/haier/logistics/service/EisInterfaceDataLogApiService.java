package com.haier.logistics.service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.shop.model.OrderProductsNew;

public interface EisInterfaceDataLogApiService {

    public ServiceResult<Boolean> record(EisInterfaceDataLog log);
    public OrderProductsNew getOrderProductsByCOrderSn(String cOrderSn);
    public boolean saveHpReservationDateRelation(OrderProductsNew orderProducts, String remark,
                                                 String changeLog);
    public int sendSms(OrderProductsNew orderProducts);

    String getProperties(String name);
}
