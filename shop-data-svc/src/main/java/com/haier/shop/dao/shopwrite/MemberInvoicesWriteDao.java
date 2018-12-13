package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.MemberInvoices;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberInvoicesWriteDao {

    void updateLockStatus(MemberInvoices mInvoice);

    void update(MemberInvoices memberInvoices);

    void update1(MemberInvoices memberInvoices);

    int insert(MemberInvoices memberInvoices);

    void updateForsynInvoices(MemberInvoices memberInvoices);

    int updateByTitleAndNumber(MemberInvoices memberInvoices);
}
