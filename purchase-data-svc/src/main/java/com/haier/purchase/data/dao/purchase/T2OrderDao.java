package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.T2OrderInterfaceLog;
import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.purchase.data.model.HaierLimitHistoryItem;
import com.haier.purchase.data.model.T2OrderItem;



/**
 * Created by 黄俊 on 2014/7/8.
 */
public interface T2OrderDao {

    /**
     * 获取预测备料历史查询表单
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    public List<T2OrderItem> findT2Orders(Map<String, Object> params);

    /**
     * 渠道和品类对应的已提交数量取得
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    public List<T2OrderItem> findT2OrdersSum(Map<String, Object> params);

    /**
     * 获得T+2条数
     * 
     * @return
     */
    public int findT2OrdersCNT(Map<String, Object> params);

    /**
     * 获得条数
     * 
     * @return
     */
    public int getRowCnts();

    /**
     * 获取PO查询信息
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    public List<CrmOrderItem> findPOList(Map<String, Object> params);
    /**
     * 获取3W查询信息
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    public List<CrmOrderItem> find3WList(Map<String, Object> params);
    /**
     * 获取PO信息条数
     * 
     * @param params
     * @return
     */
    public int findPOListCNT(Map<String, Object> params);
    /**
     * 获取3W信息条数
     * 
     * @param params
     * @return
     */
    public int find3WListCNT(Map<String, Object> params);

    /**
     * 订单提交
     * 
     * @param params
     */
    public void updateOrderStatus(Map<String, Object> params);

    /**
     * 错误信息更新
     * 
     * @param params
     */
    public void updateByQty(T2OrderItem orderItem);

    /**
     * 价格信息更新
     * 
     * @param params
     */
    public void updatePrice(T2OrderItem orderItem);

    /**
     * 创建T+2订单表单
     * 
     * @param t2OrderItem
     * @return
     */
    Integer insert(T2OrderItem t2OrderItem);

    /**
     * 订单删除
     * 
     * @param params
     */
    public void deleteOrderStatus(Map<String, Object> params);

    /**
     * 订单数量修改
     * 
     * @param params
     */
    public Integer updateCount(Map<String, Object> params);

    /**
     * 撤销订单
     * 
     * @param params
     */
    public void updateRevokeFlag(Map<String, Object> params);

    /**
     * 撤销失败信息更新
     * 
     * @param t2OrderItem
     */
    public void updateErrMsg(T2OrderItem t2OrderItem);

    /**
     * Oms订单号取得
     * 
     * @param params
     */
    public List<T2OrderItem> findCancelT2OrderForOms(Map<String, Object> params);

    /**
     * 综合查询获取T+2订单信息
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    public List<T2OrderItem> findT2OrderMultipleList(Map<String, Object> params);

    /**
     * 综合查询获取T+2订单信息条数
     * 
     * @param params
     * @return
     */
    public Integer findT2OrderMultipleListCNT(Map<String, Object> params);

    /**
     * 手工关单
     * 
     * @param params
     */
    public void manualCloseOrder(Map<String, Object> params);

    /**
     * 撤消手工关单 赵雪林 2015-1-14
     * 
     * @param params
     */
    public void cancelCloseOrder(Map<String, Object> params);

    /**
     * 审核订单
     * 
     * @param params
     */
    public void reviewT2Order(Map<String, Object> params);

    /**
     * 产品部审核订单
     * 
     * @param params
     */
    public void reviewT2OrderDepart(Map<String, Object> params);

    /**
     * 审核通过订单情报取得
     * 
     * @param params
     * @return
     */
    public List<T2OrderItem> findT2OrderForOms(Map<String, Object> params);

    /**
     * 提报OMS的状态更新
     * 
     * @param params
     */
    public void updateByOms(T2OrderItem t2OrderItem);

    public void updateOmsFlowFlagOnly(T2OrderItem t2OrderItem);

    /**
     * 根据品类渠道取得在途
     * 
     * @param category_id
     *            品类
     * @param ed_channel_id
     *            渠道
     * @return
     */
    public List<T2OrderItem> getOnwayNumByCateChan(@Param("category_id") String category_id,
                                                   @Param("ed_channel_id") String ed_channel_id);

