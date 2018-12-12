package com.haier.stock.dao.stock;

import com.haier.stock.model.InvBaseElectBussinessChannel;
import com.haier.stock.model.InvBaseSupplier;
import com.haier.stock.model.InvBaseSupplierForBaseSend;

import java.util.List;
import java.util.Map;


public interface InvBaseSupplierDao {

    /**
     * @param sale_org_id
     * @return
     */
    List<InvBaseSupplier> getSuppliperInfo(String sale_org_id);

    /**
     * @param params
     * @return
     */
    List<InvBaseSupplierForBaseSend> getSuppliperInfoForBaseSend(Map<String, Object> params);

    /**
     * @param params
     * @return
     */
    List<InvBaseElectBussinessChannel> getSAPChannCode(String cbs_channel_code);
}
