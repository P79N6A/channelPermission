package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.dao.purchase.CgoNewDao;
import com.haier.purchase.data.model.*;
import com.haier.purchase.data.service.CgoNewService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


/**
 * Created by 黄俊 on 2014/8/4.
 */
@Service
public class CgoNewServiceImpl implements CgoNewService {
    @Autowired
    CgoNewDao cgoNewDao;

    /**
     * 获取CGOT+2查询表单
     *
     * @param Map<String, Object> params
     * @return
     */
    @Override
    public List<CgoOrderItemNew> findCgoOrderList(Map<String, Object> params) {
        return cgoNewDao.findCgoOrderList(params);
    }

    /**
     * 获取CGOT+2查询条数
     *
     * @param Map<String, Object> params
     * @return
     */
    @Override
    public Integer findCgoOrderListCNT(Map<String, Object> params) {
        return cgoNewDao.findCgoOrderListCNT(params);
    }

    /**
     * 获得条数
     *
     * @return
     */
    @Override
    public int getRowCnts() {
        return cgoNewDao.getRowCnts();
    }

    /**
     * 订单提交
     *
     * @param params
     * @return
     */
    @Override
    public Object updateCgoOrderStatus(Map<String, Object> params) {
        return cgoNewDao.updateCgoOrderStatus(params);
    }

    /**
     * 错误信息更新
     *
     * @param params
     * @return
     */
    @Override
    public Object updateByQty(CgoOrderItemNew orderItem) {
        return cgoNewDao.updateByQty(orderItem);
    }

    /**
     * 价格信息更新
     *
     * @param params
     * @return
     */
    @Override
    public Object updatePrice(CgoOrderItemNew orderItem) {
        return cgoNewDao.updatePrice(orderItem);
    }

    /**
     * 订单删除
     *
     * @param params
     * @return
     */
    @Override
    public Object deleteCgoOrderStatus(Map<String, Object> params) {
        return cgoNewDao.deleteCgoOrderStatus(params);
    }

    /**
     * 综合查询获取T+2订单信息
     *
     * @param Map<String, Object> params
     * @return
     */
    @Override
    public List<CgoOrderItemNew> findCgoOrderMultipleList(Map<String, Object> params) {
        return cgoNewDao.findCgoOrderMultipleList(params);
    }

    /**
     * 综合查询获取T+2订单信息条数
     *
     * @param Map<String, Object> params
     * @return
     */
    @Override
    public Integer findCgoOrderMultipleListCNT(Map<String, Object> params) {
        return cgoNewDao.findCgoOrderMultipleListCNT(params);
    }

    /**
     * 手工关单
     *
     * @param params
     * @return
     */
    @Override
    public Object manualCloseCgoOrder(Map<String, Object> params) {
        return cgoNewDao.manualCloseCgoOrder(params);
    }

    /**
     * 撤消手工关单
     * 赵雪林 2015-1-14
     *
     * @param params
     * @return
     */
    @Override
    public Object cancelCloseCgoOrder(Map<String, Object> params) {
        return cgoNewDao.cancelCloseCgoOrder(params);
    }

    /**
     * 获取PO查询信息
     *
     * @param Map<String, Object> params
     * @return
     */
    @Override
    public List<SIOUInfoItem> findCgoPOList(Map<String, Object> params) {
        return cgoNewDao.findCgoPOList(params);
    }

    /**
     * 获取PO查询信息条数
     *
     * @param Map<String, Object> params
     * @return
     */
    @Override
    public Integer findCgoPOListCNT(Map<String, Object> params) {
        return cgoNewDao.findCgoPOListCNT(params);
    }

    /**
     * 创建T+2单
     *
     * @param params
     * @return
     */
    @Override
    public Object insertCgoOrderList(Map<String, Object> params) {
        return cgoNewDao.insertCgoOrderList(params);
    }

    /**
     * 审核订单
     *
     * @param params
     * @return
     */
    @Override
    public Object reviewCgoOrderList(Map<String, Object> params) {
        return cgoNewDao.reviewCgoOrderList(params);
    }

