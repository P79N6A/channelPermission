package com.haier.purchase.data.dao.purchase;

import com.haier.purchase.data.model.ReplenishOrder;
import com.haier.purchase.data.model.ReplenishOrderConfirmDisplayItem;
import com.haier.purchase.data.model.ReplenishOrderDisplayItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 菜鸟补货单读写接口
 **/
@Mapper
public interface CnReplOrderDao {

    /**
     * 补货单列表分页查询
     *
     * @param paramMap
     * @return
     */
    List<ReplenishOrderDisplayItem> getReplOrders(Map<String, Object> paramMap);

    /**
     * 补货单列表获取记录数
     *
     * @return
     */
    int getRowCnts();

    /**
     * 获取需要确认的补货单信息
     *
     * @param id
     * @return
     */
    ReplenishOrderConfirmDisplayItem getById4Confirm(@Param("id") Integer id);

    /**
     * 根据id查询补货单信息
     *
     * @param id
     * @return
     */
    ReplenishOrder getById(@Param("id") Integer id);

    /**
     * 保存补货单确认信息
     *
     * @param order
     * @return
     */
    int confirmOrder(ReplenishOrder order);
}
