package com.haier.shop.service;


import com.haier.shop.model.OrderRepairs;
import com.haier.shop.model.OrderRepairsVo;

import java.util.List;


/**
 * 退货操作 吴坤洋 2017-11-02
 * @author wukunyang
 *
 */
public interface ShopOrderRepairsService {
    int insertSelective(OrderRepairsVo record);//插入
    
    String queryRepaiSn(int cOrderId); //查询此网单是否第一次退货
    
    OrderRepairsVo  queryPairsId(String id);
    
    int updataStatus( String id, String status,String handleRemark); //退货审核

    int updatePushHp(OrderRepairsVo orderRepairsVo); //推送HP 添加信息

    OrderRepairsVo selectPairs(String id); //查询详细信息 推送HP

    int  updateStatus( String receiptStatus, String storageStatus,String id);//修改 发票状态 和 货物状态改成带召回

    String queryIsRejectionSign(String id);//查询用户签收时间来判断是揽收还是拒收


    OrderRepairsVo queryOrderProductId( String id);//查询信息生成出入库单
    
    OrderRepairsVo qureyRepairs(String id);//查询退货信息
    
    OrderRepairsVo selectOrederProductId(String id);//查询网单id 和退货主键
    
    OrderRepairsVo  queryTwoIdentification(String id);//推送二次鉴定的时候 需要查询退货单数据

    /**
     * 根据网单ID查找退货单
     * @param orderProductId
     * @return
     */
    List<OrderRepairs> getByOrderProductId(Integer orderProductId);

    /**
     * 更新退货单发票状态和时间
     * @param orderRepairs
     * @return
     */
    Integer updateReceiptInfo(OrderRepairs orderRepairs);
    
    int updataPushSap(String id);//更新推送SAP时间和状态
    /**
     * 关闭退货单更改退货单状态
     * @param id
     * @param handleRemark
     * @return
     */
    int   RepairsTermination(String id,String handleRemark);
    
    OrderRepairsVo queryRepairsInvoiceId(String repairSn);//根据退货单号查询数据
    
    OrderRepairsVo queryReturnEdit(String id);
}
