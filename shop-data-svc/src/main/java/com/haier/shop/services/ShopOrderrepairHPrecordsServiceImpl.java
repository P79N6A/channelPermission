package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderrepairHPrecordsReadDao;
import com.haier.shop.dao.shopwrite.OrderrepairHPrecordsWriteDao;
import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.model.OrderrepairHPrecordsVO;
import com.haier.shop.service.ShopOrderrepairHPrecordsService;

@Service
public class ShopOrderrepairHPrecordsServiceImpl implements ShopOrderrepairHPrecordsService {

    @Autowired
    private OrderrepairHPrecordsWriteDao orderrepairHPrecordsWriteDao;
    @Autowired
    private OrderrepairHPrecordsReadDao orderrepairHPrecordsReadDao;

    public OrderrepairHPrecords selectByHpreCordsId(String id){
        return orderrepairHPrecordsReadDao.selectByHpreCordsId(id);
    }

    public int insert(OrderrepairHPrecords orderrepairHPrecords){
        return orderrepairHPrecordsWriteDao.insert(orderrepairHPrecords);
    }
    public List<OrderrepairHPrecordsVO> queryRejectsPropelling(){
    	return orderrepairHPrecordsReadDao.queryRejectsPropelling();
    }
	@Override
	public List<OrderrepairHPrecords> queryHPRecords() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.queryHPRecords();
	}

	@Override
	public List<OrderrepairHPrecordsVO> queryPushSAP() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.queryPushSAP();
	}

	@Override
	public int updataRepaiHp(OrderrepairHPrecords orderrepairHPrecords) {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsWriteDao.updataRepaiHp(orderrepairHPrecords);
	}

	@Override
	public List<OrderrepairHPrecordsVO> queryTbOrederSn() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.queryTbOrederSn();
	}

	@Override
	public List<OrderrepairHPrecords> queryGenerateOutOfStorage() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.queryGenerateOutOfStorage();
	}
    
	@Override
	public int updataOutOfStorage(String OutOfStorageFlag, String id) {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsWriteDao.updataOutOfStorage(OutOfStorageFlag, id);
	}

	@Override
	public List<OrderrepairHPrecordsVO> queryThreeAppraisal() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.queryThreeAppraisal();
	}
    
}
