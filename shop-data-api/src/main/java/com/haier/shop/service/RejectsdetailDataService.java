package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Outstoragelist;
import com.haier.shop.model.Rejectsdetail;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */
public interface RejectsdetailDataService {
    public JSONObject paging(Rejectsdetail param);

    public JSONObject findOutstoragelist(Outstoragelist param);

    /**
     * 不良品导入
     * @param list
     * @return
     */
    ServiceResult<String> bulkImport(List<Rejectsdetail> list);

    ServiceResult<String> outImport(List<Outstoragelist> list);

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

    List<Object> responsibility(Rejectsdetail param);
    List<Object> Processing(Rejectsdetail param);

}
