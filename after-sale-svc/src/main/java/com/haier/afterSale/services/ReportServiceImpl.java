package com.haier.afterSale.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.haier.afterSale.service.ICountTimeOut;
import com.haier.afterSale.service.IReportService;
import com.haier.afterSale.util.ArraySplitUtil;
import com.haier.afterSale.util.NumberParseUtil;
import com.haier.afterSale.util.ReportCountTimeOutFactoy;
import com.haier.common.ServiceResult;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.OrdWfKpiData;
import com.haier.shop.model.OrderWorkflowRegion;
import com.haier.shop.service.OrderWorkFlowModelDataService;
import com.haier.shop.service.ReportModelDataService;
import com.haier.afterSale.service.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements IReportService {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(ReportServiceImpl.class);


    @Autowired
    private ReportModelDataService reportModelDataService;
    @Autowired
    private OrderWorkFlowModelDataService orderWorkFlowModelDataService;
    @Autowired
    private Kpilists kpilists;

    @Override
    public Map<String, List<Map<String, Object>>> queryKpiReportDatas(Map<String, String> params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServiceResult<Boolean> catchKpiReportDetail() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        boolean isAlreadyRun = true;
        try {
            //kpi报表日期
            Date date = this.getDate();
            //删除本日kpi报表数据,用于定时任务重新跑数据
            reportModelDataService.deleteKpiDatasByDate(date);
            Long count = reportModelDataService.getCount();
            if (null == count || count == 0) {
                isAlreadyRun = false;
            }
            //应完成开始时间Calendar
            Calendar startCal = this.getStartCalendar(isAlreadyRun);
            //应完成开始时间Calendar
            Calendar endCal = this.getEndCalendar();
            //开始日期
            long startDateTime = this.getStartDateTime(isAlreadyRun);

            //开始日期逆向
            long startDateTimeReverse = this.getStartDateTimeReverse(isAlreadyRun);

            //结束日期
            long endDateTime = this.getEndDateTime(endCal);
            //区县id和区域、工贸对应关系map
            Map<Integer, OrderWorkflowRegion> regionMap = orderWorkFlowModelDataService
                .getRegionMap();
            //库位编码和名称对应关系map
            Map<String, String> storageMap = orderWorkFlowModelDataService.getStorageName();
            //查询大家电多级库位关系列表
            Map<String, List<BigStoragesRela>> codeBigStoragesRelaMap = orderWorkFlowModelDataService.getCodeBigStoragesRelaMap();
            //今日处理的类型--id集合(正向网单id,逆向退换货单id),为了剔除滚动的差重复数据
            Map<String, Set<Long>> typeIdSetMap = new HashMap<String, Set<Long>>();
            //处理逆向kpi报表
            this.handlerReverseOntime(startDateTimeReverse, endDateTime, date, regionMap,
                storageMap, startCal, endCal, codeBigStoragesRelaMap, typeIdSetMap);
            //如果已经运行过,取昨日的未完成的差,进行处理
            if (isAlreadyRun) {
                this.handleNotFinishData(date, regionMap, storageMap, codeBigStoragesRelaMap,
                    endCal, typeIdSetMap);
            }
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("kpi报表统计数据出现错误" + e.getMessage());
            log.error("kpi报表统计数据出现错误", e);
        }
        return result;
    }

    private Calendar getStartCalendar(boolean isAlreadyRun) {
        if (isAlreadyRun) {
            Calendar startDate = Calendar.getInstance();
            //由于今日0点跑的是昨天数据,所以还减去1天
            DateUtils.addDays(startDate, -1);
            DateUtils.setStartDay(startDate);
            return startDate;
        } else {
            //还没运行过,开始日期取2018-07-01
            Calendar startDate = Calendar.getInstance();
            DateUtils.setStartYear(startDate);
            return startDate;
        }
    }

    private Calendar getEndCalendar() {
        Calendar endDate = Calendar.getInstance();
        DateUtils.addDays(endDate, -1);
        DateUtils.setEndDay(endDate);
        return endDate;
    }

    private Date getDate() {
        Calendar dateCal = Calendar.getInstance();
        DateUtils.addDays(dateCal, -1);
        DateUtils.setStartDay(dateCal);
        return dateCal.getTime();
    }

    private long getEndDateTime(Calendar endDate) {
        return endDate.getTimeInMillis() / 1000l;
    }

    private long getStartDateTime(boolean isAlreadyRun) {
        if (isAlreadyRun) {
            Calendar startDate = Calendar.getInstance();
            //减去21天,由于今日0点跑的是昨天数据,所以还再减去1天,就是22天
            DateUtils.addDays(startDate, -22);
            DateUtils.setStartDay(startDate);
            return startDate.getTimeInMillis() / 1000l;
        } else {
            //还没运行过,开始日期取2018-7-1
            Calendar startDate = Calendar.getInstance();
            DateUtils.setStartYear(startDate);
            return startDate.getTimeInMillis() / 1000l;
        }
    }

    private long getStartDateTimeReverse(boolean isAlreadyRun) {
        if (isAlreadyRun) {
            Calendar startDate = Calendar.getInstance();
            //减去21天,由于今日0点跑的是昨天数据,所以还再减去1天,就是22天
            DateUtils.addDays(startDate, -41);
            DateUtils.setStartDay(startDate);
            return startDate.getTimeInMillis() / 1000l;
        } else {
            //还没运行过,开始日期取2018-07-01
            Calendar startDate = Calendar.getInstance();
            DateUtils.setStartYear(startDate);
            return startDate.getTimeInMillis() / 1000l;
        }
    }

    /**
     *
     * @param startDateTime  如果有数据41天前时间戳，如果没数据当前年1月1日时间戳
     * @param endDateTime     前一天的时间戳
     * @param date             当前前一天日期时间
     * @param regionMap       区县id和区域、工贸对应关系map
     * @param storageMap        库位编码和名称对应关系map
     * @param startCal          如果有数据1天前时间，没有就取当前年1月1日时间
     * @param endCal            前一天的时间
     * @param codeBigStoragesRelaMap    查询大家电多级库位关系列表
     * @param typeIdSetMap              记录数据集合
     */
    private void handlerReverseOntime(long startDateTime, long endDateTime, Date date,
                                      Map<Integer, OrderWorkflowRegion> regionMap,
                                      Map<String, String> storageMap, Calendar startCal,
                                      Calendar endCal,
                                      Map<String, List<BigStoragesRela>> codeBigStoragesRelaMap,
                                      Map<String, Set<Long>> typeIdSetMap) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", startDateTime);
        paramMap.put("endDate", endDateTime);
        List<Long> orsIdList = reportModelDataService.getOntimeRateReverseOrsIds(paramMap);
        if (null == orsIdList || orsIdList.isEmpty()) {
            return;
        }
        //将orsId按500分组,因为逆向管理les存在重复,所以减少分组大小
        List<List<Long>> orsIdListArray = new ArraySplitUtil<Long>().splistList(orsIdList, 500);
        //取出开始和结束orsId,用于查询条件(id范围)
        List<Long[]> startEndOrsIdList = new ArrayList<Long[]>();
        Long[] startEndOrsId = null;
        for (List<Long> list : orsIdListArray) {
            startEndOrsId = new Long[2];
            startEndOrsId[0] = list.get(0);
            startEndOrsId[1] = list.get(list.size() - 1);
            startEndOrsIdList.add(startEndOrsId);
        }

        List<Map<String, Object>> reverseDataList = null;
        List<OrdWfKpiData> kpiDatas = null;
        for (Long[] startEndOrsIds : startEndOrsIdList) {
            paramMap.put("startOrsId", startEndOrsIds[0]);
            paramMap.put("endOrsId", startEndOrsIds[1]);
            reverseDataList = reportModelDataService.getOntimeRateReverse(paramMap);
            if (null == reverseDataList || reverseDataList.isEmpty()) {
                continue;
            }
            this.countReverseTimeOut(reverseDataList, codeBigStoragesRelaMap);
            for (String type :kpilists.kpiReverseTypeList) {
                kpiDatas = this.constractKpiDatas(reverseDataList, type, date,
                        regionMap, storageMap, startCal, endCal, typeIdSetMap, true);
                reportModelDataService.insertKpiDatas(kpiDatas, type);
            }
        }
    }

    /**
     *
     * @param date  当前前一天时间日期
     * @param regionMap     区县id和区域、工贸对应关系map
     * @param storageMap    库位编码和名称对应关系map
     * @param codeBigStoragesRelaMap    查询大家电多级库位关系列表
     * @param endCal    当前前一天时间
     * @param typeIdSetMap  记录数据的map空集合
     */

    private void handleNotFinishData(Date date, Map<Integer, OrderWorkflowRegion> regionMap,
                                     Map<String, String> storageMap,
                                     Map<String, List<BigStoragesRela>> codeBigStoragesRelaMap,
                                     Calendar endCal, Map<String, Set<Long>> typeIdSetMap) {
        //取昨日日期,今日减去1天,所以昨日的减2天
        Calendar cal = Calendar.getInstance();
        DateUtils.addDays(cal, -2);
        DateUtils.setStartDay(cal);
        //获取kpi报表昨日未完成已超期数据
        List<Map<String, Object>> notFinishList = reportModelDataService.getNotFinishData(
            cal.getTime());
        if (null == notFinishList || notFinishList.isEmpty()) {
            return;
        }
        Map<String, Set<Long>> typeIdsMap = new HashMap<String, Set<Long>>();
        Map<String, Set<Long>> reverseTypeIdsMap = new HashMap<String, Set<Long>>();
        Set<Long> orderProductIds = new HashSet<Long>();
        Set<Long> reverseOrderProductIds = new HashSet<Long>();
        String nodeType = null;
        Long orderProductId = null;
        Long orsId = null;
        Set<Long> idList = null;
        for (Map<String, Object> map : notFinishList) {
            orderProductId = NumberParseUtil.parseLong(map.get("order_product_id"));
            orsId = NumberParseUtil.parseLong(map.get("ors_id"));
            nodeType = (String) map.get("node_type");
            if (Kpilists.kpiTypeList.contains(nodeType)) {
                /*if (orderProductId < 1 || (typeIdSetMap.containsKey(nodeType) && typeIdSetMap.get(nodeType).contains(orderProductId))) {
                    continue;
                }
                if (!typeIdsMap.containsKey(nodeType)) {
                    idList = new HashSet<Long>();
                    typeIdsMap.put(nodeType, idList);
                } else {
                    idList = typeIdsMap.get(nodeType);
                }
                idList.add(orderProductId);
                orderProductIds.add(orderProductId);*/
                continue;
            } else {
                if (orsId < 1 || (typeIdSetMap.containsKey(nodeType) && typeIdSetMap.get(nodeType).contains(orsId))) {
                    continue;
                }
                if (!reverseTypeIdsMap.containsKey(nodeType)) {
                    idList = new HashSet<Long>();
                    reverseTypeIdsMap.put(nodeType, idList);
                } else {
                    idList = reverseTypeIdsMap.get(nodeType);
                }
                idList.add(orsId);
                reverseOrderProductIds.add(orsId);
            }
        }
        /*//处理正向昨日未完成超期
        this.handlerOntimeByNotFinishData(orderProductIds, typeIdsMap, date, regionMap, storageMap,
            false, codeBigStoragesRelaMap, endCal);*/
        //处理逆向昨日未完成超期
        this.handlerOntimeByNotFinishData(reverseOrderProductIds, reverseTypeIdsMap, date,
            regionMap, storageMap, true, codeBigStoragesRelaMap, endCal);
    }

    /**
     *
     * @param orderProductIds  逆向存储id集合
     * @param typeIdsMap       逆向类型标识集合
     * @param date              当前前一天时间日期
     * @param regionMap        区县id和区域、工贸对应关系map
     * @param storageMap       库位编码和名称对应关系map
     * @param isReverse        true
     * @param codeBigStoragesRelaMap    查询大家电多级库位关系列表
     * @param endCal           当前前一天时间
     */
    private void handlerOntimeByNotFinishData(Set<Long> orderProductIds,
                                              Map<String, Set<Long>> typeIdsMap,
                                              Date date,
                                              Map<Integer, OrderWorkflowRegion> regionMap,
                                              Map<String, String> storageMap,
                                              boolean isReverse,
                                              Map<String, List<BigStoragesRela>> codeBigStoragesRelaMap,
                                              Calendar endCal) {
        if (null == orderProductIds || orderProductIds.isEmpty()) {
            return;
        }
        int inSize = 100;
        Long[] opIdArray = orderProductIds.toArray(new Long[0]);
        List<Long[]> idArrayList = new ArraySplitUtil<Long>().splistArray(opIdArray, inSize);
        List<Map<String, Object>> dataList = null;
        Map<Long, Map<String, Object>> idDataMap = null;
        String nodeTpe = null;
        Set<Long> nodeOpIdList = null;
        List<Map<String, Object>> nodeDataList = null;
        List<OrdWfKpiData> kpiDatas = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, Object> data = null;
        for (Long[] opIds : idArrayList) {
            if (!isReverse) {
                paramMap.put("orderProductIds", opIds);
                dataList = reportModelDataService.getOntimeRateByOrderProductIds(paramMap);
            } else {
                paramMap.put("orsIds", opIds);
                dataList = reportModelDataService.getOntimeRateReverseByOrsIds(paramMap);
            }
            if (null == dataList || dataList.isEmpty()) {
                continue;
            }
            idDataMap = new HashMap<Long, Map<String, Object>>();

            //22库专用，因为相同的退换货ID下会有入22，出22，入10等各种情况，单纯去重会覆盖掉需要的数据
            Map<Long, Map<String, Object>> idDataMapForStore22 = new HashMap<Long, Map<String, Object>>();

            for (Map<String, Object> dataMap : dataList) {
                if (!isReverse) {
                    idDataMap.put(NumberParseUtil.parseLong(dataMap.get("orderProductId")), dataMap);
                } else {
                    idDataMap.put(NumberParseUtil.parseLong(dataMap.get("id")), dataMap);

                    if (idDataMapForStore22.containsKey(NumberParseUtil.parseLong(dataMap.get("id")))) {
                        idDataMapForStore22.get(NumberParseUtil.parseLong(dataMap.get("id"))).put(
                            NumberParseUtil.parseLong(dataMap.get("id")) + "@"
                                    + NumberParseUtil.parseLong(dataMap.get("orderProductId"))
                                    + "@" + dataMap.get("operate") + "@"
                                    + dataMap.get("storageType"), dataMap);
                    } else {
                        Map<String, Object> temp = new HashMap<String, Object>();
                        temp.put(NumberParseUtil.parseLong(dataMap.get("id")) + "@"
                                 + NumberParseUtil.parseLong(dataMap.get("orderProductId")) + "@"
                                 + dataMap.get("operate") + "@" + dataMap.get("storageType"),
                            dataMap);
                        idDataMapForStore22.put(NumberParseUtil.parseLong(dataMap.get("id")), temp);
                    }

                }
            }
            for (Entry<String, Set<Long>> entry : typeIdsMap.entrySet()) {
                nodeTpe = entry.getKey();
                nodeOpIdList = entry.getValue();
                nodeDataList = new ArrayList<Map<String, Object>>();
                for (Long id : nodeOpIdList) {
                    if (ReportType.STORE22.equals(nodeTpe)) {
                        data = idDataMapForStore22.get(id);
                        if (null == data) {
                            continue;
                        }
                        for (Entry<String, Object> entry2 : data.entrySet()) {
                            nodeDataList.add((Map<String, Object>) entry2.getValue());
                            this.addShippingTimeHour(codeBigStoragesRelaMap,
                                (Map<String, Object>) entry2.getValue());
                        }
                    } else {
                        data = idDataMap.get(id);
                        if (null == data) {
                            continue;
                        }
                        nodeDataList.add(data);
                        this.addShippingTimeHour(codeBigStoragesRelaMap, data);
                    }

                }
                ReportCountTimeOutFactoy.getInstance().countTimeOut(nodeDataList, nodeTpe, false);
                kpiDatas = this.constractKpiDatas(nodeDataList, nodeTpe, date,
                    regionMap, storageMap, null, endCal, null, isReverse);
                reportModelDataService.insertKpiDatas(kpiDatas, nodeTpe);
            }
        }
    }

    /**
     * 计算逆向kpi报表超时数据
     * @param dataList  经开始结束id查询出的报表数据
     * @param codeBigStoragesRelaMap    查询大家电多级库位关系列表
     */
    public void countReverseTimeOut(List<Map<String, Object>> dataList, Map<String, List<BigStoragesRela>> codeBigStoragesRelaMap) {
        //添加转运时效
        for (Map<String, Object> map : dataList) {
            this.addShippingTimeHour(codeBigStoragesRelaMap, map);
        }
        ReportCountTimeOutFactoy.getInstance().countTimeOut(dataList,new ArrayList<String>(Kpilists.kpiReverseTypeList), false);
    }
    /**
     * 添加转运时效
     * @param codeBigStoragesRelaMap 查询大家电多级库位关系列表
     * @param map   通过开始结束id查询的报表信息
     */
    public void addShippingTimeHour(Map<String, List<BigStoragesRela>> codeBigStoragesRelaMap,
                                    Map<String, Object> map) {
        List<BigStoragesRela> bigStoragesRelaList = codeBigStoragesRelaMap.get(map.get("tsCode"));
        Integer shippingTimeHour = 0;
        if (null != bigStoragesRelaList && !bigStoragesRelaList.isEmpty()) {
            for (BigStoragesRela bigStoragesRela : bigStoragesRelaList) {
                if (map.get("sCode").equals(bigStoragesRela.getMasterCode())) {
                    shippingTimeHour = bigStoragesRela.getMasterShippingTime();
                    break;
                } else if (map.get("sCode").equals(bigStoragesRela.getCenterCode())) {
                    shippingTimeHour = bigStoragesRela.getCenterShippingTime();
                    break;
                }
            }
        }
        map.put("shippingTimeHour", shippingTimeHour);
    }


    /**
     * 组织kpi逆向数据
     *
     * @param dataList  通过业务逻辑计算得来的map数据
     * @param type       逆向kpi报表类型
     * @param date       前一天的时间
     * @param regionMap     区县id和区域、工贸对应关系map
     * @param storageMap    库位编码和名称对应关系map
     * @param startCal       如果有数据1天前时间，没有就取当前18.7.1时间
     * @param endCal         前一天的时间
     * @param typeIdSetMap      记录数据集合空
     * @param isReverse         true
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<OrdWfKpiData> constractKpiDatas(List<Map<String, Object>> dataList, String type,
                                                Date date,
                                                Map<Integer, OrderWorkflowRegion> regionMap,
                                                Map<String, String> storageMap, Calendar startCal,
                                                Calendar endCal,
                                                Map<String, Set<Long>> typeIdSetMap,
                                                boolean isReverse) {
        List<OrdWfKpiData> kpiDatas = new ArrayList<OrdWfKpiData>();
        if (null == dataList || dataList.isEmpty()) {
            return kpiDatas;
        }
        OrdWfKpiData ordWfKpiData = null;
        Map<String, Object> timeOutMap = null;
        OrderWorkflowRegion orderWorkflowRegion = null;
        Calendar mustDate = null;
        Calendar realDate = null;
        Integer timeoutType = null;
        String storageName = null;
        //id集合,正向网单id,逆向退换货单id
        Set<Long> idSet = null;
        for (Map<String, Object> map : dataList) {
            if (!map.containsKey(type)) {
                continue;
            }
            timeOutMap = (Map<String, Object>) map.get(type);
            mustDate = (Calendar) timeOutMap.get("mustDate");
            realDate = (Calendar) timeOutMap.get("realDate");
            timeoutType = NumberParseUtil.parseInteger(timeOutMap.get("timeoutType"));
            if (null != startCal && null != endCal) {
                //应完成时间为空,或者小于开始时间,或者大于结束时间,剔除掉
                if (null == mustDate || mustDate.before(startCal) || mustDate.after(endCal)) {
                    continue;
                }
            } else if (null == startCal && null != endCal) {
                //差滚动,要剔除应完成时间在今日23:59:59之后的数据
                if (null == mustDate || mustDate.after(endCal)) {
                    continue;
                }
            }
            orderWorkflowRegion = regionMap.get(NumberParseUtil.parseInteger(map.get("region")));
            ordWfKpiData = new OrdWfKpiData();
            ordWfKpiData.setMustFinishTime(mustDate.getTime());
            if (null != realDate) {
                ordWfKpiData.setRealFinishTime(realDate.getTime());
            }
            ordWfKpiData.setTimeoutType(timeoutType);
            ordWfKpiData.setTimeoutDay(NumberParseUtil.parseInteger(timeOutMap.get("outDay")));
            if (timeoutType == ICountTimeOut.NOT_FINISH_TIMEOUT
                    || timeoutType == ICountTimeOut.NOT_FINISH_NOT_TIMEOUT) {
                ordWfKpiData.setIsFinish(0);
            } else {
                ordWfKpiData.setIsFinish(1);
            }

            //区域
            ordWfKpiData.setAreaName(orderWorkflowRegion == null ? "" : orderWorkflowRegion
                    .getQyName());
            //中心,仓库
            storageName = storageMap.get(map.get("sCode"));
            ordWfKpiData.setCenterName(null == storageName ? "" : storageName);
            //网单号
            ordWfKpiData.setcOrderSn((String) map.get("cOrderSn"));
            ordWfKpiData.setDate(date);
            //是否超时免单 0未设置 1是 2否
            ordWfKpiData.setIsTimeoutFree(NumberParseUtil.parseInteger(map.get("isTimeoutFree")));
            ordWfKpiData.setNodeType(type);
            ordWfKpiData.setOrderId(NumberParseUtil.parseLong(map.get("orderId")));
            ordWfKpiData.setOrderProductId(NumberParseUtil.parseLong(map.get("orderProductId")));
            if (map.containsKey("orsId")) {
                ordWfKpiData.setOrsId(NumberParseUtil.parseLong(map.get("orsId")));
            } else {
                ordWfKpiData.setOrsId(NumberParseUtil.parseLong(map.get("id")));
            }
            ordWfKpiData.setOrderType(NumberParseUtil.parseInteger(map.get("orderType")));
            ordWfKpiData.setSourceName((String) map.get("source"));
            //工贸名称
            ordWfKpiData.setTradeName(orderWorkflowRegion == null ? "" : orderWorkflowRegion
                    .getGmName());
            //店铺ID
            ordWfKpiData.setStoreId(NumberParseUtil.parseInteger(map.get("storeId")));
            kpiDatas.add(ordWfKpiData);
            if (null != typeIdSetMap) {
                if (typeIdSetMap.containsKey(type)) {
                    idSet = typeIdSetMap.get(type);
                } else {
                    idSet = new HashSet<Long>();
                    typeIdSetMap.put(type, idSet);
                }
                if (isReverse) {
                    idSet.add(ordWfKpiData.getOrsId());
                } else {
                    idSet.add(ordWfKpiData.getOrderProductId());
                }
            }
        }
        return kpiDatas;
    }

}
