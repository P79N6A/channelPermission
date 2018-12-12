package com.haier.shop.service;

import com.haier.shop.model.ChangeOrderConfig;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 李超 on 2018/1/10.
 */
public interface ChangeOrderConfigService {

    /**
     * 添加转单关系
     * @param changeOrderConfig
     * @return
     */
    int insert(ChangeOrderConfig changeOrderConfig);

    /**
     * 更新转单关系
     * @param changeOrderConfig
     * @return
     */
    int update(ChangeOrderConfig changeOrderConfig);

    /**
     * 根据主键查询转单关系
     * @param id
     * @return
     */
    ChangeOrderConfig get(@Param("id") Integer id);

    /**
     * 根据订单来源，区域，品类，品牌查询唯一转单关系
     * @return
     */
    ChangeOrderConfig getBySourceAndBrandAndCateAndregion(@Param("orderSourceCode") String orderSourceCode, @Param("regionId") Integer regionId, @Param("brandId") Integer brandId, @Param("cateId") Integer cateId);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(@Param("id") Integer id);

}