    /**
     * 产品部审核订单
     *
     * @param params
     * @return
     */
    @Override
    public Object reviewCgoOrderDepart(Map<String, Object> params) {
        return cgoNewDao.reviewCgoOrderDepart(params);
    }

    /**
     * 订单数量修改
     *
     * @param params
     */
    @Override
    public Integer updateCgoOrderCount(Map<String, Object> params) {
        return cgoNewDao.updateCgoOrderCount(params);
    }

    /**
     * 撤销订单
     *
     * @param params
     * @return
     */
    @Override
    public Object updateCgoOrderRevokeFlag(Map<String, Object> params) {
        return cgoNewDao.updateCgoOrderRevokeFlag(params);
    }

    /**
     * CGO正品退货检索
     *
     * @param params
     * @return
     */
    @Override
    public List<CgoGenuineRejectItemNew> findCgoGenuineRejectList(Map<String, Object> params) {
        return cgoNewDao.findCgoGenuineRejectList(params);
    }

    /**
     * CGO正品退货条数
     *
     * @param params
     * @return
     */
    @Override
    public Integer findCgoGenuineRejectListCNT(Map<String, Object> params) {
        return cgoNewDao.findCgoGenuineRejectListCNT(params);
    }

    /**
     * 创建退货单
     *
     * @param params
     * @return
     */
    @Override
    public Object insertCgoGenuineRejectList(Map<String, Object> params) {
        return cgoNewDao.insertCgoGenuineRejectList(params);
    }

    /**
     * 正品退货订单删除
     *
     * @param params
     * @return
     */
    @Override
    public Object deleteCgoGenuineRejectStatus(Map<String, Object> params) {
        return cgoNewDao.deleteCgoGenuineRejectStatus(params);
    }

    /**
     * 正品退货订单提交
     *
     * @param params
     * @return
     */
    @Override
    public Object updateCgoGenuineRejectStatus(Map<String, Object> params) {
        return cgoNewDao.updateCgoGenuineRejectStatus(params);
    }

    /**
     * 正品退货撤销订单
     *
     * @param params
     * @return
     */
    @Override
    public Object revokeCgoGenuineRejectStatus(CgoGenuineRejectItemNew cgoGenuineRejectItem) {
        return cgoNewDao.revokeCgoGenuineRejectStatus(cgoGenuineRejectItem);
    }

    /**
     * 正品退货取消订单
     *
     * @param params
     * @return
     */
    @Override
    public Object cancelCgoGenuineRejectStatus(Map<String, Object> params) {
        return cgoNewDao.cancelCgoGenuineRejectStatus(params);
    }

    /**
     * 正品退货订单出库
     *
     * @param params
     * @return
     */
    @Override
    public Object exwarehouseCgoGenuineRejectStatus(Map<String, Object> params) {
        return cgoNewDao.exwarehouseCgoGenuineRejectStatus(params);
    }

    /**
     * 负PO单List取得
     *
     * @param params
     * @return
     */
    @Override
    public List<SIOUInfoItem> findCgoGenuineRejectDetailList(Map<String, Object> params) {
        return cgoNewDao.findCgoGenuineRejectDetailList(params);
    }

    /**
     * 负PO单List取得条数
     *
     * @param params
     * @return
     */
    @Override
    public Integer findCgoGenuineRejectDetailListCNT(Map<String, Object> params) {
        return cgoNewDao.findCgoGenuineRejectDetailListCNT(params);
    }

    /**
     * 更新订单状态，从“内部审核通过”到“待CGO评审”
     *
     * @param params
     * @return
     */
    @Override
    public Object updateCgoOrderStatusFrom20(Map<String, Object> params) {
        return cgoNewDao.updateCgoOrderStatusFrom20(params);
    }

    /**
     * @param orderID
     * @param status
     * @return
     */
    @Override
    public Object updateCgoOrderStatusFrom30(Map<String, Object> params) {
        return cgoNewDao.updateCgoOrderStatusFrom30(params);
    }

    /**
     * @param params
     * @return
     */
    @Override
    public Object updateOrderStatusByOrderID(Map<String, Object> params) {
        return cgoNewDao.updateOrderStatusByOrderID(params);
    }

