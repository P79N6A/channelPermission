package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderRepairsReadDao;
import com.haier.shop.dao.shopwrite.OrderRepairsWriteDao;
import com.haier.shop.model.OrderRepairs;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.service.ShopOrderRepairsService;

@Service
public class ShopOrderRepairsServiceImpl implements ShopOrderRepairsService {

    @Autowired
    OrderRepairsReadDao orderRepairsReadDao;

    @Autowired
    OrderRepairsWriteDao orderRepairsWriteDao;

    public  int insertSelective(OrderRepairsVo record){
    orderRepairsWriteDao.insertSelective(record);
     return   record.getId();
    }


    public String queryRepaiSn(int cOrderId){
        return    orderRepairsReadDao.queryRepaiSn(cOrderId);
    }

    public OrderRepairsVo  queryPairsId(String id){
        return    orderRepairsReadDao.queryPairsId(id);
    }

    public List<OrderRepairsVo> selectOrderRepairsNOFinish(String id){
        return  orderRepairsReadDao.selectOrderRepairsNOFinish(id);
    }

    public int updataStatus( String id, String status,String handleRemark){
        return    orderRepairsWriteDao.updataStatus(id, status, handleRemark);
    }

    public int updatePushHp(OrderRepairsVo orderRepairsVo){
        return    orderRepairsWriteDao.updatePushHp(orderRepairsVo);
    }

    public OrderRepairsVo selectPairs(String id){
        return    orderRepairsReadDao.selectPairs(id);
    }

    public int  updateStatus( String receiptStatus, String storageStatus,String id){
        return    orderRepairsWriteDao.updateStatus(receiptStatus, storageStatus, id);
    }

    public  String queryIsRejectionSign(String id){
        return    orderRepairsReadDao.queryIsRejectionSign(id);
    }


    public OrderRepairsVo queryOrderProductId( String id){
        return    orderRepairsReadDao.queryOrderProductId(id);
    }

    public OrderRepairsVo qureyRepairs(String id){
        return    orderRepairsReadDao.qureyRepairs(id);
    }

    public OrderRepairsVo selectOrederProductId(String id){
        return    orderRepairsReadDao.selectOrederProductId(id);
    }

    public OrderRepairsVo  queryTwoIdentification(String id){
        return    orderRepairsReadDao.queryTwoIdentification(id);
    }

    @Override
    public List<OrderRepairs> getByOrderProductId(Integer orderProductId) {
        return orderRepairsReadDao.getByOrderProductId(orderProductId);
    }

    @Override
    public Integer updateReceiptInfo(OrderRepairs orderRepairs) {
        return orderRepairsWriteDao.updateReceiptInfo(orderRepairs);
    }


	@Override
	public int updataPushSap(String id,String pushSap) {
		// TODO Auto-generated method stub
		return orderRepairsWriteDao.updataPushSap(id,pushSap);
	}


	@Override
	public int RepairsTermination(String id,String handleStatus, String handleRemark) {
		// TODO Auto-generated method stub
		return orderRepairsWriteDao.RepairsTermination(id, handleStatus, handleRemark);
	}

    @Override
    public int RepairsRminatereverse(String id,String handleStatus, String handleRemark,String terminationReason) {
        // TODO Auto-generated method stub
        return orderRepairsWriteDao.RepairsRminatereverse(id, handleStatus, handleRemark,terminationReason);
    }

    /**
     *
     * @param id
     * @param handleStatus
     * @return
     */
    public int updateHandleStatus(String id,String handleStatus) {

        return orderRepairsWriteDao.updateHandleStatus(id, handleStatus);
    }

	@Override
	public OrderRepairsVo queryReturnEdit(String id) {
		// TODO Auto-generated method stub
		return orderRepairsReadDao.queryReturnEdit(id);
	}


	@Override
	public OrderRepairsVo queryRepairsInvoiceId(String repairSn) {
		// TODO Auto-generated method stub
		return orderRepairsReadDao.queryRepairsInvoiceId(repairSn);
	}


	@Override
	public int updataOrderRepairsStatus(String receiptStatus, String storageStatus, String id) {
		// TODO Auto-generated method stub
		return orderRepairsWriteDao.updataOrderRepairsStatus(receiptStatus, storageStatus, id);
	}


	@Override
	public int queryRepairsStats(String id) {
		// TODO Auto-generated method stub
		return orderRepairsReadDao.queryRepairsStats(id);
	}


	@Override
	public OrderRepairsVo queryWhetherRepaiSn(int cOrderId) {
		// TODO Auto-generated method stub
		return orderRepairsReadDao.queryWhetherRepaiSn(cOrderId);
	}
}
