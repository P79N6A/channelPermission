package com.haier.order.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.order.service.RejectsdetailService;
import com.haier.shop.model.Outstoragelist;
import com.haier.shop.model.Rejectsdetail;
import com.haier.shop.service.RejectsdetailDataService;
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


/**
 * Created by Administrator on 2018/8/14.
 */
@Component
public class RejectsdetailServiceImpl implements RejectsdetailService {
    private static final Logger log = LogManager.getLogger(RejectsdetailServiceImpl.class);

    @Autowired
    private RejectsdetailDataService rejectsdetailDataService;

    @Override
    public JSONObject paging(Rejectsdetail param) {
        return rejectsdetailDataService.paging(param);
    }
    @Override
    public JSONObject findOutstoragelist(Outstoragelist param) {
        return rejectsdetailDataService.findOutstoragelist(param);
    }

    @Override
    public ServiceResult<String> execExcel(List<Rejectsdetail> list) {
        return rejectsdetailDataService.bulkImport(list);
    }

    @Override
    public ServiceResult<String> outexecExcel(List<Outstoragelist> list) {
        return rejectsdetailDataService.outImport(list);
    }

    @Override
    public List<Rejectsdetail> importbad(Rejectsdetail param) {
        return rejectsdetailDataService.importbad(param);
    }

    @Override
    public List<Outstoragelist> importout(Outstoragelist param) {
        return rejectsdetailDataService.importout(param);
    }

    @Override
    public List<Object> responsibility(Rejectsdetail param) {
        return rejectsdetailDataService.responsibility(param);
    }

    @Override
    public List<Object> Processing(Rejectsdetail param) {
        return rejectsdetailDataService.Processing(param);
    }
}



















