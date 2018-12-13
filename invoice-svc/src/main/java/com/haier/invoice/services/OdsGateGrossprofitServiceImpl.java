package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.OdsGateGrossprofitService;
import com.haier.shop.dto.OdsGateGrossprofitDto;
import com.haier.shop.model.Brands;
import com.haier.shop.model.OdsGateGrossprofit;
import com.haier.shop.model.ProductCates;
import com.haier.shop.service.BrandsService;
import com.haier.shop.service.OdsGateGrossprofitDataService;
import com.haier.shop.service.ProductCatesService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/26.
 */
@Component
public class OdsGateGrossprofitServiceImpl implements OdsGateGrossprofitService {
    private static final Logger log = LogManager.getLogger(OdsGateGrossprofitServiceImpl.class);

    @Autowired
    private OdsGateGrossprofitDataService odsGateGrossprofitDataService;

    @Autowired
    private BrandsService brandsService;

    @Autowired
    private ProductCatesService productCatesService;

    @Override
    public JSONObject paging(OdsGateGrossprofitDto param, PagerInfo pagerInfo) {
        return odsGateGrossprofitDataService.paging(param,pagerInfo);
    }

    @Override
    public ServiceResult<JSONObject> insert(OdsGateGrossprofit param) {
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            int count = odsGateGrossprofitDataService.insert(param);
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

    @Override
    public ServiceResult<JSONObject> update(OdsGateGrossprofit param) {
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            int count = odsGateGrossprofitDataService.update(param);
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

    @Override
    public ServiceResult<JSONObject> delBatch(List<String> ids) {
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            int count = odsGateGrossprofitDataService.delBatch( ids);
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
    public List<Map<String, Object>> getBrands() {
        return brandsService.getBrands();
    }


    @Override
    public List<ProductCates> selectCateName() {
        return productCatesService.selectCateName();
    }

    @Override
    public ServiceResult<String> checkInfo(List<List<String>> list) {
        ServiceResult<String> result = new ServiceResult<String>();
        StringBuffer info = new StringBuffer();
        List<List<String>> newList = new ArrayList<List<String>>();
        if (list!=null){
            for (int i=0;i<list.size();i++){
                List<String> excel = list.get(i);
                this.isNullCheck(i,excel.get(0),"品牌",info);
                this.isNullCheck(i,excel.get(1),"品类",info);
                this.isNullCheck(i,excel.get(2),"毛利率",info);
                this.isNullCheck(i,excel.get(3),"开始时间",info);
                this.isNullCheck(i,excel.get(4),"结束时间",info);
                for(int j=i+1;j<list.size();j++){
                    List<String> excel2 = list.get(j);
                    if (excel.get(0).equals(excel2.get(0))&&excel.get(1).equals(excel2.get(1))&&excel.get(2).equals(excel2.get(2))
                            &&excel.get(3).equals(excel2.get(3))&&excel.get(4).equals(excel2.get(4))
                            &&excel.get(5).equals(excel2.get(5))&&excel.get(6).equals(excel2.get(6))){
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
    }

    @Override
    public ServiceResult<String> execExcel(List<List<String>> list, String user) {
        ServiceResult<String> result = new ServiceResult<String>();
        StringBuffer info = new StringBuffer();
        List<OdsGateGrossprofit> newList = new ArrayList<OdsGateGrossprofit>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            for(int i=0;i<list.size();i++){
                List<String> excel = list.get(i);
                OdsGateGrossprofit opm = new OdsGateGrossprofit();
                BigDecimal GrossProfit = new BigDecimal(excel.get(2));

                Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(excel.get(3));
                String d2 = format.format(d1);
                Date beginTime=format.parse(d2);

                Date t1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(excel.get(4));
                String t2 = format.format(t1);
                Date endTime=format.parse(t2);

                opm.setBrand(excel.get(0));
                opm.setCateGory(excel.get(1));
                opm.setGrossProfit(GrossProfit);
                opm.setBeginTime(beginTime);
                opm.setEndTime(endTime);
                opm.setCreateBy(user);
                newList.add(opm);
                List<OdsGateGrossprofit> repetition = odsGateGrossprofitDataService.queryRepetition(opm);
                if (repetition.size()>0){
                    info.append("第"+(i+1)+"行数据已存在");
                }
            }
        }catch(Exception e){
            log.error("导入异常",e);
        }
        if (newList.isEmpty()){
            info.append("导入的数据为空");
        }
        if (StringUtils.isEmpty(info.toString())){
            try {
                odsGateGrossprofitDataService.bulkImport(newList);
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
}
