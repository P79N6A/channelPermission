package com.haier.shop.dao.shopread;

import com.haier.shop.model.LesShippingInfos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LesShippingInfosRradDao {
    public List<LesShippingInfos> getByCorderSn(@Param("corderSn") String corderSn);

}
