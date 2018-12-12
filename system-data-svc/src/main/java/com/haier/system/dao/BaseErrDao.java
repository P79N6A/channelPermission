package com.haier.system.dao;


import com.haier.system.model.BaseErr;

import java.util.List;
import java.util.Map;

public interface BaseErrDao {
	
	Integer adderr(BaseErr base);
	
	List<BaseErr> queryErr(Map<String, Object> map);
	
	int getRowCnts(Map<String, Object> map);
	
}
