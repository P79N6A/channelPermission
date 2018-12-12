package com.haier.distribute.services;

import com.haier.distribute.data.model.PopOrderProducts;
import com.haier.distribute.data.model.TAdjustData;
import com.haier.distribute.data.service.OrderProductsService;
import com.haier.distribute.data.service.TAdjustDataService;
import com.haier.distribute.service.DistributeCenterAdjustTimingService;
import com.haier.shop.model.Invoices;
import com.haier.shop.service.InvoicesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 胡万来 on 2018/1/15 0015.
 */
public class DistributeCenterAdjustTimingServiceImpl implements DistributeCenterAdjustTimingService {
   @Autowired
    private TAdjustDataService tAdjustDataService;
   @Autowired
    private OrderProductsService orderProductsService;
   @Autowired
    private InvoicesService invoicesService;

    @Override
    public void adjustTiming() {
        Calendar now = Calendar.getInstance();
        TAdjustData tad = new TAdjustData();
        long endTime = System.currentTimeMillis() / 1000;
        long startTime = endTime - 86400;

        List<Invoices> forward = invoicesService.getForwardAdjust(startTime, endTime);
        for (Invoices f : forward) {//正向数据
            f.setEaiWriteState("已开票");
            f.setState(1);
        }
        List<Invoices> negative = invoicesService.getNegativeAdjust(startTime, endTime);
        for (Invoices n : negative) {//逆向数据
            n.setEaiWriteState("冲红/作废");
            n.setState(2);
        }
        forward.addAll(negative);

        for (Invoices i : forward) {//i-->D1
            String orderSn = i.getCOrderSn().substring(0, 14);
            PopOrderProducts op = orderProductsService.getOneByOrderSn(orderSn);//op-->D2

            if (i.getState() == 2) {
                tad.setNumber((short) (op.getNumber() * (-1)));//销售数量
                tad.setProductAmount(op.getPrice().multiply(new BigDecimal(-1)));//总价（发票金额）
                Long idate = Long.valueOf(i.getInvalidTime());
                tad.setInvoiceTime(new Date(idate * 1000));//开票时间
            } else {
                tad.setNumber(op.getNumber());//销售数量
                tad.setProductAmount(op.getPrice());//总价（发票金额）
                Long idate = Long.valueOf(i.getBillingTime());
                tad.setInvoiceTime(new Date(idate * 1000));//开票时间
            }
            tad.setcOrderSn(i.getCOrderSn());//网单号
            tad.setcOrderSnOld(orderSn);//原网单号
            tad.setSourceOrderSn(op.getSourceOrderSn());//来源订单号
            tad.setSource(op.getSource());//订单来源
            tad.setSellPeople(op.getSellPeople());//销售代表
            tad.setCategory(op.getCateId().toString());//品类
            tad.setBrand(op.getBrandName());//品牌
            tad.setSku(op.getSku());//sku
            tad.setProductName(op.getProductName());//宝贝型号
            tad.setConsignee(op.getConsignee());//收件人姓名
            Long date = Long.parseLong(op.getcPayTime());
            tad.setcPayTime(new Date(date * 1000));//付款时间
            tad.setcOrderStatus(op.getStatus().toString());//钉钉状态
            tad.setPeriod(now.get(Calendar.MONTH));//期间
            tad.setYear(now.get(Calendar.YEAR));//年份
            tad.setInvoiceStatus(i.getEaiWriteState());//开票状态
            tad.setSettleType("1");//结算方式
            tad.setSendStatus("0");//推送状态
            tad.setDataStatus(i.getState().toString());//数据标识
            tad.setAuditStatus("3");//审核状态
            tad.setSendTime(new Date());//推送时间
            tAdjustDataService.insertSelective(tad);
        }
    }
}
