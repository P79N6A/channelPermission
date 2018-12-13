package com.haier.shop.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.haier.common.PagerInfo;
import com.haier.shop.dao.shopread.OrderWorkflowRegionReadDao;
import com.haier.shop.dao.shopread.OrderWorkflowsReadDao;
import com.haier.shop.dao.shopwrite.OrderWorkflowsWriteDao;

import com.haier.shop.model.OrderWorkflowRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.OrderWorkflows;
import com.haier.shop.service.ShopOrderWorkflowsService;

@Service
public class ShopOrderWorkflowsServiceImpl implements ShopOrderWorkflowsService {
    @Autowired
     private OrderWorkflowsWriteDao orderWorkflowsWriteDao;

    @Autowired
    private OrderWorkflowsReadDao orderWorkflowsReadDao;

    @Autowired
    OrderWorkflowRegionReadDao orderWorkflowRegionReadDao;
    /**
     * 根据主键，获取订单全流程监控信息
     * @param id
     * @return
     */
    public OrderWorkflows get(Integer id){
        return orderWorkflowsReadDao.get(id);
    }

    /**
     * 根据网单id，获取订单全流程监控信息
     * @param orderProductId 网单id
     * @return
     */
    @Override
    public OrderWorkflows getByOrderProductId(Integer orderProductId){
        return orderWorkflowsReadDao.getByOrderProductId(orderProductId);
    }

    /**
     * 查询大于id的全流程数据集合
     * @param id
     * @return
     */
    public   List<OrderWorkflows> queryById(Integer id){
        return orderWorkflowsReadDao.queryById(id);
    }
    /**
     * LES出库后，更新LES出库时间
     * @param orderWorkflow
     * @return
     */
    public   Integer updateAfterLesShipped(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateAfterLesShipped(orderWorkflow);
    }

    /**
     * 菜鸟出库后，更新LES出库时间和开提单时间－3W
     * @param orderWorkflow
     * @return
     */
    public   Integer updateAfterCnShipped(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateAfterCnShipped(orderWorkflow);
    }

    /**
     * 付款后更新付款时间，以订金付款时间为准
     * @param orderWorkflow
     * @return
     */
    public  Integer updateAfterPayment(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateAfterPayment(orderWorkflow);
    }
    /**
     * 更新订单应确认时间、订单预警时间
     * @param orderWorkflow
     * @return
     */

    public Integer updateTime(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateTime(orderWorkflow);
    }

    /**
     * 更新到达网点时间
     * @param id
     * @param netPointArriveTime
     * @return
     */

    public  Integer updateNetPointArriveTime(Integer id, Long netPointArriveTime){
        return orderWorkflowsWriteDao.updateNetPointArriveTime(id, netPointArriveTime);
    }

    /**
     * 更新网点出库时间
     * @param id
     * @param netPointShipTime
     * @return
     */

    public Integer updateNetPointShipTime(Integer id, Long netPointShipTime){
        return orderWorkflowsWriteDao.updateNetPointShipTime(id,netPointShipTime);
    }

    /**
     * 更新网点接单时间
     * @param id
     * @param netPointAcceptTime
     * @return
     */
    public   Integer updateNetPointAcceptTime(Integer id, Long netPointAcceptTime){
        return orderWorkflowsWriteDao.updateNetPointAcceptTime(id,netPointAcceptTime);
    }
    /**
     * 更新用户签收时间
     * @param id
     * @param userAcceptTime
     * @return
     */

    public  Integer updateUserAcceptTime(Integer id, Long userAcceptTime){
        return orderWorkflowsWriteDao.updateUserAcceptTime(id, userAcceptTime);
    }

    /**
     * 更新完成关闭时间
     * @param id
     * @param finishTime
     * @return
     */

    public   Integer updateFinishTime(Integer id,Long finishTime){
        return orderWorkflowsWriteDao.updateFinishTime(id, finishTime);
    }

    /**
     * 获取待订单确认的网单列表信息
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getConfirmList(){
        return orderWorkflowsReadDao.getConfirmList();
    }

    /**
     * 获取待HP派工的网单列表信息
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getHpList(){
        return orderWorkflowsReadDao.getHpList();
    }

    /**
     * 获取待送达网点的网单列表信息
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getNetpointList(){
        return orderWorkflowsReadDao.getNetpointList();
    }

    /**
     * 获取待送达用户的网单列表信息
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getUserList(){
        return orderWorkflowsReadDao.getUserList();
    }

    /**
     * 获取用户已申请退款的网单列表信息
     * @return 网单列表集合
     */
    public   List<Map<String, Object>> getRepairList(){
        return orderWorkflowsReadDao.getRepairList();
    }

