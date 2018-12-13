package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderrepairHPrecordsReadDao;
import com.haier.shop.dao.shopwrite.OrderrepairHPrecordsWriteDao;
import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.model.OrderrepairHPrecordsVO;
import com.haier.shop.service.ShopOrderrepairHPrecordsService;

@Service
public class ShopOrderrepairHPrecordsServiceImpl implements ShopOrderrepairHPrecordsService {

    @Autowired
    private OrderrepairHPrecordsWriteDao orderrepairHPrecordsWriteDao;
    @Autowired
    private OrderrepairHPrecordsReadDao orderrepairHPrecordsReadDao;

    public List<OrderrepairHPrecordsVO> selectByHpreCordsId(String id){
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

	@Override
	public int UpdaVirtualEntryState(String id, String virtualEntryState) {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsWriteDao.UpdaVirtualEntryState(id, virtualEntryState);
	}

	@Override
	public List<OrderrepairHPrecordsVO> queryNotOutBoxQuality() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.queryNotOutBoxQuality();
	}

	@Override
	public List<OrderrepairHPrecordsVO> quertNotOutBoxStockPishSAP() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.quertNotOutBoxStockPishSAP();
	}

	@Override
	public OrderrepairHPrecordsVO querynotOutBoxOrederSn(String id) {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.querynotOutBoxOrederSn(id);
	}

	@Override
	public List<OrderrepairHPrecordsVO> queryThreeOutOfStorage() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.queryThreeOutOfStorage();
	}

	@Override
	public List<OrderrepairHPrecordsVO> SigninInvalidatedInvoiceView() {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.SigninInvalidatedInvoiceView();
	}

	@Override
	public int queryjudgeRejects(String orderRepairId) {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsReadDao.queryjudgeRejects(orderRepairId);
	}

	@Override
	public int updataPushRejects(String id) {
		// TODO Auto-generated method stub
		return orderrepairHPrecordsWriteDao.updataPushRejects(id);
	}

	@Override
	public List<OrderrepairHPrecordsVO> findByOid(String oid) {
		return orderrepairHPrecordsReadDao.findByOid(oid);
	}

	@Override
	public OrderrepairHPrecordsVO queryTenLibrary(String id) {
		return orderrepairHPrecordsReadDao.queryTenLibrary(id);
	}

	public OrderrepairHPrecordsVO queryRepairOrderInfo(String id) {
		return orderrepairHPrecordsReadDao.queryRepairOrderInfo(id);
	}

	@Override
	public OrderrepairHPrecordsVO queryChangeTheboxUnbox(String id) {
		return orderrepairHPrecordsReadDao.queryChangeTheboxUnbox(id);
	}

	@Override
	public int queryOrderHAdd1(String orderRepairId) {
		return orderrepairHPrecordsReadDao.queryOrderHAdd1(orderRepairId);
	}

	@Override
	public OrderrepairHPrecordsVO findInvoice(Integer orderProductId) {
		return orderrepairHPrecordsReadDao.findInvoice(orderProductId);
	}

}
