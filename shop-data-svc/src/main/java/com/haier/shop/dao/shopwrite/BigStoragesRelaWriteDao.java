package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.OrderPriceSourceChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BigStoragesRelaWriteDao {

  /**
   * 创建大家电多层级列表
   * @param bigStoragesRela
   * @return
   */
  int createBigStoragesRela(BigStoragesRela bigStoragesRela);

  /**
   * 更新大家电多层级列表
   * @param bigStoragesRela
   * @return
   */
  int updateBigStoragesRela(BigStoragesRela bigStoragesRela);

}
