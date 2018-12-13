package com.haier.afterSale.util;

import com.haier.afterSale.service.ICountTimeOut;
import com.haier.afterSale.services.*;
import com.haier.afterSale.service.ReportType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 判断超时工厂类,根据传入数据和节点,调用响应的实现类执行超时计算
 * 会把超时类型,超时时间,应完成时间,实际完成时间直接放入map
 * 如果sql已经过滤掉了无用数据,不再判断是否剔除数据;否则需要剔除无用数据
 * 
 * @Filename: ReportCountTimeOutFactoy.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@Component

public class ReportCountTimeOutFactoy {
    private static ReportCountTimeOutFactoy         instance            = new ReportCountTimeOutFactoy();
    /**
     * 类型和超时判断接口实现类映射关系,不使用if else,提高效率和可读性
     */
    private static final Map<String, ICountTimeOut> typeCountTimeOutMap = new HashMap<String, ICountTimeOut>();

    static {
        //妥投率
        typeCountTimeOutMap.put(ReportType.OWF, new OwfCountTimeOutImpl());
        //妥投率
        //typeCountTimeOutMap.put(ReportType.O2OOWF, new O2OOwfCountTimeOutImpl());
        //确认
        //typeCountTimeOutMap.put(ReportType.CONFIRM, new ConfirmCountTimeOutImpl());
        //派工
        //typeCountTimeOutMap.put(ReportType.HP, new HpCountTimeOutImpl());
        //开单
        //typeCountTimeOutMap.put(ReportType.LES, new LesCountTimeOutImpl());
        //多层级转运
        //typeCountTimeOutMap.put(ReportType.TRANSPORT, new TransportCountTimeOutImpl());
        //送达网点
        //typeCountTimeOutMap.put(ReportType.NETPOINT, new NetpointCountTimeOutImpl());
        //送达用户
        //typeCountTimeOutMap.put(ReportType.USER, new UserCountTimeOutImpl());
        //逆向闭环
        typeCountTimeOutMap.put(ReportType.ORDERCLOSE, new OrderCloseCountTimeOutImpl());
        //审核
        typeCountTimeOutMap.put(ReportType.AUDIT, new AuditCountTimeOutImpl());
        //O2O审核
        typeCountTimeOutMap.put(ReportType.O2OAUDIT, new O2OAuditCountTimeOutImpl());
        //一次质检
        typeCountTimeOutMap.put(ReportType.CHECKQUALITY, new CheckQualityCountTimeOutImpl());
        //入库
        typeCountTimeOutMap.put(ReportType.INSTOCK, new InStockCountTimeOutImpl());
        //冲票
        typeCountTimeOutMap.put(ReportType.INVOICE, new InvoiceCountTimeOutImpl());
        //二次质检
        typeCountTimeOutMap.put(ReportType.RECHECKQUALITY, new RecheckQualityCountTimeOutImpl());
        //转库
        typeCountTimeOutMap.put(ReportType.TRANSMITSTOCK, new TransmitStockCountTimeOutImpl());
        //退款
        typeCountTimeOutMap.put(ReportType.REFUND, new RefundCountTimeOutImpl());
        //COD回款
        //typeCountTimeOutMap.put(ReportType.COD, new CodCountTimeOutImpl());
        //O2O COD回款
        //typeCountTimeOutMap.put(ReportType.O2OCOD, new O2OCodCountTimeOutImpl());
        //转箱
        typeCountTimeOutMap.put(ReportType.TRANSMITBOX, new TransmitBoxCountTimeOutImpl());
        //二次鉴定换箱
        typeCountTimeOutMap.put(ReportType.RECHECKTRANSMITBOX,new RecheckTransmitBoxCountTimeOutImpl());
        //22库转10或41库
        typeCountTimeOutMap.put(ReportType.STORE22, new STORE22CountTimeOutImpl());
    }

    /**
     * 构造方法,避免直接new
     */
    private ReportCountTimeOutFactoy() {
    }

    /**
     * 判断超时工厂单例
     * @return
     */
    public static ReportCountTimeOutFactoy getInstance() {
        return instance;
    }

    /**
     * 根据传入数据和节点调用响应超时判断
     * 如果sql已经过滤了无用数据,不需要再判断状态等剔除逻辑.不做无用功,提高效率
     * @param dataList 数据列表
     * @param type 节点
     * @param isSqlFilter 是否sql已经过滤无用数据
     */
    public void countTimeOut(List<Map<String, Object>> dataList, String type, boolean isSqlFilter) {
        if (isSqlFilter) {
            typeCountTimeOutMap.get(type).countTimeOut(dataList);
        } else {
            typeCountTimeOutMap.get(type).countTimeOutWithFilter(dataList);
        }
    }

    /**
     * 根据传入数据和节点列表调用响应超时判断
     * 如果sql已经过滤了无用数据,不需要再判断状态等剔除逻辑.不做无用功,提高效率
     * @param dataList 数据列表
     * @param typeList 节点列表
     * @param isSqlFilter 是否sql已经过滤无用数据 false
     */
    public void countTimeOut(List<Map<String, Object>> dataList, List<String> typeList,boolean isSqlFilter) {
        if (isSqlFilter) {
            for (String type : typeList) {
                typeCountTimeOutMap.get(type).countTimeOut(dataList);
            }
        } else {
            for (String type : typeList) {
                typeCountTimeOutMap.get(type).countTimeOutWithFilter(dataList);
            }
        }
    }
}
