package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvChannel2OrderSourceDao;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.service.StockInvChannel2OrderSourceService;
/**
 * 吴坤洋
 * @author wukunyang
 *
 */
@Service
public class StockInvChannel2OrderSourceServiceImpl implements StockInvChannel2OrderSourceService{
	@Autowired
	private InvChannel2OrderSourceDao invChannel2OrderSourceDao;
	@Override
	public InvChannel2OrderSource getBySource(String source) {
		// TODO Auto-generated method stub
		return invChannel2OrderSourceDao.getBySource(source);
	}
    /**
     * 取得所有订单渠道数据
     * @return
     */
	@Override
	public List<InvChannel2OrderSource> getAllOrder2ChannelSource() {
		// TODO Auto-generated method stub
		return invChannel2OrderSourceDao.getAllOrder2ChannelSource();
	}

	@Override
	public InvChannel2OrderSource getSapChannelCodeAndCustomerCode(String ordeSourceCode) {
		// TODO Auto-generated method stub
		return invChannel2OrderSourceDao.getSapChannelCodeAndCustomerCode(ordeSourceCode);
	}

	@Override
	public List<Map<String, Object>> getChannelNames() {
		return invChannel2OrderSourceDao.getChannelNames();
	}


	/**
	 * 获取订单来源对应关系
	 */
	@Override
	public List<Map<String, String>> getInvChannel2OrderSource(String channelCode) {

			return invChannel2OrderSourceDao.getInvChannel2OrderSource(channelCode);

	}

	@Override
	public Integer queryInvChannel2OrderSourceListCount(String name) {
		return invChannel2OrderSourceDao.queryInvChannel2OrderSourceListCount(name);
	}

	@Override
	public List<InvChannel2OrderSource> queryInvChannel2OrderSourceList(String name, Integer start,
			Integer size, Integer id) {
		return invChannel2OrderSourceDao.queryInvChannel2OrderSourceList(name, start, size, id);
	}

	@Override
	public void insert(InvChannel2OrderSource tInvChannel2OrderSource) {
		invChannel2OrderSourceDao.insert(tInvChannel2OrderSource);
	}

	@Override
	public void update(InvChannel2OrderSource tInvChannel2OrderSource) {
		invChannel2OrderSourceDao.update(tInvChannel2OrderSource);
	}

	@Override
	public List<Map<String, String>> getInvStockChannel() {
		return invChannel2OrderSourceDao.getInvStockChannel();
	}

}
