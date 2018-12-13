package com.haier.shop.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.shop.dao.shopread.CostPoolsUsedLogsReadDao;
import com.haier.shop.dao.shopwrite.CostPoolsUsedLogsWriteDao;
import com.haier.shop.dao.shopwrite.OrderOperateLogsWriteDao;
import com.haier.shop.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.CostPoolsReadDao;
import com.haier.shop.dao.shopwrite.CostPoolsWriteDao;
import com.haier.shop.service.CostPoolsService;

@Service
public class CostPoolsServiceImpl implements CostPoolsService {

    private static Logger logger = Logger.getLogger(CostPoolsServiceImpl.class);

    @Autowired
    private CostPoolsReadDao costPoolsReadDao;
    @Autowired
    private CostPoolsWriteDao costPoolsWriteDao;
    @Autowired
    private OrderOperateLogsWriteDao orderOperateLogsWriteDao;
    @Autowired
    private CostPoolsUsedLogsReadDao costPoolsUsedLogsReadDao;
    @Autowired
    private CostPoolsUsedLogsWriteDao costPoolsUsedLogsWriteDao;

    public List<CostPools> getCouponCostPoolByChannelAndChanYeAndYearMonth(Integer channel, String chanYe, Integer yearMonth){
        return costPoolsReadDao.getCouponCostPoolByChannelAndChanYeAndYearMonth(channel, chanYe, yearMonth);
    }

    public int updateCouponCostPool(Integer id, BigDecimal couponAmount){
        return costPoolsWriteDao.updateCouponCostPool(id, couponAmount);
    }

    public List<Map<String, Object>> getProductCate(){
        return costPoolsReadDao.getProductCate();
        }

    public List<Map<String, Object>> getBrand(){
        return costPoolsReadDao.getBrand();
    }

    @Override
    public List<CostPools> findCostPoolsByPage(Map<String, Object> params) {
        return costPoolsReadDao.findCostPoolsByPage(params);
    }

    @Override
    public int getTotal() {
        return costPoolsReadDao.getTotal();
    }

    @Override
    public List<CostPools> findCostPoolsByChannel(String industrys, Integer channelValue, String type, String yearMonth) {
        return costPoolsReadDao.findCostPoolsByChannel(industrys,channelValue,type,yearMonth);
    }

    @Override
    public Integer findCostPoolsCount(String industrys, Integer channelValue, String type, String yearMonth) {
        return costPoolsReadDao.findCostPoolsCount(industrys,channelValue,type,yearMonth);
    }

    @Override
    public int updateBanlacnAmount(Integer id, BigDecimal slPrices) {
        return costPoolsWriteDao.updateBanlacnAmount(id,slPrices);
    }

    @Override
    public CostPools findCostPoolsByChannelAndBatchAsc(String chanye, Integer channelValue, String type, String yearMonth,BigDecimal slPrice) {
        return costPoolsReadDao.findCostPoolsByChannelAndBatchAsc(chanye,channelValue,type,yearMonth,slPrice);
    }

    @Override
    public List<Map<String, Object>> getExportCostPoolsList(Map<String, Object> paramMap) {
        return costPoolsReadDao.getExportCostPoolsList(paramMap);
    }

    @Override
    public int addCostPool(CostPools costPools) {

        CostPools batch = costPoolsReadDao.findCostPoolsByBatch(costPools);

        if(batch==null||batch.getId()==null){
            costPools.setBatch(1);
        }else{
            costPools.setBatch(batch.getBatch()+1);
        }

        return costPoolsWriteDao.addCostPool(costPools);

    }

    @Override
    public CostPools findCostPoolsById(String id) {
        return costPoolsReadDao.getId(id);
    }

    @Override
    public Integer updateCostPools(CostPools costPools) {
        return costPoolsWriteDao.updateCostPools(costPools);
    }

    @Override
    public Integer deleteCostPools(String id) {
        return costPoolsWriteDao.deleteCostPools(id);
    }

    @Override
    public CostPools findcostPoolsByTYBC(CostPools costPools) {
        return costPoolsReadDao.findcostPoolsByTYBC(costPools);
    }

    @Override
    public int reverseUpdateBanlacnAmount(Orders orders, OrderProducts orderProducts,String userName) {
        CostPoolsUsedLogs lcpulogs = costPoolsUsedLogsReadDao.findLogsByOidCid(orders.getId(),orderProducts.getId());
        if(lcpulogs==null){
            return 2; //订单网单没有 插入日志
        }
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String yearMonth = String.valueOf(year)+(month<10 ? "0"+month : ""+month);
        CostPools ncp = costPoolsReadDao.getReverseCostPools(lcpulogs.getChanye(),
                lcpulogs.getChannel(),lcpulogs.getType(),yearMonth);
        if(ncp==null){
            return 3; //没有可添加的费用信息
        }
        //修改营销池费用
        int rows = costPoolsWriteDao.reverseUpdateBanlacnAmount(ncp.getId(), lcpulogs.getAmount());
        if (rows > 0) {
            CostPoolsUsedLogs costPoolsUsedLogs = new CostPoolsUsedLogs();
            costPoolsUsedLogs.setSiteId(1);
            costPoolsUsedLogs.setType(ncp.getType());
            costPoolsUsedLogs.setChannel(ncp.getChannel());
            costPoolsUsedLogs.setChanye(ncp.getChanYe());
            costPoolsUsedLogs.setYearMonth(ncp.getYearMonth());
            costPoolsUsedLogs.setOrderSn(orders.getOrderSn());
            costPoolsUsedLogs.setcOrderSn(orderProducts.getcOrderSn());
            costPoolsUsedLogs.setOrderId(orders.getId());
            costPoolsUsedLogs.setCorderId(orderProducts.getId());
            costPoolsUsedLogs.setRelationOrderSn(orders.getRelationOrderSn());
            costPoolsUsedLogs.setSource(orders.getSource());
            costPoolsUsedLogs.setUsedType(2);
            costPoolsUsedLogs.setAmount(lcpulogs.getAmount());
            costPoolsUsedLogs.setRemark("费用池返还费用");
            costPoolsUsedLogs.setNumber(Integer.valueOf(orderProducts.getNumber()+""));
            costPoolsUsedLogsWriteDao.insert(costPoolsUsedLogs);
        }
        //添加日志
        OrderOperateLogs operateLogs = new OrderOperateLogs();
        operateLogs.setSiteId(1);
        operateLogs.setOrderId(orders.getId());
        operateLogs.setOrderProductId(orderProducts.getId());
        operateLogs.setNetPointId(0);
        operateLogs.setStatus(orderProducts.getStatus());
        //付款状态
        operateLogs.setPaymentStatus(orderProducts.getcPaymentStatus());
        operateLogs.setOperator(userName);
        operateLogs.setChangeLog("赠品费用返还");
        operateLogs.setRemark("返还金额 "+lcpulogs.getAmount());
        orderOperateLogsWriteDao.insert(operateLogs);
        return 1;
    }
}
