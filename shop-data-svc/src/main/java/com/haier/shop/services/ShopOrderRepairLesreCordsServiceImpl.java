package com.haier.shop.services;

import java.util.List;
import java.util.Map;

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
        orderRepairLESRecordsWriteDao.insert(cords);
        return cords.getId();
    }
   public  List<OrderRepairLESRecords> queryOutofStorage(){
	   return orderRepairLESRecordsReadDao.queryOutofStorage();
    }

    public  List<OrderRepairLESRecords> queryOutofStorageByRepairid(String orderRepairsId){
        return orderRepairLESRecordsReadDao.queryOutofStorageByRepairid(orderRepairsId);
    }

	@Override
	public int updataRecords(OrderRepairLESRecords orderRepairLESRecords) {
		// TODO Auto-generated method stub
		return orderRepairLESRecordsWriteDao.updataRecords(orderRepairLESRecords);
	}

    public int updateRepairLesRecordcnSuccess(String id, Integer success) {
        return orderRepairLESRecordsWriteDao.updateRepairLesRecordcnSuccess(id, success);
    }
	@Override
	public List<OrderRepairLESRecords> queryRecordSn(String operate,String storageType,String orderRepairId) {
		return orderRepairLESRecordsReadDao.queryRecordSn(operate, storageType, orderRepairId);
	}

    /**
     *
     * @return
     */
    public List<OrderRepairLESRecords> queryNotLesOrder() {
        return orderRepairLESRecordsReadDao.queryNotLesOrder();
    }

	@Override
	public List<OrderRepairLESRecords> queryStorageType() {
		// TODO Auto-generated method stub
		return orderRepairLESRecordsReadDao.queryStorageType();
	}

    public List<OrderRepairLESRecords> b2cqueryStorageType() {
        return orderRepairLESRecordsReadDao.b2cqueryStorageType();
    }
	@Override
	public List<OrderRepairLESRecords> queryTransferBatch(String orderRepairsId) {
		return orderRepairLESRecordsReadDao.queryTransferBatch(orderRepairsId);
	}

	public List<OrderRepairLESRecords> queryRepairLesOrder(String orderRepairsId) {
		return orderRepairLESRecordsReadDao.queryRepairLesOrder(orderRepairsId);
	}

    /**
     *
     * @param code
     * @return
     */
	public List<Map<String, Object>> queryStorageRegion(String code){
        return orderRepairLESRecordsReadDao.queryStorageRegion(code);
    }
	
}
