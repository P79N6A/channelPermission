package com.haier.shop.service;


import com.haier.shop.model.InvoiceExceptionDispItem;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.model.MemberInvoicesDispItem;
import com.haier.shop.model.OrderProducts;

import java.util.List;
import java.util.Map;

/**
 * 发票业务操作接口（业务专用）
 * @author lichunsheng
 * @create 2018-01-03
 **/
public interface ShopMemberInvoicesService {

    /**
     * 根据id查询会员发票信息
     * @param id
     * @return
     */
    MemberInvoices getById(Integer id);

    /**
     * 根据订单id查询会员发票信息
     * @param orderId
     * @return
     */
    MemberInvoices getByOrderId(Integer orderId);

    /**
     * 根据订单id获得用户发票抬头
     * @param orderId
     * @return
     */
    String getInvoiceTitleByOrderId(Integer orderId);

    /**
     * 更新会员发票信息锁状态
     */
    void updateLockStatus(MemberInvoices mInvoice);

    /**
     * 分页查询用户发票信息
     * @param paramMap
     * @return
     */
    Map<String, Object> findMemberInvoiceListByPage(Map<String, Object> paramMap);

    /**
     * 用户发票信息导出查询
     * @param paramMap
     * @return
     */
    List<MemberInvoicesDispItem> getExportMemberInvoicesList(Map<String, Object> paramMap);

    /**
     * 根据抬头获取用户发票信息
     * @param invoiceTitle
     * @return
     */
    MemberInvoices getMemberInvoiceByInvoiceTitle(String invoiceTitle);

    /**
     * 获取审核通过的用户发票信息
     * @param memberInvoices
     * @return
     */
    MemberInvoices checkPassedValuedInvoice(MemberInvoices memberInvoices);

    /**
     * 更新用户发票信息
     * @param memberInvoices
     */
    void update(MemberInvoices memberInvoices);

    /**
     * 按页查询异常发票展示信息
     * @param paramMap
     * @return
     */
    Map<String, Object> getInvoiceExceptionListByPage(Map<String, Object> paramMap);

    /**
     * 查询异常发票信息列表
     * @param paramMap
     * @return
     */
    List<InvoiceExceptionDispItem> getInvoiceExceptionList(Map<String, Object> paramMap);

    /**
     * 查询订单id对应的发票是否为电子发票
     * @param orderId
     * @return
     */
    int getElectricFlag(Integer orderId);

    /**
     * 修改或审核发票信息 业务操作
     * @param orderProductsList
     * @param memberInvoices
     * @param oldMemberInvoices
     * @param auditor
     */
    void saveInvoiceOperate(List<OrderProducts> orderProductsList,MemberInvoices memberInvoices, MemberInvoices oldMemberInvoices, String auditor);

    /**
     * 解锁发票信息 业务操作
     * @param id
     * @param userName
     * @return
     */
    String unlockMemberInvoices(Integer id, String userName);
}
