package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.dao.settleCenter.OdsGateGrossprofitDao;
import com.haier.shop.dao.settleCenter.OdsGatePriceDao;
import com.haier.shop.dao.settleCenter.OdsUserCategoryBrandDao;
import com.haier.shop.dao.shopread.OdsGatePriceReadDao;
import com.haier.shop.dao.shopwrite.BrandsWriteDao;
import com.haier.shop.dao.shopwrite.ProductsWriteDao;
import com.haier.shop.model.*;
import com.haier.shop.service.OdsGatePriceDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OdsGatePriceDataServiceImpl implements OdsGatePriceDataService{
    @Autowired
    OdsGatePriceDao odsGatePriceDao;
    @Autowired
    OdsGatePriceReadDao odsGatePriceReadDao;
    @Autowired
    ProductsWriteDao productsWriteDao;
    @Autowired
    OdsGateGrossprofitDao odsGateGrossprofitDao;
    @Autowired
    BrandsWriteDao brandsWriteDao;
    @Autowired
    OdsUserCategoryBrandDao odsUserCategoryBrandDao;
    @Override
    public Map<String, Object> queryGatePrice(Map<String, Object> params) {
        List<OdsUserCategoryBrand> list=odsUserCategoryBrandDao.queryUserCategoryBrandList(params);
        params.put("userCate",list);
        //获取开单列表List
        List<GatePrice> result = odsGatePriceDao.queryGatePriceList(params);
        //获得条数
        int resultcount = odsGatePriceDao.queryGatePriceCount(params);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);
        return retMap;
    }

    @Override
    public List<GatePrice> getExportGatePriceList(Map<String, Object> params) {
        List<OdsUserCategoryBrand> list=odsUserCategoryBrandDao.queryUserCategoryBrandList(params);
        params.put("userCate",list);
        List<GatePrice> odsGatePriceList = odsGatePriceDao.getExportGatePriceList(params);
        return odsGatePriceList;
    }

    @Override
    @Transactional
    public ServiceResult<String> bulkImport(List<GatePrice> list) {

        ServiceResult<String> result = new ServiceResult<>();
        if (list == null) {
            result.setSuccess(false);
            return result;
        }
        StringBuffer sbf=new StringBuffer();

        for (GatePrice gatePriceExcel : list) {
            //组织数据
            GatePrice odsGatePrice = new GatePrice();
            //验证SKU
            GatePrice getOnSaleProductsOut=odsGatePriceDao.getBySku(gatePriceExcel.getSku());
            if(null==getOnSaleProductsOut){
                result.setSuccess(false);
                result.setMessage(JSONObject.toJSONString(gatePriceExcel.getSku()+"不存在"));
                return result;
            }else if(StringUtils.isBlank(getOnSaleProductsOut.getBrandName()) ||
                    StringUtils.isBlank(getOnSaleProductsOut.getCateName())){
                result.setSuccess(false);
                result.setMessage(JSONObject.toJSONString(gatePriceExcel.getSku()+"品牌或品牌没有维护"));
                return result;
            }else{
                odsGatePrice.setBrand(getOnSaleProductsOut.getBrandName());
                odsGatePrice.setCateGory(getOnSaleProductsOut.getCateName());
                odsGatePrice.setVersion(getOnSaleProductsOut.getProductName());
            }
            odsGatePrice.setSku(gatePriceExcel.getSku());
            odsGatePrice.setPurPrice(gatePriceExcel.getPurPrice().divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP));
            odsGatePrice.setCut(gatePriceExcel.getCut().divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
            odsGatePrice.setBack(gatePriceExcel.getBack().divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
            odsGatePrice.setBigChannel(gatePriceExcel.getBigChannel());
            odsGatePrice.setLogisticsModel(gatePriceExcel.getLogisticsModel());
            odsGatePrice.setChannelRate(gatePriceExcel.getChannelRate());
            odsGatePrice.setGatePrice(gatePriceExcel.getGatePrice().divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP));
            odsGatePrice.setCreateBy(gatePriceExcel.getCreateBy());
            odsGatePrice.setEndTime("9999-12-31");
            odsGatePrice.setIp("123");
            if(!StringUtils.isBlank(String.valueOf(gatePriceExcel.getTempGatePrice()))){
                odsGatePrice.setTempGatePrice(gatePriceExcel.getTempGatePrice().divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP));
            }
            odsGatePrice.setTempBeginTime(gatePriceExcel.getTempBeginTime());
            odsGatePrice.setTempEndTime(gatePriceExcel.getTempEndTime());
            odsGatePrice.setId(String.valueOf(UUID.randomUUID()));
            BigDecimal cut=odsGatePrice.getCut();
            BigDecimal back=odsGatePrice.getBack();
            BigDecimal purPrice=odsGatePrice.getPurPrice();
            //计算裸价 = 供价 * （1 – 直扣 – 后返）
            BigDecimal barePrice=purPrice.multiply(BigDecimal.ONE.subtract(cut).subtract(back));
            barePrice=barePrice.setScale(2,BigDecimal.ROUND_HALF_UP);
            odsGatePrice.setBarePrice(barePrice);
            //实际毛利： （闸口价 – 裸价）/ 裸价
            BigDecimal actualGrossProfit=odsGatePrice.getGatePrice().subtract(barePrice).divide(barePrice,4, BigDecimal.ROUND_HALF_UP);
            odsGatePrice.setActualGrossprofit(actualGrossProfit);
            //标准毛利
            OdsGateGrossprofit queryGateGrossprofit=new OdsGateGrossprofit();
            queryGateGrossprofit.setBrand(odsGatePrice.getBrand());
            queryGateGrossprofit.setCateGory(odsGatePrice.getCateGory());
            OdsGateGrossprofit odsGateGrossprofit=odsGateGrossprofitDao.queryGrossprofitByBrandAndCateGory(queryGateGrossprofit);
            if(odsGateGrossprofit!=null){
                odsGatePrice.setNormalGrossprofit(odsGateGrossprofit.getGrossProfit());
            }else {
                result.setSuccess(false);
                result.setMessage(odsGatePrice.getBrand()+"品类"+odsGatePrice.getCateGory()+"不存在标准毛利!");
                return result;
            }

            GatePrice queryGatePrice=new GatePrice();
            queryGatePrice.setBrand(odsGatePrice.getBrand());
            queryGatePrice.setCateGory(odsGatePrice.getCateGory());
            queryGatePrice.setSku(odsGatePrice.getSku());
            queryGatePrice.setBigChannel(odsGatePrice.getBigChannel());
            queryGatePrice.setLogisticsModel(odsGatePrice.getLogisticsModel());
            GatePrice old=odsGatePriceDao.queryOdsGatePrice(queryGatePrice);
            if(old!=null){
                old.setFrozenFlag(null);
                old.setEndTime("9999-12-31");
                old.setIsValid("N");
                odsGatePriceDao.updateOdsGatePrice(old);
            }
            if(odsGatePriceDao.createOdsGatePrice(odsGatePrice)<=0){
                sbf.append(odsGatePrice.getSku()+"保存失败");
            }
        }
        if (sbf.length()<1) {
            result.setResult("导入成功");
        } else {
            result.setSuccess(false);
            result.setMessage("导入失败:" + sbf);
        }
        return result;
    }

    @Override
    public GatePrice queryOdsGatePriceById(String id) {
        return odsGatePriceDao.queryOdsGatePriceById(id);
    }

    @Override
    @Transactional
    public ServiceResult<String> updateGatePrice(GatePrice gatePrice) {
        ServiceResult<String> result = new ServiceResult<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GatePrice oldGatePrice=odsGatePriceDao.queryOdsGatePriceById(gatePrice.getId());
        GatePrice newGatePrice=oldGatePrice;
        //标准毛利
        OdsGateGrossprofit queryGateGrossprofit=new OdsGateGrossprofit();
        queryGateGrossprofit.setBrand(oldGatePrice.getBrand());
        queryGateGrossprofit.setCateGory(oldGatePrice.getCateGory());
        OdsGateGrossprofit odsGateGrossprofit=odsGateGrossprofitDao.queryGrossprofitByBrandAndCateGory(queryGateGrossprofit);
        if(odsGateGrossprofit!=null){
            newGatePrice.setNormalGrossprofit(odsGateGrossprofit.getGrossProfit());
        }else {
            result.setSuccess(false);
            result.setMessage(oldGatePrice.getBrand()+"品类"+oldGatePrice.getCateGory()+"不存在标准毛利!");
            return result;
        }
        if(oldGatePrice!=null){
            oldGatePrice.setFrozenFlag("X");
            oldGatePrice.setUpdateBy(gatePrice.getCreateBy());
            oldGatePrice.setEndTime("9999-12-31");
            odsGatePriceDao.updateLockGatePrice(oldGatePrice);
        }else {
            throw new RuntimeException("只能修改有效信息");
        }
        newGatePrice.setId(String.valueOf(UUID.randomUUID()));
        newGatePrice.setGatePrice(gatePrice.getGatePrice());
        newGatePrice.setTempGatePrice(gatePrice.getTempGatePrice());
        newGatePrice.setTempBeginTime(gatePrice.getTempBeginTime());
        newGatePrice.setTempEndTime(gatePrice.getTempEndTime());
        newGatePrice.setLowerStatus(gatePrice.getLowerStatus());
        newGatePrice.setBeginTime(df.format(new Date()));
        String beginTime=gatePrice.getTempBeginTime();
        String endTime=gatePrice.getTempEndTime();
        if(!StringUtil.isEmpty(beginTime)&&!StringUtil.isEmpty(endTime)){
            int days= 0;
            try {
                days = datemindate(df.parse(beginTime),df.parse(endTime));
            } catch (ParseException e) {
                throw new RuntimeException("");
            }
            if(days>=15){
                throw new RuntimeException("临时闸口价时间不能超过15天");
            }
        }

        newGatePrice.setCreateBy(gatePrice.getCreateBy());
        newGatePrice.setIsValid("Y");
        newGatePrice.setFrozenFlag("N");
        newGatePrice.setIp(gatePrice.getIp());
        //实际毛利： （闸口价 – 裸价）/ 裸价
        BigDecimal barePrice=oldGatePrice.getBarePrice();
        if(barePrice!=null) {
            BigDecimal actualGrossProfit = gatePrice.getGatePrice().subtract(barePrice).divide(barePrice, 4, BigDecimal.ROUND_HALF_UP);
            newGatePrice.setActualGrossprofit(actualGrossProfit);
        }

        try {
            odsGatePriceDao.createOdsGatePrice(newGatePrice);
        }catch (Exception e){
            throw new RuntimeException("创建失败");
        }
        result.setMessage("操作成功");
        return result;
    }

    @Override
    public List<String> selectBrandsList() {
        List<String> brandslist=odsGatePriceDao.selectBrandList();
        return brandslist;
    }

    @Override
    public List<String> selectCategoryList() {
        List<String> brandslist=odsGatePriceDao.selectCategoryList();
        return brandslist;
    }

    @Override
    public GatePrice getOdsGatePriceBySku(String sku) {
        return odsGatePriceReadDao.getOdsGatePriceBySku(sku);
    }

    private int datemindate(Date fDate, Date oDate){
        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fDate);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(oDate);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;
    }



}
