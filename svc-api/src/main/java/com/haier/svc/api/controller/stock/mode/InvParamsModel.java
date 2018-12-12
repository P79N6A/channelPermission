package com.haier.svc.api.controller.stock.mode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.model.InvParam;
import com.haier.stock.service.InvParamService;
@Service
public class InvParamsModel {
		@Autowired
	    private InvParamService invParamService;

	    public List<InvParam> queryParams(String group) {
	        return invParamService.queryParams(group);
	    }
}
