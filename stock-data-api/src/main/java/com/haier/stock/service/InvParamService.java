package com.haier.stock.service;

import java.util.List;

import com.haier.stock.model.InvParam;

public interface InvParamService {
	List<InvParam> queryParams(String group);
}
