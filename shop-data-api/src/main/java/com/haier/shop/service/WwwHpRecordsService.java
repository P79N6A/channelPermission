package com.haier.shop.service;

import com.haier.shop.model.WwwHpRecords;

public interface WwwHpRecordsService {

    WwwHpRecords get(Integer id);

    int insert(WwwHpRecords wwwHpRecords);

    int update(WwwHpRecords wwwHpRecords);

    /**
     * 通过vomRepairSn、sku查询"hp拒收推送质检"(orderRepairId) 退货单id
     * @param vomRepairSn
     * @param sku
     * @return
     */
    WwwHpRecords getByVomRepairSnAndSku( String vomRepairSn,
                                         String sku);
    String queryTHRepairSn(String orderRepairId);//根据退货单号查询TH号
}