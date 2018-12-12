package com.haier.shop.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.shop.dao.shopwrite.InvoiceChangeLogsWriteDao;
import com.haier.shop.dao.shopwrite.OrderProductsWriteDao;
import com.haier.shop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.MemberInvoicesReadDao;
import com.haier.shop.dao.shopwrite.MemberInvoicesWriteDao;
import com.haier.shop.service.ShopMemberInvoicesService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户发票业务实现类（业务专用）
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Service
public class ShopMemberInvoiceServiceImpl implements ShopMemberInvoicesService {

    @Autowired
    private MemberInvoicesReadDao memberInvoicesReadDao;
    @Autowired
    private MemberInvoicesWriteDao memberInvoicesWriteDao;
    @Autowired
    private OrderProductsWriteDao orderProductsWriteDao;
    @Autowired
    private InvoiceChangeLogsWriteDao invoiceChangeLogsWriteDao;

    @Override
    public MemberInvoices getById(Integer id) {
        return memberInvoicesReadDao.getById(id);
    }

    @Override
    public MemberInvoices getByOrderId(Integer orderId) {
        return memberInvoicesReadDao.getByOrderId(orderId);
    }

    @Override
    public String getInvoiceTitleByOrderId(Integer orderId) {
        return memberInvoicesReadDao.getInvoiceTitleByOrderId(orderId);
    }

    @Override
    public void updateLockStatus(MemberInvoices mInvoice) {
        memberInvoicesWriteDao.updateLockStatus(mInvoice);
    }

    @Override
    public Map<String, Object> findMemberInvoiceListByPage(Map<String, Object> paramMap) {
        List<MemberInvoicesDispItem> list = memberInvoicesReadDao.getMemberInvoicesOrderList(paramMap);
        int resultcount = memberInvoicesReadDao.getRowCnts();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", list);
        return retMap;
    }

    @Override
    public List<MemberInvoicesDispItem> getExportMemberInvoicesList(Map<String, Object> paramMap) {
        return memberInvoicesReadDao.getMemberInvoicesOrderList(paramMap);
    }

    @Override
    public MemberInvoices getMemberInvoiceByInvoiceTitle(String invoiceTitle) {
        return memberInvoicesReadDao.getMemberInvoiceByInvoiceTitle(invoiceTitle);
    }

    @Override
    public MemberInvoices checkPassedValuedInvoice(MemberInvoices memberInvoices) {
        return memberInvoicesReadDao.checkPassedValuedInvoice(memberInvoices);
    }

    @Override
    public void update(MemberInvoices memberInvoices) {
        memberInvoicesWriteDao.update(memberInvoices);
    }

    @Override
    public Map<String, Object> getInvoiceExceptionListByPage(Map<String, Object> paramMap) {

        List<InvoiceExceptionDispItem> list = memberInvoicesReadDao
                .getInvoiceExceptionList(paramMap);
        int resultcount = memberInvoicesReadDao.getRowCnts();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", list);

        return null;
    }

    @Override
    public List<InvoiceExceptionDispItem> getInvoiceExceptionList(Map<String, Object> paramMap) {
        List<InvoiceExceptionDispItem> list = memberInvoicesReadDao.getInvoiceExceptionList(paramMap);
        return list;
    }

    @Override
    public int getElectricFlag(Integer orderId) {
        return memberInvoicesReadDao.getElectricFlag(orderId);
    }

