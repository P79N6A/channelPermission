package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.StoragesRela;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoragesRelaWriteDao {

  /**
   * 创建大家电多层级列表
   * @param storagesRela
   * @return
   */
  int createStoragesRela(StoragesRela storagesRela);

  /**
   * 更新大家电多层级列表
   * @param storagesRela
   * @return
   */
  int updateStoragesRela(StoragesRela storagesRela);

}
