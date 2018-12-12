package com.haier.afterSale.service;

import java.util.Date;

import com.haier.common.ServiceResult;
import com.haier.shop.model.GroupOrders;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrdersNew;

public interface OrderRepairService {
    /**
     * 审核通过
     * @param id 退换货ID
     * @param operator 操作人
     * @param remark 备注
     * @return
     */
    ServiceResult<Boolean> checkPass(Integer id, String operator, String remark);

    /**
     * 审核不通过
     * @param id 退换货ID
     * @param operator 操作人
     * @param remark 备注
     * @return
     */
    ServiceResult<Boolean> checkNotPass(Integer id, String operator, String remark);

    /**
     * 不良品入库受理退货单为完成-第二步
     * @return
     */
    ServiceResult<Boolean> closeOrderRepairForComplete();

    /**
     * 不良品入库触发后续操作-第一步
     * 1.作废发票
     * 2.修改退货单不良品入库
     * @return
     */
    ServiceResult<Boolean> updateOrderRepairEnterIn21();

    ServiceResult<GroupOrders> getByTailOrderId(Integer tailOrderId);

    /**
     * 查询退货数量
     * @param orderProductId
     * @return
     */
    ServiceResult<Integer> getCountByOrderProductId(Integer orderProductId);

    /**
     * 查询退款单数量
     * @param cOrderSn
     * @return
     */
    ServiceResult<Integer> getCountRepairSn(String cOrderSn);

    /**
     * 保存退换货申请
     * @param orderRepairs 退换货单
     * @param userName 操作人
     * @return
     */
    ServiceResult<Integer> recordReturnGoodsInfo(OrderRepairsNew orderRepairs, String userName);

    /**
     * 作废HP工单
     * @return
     */
    ServiceResult<Boolean> goodsReturnCancelHp(OrderProductsNew orderProducts, OrdersNew order);

    /**
     * 退/换货申请
     * @param typeActual 实际处理方式 (必填）  {value: '1',text: '退货退款'},{value: '2',text: '退货不退款'}]" 
     * @param orderProductSn 网单号 (必填)
     * @param contactName 联系人姓名 
     * @param contactMobile 联系人手机号
     * @param count 退货数量  (必填)
     * @param reason 退款原因  (必填)
     * @param description 描述信息
     */
    ServiceResult<Integer> applyReturnGoods(Integer typeActual, String orderProductSn,
                                            String contactName, String contactMobile,
                                            Integer count, String reason, String description,
                                            String userName);

    /**
     * 不良品退贷日志
     */
    ServiceResult<Boolean> badNessReturnGoodsLogs();

    /**
     * 生成已入库日日单尾款订单
     * 该任务执行的频率， 默认每1分钟执行一次
     */
    ServiceResult<Boolean> genRepairCancelCorder();

    /**
     * 根据 3Wvom退货单号、sku 获取退换货网单
     * @param vomRepairSn
     * @return
     */
    ServiceResult<OrderRepairsNew> getByVomRepairSn(String vomRepairSn, String sku);
    
    /**
     * 退货单 Vom接单（3W正品退仓）
     * @param corderSn
     * @param expNo
     * @param acceptTime
     * @return
     */
    ServiceResult<Boolean> repairAfterVomAccepted3W(String corderSn, String expNo, Date acceptTime);
    
    /**
     * 退货单 VOM 拒单 (3W正品退仓)
     * @param corderSn
     * @param reason
     * @param acceptTime
     * @return
     */
    ServiceResult<Boolean> repairAfterVomRejected3W(String corderSn, String reason, Date acceptTime);
    
   /**
    * 退货单 待检入库（3W正品退仓）
    * @param cOrderSn 记录单号
    * @param lesIoNo 入库单号
    * @param secCode 出入库时间
    * @param secCode 库位
    * @param num 交易数量
    * @return
    */
    ServiceResult<Boolean> repairOrderIn3W(String cOrderSn, String lesIoNo,Date billTime,String secCode,Integer num);
}