    /**
     * 获取hp作废工单次数
     * @return 工单次数
     */
    public Integer getHpCancelByOrderProductId(Long orderProductId){
        return orderWorkflowsReadDao.getHpCancelByOrderProductId(orderProductId);
    }

    /**
     * 获取及时率报表数据
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getOntimeRate(Map<String, Object> paramMap){
        return orderWorkflowsReadDao.getOntimeRate(paramMap);
    }

    /**
     * 获取用户已申请退款的网单列表信息
     * @param startDate 起始日期
     * @param endDate 截止日期
     * @return 网单列表集合
     */
    public   List<Map<String, Object>> getRepairListByDate(Integer startDate,
                                                  Integer endDate){
        return orderWorkflowsReadDao.getRepairListByDate(startDate,endDate);
    }

    /**
     * 获取区县
     * @param parentId 上级id
     * @return 区县列表
     */
    public  List<Map<String, String>> getRegions(Integer parentId){
        return orderWorkflowsReadDao.getRegions(parentId);
    }

    /**
     * 获取库位
     * @return 库位列表
     */
    public  List<Map<String, String>> getStorages(){
        return orderWorkflowsReadDao.getStorages();
    }

    /**
     * 获取及时率网单明细总数
     * @param paramMap cOrderSn网单号数组,pager分页信息
     * @return
     */
    public  Integer getOntimeRateDetailCount(Map<String, Object> paramMap){
        return orderWorkflowsReadDao.getOntimeRateDetailCount(paramMap);
    }

    /**
     * 获取及时率网单明细列表
     * @param paramMap cOrderSn网单号数组,pager分页信息
     * @return
     */
    public  List<Map<String, Object>> getOntimeRateDetail(Map<String, Object> paramMap){
        return orderWorkflowsReadDao.getOntimeRateDetail(paramMap);
    }
    /**
     * 根据区id查询配送时效
     * @param id 区id
     * @return
     */
    public  Integer getShippingTimeByRegionId(Integer id){
        return orderWorkflowsReadDao.getShippingTimeByRegionId(id);
    }

    /**
     * 获取联系人手机信息
     * @return
     */
    public  List<Map<String, Object>> getTelephoneInfo(String smsPoint, String smsDeptName){
        return orderWorkflowsReadDao.getTelephoneInfo(smsPoint, smsDeptName);
    }

    /**
     * 获取工贸信息
     * @return
     */
    public  List<Map<String, Object>> getTradeInfo(){
        return orderWorkflowsReadDao.getTradeInfo();
    }

    /**
     * 获取中心信息
     * @return
     */
    public  List<Map<String, Object>> getCenterInfo(){
        return orderWorkflowsReadDao.getCenterInfo();
    }

    /**
     * 获取配送时效信息
     * @return
     */
    public  List<Map<String, Object>> getShippingTimeInfo(){
        return orderWorkflowsReadDao.getShippingTimeInfo();
    }


    public int insert(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.insert(orderWorkflow);
    }

    /**
     * 网单开提单后更新全流程的部分字段信息
     * @return
     */

    public   Integer updateAfterSyncOrderToLes(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateAfterSyncOrderToLes(orderWorkflow);
    }

    /**
     * 确认订单更新
     * @param orderWorkflow
     * @return
     */

    public int updateForConformOrder(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateForConformOrder(orderWorkflow);
    }

    /**
     * 公共计算更新全流程的时间
     * @return
     */

    public int updateForPubCountTime(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateForPubCountTime(orderWorkflow);
    }

    /**
     * 获取全流程信息
     * @return
     */
    public  List<OrderWorkflows> getListByOrderSn(String orderSn){
        return orderWorkflowsReadDao.getListByOrderSn(orderSn);
    }

    /**
     *  取消订单更新
     * @param orderWorkflow
     * @return
     */

    public  int updateForCancelOrder(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateForCancelOrder(orderWorkflow);
    }

    /**
     * 更新超时免单
     * @param
     * @return
     */

    public   Integer updateIsTimeoutFree(Integer orderProductId, Integer isTimeoutFree){
        return orderWorkflowsWriteDao.updateIsTimeoutFree(orderProductId, isTimeoutFree); }