    /**
     * @param params
     */
    @Override
    public Object updateCgoTuiOrderStatusFrom30(Map<String, Object> params) {
        return cgoNewDao.updateCgoTuiOrderStatusFrom30(params);
    }

    /**
     * @param params
     */
    @Override
    public Object updateRejectOrderStatusByOrderID(Map<String, Object> params) {
        return cgoNewDao.updateRejectOrderStatusByOrderID(params);
    }

    /**
     * @param params
     */
    @Override
    public Object updateOrderErrorMessage(Map<String, Object> params) {
        return cgoNewDao.updateOrderErrorMessage(params);
    }

    /**
     * @param params
     */
    @Override
    public Object updateRejectOrderErrorMessage(Map<String, Object> params) {
        return cgoNewDao.updateRejectOrderErrorMessage(params);
    }

    /**
     * @param params
     */
    @Override
    public Object updateSiOuInfoErrorMessage(Map<String, Object> params) {
        return cgoNewDao.updateSiOuInfoErrorMessage(params);
    }

    /**
     * @param params
     */
    @Override
    public String findCgoStorgeId(Map<String, Object> params) {
        return cgoNewDao.findCgoStorgeId(params);
    }

    /**
     * 根据品类渠道取得在途
     *
     * @param category_id   品类
     * @param ed_channel_id 渠道
     * @return
     */
    @Override
    public List<CgoOrderItemNew> getOnwayNumByCateChan(String category_id,
                                                       String ed_channel_id) {
        return cgoNewDao.getOnwayNumByCateChan(category_id, ed_channel_id);
    }

    /**
     * 根据品类渠道取得本周已用
     *
     * @param report_year_week 本周
     * @param category_id      品类
     * @param ed_channel_id    渠道
     * @return
     */
    @Override
    public List<CgoOrderItemNew> getUsedNumByCateChan(String report_year_week,
                                                      String category_id,
                                                      String ed_channel_id) {
        return cgoNewDao.getUsedNumByCateChan(report_year_week, category_id, ed_channel_id);
    }

    @Override
    public Integer getMaxFlowFlagFromCGO(Map param) {
        return cgoNewDao.getMaxFlowFlagFromCGO(param);
    }

    @Override
    public List<SIOUInfoItem> findCgoOrderListBySiou(Map param) {
        return cgoNewDao.findCgoOrderListBySiou(param);
    }

    /**
     * 获取订单信息
     *
     * @param Map<String, Object> params
     * @return
     */
    @Override
    public List<CgoOrderRejectItemNew> findCgoOrderRejectList(Map<String, Object> params) {
        return cgoNewDao.findCgoOrderRejectList(params);
    }

    @Override
    public List<SIOUInfoItem> findCgoGenuineRejectListByWporderid(Map<String, Object> params) {
        return cgoNewDao.findCgoGenuineRejectListByWporderid(params);
    }

    /**
     * 更新提交时间，如果已经有提交时间，那么不更新
     *
     * @param params
     */
    @Override
    public Object updateCgoGenuineRejectCommitTime(Map<String, Object> params) {
        return cgoNewDao.updateCgoGenuineRejectCommitTime(params);
    }

    /**
     * 获得统帅总单信息
     *
     * @param params
     * @return
     */
    @Override
    public List<CgoOrderItemNew> getTSOrderInfo(Map params) {
        return cgoNewDao.getTSOrderInfo(params);
    }

    /**
     * 更新发运工厂信息和最晚离基地日期
     *
     * @param params
     */
    @Override
    public Object updateStatusInfo(PurchaseT2AuditResultFromCGO vo) {
        return cgoNewDao.updateStatusInfo(vo);
    }

    /**
     * 查询总单要求到货时间
     *
     * @param params
     * @return
     */
    @Override
    public List<String> findRequestArrivalTime(Map params) {
        return cgoNewDao.findRequestArrivalTime(params);
    }

    @Override
    public Object insertOrUpdateSiOrderInfo(CreateSiOuInfoVO csiv) {
        return cgoNewDao.insertOrUpdateSiOrderInfo(csiv);
    }
}
