package com.haier.shop.dao.shopread;

import com.haier.shop.model.SysMdmVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/11 17:48
 */
@Mapper
public interface MdmDataReadDao {
    public SysMdmVO queryBySku(@Param("sku") String sku);
}
