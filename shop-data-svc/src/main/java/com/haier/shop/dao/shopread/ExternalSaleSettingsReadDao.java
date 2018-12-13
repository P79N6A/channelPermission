package com.haier.shop.dao.shopread;

import com.haier.shop.model.ExternalSaleSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/12 17:26
 */
@Mapper
public interface ExternalSaleSettingsReadDao {
    List<ExternalSaleSettings> Listf(@Param("entity")ExternalSaleSettings entity, @Param("start") int start, @Param("rows") int rows);
    int getPagerCountS(@Param("entity")ExternalSaleSettings entity);
    ExternalSaleSettings findByWhere(String externalSkus);
}
