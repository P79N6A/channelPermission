package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.CrmOrderManualDetailItem;
import com.haier.purchase.data.model.CrmOrderManualItem;
import com.haier.svc.bean.GVSOrderPriceRequire;
import com.haier.svc.bean.GVSOrderPriceResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
public interface CrmOrderManualService {

    /**提交订单
     * @param params
     * @return
     */
    public ServiceResult<Boolean> commitOrderManual(Map<String, Object> params);

    /**
     * 删除订单
     * @param params
     */
    public ServiceResult<Boolean> deleteOrderManual(Map<String, Object> params);

    /**
     * 获取CRM手工采购订单信息
     * @param Map<String, Object> params
     * @return
     */
    ServiceResult<List<CrmOrderManualDetailItem>> getCrmOrderManualList(Map<String, Object> params);

    /**
     * 返回总条数
     * @return
     */
    ServiceResult<Integer> getRowCnts();

    /**
     * CRM手工采购订单编辑
     * @param
     */
    public ServiceResult<Boolean> editCRMOrderManual(Map<String, Object> params);

    /**
     * CRM手工采购推送接口
     * @param comi
     * @param l_comdi
     */
    public ServiceResult<Boolean> insertWAOrderBillToCRM(CrmOrderManualItem comi,
                                                         List<CrmOrderManualDetailItem> l_comdi);

    /**
     * 自动手工开单
     * @param sku 物料编码
     * @param edChannelId 渠道
     * @param qty 数量
     * @param estorgeId WA库位码
     * @param billType 订单类型
     * @param sapFlow 配送方式
     * @return
     */
    public ServiceResult<CrmOrderManualDetailItem> createAutoOrderManual(String sku,
                                                                         String edChannelId,
                                                                         Integer qty,
                                                                         String estorgeId,
                                                                         String billType,
                                                                         Integer sapFlow);

    /**
     * 获取价格
     * */
    public GVSOrderPriceResponse quirePrice(GVSOrderPriceRequire order);
}
