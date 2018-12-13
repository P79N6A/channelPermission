package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dao.settleCenter.OutstoragelistDao;
import com.haier.shop.dao.settleCenter.RejectsdetailDao;
import com.haier.shop.model.Outstoragelist;
import com.haier.shop.model.Rejectsdetail;
import com.haier.shop.service.RejectsdetailDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/8/14.
 */
@Service
public class RejectsdetailDataServiceImpl implements RejectsdetailDataService {

    @Autowired
    private RejectsdetailDao rejectsdetailDao;

    @Autowired
    private OutstoragelistDao outstoragelistDao;

    @Override
    public JSONObject paging(Rejectsdetail param) {

        JSONObject result = new JSONObject();
        if(!param.getMonth().equals("全年")){
            int year = Integer.valueOf(((param.getYear()).toString()).substring(0,4));
            int month = Integer.valueOf(((param.getMonth()).toString().substring(0,1)));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.set(Calendar.YEAR, year);
            calendarNow.set(Calendar.MONTH, month - 1);
            calendarNow.set(Calendar.DATE, 1);
            Calendar calendarPreMonth = Calendar.getInstance();
            calendarPreMonth.setTime(calendarNow.getTime());
            calendarPreMonth.add(Calendar.DATE, -1);
            String preMonth = simpleDateFormat.format(calendarPreMonth.getTime());  //开始
            Calendar calendarNextMonth = Calendar.getInstance();
            calendarNextMonth.setTime(calendarNow.getTime());
            calendarNextMonth.add(Calendar.MONTH, 1);
            String nextMonth = simpleDateFormat.format(calendarNextMonth.getTime());//结束
            param.setPreMonth(preMonth);
            param.setNextMonth(nextMonth);

            List<Rejectsdetail> rejectsdetailList = rejectsdetailDao.paging(param);
            Integer[] xarray=new Integer[rejectsdetailList.size()];
            BigDecimal[] yarray=new BigDecimal[rejectsdetailList.size()];
            int i=0;
            Integer rejects=0;
            Integer outqtys=0;
            for(Rejectsdetail re:rejectsdetailList){
                if(re.getOutqty().equals("0")){
                    xarray[i]= Integer.valueOf(re.getRejectnum());
                    yarray[i]=new BigDecimal(0);
                    rejects+=Integer.valueOf(re.getRejectnum());
                    outqtys+=Integer.parseInt((re.getOutqty()));
                    i++;
                }else {
                    xarray[i] = Integer.valueOf(re.getRejectnum());
                    Integer x = Integer.parseInt((re.getRejectnum()));
                    Integer y = Integer.parseInt((re.getOutqty()));
                    float ft = (float) x / y*100;
                    int scale = 2;//设置位数
                    int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
                    BigDecimal bd = new BigDecimal((double) ft);
                    bd = bd.setScale(scale, roundingMode);
                    yarray[i] = bd;
                    rejects += Integer.valueOf(re.getRejectnum());
                    outqtys += Integer.parseInt((re.getOutqty()));
                    i++;
                }
            }
            if (rejectsdetailList.size()==0) {
                rejectsdetailList = new ArrayList<Rejectsdetail>();
                result.put("rows",null);
                return result;
            }else{
                Rejectsdetail j=new Rejectsdetail();
                j.setOutqty(outqtys.toString());
                j.setRejectnum(rejects.toString());
                j.setIndustry("合计");
                rejectsdetailList.add(j);
            }
            result.put("rows", rejectsdetailList);
            result.put("xData",xarray);
            result.put("yData",yarray);

        }else{
            List<List<Rejectsdetail>> rejectsdetailList=new ArrayList<>();
            Integer rejects=0;
            Integer outqtys=0;
            List<Object> obj=new ArrayList<>();
            Integer [] xarray=new Integer[12];
            BigDecimal[] yarray=new BigDecimal[12];
            String [] zarray=new String[12];


            for(int i=0;i<12;i++){
                List<Object> objectList=new ArrayList<>();
                param.setMonth(String.valueOf(i+1));
                int year = Integer.valueOf(((param.getYear()).toString()).substring(0,4));
                int month = Integer.valueOf(param.getMonth());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendarNow = Calendar.getInstance();
                calendarNow.set(Calendar.YEAR, year);
                calendarNow.set(Calendar.MONTH, month - 1);
                calendarNow.set(Calendar.DATE, 1);
                Calendar calendarPreMonth = Calendar.getInstance();
                calendarPreMonth.setTime(calendarNow.getTime());
                calendarPreMonth.add(Calendar.DATE, -1);
                String preMonth = simpleDateFormat.format(calendarPreMonth.getTime());  //开始
                Calendar calendarNextMonth = Calendar.getInstance();
                calendarNextMonth.setTime(calendarNow.getTime());
                calendarNextMonth.add(Calendar.MONTH, 1);
                String nextMonth = simpleDateFormat.format(calendarNextMonth.getTime());//结束
                param.setPreMonth(preMonth);
                param.setNextMonth(nextMonth);
                List<Rejectsdetail> list=rejectsdetailDao.findyear(param);
                if(list.size()>0){
                    if(list.get(0)==null || (list.get(0).getRejectnum().equals("0") && list.get(0).getOutqty().equals("0"))){
                        list = new ArrayList<Rejectsdetail>();
                        xarray[i]=0;
                        yarray[i]=new BigDecimal(0);
                        zarray[i]=i+1+"月";
                        objectList.add(i+1+"月");
                        objectList.add(0);
                        objectList.add(0);
                    }else{
                        for(Rejectsdetail r:list) {
                            if (r.getOutqty().equals("0")) {
                                xarray[i] = Integer.valueOf(r.getRejectnum());
                                yarray[i] = new BigDecimal(0);
                                zarray[i] = i + 1 + "月";
                                objectList.add(i + 1 + "月");
                                objectList.add(Integer.parseInt((r.getRejectnum())));
                                objectList.add(0);
                                rejects += Integer.valueOf(r.getRejectnum());
                                outqtys += Integer.parseInt((r.getOutqty()));
                            } else {
                                xarray[i] = Integer.valueOf(r.getRejectnum());
                                Integer x = Integer.parseInt((r.getRejectnum()));
                                Integer y = Integer.parseInt((r.getOutqty()));
                                float ft = (float) x / y*100;
                                int scale = 2;//设置位数
                                int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
                                BigDecimal bd = new BigDecimal((double) ft);
                                bd = bd.setScale(scale, roundingMode);
                                yarray[i] = bd;
                                zarray[i] = i + 1 + "月";
                                objectList.add(i + 1 + "月");
                                objectList.add(x);
                                objectList.add(y);
                                rejects += Integer.valueOf(r.getRejectnum());
                                outqtys += Integer.parseInt((r.getOutqty()));
                            }
                        }
                    }
                }
                obj.add(objectList);
            }
            List<Object> su=new ArrayList<>();
            su.add("合计");
            su.add(rejects);
            su.add(outqtys);


            String avg="";
            if(outqtys==0){
                avg="0.00%";
            }else{
                float ftg = (float) rejects / outqtys;

                BigDecimal b = new BigDecimal(ftg*100);
                ftg = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
                avg=String.valueOf(ftg)+"%";
            }
            obj.add(su);
            result.put("avgrate",avg);
            result.put("xData",xarray);
            result.put("yData",yarray);
            result.put("zData",zarray);
            result.put("rows",obj);
        }

        //------------------------------------------------------------------------------------------------
        return result;
    }

