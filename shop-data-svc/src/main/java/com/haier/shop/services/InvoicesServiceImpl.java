package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.InvoicesDao;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.service.InvoicesService;

@Service
public class InvoicesServiceImpl implements InvoicesService {
	
	@Autowired
	InvoicesDao invoicesDao;
	
    public int deleteByPrimaryKey(Integer id){
    	return invoicesDao.deleteByPrimaryKey(id);
    }

    public int insert(Invoices record){
    	return invoicesDao.insert(record);
    }

    public int insertSelective(Invoices record){
    	return invoicesDao.insertSelective(record);
    }

    public Invoices selectByPrimaryKey(Integer id){
    	return invoicesDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Invoices record){
    	return invoicesDao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(Invoices record){
    	return invoicesDao.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(Invoices record){
    	return invoicesDao.updateByPrimaryKey(record);
    }
    
    public List<Invoices> getForwardAdjust(long startTime, long endTime){
    	return invoicesDao.getForwardAdjust(startTime, endTime);
    }

    public List<Invoices> getNegativeAdjust(long startTime, long endTime){
    	return invoicesDao.getNegativeAdjust(startTime, endTime);
    }
    
    /**
     * 修改发票信息
     * @param invoice
     */
    public int update(Invoices invoice){
    	return invoicesDao.update(invoice);
    }
    /**
     * 利用cOrderSn获得OrderProducts信息
     * @param cOrderSn
     * @return
     */
    public OrderProductsNew getOrderProductsByCOrderSn(String cOrderSn){
    	return invoicesDao.getOrderProductsByCOrderSn(cOrderSn);
    }
    

    /**
     * 创建发票信息
     * @param invoice
     */
    public int insertInvoice(Invoices invoice){
    	return invoicesDao.insertInvoice(invoice);
    }
    
    /**
     * 根据网单号，获取发票信息列表
     * @param opId
     * @return
     */
    public List<Invoices> getByOrderProductId(Integer opId){
    	return invoicesDao.getByOrderProductId(opId);
    }
    /**
     * 获取待同步开票系统发票信息列表
     * @param topx 取数条数
     * @return
     */
    public List<Invoices> getSyncInvoiceList(Integer topx){
    	return invoicesDao.getSyncInvoiceList(topx);
    }
    
    /**
     * 根据网单号查询发票
     * @param cOrderSn
     * @return
     */
    public Invoices getByCorderSn(String cOrderSn){
    	return invoicesDao.getByCorderSn(cOrderSn);
    }
    
    /**
     * 根据网单号查询发票部分信息
     * @param cOrderSn
     * @return
     */
    public Map<String, Object> searchInvoicesInfoByCOrderSn(String cOrderSn){
    	return invoicesDao.searchInvoicesInfoByCOrderSn(cOrderSn);
    }
    
    /**
     * 获取待同步作废发票列表
     * @param topx 取数条数
     * @return
     */
    public List<Invoices> getInvoiceInvalidNotEndList(Integer topx){
    	return invoicesDao.getInvoiceInvalidNotEndList(topx);
    }
    
    /**
     * 根据ID，获取发票对象
     * @param id
     * @return
     */
    public Invoices get(Integer id){
    	return invoicesDao.get(id);
    }

}