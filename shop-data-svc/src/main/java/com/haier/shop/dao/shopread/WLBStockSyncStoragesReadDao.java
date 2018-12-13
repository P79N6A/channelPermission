package com.haier.shop.dao.shopread;

import com.haier.shop.model.WlbstocksyncstoragesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/7/26 15:58
 */
@Mapper
public interface WLBStockSyncStoragesReadDao {

    List<WlbstocksyncstoragesVo> getStockSyncStorageList(Map<String,Object> params);

    int getRowCnts();
}
