package com.haier.eis.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.LesStockTransQueueDao;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.LesStockTransQueueService;
@Service
public class LesStockTransQueueServiceImpl implements LesStockTransQueueService {

	@Autowired
	LesStockTransQueueDao lesStockTransQueueDao;
	@Override
	public LesStockTransQueue getByLesBillNo(String billNo) {
		return lesStockTransQueueDao.getByLesBillNo(billNo);
	}

	@Override
	public Integer insert(LesStockTransQueue stockTransQueues) {
		return lesStockTransQueueDao.insert(stockTransQueues);
	}

	@Override
	public List<LesStockTransQueue> getLesStockTransQueueByCorderSn(String corderSn) {
		return lesStockTransQueueDao.getLesStockTransQueueByCorderSn(corderSn);
	}

	@Override
	public LesStockTransQueue queryCorderSn(String orderSn,String mark,String charg) {
		return lesStockTransQueueDao.queryCorderSn(orderSn, mark, charg);
	}

	@Override
	public List<LesStockTransQueue> getForFinance(){
		return lesStockTransQueueDao.getForFinance();
	}

	@Override
	public List<LesStockTransQueue> getForFinanceByParams(Map<String,Object> params){
		return lesStockTransQueueDao.getForFinanceByParams(params);
	}

	@Override
  public Integer updateAfterDoFinance(Integer id){
    return lesStockTransQueueDao.updateAfterDoFinance(id);
  }

  @Override
	public LesStockTransQueue getById(Integer id){
  	return lesStockTransQueueDao.getById(id);
	}

	@Override
	public List<Map<String, Object>> getDbStatusUpdated(List<Map<String, Object>> list) {
		List<String> lineNumList = new ArrayList<>();
		//将订单号放到list里进行查询
		for(Map<String, Object> map : list){
			lineNumList.add(map.get("lineNum").toString());
		}
		//查出相关订单
		List<LesStockTransQueue> result = lesStockTransQueueDao.getByLineNums(lineNumList);
		//把查出的订单状态放入list并返回
		if(result != null && !result.isEmpty()){
			for(LesStockTransQueue q : result){
				String orderNo = q.getCorderSn();
				for(Map<String, Object> map : list){
					if(orderNo.equals(map.get("lineNum").toString())){
						//可能有出库(ZGI6)、入库(ZGR6)两条记录，保留ZGI6状态。
						//上面的lesStockTransQueueDao.getByLineNums(lineNumList)已经做好了排序，ZGI6会排在ZGR6后面
						map.put("newStatus", q.getBillType());
						if(q.getBillTime() != null){
							map.put("billTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(q.getBillTime()));
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public LesStockTransQueue findInStockCode(String corderSn) {
		return lesStockTransQueueDao.findInStockCode(corderSn);
	}

	@Override
	public List<LesStockTransQueue> getLesStockTransQueueByCorderSnBillType(String lineNum) {
		return lesStockTransQueueDao.getLesStockTransQueueByCorderSnBillType(lineNum);
	}

	@Override
	public ServiceResult<List<LesStockTransQueue>> getPushSapResult(String startTime, String endTime,
			String corderSn, String status, String billType, PagerInfo pagerInfo) {
		ServiceResult<List<LesStockTransQueue>> result = new ServiceResult<>();
		int rowsCount = lesStockTransQueueDao.getPushSapResultCount(startTime, endTime,
				corderSn, status, billType);
		List<LesStockTransQueue> list = lesStockTransQueueDao.getPushSapResult(startTime, endTime,
				corderSn, status, billType, pagerInfo);
		result.setResult(list);
		if (pagerInfo != null) {
			pagerInfo.setRowsCount(rowsCount);
			result.setPager(pagerInfo);
		}
		return result;
	}

}
