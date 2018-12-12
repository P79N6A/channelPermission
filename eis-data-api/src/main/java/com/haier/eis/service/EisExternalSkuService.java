package com.haier.eis.service;


import com.haier.eis.model.EisExternalSku;

import java.util.List;

public interface EisExternalSkuService {

    List<EisExternalSku> getBySku(String sku);

    EisExternalSku getBySkuType(String sku,String type);

    EisExternalSku getByExternalSku(String externalSku);

    List<EisExternalSku> queryAllExternalSku();
}
