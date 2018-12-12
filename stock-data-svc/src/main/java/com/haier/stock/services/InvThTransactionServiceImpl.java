package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import com.haier.stock.dao.stock.InvThTransactionDao;
import com.haier.stock.model.InvThTransaction;
import com.haier.stock.service.InvThTransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvThTransactionServiceImpl implements InvThTransactionService {
    @Autowired
    InvThTransactionDao invThTransactionDao;

    @Override
    public Integer insertTrans(InvThTransaction thTrans) {
        // TODO Auto-generated method stub
        return invThTransactionDao.insertTrans(thTrans);
    }

    @Override
    public Integer updateTrans(InvThTransaction thTrans) {
        // TODO Auto-generated method stub
        return invThTransactionDao.updateTrans(thTrans);
    }

    @Override
    public List<InvThTransaction> getInDataList(String channel) {
        // TODO Auto-generated method stub
        return invThTransactionDao.getInDataList(channel);
    }

    @Override
    public List<InvThTransaction> getInDataMachineSetList(String channel) {
        // TODO Auto-generated method stub
        return invThTransactionDao.getInDataMachineSetList(channel);
    }

    @Override
    public List<Map<String, Object>> getOutDataList(String channel) {
        // TODO Auto-generated method stub
        return invThTransactionDao.getOutDataList(channel);
    }

    @Override
    public Integer updateInStatusByOrderSns(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return invThTransactionDao.updateInStatusByOrderSns(params);
    }

    @Override
    public Integer updateInStatusByKeyProductNo(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return invThTransactionDao.updateInStatusByKeyProductNo(params);
    }

    @Override
    public List<InvThTransaction> querySubList(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return invThTransactionDao.querySubList(params);
    }

    @Override
    public Integer updateOutStatusByVbelnSos(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return invThTransactionDao.updateOutStatusByVbelnSos(params);
    }

    @Override
    public InvThTransaction getMaxTransId() {
        // TODO Auto-generated method stub
        return invThTransactionDao.getMaxTransId();
    }

    @Override
    public InvThTransaction getTrans(InvThTransaction thTrans) {
        // TODO Auto-generated method stub
        return invThTransactionDao.getTrans(thTrans);
    }

    @Override
    public List<InvThTransaction> getRepairIncompleteList(Integer repairStatus) {
        // TODO Auto-generated method stub
        return invThTransactionDao.getRepairIncompleteList(repairStatus);
    }

    @Override
    public Integer updateRepairStatus(String channelOrderSn, Integer repairStatus, String message) {
        // TODO Auto-generated method stub
        return invThTransactionDao.updateRepairStatus(channelOrderSn, repairStatus, message);
    }

    @Override
    public Integer updateJlRepairStatus(String channelOrderSn, Integer repairStatus, String message, String channel) {
        // TODO Auto-generated method stub
        return invThTransactionDao.updateJlRepairStatus(channelOrderSn, repairStatus, message, channel);
    }

    @Override
    public Integer updateById(InvThTransaction thTrans) {
        // TODO Auto-generated method stub
        return invThTransactionDao.updateById(thTrans);
    }

    @Override
    public InvThTransaction get(Integer id) {
        // TODO Auto-generated method stub
        return invThTransactionDao.get(id);
    }

    @Override
    public List<InvThTransaction> getHpNodesList() {
        // TODO Auto-generated method stub
        return invThTransactionDao.getHpNodesList();
    }

    @Override
    public List<InvThTransaction> getInvThOrderInDataList(String channel) {
        // TODO Auto-generated method stub
        return invThTransactionDao.getInvThOrderInDataList(channel);
    }

    @Override
    public List<InvThTransaction> getInvThOrderOutDataList(String channel) {
        // TODO Auto-generated method stub
        return invThTransactionDao.getInvThOrderOutDataList(channel);
    }

    @Override
    public List<InvThTransaction> queryInvThOrderRepairCloseData(Integer repairStatus, String channel) {
        // TODO Auto-generated method stub
        return invThTransactionDao.queryInvThOrderRepairCloseData(repairStatus, channel);
    }

    @Override
    public InvThTransaction getInvThTransactionByProductNoAndChannelSn(String productNo, String channelOrderSn) {
        // TODO Auto-generated method stub
        return invThTransactionDao.getInvThTransactionByProductNoAndChannelSn(productNo, channelOrderSn);
    }
}
