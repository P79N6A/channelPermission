package com.haier.shop.service;

import com.haier.shop.model.MemberInvoices;

/**
 * Created by 李超 on 2018/1/9.
 */
public interface MemberInvoicesService {
    /**
     * 根据主键获取会员发票对象
     * @param id
     * @return
     */
    MemberInvoices get(Integer id);

    int insert(MemberInvoices memberInvoices);

    void update(MemberInvoices memberInvoices);

    void updateForsynInvoices(MemberInvoices memberInvoices);

    MemberInvoices getByOrderId(Integer orderId);

    MemberInvoices checkPassedValuedInvoice(MemberInvoices memberInvoices);

    int updateByTitleAndNumber(MemberInvoices memberInvoices);

    Integer getIdByOrderId(Integer id);
}
