package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoiceExceptionDispItem;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.model.MemberInvoicesDispItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberInvoicesReadDao {

    /**
     * 根据订单id查询会员发票信息
     * @param orderId
     * @return
     */
    MemberInvoices getByOrderId(@Param("orderId") Integer orderId);

    /**
     * 根据id查询会员发票信息
     * @param id
     * @return
     */
    MemberInvoices getById(@Param("id") Integer id);

    /**
     * 根据订单id获得用户发票抬头
     * @param orderId
     * @return
     */
    String getInvoiceTitleByOrderId(@Param("orderId") Integer orderId);

    /**
     * 根据订单id查询会员发票信息
     * @param paramMap
     * @return
     */
    List<MemberInvoicesDispItem> getMemberInvoicesOrderList(Map<String, Object> paramMap);

    /**
     * 查询记录数
     * @return
     */
    int getRowCnts();

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

}
