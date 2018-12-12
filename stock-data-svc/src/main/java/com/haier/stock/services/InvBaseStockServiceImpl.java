package com.haier.stock.services;

import com.haier.common.PagerInfo;

import com.haier.stock.dao.stock.InvBaseStockDao;
import com.haier.stock.model.InvBaseStockExcel;
import com.haier.stock.service.InvBaseStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InvBaseStockServiceImpl implements InvBaseStockService {
    @Autowired
    private InvBaseStockDao invBaseStockDao;
    public List<InvBaseStockExcel> queryInvBaseStockList(Map<String,String> map,
                                                         PagerInfo pager){
        List<InvBaseStockExcel> list=invBaseStockDao.queryInvBaseStockList(map,pager);
        return list;
    }
    public List<InvBaseStockExcel> queryInvBaseStockList(Map<String,String> map){
        List<InvBaseStockExcel> list=invBaseStockDao.queryInvBaseStockCountList(map);
        return list;
    }
    public  List<InvBaseStockExcel> queryInvBaseStockList1(InvBaseStockExcel invBaseStock, PagerInfo pager){
        return invBaseStockDao.queryInvBaseStockList1(invBaseStock,pager);
    }
    public int getRowCnt(){
        return invBaseStockDao.getRowCnt();
    }
}
