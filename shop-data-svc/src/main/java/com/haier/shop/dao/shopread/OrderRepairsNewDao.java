package com.haier.shop.dao.shopread;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.OrderRepairsNew;


public interface OrderRepairsNewDao {
    /**
     * 根据主键获取退货单
     * @param id
     * @return
     */
    OrderRepairsNew get(Integer id);
    /**
     * 获取指定网单的有效退货单
     * @param orderProductId 网单id
     * @return
     */
    OrderRepairsNew getValidByOrderProductId(Integer orderProductId);
    /**
     * 退货单入库后，更新相关信息
     * @param orderRepairs
     * @return
     */
    Integer updateAfterLesInStorage(OrderRepairsNew orderRepairs);
    
    List<OrderRepairsNew> getByOrderProductId(Integer orderProductId);
    
    /**
     * 更新退货单
     * @param orderRepairs
     * @return
     */
    Integer update(OrderRepairsNew orderRepairs);
    
    /**
     * 查询不良品待关闭退货单数据
     * @return
     */
    List<OrderRepairsNew> queryWaitCloseForCompleteList();
    
    Integer updateForComplete(OrderRepairsNew orderRepairs);
    
    /**
     * 通过repairSn查询退货单
     * @param repairSn
     * @return
     */
    OrderRepairsNew getByRepairSn(@Param("repairSn") String repairSn);
    
    /**
     * 通过orderRepairs更新退货单货物状态
     * @param orderRepairs
     * @return
     */
    Integer updateForRepairSn(@Param("repairSn") String repairSn,
                              @Param("storageStatus") Integer storageStatus);
    
    /**
     * 根据网单id统计退货数量
     * @param orderProductId
     * @return
     */
    Integer getCountByOrderProductId(Integer orderProductId);
    
    
    /**
     * 插入一条orderRepairs
     * @param orderRepairs
     * @return
     */
    Integer insert(OrderRepairsNew orderRepairs);
    
    /**
     * 查询退款单数量
     * @param cOrderSn
     * @return
     */
    Integer getCountRepairSn(String cOrderSn);
    
    /**
     * 更新接收状态
     * @param orderRepairs
     * @return
     */
    Integer updateForStatus(OrderRepairsNew orderRepairs);


    int updateExchange(Integer repairId);
}