    @Override
    public JSONObject findOutstoragelist(Outstoragelist param) {
        JSONObject result = new JSONObject();
        List<Outstoragelist> RejectsdetailList = outstoragelistDao.findOutstoragelist(param);
        result.put("data",RejectsdetailList);
        return result;
    }

    /**
     * 不良品导入
     * @param list
     * @return
     */
    @Override
    public ServiceResult<String> bulkImport(List<Rejectsdetail> list) {
        ServiceResult<String> result = new ServiceResult<>();
        if (list.size()==0) {
            result.setSuccess(false);
            result.setMessage("数据导入异常");
            return result;
        }
        for(Rejectsdetail rejectsdetail:list){
            if(null==rejectsdetail.getAttachmentBarCode()||"".equals(rejectsdetail.getAttachmentBarCode())){
                result.setSuccess(false);
                result.setMessage(JSONObject.toJSONString(rejectsdetail.getAttachmentBarCode()+"不存在"));
                return result;
            }
           List<Rejectsdetail> rejectsdetailList=rejectsdetailDao.findallbyattachmentBarCode(rejectsdetail);
               if(rejectsdetailList!=null&&rejectsdetailList.size()!=0){
                   for(Rejectsdetail rejectsdetaillists:rejectsdetailList){
                       rejectsdetail.setId(rejectsdetaillists.getId());
                       rejectsdetailDao.update(rejectsdetail);
                   }
               }else{
                   rejectsdetailDao.insert(rejectsdetail);
               }
        }
        result.setSuccess(true);
        result.setMessage("操作成功");
        return result;
    }

