package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.settleCenter.*;
import com.haier.shop.model.*;
import com.haier.shop.service.OdsTMFXRebatesMonthlyDetailDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OdsTMFXRebatesMonthlyDetailDataServiceImpl implements OdsTMFXRebatesMonthlyDetailDataService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                        .getLogger(OdsTMFXRebatesMonthlyDetailDataServiceImpl.class);

    @Autowired
    OdsTMFXRebatesMonthlyDetailDao odsTMFXRebatesMonthlyDetailDao;
    @Autowired
    OdsTMFXRebatesDateDetailDao odsTMFXRebatesDateDetailDao;

    @Autowired
    OdsTMFXPointMaintainDao odsTMFXPointMaintainDao;

    @Autowired
    SettlementInvoiceDataDao settlementInvoiceDataDao;

    @Autowired
    OdsTMFXTargetMaintainDao odsTMFXTargetMaintainDao;

    @Autowired
    OdsTMFXRebatesSummaryDao odsTMFXRebatesSummaryDao;

    @Autowired
    OdsTMFXShopSummaryDao odsTMFXShopSummaryDao;

    @Autowired
    OdsTMFXPromotionCostDao odsTMFXPromotionCostDao;

    @Autowired
    OdsTMFXIndustrySummaryDao odsTMFXIndustrySummaryDao;

    @Autowired
    OdsTMFXPeculiarCategoryDao odsTMFXPeculiarCategoryDao;
    @Autowired
    SettlementLogDao settlementLogDao;

    @Override
    public List<OdsTMFXRebatesMonthlyDetail> queryOdsTMFXRebatesMonthlyDetail(OdsTMFXRebatesMonthlyDetail model) {
        return odsTMFXRebatesMonthlyDetailDao.list(model);
    }

    @Override
    public JSONObject paging(OdsTMFXRebatesMonthlyDetail param, PagerInfo page) {
        JSONObject result = new JSONObject();
        List<OdsTMFXRebatesMonthlyDetail> odsTMFXRebatesMonthlyDetailList = odsTMFXRebatesMonthlyDetailDao.paging(param,page);
        if(odsTMFXRebatesMonthlyDetailList == null){
            odsTMFXRebatesMonthlyDetailList = new ArrayList<OdsTMFXRebatesMonthlyDetail>();
        }
        result.put("rows", odsTMFXRebatesMonthlyDetailList);
        result.put("total", odsTMFXRebatesMonthlyDetailDao.count(param));
        return result;
    }
    @Override
    public JSONObject export(OdsTMFXRebatesMonthlyDetail param) {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = odsTMFXRebatesMonthlyDetailDao.export(param);
        if(list == null){
            list = new ArrayList<Map<String,Object>>();
        }
        result.put("rows", list);
        return result;
    }

    @Override
    public List<OdsTMFXRebatesMonthlyDetail> queryOdsTMFXRebatesMonthlyDetailById(String id) {
        return odsTMFXRebatesMonthlyDetailDao.load(id);
    }

    @Override
    public void create(OdsTMFXRebatesMonthlyDetail model) {
        odsTMFXRebatesMonthlyDetailDao.create(model);

    }

    @Override
    public void creates(List<OdsTMFXRebatesMonthlyDetail> modelList) {
        odsTMFXRebatesMonthlyDetailDao.creates(modelList);

    }

    @Override
    public void updateOdsTMFXRebatesMonthlyDetail(OdsTMFXRebatesMonthlyDetail model) {
        odsTMFXRebatesMonthlyDetailDao.update(model);

    }


    /**
     * 数据生成
     * @return
     */

    @Override
    public JSONObject createData(String year, String month, String type) {
        JSONObject result = new JSONObject();
        //生成计算日志
        SettlementLog settlementLog = new SettlementLog();
        settlementLog.setYear(year);
        settlementLog.setMonth(month);
        settlementLog.setType(type);
        settlementLog.setStatusDate(new Date());
        settlementLog.setSource("TM");
        try {
            int i = settlementLogDao.queryLog("TM");
            if(i>0){
                result.put("success", false);
                result.put("msg", "生成失败,有计算任务正在执行。");
                String settlementLogInfo = settlementLogDao.queryLogInfo("TM");
                result.put("settlementLogInfo", settlementLogInfo);
                return result;
            }

            settlementLog.setStatus("1");
            settlementLogDao.create(settlementLog);
            //生成返利月度明细
            String st = this.createDetail(year,month,type);
            if (StringUtils.isNotBlank(st)){

                result.put("success", false);
                result.put("msg", st.toString());
            }else {
                this.createSummary(year, month, type);
                if ("m".equals(type)) {
                    this.createPromotionCostSummary(year, month, type);
                }
                this.createShopData(year, month, type);
                this.createIndustrySummary(year, month, type);
                result.put("success", true);
                result.put("msg", "生成成功");
            }
            settlementLog.setStatus("3");
            settlementLog.setEndDate(new Date());
            settlementLogDao.update(settlementLog);
            String settlementLogInfo = settlementLogDao.queryLogInfo("TM");
            result.put("settlementLogInfo", settlementLogInfo);
        } catch (Exception e) {
            settlementLog.setStatus("2");
            settlementLogDao.update(settlementLog);
            result.put("success", false);
            result.put("msg", "生成失败");
            String settlementLogInfo = settlementLogDao.queryLogInfo("TM");
            result.put("settlementLogInfo", settlementLogInfo);
            e.printStackTrace();
            log.error("createData error", e);
        }

        return result;
    }

    /**
     * 生成返利月度明细
     * 查询最新点位的时候分开查,查1条的用queryPointByDistribution 查多条的用queryPointByDistribution3 --2018.02.26
     * @param year
     * @param month
     * @param type
     */
    @Override
    @Transactional
    public String createDetail(String year,String month,String type){
        //清空历史数据
        odsTMFXRebatesMonthlyDetailDao.clearHistoryData(year,month,type);
        String monthStr = month.replaceAll("^(0)","");//将月份前面的0去掉
//        List<DistributionDetails> details = distributionDetailsDao.queryDetailToSummary(year, monthStr, type);

        List<SettlementInvoiceData> details = settlementInvoiceDataDao.queryDetailToSummary(year, monthStr, type,null);
        if (details==null || details.isEmpty()){
            return "查询不到明细数据";
        }
        for (SettlementInvoiceData detail:details){
            OdsTMFXRebatesMonthlyDetail rebatesMonthly = new OdsTMFXRebatesMonthlyDetail();
            rebatesMonthly.setEcologyShop(detail.getSellpeople());
            rebatesMonthly.setYear(year);
            rebatesMonthly.setMonth(month);
            rebatesMonthly.setType(type);
            rebatesMonthly.setIndustry(detail.getIndustry());
            rebatesMonthly.setBrand(detail.getBrandname());
            rebatesMonthly.setSku(detail.getSku());
            rebatesMonthly.setModelDes(detail.getProductname());
            rebatesMonthly.setSaleNumber(detail.getNumber());
            rebatesMonthly.setSaleAmount(detail.getAmount());
            rebatesMonthly.setSource(detail.getSource());

            //查询点位
            OdsTMFXPointMaintain queryPoint = new OdsTMFXPointMaintain();
            queryPoint.setYear(year);
            queryPoint.setMonth(month);
            queryPoint.setType(type);
            queryPoint.setIndustry(detail.getIndustry());
            queryPoint.setBrand(detail.getBrandname());

            //查询日期点位 2018-07-30
            OdsTMFXPointMaintain queryDatePoint = new OdsTMFXPointMaintain();
            queryDatePoint.setYear(year);
            queryDatePoint.setMonth(month);
            queryDatePoint.setType("d");
            queryDatePoint.setIndustry(detail.getIndustry());
            queryDatePoint.setBrand(detail.getBrandname());

            BigDecimal monthlyCashAmount = BigDecimal.ZERO;
            //查询汇总明细
            SettlementInvoiceData queryAmount = new SettlementInvoiceData();
//            DistributionDetails queryAmount = new DistributionDetails();
            queryAmount.setYear(year);
            queryAmount.setMonth(monthStr);
            queryAmount.setIndustry(detail.getIndustry());
            queryAmount.setBrandname(detail.getBrandname());
            // SKU
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(detail.getSku());
            queryPoint.setRewardType("SKU");
            //生态店、SKU
            List<OdsTMFXPointMaintain> pointList = odsTMFXPointMaintainDao.queryPointByDistribution(queryPoint);
            if (pointList.size()==0){
                //SKU
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution(queryPoint);
                if (pointList.size()==0){
                    //生态店
                    //剩余型号的
                    queryPoint.setEcologyShop(detail.getSellpeople());
                    queryPoint.setSku(null);
                    queryPoint.setRewardType("剩余型号");
                    pointList = odsTMFXPointMaintainDao.queryPointByDistribution(queryPoint);
                    if (pointList.size()==0){
                        //不限规则
                        queryPoint.setEcologyShop(null);
                        pointList = odsTMFXPointMaintainDao.queryPointByDistribution(queryPoint);
                    }
                }
            }
            if (pointList.size()==1){
                OdsTMFXPointMaintain point = pointList.get(0);
                if (point.getBasePoint()!=null){
                    rebatesMonthly.setBasePoint(point.getBasePoint());
                    rebatesMonthly.setBaseRebatesAmount((detail.getAmount().multiply(point.getBasePoint())).setScale(2,BigDecimal.ROUND_HALF_UP));
                    monthlyCashAmount = monthlyCashAmount.add(rebatesMonthly.getBaseRebatesAmount());
                }
            }
            //SKU销额台阶的
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(detail.getSku());
            queryPoint.setRewardType("SKU销额台阶");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            if (pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            }
            if (pointList.size()!=0){
                //SKU销额台阶,销额台阶,SKU销量台阶 查询汇总明细金额的时候是要到生态店的
                queryAmount.setSellpeople(detail.getSellpeople());
                queryAmount.setSku(queryPoint.getSku());
//                DistributionDetails volumeAndAmount = distributionDetailsDao.querySummaryVolumeOrAmount(queryAmount);
                SettlementInvoiceData volumeAndAmount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);
                BigDecimal skuStepRebatesAmount = BigDecimal.ZERO;
                for (OdsTMFXPointMaintain pointData:pointList){
                    //判断属于哪个台阶
                    if ((volumeAndAmount.getAmount().compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                            ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&volumeAndAmount.getAmount().compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                        skuStepRebatesAmount = (detail.getAmount().multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                        rebatesMonthly.setSkuStepPoint(pointData.getSkuStepPoint());
                        rebatesMonthly.setSkuStepRebatesAmount(skuStepRebatesAmount);
                    }
                }
                monthlyCashAmount = monthlyCashAmount.add(skuStepRebatesAmount);
            }
            if(type.equals("m")){
                // 2018-7-30  添加按日期类型计算
                queryDatePoint.setEcologyShop(detail.getSellpeople());
                queryDatePoint.setSku(detail.getSku());
                queryDatePoint.setRewardType("SKU销额台阶");
                List<OdsTMFXPointMaintain> dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);

                if (dateRuleList.size()==0){
                    queryDatePoint.setEcologyShop(null);
                    dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);
                }
                for(OdsTMFXPointMaintain dateRule:dateRuleList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("year",year);
                    map.put("beginDate",dateRule.getBeginMonth());
                    map.put("endDate",dateRule.getEndMonth());
                    map.put("sellpeople",detail.getSellpeople());
                    map.put("industry",detail.getIndustry());
                    map.put("brandname",detail.getBrandname());
                    map.put("sku",detail.getSku());
                    //查询汇总数据
                    SettlementInvoiceData  detailInfo = settlementInvoiceDataDao.querySummaryVolumeOrAmountByDate(map);
                    queryDatePoint.setBeginMonth(dateRule.getBeginMonth());
                    queryDatePoint.setEndMonth(dateRule.getEndMonth());
                    //日期台阶点位
                    List<OdsTMFXPointMaintain> datePointList =odsTMFXPointMaintainDao.queryPointByDistribution6(queryDatePoint);
                    if(null!= detailInfo && datePointList.size()>0){
                        BigDecimal dateSkuStepRebatesAmount = BigDecimal.ZERO;
                        OdsTMFXRebatesDateDetail dateDetail = new OdsTMFXRebatesDateDetail();
                        dateDetail.setYear(year);
                        dateDetail.setBeginDate(dateRule.getBeginMonth());
                        dateDetail.setEndDate(dateRule.getEndMonth());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setIndustry(detail.getIndustry());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setBrand(detail.getBrandname());
                        dateDetail.setSku(detail.getSku());
                        dateDetail.setRewardType("SKU销额台阶");
                        dateDetail.setSaleAmount(detail.getAmount());
                        dateDetail.setSkuStepRebatesAmount(dateSkuStepRebatesAmount);
                        for(OdsTMFXPointMaintain datePoint : datePointList){
                            //判断属于哪个台阶
                            if ((detailInfo.getAmount().compareTo(new BigDecimal(datePoint.getBeginTarget()))>=0) &&
                                ("∞".equals(datePoint.getEndTarget())||(!"∞".equals(datePoint.getEndTarget())&&detailInfo.getAmount().compareTo(new BigDecimal(datePoint.getEndTarget()))==-1))){
                                dateDetail.setSaleAmount(detailInfo.getAmount());
                                dateDetail.setSkuStepPoint(datePoint.getSkuStepPoint());
                                dateSkuStepRebatesAmount = (detailInfo.getAmount().multiply(datePoint.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                dateDetail.setSkuStepRebatesAmount(dateSkuStepRebatesAmount);
                            }
                        }
                        odsTMFXRebatesDateDetailDao.create(dateDetail);
                        BigDecimal skuStepRebatesAmount = rebatesMonthly.getSkuStepRebatesAmount()==null?new BigDecimal("0"):rebatesMonthly.getSkuStepRebatesAmount();
                        rebatesMonthly.setSkuStepRebatesAmount(skuStepRebatesAmount.add(dateSkuStepRebatesAmount));
                        monthlyCashAmount = monthlyCashAmount.add(dateSkuStepRebatesAmount);
                    }
                }
            }


            //销额台阶的
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(null);
            queryPoint.setRewardType("销额台阶");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            if (pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            }
            if (pointList.size()!=0){
                //SKU销额台阶,销额台阶,SKU销量台阶 查询汇总明细金额的时候是要到生态店的
                queryAmount.setSellpeople(detail.getSellpeople());
                queryAmount.setSku(null);
//                DistributionDetails volumeAndAmount = distributionDetailsDao.querySummaryVolumeOrAmount(queryAmount);
                SettlementInvoiceData volumeAndAmount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);
                BigDecimal stepRebatesAmount = BigDecimal.ZERO;
                for (OdsTMFXPointMaintain pointData:pointList){
                    //判断属于哪个台阶
                    if ((volumeAndAmount.getAmount().compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                            ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&volumeAndAmount.getAmount().compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                        stepRebatesAmount = (detail.getAmount().multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                        rebatesMonthly.setStepPoint(pointData.getStepPoint());
                        rebatesMonthly.setStepRebatesAmount(stepRebatesAmount);
                    }
                }
                monthlyCashAmount = monthlyCashAmount.add(stepRebatesAmount);
            }

            if(type.equals("m")){
                // 2018-7-30  添加按日期类型计算
                queryDatePoint.setEcologyShop(detail.getSellpeople());
                queryDatePoint.setSku(null);
                queryDatePoint.setRewardType("销额台阶");
                List<OdsTMFXPointMaintain> dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);

                if (dateRuleList.size()==0){
                    queryDatePoint.setEcologyShop(null);
                    dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);
                }
                for(OdsTMFXPointMaintain dateRule:dateRuleList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("year",year);
                    map.put("beginDate",dateRule.getBeginMonth());
                    map.put("endDate",dateRule.getEndMonth());
                    map.put("sellpeople",detail.getSellpeople());
                    map.put("industry",detail.getIndustry());
                    map.put("brandname",detail.getBrandname());
                    //map.put("sku",detail.getSku());
                    //查询汇总数据
                    SettlementInvoiceData  detailInfo = settlementInvoiceDataDao.querySummaryVolumeOrAmountByDate(map);
                    queryDatePoint.setBeginMonth(dateRule.getBeginMonth());
                    queryDatePoint.setEndMonth(dateRule.getEndMonth());
                    //日期台阶点位
                    List<OdsTMFXPointMaintain> datePointList =odsTMFXPointMaintainDao.queryPointByDistribution6(queryDatePoint);
                    if(null!= detailInfo && datePointList.size()>0){
                        BigDecimal dateStepRebatesAmount = BigDecimal.ZERO;
                        OdsTMFXRebatesDateDetail dateDetail = new OdsTMFXRebatesDateDetail();
                        dateDetail.setYear(year);
                        dateDetail.setBeginDate(dateRule.getBeginMonth());
                        dateDetail.setEndDate(dateRule.getEndMonth());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setIndustry(detail.getIndustry());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setBrand(detail.getBrandname());
                        dateDetail.setSku(detail.getSku());
                        dateDetail.setRewardType("销额台阶");
                        dateDetail.setSaleAmount(detail.getAmount());
                        dateDetail.setStepRebatesAmount(dateStepRebatesAmount);
                        for(OdsTMFXPointMaintain datePoint : datePointList){
                            //判断属于哪个台阶
                            if ((detailInfo.getAmount().compareTo(new BigDecimal(datePoint.getBeginTarget()))>=0) &&
                                ("∞".equals(datePoint.getEndTarget())||(!"∞".equals(datePoint.getEndTarget())&&detailInfo.getAmount().compareTo(new BigDecimal(datePoint.getEndTarget()))==-1))){
                                dateDetail.setSaleAmount(detailInfo.getAmount());
                                dateDetail.setSkuStepPoint(datePoint.getStepPoint());
                                dateStepRebatesAmount = (detailInfo.getAmount().multiply(datePoint.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                dateDetail.setStepRebatesAmount(dateStepRebatesAmount);
                            }
                        }
                        odsTMFXRebatesDateDetailDao.create(dateDetail);
                        BigDecimal stepRebatesAmount = rebatesMonthly.getStepRebatesAmount()== null?new BigDecimal("0"):rebatesMonthly.getStepRebatesAmount();
                        rebatesMonthly.setStepRebatesAmount(stepRebatesAmount.add(dateStepRebatesAmount));
                        monthlyCashAmount = monthlyCashAmount.add(dateStepRebatesAmount);
                    }
                }
            }






            //品类专卖店的
            //额外返点
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(null);
            queryPoint.setRewardType("品类专卖店");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution(queryPoint);
            if (pointList.size()==1){
                OdsTMFXPointMaintain point = pointList.get(0);
                if (point.getExtraRebate()!=null) {
                    rebatesMonthly.setExtraRebate(point.getExtraRebate());
                    rebatesMonthly.setExtraRebatesAmount((detail.getAmount().multiply(point.getExtraRebate())).setScale(2, BigDecimal.ROUND_HALF_UP));
                    monthlyCashAmount = monthlyCashAmount.add(rebatesMonthly.getExtraRebatesAmount());
                }
            }
            //月度销额达标的
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(null);
            queryPoint.setRewardType("月度销额达标");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution(queryPoint);
            if (pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution(queryPoint);
            }
            if (pointList.size()==1){
                OdsTMFXPointMaintain point = pointList.get(0);
                if (point.getMarkPoint()!=null){
                    rebatesMonthly.setMarkPoint(point.getMarkPoint());
                    //查询汇总金额
                    queryAmount.setSellpeople(detail.getSellpeople());
                    queryAmount.setSku(null);
//                    DistributionDetails amount = distributionDetailsDao.querySummaryVolumeOrAmount(queryAmount);
                    SettlementInvoiceData amount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);
                    //查询目标
                    OdsTMFXTargetMaintain queryTarget = new OdsTMFXTargetMaintain();
                    queryTarget.setYear(year);
                    queryTarget.setMonth(month);
                    queryTarget.setType("m");
                    queryTarget.setEcologyShop(detail.getSellpeople());
                    queryTarget.setIndustry(detail.getIndustry());
                    queryTarget.setBrand(detail.getBrandname());
                    OdsTMFXTargetMaintain target = odsTMFXTargetMaintainDao.findTargetMaintain(queryTarget);
                    BigDecimal completion = BigDecimal.ZERO;
                    if (target!=null) {
                        //销额完成率
                        completion = amount.getAmount().divide(target.getTarget().multiply(new BigDecimal("10000")), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    if (completion.compareTo(new BigDecimal("0.8"))>=0&&completion.compareTo(BigDecimal.ONE)==-1){
                        //达标
                        BigDecimal markRebatesAmount = (detail.getAmount().multiply(point.getMarkPoint()).multiply(completion)).setScale(2,BigDecimal.ROUND_HALF_UP);
                        rebatesMonthly.setMarkRebatesAmount(markRebatesAmount);
                    }else if (completion.compareTo(BigDecimal.ONE)>=0){
                        //超额完成
                        BigDecimal markRebatesAmount = (detail.getAmount().multiply(point.getMarkPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                        rebatesMonthly.setMarkRebatesAmount(markRebatesAmount);
                    }else{
                        //不达标
                        rebatesMonthly.setMarkRebatesAmount(BigDecimal.ZERO);
                    }
                    monthlyCashAmount = monthlyCashAmount.add(rebatesMonthly.getMarkRebatesAmount());
                }
            }

            //计算季度通补
            if(type.equals("q")){
                //查询季度下月度目标和
                OdsTMFXTargetMaintain queryTarget1 = new OdsTMFXTargetMaintain();
                queryTarget1.setYear(year);
                queryTarget1.setMonth(month);
                queryTarget1.setType("q");
                queryTarget1.setEcologyShop(detail.getSellpeople());
                queryTarget1.setIndustry(detail.getIndustry());
                queryTarget1.setBrand(detail.getBrandname());
                //该季度下三个月的月度返利目标
                List<OdsTMFXTargetMaintain> targetList = odsTMFXTargetMaintainDao.findTargetMaintain2(queryTarget1);
                if(targetList.size()>0){
                    BigDecimal targetSum = BigDecimal.ZERO;//三个月返利目标和
                    for(OdsTMFXTargetMaintain odsTMFXTargetMaintain : targetList){
                        targetSum = targetSum.add(odsTMFXTargetMaintain.getTarget());
                    }
                    targetSum = targetSum.multiply(new BigDecimal("10000"));
                    //判断季度的销额是否达到三个月的返利目标和,如果达标则计算季度下各月没有达标的通补金额  通补金额=实际销额*达标点位
                    if(detail.getAmount().compareTo(targetSum)>=0){
                        OdsTMFXRebatesMonthlyDetail  odsTMFXRebatesMonthlyDetail = new OdsTMFXRebatesMonthlyDetail();
                        odsTMFXRebatesMonthlyDetail.setEcologyShop(detail.getSellpeople());
                        odsTMFXRebatesMonthlyDetail.setYear(year);
                        odsTMFXRebatesMonthlyDetail.setMonth(month);
                        odsTMFXRebatesMonthlyDetail.setType("q");
                        odsTMFXRebatesMonthlyDetail.setIndustry(detail.getIndustry());
                        odsTMFXRebatesMonthlyDetail.setBrand(detail.getBrandname());
                        odsTMFXRebatesMonthlyDetail.setSku(detail.getSku());
                        List<OdsTMFXRebatesMonthlyDetail> list = odsTMFXRebatesMonthlyDetailDao.queryList(odsTMFXRebatesMonthlyDetail);
                        BigDecimal sumOfMoney = BigDecimal.ZERO;
                        for(OdsTMFXRebatesMonthlyDetail model : list){
                            //如果没有月度达标返利金额则表示该月没有达标,计算该月季度通补金额
                            if(null == model.getMarkRebatesAmount() || model.getMarkRebatesAmount().compareTo(new BigDecimal("0"))==0 ){
                                BigDecimal markPoint =  model.getMarkPoint()==null?new BigDecimal("0"):model.getMarkPoint();
                                sumOfMoney = sumOfMoney.add(model.getSaleAmount().multiply(markPoint));
                                //rebatesMonthly.setMarkPoint(model.getMarkPoint());
                            }
                        }
                        rebatesMonthly.setMarkRebatesAmount(sumOfMoney);
                        monthlyCashAmount = monthlyCashAmount.add(sumOfMoney);
                    }
                }
            }

            //SKU销量台阶的
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(detail.getSku());
            queryPoint.setRewardType("SKU销量台阶");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            if(pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            }
            if (pointList.size()!=0){
                //SKU销额台阶,销额台阶,SKU销量台阶,销量台阶 查询汇总明细金额的时候是要到生态店的
                queryAmount.setSellpeople(detail.getSellpeople());
                queryAmount.setSku(queryPoint.getSku());
//                OdsDistributionDetails volumeAndAmount = odsDistributionDetailsDao.querySummaryVolumeAndAmount(queryAmount);
                SettlementInvoiceData volumeAndAmount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);

                BigDecimal fixedAmount = BigDecimal.ZERO;
                for (OdsTMFXPointMaintain pointData:pointList){
                    if ((volumeAndAmount.getNumber().longValue()>=Long.valueOf(pointData.getBeginTarget()))&&
                            ("∞".equals(pointData.getEndTarget())||volumeAndAmount.getNumber().longValue()<Long.valueOf(pointData.getEndTarget()))){
                        //台阶点位的
                        if (pointData.getStepPoint()!=null){
                            //返利金额=销售金额乘以台阶点位
                            fixedAmount = (pointData.getStepPoint().multiply(detail.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP);
                            rebatesMonthly.setFixedPoint(pointData.getStepPoint());
                        }
                        //固定金额的
                        if (pointData.getFixedAmount()!=null){
                            //返利金额=销售数量乘以固定金额
                            fixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(detail.getNumber()))).setScale(2,BigDecimal.ROUND_HALF_UP);
                            rebatesMonthly.setFixedPoint(pointData.getFixedAmount());
                        }
                        rebatesMonthly.setFixedAmount(fixedAmount);
                    }
                }
                monthlyCashAmount = monthlyCashAmount.add(fixedAmount);
            }

            if(type.equals("m")){
                // 2018-7-30  添加按日期类型计算
                queryDatePoint.setEcologyShop(detail.getSellpeople());
                queryDatePoint.setSku(detail.getSku());
                queryDatePoint.setRewardType("SKU销量台阶");
                List<OdsTMFXPointMaintain> dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);

                if (dateRuleList.size()==0){
                    queryDatePoint.setEcologyShop(null);
                    dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);
                }
                for(OdsTMFXPointMaintain dateRule:dateRuleList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("year",year);
                    map.put("beginDate",dateRule.getBeginMonth());
                    map.put("endDate",dateRule.getEndMonth());
                    map.put("sellpeople",detail.getSellpeople());
                    map.put("industry",detail.getIndustry());
                    map.put("brandname",detail.getBrandname());
                    map.put("sku",detail.getSku());
                    //查询汇总数据
                    SettlementInvoiceData  detailInfo = settlementInvoiceDataDao.querySummaryVolumeOrAmountByDate(map);
                    queryDatePoint.setBeginMonth(dateRule.getBeginMonth());
                    queryDatePoint.setEndMonth(dateRule.getEndMonth());
                    //日期台阶点位
                    List<OdsTMFXPointMaintain> datePointList =odsTMFXPointMaintainDao.queryPointByDistribution6(queryDatePoint);
                    if(null!= detailInfo && datePointList.size()>0){
                        BigDecimal dateFixedAmount = BigDecimal.ZERO;
                        OdsTMFXRebatesDateDetail dateDetail = new OdsTMFXRebatesDateDetail();
                        dateDetail.setYear(year);
                        dateDetail.setBeginDate(dateRule.getBeginMonth());
                        dateDetail.setEndDate(dateRule.getEndMonth());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setIndustry(detail.getIndustry());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setBrand(detail.getBrandname());
                        dateDetail.setSku(detail.getSku());
                        //dateDetail.setSkuStepPoint(datePoint.getStepPoint());
                        dateDetail.setRewardType("SKU销量台阶");
                        dateDetail.setSaleNumber(detailInfo.getNumber());
                        for(OdsTMFXPointMaintain datePoint : datePointList){
                            //判断属于哪个台阶
                            if ((detailInfo.getNumber().longValue()>=Long.valueOf(datePoint.getBeginTarget()))&&
                                ("∞".equals(datePoint.getEndTarget())||detailInfo.getNumber().longValue()<Long.valueOf(datePoint.getEndTarget()))){
                                //台阶点位的
                                if (datePoint.getStepPoint()!=null){
                                    //返利金额=销售金额乘以台阶点位
                                    dateFixedAmount = (datePoint.getStepPoint().multiply(detailInfo.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    dateDetail.setFixedPoint(datePoint.getStepPoint());
                                    dateDetail.setFixedAmount(dateFixedAmount);
                                }
                                //固定金额的
                                if (datePoint.getFixedAmount()!=null){
                                    //返利金额=销售数量乘以固定金额
                                    dateFixedAmount = (datePoint.getFixedAmount().multiply(new BigDecimal(detailInfo.getNumber()))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    dateDetail.setFixedPoint(datePoint.getFixedAmount());
                                    dateDetail.setFixedAmount(dateFixedAmount);
                                }
                            }
                        }
                        odsTMFXRebatesDateDetailDao.create(dateDetail);
                        BigDecimal fixedAmount = rebatesMonthly.getFixedAmount()== null?new BigDecimal("0"):rebatesMonthly.getFixedAmount();
                        rebatesMonthly.setFixedAmount(fixedAmount.add(dateFixedAmount));
                        monthlyCashAmount = monthlyCashAmount.add(dateFixedAmount);
                    }
                }
            }





            //销量台阶 2018-03-22新增 销量达标的 返利金额=销额乘点位
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(null);
            queryPoint.setRewardType("销量台阶");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            if (pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            }
            if (pointList.size()!=0){
                //查询销量
                queryAmount.setSellpeople(detail.getSellpeople());
                queryAmount.setSku(null);
//                OdsDistributionDetails volumeAndAmount = odsDistributionDetailsDao.querySummaryVolumeAndAmount(queryAmount);
                SettlementInvoiceData volumeAndAmount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);
                BigDecimal amountStepRebates = BigDecimal.ZERO;
                for (OdsTMFXPointMaintain pointData : pointList){
                    if ((volumeAndAmount.getNumber().longValue()>=Long.valueOf(pointData.getBeginTarget()))&&
                            ("∞".equals(pointData.getEndTarget())||volumeAndAmount.getNumber().longValue()<Long.valueOf(pointData.getEndTarget()))){
                        amountStepRebates = (pointData.getAmountPoint().multiply(detail.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP);
                        rebatesMonthly.setAmountPoint(pointData.getAmountPoint());
                        rebatesMonthly.setAmountStepRebates(amountStepRebates);
                    }
                }
                monthlyCashAmount = monthlyCashAmount.add(amountStepRebates);
            }


            if(type.equals("m")){
                // 2018-7-30  添加按日期类型计算
                queryDatePoint.setEcologyShop(detail.getSellpeople());
                queryDatePoint.setSku(null);
                queryDatePoint.setRewardType("销量台阶");
                List<OdsTMFXPointMaintain> dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);

                if (dateRuleList.size()==0){
                    queryDatePoint.setEcologyShop(null);
                    dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);
                }
                for(OdsTMFXPointMaintain dateRule:dateRuleList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("year",year);
                    map.put("beginDate",dateRule.getBeginMonth());
                    map.put("endDate",dateRule.getEndMonth());
                    map.put("sellpeople",detail.getSellpeople());
                    map.put("industry",detail.getIndustry());
                    map.put("brandname",detail.getBrandname());
                    //map.put("sku",detail.getSku());
                    //查询汇总数据
                    SettlementInvoiceData  detailInfo = settlementInvoiceDataDao.querySummaryVolumeOrAmountByDate(map);
                    queryDatePoint.setBeginMonth(dateRule.getBeginMonth());
                    queryDatePoint.setEndMonth(dateRule.getEndMonth());
                    //日期台阶点位
                    List<OdsTMFXPointMaintain> datePointList =odsTMFXPointMaintainDao.queryPointByDistribution6(queryDatePoint);
                    if(null!= detailInfo && datePointList.size()>0){

                        BigDecimal dateAmountStepRebates = BigDecimal.ZERO;
                        OdsTMFXRebatesDateDetail dateDetail = new OdsTMFXRebatesDateDetail();
                        dateDetail.setYear(year);
                        dateDetail.setBeginDate(dateRule.getBeginMonth());
                        dateDetail.setEndDate(dateRule.getEndMonth());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setIndustry(detail.getIndustry());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setBrand(detail.getBrandname());
                        dateDetail.setSku(detail.getSku());
                        dateDetail.setSaleNumber(detailInfo.getNumber());
                        dateDetail.setSaleAmount(detailInfo.getAmount());
                        //dateDetail.setSkuStepPoint(datePoint.getStepPoint());
                        dateDetail.setRewardType("销量台阶");
                        for(OdsTMFXPointMaintain datePoint : datePointList){
                            //判断属于哪个台阶
                            if ((detailInfo.getNumber().longValue()>=Long.valueOf(datePoint.getBeginTarget())) &&
                                ("∞".equals(datePoint.getEndTarget())||detailInfo.getNumber().longValue()<Long.valueOf(datePoint.getEndTarget()))){
                                dateDetail.setAmountPoint(datePoint.getAmountPoint());
                                dateAmountStepRebates = (datePoint.getAmountPoint().multiply(detailInfo.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                dateDetail.setAmountStepRebates(dateAmountStepRebates);
                            }
                        }
                        odsTMFXRebatesDateDetailDao.create(dateDetail);
                        BigDecimal amountStepRebates = rebatesMonthly.getAmountStepRebates()==null?new BigDecimal("0"):rebatesMonthly.getAmountStepRebates();
                        rebatesMonthly.setAmountStepRebates(amountStepRebates.add(dateAmountStepRebates));
                        monthlyCashAmount = monthlyCashAmount.add(dateAmountStepRebates);
                    }
                }
            }


            /*
            * 2018-07-26 添加对赌奖励类型
            * */

            //销额对赌
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(null);
            queryPoint.setRewardType("销额对赌");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            if (pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            }
            if (pointList.size()!=0){
                //销额对赌 销量对赌 SKU销额对赌 SKU销量对赌 查询汇总明细金额的时候是要到生态店的
                queryAmount.setSellpeople(detail.getSellpeople());
                queryAmount.setSku(null);
                SettlementInvoiceData volumeAndAmount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);
                OdsTMFXPointMaintain point = pointList.get(0);
                //对赌目标
                String bettingTarget = point.getBettingTarget();
                if(null!= bettingTarget &&!bettingTarget.equals("")){
                    //销额对赌完成率
                    BigDecimal completion = BigDecimal.ZERO;
                    completion = (volumeAndAmount.getAmount().divide(new BigDecimal(bettingTarget))).setScale(5,BigDecimal.ROUND_HALF_UP);
                    //销额对赌返利金额
                    BigDecimal bettingStepRebatesAmount = BigDecimal.ZERO;
                    for (OdsTMFXPointMaintain pointData:pointList){
                        //对赌计算规则 C为闭 O为开
                        String intervalRule = pointData.getIntervalRule();
                        //计算类型 target 按目标  actual 按实际
                        String calculationType = pointData.getCalculationType();
                        //对赌系数  如果对赌系数为0 则按完成率计算
                        BigDecimal bettingCoefficient = pointData.getBettingCoefficient();
                        bettingCoefficient = bettingCoefficient.compareTo(new BigDecimal(0))==0?completion:bettingCoefficient;
                        if(intervalRule.equals("CC")){
                            //判断属于哪个台阶
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                if(calculationType.equals("target")){
                                    bettingStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }else if(calculationType.equals("actual")){
                                    bettingStepRebatesAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }

                                rebatesMonthly.setBtStepPoint(pointData.getStepPoint());//对赌返点
                                rebatesMonthly.setBtStepRebatesAmount(bettingStepRebatesAmount);//对赌返利金额
                                rebatesMonthly.setBtTarget(bettingTarget);
                                rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }else if(intervalRule.equals("CO")){
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                                if(calculationType.equals("target")){
                                    bettingStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }else if(calculationType.equals("actual")){
                                    bettingStepRebatesAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }
                                rebatesMonthly.setBtStepPoint(pointData.getStepPoint());//对赌返点
                                rebatesMonthly.setBtStepRebatesAmount(bettingStepRebatesAmount);//对赌返利金额
                                rebatesMonthly.setBtTarget(bettingTarget);
                                rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }else if(intervalRule.equals("OC")){
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))==1) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                if(calculationType.equals("target")){
                                    bettingStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }else if(calculationType.equals("actual")){
                                    bettingStepRebatesAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }
                                rebatesMonthly.setBtStepPoint(pointData.getStepPoint());//对赌返点
                                rebatesMonthly.setBtStepRebatesAmount(bettingStepRebatesAmount);//对赌返利金额
                                rebatesMonthly.setBtTarget(bettingTarget);
                                rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }

                    }
                    monthlyCashAmount = monthlyCashAmount.add(bettingStepRebatesAmount);
                }

            }

            if(type.equals("m")){
                // 2018-7-30  添加按日期类型计算
                queryDatePoint.setEcologyShop(detail.getSellpeople());
                queryDatePoint.setSku(null);
                queryDatePoint.setRewardType("销额对赌");
                List<OdsTMFXPointMaintain> dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);

                if (dateRuleList.size()==0){
                    queryDatePoint.setEcologyShop(null);
                    dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);
                }
                for(OdsTMFXPointMaintain dateRule:dateRuleList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("year",year);
                    map.put("beginDate",dateRule.getBeginMonth());
                    map.put("endDate",dateRule.getEndMonth());
                    map.put("sellpeople",detail.getSellpeople());
                    map.put("industry",detail.getIndustry());
                    map.put("brandname",detail.getBrandname());
                    //map.put("sku",detail.getSku());
                    //查询汇总数据
                    SettlementInvoiceData  detailInfo = settlementInvoiceDataDao.querySummaryVolumeOrAmountByDate(map);
                    queryDatePoint.setBeginMonth(dateRule.getBeginMonth());
                    queryDatePoint.setEndMonth(dateRule.getEndMonth());
                    //日期台阶点位
                    List<OdsTMFXPointMaintain> datePointList =odsTMFXPointMaintainDao.queryPointByDistribution6(queryDatePoint);

                    OdsTMFXPointMaintain point = datePointList.get(0);
                    //对赌目标
                    String bettingTarget = point.getBettingTarget();
                    if(null!= bettingTarget &&!bettingTarget.equals("")){
                        //销额对赌完成率
                        BigDecimal completion = BigDecimal.ZERO;
                        completion = (detailInfo.getAmount().divide(new BigDecimal(bettingTarget))).setScale(5,BigDecimal.ROUND_HALF_UP);
                        //销额对赌返利金额
                        BigDecimal bettingStepRebatesAmount = BigDecimal.ZERO;
                        OdsTMFXRebatesDateDetail dateDetail = new OdsTMFXRebatesDateDetail();
                        dateDetail.setYear(year);
                        dateDetail.setBeginDate(dateRule.getBeginMonth());
                        dateDetail.setEndDate(dateRule.getEndMonth());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setIndustry(detail.getIndustry());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setBrand(detail.getBrandname());
                        dateDetail.setSku(detail.getSku());
                        dateDetail.setRewardType("销额对赌");
                        dateDetail.setBtTarget(bettingTarget);
                        dateDetail.setSaleAmount(detailInfo.getAmount());
                        for (OdsTMFXPointMaintain pointData:datePointList){
                            //对赌计算规则 C为闭 O为开
                            String intervalRule = pointData.getIntervalRule();
                            //计算类型 target 按目标  actual 按实际
                            String calculationType = pointData.getCalculationType();
                            //对赌系数  如果对赌系数为0 则按完成率计算
                            BigDecimal bettingCoefficient = pointData.getBettingCoefficient();
                            bettingCoefficient = bettingCoefficient.compareTo(new BigDecimal(0))==0?completion:bettingCoefficient;
                            if(intervalRule.equals("CC")){
                                //判断属于哪个台阶
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    if(calculationType.equals("target")){
                                        bettingStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        bettingStepRebatesAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }

                                    dateDetail.setBtStepPoint(pointData.getStepPoint());//对赌返点
                                    dateDetail.setBtStepRebatesAmount(bettingStepRebatesAmount);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }else if(intervalRule.equals("CO")){
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                                    if(calculationType.equals("target")){
                                        bettingStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        bettingStepRebatesAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }
                                    dateDetail.setBtStepPoint(pointData.getStepPoint());//对赌返点
                                    dateDetail.setBtStepRebatesAmount(bettingStepRebatesAmount);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }else if(intervalRule.equals("OC")){
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))==1) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    if(calculationType.equals("target")){
                                        bettingStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        bettingStepRebatesAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }
                                    dateDetail.setBtStepPoint(pointData.getStepPoint());//对赌返点
                                    dateDetail.setBtStepRebatesAmount(bettingStepRebatesAmount);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }
                        }
                        odsTMFXRebatesDateDetailDao.create(dateDetail);
                        BigDecimal btStepRebatesAmount = rebatesMonthly.getBtStepRebatesAmount()==null?new BigDecimal("0"):rebatesMonthly.getBtStepRebatesAmount();
                        rebatesMonthly.setBtStepRebatesAmount(btStepRebatesAmount.add(bettingStepRebatesAmount));//对赌返利金额
                        monthlyCashAmount = monthlyCashAmount.add(bettingStepRebatesAmount);
                    }
                }
            }


            //SKU销额对赌
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(detail.getSku());
            queryPoint.setRewardType("SKU销额对赌");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            if (pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            }
            if (pointList.size()!=0){
                //销额对赌 销量对赌 SKU销额对赌 SKU销量对赌 查询汇总明细金额的时候是要到生态店的
                queryAmount.setSellpeople(detail.getSellpeople());
                queryAmount.setSku(queryPoint.getSku());
                SettlementInvoiceData volumeAndAmount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);
                OdsTMFXPointMaintain point = pointList.get(0);
                //对赌目标
                String bettingTarget = point.getBettingTarget();
                if(null!= bettingTarget &&!bettingTarget.equals("")){
                    //SKU销额对赌完成率
                    BigDecimal completion = BigDecimal.ZERO;
                    completion = (volumeAndAmount.getAmount().divide(new BigDecimal(bettingTarget))).setScale(5,BigDecimal.ROUND_HALF_UP);
                    //SKU销额赌返利金额
                    BigDecimal bettingSkuStepRebatesAmount = BigDecimal.ZERO;
                    for (OdsTMFXPointMaintain pointData:pointList){
                        //对赌计算规则 C为闭 O为开
                        String intervalRule = pointData.getIntervalRule();
                        //计算类型 target 按目标  actual 按实际
                        String calculationType = pointData.getCalculationType();
                        //对赌系数  如果对赌系数为0 则按完成率计算
                        BigDecimal bettingCoefficient = pointData.getBettingCoefficient();
                        bettingCoefficient = bettingCoefficient.compareTo(new BigDecimal(0))==0?completion:bettingCoefficient;
                        if(intervalRule.equals("CC")){
                            //判断属于哪个台阶
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                if(calculationType.equals("target")){
                                    bettingSkuStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }else if(calculationType.equals("actual")){
                                    bettingSkuStepRebatesAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }

                                rebatesMonthly.setBtSkuStepPoint(pointData.getSkuStepPoint());//对赌返点
                                rebatesMonthly.setBtSkuStepRebatesAmount(bettingSkuStepRebatesAmount);//对赌返利金额
                                rebatesMonthly.setBtTarget(bettingTarget);
                                rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }else if(intervalRule.equals("CO")){
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                                if(calculationType.equals("target")){
                                    bettingSkuStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }else if(calculationType.equals("actual")){
                                    bettingSkuStepRebatesAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }
                                rebatesMonthly.setBtSkuStepPoint(pointData.getSkuStepPoint());//对赌返点
                                rebatesMonthly.setBtSkuStepRebatesAmount(bettingSkuStepRebatesAmount);//对赌返利金额
                                rebatesMonthly.setBtTarget(bettingTarget);
                                rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }else if(intervalRule.equals("OC")){
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))==1) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                if(calculationType.equals("target")){
                                    bettingSkuStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }else if(calculationType.equals("actual")){
                                    bettingSkuStepRebatesAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                }
                                rebatesMonthly.setBtSkuStepPoint(pointData.getSkuStepPoint());//对赌返点
                                rebatesMonthly.setBtSkuStepRebatesAmount(bettingSkuStepRebatesAmount);//对赌返利金额
                                rebatesMonthly.setBtTarget(bettingTarget);
                                rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }
                    }
                    monthlyCashAmount = monthlyCashAmount.add(bettingSkuStepRebatesAmount);
                }

            }



            if(type.equals("m")){
                // 2018-7-30  添加按日期类型计算
                queryDatePoint.setEcologyShop(detail.getSellpeople());
                queryDatePoint.setSku(detail.getSku());
                queryDatePoint.setRewardType("SKU销额对赌");
                List<OdsTMFXPointMaintain> dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);

                if (dateRuleList.size()==0){
                    queryDatePoint.setEcologyShop(null);
                    dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);
                }
                for(OdsTMFXPointMaintain dateRule:dateRuleList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("year",year);
                    map.put("beginDate",dateRule.getBeginMonth());
                    map.put("endDate",dateRule.getEndMonth());
                    map.put("sellpeople",detail.getSellpeople());
                    map.put("industry",detail.getIndustry());
                    map.put("brandname",detail.getBrandname());
                    map.put("sku",detail.getSku());
                    //查询汇总数据
                    SettlementInvoiceData  detailInfo = settlementInvoiceDataDao.querySummaryVolumeOrAmountByDate(map);
                    queryDatePoint.setBeginMonth(dateRule.getBeginMonth());
                    queryDatePoint.setEndMonth(dateRule.getEndMonth());
                    //日期台阶点位
                    List<OdsTMFXPointMaintain> datePointList =odsTMFXPointMaintainDao.queryPointByDistribution6(queryDatePoint);
                    OdsTMFXPointMaintain point = datePointList.get(0);
                    //对赌目标
                    String bettingTarget = point.getBettingTarget();
                    if(null!= bettingTarget &&!bettingTarget.equals("")){
                        //SKU销额对赌完成率
                        BigDecimal completion = BigDecimal.ZERO;
                        completion = (detailInfo.getAmount().divide(new BigDecimal(bettingTarget))).setScale(5,BigDecimal.ROUND_HALF_UP);
                        //SKU销额赌返利金额
                        BigDecimal bettingSkuStepRebatesAmount = BigDecimal.ZERO;
                        OdsTMFXRebatesDateDetail dateDetail = new OdsTMFXRebatesDateDetail();
                        dateDetail.setYear(year);
                        dateDetail.setBeginDate(dateRule.getBeginMonth());
                        dateDetail.setEndDate(dateRule.getEndMonth());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setIndustry(detail.getIndustry());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setBrand(detail.getBrandname());
                        dateDetail.setSku(detail.getSku());
                        dateDetail.setRewardType("SKU销额对赌");
                        dateDetail.setBtTarget(bettingTarget);
                        dateDetail.setSaleAmount(detailInfo.getAmount());
                        for (OdsTMFXPointMaintain pointData:datePointList){
                            //对赌计算规则 C为闭 O为开
                            String intervalRule = pointData.getIntervalRule();
                            //计算类型 target 按目标  actual 按实际
                            String calculationType = pointData.getCalculationType();
                            //对赌系数  如果对赌系数为0 则按完成率计算
                            BigDecimal bettingCoefficient = pointData.getBettingCoefficient();
                            bettingCoefficient = bettingCoefficient.compareTo(new BigDecimal(0))==0?completion:bettingCoefficient;
                            if(intervalRule.equals("CC")){
                                //判断属于哪个台阶
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    if(calculationType.equals("target")){
                                        bettingSkuStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        bettingSkuStepRebatesAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }

                                    dateDetail.setBtSkuStepPoint(pointData.getSkuStepPoint());//对赌返点
                                    dateDetail.setBtSkuStepRebatesAmount(bettingSkuStepRebatesAmount);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }else if(intervalRule.equals("CO")){
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                                    if(calculationType.equals("target")){
                                        bettingSkuStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        bettingSkuStepRebatesAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }
                                    dateDetail.setBtSkuStepPoint(pointData.getSkuStepPoint());//对赌返点
                                    dateDetail.setBtSkuStepRebatesAmount(bettingSkuStepRebatesAmount);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }else if(intervalRule.equals("OC")){
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))==1) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    if(calculationType.equals("target")){
                                        bettingSkuStepRebatesAmount = (new BigDecimal(bettingTarget).multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        bettingSkuStepRebatesAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getSkuStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }
                                    dateDetail.setBtSkuStepPoint(pointData.getSkuStepPoint());//对赌返点
                                    dateDetail.setBtSkuStepRebatesAmount(bettingSkuStepRebatesAmount);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }
                        }
                        odsTMFXRebatesDateDetailDao.create(dateDetail);
                        BigDecimal btSkuStepRebatesAmount = rebatesMonthly.getBtSkuStepRebatesAmount()==null?new BigDecimal("0"):rebatesMonthly.getBtSkuStepRebatesAmount();
                        rebatesMonthly.setBtSkuStepRebatesAmount(btSkuStepRebatesAmount.add(bettingSkuStepRebatesAmount));//对赌返利金额
                        monthlyCashAmount = monthlyCashAmount.add(bettingSkuStepRebatesAmount);
                    }
                }
            }



            //销量对赌
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(null);
            queryPoint.setRewardType("销量对赌");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            if (pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            }
            if (pointList.size()!=0){
                //销额对赌 销量对赌 SKU销额对赌 SKU销量对赌 查询汇总明细金额的时候是要到生态店的
                queryAmount.setSellpeople(detail.getSellpeople());
                queryAmount.setSku(null);
                SettlementInvoiceData volumeAndAmount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);
                OdsTMFXPointMaintain point = pointList.get(0);
                //对赌目标
                String bettingTarget = point.getBettingTarget();
                if(null!= bettingTarget &&!bettingTarget.equals("")){
                    //销量对赌完成率
                    BigDecimal completion = BigDecimal.ZERO;
                    completion = (new BigDecimal(volumeAndAmount.getNumber()).divide(new BigDecimal(bettingTarget))).setScale(5,BigDecimal.ROUND_HALF_UP);
                    //销量对赌返利金额
                    BigDecimal btAmountStepRebates = BigDecimal.ZERO;
                    for (OdsTMFXPointMaintain pointData:pointList){
                        //对赌计算规则 C为闭 O为开
                        String intervalRule = pointData.getIntervalRule();
                        //计算类型 target 按目标  actual 按实际
                        String calculationType = pointData.getCalculationType();
                        //对赌系数  如果对赌系数为0 则按完成率计算
                        BigDecimal bettingCoefficient = pointData.getBettingCoefficient();
                        bettingCoefficient = bettingCoefficient.compareTo(new BigDecimal(0))==0?completion:bettingCoefficient;
                        if(intervalRule.equals("CC")){
                            //判断属于哪个台阶
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    btAmountStepRebates = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getAmountPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    rebatesMonthly.setBtAmountPoint(pointData.getAmountPoint());//对赌返点
                                    rebatesMonthly.setBtAmountStepRebates(btAmountStepRebates);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }else if(intervalRule.equals("CO")){
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                                    btAmountStepRebates = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getAmountPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    rebatesMonthly.setBtAmountPoint(pointData.getAmountPoint());//对赌返点
                                    rebatesMonthly.setBtAmountStepRebates(btAmountStepRebates);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }else if(intervalRule.equals("OC")){
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))==1) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    btAmountStepRebates = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getAmountPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    rebatesMonthly.setBtAmountPoint(pointData.getAmountPoint());//对赌返点
                                    rebatesMonthly.setBtAmountStepRebates(btAmountStepRebates);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                            }
                        }
                    }
                    monthlyCashAmount = monthlyCashAmount.add(btAmountStepRebates);
                }

            }


            if(type.equals("m")){
                // 2018-7-30  添加按日期类型计算
                queryDatePoint.setEcologyShop(detail.getSellpeople());
                queryDatePoint.setSku(null);
                queryDatePoint.setRewardType("销量对赌");
                List<OdsTMFXPointMaintain> dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);

                if (dateRuleList.size()==0){
                    queryDatePoint.setEcologyShop(null);
                    dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);
                }
                for(OdsTMFXPointMaintain dateRule:dateRuleList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("year",year);
                    map.put("beginDate",dateRule.getBeginMonth());
                    map.put("endDate",dateRule.getEndMonth());
                    map.put("sellpeople",detail.getSellpeople());
                    map.put("industry",detail.getIndustry());
                    map.put("brandname",detail.getBrandname());
                    map.put("sku",detail.getSku());
                    //查询汇总数据
                    SettlementInvoiceData  detailInfo = settlementInvoiceDataDao.querySummaryVolumeOrAmountByDate(map);
                    queryDatePoint.setBeginMonth(dateRule.getBeginMonth());
                    queryDatePoint.setEndMonth(dateRule.getEndMonth());
                    //日期台阶点位
                    List<OdsTMFXPointMaintain> datePointList =odsTMFXPointMaintainDao.queryPointByDistribution6(queryDatePoint);
                    OdsTMFXPointMaintain point = datePointList.get(0);
                    //对赌目标
                    String bettingTarget = point.getBettingTarget();
                    if(null!= bettingTarget &&!bettingTarget.equals("")){
                        //销量对赌完成率
                        BigDecimal completion = BigDecimal.ZERO;
                        completion = (new BigDecimal(detailInfo.getNumber()).divide(new BigDecimal(bettingTarget))).setScale(5,BigDecimal.ROUND_HALF_UP);
                        //销量对赌返利金额
                        BigDecimal btAmountStepRebates = BigDecimal.ZERO;
                        OdsTMFXRebatesDateDetail dateDetail = new OdsTMFXRebatesDateDetail();
                        dateDetail.setYear(year);
                        dateDetail.setBeginDate(dateRule.getBeginMonth());
                        dateDetail.setEndDate(dateRule.getEndMonth());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setIndustry(detail.getIndustry());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setBrand(detail.getBrandname());
                        dateDetail.setSku(detail.getSku());
                        dateDetail.setRewardType("销量对赌");
                        dateDetail.setBtTarget(bettingTarget);
                        dateDetail.setSaleAmount(detailInfo.getAmount());
                        dateDetail.setSaleNumber(detailInfo.getNumber());
                        for (OdsTMFXPointMaintain pointData:datePointList){
                            //对赌计算规则 C为闭 O为开
                            String intervalRule = pointData.getIntervalRule();
                            //计算类型 target 按目标  actual 按实际
                            String calculationType = pointData.getCalculationType();
                            //对赌系数  如果对赌系数为0 则按完成率计算
                            BigDecimal bettingCoefficient = pointData.getBettingCoefficient();
                            bettingCoefficient = bettingCoefficient.compareTo(new BigDecimal(0))==0?completion:bettingCoefficient;
                            if(intervalRule.equals("CC")){
                                //判断属于哪个台阶
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    btAmountStepRebates = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getAmountPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    dateDetail.setBtAmountPoint(pointData.getAmountPoint());//对赌返点
                                    dateDetail.setBtAmountStepRebates(btAmountStepRebates);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }else if(intervalRule.equals("CO")){
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                                    btAmountStepRebates = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getAmountPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    dateDetail.setBtAmountPoint(pointData.getAmountPoint());//对赌返点
                                    dateDetail.setBtAmountStepRebates(btAmountStepRebates);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }else if(intervalRule.equals("OC")){
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))==1) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    btAmountStepRebates = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getAmountPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    dateDetail.setBtAmountPoint(pointData.getAmountPoint());//对赌返点
                                    dateDetail.setBtAmountStepRebates(btAmountStepRebates);//对赌返利金额
                                    dateDetail.setBtTarget(bettingTarget);
                                    dateDetail.setBtCoefficient(bettingCoefficient);
                                    dateDetail.setBtCalculationType(calculationType);
                                }
                            }
                        }
                        odsTMFXRebatesDateDetailDao.create(dateDetail);
                        BigDecimal btAmountStepRebates1 = rebatesMonthly.getBtAmountStepRebates()==null?new BigDecimal("0"):rebatesMonthly.getBtAmountStepRebates();
                        rebatesMonthly.setBtAmountStepRebates(btAmountStepRebates1.add(btAmountStepRebates));//对赌返利金额
                        monthlyCashAmount = monthlyCashAmount.add(btAmountStepRebates);
                    }
                }
            }


            //SKU销量对赌
            queryPoint.setEcologyShop(detail.getSellpeople());
            queryPoint.setSku(detail.getSku());
            queryPoint.setRewardType("SKU销量对赌");
            pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            if (pointList.size()==0){
                queryPoint.setEcologyShop(null);
                pointList = odsTMFXPointMaintainDao.queryPointByDistribution3(queryPoint);
            }
            if (pointList.size()!=0){
                //销额对赌 销量对赌 SKU销额对赌 SKU销量对赌 查询汇总明细金额的时候是要到生态店的
                queryAmount.setSellpeople(detail.getSellpeople());
                queryAmount.setSku(queryPoint.getSku());
                SettlementInvoiceData volumeAndAmount = settlementInvoiceDataDao.querySummaryVolumeOrAmount(queryAmount);
                OdsTMFXPointMaintain point = pointList.get(0);
                //对赌目标
                String bettingTarget = point.getBettingTarget();
                if(null!= bettingTarget &&!bettingTarget.equals("")){
                    //SKU销量对赌完成率
                    BigDecimal completion = BigDecimal.ZERO;
                    completion = (new BigDecimal(volumeAndAmount.getNumber()).divide(new BigDecimal(bettingTarget))).setScale(5,BigDecimal.ROUND_HALF_UP);
                    //SKU销量赌返利金额
                    BigDecimal btFixedAmount = BigDecimal.ZERO;
                    for (OdsTMFXPointMaintain pointData:pointList){
                        //对赌计算规则 C为闭 O为开
                        String intervalRule = pointData.getIntervalRule();
                        //计算类型 target 按目标  actual 按实际
                        String calculationType = pointData.getCalculationType();
                        //对赌系数  如果对赌系数为0 则按完成率计算
                        BigDecimal bettingCoefficient = pointData.getBettingCoefficient();
                        bettingCoefficient = bettingCoefficient.compareTo(new BigDecimal(0))==0?completion:bettingCoefficient;
                        if(intervalRule.equals("CC")){
                            //判断属于哪个台阶
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                //台阶点位
                                if (pointData.getStepPoint()!=null){
                                    btFixedAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    rebatesMonthly.setBtFixedPoint(pointData.getStepPoint());//对赌返点
                                    rebatesMonthly.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                                }
                                //固定金额的
                                if (pointData.getFixedAmount()!=null){
                                    if(calculationType.equals("target")){
                                        //返利金额=对赌目标数量乘以固定金额
                                        btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(bettingTarget))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        //返利金额=销量乘以固定金额
                                        btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(detail.getNumber()))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }
                                    rebatesMonthly.setBtFixedPoint(pointData.getFixedAmount());//固定金额
                                    rebatesMonthly.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                                }
                            }
                        }else if(intervalRule.equals("CO")){
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                                //台阶点位
                                if (pointData.getStepPoint()!=null){
                                    btFixedAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    rebatesMonthly.setBtFixedPoint(pointData.getStepPoint());//对赌返点
                                    rebatesMonthly.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                                }
                                //固定金额的
                                if (pointData.getFixedAmount()!=null){
                                    if(calculationType.equals("target")){
                                        //返利金额=对赌目标数量乘以固定金额
                                        btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(bettingTarget))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        //返利金额=销量乘以固定金额
                                        btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(detail.getNumber()))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }
                                    rebatesMonthly.setBtFixedPoint(pointData.getFixedAmount());//固定金额
                                    rebatesMonthly.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                                }
                            }
                        }else if(intervalRule.equals("OC")){
                            if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))==1) &&
                                ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                //台阶点位
                                if (pointData.getStepPoint()!=null){
                                    btFixedAmount = (detail.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    rebatesMonthly.setBtFixedPoint(pointData.getStepPoint());//对赌返点
                                    rebatesMonthly.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                                }
                                //固定金额的
                                if (pointData.getFixedAmount()!=null){
                                    if(calculationType.equals("target")){
                                        //返利金额=对赌目标数量乘以固定金额
                                        btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(bettingTarget))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }else if(calculationType.equals("actual")){
                                        //返利金额=销量乘以固定金额
                                        btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(detail.getNumber()))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                    }
                                    rebatesMonthly.setBtFixedPoint(pointData.getFixedAmount());//固定金额
                                    rebatesMonthly.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                    rebatesMonthly.setBtTarget(bettingTarget);
                                    rebatesMonthly.setBtCoefficient(bettingCoefficient);
                                    rebatesMonthly.setBtCalculationType(calculationType);
                                }
                            }
                        }
                    }
                    monthlyCashAmount = monthlyCashAmount.add(btFixedAmount);
                }

            }



            if(type.equals("m")){
                // 2018-7-30  添加按日期类型计算
                queryDatePoint.setEcologyShop(detail.getSellpeople());
                queryDatePoint.setSku(detail.getSku());
                queryDatePoint.setRewardType("SKU销量对赌");
                List<OdsTMFXPointMaintain> dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);

                if (dateRuleList.size()==0){
                    queryDatePoint.setEcologyShop(null);
                    dateRuleList = odsTMFXPointMaintainDao.queryPointByDistribution5(queryDatePoint);
                }
                for(OdsTMFXPointMaintain dateRule:dateRuleList){
                    Map<String,Object> map = new HashMap<>();
                    map.put("year",year);
                    map.put("beginDate",dateRule.getBeginMonth());
                    map.put("endDate",dateRule.getEndMonth());
                    map.put("sellpeople",detail.getSellpeople());
                    map.put("industry",detail.getIndustry());
                    map.put("brandname",detail.getBrandname());
                    map.put("sku",detail.getSku());
                    //查询汇总数据
                    SettlementInvoiceData  detailInfo = settlementInvoiceDataDao.querySummaryVolumeOrAmountByDate(map);
                    queryDatePoint.setBeginMonth(dateRule.getBeginMonth());
                    queryDatePoint.setEndMonth(dateRule.getEndMonth());
                    //日期台阶点位
                    List<OdsTMFXPointMaintain> datePointList =odsTMFXPointMaintainDao.queryPointByDistribution6(queryDatePoint);
                    OdsTMFXPointMaintain point = datePointList.get(0);
                    //对赌目标
                    String bettingTarget = point.getBettingTarget();
                    if(null!= bettingTarget &&!bettingTarget.equals("")){
                        //SKU销量对赌完成率
                        BigDecimal completion = BigDecimal.ZERO;
                        completion = (new BigDecimal(detailInfo.getNumber()).divide(new BigDecimal(bettingTarget))).setScale(5,BigDecimal.ROUND_HALF_UP);
                        //SKU销量赌返利金额
                        BigDecimal btFixedAmount = BigDecimal.ZERO;
                        OdsTMFXRebatesDateDetail dateDetail = new OdsTMFXRebatesDateDetail();
                        dateDetail.setYear(year);
                        dateDetail.setBeginDate(dateRule.getBeginMonth());
                        dateDetail.setEndDate(dateRule.getEndMonth());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setIndustry(detail.getIndustry());
                        dateDetail.setEcologyShop(detail.getSellpeople());
                        dateDetail.setBrand(detail.getBrandname());
                        dateDetail.setSku(detail.getSku());
                        dateDetail.setRewardType("SKU销量对赌");
                        dateDetail.setSaleAmount(detailInfo.getAmount());
                        dateDetail.setSaleNumber(detailInfo.getNumber());
                        for (OdsTMFXPointMaintain pointData:datePointList){
                            //对赌计算规则 C为闭 O为开
                            String intervalRule = pointData.getIntervalRule();
                            //计算类型 target 按目标  actual 按实际
                            String calculationType = pointData.getCalculationType();
                            //对赌系数  如果对赌系数为0 则按完成率计算
                            BigDecimal bettingCoefficient = pointData.getBettingCoefficient();
                            bettingCoefficient = bettingCoefficient.compareTo(new BigDecimal(0))==0?completion:bettingCoefficient;
                            if(intervalRule.equals("CC")){
                                //判断属于哪个台阶
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    //台阶点位
                                    if (pointData.getStepPoint()!=null){
                                        btFixedAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        dateDetail.setBtFixedPoint(pointData.getStepPoint());//对赌返点
                                        dateDetail.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                        dateDetail.setBtTarget(bettingTarget);
                                        dateDetail.setBtCoefficient(bettingCoefficient);
                                        dateDetail.setBtCalculationType(calculationType);
                                    }
                                    //固定金额的
                                    if (pointData.getFixedAmount()!=null){
                                        if(calculationType.equals("target")){
                                            //返利金额=对赌目标数量乘以固定金额
                                            btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(bettingTarget))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        }else if(calculationType.equals("actual")){
                                            //返利金额=销量乘以固定金额
                                            btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(detailInfo.getNumber()))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        }
                                        dateDetail.setBtFixedPoint(pointData.getFixedAmount());//固定金额
                                        dateDetail.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                        dateDetail.setBtTarget(bettingTarget);
                                        dateDetail.setBtCoefficient(bettingCoefficient);
                                        dateDetail.setBtCalculationType(calculationType);
                                    }
                                }
                            }else if(intervalRule.equals("CO")){
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))>=0) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))==-1))){
                                    //台阶点位
                                    if (pointData.getStepPoint()!=null){
                                        btFixedAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        dateDetail.setBtFixedPoint(pointData.getStepPoint());//对赌返点
                                        dateDetail.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                        dateDetail.setBtTarget(bettingTarget);
                                        dateDetail.setBtCoefficient(bettingCoefficient);
                                        dateDetail.setBtCalculationType(calculationType);
                                    }
                                    //固定金额的
                                    if (pointData.getFixedAmount()!=null){
                                        if(calculationType.equals("target")){
                                            //返利金额=对赌目标数量乘以固定金额
                                            btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(bettingTarget))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        }else if(calculationType.equals("actual")){
                                            //返利金额=销量乘以固定金额
                                            btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(detailInfo.getNumber()))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        }
                                        dateDetail.setBtFixedPoint(pointData.getFixedAmount());//固定金额
                                        dateDetail.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                        dateDetail.setBtTarget(bettingTarget);
                                        dateDetail.setBtCoefficient(bettingCoefficient);
                                        dateDetail.setBtCalculationType(calculationType);
                                    }
                                }
                            }else if(intervalRule.equals("OC")){
                                if ((completion.compareTo(new BigDecimal(pointData.getBeginTarget()))==1) &&
                                    ("∞".equals(pointData.getEndTarget())||(!"∞".equals(pointData.getEndTarget())&&completion.compareTo(new BigDecimal(pointData.getEndTarget()))<=0))){
                                    //台阶点位
                                    if (pointData.getStepPoint()!=null){
                                        btFixedAmount = (detailInfo.getAmount().multiply(bettingCoefficient).multiply(pointData.getStepPoint())).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        dateDetail.setBtFixedPoint(pointData.getStepPoint());//对赌返点
                                        dateDetail.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                        dateDetail.setBtTarget(bettingTarget);
                                        dateDetail.setBtCoefficient(bettingCoefficient);
                                        dateDetail.setBtCalculationType(calculationType);
                                    }
                                    //固定金额的
                                    if (pointData.getFixedAmount()!=null){
                                        if(calculationType.equals("target")){
                                            //返利金额=对赌目标数量乘以固定金额
                                            btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(bettingTarget))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        }else if(calculationType.equals("actual")){
                                            //返利金额=销量乘以固定金额
                                            btFixedAmount = (pointData.getFixedAmount().multiply(new BigDecimal(detailInfo.getNumber()))).setScale(2,BigDecimal.ROUND_HALF_UP);
                                        }
                                        dateDetail.setBtFixedPoint(pointData.getFixedAmount());//固定金额
                                        dateDetail.setBtFixedAmount(btFixedAmount);//对赌返利金额
                                        dateDetail.setBtTarget(bettingTarget);
                                        dateDetail.setBtCoefficient(bettingCoefficient);
                                        dateDetail.setBtCalculationType(calculationType);
                                    }
                                }
                            }
                        }
                        odsTMFXRebatesDateDetailDao.create(dateDetail);
                        BigDecimal btFixedAmount1 = rebatesMonthly.getBtFixedAmount()==null?new BigDecimal("0"):rebatesMonthly.getBtFixedAmount();
                        rebatesMonthly.setBtFixedAmount(btFixedAmount1.add(btFixedAmount));//对赌返利金额
                        monthlyCashAmount = monthlyCashAmount.add(btFixedAmount);
                    }
                }
            }


            //结束
            rebatesMonthly.setMonthlyCashAmount(monthlyCashAmount);

            UUID uuid = UUID.randomUUID();
            String id = uuid.toString().replaceAll("-", "");
            rebatesMonthly.setId(id);
            odsTMFXRebatesMonthlyDetailDao.create(rebatesMonthly);
        }
        return null;

    }

    /**
     * 返利汇总 按生态店 产业 品牌
     */
    @Override
    @Transactional
    public void createSummary(String year,String month,String type){
        //生成数据之前先删除以前的数据
        odsTMFXRebatesSummaryDao.clearHistoryData(year,month,type);
        if ("m".equals(type)){
            //月度
            List<OdsTMFXRebatesMonthlyDetail> monthlySummaryList = odsTMFXRebatesMonthlyDetailDao.findMonthlySummaryList(year, month);
            for (OdsTMFXRebatesMonthlyDetail detail:monthlySummaryList){
                OdsTMFXRebatesSummary summary = new OdsTMFXRebatesSummary();
                summary.setEcologyShop(detail.getEcologyShop());
                summary.setYear(year);
                summary.setMonth(month);
                summary.setType(type);
                summary.setIndustry(detail.getIndustry());
                summary.setBrand(detail.getBrand());
                summary.setSaleAmount(detail.getSaleAmount());
                summary.setBaseRebatesAmount(detail.getBaseRebatesAmount());
                summary.setExtraRebatesAmount(detail.getExtraRebatesAmount());
                summary.setStepRebatesAmount(detail.getStepRebatesAmount());
                summary.setMarkRebatesAmount(detail.getMarkRebatesAmount());
                summary.setFixedAmount(detail.getFixedAmount());
                summary.setSkuStepRebatesAmount(detail.getSkuStepRebatesAmount());
                //兑现总额=汇总的返利金额    返利总额=兑现总额+营销费用+其他费用(cashAmount 返利总额  rebatesAmount 兑现总额) --2018.03.02改
                summary.setRebatesAmount(detail.getMonthlyCashAmount());
                //summary.setRebatesAmount(detail.getBaseRebatesAmount().add(detail.getExtraRebatesAmount()).add(detail.getStepRebatesAmount()).add(detail.getMarkRebatesAmount()).add(detail.getFixedAmount()).add(detail.getSkuStepRebatesAmount()));
                //查询目标计算完成率
                //查询目标 单店铺单品牌单产业的目标 --2018.03.02改
                //查询销额 单店铺单品牌单产业的销额 --2018.03.02改
                BigDecimal targetSummary = odsTMFXTargetMaintainDao.findTargetSummary(year, month, detail.getEcologyShop(),detail.getBrand(),detail.getIndustry(),type);
                BigDecimal amount = odsTMFXRebatesMonthlyDetailDao.findAmountSummary(year, month, detail.getEcologyShop(),detail.getBrand(),detail.getIndustry());
                if (targetSummary!=null&&targetSummary.compareTo(BigDecimal.ZERO)!=0){
                    BigDecimal target = targetSummary.multiply(new BigDecimal("10000"));
                    BigDecimal completion = amount.divide(target,2,BigDecimal.ROUND_HALF_UP);
                    summary.setTarget(target);
                    summary.setCompletion(completion);
                }
                UUID uuid = UUID.randomUUID();
                String id = uuid.toString().replaceAll("-", "");
                summary.setId(id);
                odsTMFXRebatesSummaryDao.insertSelective(summary);
            }
        }else{
            //季度年度
//            List<OdsDistributionDetails> details = odsDistributionDetailsDao.queryDetailToSummary2(year, month, type);
            List<SettlementInvoiceData> details = settlementInvoiceDataDao.queryDetailToSummary2(year, month, type,null);

            for (SettlementInvoiceData detail:details){
                OdsTMFXRebatesSummary summary = new OdsTMFXRebatesSummary();
                summary.setEcologyShop(detail.getSellpeople());
                summary.setYear(year);
                summary.setMonth(month);
                summary.setType(type);
                summary.setIndustry(detail.getIndustry());
                summary.setBrand(detail.getBrandname());
                summary.setSaleAmount(detail.getAmount());
                //查询目标计算完成率
                OdsTMFXTargetMaintain target = new OdsTMFXTargetMaintain();
                target.setEcologyShop(detail.getSellpeople());
                target.setYear(year);
                target.setMonth(month);
                target.setBrand(detail.getBrandname());
                target.setIndustry(detail.getIndustry());
                target.setType(type);
                OdsTMFXTargetMaintain findTarget = odsTMFXTargetMaintainDao.findTargetMaintain(target);
                if(findTarget!=null){
                    BigDecimal target2 = findTarget.getTarget().multiply(new BigDecimal("10000"));
                    BigDecimal completion = detail.getAmount().divide(target2,2,BigDecimal.ROUND_HALF_UP);
                    summary.setIndustryTarget(target2);
                    summary.setIndustryCompletion(completion);
                }
                //查询点位
                OdsTMFXPointMaintain queryPoint = new OdsTMFXPointMaintain();
                queryPoint.setYear(year);
                queryPoint.setMonth(month);
                queryPoint.setType(type);
                queryPoint.setIndustry(detail.getIndustry());
                queryPoint.setBrand(detail.getBrandname());
                queryPoint.setEcologyShop(detail.getSellpeople());
                if ("q".equals(type)){
                    queryPoint.setRewardType("季返");
                }else{
                    queryPoint.setRewardType("年返");
                }
                OdsTMFXPointMaintain point = odsTMFXPointMaintainDao.queryPointByDistribution2(queryPoint);
                if (point!=null){
                    if (point.getBasePoint()!=null){
                        summary.setBasePoint(point.getBasePoint());
                    }else{
                        summary.setBasePoint(BigDecimal.ZERO);
                    }
                }
                if (findTarget!=null&&point!=null){
                    BigDecimal baseAmount = null;
                    if (summary.getIndustryCompletion().compareTo(new BigDecimal("0.8"))>=0&&summary.getIndustryCompletion().compareTo(BigDecimal.ONE)==-1){
                        baseAmount = (summary.getBasePoint().multiply(summary.getIndustryCompletion()).multiply(detail.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP);
                    }else if (summary.getIndustryCompletion().compareTo(BigDecimal.ONE)>=0){
                        baseAmount = (summary.getBasePoint().multiply(detail.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP);
                    }else{
                        baseAmount = BigDecimal.ZERO;
                    }
                    summary.setBaseRebatesAmount(baseAmount);
                    summary.setRebatesAmount(baseAmount);
                }
                UUID uuid = UUID.randomUUID();
                String id = uuid.toString().replaceAll("-", "");
                summary.setId(id);
                odsTMFXRebatesSummaryDao.insertSelective(summary);
            }
        }

    }

    /**
     * 返利汇总 按生态店
     */
    @Override
    @Transactional
    public String createShopData(String year,String month,String type){
        //生成数据之前先清除历史数据
        odsTMFXShopSummaryDao.clearHistoryData(year,month,type);
        List<OdsTMFXRebatesSummary> summaries = odsTMFXRebatesSummaryDao.querySummaryByShop(year, month, type);
        if (summaries==null||summaries.isEmpty()){
            return "没有查询到产业汇总数据";
        }
        for (OdsTMFXRebatesSummary summary:summaries){
            OdsTMFXShopSummary shop = new OdsTMFXShopSummary();
            shop.setYear(year);
            shop.setMonth(month);
            shop.setType(type);
            shop.setEcologyShop(summary.getEcologyShop());
            //目标通过查询出来的 完成率改成通过计算出来的 完成率=销售额/目标  --2018.03.12
            BigDecimal targetSummary = odsTMFXTargetMaintainDao.findTargetSummary(year,month,summary.getEcologyShop(),null,null,type);
            if (targetSummary!=null&&targetSummary.compareTo(BigDecimal.ZERO)!=0){
                BigDecimal target = targetSummary.multiply(new BigDecimal("10000"));
                BigDecimal completion = summary.getSaleAmount().divide(target,2,BigDecimal.ROUND_HALF_UP);
                shop.setTarget(target);
                shop.setCompletion(completion);
            }
//            shop.setCompletion(summary.getCompletion());
            shop.setSaleAmount(summary.getSaleAmount());
            //shop.setYxCost(summary.getYxCost());
            //shop.setQtCost(summary.getQtCost());
            shop.setMonthlyCashAmount(summary.getRebatesAmount());
//            shop.setRebatesAmount(summary.getYxCost().add(summary.getQtCost().add(summary.getRebatesAmount())));
            //查询年度签约目标
            BigDecimal yearTarget = odsTMFXTargetMaintainDao.findYearTarget(year,summary.getEcologyShop());
            shop.setYearTarget(yearTarget);
            /*----------------------- 2018.03.09 整改去掉营销费用 返利总额=其他费用+兑现总额 --------------------------*/
            //去营销费用表查询营销费用跟其他费用
//            BigDecimal yxAmount = odsTMFXPromotionCostDao.queryYXCostFromBrand(year, month, null, summary.getEcologyShop());
            OdsTMFXPromotionCost query = new OdsTMFXPromotionCost();
            query.setEcologyShop(summary.getEcologyShop());
            query.setYear(year);
            query.setMonth(month);
            BigDecimal qtAmount = odsTMFXPromotionCostDao.queryQTCostAmount(query);
//            shop.setYxCost(yxAmount);
            shop.setQtCost(qtAmount);
//            shop.setRebatesAmount(summary.getRebatesAmount().add(yxAmount).add(qtAmount));
            shop.setRebatesAmount(summary.getRebatesAmount().add(qtAmount));
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString().replaceAll("-", "");
            shop.setId(id);
            odsTMFXShopSummaryDao.insertSelective(shop);
        }
        return null;
    }

    /**
     * 按产业汇总生成营销费用及比例
     * @param year
     * @param month
     * @param type
     */
    @Override
    @Transactional
    public String createPromotionCostSummary(String year,String month,String type){
        List<OdsTMFXRebatesSummary> list = odsTMFXRebatesSummaryDao.queryAllRebatesSummary(year, month, type);
        if (list==null||list.isEmpty()){
            return "查询不到产业汇总数据";
        }
        for (OdsTMFXRebatesSummary summary:list){
            OdsTMFXRebatesSummary update = new OdsTMFXRebatesSummary();
            update.setId(summary.getId());
            /*//根据品牌查询店铺总的返点金额
            BigDecimal rebatesAmount = odsTMFXRebatesSummaryDao.queryRebatesAmountByBrand(year, month, type,summary.getBrand(),summary.getEcologyShop());*/
            //查询其他费用
            OdsTMFXPromotionCost query = new OdsTMFXPromotionCost();
            query.setYear(year);
            query.setMonth(month);
            query.setEcologyShop(summary.getEcologyShop());
            query.setBrand(summary.getBrand());
            query.setIndustry(summary.getIndustry());
            BigDecimal qtCostAmount = odsTMFXPromotionCostDao.queryQTCostAmount(query);
            //rebatesAmount = rebatesAmount.add(qtCostAmount);
            /* -----------------2018.03.09整改  去掉营销费用 返利总额=其他费用+兑现总额----------------  */
            //计算分摊比例   单店铺单品牌单产业销额/单店铺总销额 (新版)
//            BigDecimal proportion = null;
//            /*if (rebatesAmount!=null&&rebatesAmount.compareTo(BigDecimal.ZERO)!=0){
//                proportion = (summary.getRebatesAmount().add(qtCostAmount)).divide(rebatesAmount,4,BigDecimal.ROUND_HALF_UP);
//            }else{
//                proportion = BigDecimal.ZERO;
//            }
//            //查询单店单品牌的营销费用
//            BigDecimal yx = odsTMFXPromotionCostDao.queryYXCostFromBrand(year,month,summary.getBrand(),summary.getEcologyShop());*/
//            //查询单店铺的总销额
//            BigDecimal summaryAmount = odsTMFXRebatesSummaryDao.querySummaryAmount(year, month, summary.getEcologyShop());
//            if (summaryAmount != null && summaryAmount.compareTo(BigDecimal.ZERO) != 0) {
//                proportion = summary.getSaleAmount().divide(summaryAmount, 6, BigDecimal.ROUND_HALF_UP);
//            } else {
//                proportion = BigDecimal.ZERO;
//            }
//            //查询单店铺营销费用总额
//            BigDecimal yx = odsTMFXPromotionCostDao.queryYXCostFromBrand(year, month, null, summary.getEcologyShop());
//            BigDecimal yxCost = yx.multiply(proportion).setScale(2,BigDecimal.ROUND_HALF_UP);
//            update.setShareProportion(proportion);
//            update.setYxCost(yxCost);
            update.setQtCost(qtCostAmount);
//            update.setCashAmount(summary.getRebatesAmount().add(yxCost).add(qtCostAmount));
            update.setCashAmount(summary.getRebatesAmount().add(qtCostAmount));
            odsTMFXRebatesSummaryDao.updateByPrimaryKeySelective(update);
        }
        return null;
    }

    /**
     * 只按品牌产业汇总
     * @param year
     * @param month
     * @param type
     */
    @Override
    @Transactional
    public void createIndustrySummary(String year,String month,String type){
        //生成数据之前先清除历史数据
        odsTMFXIndustrySummaryDao.clearHistoryData(year,month,type);
        List<OdsTMFXRebatesSummary> list = odsTMFXRebatesSummaryDao.queryByIndustry(year, month, type);
        for (OdsTMFXRebatesSummary summary:list){
            OdsTMFXIndustrySummary create = new OdsTMFXIndustrySummary();
            create.setYear(summary.getYear());
            create.setMonth(summary.getMonth());
            create.setType(summary.getType());
            create.setIndustry(summary.getIndustry());
            create.setBrand(summary.getBrand());
            create.setMonthlyCashAmount(summary.getRebatesAmount());
            //create.setYxCost(summary.getYxCost());
            create.setSaleAmount(summary.getSaleAmount());
//            create.setRebatesAmount(summary.getYxCost().add(summary.getRebatesAmount()));
            /*----------------------- 2018.03.09 整改去掉营销费用 返利总额=其他费用+兑现总额 --------------------------*/
            //计算营销费用   所有店铺所有品牌单产业销额/所有店铺所有品牌所有产业销额）*录入的所有店铺的营销费用总额
            //单产业销额
//            BigDecimal industryAmount = odsTMFXRebatesSummaryDao.queryAmountByIndustry(year, month, type, summary.getBrand(),summary.getIndustry());
//            //总的销额
//            BigDecimal summaryAmount = odsTMFXRebatesSummaryDao.queryAmountByIndustry(year, month, type, null, null);
//            //营销费用总额
//            BigDecimal yxSummary = odsTMFXPromotionCostDao.queryYXCostFromBrand(year, month, null, null);
//            BigDecimal yxCost = BigDecimal.ZERO;
//            if (summaryAmount != null && summaryAmount.compareTo(BigDecimal.ZERO) != 0) {
//                BigDecimal proportion = industryAmount.divide(summaryAmount, 6, BigDecimal.ROUND_HALF_UP);
//                yxCost = proportion.multiply(yxSummary).setScale(2, BigDecimal.ROUND_HALF_UP);
//            }
//            create.setYxCost(yxCost);
            //查询其他费用金额
            OdsTMFXPromotionCost query = new OdsTMFXPromotionCost();
            query.setYear(year);
            query.setMonth(month);
            query.setBrand(summary.getBrand());
            query.setIndustry(summary.getIndustry());
            BigDecimal qtCost = odsTMFXPromotionCostDao.queryQTCostAmount(query);
            create.setQtCost(qtCost);
//            create.setRebatesAmount(summary.getRebatesAmount().add(yxCost).add(qtCost));
            create.setRebatesAmount(summary.getRebatesAmount().add(qtCost));
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString().replaceAll("-", "");
            create.setId(id);
            odsTMFXIndustrySummaryDao.insertSelective(create);
        }
    }

    @Override
    @Transactional
    public void createPeculiar(OdsTMFXPeculiarCategory old,OdsTMFXPeculiarCategory newcome){
        old.setFlag("N");
        odsTMFXPeculiarCategoryDao.updateByPrimaryKeySelective(old);
        odsTMFXPeculiarCategoryDao.insertSelective(newcome);
    }

}
