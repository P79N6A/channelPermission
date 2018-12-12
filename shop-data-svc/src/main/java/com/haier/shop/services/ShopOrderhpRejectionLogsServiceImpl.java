package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderhpRejectionLogsReadDao;
import com.haier.shop.dao.shopwrite.OrderhpRejectionLogsWriteDao;
import com.haier.shop.model.OrderhpRejectionLogs;
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
    
    public String quereHPRejection(String id){
    	return orderhpRejectionLogsReadDao.quereHPRejection(id);
    }
    public int updateHpRejectionLogs(OrderhpRejectionLogs record){
		return orderhpRejectionLogsWriteDao.updateHpRejectionLogs(record);
    	
    }
    
    public List<OrderhpRejectionLogs> quereEmptyOutSAP(){
    	return orderhpRejectionLogsReadDao.quereEmptyOutSAP();
    }

	@Override
	public int updataEmptyOut(String id, String emptyOut) {
		// TODO Auto-generated method stub
		return orderhpRejectionLogsWriteDao.updataEmptyOut(id, emptyOut);
	}
}
