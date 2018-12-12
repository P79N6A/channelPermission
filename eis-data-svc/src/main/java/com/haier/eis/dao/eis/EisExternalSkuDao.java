package com.haier.eis.dao.eis;


import com.haier.eis.model.EisExternalSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EisExternalSkuDao {

    List<EisExternalSku> getBySku(@Param("sku") String sku);

    EisExternalSku getBySkuType(@Param("sku") String sku, @Param("type") String type);

    EisExternalSku getByExternalSku(@Param("externalSku") String externalSku);

    List<EisExternalSku> queryAllExternalSku();
}
