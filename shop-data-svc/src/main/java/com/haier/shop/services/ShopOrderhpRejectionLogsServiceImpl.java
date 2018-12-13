package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderhpRejectionLogsReadDao;
import com.haier.shop.dao.shopwrite.OrderhpRejectionLogsWriteDao;
import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.OrderhpRejectionLogsVO;
import com.haier.shop.service.ShopOrderhpRejectionLogsService;

@Service
public class ShopOrderhpRejectionLogsServiceImpl implements ShopOrderhpRejectionLogsService {
    @Autowired
    private OrderhpRejectionLogsReadDao orderhpRejectionLogsReadDao;
    @Autowired
    private OrderhpRejectionLogsWriteDao orderhpRejectionLogsWriteDao;


    public int insert(OrderhpRejectionLogs record){
        return orderhpRejectionLogsWriteDao.insert(record);
    }

    public int insertSelective(OrderhpRejectionLogs record){
        return orderhpRejectionLogsWriteDao.insertSelective(record);
    }

    public OrderhpRejectionLogs selectByPrimaryKey(Integer id){
        return orderhpRejectionLogsReadDao.selectByPrimaryKey(id);
    }
    
    public String quereHPRejection(String orderReprisSn,String hpLesId){
    	return orderhpRejectionLogsReadDao.quereHPRejection(orderReprisSn, hpLesId);
    }
    public int updateHpRejectionLogs(OrderhpRejectionLogs record){
		return orderhpRejectionLogsWriteDao.updateHpRejectionLogs(record);
    	
    }
    
    public List<OrderhpRejectionLogsVO> quereEmptyOutSAP(){
    	return orderhpRejectionLogsReadDao.quereEmptyOutSAP();
    }

	@Override
	public int updataEmptyOut(String id, String emptyOut) {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsWriteDao.updataEmptyOut(id, emptyOut);
	}

	@Override
	public List<OrderhpRejectionLogsVO> queryTheVirtualInto() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.queryTheVirtualInto();
	}

	@Override
	public String quereHpLesId(String channelOrderSn) {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.quereHpLesId(channelOrderSn);
	}

	@Override
	public List<OrderhpRejectionLogsVO> queryRealOutofData() {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.queryRealOutofData();
	}

	@Override
	public List<OrderhpRejectionLogs> queryVirtualEntryState(String channelOrderSn) {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsReadDao.queryVirtualEntryState(channelOrderSn);
	}
}