    @Override
    public ServiceResult<String> outImport(List<Outstoragelist> list) {
        ServiceResult<String> result = new ServiceResult<>();
        if (list.size()==0 ) {
            result.setSuccess(false);
            result.setMessage("数据导入异常");
            return result;
        }
        for(Outstoragelist rejectsdetail:list){
            if((null==rejectsdetail.getYears()||"".equals(rejectsdetail.getYears()))&&(null==rejectsdetail.getMonths()||"".equals(rejectsdetail.getMonths()))){
                result.setSuccess(false);
                result.setMessage(JSONObject.toJSONString("日期不存在"));
                return result;
            }
            List<Outstoragelist> rejectsdetailList=outstoragelistDao.findallbyyearmonth(rejectsdetail);
            if(rejectsdetailList!=null&&rejectsdetailList.size()!=0){
                for(Outstoragelist rejectsdetaillists:rejectsdetailList){
                    rejectsdetail.setId(rejectsdetaillists.getId());
                    outstoragelistDao.update(rejectsdetail);
                }
            }else{
                outstoragelistDao.insert(rejectsdetail);
            }
        }
        result.setSuccess(true);
        result.setMessage("操作成功");
        return result;
    }

    /**
     * 不良品导出信息
     * @param param
     * @return
     */
    @Override
    public List<Rejectsdetail> importbad(Rejectsdetail param) {
        int year = Integer.valueOf(((param.getYear()).toString()).substring(0,4));
        int month = Integer.valueOf(((param.getMonth()).toString().substring(0,1)));
        int month2 = Integer.valueOf(((param.getMonth2()).toString().substring(0,1)));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendarNow = Calendar.getInstance();
        calendarNow.set(Calendar.YEAR, year);
        calendarNow.set(Calendar.MONTH, month - 1);
        calendarNow.set(Calendar.DATE, 1);
        Calendar calendarPreMonth = Calendar.getInstance();
        calendarPreMonth.setTime(calendarNow.getTime());
        calendarPreMonth.add(Calendar.DATE, -1);
        String preMonth = simpleDateFormat.format(calendarPreMonth.getTime());  //开始

        Calendar calendarNow1 = Calendar.getInstance();
        calendarNow.set(Calendar.YEAR, year);
        calendarNow.set(Calendar.MONTH, month2 - 1);
        calendarNow.set(Calendar.DATE, 1);
        Calendar calendarPreMonth1 = Calendar.getInstance();
        calendarPreMonth.setTime(calendarNow.getTime());
        calendarPreMonth.add(Calendar.DATE, -1);
        String preMonth1 = simpleDateFormat.format(calendarPreMonth.getTime());
        Calendar calendarNextMonth = Calendar.getInstance();
        calendarNextMonth.setTime(calendarNow.getTime());
        calendarNextMonth.add(Calendar.MONTH, 1);
        String nextMonth = simpleDateFormat.format(calendarNextMonth.getTime());//结束

        param.setPreMonth(preMonth);
        param.setNextMonth(nextMonth);
        List<Rejectsdetail> list=rejectsdetailDao.importbad(param);
        return list;
    }

    /**
     * 出库商品导出信息
     * @param param
     * @return
     */
    @Override
    public List<Outstoragelist> importout(Outstoragelist param) {
        String monthbegin=String.valueOf(Integer.valueOf(param.getMonths())-1);
        String monthend=String.valueOf(Integer.valueOf(param.getMonths2())+1);
        param.setMonths(monthbegin);
        param.setMonths2(monthend);
        List<Outstoragelist> list=outstoragelistDao.importout(param);
        return list;
    }

