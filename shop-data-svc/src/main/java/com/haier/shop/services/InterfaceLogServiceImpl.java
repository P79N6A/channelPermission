package com.haier.shop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.InterfaceLogDao;
import com.haier.shop.model.InterfaceLog;
import com.haier.shop.service.InterfaceLogService;

/**
 * @Title: InterfaceLogServiceImpl.java 
 * @Package com.haier.shop.services 
 * @Description: 操作日志Service 
 * @author layne   
 * @date 2018年7月5日 下午3:00:18 
 * @version V1.0
 */
@Service
public class InterfaceLogServiceImpl implements InterfaceLogService {

    @Autowired
    InterfaceLogDao interfaceLogDao;

	@Override
	public int insert(InterfaceLog log) {
		return interfaceLogDao.insert(log);
	}
}