package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.MemberInvoicesReadDao;
import com.haier.shop.dao.shopwrite.MemberInvoicesWriteDao;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.service.MemberInvoicesService;

/**
 * Created by 李超 on 2018/1/9.
 */
@Service
public class MemberInvoicesServiceImpl implements MemberInvoicesService {

    @Autowired
    MemberInvoicesReadDao memberInvoicesReadDao;
    @Autowired
    MemberInvoicesWriteDao memberInvoicesWriteDao;

    @Override
    public MemberInvoices get(Integer id) {
        return memberInvoicesReadDao.getById(id);
    }

    @Override
    public int insert(MemberInvoices memberInvoices) {
        return memberInvoicesWriteDao.insert(memberInvoices);
    }

    @Override
    public void update(MemberInvoices memberInvoices) {
        memberInvoicesWriteDao.update(memberInvoices);
    }

    @Override
    public void updateForsynInvoices(MemberInvoices memberInvoices) {
        memberInvoicesWriteDao.updateForsynInvoices(memberInvoices);
    }

    @Override
    public MemberInvoices getByOrderId(Integer orderId) {
        return memberInvoicesReadDao.getByOrderId(orderId);
    }

    @Override
    public MemberInvoices checkPassedValuedInvoice(MemberInvoices memberInvoices) {
        return memberInvoicesReadDao.checkPassedValuedInvoice(memberInvoices);
    }
}
