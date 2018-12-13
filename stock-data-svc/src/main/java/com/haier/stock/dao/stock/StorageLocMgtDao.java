package com.haier.stock.dao.stock;

import com.haier.stock.model.StorageLocMgt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StorageLocMgtDao {

    Integer countStorageLocMgtWithLike(StorageLocMgt storageLocMgt);

    List<StorageLocMgt> getInvWareHouseList(StorageLocMgt storageLocMgt);

    List<StorageLocMgt> queryStorageLocMgtExcel(StorageLocMgt storageLocMgt);

}
