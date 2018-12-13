package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.Wlbstocksyncstorages;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:JinXueqian
 * @Date: 2018/7/26 19:41
 */
@Mapper
public interface WLBStockSyncStoragesWriteDao {

    int deleteByPrimaryKey(Integer id);

    int insert(Wlbstocksyncstorages wlbstocksyncstorages);

}
