package com.haier.eis.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.VomwwwOutinstockAnalysisDao;
import com.haier.eis.model.VomwwwOutinstockAnalysis;
import com.haier.eis.service.EisVomwwwOutinstockAnalysisService;
@Service
public class EisVomwwwOutinstockAnalysisServiceImpl implements EisVomwwwOutinstockAnalysisService{
	@Autowired
	private VomwwwOutinstockAnalysisDao vomwwwOutinstockAnalysisDao;
	@Override
	public List<VomwwwOutinstockAnalysis> getInAnalysisByTradeNo(String tradeNo, String subTradeNo, int busType) {
		// TODO Auto-generated method stub
		return vomwwwOutinstockAnalysisDao.getInAnalysisByTradeNo(tradeNo, subTradeNo, busType);
	}

	@Override
	public Integer updateSapStatusById(VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis) {
		// TODO Auto-generated method stub
		return vomwwwOutinstockAnalysisDao.updateSapStatusById(vomwwwOutinstockAnalysis);
	}

	@Override
	public List<VomwwwOutinstockAnalysis> getByCondition(Map<String, Object> queryMap, int size) {
		// TODO Auto-generated method stub
		return vomwwwOutinstockAnalysisDao.getByCondition(queryMap, size);
	}

	@Override
	public VomwwwOutinstockAnalysis quereyVOMthNO(String tbNo) {
		// TODO Auto-generated method stub
		return vomwwwOutinstockAnalysisDao.quereyVOMthNO(tbNo);
	}

	@Override
	public VomwwwOutinstockAnalysis quereyBackNo(String backNo) {
		// TODO Auto-generated method stub
		return vomwwwOutinstockAnalysisDao.quereyBackNo(backNo);
	}

	@Override
	public List<VomwwwOutinstockAnalysis> outStockSap(String tbNo) {
		// TODO Auto-generated method stub
		return vomwwwOutinstockAnalysisDao.outStockSap(tbNo);
	}

}
