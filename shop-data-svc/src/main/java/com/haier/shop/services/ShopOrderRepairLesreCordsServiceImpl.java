package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderRepairLESRecordsReadDao;
import com.haier.shop.dao.shopwrite.OrderRepairLESRecordsWriteDao;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.service.ShopOrderRepairLesreCordsService;
@Service
public class ShopOrderRepairLesreCordsServiceImpl implements ShopOrderRepairLesreCordsService {
    @Autowired
    private OrderRepairLESRecordsReadDao orderRepairLESRecordsReadDao;

    @Autowired
    private OrderRepairLESRecordsWriteDao orderRepairLESRecordsWriteDao;

    public List<OrderRepairLESRecords> queryLesreCodrdsId(String id){
        return orderRepairLESRecordsReadDao.queryLesreCodrdsId(id);
    }
    public int insert(OrderRepairLESRecords cords){
        return orderRepairLESRecordsWriteDao.insert(cords);
    }
   public  List<OrderRepairLESRecords> queryOutofStorage(){
	   return orderRepairLESRecordsReadDao.queryOutofStorage();
    }
	@Override
	public int updataRecords(OrderRepairLESRecords orderRepairLESRecords) {
		// TODO Auto-generated method stub
		return orderRepairLESRecordsWriteDao.updataRecords(orderRepairLESRecords);
	}
	@Override
	public List<OrderRepairLESRecords> queryRecordSn(String orderRepairId) {
		// TODO Auto-generated method stub
		return orderRepairLESRecordsReadDao.queryRecordSn(orderRepairId);
	}
	
	
}
