package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.ChangeOrderConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 转单关系配置表dao
 * @author wangp-c
 *
 */
@Mapper
public interface ChangeOrderConfigWriteDao {

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
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(@Param("id") Integer id);


}
