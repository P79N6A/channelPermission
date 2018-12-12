package com.haier.shop.service;

import java.util.List;
import java.util.Map;

import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsNew;


public interface InvoicesService {
    int deleteByPrimaryKey(Integer id);

    int insert(Invoices record);

    int insertSelective(Invoices record);

    Invoices selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Invoices record);

    int updateByPrimaryKeyWithBLOBs(Invoices record);

    int updateByPrimaryKey(Invoices record);
    
    List<Invoices> getForwardAdjust(long startTime, long endTime);

    List<Invoices> getNegativeAdjust(long startTime, long endTime);
    
    /**
     * 修改发票信息
     * @param invoice
     */
    int update(Invoices invoice);
    /**
     * 利用cOrderSn获得OrderProducts信息
     * @param cOrderSn
     * @return
     */
    OrderProductsNew getOrderProductsByCOrderSn(String cOrderSn);
    

    /**
     * 创建发票信息
     * @param invoice
     */
    int insertInvoice(Invoices invoice);
    
    /**
     * 根据网单号，获取发票信息列表
     * @param opId
     * @return
     */
    List<Invoices> getByOrderProductId(Integer opId);
    /**
     * 获取待同步开票系统发票信息列表
     * @param topx 取数条数
     * @return
     */
    List<Invoices> getSyncInvoiceList(Integer topx);
    
    /**
     * 根据网单号查询发票
     * @param cOrderSn
     * @return
     */
    Invoices getByCorderSn(String cOrderSn);
    
    /**
     * 根据网单号查询发票部分信息
     * @param cOrderSn
     * @return
     */
    Map<String, Object> searchInvoicesInfoByCOrderSn(String cOrderSn);
    
    /**
     * 获取待同步作废发票列表
     * @param topx 取数条数
     * @return
     */
    List<Invoices> getInvoiceInvalidNotEndList(Integer topx);
    
    /**
     * 根据ID，获取发票对象
     * @param id
     * @return
     */
    Invoices get(Integer id);

}