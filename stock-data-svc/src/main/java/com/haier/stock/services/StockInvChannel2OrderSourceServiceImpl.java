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

}
