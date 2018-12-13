package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.ExternalSaleSettings;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/12 17:30
 */
@Mapper
public interface ExternalSaleSettingsWriteDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ExternalSaleSettings record);

    int updateByPrimaryKeySelective(ExternalSaleSettings record);
}
