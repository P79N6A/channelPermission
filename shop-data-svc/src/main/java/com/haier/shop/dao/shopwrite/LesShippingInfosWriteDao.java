package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.LesShippingInfos;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LesShippingInfosWriteDao {

    public Integer insert(LesShippingInfos lesShippingInfos);
}
