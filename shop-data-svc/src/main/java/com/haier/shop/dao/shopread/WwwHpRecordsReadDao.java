package com.haier.shop.dao.shopread;

import com.haier.shop.model.WwwHpRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WwwHpRecordsReadDao {

    WwwHpRecords get(Integer id);

    /**
     * 通过vomRepairSn、sku查询"hp拒收推送质检"(orderRepairId) 退货单id
     * @param vomRepairSn
     * @param sku
     * @return
     */
    WwwHpRecords getByVomRepairSnAndSku(@Param("vomRepairSn") String vomRepairSn,
                                        @Param("sku") String sku);
    
    String queryTHRepairSn(String orderRepairId);//根据退货单号查询TH号

}