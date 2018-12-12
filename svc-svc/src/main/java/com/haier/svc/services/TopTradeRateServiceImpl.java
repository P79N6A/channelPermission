/*
package com.haier.svc.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
//import com.haier.svc.bean.QueryTopTradeRateParameter;
//import com.haier.svc.dao.shop.TopTradeRateDao;
import com.haier.shop.model.QueryTopTradeRateParameter;
import com.haier.shop.service.ShopTopTradeRateService;
import com.haier.svc.service.TopTradeRateService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * Created by LuJun on 2017/11/6.
 *//*

@Service
public class TopTradeRateServiceImpl implements TopTradeRateService {

    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(TopTradeRateServiceImpl.class);

    @Autowired
    private ShopTopTradeRateService shopTopTradeRateService;

    */
/**
     * 查询天猫评价信息
     *
     * @param queryOrder
     * @return
     *//*

    @Override
    public ServiceResult<List<QueryTopTradeRateParameter>> getAllData(QueryTopTradeRateParameter queryOrder) {
        ServiceResult<List<QueryTopTradeRateParameter>> result = new ServiceResult<List<QueryTopTradeRateParameter>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (queryOrder == null) {
                result.setSuccess(false);
                result.setMessage("【用户评价getAllData】获取订单信息服务参数并为null");
                logger.error("【用户评价getAllData】获取订单信息服务参数并为null");
                return result;
            }
            List<QueryTopTradeRateParameter> findQueryOrderList = shopTopTradeRateService
                    .getAllData(queryOrder);
            int count = shopTopTradeRateService.getCount(queryOrder);
            List<QueryTopTradeRateParameter> resultList = new ArrayList<QueryTopTradeRateParameter>();
            for (QueryTopTradeRateParameter item : findQueryOrderList) {
                String productTypeId = item.getProductType();
                String productTypeName = this.getProductTypeName(productTypeId);
                if (null != item.getPayTime() && !"0".equals(item.getPayTime()) && !"".equals(item.getPayTime())) {
                    Date date = new Date(Long.valueOf(item.getPayTime()) * 1000);
                    item.setPayTime(sdf.format(date));
                } else {
                    item.setPayTime("");
                }
                item.setCommentCreateTime(sdf.format(item.getCommentCreateTimeShow()));
                item.setCreateTime(sdf.format(item.getCommentCreateTimeShow()));
                item.setProductType(productTypeName);
                resultList.add(item);
            }
            result.setResult(resultList);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count);
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("用户评价获取评论信息list失败：", e);
        }
        return result;
    }


    */
/**
     * 所有商品类型后续可补充
     *
     * @param productTypeId
     * @return
     *//*

    public String getProductTypeName(String productTypeId) {
        Map<String, String> productTypeMap = new HashMap<String, String>();
        productTypeMap.put("4", "手机");
        productTypeMap.put("10", "台式机");
        productTypeMap.put("13", "彩电");
        productTypeMap.put("22", "吸尘器");
        productTypeMap.put("24", "数码摄像机");
        productTypeMap.put("25", "电饭煲");
        productTypeMap.put("27", "冷柜");
        productTypeMap.put("28", "冰箱");
        productTypeMap.put("30", "数码相机");
        productTypeMap.put("31", "波轮洗衣机");
        productTypeMap.put("36", "燃气灶");
        productTypeMap.put("37", "消毒柜");
        productTypeMap.put("39", "电热水器");
        productTypeMap.put("40", "燃气热水器");
        productTypeMap.put("42", "整体厨房");
        productTypeMap.put("43", "电磁炉");
        productTypeMap.put("44", "电压力锅");
        productTypeMap.put("46", "电熨斗");
        productTypeMap.put("53", "数码相框");
        productTypeMap.put("55", "搅拌机");
        productTypeMap.put("59", "微波炉");
        productTypeMap.put("63", "饮水机");
        String productTypeName = productTypeMap.get(productTypeId) == null ? "" : productTypeMap.get(productTypeId);
        return productTypeName;
    }


}
*/
