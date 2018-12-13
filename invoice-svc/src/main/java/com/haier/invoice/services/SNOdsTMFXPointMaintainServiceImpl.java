package com.haier.invoice.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.SNOdsTMFXPointMaintainService;
import com.haier.shop.model.SNOdsTMFXPointMaintain;
import com.haier.shop.service.SNOdsTMFXPointMaintainDataService;

/**
 * Created by jtbshan on 2018/5/22.
 */
@Service("SNOdsTMFXPointMaintainServiceImpl")
public  class SNOdsTMFXPointMaintainServiceImpl implements SNOdsTMFXPointMaintainService {
    private static final Logger log = LogManager.getLogger(SNOdsTMFXPointMaintainServiceImpl.class);

    @Autowired
    private SNOdsTMFXPointMaintainDataService sNOdsTMFXPointMaintainDataService;


    public JSONObject paging (SNOdsTMFXPointMaintain param, PagerInfo pagerInfo){
        return sNOdsTMFXPointMaintainDataService.paging(param,pagerInfo);
    }
    public JSONObject export (SNOdsTMFXPointMaintain param){
        return sNOdsTMFXPointMaintainDataService.export(param);
    }
    public ServiceResult<JSONObject> insert (SNOdsTMFXPointMaintain param){
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            int count = sNOdsTMFXPointMaintainDataService.insert(param);
            if(count == 1){
                serviceResult.setSuccess(true);
                serviceResult.setMessage("创建成功");
            }else {
                serviceResult.setSuccess(false);
                serviceResult.setMessage("创建失败");
            }
        }catch (Exception e){
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("创建异常",e);
        }
        return serviceResult;
    }

    public ServiceResult<JSONObject> update (SNOdsTMFXPointMaintain param){
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            int count = sNOdsTMFXPointMaintainDataService.update(param);
            if(count == 1){
                serviceResult.setSuccess(true);
                serviceResult.setMessage("更新成功");
            }else {
                serviceResult.setSuccess(false);
                serviceResult.setMessage("更新失败");
            }
        }catch (Exception e){
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("更新异常",e);
        }
        return serviceResult;
    }
    public ServiceResult<JSONObject>  delBatch(List<String> ids){
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            int count = sNOdsTMFXPointMaintainDataService.delBatch(ids);
            if(count > 0){
                serviceResult.setSuccess(true);
                serviceResult.setMessage("删除成功");
            }else {
                serviceResult.setSuccess(false);
                serviceResult.setMessage("删除失败");
            }
        }catch (Exception e){
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("删除异常",e);
        }
        return serviceResult;
    }


    @Override
    public ServiceResult<String> checkInfo(List<List<String>> list) {
        try {

            ServiceResult<String> result = new ServiceResult<String>();
            StringBuffer info = new StringBuffer();
            List<List<String>> newList = new ArrayList<List<String>>();
            if (list!=null){
                for (int i=0;i<list.size();i++){
                    List<String> excel = list.get(i);
                    this.isNullCheck(i,excel.get(1),"年度",info);
                    this.isNullCheck(i,excel.get(2),"期间起",info);
                    this.isNullCheck(i,excel.get(3),"期间止",info);
                    this.isNullCheck(i,excel.get(4),"产业",info);
                /*this.isNullCheck(i,excel.getCol6(),"型号描述",info);*/
                    this.isNullCheck(i,excel.get(7),"品牌",info);
                    this.isNullCheck(i,excel.get(8),"奖励类型",info);
                    this.isNullCheck(i,excel.get(9),"类型",info);
                /*this.isNullCheck(i,excel.getCol10(),"目标起",info);
                this.isNullCheck(i,excel.getCol11(),"目标止",info);
                this.isNullCheck(i,excel.getCol12(),"基础点位",info);
                this.isNullCheck(i,excel.getCol13(),"额外返点",info);*/
                    /**
                     * 添加奖励类型  2018-07-25
                     */
                    if(excel.get(8).equals("销额对赌") ||excel.get(8).equals("销量对赌") ||excel.get(8).equals("SKU销额对赌") ||excel.get(8).equals("SKU销量对赌")){
                        this.isNullCheck(i,excel.get(10),"目标起",info);
                        this.isNullCheck(i,excel.get(11),"目标止",info);
                        this.isNullCheck(i,excel.get(21),"对赌目标",info);
                        this.isNullCheck(i,excel.get(22),"对赌系数",info);
                    }
                    for(int j=i+1;j<list.size();j++){
                        List<String> excel2 = list.get(j);
                        if (excel.get(0).equals(excel2.get(0))&&excel.get(1).equals(excel2.get(1))&&excel.get(2).equals(excel2.get(2))
                            &&excel.get(3).equals(excel2.get(3))&&excel.get(4).equals(excel2.get(4))
                            &&excel.get(9).equals(excel2.get(9))&&excel.get(5).equals(excel2.get(5))
                            &&excel.get(7).equals(excel2.get(7))&&excel.get(8).equals(excel2.get(8))
                            &&excel.get(10).equals(excel2.get(10))&&excel.get(11).equals(excel2.get(11))){
                            info.append("第" + (i + 1) + "行和第" + (j + 1) + "行重复:");
                        }

                    }
                    newList.add(excel);
                }
                list.clear();
                list.addAll(newList);
                if (!StringUtils.isEmpty(info.toString())){
                    result.setSuccess(false);
                    result.setMessage(info.toString());
                    result.setError("error",info.toString());
                }else{
                    result.setSuccess(true);
                    result.setMessage("校验通过");
                }
            }else{
                result.setSuccess(false);
                result.setMessage("导入数据为空");
                result.setError("error","导入数据为空");
            }
            return result;
        }catch (Exception e){
            log.error("系统异常："+e.getMessage());
            return null;
        }


    }


    /**
     * 导入处理
     *
     * @param list
     * @param user
     * @return
     */
    @Override
    public ServiceResult<String> execExcel(List<List<String>> list, String user) {
        ServiceResult<String> result = new ServiceResult<String>();
        StringBuffer info = new StringBuffer();
        List<SNOdsTMFXPointMaintain> newList = new ArrayList<SNOdsTMFXPointMaintain>();
        for(int i=0;i<list.size();i++){
            List<String> excel = list.get(i);
            SNOdsTMFXPointMaintain opm = new SNOdsTMFXPointMaintain();
            opm.setEcologyShop(excel.get(0));
            opm.setYear(excel.get(1));
            opm.setBeginMonth(excel.get(2));
            opm.setEndMonth(excel.get(3));
            opm.setIndustry(excel.get(4));
            opm.setSku(excel.get(5));
            opm.setModelDes(excel.get(6));
            opm.setBrand(excel.get(7));
            opm.setRewardType(excel.get(8));
            if ("月度".equals(excel.get(9))){
                opm.setType("m");
            }else if("季度".equals(excel.get(9))){
                opm.setType("q");
            }else if("年度".equals(excel.get(9))){
                opm.setType("y");
            }else if("日期".equals(excel.get(9))){
                opm.setType("d");
            }
            opm.setBeginTarget(excel.get(10));
            opm.setEndTarget(excel.get(11));
            if (!StringUtils.isEmpty(excel.get(12))){
                opm.setBasePoint(new BigDecimal(excel.get(12)));
            }
            if (!StringUtils.isEmpty(excel.get(13))){
                opm.setExtraRebate(new BigDecimal(excel.get(13)));
            }
            if (!StringUtils.isEmpty(excel.get(14))){
                opm.setStepPoint(new BigDecimal(excel.get(14)));
            }
            if (!StringUtils.isEmpty(excel.get(15))){
                opm.setMarkPoint(new BigDecimal(excel.get(15)));
            }
            if (!StringUtils.isEmpty(excel.get(16))){
                opm.setFixedAmount(new BigDecimal(excel.get(16)));
            }
            if (!StringUtils.isEmpty(excel.get(17))){
                opm.setSkuStepPoint(new BigDecimal(excel.get(17)));
            }
            if (!StringUtils.isEmpty(excel.get(18))){
                opm.setAmountPoint(new BigDecimal(excel.get(18)));
            }

            //对赌计算规则
            if (!StringUtils.isEmpty(excel.get(19))){
                opm.setIntervalRule(excel.get(19));
            }
            //对赌计算类型
            if (!StringUtils.isEmpty(excel.get(20))){
                if ("按目标".equals(excel.get(20))){
                    opm.setCalculationType("target");
                }else {
                    opm.setCalculationType("actual");
                }
            }
            //对赌目标
            if (!StringUtils.isEmpty(excel.get(21))){
                opm.setBettingTarget(excel.get(21));
            }
            //对赌系数
            if (!StringUtils.isEmpty(excel.get(22))){
                opm.setBettingCoefficient(new BigDecimal(excel.get(22)));
            }

            opm.setCreateBy(user);
            newList.add(opm);
            if ("m".equals(opm.getType())&&(!StringUtils.isNumeric(opm.getBeginMonth())||!StringUtils.isNumeric(opm.getEndMonth()))){
                info.append("第" + (i + 1) + "行:月度期间与类型不匹配!");
            }
            if ("q".equals(opm.getType())){
                if (StringUtils.isNumeric(opm.getBeginMonth())||StringUtils.isNumeric(opm.getEndMonth())||"N".equals(opm.getBeginMonth())||"N".equals(opm.getEndMonth())||
                        !opm.getBeginMonth().equals(opm.getEndMonth())){
                    info.append("第" + (i + 1) + "行:季度期间与类型不匹配!");
                }
                if (!"季返".equals(opm.getRewardType())){
                    info.append("第" + (i + 1) + "行:季度的奖励类型只能是季返!");
                }
            }
            if ("y".equals(opm.getType())){
                if (!"N".equals(opm.getBeginMonth())||!"N".equals(opm.getEndMonth())) {
                    info.append("第" + (i + 1) + "行:年度期间与类型不匹配!");
                }
            }
            if ("彩电".equals(opm.getIndustry())&&!"m".equals(opm.getType())){
                info.append("第" + (i + 1) + "行:彩电年度季度都不参与!");
            }
            if ("n".equals(opm.getType())&&!"年返".equals(opm.getRewardType())){
                info.append("第" + (i + 1) + "行:年度的奖励类型只能是年返!");
            }
            if(("SKU".equals(opm.getRewardType())||"剩余型号".equals(opm.getRewardType()))&&(opm.getBasePoint()==null||opm.getExtraRebate()!=null||
                    opm.getStepPoint()!=null||opm.getMarkPoint()!=null||opm.getFixedAmount()!=null||opm.getSkuStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:SKU跟剩余型号的只维护基础点位!");
            }
            if(("销额台阶".equals(opm.getRewardType()))&&(opm.getStepPoint()==null||opm.getExtraRebate()!=null||
                    opm.getBasePoint()!=null||opm.getMarkPoint()!=null||opm.getFixedAmount()!=null||opm.getSkuStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:销额台阶的只维护台阶点位!");
            }
            if(("销量台阶".equals(opm.getRewardType()))&&(opm.getAmountPoint()==null||opm.getExtraRebate()!=null||opm.getStepPoint()!=null||
                    opm.getBasePoint()!=null||opm.getMarkPoint()!=null||opm.getFixedAmount()!=null||opm.getSkuStepPoint()!=null)){
                info.append("第"+(i+1)+"行数据:销额台阶的只维护台阶点位!");
            }
            if(("SKU销额台阶".equals(opm.getRewardType()))&&(opm.getSkuStepPoint()==null||opm.getExtraRebate()!=null||
                    opm.getBasePoint()!=null||opm.getMarkPoint()!=null||opm.getFixedAmount()!=null||opm.getStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:SKU销额台阶的只维护SKU销额台阶点位!");
            }
            if(("品类专卖店".equals(opm.getRewardType()))&&(opm.getExtraRebate()==null||opm.getStepPoint()!=null||opm.getEcologyShop()==null||
                    opm.getBasePoint()!=null||opm.getMarkPoint()!=null||opm.getFixedAmount()!=null||opm.getSkuStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:品类专卖店的要维护到生态店和额外点位!");
            }
            if(("月度达标".equals(opm.getRewardType()))&&(opm.getMarkPoint()==null||opm.getStepPoint()!=null||
                    opm.getBasePoint()!=null||opm.getExtraRebate()!=null||opm.getFixedAmount()!=null||opm.getSkuStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:月度达标的只维护到达标点位!");
            }
            if(("SKU销量台阶".equals(opm.getRewardType()))&&((opm.getStepPoint()!=null&&opm.getFixedAmount()!=null)||(opm.getStepPoint()==null&&opm.getFixedAmount()==null)||
                    opm.getBasePoint()!=null||opm.getExtraRebate()!=null||opm.getMarkPoint()!=null||opm.getSkuStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:SKU销量台阶的只维护台阶点位跟固定金额其中的一种!");
            }
            if(("销额台阶".equals(opm.getRewardType())||"月度达标".equals(opm.getRewardType())||"SKU销量台阶".equals(opm.getRewardType())||"SKU销额台阶".equals(opm.getRewardType())||"销量台阶".equals(opm.getRewardType()))&&
                    (StringUtils.isEmpty(opm.getBeginTarget())||StringUtils.isEmpty(opm.getEndTarget()))){
                info.append("第"+(i+1)+"行数据:台阶政策需维护目标起止!");
            }
            if(("SKU".equals(opm.getRewardType())||"SKU销额台阶".equals(opm.getRewardType())||"SKU销量台阶".equals(opm.getRewardType()))&&StringUtils.isEmpty(opm.getSku())){
                info.append("第"+(i+1)+"行数据:SKU点位政策需维护SKU!");
            }



            /*
            2018-07-25   因添加对赌奖励类型添加以下校验规则
            * */

            if(("销额对赌".equals(opm.getRewardType()))&&(opm.getStepPoint()==null||opm.getExtraRebate()!=null||
                opm.getBasePoint()!=null||opm.getMarkPoint()!=null||opm.getFixedAmount()!=null||opm.getSkuStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:销额对赌的只维护台阶点位!");
            }
            if(("销量对赌".equals(opm.getRewardType()))&&(opm.getAmountPoint()==null||opm.getExtraRebate()!=null||opm.getStepPoint()!=null||
                opm.getBasePoint()!=null||opm.getMarkPoint()!=null||opm.getFixedAmount()!=null||opm.getSkuStepPoint()!=null)){
                info.append("第"+(i+1)+"行数据:销量对赌的只维护台阶点位!");
            }
            if(("SKU销额对赌".equals(opm.getRewardType()))&&(opm.getSkuStepPoint()==null||opm.getExtraRebate()!=null||
                opm.getBasePoint()!=null||opm.getMarkPoint()!=null||opm.getFixedAmount()!=null||opm.getStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:SKU销额对赌的只维护SKU销额台阶点位!");
            }
            if(("SKU销量对赌".equals(opm.getRewardType()))&&((opm.getStepPoint()!=null&&opm.getFixedAmount()!=null)||(opm.getStepPoint()==null&&opm.getFixedAmount()==null)||
                opm.getBasePoint()!=null||opm.getExtraRebate()!=null||opm.getMarkPoint()!=null||opm.getSkuStepPoint()!=null||opm.getAmountPoint()!=null)){
                info.append("第"+(i+1)+"行数据:SKU销量对赌的只维护台阶点位跟固定金额其中的一种!");
            }
            if(("销额对赌".equals(opm.getRewardType())||"销量对赌".equals(opm.getRewardType())||"SKU销额对赌".equals(opm.getRewardType())||"SKU销量对赌".equals(opm.getRewardType()))&&
                (StringUtils.isEmpty(opm.getBeginTarget())||StringUtils.isEmpty(opm.getEndTarget()))){
                info.append("第"+(i+1)+"行数据:对赌台阶政策需维护目标起止!");
            }


            List<SNOdsTMFXPointMaintain> repetition = sNOdsTMFXPointMaintainDataService.queryRepetition(opm);
            if (repetition.size()>0){
                info.append("第"+(i+1)+"行数据已存在");
            }
        }
        if (newList.isEmpty()){
            info.append("导入的数据为空");
        }
        if (StringUtils.isEmpty(info.toString())){
            try {
                sNOdsTMFXPointMaintainDataService.bulkImport(newList);
                result.setResult("导入成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setMessage("导入失败:"+e.getMessage());
                result.setError("error","导入失败:"+e.getMessage());
            }
        }else{
            result.setSuccess(false);
            result.setMessage(info.toString());
            result.setError("error",info.toString());
        }

        return result;
    }


    public void isNullCheck(int index, String col, String colname, StringBuffer result) {
        if (StringUtils.isEmpty(col)) {
            result.append("第" + (index + 1) + "行:" + colname + "为空;");
        }
    }

    @Override
    public String getLogInfo() {
        return sNOdsTMFXPointMaintainDataService.getLogInfo("SN");
    }
}
