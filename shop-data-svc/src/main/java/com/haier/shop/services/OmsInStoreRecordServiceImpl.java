package com.haier.shop.services;

import com.haier.shop.dao.shopwrite.OmsInStoreRecordWriteDao;
import com.haier.shop.model.OmsInStoreRecords;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.service.OmsInStoreRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OmsInStoreRecordServiceImpl implements OmsInStoreRecordService {
    @Autowired
    private OmsInStoreRecordWriteDao omsInStoreRecordWriteDao;
    public List<OmsInStoreRecords> insert(List<OmsInStoreRecords> list){
        omsInStoreRecordWriteDao.insert(list);
        return  list;
    }
    public List<String> selectRowId(List<String> list){
        return omsInStoreRecordWriteDao.selectRowId(list);
    }
    public List<OmsInStoreRecords> selectByStatus(){
        return omsInStoreRecordWriteDao.selectByStatus();
    }
    public void updateByDb(List<String> list){ omsInStoreRecordWriteDao.updateByDb(list);}

    @Override
    public int updateForOrderCode(OmsInStoreRecords oms) {
        return omsInStoreRecordWriteDao.updateForOrderCode(oms);
    }

    @Override
    public void updateDb(List<OmsInStoreRecords> list_db) {
        omsInStoreRecordWriteDao.updateDb(list_db);
    }

    @Override
    public List<OmsInStoreRecords> selectByVomStatus() {
        return omsInStoreRecordWriteDao.selectByVomStatus();
    }

    @Override
    public int updataVomStatus(OmsInStoreRecords oms) {
        return omsInStoreRecordWriteDao.updataVomStatus(oms);
    }

    @Override
    public List<OmsInStoreRecords> findForVomStatus() {
        return omsInStoreRecordWriteDao.findForVomStatus();
    }

    @Override
    public List<OmsInStoreRecords> findAllocationRemnant(OmsInStoreRecords params) {
        return omsInStoreRecordWriteDao.findAllocationRemnant(params);
    }

    @Override
    public List<OmsInStoreRecords> findForSapStatus() {
        return omsInStoreRecordWriteDao.findForSapStatus();
    }

    @Override
    public int updateOutSapStatus(OmsInStoreRecords oms) {
        return omsInStoreRecordWriteDao.updateOutSapStatus(oms);
    }

    @Override
    public int updateInSapStatus(OmsInStoreRecords oms) {
        return omsInStoreRecordWriteDao.updateInSapStatus(oms);
    }

    @Override
    public int findAllocationRemnantCount(OmsInStoreRecords params) {
        return omsInStoreRecordWriteDao.findAllocationRemnantCount(params);
    }

    @Override
    public void updateNotDb(List<String> list) {
        omsInStoreRecordWriteDao.updateNotDb(list);
    }

    @Override
	public List<OmsInStoreRecords> selectForStatus() {
		// TODO Auto-generated method stub
		return omsInStoreRecordWriteDao.selectForStatus();
	}
}
