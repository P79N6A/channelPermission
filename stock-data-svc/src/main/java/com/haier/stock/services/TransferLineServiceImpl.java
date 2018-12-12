package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.TransferLineDao;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.service.StockTransferLineService;


/**
 * 货物调拨管理DAO
 *                       
 * @Filename: TransferDao.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 *
 */
@Service
public class TransferLineServiceImpl implements StockTransferLineService {
    
    @Autowired
    TransferLineDao transferLineDao;
    
    @Override
    public InvTransferLine get(Integer lineId) {
        return transferLineDao.get(lineId);
    }

    @Override
    public List<InvTransferLine> getTransferLines(Map<String, Object> params) {
        return transferLineDao.getTransferLines(params);
    }

    @Override
    public Integer getCount(Map<String, Object> params) {
        return transferLineDao.getCount(params);
    }

    @Override
    public List<InvTransferLine> getByLineIds(Integer[] lineIdArr) {
        return transferLineDao.getByLineIds(lineIdArr);
    }

    @Override
    public List<InvTransferLine> getInTransferInfoFromCaiNiao() {
        return transferLineDao.getInTransferInfoFromCaiNiao();
    }

    @Override
    public Integer updateLineStatusByLineIds(Map<String, Object> params) {
        return transferLineDao.updateLineStatusByLineIds(params);
    }

    @Override
    public void insert(InvTransferLine line) {
        transferLineDao.insert(line);
    }

    @Override
    public List<InvTransferLine> getPPTransferLineNum(String param) {
        return transferLineDao.getPPTransferLineNum(param);
    }

    @Override
    public InvTransferLine getInvTransferLineByLineNum(String lineNum) {
        return transferLineDao.getInvTransferLineByLineNum(lineNum);
    }

    @Override
    public InvTransferLine getInvTransferLineBySoLineNum(String soLineNum) {
        return transferLineDao.getInvTransferLineBySoLineNum(soLineNum);
    }

    @Override
    public List<InvTransferLine> getTransferLineByLineStatus(Integer lineStatus) {
        return transferLineDao.getTransferLineByLineStatus(lineStatus);
    }

    @Override
    public List<InvTransferLine> getTransferLine3WByStatusReason(Integer lineStatus) {
        return transferLineDao.getTransferLine3WByStatusReason(lineStatus);
    }

    @Override
    public Integer update(InvTransferLine line) {
        return transferLineDao.update(line);
    }

    @Override
    public Integer updateLineTransferQtyByLineId(Map<String, Object> params) {
        return transferLineDao.updateLineTransferQtyByLineId(params);
    }

    @Override
    public Integer updateRemarkByLineId(Integer id, String remark) {
        return transferLineDao.updateRemarkByLineId(id,remark);
    }
}