    /**
     * 责任占比
     * @param param
     * @return
     */
    @Override
    public List<Object> responsibility(Rejectsdetail param) {
        if(param.getMonth()==null || "全年".equals(param.getMonth())){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, param.getYear());
            Date currYearFirst = calendar.getTime();
            calendar.clear();
            calendar.set(Calendar.YEAR, param.getYear());
            calendar.roll(Calendar.DAY_OF_YEAR, -1);
            Date currYearLast = calendar.getTime();

            param.setPreMonth(simpleDateFormat.format(currYearFirst));
            param.setNextMonth(simpleDateFormat.format(currYearLast));

            int jd=rejectsdetailDao.responsibilityjd(param);
            int hr=rejectsdetailDao.responsibilityhr(param);
            List<Object> list=new ArrayList<>();
            list.add(jd);
            list.add(hr);
            return list;
        }else{
        int year = Integer.valueOf(((param.getYear()).toString()).substring(0,4));
        int month = Integer.valueOf(((param.getMonth()).toString().substring(0,1)));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.set(Calendar.YEAR, year);
        calendarNow.set(Calendar.MONTH, month - 1);
        calendarNow.set(Calendar.DATE, 1);
        Calendar calendarPreMonth = Calendar.getInstance();
        calendarPreMonth.setTime(calendarNow.getTime());
        calendarPreMonth.add(Calendar.DATE, -1);
        String preMonth = simpleDateFormat.format(calendarPreMonth.getTime());  //开始
        Calendar calendarNextMonth = Calendar.getInstance();
        calendarNextMonth.setTime(calendarNow.getTime());
        calendarNextMonth.add(Calendar.MONTH, 1);
        String nextMonth = simpleDateFormat.format(calendarNextMonth.getTime());//结束
        param.setPreMonth(preMonth);
        param.setNextMonth(nextMonth);

        int jd=rejectsdetailDao.responsibilityjd(param);
        int hr=rejectsdetailDao.responsibilityhr(param);
        List<Object> list=new ArrayList<>();
        list.add(jd);
        list.add(hr);
        return list;
        }
    }

    /***
     * 处理方式占比信息
     * @param param
     * @return
     */
    @Override
    public List<Object> Processing(Rejectsdetail param) {
        List<Object> list=new ArrayList<>();
        if(param.getMonth()==null || "全年".equals(param.getMonth())){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, param.getYear());
            Date currYearFirst = calendar.getTime();
            calendar.clear();
            calendar.set(Calendar.YEAR, param.getYear());
            calendar.roll(Calendar.DAY_OF_YEAR, -1);
            Date currYearLast = calendar.getTime();

            param.setPreMonth(simpleDateFormat.format(currYearFirst));
            param.setNextMonth(simpleDateFormat.format(currYearLast));
            int Rejection= rejectsdetailDao.Rejection(param);
            int Takepartjd=rejectsdetailDao.Takepartjd(param);
            int afterwhere=rejectsdetailDao.afterwhere(param);


            list.add(Rejection);
            list.add(Takepartjd);
            list.add(afterwhere);

        }else{
            int year = Integer.valueOf(((param.getYear()).toString()).substring(0,4));
            int month = Integer.valueOf(((param.getMonth()).toString().substring(0,1)));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.set(Calendar.YEAR, year);
            calendarNow.set(Calendar.MONTH, month - 1);
            calendarNow.set(Calendar.DATE, 1);
            Calendar calendarPreMonth = Calendar.getInstance();
            calendarPreMonth.setTime(calendarNow.getTime());
            calendarPreMonth.add(Calendar.DATE, -1);
            String preMonth = simpleDateFormat.format(calendarPreMonth.getTime());  //开始
            Calendar calendarNextMonth = Calendar.getInstance();
            calendarNextMonth.setTime(calendarNow.getTime());
            calendarNextMonth.add(Calendar.MONTH, 1);
            String nextMonth = simpleDateFormat.format(calendarNextMonth.getTime());//结束
            param.setPreMonth(preMonth);
            param.setNextMonth(nextMonth);

            int Rejection= rejectsdetailDao.Rejection(param);
            int Takepartjd=rejectsdetailDao.Takepartjd(param);
            int afterwhere=rejectsdetailDao.afterwhere(param);

            list.add(Rejection);
            list.add(Takepartjd);
            list.add(afterwhere);
        }

        return list;
    }
}































