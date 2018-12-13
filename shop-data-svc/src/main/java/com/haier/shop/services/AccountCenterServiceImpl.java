package com.haier.shop.services;


import com.haier.shop.model.O2OOrderTailendQueues;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.AccountCenterReadDao;
import com.haier.shop.dao.shopwrite.AccountCenterWriteDao;
import com.haier.shop.model.O2OOrderConfirmQueues;
import com.haier.shop.model.O2oOrderCloseQueues;
import com.haier.shop.model.O2oOrderCloseQueuesExt;
import com.haier.shop.service.AccountCenterService;

@Service
public class AccountCenterServiceImpl implements AccountCenterService {

    @Autowired
    AccountCenterReadDao accountCenterReadDao;
    @Autowired
    AccountCenterWriteDao accountCenterWriteDao;

    /**
     * 插入 o2o确认订单成功队列表
     */
    @Override
    public int insertForwardConfirmToAccountCenterList(O2OOrderConfirmQueues o2oOrderConfirmQueues) {
        return accountCenterWriteDao.insertForwardConfirmToAccountCenterList(o2oOrderConfirmQueues);
    }

    /**
     * 更具网单号ID OrderProductId 获取 o2o确认订单成功队列表
     *
     * @param orderProductId
     * @return
     */
    @Override
    public O2OOrderConfirmQueues getForwardConfirmToAccountCenterByOrderProductId(Integer orderProductId) {
        return accountCenterReadDao.getForwardConfirmToAccountCenterByOrderProductId(orderProductId);
    }

    /**
     * 获取 o2o确认订单成功队列表(count=0)
     *
     * @param topX
     * @return
     */
    @Override
    public List<O2OOrderConfirmQueues> getForwardConfirmToAccountCenterList(Integer topX) {
        return accountCenterReadDao.getForwardConfirmToAccountCenterList(topX);
    }

    /**
     * 获取 o2o确认订单成功队列表(count>0 and count<30)
     *
     * @param topX
     * @return
     */
    @Override
    public List<O2OOrderConfirmQueues> getForwardConfirmToAccountCenterListThousand(Integer topX) {
        return accountCenterReadDao.getForwardConfirmToAccountCenterListThousand(topX);
    }

    /**
     * 更新 o2o确认订单成功队列表
     */
    @Override
    public int updateForwardConfirmToAccountCenterList(O2OOrderConfirmQueues o2oOrderConfirmQueues) {
        return accountCenterWriteDao.updateForwardConfirmToAccountCenterList(o2oOrderConfirmQueues);
    }

    /**
     * 获取完成关闭网单(count=0)
     *
     * @param topX
     */
    @Override
    public List<O2oOrderCloseQueues> getFinishColseQueues(Integer topX) {
        return accountCenterReadDao.getFinishColseQueues(topX);
    }

    /**
     * 获取完成关闭网单(count>0 and count<30)
     *
     * @param topX
     */
    @Override
    public List<O2oOrderCloseQueues> getDealFinishColseQueues(Integer topX) {
        return accountCenterReadDao.getDealFinishColseQueues(topX);
    }

    /**
     * 获得取消网单
     *
     * @param topX,flag flag=0 count大于0且小于30次
     *                  flag=1 count=0
     */
    @Override
    public List<O2oOrderCloseQueues> getCancelStatusOrderQueues(Integer topX,
                                                                Integer flag) {
        return accountCenterReadDao.getCancelStatusOrderQueues(topX, flag);
    }

    /**
     * 更新推送成功完成关闭网单
     *
     * @param o2oQueue
     */
    @Override
    public int updateO2oCloseQueuesSuccessData(O2oOrderCloseQueues o2oQueue) {
        return accountCenterWriteDao.updateO2oCloseQueuesSuccessData(o2oQueue);
    }

    /**
     * 更新未完成推送以及推送失败的完成关闭网单
     *
     * @param o2oQueue
     */
    @Override
    public int updateO2oCloseQueuesFailData(O2oOrderCloseQueues o2oQueue) {
        return accountCenterWriteDao.updateO2oCloseQueuesFailData(o2oQueue);
    }

    /**
     * 完成关闭订单
     *
     * @param o2oQueue
     * @return
     */
    @Override
    public int insert(O2oOrderCloseQueues o2oQueue) {
        return accountCenterWriteDao.insert(o2oQueue);
    }

    /**
     * 完成关闭取消关闭网单写入队列（不重复插入）
     *
     * @param o2oQueue
     * @return
     */
    @Override
    public int insertNotExists(O2oOrderCloseQueues o2oQueue) {
        return accountCenterWriteDao.insertNotExists(o2oQueue);
    }

    @Override
    public List<Map<String, Object>> getBrands() {
        return accountCenterReadDao.getBrands();
    }

    @Override
    public List<Map<String, Object>> getProductCates() {
        return accountCenterReadDao.getProductCates();
    }

    @Override
    public List<Map<String, Object>> getReverseAcceptFinishInfo(Long lastTime) {
        return accountCenterReadDao.getReverseAcceptFinishInfo(lastTime);
    }

    /**
     * 根据订单和网单号查询队列表中匹配的数据总数
     *
     * @param type
     * @param orderProductId
     * @return
     */
    @Override
    public int getCountOcqByTypeAndOrderProductId(Integer type,
                                                  Integer orderProductId) {
        return accountCenterReadDao.getCountOcqByTypeAndOrderProductId(type, orderProductId);
    }

    @Override
    public int getCountOcqByfinishColseQueues() {
        return accountCenterReadDao.getCountOcqByfinishColseQueues();

    }

    @Override
    public int getCountOcqCancelStatusOrderQueues() {
        return accountCenterReadDao.getCountOcqCancelStatusOrderQueues();
    }

    /**
     * 获取受理完成队列和取消关闭关联后的数据
     *
     * @param unSendQueryNum
     * @param sendCount
     * @return
     */
    @Override
    public List<O2oOrderCloseQueuesExt> getUnSendOrderRepairsAndCancel(Integer unSendQueryNum,
                                                                       Integer sendCount) {
        return accountCenterReadDao.getUnSendOrderRepairsAndCancel(unSendQueryNum, sendCount);
    }

    /**
     * 更新订单完成关闭队列
     *
     * @param o2oOrderCloseQueuesExt
     * @return
     */
    @Override
    public int updateOrderCloseQueue(O2oOrderCloseQueuesExt o2oOrderCloseQueuesExt) {
        return accountCenterWriteDao.updateOrderCloseQueue(o2oOrderCloseQueuesExt);
    }

    /**
     * 网单号ID DepositOrderProductId 获取 o2o已付尾款订单队列表
     */
    @Override
    public O2OOrderTailendQueues getTailendToAccountCenterByDepositOrderProductId(Integer depositOrderProductId){
        return accountCenterReadDao.getTailendToAccountCenterByDepositOrderProductId(depositOrderProductId);
    }

    /**
     * 插入 o2o已付尾款队列表
     */
    @Override
    public int insertTailendToAccountCenterList(O2OOrderTailendQueues o2oOrderTailendQueues){
        return accountCenterWriteDao.insertTailendToAccountCenterList(o2oOrderTailendQueues);
    }
}