    /**
     * 修改核对发票业务操作
     * @param memberInvoices
     * @param oldmemberInvoices
     */
    @Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void saveInvoiceOperate(List<OrderProducts> orderProductsList,MemberInvoices memberInvoices, MemberInvoices oldmemberInvoices, String auditor) {
        // 增值税发票的场合
        if (memberInvoices.getInvoiceType().equals(1)) {
            // 将开票类型修改为“共享开票”
            if (orderProductsList != null && orderProductsList.size() > 0) {
                for (OrderProducts eachOrderProducts : orderProductsList) {
                    eachOrderProducts.setMakeReceiptType(2);
                    orderProductsWriteDao.updateMakeReceiptType(eachOrderProducts);
                }
            }

            // 增票不能是电子发票
            memberInvoices.setElectricFlag(0);

            // 已存在的增票信息自动审核通过
            Integer state_t = memberInvoices.getState();
            memberInvoices.setState(1);
            MemberInvoices queryMemberInvoices = memberInvoicesReadDao
                    .checkPassedValuedInvoice(memberInvoices);

            if (queryMemberInvoices != null) {
                memberInvoices.setState(1); // 审核通过
                memberInvoices.setAuditTime(new Date().getTime() / 1000);
                memberInvoices.setAuditor("系统");
                memberInvoices.setRemark("该增票信息先前已存在并审核通过,本订单增票信息自动审核通过");
                memberInvoices.setParentId(queryMemberInvoices.getId());
                memberInvoicesWriteDao.update(memberInvoices);
            } else {
                memberInvoices.setState(state_t);
                memberInvoicesWriteDao.update(memberInvoices);
            }
        } else {
            memberInvoicesWriteDao.update(memberInvoices);
        }
        //插入日志
        InvoiceChangeLogs invoiceChangeLogs = new InvoiceChangeLogs();
        invoiceChangeLogs.setAddTime(new Date().getTime() / 1000);
        invoiceChangeLogs.setInvoiceId(memberInvoices.getId());
        invoiceChangeLogs.setOperator(auditor);
        if (!oldmemberInvoices.getInvoiceTitle().equals(
                memberInvoices.getInvoiceTitle())) {
            invoiceChangeLogs.setRemark("发票抬头：由“" + oldmemberInvoices.getInvoiceTitle()
                    + "”变更为“" + memberInvoices.getInvoiceTitle()
                    + "”");
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
        if (!oldmemberInvoices.getInvoiceType()
                .equals(memberInvoices.getInvoiceType())) {
            Integer oldmemberinvoiceType = oldmemberInvoices.getInvoiceType();
            String oldmemberinvoiceTypeStr="";
            if(oldmemberinvoiceType.equals(1)){
                oldmemberinvoiceTypeStr="增值税发票";
            }else if(oldmemberinvoiceType.equals(2)){
                oldmemberinvoiceTypeStr="普通发票";
            }else if(oldmemberinvoiceType.equals(3)){
                oldmemberinvoiceTypeStr="增值税发票（普）";
            }

            Integer memberinvoiceType = memberInvoices.getInvoiceType();
            String memberinvoiceTypeStr="";
            if(memberinvoiceType.equals(1)){
                memberinvoiceTypeStr="增值税发票";
            }else if(memberinvoiceType.equals(2)){
                memberinvoiceTypeStr="普通发票";
            }else if(memberinvoiceType.equals(3)){
                memberinvoiceTypeStr="增值税发票（普）";
            }

            invoiceChangeLogs
                    .setRemark("发票类型：由“"
                            +oldmemberinvoiceTypeStr
                            + "”变更为“"
                            + memberinvoiceTypeStr+"”");
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
        if (!oldmemberInvoices.getTaxPayerNumber().equals(
                memberInvoices.getTaxPayerNumber())) {
            invoiceChangeLogs.setRemark("纳税人识别号：由“" + oldmemberInvoices.getTaxPayerNumber()
                    + "”变更为“" + memberInvoices.getTaxPayerNumber()
                    + "”");
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
        if (!oldmemberInvoices.getRegisterAddress().equals(
                memberInvoices.getRegisterAddress())) {
            invoiceChangeLogs.setRemark("注册地址：由“" + oldmemberInvoices.getRegisterAddress()
                    + "”变更为“"
                    + memberInvoices.getRegisterAddress() + "”");
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
        if (!oldmemberInvoices.getRegisterPhone().equals(
                memberInvoices.getRegisterPhone())) {
            invoiceChangeLogs.setRemark("注册电话：由“" + oldmemberInvoices.getRegisterPhone()
                    + "”变更为“" + memberInvoices.getRegisterPhone()
                    + "”");
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
        if (!oldmemberInvoices.getBankName().equals(memberInvoices.getBankName())) {
            invoiceChangeLogs
                    .setRemark("开户银行：由“" + oldmemberInvoices.getBankName() + "”变更为“"
                            + memberInvoices.getBankName() + "”");
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
        if (!oldmemberInvoices.getBankCardNumber().equals(
                memberInvoices.getBankCardNumber())) {
            invoiceChangeLogs.setRemark("开户卡号：由“" + oldmemberInvoices.getBankCardNumber()
                    + "”变更为“" + memberInvoices.getBankCardNumber()
                    + "”");
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
        if (!oldmemberInvoices.getState().equals(memberInvoices.getState())) {
            invoiceChangeLogs.setRemark("审核状态：由“" + oldmemberInvoices.getState() + "”变更为“"
                    + memberInvoices.getState() + "”");
            invoiceChangeLogs.setRemark(invoiceChangeLogs.getRemark().replace("0", "待审核")
                    .replace("1", "审核通过").replace("2", "拒绝"));
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
        if (!oldmemberInvoices.getRemark().equals(memberInvoices.getRemark())) {
            invoiceChangeLogs.setRemark("备注信息：由“" + oldmemberInvoices.getRemark() + "”变更为“"
                    + memberInvoices.getRemark() + "”");
            invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        }
    }

    /**
     * 解锁发票信息业务操作
     * @param id
     * @param userName
     * @return
     */
    @Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public String unlockMemberInvoices(Integer id, String userName) {
        // 根据Id获得发票信息
        MemberInvoices memberInvoices = memberInvoicesReadDao.getById(id);
        if (memberInvoices == null) {
            return "对不起，该发票信息不存在!";
        }
        memberInvoices.setId(id);
        memberInvoices.setIsLock(0);//待锁定
        memberInvoicesWriteDao.update(memberInvoices);

        InvoiceChangeLogs invoiceChangeLogs = new InvoiceChangeLogs();
        invoiceChangeLogs.setAddTime(new Date().getTime() / 1000);
        invoiceChangeLogs.setInvoiceId(memberInvoices.getId());
        invoiceChangeLogs.setOperator(userName);
        invoiceChangeLogs.setRemark("手动解除发票信息锁定");
        invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
        return "";
    }
}