    /**
     * 获取及时率报表逆向数据列表
     */
    public List<Map<String, Object>> getOntimeRateReverseList(Map<String, Object> paramMap)
            throws ParseException {
        String nodeType = (String) paramMap.get("nodeType");
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(new Date(dateToInteger((String) paramMap.get("startDate")) * 1000L));
        endCalendar.setTime(new Date(dateToInteger((String) paramMap.get("endDate")) * 1000L));
        if ("audit".equals(nodeType) || "invoice".equals(nodeType)) {
            startCalendar.add(Calendar.DATE, -1);
            endCalendar.add(Calendar.DATE, -1);
        } else if ("hp".equals(nodeType)) {
            startCalendar.add(Calendar.DATE, -2);
            endCalendar.add(Calendar.DATE, -2);
        } else if ("les".equals(nodeType)) {
            startCalendar.add(Calendar.DATE, -7);
            endCalendar.add(Calendar.DATE, -7);
        } else if ("orderclose".equals(nodeType)) {
            startCalendar.add(Calendar.DATE, -11);
            endCalendar.add(Calendar.DATE, -11);
        } else if ("refund".equals(nodeType)) {
            startCalendar.add(Calendar.DATE, -3);
            endCalendar.add(Calendar.DATE, -3);
        }
        paramMap.put("startDate", startCalendar.getTimeInMillis() / 1000);
        paramMap.put("endDate", endCalendar.getTimeInMillis() / 1000 + 23 * 60 * 60 + 59 * 60 + 59);
        List<Map<String, Object>> resultList = orderWorkflowsReadDao.getOntimeRateReverse(paramMap);
        //多次申请退换货以最后一次考核
        Map<Object, Object> tempMap = new HashMap<Object, Object>();
        List<Long> tempList = new ArrayList<Long>();
        for (Map<String, Object> map : resultList) {
            if (tempMap.get(map.get("orderProductId")) == null) {
                tempMap.put(map.get("orderProductId"), map.get("id"));
            } else if ((Long) tempMap.get(map.get("orderProductId")) < (Long) map.get("id")) {
                tempList.add((Long) tempMap.get(map.get("orderProductId")));
                tempMap.put(map.get("orderProductId"), map.get("id"));
            }
        }
        Iterator<Map<String, Object>> it = resultList.iterator();
        while (it.hasNext()) {
            Map<String, Object> map = it.next();
            for (Long id : tempList) {
                if (id == (Long) map.get("id"))
                    it.remove();
            }
        }
        return resultList;
    }


    /**
     * 获取及时率报表逆向数据列表
     */
    public List<Map<String, Object>> getOntimeRateReverseListNew(Map<String, Object> paramMap,Boolean need) throws ParseException {
        String nodeType = (String) paramMap.get("nodeType");
        if (need) {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            startCalendar
                    .setTime(new Date(dateToInteger((String) paramMap.get("startDate")) * 1000L));
            endCalendar.setTime(new Date(dateToInteger((String) paramMap.get("endDate")) * 1000L));
            if ("audit".equals(nodeType) || "invoice".equals(nodeType)) {
                startCalendar.add(Calendar.DATE, -1);
                endCalendar.add(Calendar.DATE, -1);
            } else if ("hp".equals(nodeType)) {
                startCalendar.add(Calendar.DATE, -2);
                endCalendar.add(Calendar.DATE, -2);
            } else if ("les".equals(nodeType)) {
                startCalendar.add(Calendar.DATE, -7);
                endCalendar.add(Calendar.DATE, -7);
            } else if ("orderclose".equals(nodeType)) {
                startCalendar.add(Calendar.DATE, -9);
                endCalendar.add(Calendar.DATE, -9);
            } else if ("refund".equals(nodeType)) {
                startCalendar.add(Calendar.DATE, -3);
                endCalendar.add(Calendar.DATE, -3);
            } else if ("blp".equals(nodeType)) {//不良品
                startCalendar.add(Calendar.DATE, -15);
                endCalendar.add(Calendar.DATE, -15);
            }
            //22库及时率增加
            else if ("22storehouse".equals(nodeType)) {
                //22库及时率
                startCalendar.add(Calendar.DATE, -22);
                endCalendar.add(Calendar.DATE, -22);
            } else if ("recheckquality".equals(nodeType)) {
                //二次质检
                startCalendar.add(Calendar.DATE, -5);
                endCalendar.add(Calendar.DATE, -5);
            } else if ("transmitbox".equals(nodeType)) {
                //转箱
                startCalendar.add(Calendar.DATE, -15);
                endCalendar.add(Calendar.DATE, -15);
            } else if ("transmitstock".equals(nodeType)) {
                //转库
                startCalendar.add(Calendar.DATE, -2);
                endCalendar.add(Calendar.DATE, -2);
            }
            paramMap.put("startDate", startCalendar.getTimeInMillis() / 1000);
            paramMap.put("endDate",
                    endCalendar.getTimeInMillis() / 1000 + 23 * 60 * 60 + 59 * 60 + 59);
        }

        List<Map<String, Object>> resultList = null;
        if ("blp".equals(nodeType)) {//不良品
            resultList = orderWorkflowsReadDao.getOntimeRateReverseBlp(paramMap);
        }else if ("hp".equals(nodeType)) {
            resultList = orderWorkflowsReadDao.getOntimeRateReverseHp(paramMap);
            if (resultList != null && resultList.size() > 1) {
                deleteMuli(resultList, "hpId");
            }
        }else if ("orderclose".equals(nodeType)) {
            resultList = orderWorkflowsReadDao.getOntimeRateReverseOrderclose(paramMap);
            if (resultList != null && resultList.size() > 1) {
                deleteMuli(resultList, "hpId");
            }
        }
        return resultList;
    }

