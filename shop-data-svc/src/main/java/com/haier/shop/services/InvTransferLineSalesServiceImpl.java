package com.haier.shop.services;

import com.haier.shop.dao.shopread.InvTransferLineSalesReadDao;
import com.haier.shop.dao.shopwrite.InvTransferLineSalesDao;
import com.haier.shop.model.InvTransferLineSales;
import com.haier.shop.model.InvTransferLineSalesVo;
import com.haier.shop.service.InvTransferLineSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvTransferLineSalesServiceImpl implements InvTransferLineSalesService {
    @Autowired
    private InvTransferLineSalesDao invTransferLineSalesDao;
    @Autowired
    private InvTransferLineSalesReadDao invTransferLineSalesReadDao;

    @Override
    public void insertSalse(InvTransferLineSales invTransferLineSales) {
        invTransferLineSalesDao.insertSalse(invTransferLineSales);
    }

    @Override
    public List<InvTransferLineSales> findListByVo(InvTransferLineSalesVo vo, int start, int rows) {
        return invTransferLineSalesReadDao.findListByVo(vo,start,rows);
    }

    @Override
    public int getPagerCountS(InvTransferLineSalesVo vo) {
        return invTransferLineSalesReadDao.getPagerCountS(vo);
    }

    @Override
    public List<InvTransferLineSales> exportListByVo(InvTransferLineSalesVo vo) {
        return invTransferLineSalesReadDao.exportListByVo(vo);
    }

    @Override
    public InvTransferLineSales getByLine__num(String line_num) {
        return invTransferLineSalesReadDao.getByLine__num(line_num);
    }

    @Override
    public void updateInvoiceState(int invoiceState,Integer id) {
        invTransferLineSalesDao.updateInvoiceState(invoiceState,id);
    }
}