    /**
     * 根据品类渠道取得本周已用
     * 
     * @param report_year_week
     *            本周
     * @param category_id
     *            品类
     * @param ed_channel_id
     *            渠道
     * @return
     */
    public List<T2OrderItem> getUsedNumByCateChan(@Param("report_year_week") String report_year_week,
                                                  @Param("category_id") String category_id,
                                                  @Param("ed_channel_id") String ed_channel_id);

    /**
     * 更新订单状态为已开入WA提单
     * 
     * @param so_id
     *            so单号
     * @param outTime
     *            出日日顺库时间
     * @return
     */
    public void updateStatusToOutRRS(Map<String, Object> params);

    /**
     * 更新订单状态为已入WA库
     * 
     * @param so_id
     *            so单号
     * @param inTime
     *            入WA库时间
     * @return
     */
    public void updateStatusToInWA(Map<String, Object> params);

    /**
     * 根据订单号获取CRM自动采购的DN号
     * 
     * @param params
     * @return
     */
    public List<String> get85DNFromHaierT2(Map params);

    /**
     * 更新入WA库状态
     * 
     * @param params
     */
    public void updateStatusToInWAByDN(Map params);

    /**
     * 更新haier t+2主表状态
     * 
     * @param params
     */
    public void updateStatus(Map params);

    /**
     * 从超期库存闸口表中获得标准的渠道、品类数据
     * 
     * @return
     */
    public List<HaierLimitHistoryItem> getFullChannelProductList(Map params);

    /**
     * 插入或更新超期历史数据
     * 
     * @param item
     */
    public void insertOrUpdateLimitHistory(HaierLimitHistoryItem item);

    /**
     * 插入超期历史数据
     * 
     * @param item
     */
    public void insertLimitHistory(HaierLimitHistoryItem item);

    /**
     * 更新超期历史数据
     * 
     * @param item
     */
    public void updateLimitHistory(HaierLimitHistoryItem item);

    /**
     * 更新同步状态
     * 
     * @param item
     */
    public void updateSyncStatusByOms(T2OrderItem item);

    /**
     * OMS已冻结推送
     * 
     * @param params
     */
    public int commitAgainOrderMultiple(Map<String, Object> params);

    /**
     * 来源单号查询
     * 
     * @param source_order_id
     * @return
     */
    public T2OrderItem getDataBySourceOrderId(String source_order_id);
    
    /**
     * 创建3w信息
     * 
     * @param t2OrderItem
     * @return
     */
    Integer w3insert(CrmOrderItem crmOrderItem);

    /**
     * 更新3w修改
     * 
     * @param params
     */
    public void update3wByDnCode(CrmOrderItem crmOrderItem);
    /**
     * 根据85码获取wp单号
     * 
     * @param params
     */
    public List<CrmOrderItem>  getDataBySourceByWPid(CrmOrderItem crmOrderItem);
    /**
     * 根据J单号获取wp单号
     * 
     * @param params
     */
    public List<CrmOrderItem>  getDataByJCode(CrmOrderItem crmOrderItem);

    /**
     *预约导出列表查询
     * 
     * @return
     */
    public List<CrmOrderItem> get3wSubscribeList(Map<String, Object> params);
    /**
     * 自动任务查询需要从crm获取订单的信息
     * @return
     */
    public List<CrmOrderItem> getT2ListToCrm();
    /**
     * 根据预约单号和85码从3w表中查询数据
     * @return
     */
    public List<CrmOrderItem> getIs3wList(CrmOrderItem crmOrderItem);
    /**
     *根据wp单号从3w表中查询已签收的货物数
     * @return
     */
    public List<CrmOrderItem> getSumByWPNo(CrmOrderItem crmOrderItem);
    /**
     * 订单状态更新为已入3w库
     * @param crmOrderItem
     */
    public void update3Wyiru(CrmOrderItem crmOrderItem);
    /**
     *款先签收，需要自动推送cbs的订单信息
     * @return
     */
    public List<CrmOrderItem> getListByNoToSAP(CrmOrderItem crmOrderItem);
    /**
     * 更新3w推送sap结果信息
     * @param crmOrderItem
     */
    public void updateToSAPresoult(CrmOrderItem crmOrderItem);
    
    /**
     * 获取待推送sap的订单
     * @return
     */
    public List<T2OrderItem> findT2OrdersToSap();

    public void insertT2OrderInterfaceLog(Map<String, Object> params);

    public List<T2OrderInterfaceLog> findPurchaseLog(Map<String,Object> map);

    public int getPurchaseLogRow(Map<String, Object> map);
}