    private void deleteMuli(List<Map<String, Object>> resultList, String subId) {
        int beforeMainId = 0;
        int beforeSubId = 0;
        int nowMainId = 0;
        int nowSubId = 0;

        Iterator<Map<String, Object>> it = resultList.iterator();
        while (it.hasNext()) {
            Map<String, Object> map = it.next();
            nowMainId = Integer.parseInt(map.get("id").toString());
            nowSubId = map.get(subId) == null ? 0 : Integer.parseInt(map.get(subId).toString());
            if (beforeMainId == nowMainId) {
                if (nowSubId < beforeSubId) {
                    it.remove();
                }
            }
            beforeMainId = nowMainId;
            beforeSubId = nowSubId;
        }

    }
    /**
     * 根据网单号获取及时率报表逆向数据列表
     */
    @Override
    public List<Map<String, Object>> getOntimeRateReverseListByOrderSn(Map<String, Object> paramMap)
            throws Exception {
        List<Map<String, Object>> resultList = orderWorkflowsReadDao
                .getOntimeRateReverseByOrderSn(paramMap);
        //多次申请退换货以最后一次考核
        Map<Object, Object> tempMap = new HashMap<Object, Object>();
        List<Long> tempList = new ArrayList<Long>();
        for (Map<String, Object> map : resultList) {
            if (tempMap.get(map.get("orderProductId")) == null) {
                tempMap.put(map.get("orderProductId"), map.get("id"));
            } else if ((Long) tempMap.get(map.get("orderProductId")) < (Long) map.get("id")) {
                tempList.add((Long) tempMap.get(map.get("orderProductId")));
                tempMap.put(map.get("orderProductId"), map.get("id"));
            }
        }
        Iterator<Map<String, Object>> it = resultList.iterator();
        while (it.hasNext()) {
            Map<String, Object> map = it.next();
            for (Long id : tempList) {
                if (id == (Long) map.get("id"))
                    it.remove();
            }
        }
        return resultList;
    }
    @Override
    public Integer getOntimeRateReverseDetailCount(Map<String, Object> paramMap){
        return orderWorkflowsReadDao.getOntimeRateReverseDetailCount(paramMap);
    }

    @Override
    public Integer getOntimeRateReverseDetailCountNew(Map<String, Object> paramMap){
        return orderWorkflowsReadDao.getOntimeRateReverseDetailCountNew(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseDetail(Map<String, Object> paramMap){
        return  orderWorkflowsReadDao.getOntimeRateReverseDetail(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseDetailNew(Map<String, Object> paramMap){
        return  orderWorkflowsReadDao.getOntimeRateReverseDetailNew(paramMap);
    }

    @Override
    public List<OrderWorkflowRegion> getOwfRegion() {
        return orderWorkflowRegionReadDao.getOwfRegion();
    }

    //--------------------------------------------private-------------------------------------------
    private Integer dateToInteger(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = format.parse(str);
        return ((Long) (date.getTime() / 1000)).intValue();
    }
}
