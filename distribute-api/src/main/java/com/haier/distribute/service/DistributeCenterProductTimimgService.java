package com.haier.distribute.service;

import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;

/**
 * Created by 付振兴 on 2018/3/23 1117.
 */
@Service
public interface DistributeCenterProductTimimgService {
	ServiceResult<Boolean> pushProduct();
	ServiceResult<Boolean> pushPrice();
	ServiceResult<Boolean> pushAvailable();
}
