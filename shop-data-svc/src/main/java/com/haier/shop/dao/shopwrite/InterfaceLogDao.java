package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InterfaceLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @Title: InterfaceLogDao.java
 * @Package com.haier.shop.dao.shopwrite
 * @Description: 操作日志Dao
 * @author layne
 * @date 2018年7月5日 下午2:31:03
 * @version V1.0
 */
@Mapper
public interface InterfaceLogDao {

	int insert(InterfaceLog log);
}
