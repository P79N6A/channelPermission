package com.haier.order.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Outstoragelist;
import com.haier.shop.model.Rejectsdetail;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */
public interface RejectsdetailService {
    /**
     * 不良品查询
     * @param param
     * @param
     * @return
     */
    public JSONObject paging(Rejectsdetail param);

    /**
     * 责任占比信息
     * @param param
     * @return
     */
    public List<Object> responsibility(Rejectsdetail param);

    /**
     * 处理方式占比信息
     * @param param
     * @return
     */
    public List<Object> Processing(Rejectsdetail param);

    /**
     * 出库产品查看
     * @param param
     * @return
     */
    public JSONObject findOutstoragelist(Outstoragelist param);

    /**
     * 不良品导入处理
     */
    ServiceResult<String> execExcel(List<Rejectsdetail> list);

    /**
     * 出库商品导入处理
     */
    ServiceResult<String> outexecExcel(List<Outstoragelist> list);

    /**
     * 不良品导出信息查询
     * @param param
     * @return
     */
    public List<Rejectsdetail> importbad(Rejectsdetail param);

    /**
     * 出库商品导出信息查询
     * @param param
     * @return
     */
    public List<Outstoragelist> importout(Outstoragelist param);



}
