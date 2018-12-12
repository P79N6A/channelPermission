package com.haier.shop.dao.shopread;

import com.haier.shop.model.MsLinkage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MsLinkageReadDao {

    MsLinkage getMsLinkage(@Param("sku") String sku, @Param("orderSource") String orderSource);

}
